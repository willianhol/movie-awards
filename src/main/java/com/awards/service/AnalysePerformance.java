package com.awards.service;

import com.awards.exceptions.NoDataException;
import com.awards.model.Movie;
import com.awards.repository.MovieRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.*;

@ApplicationScoped
public class AnalysePerformance {

    private final MovieRepository repository;

    public AnalysePerformance(MovieRepository repository) {
        this.repository = repository;
    }

    public Map<String, List<ProducerAwardInterval>> calculateIntervals(){
        return this.calculateIntervals(this.repository.listAll());
    }


    private Map<String, List<ProducerAwardInterval>> calculateIntervals(List<Movie> movies) {

        Map<String, List<Integer>> producerWins = new HashMap<>();

        if (movies == null || movies.isEmpty()) {
            throw new NoDataException("No movies data provided");
        }

        movies.stream()
                .filter(Movie::isWinner)
                .filter(movie -> movie.getProducer() != null && !movie.getProducer().isEmpty())
                .forEach(movie ->
                        producerWins
                                .computeIfAbsent(movie.getProducer(), k -> new ArrayList<>())
                                .add(movie.getYear())
                );

        List<ProducerAwardInterval> intervals = new ArrayList<>();

        producerWins.forEach((producer, years) -> {
            if (years.size() > 1) {
                Collections.sort(years);
                for (int i = 1; i < years.size(); i++) {
                    int interval = years.get(i) - years.get(i - 1);
                    intervals.add(new ProducerAwardInterval(producer, interval, years.get(i - 1), years.get(i)));
                }
            }
        });

        OptionalInt minInterval = intervals.stream().mapToInt(ProducerAwardInterval::getInterval).min();
        OptionalInt maxInterval = intervals.stream().mapToInt(ProducerAwardInterval::getInterval).max();

        List<ProducerAwardInterval> minProducers = intervals.stream()
                .filter(p -> p.getInterval() == minInterval.orElse(-1))
                .toList();

        List<ProducerAwardInterval> maxProducers = intervals.stream()
                .filter(p -> p.getInterval() == maxInterval.orElse(-1))
                .toList();

        Map<String, List<ProducerAwardInterval>> result = new HashMap<>();
        result.put("min", minProducers);
        result.put("max", maxProducers);

        return result;
    }

}

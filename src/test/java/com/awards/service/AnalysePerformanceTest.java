package com.awards.service;

import com.awards.exceptions.NoDataException;
import com.awards.model.Movie;
import com.awards.repository.MovieRepository;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;

@QuarkusTest
class AnalysePerformanceTest {

    @InjectMock
    private MovieRepository repository;

    @Inject
    private AnalysePerformance analysePerformance;


    @Test
    void calculateIntervalsWithMultipleProducersAndWinners() {

        when(repository.listAll()).thenReturn(List.of(
                new Movie(null, 2000, "Movie1", "Studio1", "Producer1", true),
                new Movie(null, 2005, "Movie2", "Studio2", "Producer1", true),
                new Movie(null, 2010, "Movie3", "Studio3", "Producer2", true),
                new Movie(null, 2015, "Movie4", "Studio4", "Producer2", true)
        ));

        Map<String, List<ProducerAwardInterval>> result = analysePerformance.calculateIntervals();

        Assertions.assertEquals(2, result.get("min").size());
        Assertions.assertEquals(2, result.get("max").size());
        Assertions.assertEquals(5, result.get("min").get(0).getInterval());
        Assertions.assertEquals(5, result.get("max").get(0).getInterval());
    }

    @Test
    void calculateIntervalsWithSingleProducerAndMultipleWins() {
        when(repository.listAll()).thenReturn(List.of(
                new Movie(null, 2000, "Movie1", "Studio1", "Producer1", true),
                new Movie(null, 2003, "Movie2", "Studio2", "Producer1", true),
                new Movie(null, 2008, "Movie3", "Studio3", "Producer1", true)
        ));

        Map<String, List<ProducerAwardInterval>> result = analysePerformance.calculateIntervals();

        Assertions.assertEquals(1, result.get("min").size());
        Assertions.assertEquals(1, result.get("max").size());
        Assertions.assertEquals(3, result.get("min").get(0).getInterval());
        Assertions.assertEquals(5, result.get("max").get(0).getInterval());
    }

    @Test
    void calculateIntervalsWithNoWinners() {
        when(repository.listAll()).thenReturn(List.of(
                new Movie(null, 2000, "Movie1", "Studio1", "Producer1", false),
                new Movie(null, 2005, "Movie2", "Studio2", "Producer2", false)
        ));

        caculateIntervals();
    }

    @Test
    void shouldThrowsNoDataExceptionWhenReceiveAEmptyList() {
        when(repository.listAll()).thenReturn(List.of());

        Assertions.assertThrows(NoDataException.class, () -> {
            analysePerformance.calculateIntervals();
        });

    }

    @Test
    void calculateIntervalsWithProducerHavingSingleWin() {
        when(repository.listAll()).thenReturn(List.of(
                new Movie(null, 2000, "Movie1", "Studio1", "Producer1", true)
        ));

        caculateIntervals();
    }


    @Test
    void calculateIntervalsWithProducerHavingNonConsecutiveWins() {
        when(repository.listAll()).thenReturn(List.of(
                new Movie(null, 2000, "Movie1", "Studio1", "Producer1", true),
                new Movie(null, 2005, "Movie2", "Studio2", "Producer1", true),
                new Movie(null, 2015, "Movie3", "Studio3", "Producer1", true)
        ));

        Map<String, List<ProducerAwardInterval>> result = analysePerformance.calculateIntervals();

        Assertions.assertEquals(1, result.get("min").size());
        Assertions.assertEquals(1, result.get("max").size());
        Assertions.assertEquals(5, result.get("min").get(0).getInterval());
        Assertions.assertEquals(10, result.get("max").get(0).getInterval());
    }

    @Test
    void calculateIntervalsWithProducerHavingWinsInSameYear() {

        when(repository.listAll()).thenReturn(List.of(
                new Movie(null, 2000, "Movie1", "Studio1", "Producer1", true),
                new Movie(null, 2000, "Movie2", "Studio2", "Producer1", true)
        ));

        Map<String, List<ProducerAwardInterval>> result = analysePerformance.calculateIntervals();

        Assertions.assertEquals(1, result.get("min").size(), "Expected one intervals for the same year wins");
        Assertions.assertEquals(1, result.get("max").size(), "Expected one intervals for the same year wins");
    }

    @Test
    void calculateIntervalsWithMovieHavingNullProducer() {

        when(repository.listAll()).thenReturn(List.of(
                new Movie(null, 2000, "Movie1", "Studio1", null, true)
        ));

        caculateIntervals();
    }

    @Test
    void calculateIntervalsWithMovieHavingNegativeYear() {
        when(repository.listAll()).thenReturn(List.of(
                new Movie(null, -2000, "Movie1", "Studio1", "Producer1", true)
        ));

        caculateIntervals();
    }

    @Test
    void calculateIntervalsWithMovieHavingEmptyProducerName() {
        when(repository.listAll()).thenReturn(List.of(
                new Movie(null, 2000, "Movie1", "Studio1", "", true)
        ));

        caculateIntervals();
    }

    private void caculateIntervals() {
        Map<String, List<ProducerAwardInterval>> result = analysePerformance.calculateIntervals();

        Assertions.assertTrue(result.get("min").isEmpty());
        Assertions.assertTrue(result.get("max").isEmpty());
    }
}

package com.awards.startup;

import com.awards.exceptions.IllegalFileFormatException;
import com.awards.model.Movie;
import com.awards.repository.MovieRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@ApplicationScoped
public class CSVLoader {

    public static final int FIELD_YEAR = 0;
    public static final int FIELD_TITLE = 1;
    public static final int FIELD_STUDIO = 2;
    public static final int FIELD_PRODUCER = 3;
    public static final int FIELD_WINNER = 4;
    public static final String FIELD_SEPARATOR = ";";
    public static final int LINE_SIZE = 5;
    private final MovieRepository repository;

    public CSVLoader(MovieRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void init() {

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("movies.csv")),
                StandardCharsets.UTF_8))) {

            String line = reader.readLine(); // skip header

            while ((line = reader.readLine()) != null) {

                String[] fields = line.split(FIELD_SEPARATOR);

                if (fields.length < LINE_SIZE) continue;

                Movie movie = Movie.builder()
                        .year(Integer.parseInt(fields[FIELD_YEAR]))
                        .title(fields[FIELD_TITLE])
                        .studio(fields[FIELD_STUDIO])
                        .producer(fields[FIELD_PRODUCER])
                        .winner(isWinner(fields))
                        .build();

                repository.persist(movie);
            }
        } catch (Exception e) {
            throw new IllegalFileFormatException("Error loading CSV file: " + e.getMessage(), e);
        }
    }

    private static boolean isWinner(String[] fields) {
        return "yes".equalsIgnoreCase(fields[FIELD_WINNER].trim());
    }
}

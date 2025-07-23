package com.awards.startup;

import io.quarkus.runtime.Startup;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
@ApplicationScoped
public class StartupCSVLoader {


    private final CSVLoader loader;

    public StartupCSVLoader(CSVLoader loader) {
        this.loader = loader;
    }

    @PostConstruct
    @Startup
    @Transactional
    public void init() {
        loader.init();
    }
}

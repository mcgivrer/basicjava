package com.demo.app;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainAppTest {

    MainApp app;

    @BeforeEach
    public void setup() {
        app = new MainApp("testProcessing");
    }

    @AfterEach
    public void tearDown() {
        app = null;
    }

    @Test
    void process() {
        app.run(new String[]{"app.max.iteration=100"});
        Assertions.assertEquals(100, app.getIterationCounter());

    }
}
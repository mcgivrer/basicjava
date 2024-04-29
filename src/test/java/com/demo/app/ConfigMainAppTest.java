package com.demo.app;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ConfigMainAppTest {

    MainApp app;

    @BeforeEach
    void setUp() {
        app = new MainApp("test");
    }

    @AfterEach
    void tearDown() {
        app = null;
    }

    @Test
    void initialize() {
        app.initialize(new String[]{});
        assertFalse(app.getTestMode());
        assertEquals(app.getDebugLevel(), 0);
    }

    @Test
    void loadConfigurationFrom() {
        app.initialize(new String[]{"c=/test-debug-config.properties"});
        assertTrue(app.getTestMode());
        assertEquals(app.getDebugLevel(), 2);
    }

    @Test
    void extractConfigAttributes() {
        Map<String, String> map = Map.of("app.name", "this is my name", "app.debug.level", "1", "app.test.mode", "true");
        app.extractConfigAttributes(map);
        assertEquals("this is my name", app.getName());
        assertTrue(app.getTestMode());
        assertEquals(app.getDebugLevel(), 1);
    }
}
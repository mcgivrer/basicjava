package com.demo.app;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class I18nMainAppTest {


    @Test
    void getMessage() {
        String message = MainApp.getMessage("app.title");
        Assertions.assertEquals("test Application", message);
    }

    @Test
    void replaceTemplate() {
        String message = MainApp.getMessage("app.test.title");
        Assertions.assertEquals("Test-1.0.0", message);
    }
}
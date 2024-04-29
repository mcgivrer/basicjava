package com.demo.app;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Locale;

class LogMainAppTest {

    private final PrintStream standardOut = System.out;
    private final PrintStream standardErr = System.err;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errorStreamCaptor = new ByteArrayOutputStream();
    MainApp app;

    @BeforeEach
    public void setUp() {
        // capture Console output.
        System.setOut(new PrintStream(outputStreamCaptor));
        System.setErr(new PrintStream(errorStreamCaptor));

        app = new MainApp("testLog");
        MainApp.setDebugLevel(5);
        // default Locale US for number format.
        Locale.setDefault(Locale.ROOT);
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
        System.setErr(standardErr);
        app = null;
    }

    @Test
    public void debug() {
        MainApp.debug(LogMainAppTest.class, "test-debug-%d", 1);
        String output = outputStreamCaptor.toString().trim();
        String outputCompareString = output.substring(output.indexOf("|"));
        Assertions.assertEquals("| com.demo.app.LogMainAppTest | DEBUG | test-debug-1", outputCompareString);
    }

    @Test
    public void info() {
        MainApp.info(LogMainAppTest.class, "test-info-%s", "test02");
        String output = outputStreamCaptor.toString().trim();
        String outputCompareString = output.substring(output.indexOf("|"));
        Assertions.assertEquals("| com.demo.app.LogMainAppTest | INFO | test-info-test02", outputCompareString);
    }

    @Test
    public void warn() {
        MainApp.warn(LogMainAppTest.class, "test-warn-%.2f", 1.0f);
        String output = outputStreamCaptor.toString().trim();
        String outputCompareString = output.substring(output.indexOf("|"));
        Assertions.assertEquals("| com.demo.app.LogMainAppTest | WARN | test-warn-1.00", outputCompareString);
    }

    @Test
    public void error() {
        MainApp.error(LogMainAppTest.class, "test-error-%s", true);
        String output = outputStreamCaptor.toString().trim();
        String outputCompareString = output.substring(output.indexOf("|"));
        Assertions.assertEquals("| com.demo.app.LogMainAppTest | ERROR | test-error-true", outputCompareString, "System out is not updated");
        String error = errorStreamCaptor.toString().trim();
        String errorCompareString = error.substring(output.indexOf("|"));
        Assertions.assertEquals("| com.demo.app.LogMainAppTest | ERROR | test-error-true", errorCompareString, "System error is not updated");
    }

    @Test
    public void fatal() {
        MainApp.fatal(LogMainAppTest.class, "test-fatal-%.2f", 128.0f);
        String output = outputStreamCaptor.toString().trim();
        String outputCompareString = output.substring(output.indexOf("|"));
        Assertions.assertEquals("| com.demo.app.LogMainAppTest | FATAL | test-fatal-128.00", outputCompareString, "System out is not updated");

        String error = errorStreamCaptor.toString().trim();
        String errorCompareString = error.substring(output.indexOf("|"));
        Assertions.assertEquals("| com.demo.app.LogMainAppTest | FATAL | test-fatal-128.00", errorCompareString, "System error is not updated");
    }
}
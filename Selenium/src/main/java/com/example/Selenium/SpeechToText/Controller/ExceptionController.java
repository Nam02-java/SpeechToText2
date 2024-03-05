package com.example.Selenium.SpeechToText.Controller;

/**
 * loading
 */
public class ExceptionController extends Exception{

    public ExceptionController(String exceptionNotification) {
        super(exceptionNotification);
    }

    public ExceptionController(String exceptionNotification, Throwable cause) {
        super(exceptionNotification, cause);
    }

    public ExceptionController(Throwable cause) {
        super(cause);
    }
}

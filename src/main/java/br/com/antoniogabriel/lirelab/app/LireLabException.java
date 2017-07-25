package br.com.antoniogabriel.lirelab.app;

public class LireLabException extends RuntimeException {
    public LireLabException(String message) {
        super(message);
    }

    public LireLabException(String message, Throwable cause) {
        super(message, cause);
    }
}
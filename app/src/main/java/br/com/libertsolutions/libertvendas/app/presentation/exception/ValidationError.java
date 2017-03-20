package br.com.libertsolutions.libertvendas.app.presentation.exception;

/**
 * @author Filipe Bezerra
 */
public class ValidationError extends RuntimeException {

    private ValidationError(String message) {
        super(message);
    }

    public static ValidationError newError(String message) {
        return new ValidationError(message);
    }
}
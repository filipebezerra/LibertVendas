package br.com.libertsolutions.libertvendas.app.data.repository;

/**
 * @author Filipe Bezerra
 */
public class MethodNotApplicableException extends RuntimeException {

    public MethodNotApplicableException(final String methodName) {
        super(String.format("%s is not applicable for this class.", methodName));
    }
}

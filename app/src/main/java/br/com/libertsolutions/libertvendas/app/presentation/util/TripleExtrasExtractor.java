package br.com.libertsolutions.libertvendas.app.presentation.util;

/**
 * @author Filipe Bezerra
 */
public interface TripleExtrasExtractor<T1, T2, T3> {
    T1 extractFirstExtra();

    T2 extractSecondExtra();

    T3 extractThirdExtra();
}

package br.com.libertsolutions.libertvendas.app.presentation.importation;

/**
 * @author Filipe Bezerra
 */
public class CompletedImportationEvent {

    private CompletedImportationEvent() {}

    static CompletedImportationEvent newEvent() {
        return new CompletedImportationEvent();
    }
}

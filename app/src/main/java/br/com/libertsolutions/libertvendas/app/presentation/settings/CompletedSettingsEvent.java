package br.com.libertsolutions.libertvendas.app.presentation.settings;

/**
 * @author Filipe Bezerra
 */
public class CompletedSettingsEvent {

    private CompletedSettingsEvent() {}

    static CompletedSettingsEvent newEvent() {
        return new CompletedSettingsEvent();
    }
}

package br.com.libertsolutions.libertvendas.app.presentation.settings;

/**
 * @author Filipe Bezerra
 */
class SettingsEvent {
    private final String key;

    private SettingsEvent(String key) {
        this.key = key;
    }

    static SettingsEvent create(String key) {
        return new SettingsEvent(key);
    }

    String getKey() {
        return key;
    }
}

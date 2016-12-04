package br.com.libertsolutions.libertvendas.app.data.settings;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * @author Filipe Bezerra
 */
public class SettingsRepositories {

    private SettingsRepositories() {/* No instances */}

    private static SettingsRepository sRepository = null;

    public synchronized static SettingsRepository getRepository(@NonNull Context context) {
        if (sRepository == null) {
            sRepository = new SharedPreferencesSettingsRepositoryImpl(context);
        }
        return sRepository;
    }
}

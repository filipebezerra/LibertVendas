package br.com.libertsolutions.libertvendas.app;

import android.content.Context;
import android.support.annotation.NonNull;
import br.com.libertsolutions.libertvendas.app.data.settings.SettingsRepositories;
import br.com.libertsolutions.libertvendas.app.data.settings.SettingsRepository;

/**
 * @author Filipe Bezerra
 */

public class Injection {
    public static SettingsRepository provideSettingsRepository(@NonNull Context context) {
        return SettingsRepositories.getRepository(context);
    }
}

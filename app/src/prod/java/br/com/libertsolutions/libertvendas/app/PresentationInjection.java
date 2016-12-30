package br.com.libertsolutions.libertvendas.app;

import android.content.Context;
import android.support.annotation.NonNull;
import br.com.libertsolutions.libertvendas.app.data.settings.SettingsRepositories;
import br.com.libertsolutions.libertvendas.app.data.settings.SettingsRepository;
import br.com.libertsolutions.libertvendas.app.presentation.PresentationServices;
import br.com.libertsolutions.libertvendas.app.presentation.android.ConnectivityServices;

/**
 * @author Filipe Bezerra
 */
public final class PresentationInjection {

    private PresentationInjection() {/* No instances */}

    public static ConnectivityServices provideConnectivityServices(@NonNull Context context) {
        return PresentationServices.provideConnectivityServices(context);
    }

    public static SettingsRepository provideSettingsRepository(@NonNull Context context) {
        return SettingsRepositories.getRepository(context);
    }
}

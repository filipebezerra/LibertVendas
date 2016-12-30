package br.com.libertsolutions.libertvendas.app.presentation;

import android.content.Context;
import android.support.annotation.NonNull;
import br.com.libertsolutions.libertvendas.app.presentation.android.ConnectivityServices;

/**
 * @author Filipe Bezerra
 */
public final class PresentationServices {

    private PresentationServices() {/* No instances */}

    private static ConnectivityServices sConnectivityServices;

    public static ConnectivityServices provideConnectivityServices(@NonNull Context context) {
        if (sConnectivityServices == null) {
            sConnectivityServices = new ConnectivityServicesImpl(context);
        }
        return sConnectivityServices;
    }
}

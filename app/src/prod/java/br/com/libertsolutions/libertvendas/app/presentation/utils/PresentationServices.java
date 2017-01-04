package br.com.libertsolutions.libertvendas.app.presentation.utils;

import android.content.Context;
import android.support.annotation.NonNull;

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

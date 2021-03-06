package br.com.libertsolutions.libertvendas.app.presentation.helper;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v4.net.ConnectivityManagerCompat;

import static android.net.ConnectivityManager.TYPE_ETHERNET;
import static android.net.ConnectivityManager.TYPE_MOBILE;
import static android.net.ConnectivityManager.TYPE_WIFI;

/**
 * @author Filipe Bezerra
 */
public class ConnectivityHelper {

    private static ConnectivityManager sConnectivityManager;

    public ConnectivityHelper(final Context context) {
        sConnectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    public boolean isConnectionMetered() {
        return ConnectivityManagerCompat.isActiveNetworkMetered(sConnectivityManager);
    }

    @SuppressWarnings("deprecation") public boolean isOnline() {
        if (Build.VERSION.SDK_INT >=  Build.VERSION_CODES.LOLLIPOP) {
            for (Network network : sConnectivityManager.getAllNetworks()) {
                if (network != null) {
                    final NetworkInfo networkInfo = sConnectivityManager.getNetworkInfo(network);

                    if (networkInfo != null && networkInfo.isConnected()) {
                        return true;
                    }
                }
            }
            return false;
        } else {
            NetworkInfo mWifi = sConnectivityManager.getNetworkInfo(TYPE_WIFI);
            if (mWifi != null && mWifi.isConnected()) {
                return true;
            }

            NetworkInfo m3G = sConnectivityManager.getNetworkInfo(TYPE_MOBILE);
            if (m3G != null && m3G.isConnected()) {
                return true;
            }

            if (Build.VERSION.SDK_INT >=  Build.VERSION_CODES.HONEYCOMB_MR2) {
                NetworkInfo mEthernet = sConnectivityManager.getNetworkInfo(TYPE_ETHERNET);
                return mEthernet != null && mEthernet.isConnected();
            } else {
                return false;
            }
        }
    }
}

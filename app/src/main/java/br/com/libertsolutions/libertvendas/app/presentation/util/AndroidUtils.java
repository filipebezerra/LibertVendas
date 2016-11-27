package br.com.libertsolutions.libertvendas.app.presentation.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import static android.net.ConnectivityManager.TYPE_ETHERNET;
import static android.net.ConnectivityManager.TYPE_MOBILE;
import static android.net.ConnectivityManager.TYPE_WIFI;

/**
 * @author Filipe Bezerra
 */
public class AndroidUtils {
    private static ConnectivityManager sConnectivityManager;

    private AndroidUtils() {/* No constructor */}

    @SuppressWarnings("deprecation")
    // in the future see: http://developer.android.com/intl/pt-br/reference/android/support/v4/net/ConnectivityManagerCompat.html
    public static boolean isDeviceConnected(@NonNull Context context) {
        if (sConnectivityManager == null) {
            sConnectivityManager = (ConnectivityManager) context.getSystemService(
                    Context.CONNECTIVITY_SERVICE);
        }

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

    public static void focusThenShowKeyboard(
            @NonNull final Context context, @NonNull final View view) {
        if (view.isShown()) {
            if (view.requestFocus()) {
                showKeyboard(context, view);
            }
        }
    }

    public static void showKeyboard(@NonNull final Context context, final View view) {
        view.postDelayed(() -> {
            InputMethodManager inputManager = (InputMethodManager)
                    context.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.showSoftInput(view.findFocus(), 0);
        }, 50);
    }

    public static void hideKeyboard(@NonNull Context context, View currentFocus) {
        InputMethodManager inputManager = (InputMethodManager) context.getSystemService(
                Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
    }
}

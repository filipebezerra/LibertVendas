package br.com.libertsolutions.libertvendas.app.presentation.util;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import br.com.libertsolutions.libertvendas.app.presentation.activity.LibertVendasActivity;
import br.com.libertsolutions.libertvendas.app.presentation.home.HomeActivity;
import br.com.libertsolutions.libertvendas.app.presentation.settings.SettingsActivity;

/**
 * @author Filipe Bezerra
 */

public class Navigator {
    public static final int RESULT_OK = Activity.RESULT_OK;
    public static final int RESULT_CANCELED = Activity.RESULT_CANCELED;

    private final LibertVendasActivity mActivity;

    public Navigator(LibertVendasActivity activity) {
        mActivity = activity;
    }

    public void toSettings() {
        final Intent settingsIntent = new Intent(mActivity, SettingsActivity.class);
        ActivityCompat.startActivity(mActivity, settingsIntent, null);
    }

    public void toHome() {
        final Intent homeIntent = new Intent(mActivity, HomeActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        ActivityCompat.startActivity(mActivity, homeIntent, null);
    }
}

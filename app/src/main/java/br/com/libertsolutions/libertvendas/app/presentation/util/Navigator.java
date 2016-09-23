package br.com.libertsolutions.libertvendas.app.presentation.util;

import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import br.com.libertsolutions.libertvendas.app.presentation.activity.LibertVendasActivity;
import br.com.libertsolutions.libertvendas.app.presentation.home.HomeActivity;

/**
 * @author Filipe Bezerra
 */

public class Navigator {
    private final LibertVendasActivity mActivity;

    public Navigator(LibertVendasActivity activity) {
        mActivity = activity;
    }

    public void toHome() {
        final Intent homeIntent = new Intent(mActivity, HomeActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        ActivityCompat.startActivity(mActivity, homeIntent, null);
    }
}

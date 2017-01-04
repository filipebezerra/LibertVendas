package br.com.libertsolutions.libertvendas.app.presentation.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import br.com.libertsolutions.libertvendas.app.R;
import br.com.libertsolutions.libertvendas.app.presentation.activity.LibertVendasActivity;

/**
 * @author Filipe Bezerra
 */
public class SettingsActivity extends LibertVendasActivity {

    @Override protected int provideContentViewResource() {
        return R.layout.activity_settings;
    }

    @Override protected void onCreate(@Nullable Bundle inState) {
        super.onCreate(inState);
        setAsSubActivity();

        if (inState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .replace(R.id.fragment, SettingsFragment.newInstance())
                    .commit();
        }
    }
}

package br.com.libertsolutions.libertvendas.app.presentation.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import br.com.libertsolutions.libertvendas.app.R;
import br.com.libertsolutions.libertvendas.app.presentation.base.BaseActivity;

/**
 * @author Filipe Bezerra
 */
public class SettingsActivity extends BaseActivity {

    @Override protected int provideContentViewResource() {
        return R.layout.activity_settings;
    }

    @Override protected void onCreate(@Nullable final Bundle inState) {
        super.onCreate(inState);
        setAsSubActivity();
        if (inState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_layout_all_fragment_container,
                            SettingsFragment.newInstance())
                    .commit();
        }
    }
}

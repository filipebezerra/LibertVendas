package br.com.libertsolutions.libertvendas.app;

import android.app.Application;
import timber.log.Timber;

/**
 * @author Filipe Bezerra
 */
public class ApplicationImpl extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initLogging();
    }

    private void initLogging() {
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }
}

package br.com.libertsolutions.libertvendas.app;

import android.app.Application;
import net.danlew.android.joda.JodaTimeAndroid;

/**
 * @author Filipe Bezerra
 */
public abstract class BaseApplication extends Application {

    private static BaseApplication mInstance;

    @Override public void onCreate() {
        super.onCreate();
        mInstance = this;
        initializeLogging();
        initializeFabric();
        initializeRealm();
        initializeJodaTime();
    }

    protected abstract void initializeLogging();

    protected abstract void initializeFabric();

    protected abstract void initializeRealm();

    private void initializeJodaTime() {
        JodaTimeAndroid.init(this);
    }

    public static BaseApplication getInstance() {
        return mInstance;
    }
}

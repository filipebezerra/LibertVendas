package br.com.libertsolutions.libertvendas.app;

import android.app.Application;

/**
 * @author Filipe Bezerra
 */
public abstract class BaseApplication extends Application {

    private static BaseApplication mInstance;

    @Override public void onCreate() {
        super.onCreate();
        mInstance = this;
        initializeLogging();
        initializePicasso();
        initializeRealm();
    }

    public static BaseApplication getInstance() {
        return mInstance;
    }

    protected abstract void initializeLogging();

    protected abstract void initializePicasso();

    protected abstract void initializeRealm();
}

package br.com.libertsolutions.libertvendas.app;

import android.app.Application;
import com.squareup.leakcanary.LeakCanary;
import net.danlew.android.joda.JodaTimeAndroid;

/**
 * @author Filipe Bezerra
 */
public abstract class BaseApplication extends Application {

    private static BaseApplication mInstance;

    @Override public void onCreate() {
        super.onCreate();
        mInstance = this;
        initLeakCanary();
        initLogging();
        initFabric();
        initRealm();
        initJodaTime();
    }

    private void initLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
    }

    protected abstract void initLogging();

    protected abstract void initFabric();

    protected abstract void initRealm();

    private void initJodaTime() {
        JodaTimeAndroid.init(this);
    }

    public static BaseApplication getInstance() {
        return mInstance;
    }
}

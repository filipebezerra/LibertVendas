package br.com.libertsolutions.libertvendas.app;

import android.app.Application;
import com.crashlytics.android.answers.Answers;
import io.fabric.sdk.android.Fabric;
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
        initializeRealm();
        initializeJodaTime();
        initializeAnswers();
    }

    public static BaseApplication getInstance() {
        return mInstance;
    }

    protected abstract void initializeLogging();

    protected abstract void initializeRealm();

    private void initializeJodaTime() {
        JodaTimeAndroid.init(this);
    }

    private void initializeAnswers() {
        Fabric.with(this, new Answers());
    }
}

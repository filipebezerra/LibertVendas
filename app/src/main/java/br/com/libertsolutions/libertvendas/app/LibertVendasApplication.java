package br.com.libertsolutions.libertvendas.app;

import android.app.Application;
import br.com.libertsolutions.libertvendas.app.presentation.util.CrashReportingTree;
import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.core.CrashlyticsCore;
import io.fabric.sdk.android.Fabric;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import timber.log.Timber;

/**
 * @author Filipe Bezerra
 */
public class LibertVendasApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initializeLogging();
        initializeCrashReporting();
        initializeAnalytics();
        initializeDataStorageWithRealm();
    }

    private void initializeLogging() {
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new CrashReportingTree());
        }
    }

    private void initializeCrashReporting() {
        Crashlytics crashlyticsKit = new Crashlytics.Builder()
                .core(new CrashlyticsCore.Builder().disabled(BuildConfig.DEBUG).build())
                .build();

        Fabric.with(this, crashlyticsKit);
    }

    private void initializeAnalytics() {
        Fabric.with(this, new Answers());
    }

    private void initializeDataStorageWithRealm() {
        RealmConfiguration configuration = new RealmConfiguration.Builder(this)
                .name("libertapp.realm")
                .schemaVersion(4)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(configuration);
    }
}

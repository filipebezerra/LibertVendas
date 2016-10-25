package br.com.libertsolutions.libertvendas.app;

import android.app.Application;
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
        initializeLoggingOrCrashReporting();
        initializeDataStorageWithRealm();
    }

    private void initializeLoggingOrCrashReporting() {
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            //TODO initialize CrashReportingTree integrated with Crashlytics
        }
    }

    private void initializeDataStorageWithRealm() {
        Realm.init(this);
        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .name("libertapp.realm")
                .schemaVersion(2)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(configuration);
    }
}

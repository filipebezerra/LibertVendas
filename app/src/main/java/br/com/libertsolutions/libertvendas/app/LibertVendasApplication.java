package br.com.libertsolutions.libertvendas.app;

import android.app.Application;
import br.com.libertsolutions.libertvendas.app.presentation.util.CrashReportingTree;
import com.crashlytics.android.Crashlytics;
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
        initializeLoggingOrCrashReporting();
        initializeDataStorageWithRealm();
    }

    private void initializeLoggingOrCrashReporting() {
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Fabric.with(this, new Crashlytics());
            Timber.plant(new CrashReportingTree());
        }
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

package br.com.libertsolutions.libertvendas.app;

import android.app.Application;
import br.com.libertsolutions.libertvendas.app.presentation.utils.ReleaseTree;
import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;
import io.fabric.sdk.android.Fabric;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import timber.log.Timber;

/**
 * @author Filipe Bezerra
 */
public class LibertVendasApplication extends Application {

    private static LibertVendasApplication mInstance;

    @Override public void onCreate() {
        super.onCreate();
        mInstance = this;

        initializeCrashlytics();
        initializeLogging();
        initializeDataStorageWithRealm();
    }

    private void initializeCrashlytics() {
        Crashlytics crashlyticsKit = new Crashlytics.Builder()
                .core(new CrashlyticsCore.Builder().disabled(BuildConfig.DEBUG).build())
                .build();

        Fabric.with(this, crashlyticsKit);
    }

    private void initializeLogging() {
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new ReleaseTree());
        }
    }

    private void initializeDataStorageWithRealm() {
        Realm.init(this);
        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .name("libertapp.realm")
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(configuration);
    }

    public static LibertVendasApplication getInstance() {
        return mInstance;
    }
}

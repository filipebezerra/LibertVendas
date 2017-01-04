package br.com.libertsolutions.libertvendas.app;

import android.app.Application;
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

        initializeLogging();
        initializeDataStorageWithRealm();
    }

    private void initializeLogging() {
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {

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

package br.com.libertsolutions.libertvendas.app;

import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import timber.log.Timber;

/**
 * @author Filipe Bezerra
 */
public class ApplicationImpl extends BaseApplication {

    @Override protected void initializeLogging() {
        Fabric.with(this, new Crashlytics());
        Timber.plant(new ReleaseTree());
    }

    @Override protected void initializeRealm() {
        Realm.init(this);
        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .name("libertapp.realm")
                .schemaVersion(BuildConfig.SCHEMA_VERSION)
                .build();
        Realm.setDefaultConfiguration(configuration);
    }
}

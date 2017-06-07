package br.com.libertsolutions.libertvendas.app;

import br.com.libertsolutions.libertvendas.app.data.realm.RealmMigrationImpl;
import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;
import io.fabric.sdk.android.Fabric;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import timber.log.Timber;

/**
 * @author Filipe Bezerra
 */
public class ApplicationImpl extends BaseApplication {

    @Override protected void initLogging() {
        Timber.plant(new ReleaseTree());
    }

    @Override protected void initFabric() {
        Fabric.with(this, new Crashlytics(), new Answers());
    }

    @Override protected void initRealm() {
        Realm.init(this);
        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .name("libertapp.realm")
                .schemaVersion(BuildConfig.SCHEMA_VERSION)
                .migration(new RealmMigrationImpl())
                .build();
        Realm.setDefaultConfiguration(configuration);
    }
}

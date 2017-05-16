package br.com.libertsolutions.libertvendas.app;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import timber.log.Timber;

/**
 * @author Filipe Bezerra
 */
public class ApplicationImpl extends BaseApplication {

    @Override protected void initializeLogging() {
        Timber.plant(new Timber.DebugTree() {
            @Override
            protected String createStackElementTag(final StackTraceElement element) {
                return super.createStackElementTag(element) + ":" + element.getLineNumber();
            }
        });
    }

    @Override protected void initializeFabric() {
        // Fabric it's not enabled in debug build variant
    }

    @Override protected void initializeRealm() {
        Realm.init(this);
        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .name("libertapp-dev.realm")
                .deleteRealmIfMigrationNeeded()
                .schemaVersion(BuildConfig.SCHEMA_VERSION)
                .build();
        Realm.setDefaultConfiguration(configuration);
    }
}

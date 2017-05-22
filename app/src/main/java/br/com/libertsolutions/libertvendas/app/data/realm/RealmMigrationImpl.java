package br.com.libertsolutions.libertvendas.app.data.realm;

import br.com.libertsolutions.libertvendas.app.domain.entity.OrderEntity;
import io.realm.DynamicRealm;
import io.realm.RealmMigration;
import io.realm.RealmSchema;

/**
 * @author Filipe Bezerra
 */
public class RealmMigrationImpl implements RealmMigration {

    @Override public void migrate(
            final DynamicRealm realm, long oldVersion, final long newVersion) {
        final RealmSchema realmSchema = realm.getSchema();

        if (oldVersion == 1) {
            realmSchema
                    .get(OrderEntity.class.getSimpleName())
                    .addField("discountPercentage", Float.class);
            oldVersion++;
        }
    }
}

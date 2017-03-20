package br.com.libertsolutions.libertvendas.app.data.city;

import br.com.libertsolutions.libertvendas.app.data.realm.RealmResultsSpecification;
import br.com.libertsolutions.libertvendas.app.domain.entity.CityEntity;
import io.realm.Realm;
import io.realm.RealmResults;

import static br.com.libertsolutions.libertvendas.app.domain.entity.CityEntity.Fields.NAME;
import static br.com.libertsolutions.libertvendas.app.domain.entity.CityEntity.Fields.STATE_ID_RELATION;
import static io.realm.Sort.ASCENDING;

/**
 * @author Filipe Bezerra
 */
public class CitiesByStateSpecification implements RealmResultsSpecification<CityEntity> {

    private final int stateId;

    public CitiesByStateSpecification(final int stateId) {
        this.stateId = stateId;
    }

    @Override public RealmResults<CityEntity> toRealmResults(final Realm realm) {
        return realm.where(CityEntity.class)
                .equalTo(STATE_ID_RELATION, stateId)
                .findAllSorted(NAME, ASCENDING);
    }
}

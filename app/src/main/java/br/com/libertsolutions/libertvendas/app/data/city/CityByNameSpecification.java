package br.com.libertsolutions.libertvendas.app.data.city;

import br.com.libertsolutions.libertvendas.app.data.realm.RealmSingleSpecification;
import br.com.libertsolutions.libertvendas.app.domain.entity.CityEntity;
import br.com.libertsolutions.libertvendas.app.presentation.util.StringUtils;
import io.realm.Case;
import io.realm.Realm;

/**
 * @author Filipe Bezerra
 */
public class CityByNameSpecification implements RealmSingleSpecification<CityEntity> {

    private final String name;

    public CityByNameSpecification(final String name) {
        this.name = StringUtils.removeAccents(name);
    }

    @Override public CityEntity toSingle(final Realm realm) {
        return realm.where(CityEntity.class)
                .equalTo(CityEntity.Fields.NAME, name, Case.INSENSITIVE)
                .findFirst();
    }
}

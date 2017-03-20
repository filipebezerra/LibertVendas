package br.com.libertsolutions.libertvendas.app.data.city;

import br.com.libertsolutions.libertvendas.app.data.realm.RealmRepository;
import br.com.libertsolutions.libertvendas.app.domain.entity.CityEntity;
import br.com.libertsolutions.libertvendas.app.domain.pojo.City;

import static br.com.libertsolutions.libertvendas.app.data.LocalDataInjector.cityMapper;

/**
 * @author Filipe Bezerra
 */
public class CityRealmRepository extends RealmRepository<City, CityEntity>
        implements CityRepository {

    public CityRealmRepository() {
        super(CityEntity.class, cityMapper());
    }
}

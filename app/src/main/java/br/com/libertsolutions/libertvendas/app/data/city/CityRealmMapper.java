package br.com.libertsolutions.libertvendas.app.data.city;

import br.com.libertsolutions.libertvendas.app.data.realm.RealmMapper;
import br.com.libertsolutions.libertvendas.app.domain.entity.CityEntity;
import br.com.libertsolutions.libertvendas.app.domain.pojo.City;

import static br.com.libertsolutions.libertvendas.app.data.LocalDataInjector.stateMapper;

/**
 * @author Filipe Bezerra
 */
public class CityRealmMapper extends RealmMapper<City, CityEntity> {

    @Override public CityEntity toEntity(final City object) {
        return new CityEntity()
                .withCityId(object.getCityId())
                .withMunicipalityCode(object.getMunicipalityCode())
                .withName(object.getName())
                .withState(stateMapper().toEntity(object.getState()));
    }

    @Override public City toViewObject(final CityEntity entity) {
        return new City()
                .withCityId(entity.getCityId())
                .withMunicipalityCode(entity.getMunicipalityCode())
                .withName(entity.getName())
                .withState(stateMapper().toViewObject(entity.getState()));
    }
}

package br.com.libertsolutions.libertvendas.app.domain.entity;

import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;
import io.realm.annotations.Required;

/**
 * @author Filipe Bezerra
 */
@RealmClass
public class CityEntity implements RealmModel {

    public static final class Fields {

        public static final String STATE_ID_RELATION = "state."+StateEntity.Fields.STATE_ID;

        public static final String NAME = "name";
    }

    @PrimaryKey
    private Integer cityId;

    private String municipalityCode;

    @Required
    private String name;

    private StateEntity state;

    public Integer getCityId() {
        return cityId;
    }

    public CityEntity withCityId(final Integer cityId) {
        this.cityId = cityId;
        return this;
    }

    public String getMunicipalityCode() {
        return municipalityCode;
    }

    public CityEntity withMunicipalityCode(final String municipalityCode) {
        this.municipalityCode = municipalityCode;
        return this;
    }

    public String getName() {
        return name;
    }

    public CityEntity withName(final String name) {
        this.name = name;
        return this;
    }

    public StateEntity getState() {
        return state;
    }

    public CityEntity withState(final StateEntity state) {
        this.state = state;
        return this;
    }
}

package br.com.libertsolutions.libertvendas.app.domain.entity;

import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;
import io.realm.annotations.Required;

/**
 * @author Filipe Bezerra
 */
@RealmClass
public class StateEntity implements RealmModel {

    public static final class Fields {

        public static final String STATE_ID = "stateId";

        public static final String NAME = "name";
    }

    @PrimaryKey
    private Integer stateId;

    @Required
    private String federativeUnit;

    @Required
    private String name;

    public Integer getStateId() {
        return stateId;
    }

    public StateEntity withStateId(final Integer stateId) {
        this.stateId = stateId;
        return this;
    }

    public String getFederativeUnit() {
        return federativeUnit;
    }

    public StateEntity withFederativeUnit(final String federativeUnit) {
        this.federativeUnit = federativeUnit;
        return this;
    }

    public String getName() {
        return name;
    }

    public StateEntity withName(final String name) {
        this.name = name;
        return this;
    }
}

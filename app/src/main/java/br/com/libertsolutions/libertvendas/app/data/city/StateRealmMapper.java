package br.com.libertsolutions.libertvendas.app.data.city;

import br.com.libertsolutions.libertvendas.app.data.realm.RealmMapper;
import br.com.libertsolutions.libertvendas.app.domain.entity.StateEntity;
import br.com.libertsolutions.libertvendas.app.domain.pojo.State;

/**
 * @author Filipe Bezerra
 */
public class StateRealmMapper extends RealmMapper<State, StateEntity> {

    @Override public StateEntity toEntity(final State object) {
        return new StateEntity()
                .withStateId(object.getStateId())
                .withFederativeUnit(object.getFederativeUnit())
                .withName(object.getName());
    }

    @Override public State toViewObject(final StateEntity entity) {
        return new State()
                .withStateId(entity.getStateId())
                .withFederativeUnit(entity.getFederativeUnit())
                .withName(entity.getName());
    }
}

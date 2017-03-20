package br.com.libertsolutions.libertvendas.app.data.city;

import br.com.libertsolutions.libertvendas.app.data.realm.RealmRepository;
import br.com.libertsolutions.libertvendas.app.domain.entity.StateEntity;
import br.com.libertsolutions.libertvendas.app.domain.pojo.State;

import static br.com.libertsolutions.libertvendas.app.data.LocalDataInjector.stateMapper;

/**
 * @author Filipe Bezerra
 */
public class StateRealmRepository extends RealmRepository<State, StateEntity>
        implements StateRepository {

    public StateRealmRepository() {
        super(StateEntity.class, stateMapper());
    }
}

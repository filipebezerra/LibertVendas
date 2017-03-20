package br.com.libertsolutions.libertvendas.app.data.realm;

import br.com.libertsolutions.libertvendas.app.data.repository.Specification;
import io.realm.Realm;
import io.realm.RealmModel;

/**
 * @author Filipe Bezerra
 */
public interface RealmSingleSpecification<E extends RealmModel> extends Specification {

    E toSingle(Realm realm);
}

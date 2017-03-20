package br.com.libertsolutions.libertvendas.app.data.realm;

import br.com.libertsolutions.libertvendas.app.data.repository.Specification;
import io.realm.Realm;
import io.realm.RealmModel;
import io.realm.RealmResults;

/**
 * @author Filipe Bezerra
 */
public interface RealmResultsSpecification<E extends RealmModel> extends Specification {

    RealmResults<E> toRealmResults(Realm realm);
}

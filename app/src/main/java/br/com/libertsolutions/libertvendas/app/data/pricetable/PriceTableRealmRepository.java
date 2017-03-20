package br.com.libertsolutions.libertvendas.app.data.pricetable;

import br.com.libertsolutions.libertvendas.app.data.realm.RealmRepository;
import br.com.libertsolutions.libertvendas.app.domain.entity.PriceTableEntity;
import br.com.libertsolutions.libertvendas.app.domain.pojo.PriceTable;

import static br.com.libertsolutions.libertvendas.app.data.LocalDataInjector.priceTableMapper;

/**
 * @author Filipe Bezerra
 */
public class PriceTableRealmRepository extends RealmRepository<PriceTable, PriceTableEntity>
        implements PriceTableRepository {

    public PriceTableRealmRepository() {
        super(PriceTableEntity.class, priceTableMapper());
    }
}

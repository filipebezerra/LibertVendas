package br.com.libertsolutions.libertvendas.app.data.pricetable;

import br.com.libertsolutions.libertvendas.app.data.realm.RealmSingleSpecification;
import br.com.libertsolutions.libertvendas.app.domain.entity.PriceTableEntity;
import io.realm.Realm;

import static br.com.libertsolutions.libertvendas.app.domain.entity.PriceTableEntity.Fields.PRICE_TABLE_ID;

/**
 * @author Filipe Bezerra
 */
public class PriceTableByIdSpecification
        implements RealmSingleSpecification<PriceTableEntity> {

    private final int priceTableId;

    public PriceTableByIdSpecification(final int priceTableId) {
        this.priceTableId = priceTableId;
    }

    @Override public PriceTableEntity toSingle(final Realm realm) {
        return realm.where(PriceTableEntity.class)
                .equalTo(PRICE_TABLE_ID, priceTableId)
                .findFirst();
    }
}

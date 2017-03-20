package br.com.libertsolutions.libertvendas.app.data.pricetable;

import br.com.libertsolutions.libertvendas.app.data.realm.RealmMapper;
import br.com.libertsolutions.libertvendas.app.domain.entity.PriceTableEntity;
import br.com.libertsolutions.libertvendas.app.domain.entity.PriceTableItemEntity;
import br.com.libertsolutions.libertvendas.app.domain.pojo.PriceTable;
import io.realm.RealmList;

import static br.com.libertsolutions.libertvendas.app.data.LocalDataInjector.priceTableItemMapper;

/**
 * @author Filipe Bezerra
 */
public class PriceTableRealmMapper extends RealmMapper<PriceTable, PriceTableEntity> {

    @Override public PriceTableEntity toEntity(final PriceTable object) {
        return new PriceTableEntity()
                .withPriceTableId(object.getPriceTableId())
                .withCode(object.getCode())
                .withName(object.getName())
                .withItems((RealmList<PriceTableItemEntity>) priceTableItemMapper()
                        .toEntities(object.getItems()))
                .withActive(object.isActive())
                .withLastChangeTime(object.getLastChangeTime());
    }

    @Override public PriceTable toViewObject(final PriceTableEntity entity) {
        return new PriceTable()
                .withPriceTableId(entity.getPriceTableId())
                .withCode(entity.getCode())
                .withName(entity.getName())
                .withItems(priceTableItemMapper().toViewObjects(entity.getItems()))
                .withActive(entity.isActive())
                .withLastChangeTime(entity.getLastChangeTime());
    }
}

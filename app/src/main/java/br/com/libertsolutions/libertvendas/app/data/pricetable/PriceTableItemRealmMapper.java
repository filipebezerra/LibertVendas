package br.com.libertsolutions.libertvendas.app.data.pricetable;

import br.com.libertsolutions.libertvendas.app.data.realm.RealmMapper;
import br.com.libertsolutions.libertvendas.app.domain.entity.PriceTableItemEntity;
import br.com.libertsolutions.libertvendas.app.domain.pojo.PriceTableItem;

import static br.com.libertsolutions.libertvendas.app.data.LocalDataInjector.productMapper;

/**
 * @author Filipe Bezerra
 */
public class PriceTableItemRealmMapper extends RealmMapper<PriceTableItem, PriceTableItemEntity> {

    @Override public PriceTableItemEntity toEntity(final PriceTableItem object) {
        return new PriceTableItemEntity()
                .withItemId(object.getItemId())
                .withSalesPrice(object.getSalesPrice())
                .withProductId(object.getProductId())
                .withProduct(productMapper().toEntity(object.getProduct()))
                .withLastChangeTime(object.getLastChangeTime());
    }

    @Override public PriceTableItem toViewObject(final PriceTableItemEntity entity) {
        return new PriceTableItem()
                .withItemId(entity.getItemId())
                .withSalesPrice(entity.getSalesPrice())
                .withProductId(entity.getProductId())
                .withProduct(productMapper().toViewObject(entity.getProduct()))
                .withLastChangeTime(entity.getLastChangeTime());
    }
}

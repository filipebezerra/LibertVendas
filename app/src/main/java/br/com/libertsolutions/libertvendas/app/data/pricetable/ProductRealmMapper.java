package br.com.libertsolutions.libertvendas.app.data.pricetable;

import br.com.libertsolutions.libertvendas.app.data.realm.RealmMapper;
import br.com.libertsolutions.libertvendas.app.domain.entity.ProductEntity;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Product;

/**
 * @author Filipe Bezerra
 */
public class ProductRealmMapper extends RealmMapper<Product, ProductEntity> {

    @Override public ProductEntity toEntity(final Product object) {
        return new ProductEntity()
                .withProductId(object.getProductId())
                .withCode(object.getCode())
                .withBarCode(object.getBarCode())
                .withDescription(object.getDescription())
                .withUnit(object.getUnit())
                .withGroup(object.getGroup())
                .withStockQuantity(object.getStockQuantity())
                .withObservation(object.getObservation())
                .withActive(object.isActive())
                .withLastChangeTime(object.getLastChangeTime());
    }

    @Override public Product toViewObject(final ProductEntity entity) {
        return new Product()
                .withProductId(entity.getProductId())
                .withCode(entity.getCode())
                .withBarCode(entity.getBarCode())
                .withDescription(entity.getDescription())
                .withUnit(entity.getUnit())
                .withGroup(entity.getGroup())
                .withStockQuantity(entity.getStockQuantity())
                .withObservation(entity.getObservation())
                .withActive(entity.isActive())
                .withLastChangeTime(entity.getLastChangeTime());
    }
}

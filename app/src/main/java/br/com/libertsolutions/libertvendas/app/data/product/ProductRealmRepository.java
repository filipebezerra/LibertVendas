package br.com.libertsolutions.libertvendas.app.data.product;

import br.com.libertsolutions.libertvendas.app.data.realm.RealmRepository;
import br.com.libertsolutions.libertvendas.app.domain.entity.ProductEntity;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Product;

import static br.com.libertsolutions.libertvendas.app.data.LocalDataInjector.productMapper;

/**
 * @author Filipe Bezerra
 */
public class ProductRealmRepository extends RealmRepository<Product, ProductEntity>
        implements ProductRepository {

    public ProductRealmRepository() {
        super(ProductEntity.class, productMapper());
    }
}

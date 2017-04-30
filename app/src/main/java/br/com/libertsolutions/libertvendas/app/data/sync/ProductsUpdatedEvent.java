package br.com.libertsolutions.libertvendas.app.data.sync;

/**
 * @author Filipe Bezerra
 */
public class ProductsUpdatedEvent {

    private ProductsUpdatedEvent() {}

    static ProductsUpdatedEvent productsUpdated() {
        return new ProductsUpdatedEvent();
    }
}

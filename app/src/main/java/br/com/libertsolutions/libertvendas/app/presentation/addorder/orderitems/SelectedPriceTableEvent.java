package br.com.libertsolutions.libertvendas.app.presentation.addorder.orderitems;

import br.com.libertsolutions.libertvendas.app.domain.pojo.PriceTable;

/**
 * @author Filipe Bezerra
 */
public class SelectedPriceTableEvent {

    private final PriceTable mPriceTable;

    private SelectedPriceTableEvent(final PriceTable priceTable) {
        mPriceTable = priceTable;
    }

    static SelectedPriceTableEvent newEvent(final PriceTable priceTable) {
        return new SelectedPriceTableEvent(priceTable);
    }

    public PriceTable getPriceTable() {
        return mPriceTable;
    }
}

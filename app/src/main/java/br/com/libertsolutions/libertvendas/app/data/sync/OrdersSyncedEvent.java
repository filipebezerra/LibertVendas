package br.com.libertsolutions.libertvendas.app.data.sync;

/**
 * @author Filipe Bezerra
 */
public class OrdersSyncedEvent {

    private OrdersSyncedEvent() {}

    static OrdersSyncedEvent ordersSynced() {
        return new OrdersSyncedEvent();
    }
}

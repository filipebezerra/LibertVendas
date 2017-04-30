package br.com.libertsolutions.libertvendas.app.data.sync;

/**
 * @author Filipe Bezerra
 */
public class CustomersSyncedEvent {

    private CustomersSyncedEvent() {}

    static CustomersSyncedEvent customersSynced() {
        return new CustomersSyncedEvent();
    }
}

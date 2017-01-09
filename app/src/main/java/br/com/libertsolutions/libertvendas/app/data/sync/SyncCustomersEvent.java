package br.com.libertsolutions.libertvendas.app.data.sync;

/**
 * @author Filipe Bezerra
 */
public class SyncCustomersEvent {

    private SyncCustomersEvent() {}

    static SyncCustomersEvent newEvent() {
        return new SyncCustomersEvent();
    }
}

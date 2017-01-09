package br.com.libertsolutions.libertvendas.app.data.sync;

/**
 * @author Filipe Bezerra
 */
public class SyncOrdersEvent {

    private SyncOrdersEvent() {}

    static SyncOrdersEvent newEvent() {
        return new SyncOrdersEvent();
    }
}

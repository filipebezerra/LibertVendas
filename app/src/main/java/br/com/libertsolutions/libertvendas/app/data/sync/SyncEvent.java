package br.com.libertsolutions.libertvendas.app.data.sync;

/**
 * @author Filipe Bezerra
 */
public class SyncEvent {

    private final int result;

    private SyncEvent(final int result) {
        this.result = result;
    }

    public static SyncEvent newEvent(final int result) {
        return new SyncEvent(result);
    }

    public int getResult() {
        return result;
    }
}

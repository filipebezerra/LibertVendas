package br.com.libertsolutions.libertvendas.app.data.sync;

/**
 * @author Filipe Bezerra
 */
public class SyncEvent {

    private final String tag;

    private final int result;

    private SyncEvent(final String tag, final int result) {
        this.tag = tag;
        this.result = result;
    }

    public static SyncEvent newEvent(final String tag, final int result) {
        return new SyncEvent(tag, result);
    }

    public String getTag() {
        return tag;
    }

    public int getResult() {
        return result;
    }
}

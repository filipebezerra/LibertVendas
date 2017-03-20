package br.com.libertsolutions.libertvendas.app.domain.pojo;

/**
 * @author Filipe Bezerra
 */
public class Settings {

    private final String serverAddress;

    private final String authKey;

    private final boolean automaticallySyncOrders;

    private final int syncPeriodicity;

    private Settings(
            final String serverAddress, final String authKey,
            final boolean automaticallySyncOrders, final int syncPeriodicity) {
        this.serverAddress = serverAddress;
        this.authKey = authKey;
        this.automaticallySyncOrders = automaticallySyncOrders;
        this.syncPeriodicity = syncPeriodicity;
    }

    public static Settings create(
            final String serverAddress, final String authKey,
            final boolean automaticallySyncOrders, final int syncPeriodicity) {
        return new Settings(serverAddress, authKey, automaticallySyncOrders, syncPeriodicity);
    }

    public String getServerAddress() {
        return serverAddress;
    }

    public String getAuthKey() {
        return authKey;
    }

    public boolean isAutomaticallySyncOrders() {
        return automaticallySyncOrders;
    }

    public int getSyncPeriodicity() {
        return syncPeriodicity;
    }
}

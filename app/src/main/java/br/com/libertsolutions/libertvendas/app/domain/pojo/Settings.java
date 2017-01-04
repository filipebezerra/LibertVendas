package br.com.libertsolutions.libertvendas.app.domain.pojo;

/**
 * @author Filipe Bezerra
 */
public final class Settings {

    private final String serverAddress;

    private final String authenticationKey;

    private final boolean autoSync;

    private Settings(
            final String serverAddress, final String authenticationKey, final boolean autoSync) {
        this.serverAddress = serverAddress;
        this.authenticationKey = authenticationKey;
        this.autoSync = autoSync;
    }

    public static Settings create(
            final String serverAddress, final String authenticationKey, final boolean autoSync) {
        return new Settings(serverAddress, authenticationKey, autoSync);
    }

    public String getServerAddress() {
        return serverAddress;
    }

    public String getAuthenticationKey() {
        return authenticationKey;
    }

    public boolean isAutoSync() {
        return autoSync;
    }
}

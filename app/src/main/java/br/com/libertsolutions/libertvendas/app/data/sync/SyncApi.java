package br.com.libertsolutions.libertvendas.app.data.sync;

import br.com.libertsolutions.libertvendas.app.domain.dto.ServerStatus;
import retrofit2.Call;
import retrofit2.http.GET;
import rx.Observable;

/**
 * @author Filipe Bezerra
 */
public interface SyncApi {

    @GET("api/servidor/get") Observable<ServerStatus> serverStatus();

    @GET("api/servidor/get") Call<ServerStatus> getServerStatus();
}

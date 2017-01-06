package br.com.libertsolutions.libertvendas.app.data.pedido;

import br.com.libertsolutions.libertvendas.app.domain.dto.PedidoDto;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @author Filipe Bezerra
 */
public interface PedidoService {

    @POST("api/pedido/post") Observable<PedidoDto> post(
            @Query("cnpj") String cnpj, @Body PedidoDto pedido);

    @PUT("api/pedido/put") Observable<PedidoDto> put(
            @Query("cnpj") String cnpj, @Body PedidoDto pedido);
}

package br.com.libertsolutions.libertvendas.app.data.pedido;

import br.com.libertsolutions.libertvendas.app.domain.dto.PedidoDto;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author Filipe Bezerra
 */
public interface PedidoService {

    @POST("api/pedido/post") Call<PedidoDto> post(
            @Query("cnpj") String cnpj, @Query("cpfCnpj") String cpfCnpj, @Body PedidoDto pedido);
}
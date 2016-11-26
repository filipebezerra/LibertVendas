package br.com.libertsolutions.libertvendas.app.data.pedidos;

import br.com.libertsolutions.libertvendas.app.domain.dto.PedidoDto;
import br.com.libertsolutions.libertvendas.app.domain.dto.PedidoResponseDto;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Pedido;
import java.util.List;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @author Filipe Bezerra
 */
public interface PedidoService {
    Observable<List<Pedido>> get();

    @POST("api/pedido/post") Observable<List<PedidoResponseDto>> post(
            @Body List<PedidoDto> pPedidos,
            @Query("cnpj") String cnpjEmpresa,
            @Query("cpfCnpj") String cpfCnpjVendedor);
}

package br.com.libertsolutions.libertvendas.app.data.order;

import br.com.libertsolutions.libertvendas.app.domain.dto.OrderDto;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author Filipe Bezerra
 */
public interface OrderApi {

    @POST("api/pedido/post")
    Call<OrderDto> createOrder(
            @Query("cnpj") String companyCnpj,
            @Query("cpfCnpj") String salesmanCpfOrCnpj, @Body OrderDto order);
}

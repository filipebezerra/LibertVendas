package br.com.libertsolutions.libertvendas.app.data.cliente;

import br.com.libertsolutions.libertvendas.app.domain.dto.ClienteDto;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @author Filipe Bezerra
 */
public interface ClienteService {

    @GET("api/cliente/get") Observable<List<ClienteDto>> get(@Query("cnpj") String cnpj);

    @POST("api/cliente/post") Call<ClienteDto> post(
            @Query("cnpj") String cnpj, @Body ClienteDto newCustomer);

    @PATCH("api/cliente/patch") Call<List<ClienteDto>> patch(
            @Query("cnpj") String cnpj, @Body List<ClienteDto> changedCustomers);
}

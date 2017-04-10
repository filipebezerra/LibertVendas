package br.com.libertsolutions.libertvendas.app.data.customer;

import br.com.libertsolutions.libertvendas.app.domain.pojo.Customer;
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
public interface CustomerApi {

    @GET("api/cliente/get") Observable<List<Customer>> get(@Query("cnpj") String cnpj);

    @POST("api/cliente/post")
    Call<Customer> createCustomer(@Query("cnpj") String companyCnpj, @Body Customer customer);

    @PATCH("api/cliente/patch")
    Call<List<Customer>> updateCustomers(
            @Query("cnpj") String companyCnpj, @Body List<Customer> customers);

    @GET("api/cliente/get") Call<List<Customer>> getUpdates(
            @Query("cnpj") String cnpj, @Query("ultimaAtualizacao") String lastUpdateTime);
}

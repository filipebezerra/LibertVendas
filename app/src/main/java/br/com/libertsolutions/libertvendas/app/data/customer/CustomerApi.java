package br.com.libertsolutions.libertvendas.app.data.customer;

import br.com.libertsolutions.libertvendas.app.domain.pojo.Customer;
import java.util.List;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @author Filipe Bezerra
 */
public interface CustomerApi {

    @GET("api/cliente/get") Observable<List<Customer>> get(@Query("cnpj") String cnpj);
}

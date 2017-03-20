package br.com.libertsolutions.libertvendas.app.data.paymentmethod;

import br.com.libertsolutions.libertvendas.app.domain.pojo.PaymentMethod;
import java.util.List;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @author Filipe Bezerra
 */
public interface PaymentMethodApi {

    @GET("api/formpgto/get") Observable<List<PaymentMethod>> get(@Query("cnpj") String cnpj);
}

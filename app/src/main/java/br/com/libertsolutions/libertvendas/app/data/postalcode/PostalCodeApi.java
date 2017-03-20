package br.com.libertsolutions.libertvendas.app.data.postalcode;

import br.com.libertsolutions.libertvendas.app.domain.pojo.PostalCode;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * @author Filipe Bezerra
 */
public interface PostalCodeApi {

    @GET("http://api.postmon.com.br/v1/cep/{postalCode}")
    Observable<PostalCode> get(@Path("postalCode") String postalCode);
}

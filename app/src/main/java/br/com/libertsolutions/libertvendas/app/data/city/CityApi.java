package br.com.libertsolutions.libertvendas.app.data.city;

import br.com.libertsolutions.libertvendas.app.domain.pojo.City;
import java.util.List;
import retrofit2.http.GET;
import rx.Observable;

/**
 * @author Filipe Bezerra
 */
public interface CityApi {

    @GET("api/cidade/get") Observable<List<City>> get();
}

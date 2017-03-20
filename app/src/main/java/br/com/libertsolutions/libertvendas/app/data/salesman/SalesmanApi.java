package br.com.libertsolutions.libertvendas.app.data.salesman;

import br.com.libertsolutions.libertvendas.app.domain.dto.SalesmanDto;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @author Filipe Bezerra
 */
public interface SalesmanApi {

    @GET("api/vendedor/get") Observable<SalesmanDto> get(
            @Query("cpfCnpj") String cpfOrCnpj, @Query("senha") String password);
}

package br.com.libertsolutions.libertvendas.app.data.cliente;

import br.com.libertsolutions.libertvendas.app.domain.dto.ClienteDto;
import java.util.List;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @author Filipe Bezerra
 */
public interface ClienteService {

    @GET("api/cliente/get") Observable<List<ClienteDto>> get(@Query("cnpj") String cnpj);
}

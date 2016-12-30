package br.com.libertsolutions.libertvendas.app.data.cidade;

import br.com.libertsolutions.libertvendas.app.domain.dto.CidadeDto;
import java.util.List;
import retrofit2.http.GET;
import rx.Observable;

/**
 * @author Filipe Bezerra
 */
public interface CidadeService {

    @GET("api/cidade/get") Observable<List<CidadeDto>> get();
}

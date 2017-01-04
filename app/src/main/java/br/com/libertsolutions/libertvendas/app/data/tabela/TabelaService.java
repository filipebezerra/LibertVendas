package br.com.libertsolutions.libertvendas.app.data.tabela;

import br.com.libertsolutions.libertvendas.app.domain.dto.TabelaDto;
import java.util.List;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @author Filipe Bezerra
 */
public interface TabelaService {

    @GET("api/tabela/get") Observable<List<TabelaDto>> get(@Query("cnpj") String cnpj);
}


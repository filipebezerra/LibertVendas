package br.com.libertsolutions.libertvendas.app.data.tabelaspreco;

import br.com.libertsolutions.libertvendas.app.domain.dto.TabelaPrecoDto;
import java.util.List;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @author Filipe Bezerra
 */
public interface TabelaPrecoService {
    @GET("api/tabela/get") Observable<List<TabelaPrecoDto>> get(@Query("cnpj") String cnpj);
}

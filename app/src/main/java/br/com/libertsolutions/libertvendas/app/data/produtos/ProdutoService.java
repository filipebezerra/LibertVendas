package br.com.libertsolutions.libertvendas.app.data.produtos;

import br.com.libertsolutions.libertvendas.app.domain.dto.ProdutoDto;
import java.util.List;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @author Filipe Bezerra
 */
public interface ProdutoService {
    @GET("api/produto/get") Observable<List<ProdutoDto>> get(@Query("cnpj") String cnpj);
}

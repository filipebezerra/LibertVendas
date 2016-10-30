package br.com.libertsolutions.libertvendas.app.data.formaspagamento;

import br.com.libertsolutions.libertvendas.app.domain.dto.FormaPagamentoDto;
import java.util.List;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @author Filipe Bezerra
 */
public interface FormaPagamentoService {
    @GET("api/formggto/get") Observable<List<FormaPagamentoDto>> get(@Query("cnpj") String cnpj);
}

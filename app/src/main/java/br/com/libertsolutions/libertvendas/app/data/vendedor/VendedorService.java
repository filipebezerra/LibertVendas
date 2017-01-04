package br.com.libertsolutions.libertvendas.app.data.vendedor;

import br.com.libertsolutions.libertvendas.app.domain.dto.VendedorResponseDto;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @author Filipe Bezerra
 */
public interface VendedorService {

    @GET("api/vendedor/get") Observable<VendedorResponseDto> get(
            @Query("cpfCnpj") String cpfCnpj, @Query("senha") String senha);
}

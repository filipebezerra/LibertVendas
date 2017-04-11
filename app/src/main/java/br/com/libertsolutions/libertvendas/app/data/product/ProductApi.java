package br.com.libertsolutions.libertvendas.app.data.product;

import br.com.libertsolutions.libertvendas.app.domain.pojo.Product;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author Filipe Bezerra
 */
public interface ProductApi {

    @GET("api/produto/get") Call<List<Product>> getUpdates(
            @Query("cnpj") String cnpj, @Query("ultimaAtualizacao") String lastUpdateTime);
}

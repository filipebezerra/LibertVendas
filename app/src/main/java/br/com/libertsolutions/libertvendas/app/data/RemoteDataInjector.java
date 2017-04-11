package br.com.libertsolutions.libertvendas.app.data;

import br.com.libertsolutions.libertvendas.app.data.city.CityApi;
import br.com.libertsolutions.libertvendas.app.data.customer.CustomerApi;
import br.com.libertsolutions.libertvendas.app.data.order.OrderApi;
import br.com.libertsolutions.libertvendas.app.data.paymentmethod.PaymentMethodApi;
import br.com.libertsolutions.libertvendas.app.data.postalcode.PostalCodeApi;
import br.com.libertsolutions.libertvendas.app.data.pricetable.PriceTableApi;
import br.com.libertsolutions.libertvendas.app.data.product.ProductApi;
import br.com.libertsolutions.libertvendas.app.data.salesman.SalesmanApi;
import br.com.libertsolutions.libertvendas.app.data.sync.SyncApi;

import static br.com.libertsolutions.libertvendas.app.presentation.PresentationInjector.provideRetrofit;

/**
 * @author Filipe Bezerra
 */
public class RemoteDataInjector {

    private RemoteDataInjector() {/* No instances */}

    public static SalesmanApi provideSalesmanApi() {
        return provideRetrofit().create(SalesmanApi.class);
    }

    public static CustomerApi provideCustomerApi() {
        return provideRetrofit().create(CustomerApi.class);
    }

    public static PaymentMethodApi providePaymentMethodApi() {
        return provideRetrofit().create(PaymentMethodApi.class);
    }

    public static PriceTableApi providePriceTableApi() {
        return provideRetrofit().create(PriceTableApi.class);
    }

    public static CityApi provideCityApi() {
        return provideRetrofit().create(CityApi.class);
    }

    public static PostalCodeApi providePostalCodeApi() {
        return provideRetrofit().create(PostalCodeApi.class);
    }

    public static OrderApi provideOrderApi() {
        return provideRetrofit().create(OrderApi.class);
    }

    public static SyncApi provideSyncApi() {
        return provideRetrofit().create(SyncApi.class);
    }

    public static ProductApi provideProductApi() {
        return provideRetrofit().create(ProductApi.class);
    }
}

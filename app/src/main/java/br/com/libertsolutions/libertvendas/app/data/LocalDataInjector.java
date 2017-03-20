package br.com.libertsolutions.libertvendas.app.data;

import br.com.libertsolutions.libertvendas.app.data.city.CityRealmMapper;
import br.com.libertsolutions.libertvendas.app.data.city.CityRealmRepository;
import br.com.libertsolutions.libertvendas.app.data.city.CityRepository;
import br.com.libertsolutions.libertvendas.app.data.city.StateRealmMapper;
import br.com.libertsolutions.libertvendas.app.data.city.StateRealmRepository;
import br.com.libertsolutions.libertvendas.app.data.city.StateRepository;
import br.com.libertsolutions.libertvendas.app.data.company.CompanyRealmMapper;
import br.com.libertsolutions.libertvendas.app.data.company.customer.CompanyCustomerRealmMapper;
import br.com.libertsolutions.libertvendas.app.data.company.customer.CompanyCustomerRealmRepository;
import br.com.libertsolutions.libertvendas.app.data.company.customer.CompanyCustomerRepository;
import br.com.libertsolutions.libertvendas.app.data.company.paymentmethod.CompanyPaymentMethodRealmMapper;
import br.com.libertsolutions.libertvendas.app.data.company.paymentmethod.CompanyPaymentMethodRealmRepository;
import br.com.libertsolutions.libertvendas.app.data.company.paymentmethod.CompanyPaymentMethodRepository;
import br.com.libertsolutions.libertvendas.app.data.company.pricetable.CompanyPriceTableRealmMapper;
import br.com.libertsolutions.libertvendas.app.data.company.pricetable.CompanyPriceTableRealmRepository;
import br.com.libertsolutions.libertvendas.app.data.company.pricetable.CompanyPriceTableRepository;
import br.com.libertsolutions.libertvendas.app.data.customer.CustomerRealmMapper;
import br.com.libertsolutions.libertvendas.app.data.customer.CustomerRealmRepository;
import br.com.libertsolutions.libertvendas.app.data.customer.CustomerRepository;
import br.com.libertsolutions.libertvendas.app.data.order.OrderItemRealmMapper;
import br.com.libertsolutions.libertvendas.app.data.order.OrderRealmMapper;
import br.com.libertsolutions.libertvendas.app.data.order.OrderRealmRepository;
import br.com.libertsolutions.libertvendas.app.data.order.OrderRepository;
import br.com.libertsolutions.libertvendas.app.data.paymentmethod.PaymentMethodRealmMapper;
import br.com.libertsolutions.libertvendas.app.data.paymentmethod.PaymentMethodRealmRepository;
import br.com.libertsolutions.libertvendas.app.data.paymentmethod.PaymentMethodRepository;
import br.com.libertsolutions.libertvendas.app.data.pricetable.PriceTableItemRealmMapper;
import br.com.libertsolutions.libertvendas.app.data.pricetable.PriceTableRealmMapper;
import br.com.libertsolutions.libertvendas.app.data.pricetable.PriceTableRealmRepository;
import br.com.libertsolutions.libertvendas.app.data.pricetable.PriceTableRepository;
import br.com.libertsolutions.libertvendas.app.data.pricetable.ProductRealmMapper;
import br.com.libertsolutions.libertvendas.app.data.repository.Mapper;
import br.com.libertsolutions.libertvendas.app.domain.entity.CityEntity;
import br.com.libertsolutions.libertvendas.app.domain.entity.CompanyCustomerEntity;
import br.com.libertsolutions.libertvendas.app.domain.entity.CompanyEntity;
import br.com.libertsolutions.libertvendas.app.domain.entity.CompanyPaymentMethodEntity;
import br.com.libertsolutions.libertvendas.app.domain.entity.CompanyPriceTableEntity;
import br.com.libertsolutions.libertvendas.app.domain.entity.CustomerEntity;
import br.com.libertsolutions.libertvendas.app.domain.entity.OrderEntity;
import br.com.libertsolutions.libertvendas.app.domain.entity.OrderItemEntity;
import br.com.libertsolutions.libertvendas.app.domain.entity.PaymentMethodEntity;
import br.com.libertsolutions.libertvendas.app.domain.entity.PriceTableEntity;
import br.com.libertsolutions.libertvendas.app.domain.entity.PriceTableItemEntity;
import br.com.libertsolutions.libertvendas.app.domain.entity.ProductEntity;
import br.com.libertsolutions.libertvendas.app.domain.entity.StateEntity;
import br.com.libertsolutions.libertvendas.app.domain.pojo.City;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Company;
import br.com.libertsolutions.libertvendas.app.domain.pojo.CompanyCustomer;
import br.com.libertsolutions.libertvendas.app.domain.pojo.CompanyPaymentMethod;
import br.com.libertsolutions.libertvendas.app.domain.pojo.CompanyPriceTable;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Customer;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Order;
import br.com.libertsolutions.libertvendas.app.domain.pojo.OrderItem;
import br.com.libertsolutions.libertvendas.app.domain.pojo.PaymentMethod;
import br.com.libertsolutions.libertvendas.app.domain.pojo.PriceTable;
import br.com.libertsolutions.libertvendas.app.domain.pojo.PriceTableItem;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Product;
import br.com.libertsolutions.libertvendas.app.domain.pojo.State;

/**
 * @author Filipe Bezerra
 */
public class LocalDataInjector {

    private LocalDataInjector() {/* No instances */}

    private static Mapper<City, CityEntity> sCityMapper = null;
    public static Mapper<City, CityEntity> cityMapper() {
        if (sCityMapper == null) {
            sCityMapper = new CityRealmMapper();
        }
        return sCityMapper;
    }

    private static Mapper<State, StateEntity> sStateMapper = null;
    public static Mapper<State, StateEntity> stateMapper() {
        if (sStateMapper == null) {
            sStateMapper = new StateRealmMapper();
        }
        return sStateMapper;
    }

    private static Mapper<PaymentMethod, PaymentMethodEntity> sPaymentMethodMapper = null;
    public static Mapper<PaymentMethod, PaymentMethodEntity> paymentMethodMapper() {
        if (sPaymentMethodMapper == null) {
            sPaymentMethodMapper = new PaymentMethodRealmMapper();
        }
        return sPaymentMethodMapper;
    }

    private static Mapper<Company, CompanyEntity> sCompanyMapper = null;
    public static Mapper<Company, CompanyEntity> companyMapper() {
        if (sCompanyMapper == null) {
            sCompanyMapper = new CompanyRealmMapper();
        }
        return sCompanyMapper;
    }

    private static Mapper<Customer, CustomerEntity> sCustomerMapper = null;
    public static Mapper<Customer, CustomerEntity> customerMapper() {
        if (sCustomerMapper == null) {
            sCustomerMapper = new CustomerRealmMapper();
        }
        return sCustomerMapper;
    }

    private static Mapper<PriceTable, PriceTableEntity> sPriceTableMapper = null;
    public static Mapper<PriceTable, PriceTableEntity> priceTableMapper() {
        if (sPriceTableMapper == null) {
            sPriceTableMapper = new PriceTableRealmMapper();
        }
        return sPriceTableMapper;
    }

    private static Mapper<PriceTableItem, PriceTableItemEntity> sPriceTableItemMapper = null;
    public static Mapper<PriceTableItem, PriceTableItemEntity> priceTableItemMapper() {
        if (sPriceTableItemMapper == null) {
            sPriceTableItemMapper = new PriceTableItemRealmMapper();
        }
        return sPriceTableItemMapper;
    }

    private static Mapper<Product, ProductEntity> sProductMapper = null;
    public static Mapper<Product, ProductEntity> productMapper() {
        if (sProductMapper == null) {
            sProductMapper = new ProductRealmMapper();
        }
        return sProductMapper;
    }

    private static Mapper<CompanyCustomer, CompanyCustomerEntity> sCompanyCustomerMapper = null;
    public static Mapper<CompanyCustomer, CompanyCustomerEntity> companyCustomerMapper() {
        if (sCompanyCustomerMapper == null) {
            sCompanyCustomerMapper = new CompanyCustomerRealmMapper();
        }
        return sCompanyCustomerMapper;
    }

    private static Mapper<CompanyPaymentMethod, CompanyPaymentMethodEntity> sCompanyPaymentMethodMapper = null;
    public static Mapper<CompanyPaymentMethod, CompanyPaymentMethodEntity> companyPaymentMethodMapper() {
        if (sCompanyPaymentMethodMapper == null) {
            sCompanyPaymentMethodMapper = new CompanyPaymentMethodRealmMapper();
        }
        return sCompanyPaymentMethodMapper;
    }

    private static Mapper<CompanyPriceTable, CompanyPriceTableEntity> sCompanyPriceTableMapper = null;
    public static Mapper<CompanyPriceTable, CompanyPriceTableEntity> companyPriceTableMapper() {
        if (sCompanyPriceTableMapper == null) {
            sCompanyPriceTableMapper = new CompanyPriceTableRealmMapper();
        }
        return sCompanyPriceTableMapper;
    }

    private static Mapper<Order, OrderEntity> sOrderMapper = null;
    public static Mapper<Order, OrderEntity> orderMapper() {
        if (sOrderMapper == null) {
            sOrderMapper = new OrderRealmMapper();
        }
        return sOrderMapper;
    }

    private static Mapper<OrderItem, OrderItemEntity> sOrderItemMapper = null;
    public static Mapper<OrderItem, OrderItemEntity> orderItemMapper() {
        if (sOrderItemMapper == null) {
            sOrderItemMapper = new OrderItemRealmMapper();
        }
        return sOrderItemMapper;
    }

    public static CustomerRepository provideCustomerRepository() {
        return new CustomerRealmRepository();
    }

    public static PaymentMethodRepository providePaymentMethodRepository() {
        return new PaymentMethodRealmRepository();
    }

    public static PriceTableRepository providePriceTableRepository() {
        return new PriceTableRealmRepository();
    }

    public static CityRepository provideCityRepository() {
        return new CityRealmRepository();
    }

    public static CompanyCustomerRepository provideCompanyCustomerRepository() {
        return new CompanyCustomerRealmRepository();
    }

    public static CompanyPaymentMethodRepository provideCompanyPaymentMethodRepository() {
        return new CompanyPaymentMethodRealmRepository();
    }

    public static CompanyPriceTableRepository provideCompanyPriceTableRepository() {
        return new CompanyPriceTableRealmRepository();
    }

    public static StateRepository provideStateRepository() {
        return new StateRealmRepository();
    }

    public static OrderRepository providerOrderRepository() {
        return new OrderRealmRepository();
    }
}

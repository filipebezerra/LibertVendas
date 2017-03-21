package br.com.libertsolutions.libertvendas.app.data.customer;

import br.com.libertsolutions.libertvendas.app.data.realm.RealmMapper;
import br.com.libertsolutions.libertvendas.app.domain.entity.CustomerEntity;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Customer;

import static br.com.libertsolutions.libertvendas.app.data.LocalDataInjector.cityMapper;

/**
 * @author Filipe Bezerra
 */
public class CustomerRealmMapper extends RealmMapper<Customer, CustomerEntity> {

    @Override public CustomerEntity toEntity(final Customer object) {
        return new CustomerEntity()
                .withId(object.getId())
                .withCustomerId(object.getCustomerId())
                .withCode(object.getCode())
                .withName(object.getName())
                .withFantasyName(object.getFantasyName())
                .withType(object.getType())
                .withCpfOrCnpj(object.getCpfOrCnpj())
                .withContact(object.getContact())
                .withEmail(object.getEmail())
                .withMainPhone(object.getMainPhone())
                .withSecondaryPhone(object.getSecondaryPhone())
                .withAddress(object.getAddress())
                .withPostalCode(object.getPostalCode())
                .withDistrict(object.getDistrict())
                .withAddressNumber(object.getAddressNumber())
                .withAddressComplement(object.getAddressComplement())
                .withDefaultPriceTable(object.getDefaultPriceTable())
                .withCity(cityMapper().toEntity(object.getCity()))
                .withActive(object.isActive())
                .withLastChangeTime(object.getLastChangeTime())
                .withStatus(object.getStatus());
    }

    @Override public Customer toViewObject(final CustomerEntity entity) {
        return new Customer()
                .withId(entity.getId())
                .withCustomerId(entity.getCustomerId())
                .withCode(entity.getCode())
                .withName(entity.getName())
                .withFantasyName(entity.getFantasyName())
                .withType(entity.getType())
                .withCpfOrCnpj(entity.getCpfOrCnpj())
                .withContact(entity.getContact())
                .withEmail(entity.getEmail())
                .withMainPhone(entity.getMainPhone())
                .withSecondaryPhone(entity.getSecondaryPhone())
                .withAddress(entity.getAddress())
                .withPostalCode(entity.getPostalCode())
                .withDistrict(entity.getDistrict())
                .withAddressNumber(entity.getAddressNumber())
                .withAddressComplement(entity.getAddressComplement())
                .withDefaultPriceTable(entity.getDefaultPriceTable())
                .withCity(cityMapper().toViewObject(entity.getCity()))
                .withActive(entity.isActive())
                .withLastChangeTime(entity.getLastChangeTime())
                .withStatus(entity.getStatus());
    }
}

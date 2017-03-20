package br.com.libertsolutions.libertvendas.app.data.company;

import br.com.libertsolutions.libertvendas.app.data.realm.RealmMapper;
import br.com.libertsolutions.libertvendas.app.domain.entity.CompanyEntity;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Company;

/**
 * @author Filipe Bezerra
 */
public class CompanyRealmMapper extends RealmMapper<Company, CompanyEntity> {

    @Override public CompanyEntity toEntity(final Company object) {
        return new CompanyEntity()
                .withCompanyId(object.getCompanyId())
                .withName(object.getName())
                .withCnpj(object.getCnpj())
                .withPriceTableId(object.getPriceTableId());
    }

    @Override public Company toViewObject(final CompanyEntity entity) {
        return new Company()
                .withCompanyId(entity.getCompanyId())
                .withName(entity.getName())
                .withCnpj(entity.getCnpj())
                .withPriceTableId(entity.getPriceTableId());
    }
}

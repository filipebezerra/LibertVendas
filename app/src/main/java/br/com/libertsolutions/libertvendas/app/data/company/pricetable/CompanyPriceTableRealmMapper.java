package br.com.libertsolutions.libertvendas.app.data.company.pricetable;

import br.com.libertsolutions.libertvendas.app.data.realm.RealmMapper;
import br.com.libertsolutions.libertvendas.app.domain.entity.CompanyPriceTableEntity;
import br.com.libertsolutions.libertvendas.app.domain.pojo.CompanyPriceTable;

/**
 * @author Filipe Bezerra
 */
public class CompanyPriceTableRealmMapper
        extends RealmMapper<CompanyPriceTable, CompanyPriceTableEntity> {

    @Override public CompanyPriceTableEntity toEntity(final CompanyPriceTable object) {
        return new CompanyPriceTableEntity()
                .withId(object.getCompanyId(), object.getPriceTableId());
    }

    @Override public CompanyPriceTable toViewObject(final CompanyPriceTableEntity entity) {
        return CompanyPriceTable
                .of(entity.getId(), entity.getCompanyId(), entity.getPriceTableId());
    }
}

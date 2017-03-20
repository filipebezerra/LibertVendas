package br.com.libertsolutions.libertvendas.app.data.company.pricetable;

import br.com.libertsolutions.libertvendas.app.data.realm.RealmRepository;
import br.com.libertsolutions.libertvendas.app.domain.entity.CompanyPriceTableEntity;
import br.com.libertsolutions.libertvendas.app.domain.pojo.CompanyPriceTable;

import static br.com.libertsolutions.libertvendas.app.data.LocalDataInjector.companyPriceTableMapper;

/**
 * @author Filipe Bezerra
 */
public class CompanyPriceTableRealmRepository
        extends RealmRepository<CompanyPriceTable, CompanyPriceTableEntity>
        implements CompanyPriceTableRepository {

    public CompanyPriceTableRealmRepository() {
        super(CompanyPriceTableEntity.class, companyPriceTableMapper());
    }
}

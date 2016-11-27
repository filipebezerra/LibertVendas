package br.com.libertsolutions.libertvendas.app.data.vendedor;

import br.com.libertsolutions.libertvendas.app.data.repository.AbstractRealmRepository;
import br.com.libertsolutions.libertvendas.app.data.repository.Mapper;
import br.com.libertsolutions.libertvendas.app.domain.entity.VendedorEntity;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Vendedor;

/**
 * @author Filipe Bezerra
 */
public class VendedorRepository extends AbstractRealmRepository<Vendedor, VendedorEntity> {
    public VendedorRepository(Mapper<Vendedor, VendedorEntity> mapper) {
        super(VendedorEntity.class, mapper);
    }

    @Override public String idFieldName() {
        return VendedorEntity.ID_FIELD_NAME;
    }
}

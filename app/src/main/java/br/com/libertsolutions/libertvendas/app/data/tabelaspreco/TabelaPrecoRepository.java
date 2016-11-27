package br.com.libertsolutions.libertvendas.app.data.tabelaspreco;

import br.com.libertsolutions.libertvendas.app.data.repository.AbstractRealmRepository;
import br.com.libertsolutions.libertvendas.app.data.repository.Mapper;
import br.com.libertsolutions.libertvendas.app.domain.entity.TabelaPrecoEntity;
import br.com.libertsolutions.libertvendas.app.domain.pojo.TabelaPreco;

/**
 * @author Filipe Bezerra
 */
public class TabelaPrecoRepository extends AbstractRealmRepository<TabelaPreco, TabelaPrecoEntity> {
    public TabelaPrecoRepository(Mapper<TabelaPreco, TabelaPrecoEntity> mapper) {
        super(TabelaPrecoEntity.class, mapper);
    }

    @Override
    public String idFieldName() {
        return TabelaPrecoEntity.ID_FIELD_NAME;
    }
}

package br.com.libertsolutions.libertvendas.app.data.tabelaspreco;

import android.content.Context;
import br.com.libertsolutions.libertvendas.app.data.repository.AbstractRealmRepository;
import br.com.libertsolutions.libertvendas.app.data.repository.Mapper;
import br.com.libertsolutions.libertvendas.app.domain.entity.TabelaPrecoEntity;
import br.com.libertsolutions.libertvendas.app.domain.pojo.TabelaPreco;

/**
 * @author Filipe Bezerra
 */
public class TabelaPrecoRepository extends AbstractRealmRepository<TabelaPreco, TabelaPrecoEntity> {
    public TabelaPrecoRepository(Context context, Mapper<TabelaPreco, TabelaPrecoEntity> mapper) {
        super(context, TabelaPrecoEntity.class, mapper);
    }
}

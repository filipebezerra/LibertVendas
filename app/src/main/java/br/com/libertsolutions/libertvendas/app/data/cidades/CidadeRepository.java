package br.com.libertsolutions.libertvendas.app.data.cidades;

import android.content.Context;
import br.com.libertsolutions.libertvendas.app.data.repository.AbstractRealmRepository;
import br.com.libertsolutions.libertvendas.app.data.repository.Mapper;
import br.com.libertsolutions.libertvendas.app.domain.entity.CidadeEntity;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Cidade;

/**
 * @author Filipe Bezerra
 */
public class CidadeRepository extends AbstractRealmRepository<Cidade, CidadeEntity> {
    public CidadeRepository(Context context, Mapper<Cidade, CidadeEntity> mapper) {
        super(context, CidadeEntity.class, mapper);
    }
}

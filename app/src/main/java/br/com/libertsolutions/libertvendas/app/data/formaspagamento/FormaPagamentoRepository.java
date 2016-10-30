package br.com.libertsolutions.libertvendas.app.data.formaspagamento;

import android.content.Context;
import br.com.libertsolutions.libertvendas.app.data.repository.AbstractRealmRepository;
import br.com.libertsolutions.libertvendas.app.data.repository.Mapper;
import br.com.libertsolutions.libertvendas.app.domain.entity.FormaPagamentoEntity;
import br.com.libertsolutions.libertvendas.app.domain.pojo.FormaPagamento;

/**
 * @author Filipe Bezerra
 */
public class FormaPagamentoRepository
        extends AbstractRealmRepository<FormaPagamento, FormaPagamentoEntity> {
    public FormaPagamentoRepository(
            Context context, Mapper<FormaPagamento, FormaPagamentoEntity> mapper) {
        super(context, FormaPagamentoEntity.class, mapper);
    }
}

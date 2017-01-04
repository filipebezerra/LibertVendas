package br.com.libertsolutions.libertvendas.app.data.cidade;

import br.com.libertsolutions.libertvendas.app.data.repository.Repository;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Cidade;
import java.util.List;
import rx.Observable;

/**
 * @author Filipe Bezerra
 */
public interface CidadeRepository extends Repository<Cidade, Integer> {

    Observable<List<Cidade>> findByIdEstado(Integer idEstado);
}

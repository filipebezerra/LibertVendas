package br.com.libertsolutions.libertvendas.app.presentation.pedido.finalizando;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;
import br.com.libertsolutions.libertvendas.app.domain.pojo.FormaPagamento;
import java.util.List;

/**
 * @author Filipe Bezerra
 */
public class FormaPagamentoAdapter extends ArrayAdapter<FormaPagamento> {
    public FormaPagamentoAdapter(
            @NonNull Context context, @NonNull List<FormaPagamento> pFormaPagamentoList) {
        super(context, android.R.layout.simple_spinner_item, pFormaPagamentoList);
        setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }
}
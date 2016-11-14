package br.com.libertsolutions.libertvendas.app.presentation.pedido.finalizapedido;

import android.content.Context;
import android.widget.EditText;
import br.com.libertsolutions.libertvendas.app.domain.pojo.FormaPagamento;
import java.util.List;
import smtchahal.materialspinner.MaterialSpinner;

/**
 * @author Filipe Bezerra
 */
class FinalizaPedidoViewModel {
    private final Context mContext;

    private final EditText mEditTextDataEmissao;

    private FormaPagamentoAdapter mFormaPagamentoAdapter;
    private final MaterialSpinner mSpinnerFormaPagamento;

    private final EditText mEditTextTotalProdutos;

    private final EditText mEditTextCliente;

    FinalizaPedidoViewModel(Context pContext, EditText pEditTextDataEmissao,
            MaterialSpinner pSpinnerFormaPagamento, EditText pEditTextTotalProdutos,
            EditText pEditTextCliente) {
        mContext = pContext;
        mEditTextDataEmissao = pEditTextDataEmissao;
        mSpinnerFormaPagamento = pSpinnerFormaPagamento;
        mEditTextTotalProdutos = pEditTextTotalProdutos;
        mEditTextCliente = pEditTextCliente;
    }

    FinalizaPedidoViewModel dataEmissao(String pDataEmissao) {
        mEditTextDataEmissao.setText(pDataEmissao);
        return this;
    }

    FinalizaPedidoViewModel formasPagamento(List<FormaPagamento> pFormaPagamentoList) {
        mFormaPagamentoAdapter = new FormaPagamentoAdapter(mContext, pFormaPagamentoList);
        mSpinnerFormaPagamento.setAdapter(mFormaPagamentoAdapter);
        return this;
    }

    FinalizaPedidoViewModel totalProdutos(String pTotalProdutos) {
        mEditTextTotalProdutos.setText(pTotalProdutos);
        return this;
    }

    FinalizaPedidoViewModel cliente(String nomeCliente) {
        mEditTextCliente.setText(nomeCliente);
        return this;
    }
}

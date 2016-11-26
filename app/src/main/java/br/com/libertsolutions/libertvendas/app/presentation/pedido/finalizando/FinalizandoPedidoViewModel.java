package br.com.libertsolutions.libertvendas.app.presentation.pedido.finalizando;

import android.content.Context;
import android.text.TextUtils;
import android.widget.EditText;
import br.com.libertsolutions.libertvendas.app.domain.pojo.FormaPagamento;
import java.util.List;
import smtchahal.materialspinner.MaterialSpinner;

/**
 * @author Filipe Bezerra
 */
class FinalizandoPedidoViewModel {
    private final Context mContext;

    private final EditText mEditTextDataEmissao;

    private final EditText mEditTextCliente;

    private final EditText mEditTextTotalProdutos;

    private final EditText mEditTextValorDesconto;

    private FormaPagamentoAdapter mFormaPagamentoAdapter;
    private final MaterialSpinner mSpinnerFormaPagamento;

    private final EditText mEditTextObservacao;

    FinalizandoPedidoViewModel(Context pContext, EditText pEditTextDataEmissao,
            EditText pEditTextCliente, EditText pEditTextTotalProdutos,
            EditText pEditTextValorDesconto, MaterialSpinner pSpinnerFormaPagamento,
            EditText pEditTextObservacao) {
        mContext = pContext;
        mEditTextDataEmissao = pEditTextDataEmissao;
        mEditTextValorDesconto = pEditTextValorDesconto;
        mSpinnerFormaPagamento = pSpinnerFormaPagamento;
        mEditTextTotalProdutos = pEditTextTotalProdutos;
        mEditTextCliente = pEditTextCliente;
        mEditTextObservacao = pEditTextObservacao;
    }

    void dataEmissao(String pDataEmissao) {
        mEditTextDataEmissao.setText(pDataEmissao);
    }

    boolean hasDataEmissao() {
        return !TextUtils.isEmpty(mEditTextDataEmissao.getText());
    }

    boolean hasDesconto() {
        return !TextUtils.isEmpty(mEditTextValorDesconto.getText()) &&
                !mEditTextValorDesconto.getText().toString().equals("0");
    }

    String desconto() {
        return mEditTextValorDesconto.getText().toString();
    }

    void formasPagamento(List<FormaPagamento> pFormaPagamentoList) {
        mFormaPagamentoAdapter = new FormaPagamentoAdapter(mContext, pFormaPagamentoList);
        mSpinnerFormaPagamento.setAdapter(mFormaPagamentoAdapter);
    }

    boolean hasFormaPagamento() {
        return mSpinnerFormaPagamento.getSelectedItem() != null;
    }

    FormaPagamento formaPagamento() {
        return mFormaPagamentoAdapter.getItem(mSpinnerFormaPagamento.getSelectedItemPosition());
    }

    void totalProdutos(String pTotalProdutos) {
        mEditTextTotalProdutos.setText(pTotalProdutos);
    }

    String totalProdutos() {
        return mEditTextTotalProdutos.getText().toString();
    }

    void cliente(String nomeCliente) {
        mEditTextCliente.setText(nomeCliente);
    }

    boolean hasCliente() {
        return !TextUtils.isEmpty(mEditTextCliente.getText());
    }

    String observacao() {
        return mEditTextObservacao.getText().toString();
    }

    boolean hasRequiredValues() {
        return hasDataEmissao()
                && hasFormaPagamento()
                && hasCliente();
    }
}

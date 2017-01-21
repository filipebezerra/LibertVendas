package br.com.libertsolutions.libertvendas.app.presentation.listapedidos;

import android.support.v7.util.DiffUtil;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Pedido;
import java.util.List;

/**
 * @author Filipe Bezerra
 */
class OrderListDiffCallback extends DiffUtil.Callback {

    private final List<Pedido> mOldOrderList;
    private final List<Pedido> mNewOrderList;

    OrderListDiffCallback(final List<Pedido> oldOrderList, final List<Pedido> newOrderList) {
        mOldOrderList = oldOrderList;
        mNewOrderList = newOrderList;
    }

    @Override public int getOldListSize() {
        return mOldOrderList.size();
    }

    @Override public int getNewListSize() {
        return mNewOrderList.size();
    }

    @Override public boolean areItemsTheSame(final int oldItemPosition, final int newItemPosition) {
        return mOldOrderList.get(oldItemPosition).equals(mNewOrderList.get(newItemPosition));
    }

    @Override public boolean areContentsTheSame(
            final int oldItemPosition, final int newItemPosition) {
        final Pedido oldOrder = mOldOrderList.get(oldItemPosition);
        final Pedido newOrder = mNewOrderList.get(newItemPosition);

        if (oldOrder.getDataEmissao() == newOrder.getDataEmissao() &&
                oldOrder.getDesconto() == newOrder.getDesconto() &&
                oldOrder.getObservacao().equals(newOrder.getObservacao()) &&
                oldOrder.getCliente().equals(newOrder.getCliente()) &&
                oldOrder.getFormaPagamento().equals(newOrder.getFormaPagamento()) &&
                oldOrder.getItens().size() == newOrder.getItens().size() &&
                oldOrder.getItens().equals(newOrder.getItens()) &&
                oldOrder.getUltimaAlteracao().equals(newOrder.getUltimaAlteracao()) &&
                oldOrder.getCpfCnpjVendedor().equals(newOrder.getCpfCnpjVendedor()) &&
                oldOrder.getCnpjEmpresa().equals(newOrder.getCnpjEmpresa())) {

            for (int i = 0; i < oldOrder.getItens().size(); i++) {
                if (oldOrder.getItens().get(i).getQuantidade()
                        != newOrder.getItens().get(i).getQuantidade()) {
                    return false;
                }
            }

            return true;
        }
        return false;
    }
}

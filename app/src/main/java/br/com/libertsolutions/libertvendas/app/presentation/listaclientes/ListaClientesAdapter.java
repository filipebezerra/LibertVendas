package br.com.libertsolutions.libertvendas.app.presentation.listaclientes;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import br.com.libertsolutions.libertvendas.app.R;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Cliente;
import br.com.libertsolutions.libertvendas.app.presentation.util.FormattingUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Filipe Bezerra
 */
class ListaClientesAdapter extends RecyclerView.Adapter<ListaClientesViewHolder>
        implements Filterable {

    private final Context mContext;

    private List<Cliente> mClienteList;

    private List<Cliente> mClienteOriginalList;

    private ClienteAdapterFilter mFilter;

    ListaClientesAdapter(@NonNull Context pContext, @NonNull List<Cliente> pClienteList) {
        mContext = pContext;
        mClienteList = pClienteList;
    }

    @Override public ListaClientesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(mContext)
                .inflate(R.layout.list_item_cliente, parent, false);
        return new ListaClientesViewHolder(itemView);
    }

    @Override public void onBindViewHolder(ListaClientesViewHolder holder, int position) {
        final Cliente cliente = mClienteList.get(position);
        holder.textViewRazaoSocial.setText(cliente.getNome());
        holder.textViewCpfOuCnpj.setText(FormattingUtils.formatCPForCPNJ(cliente.getCpfCnpj()));

        if (!TextUtils.isEmpty(cliente.getTelefone())
                && !TextUtils.isEmpty(cliente.getTelefone2())) {
            holder.textViewTelefone.setText(
                    mContext.getString(R.string.template_text_telefones,
                            FormattingUtils.formatPhoneNumber(cliente.getTelefone()),
                            FormattingUtils.formatPhoneNumber(cliente.getTelefone2())));
        } else if (!TextUtils.isEmpty(cliente.getTelefone())) {
            holder.textViewTelefone.setText(
                    FormattingUtils.formatPhoneNumber(cliente.getTelefone()));
        } else if (!TextUtils.isEmpty(cliente.getTelefone2())) {
            holder.textViewTelefone.setText(
                    FormattingUtils.formatPhoneNumber(cliente.getTelefone2()));
        } else {
            holder.textViewTelefone.setText(mContext.getString(R.string.text_sem_telefone));
        }

        if (!TextUtils.isEmpty(cliente.getEmail())) {
            holder.textViewEmail.setText(cliente.getEmail());
        } else {
            holder.textViewEmail.setText(mContext.getString(R.string.text_sem_email));
        }
    }

    @Override
    public int getItemCount() {
        return mClienteList.size();
    }

    @Override
    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new ClienteAdapterFilter();
        }
        return mFilter;
    }

    private class ClienteAdapterFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            FilterResults results = new FilterResults();

            if (mClienteOriginalList == null) {
                mClienteOriginalList = new ArrayList<>(mClienteList);
            }

            if (TextUtils.isEmpty(prefix)) {
                List<Cliente> list = new ArrayList<>(mClienteOriginalList);
                results.values = list;
                results.count = list.size();
            } else {
                final String prefixString = prefix.toString().toLowerCase();

                List<Cliente> values = new ArrayList<>(mClienteOriginalList);
                final List<Cliente> newValues = new ArrayList<>();

                for(Cliente cliente : values) {
                    String valueText = cliente.getNome();
                    if (!TextUtils.isEmpty(valueText) &&
                            valueText.toLowerCase().contains(prefixString)) {
                        newValues.add(cliente);
                    }
                }

                results.values = newValues;
                results.count = newValues.size();
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence prefix, FilterResults results) {
            mClienteList.clear();
            //noinspection unchecked
            mClienteList.addAll((List<Cliente>) results.values);
            notifyDataSetChanged();
        }
    }
}

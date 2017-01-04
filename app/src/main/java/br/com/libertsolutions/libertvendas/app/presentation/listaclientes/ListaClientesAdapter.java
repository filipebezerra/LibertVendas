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
import br.com.libertsolutions.libertvendas.app.presentation.utils.FormattingUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Filipe Bezerra
 */
class ListaClientesAdapter extends RecyclerView.Adapter<ListaClientesViewHolder>
        implements Filterable {

    private final Context mContext;

    private List<Cliente> mClientes;

    private List<Cliente> mClientesOriginalCopy;

    private ListaClientesFilter mFilter;

    ListaClientesAdapter(@NonNull Context context, @NonNull List<Cliente> clientes) {
        mContext = context;
        mClientes = clientes;
    }

    @Override public ListaClientesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView
                = LayoutInflater.from(mContext).inflate(R.layout.list_item_cliente, parent, false);
        return new ListaClientesViewHolder(itemView);
    }

    @Override public void onBindViewHolder(ListaClientesViewHolder holder, int position) {
        final Cliente cliente = mClientes.get(position);
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

    @Override public int getItemCount() {
        return mClientes.size();
    }

    @Override public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new ListaClientesFilter();
        }
        return mFilter;
    }

    private class ListaClientesFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            FilterResults results = new FilterResults();

            if (mClientesOriginalCopy == null) {
                mClientesOriginalCopy = new ArrayList<>(mClientes);
            }

            if (TextUtils.isEmpty(prefix)) {
                List<Cliente> list = new ArrayList<>(mClientesOriginalCopy);
                results.values = list;
                results.count = list.size();
            } else {
                final String prefixString = prefix.toString().toLowerCase();

                List<Cliente> values = new ArrayList<>(mClientesOriginalCopy);
                final List<Cliente> newValues = new ArrayList<>();

                for(Cliente cliente : values) {
                    boolean contains = containsAnyProperty(prefixString,
                            cliente.getNome(),
                            cliente.getContato(),
                            cliente.getCpfCnpj());

                    if (contains) {
                        newValues.add(cliente);
                    }
                }

                results.values = newValues;
                results.count = newValues.size();
            }

            return results;
        }

        private boolean containsAnyProperty(String prefix, String...properties) {
            for (String property : properties) {
                if (!TextUtils.isEmpty(property)
                        && property.trim().toLowerCase().contains(prefix)) {
                    return true;
                }
            }
            return false;
        }

        @Override
        protected void publishResults(CharSequence prefix, FilterResults results) {
            mClientes.clear();
            //noinspection unchecked
            mClientes.addAll((List<Cliente>) results.values);
            notifyDataSetChanged();
        }
    }
}

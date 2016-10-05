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

import java.util.ArrayList;
import java.util.List;

import br.com.libertsolutions.libertvendas.app.R;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Cliente;

/**
 * @author Filipe Bezerra
 */

class ClienteAdapter extends RecyclerView.Adapter<ClienteViewHolder> implements Filterable {
    private final Context mContext;

    private List<Cliente> mClienteList;

    private List<Cliente> mOriginalValues;

    private ClienteAdapterFilter mFilter;

    ClienteAdapter(@NonNull Context pContext, @NonNull List<Cliente> pClienteList) {
        mContext = pContext;
        mClienteList = pClienteList;
    }

    @Override
    public ClienteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(mContext)
                .inflate(R.layout.list_item_cliente, parent, false);
        return new ClienteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ClienteViewHolder holder, int position) {
        final Cliente cliente = mClienteList.get(position);
        holder.textViewRazaoSocial.setText(cliente.getRazaoSocial());
        holder.textViewTelefone.setText(cliente.getTelefone());
        holder.textViewEmail.setText(cliente.getEmail());
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

            if (mOriginalValues == null) {
                mOriginalValues = new ArrayList<>(mClienteList);
            }

            if (TextUtils.isEmpty(prefix)) {
                List<Cliente> list = new ArrayList<>(mOriginalValues);
                results.values = list;
                results.count = list.size();
            } else {
                final String prefixString = prefix.toString().toLowerCase();

                List<Cliente> values = new ArrayList<>(mOriginalValues);
                final List<Cliente> newValues = new ArrayList<>();

                for(Cliente cliente : values) {
                    String valueText = cliente.getRazaoSocial();
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
            //noinspection unchecked
            mClienteList = (List<Cliente>) results.values;
            notifyDataSetChanged();
        }
    }
}

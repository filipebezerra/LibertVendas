package br.com.libertsolutions.libertvendas.app.presentation.cliente;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Cidade;
import butterknife.BindView;
import butterknife.ButterKnife;
import java.util.List;

/**
 * @author Filipe Bezerra
 */
class CidadeAdapter extends ArrayAdapter<Cidade> {
    private LayoutInflater mInflater;

    CidadeAdapter(@NonNull Context context, @NonNull List<Cidade> pCidadeList) {
        super(context, android.R.layout.simple_spinner_item, pCidadeList);
        setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mInflater = LayoutInflater.from(context);
    }

    @NonNull @Override public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(android.R.layout.simple_spinner_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.textViewNome.setText(getItem(position).getNome());
        return convertView;
    }

    static final class ViewHolder {
        @BindView(android.R.id.text1) TextView textViewNome;

        ViewHolder(View pItemView) {
            ButterKnife.bind(this, pItemView);
        }
    }
}

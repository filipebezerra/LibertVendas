package br.com.libertsolutions.libertvendas.app.presentation.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import java.util.List;

/**
 * @author Filipe Bezerra
 */
public abstract class SingleTextViewAdapter<T> extends ArrayAdapter<T> {

    private static final int RES_SIMPLE_SPINNER_ITEM_LAYOUT
            = android.R.layout.simple_spinner_item;

    private static final int RES_SIMPLE_SPINNER_DROPDOWN_ITEM_LAYOUT
            = android.R.layout.simple_spinner_dropdown_item;

    private LayoutInflater mInflater;

    public SingleTextViewAdapter(@NonNull Context context, @NonNull List<T> list) {
        super(context, RES_SIMPLE_SPINNER_ITEM_LAYOUT, list);
        setDropDownViewResource(RES_SIMPLE_SPINNER_DROPDOWN_ITEM_LAYOUT);
        mInflater = LayoutInflater.from(context);
    }

    @NonNull @Override public View getView(
            int position, View convertView, @NonNull ViewGroup parent) {

        SingleTextViewViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(RES_SIMPLE_SPINNER_ITEM_LAYOUT, parent, false);
            holder = new SingleTextViewViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (SingleTextViewViewHolder) convertView.getTag();
        }

        holder.textViewNome.setText(getText(position));
        return convertView;
    }

    protected abstract String getText(final int position);

    static class SingleTextViewViewHolder {

        @BindView(android.R.id.text1) TextView textViewNome;

        SingleTextViewViewHolder(View pItemView) {
            ButterKnife.bind(this, pItemView);
        }
    }
}

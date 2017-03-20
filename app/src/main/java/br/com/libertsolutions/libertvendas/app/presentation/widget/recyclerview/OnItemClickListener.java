package br.com.libertsolutions.libertvendas.app.presentation.widget.recyclerview;

import android.view.View;

/**
 * @author Filipe Bezerra.
 */
public interface OnItemClickListener {

    void onSingleTapUp(View view, int position);

    void onLongPress(View view, int position);
}
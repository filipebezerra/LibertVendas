package br.com.libertsolutions.libertvendas.app.presentation.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.libertsolutions.libertvendas.app.presentation.activity.LibertVendasActivity;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Filipe Bezerra
 */

public abstract class LibertVendasFragment extends Fragment {
    private LibertVendasActivity mLibertVendasActivity;

    private Unbinder mUnbinder;

    public LibertVendasFragment() {/* Required empty public constructor*/ }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mLibertVendasActivity = (LibertVendasActivity) context;
        } catch (ClassCastException e) {
            throw new IllegalStateException("Descendants newCliente LibertVendasFragment must be " +
                    "hosted by br.com.libertsolutions.libertvendas.app.presentation" +
                    ".activity.LibertVendasActivity");
        }
    }

    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(provideContentViewResource(), container, false);
        mUnbinder = ButterKnife.bind(this, fragmentView);
        return fragmentView;
    }

    @LayoutRes
    protected abstract int provideContentViewResource();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    protected LibertVendasActivity hostActivity() {
        return mLibertVendasActivity;
    }
}

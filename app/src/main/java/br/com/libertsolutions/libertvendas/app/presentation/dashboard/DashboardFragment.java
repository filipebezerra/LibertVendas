package br.com.libertsolutions.libertvendas.app.presentation.dashboard;

import br.com.libertsolutions.libertvendas.app.R;
import br.com.libertsolutions.libertvendas.app.presentation.fragment.LibertVendasFragment;

/**
 * @author Filipe Bezerra
 */
public class DashboardFragment extends LibertVendasFragment {

    public static final String TAG = DashboardFragment.class.getName();

    public static DashboardFragment newInstance() {
        return new DashboardFragment();
    }

    @Override protected int provideContentViewResource() {
        return R.layout.fragment_dashboard;
    }
}

package br.com.libertsolutions.libertvendas.app.presentation.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import br.com.libertsolutions.libertvendas.app.R;
import br.com.libertsolutions.libertvendas.app.data.settings.SettingsRepository;
import br.com.libertsolutions.libertvendas.app.presentation.helper.ConnectivityHelper;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import org.greenrobot.eventbus.EventBus;

/**
 * @author Filipe Bezerra
 */
public abstract class BaseFragment extends Fragment {

    private BaseActivity hostActivity;

    private Unbinder mUnbinder;

    @Nullable @BindView(R.id.linear_layout_all_error_state) protected LinearLayout mLinearLayoutErrorState;
    @Nullable @BindView(R.id.linear_layout_all_empty_state) protected LinearLayout mLinearLayoutEmptyState;

    public BaseFragment() {}

    @LayoutRes protected abstract int provideContentViewResource();

    @Override public void onAttach(Context context) {
        super.onAttach(context);
        try {
            hostActivity = (BaseActivity) context;
        } catch (ClassCastException e) {
            throw new IllegalStateException("Descendants of " + getClass().getName() +
                    " must be hosted by " + BaseActivity.class.getName());
        }
    }

    @Nullable @Override public View onCreateView(
            LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle inState) {
        final View fragmentView = inflater.inflate(provideContentViewResource(), container, false);
        mUnbinder = ButterKnife.bind(this, fragmentView);
        return fragmentView;
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    protected Navigator navigate() {
        return hostActivity.navigate();
    }

    protected SettingsRepository settings() {
        return hostActivity.settings();
    }

    protected EventBus eventBus() {
        return hostActivity.eventBus();
    }

    protected ConnectivityHelper connectivity() {
        return hostActivity.connectivity();
    }

    protected void setTitle(String title) {
        hostActivity.setTitle(title);
    }

    protected BaseActivity getHostActivity() {
        return hostActivity;
    }

}

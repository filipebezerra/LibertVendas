package br.com.libertsolutions.libertvendas.app.presentation.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import br.com.libertsolutions.libertvendas.app.R;
import br.com.libertsolutions.libertvendas.app.data.settings.SettingsRepository;
import br.com.libertsolutions.libertvendas.app.presentation.PresentationInjector;
import br.com.libertsolutions.libertvendas.app.presentation.helper.ConnectivityHelper;
import butterknife.BindView;
import butterknife.ButterKnife;
import org.greenrobot.eventbus.EventBus;

/**
 * @author Filipe Bezerra
 */
public abstract class BaseActivity extends AppCompatActivity {

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    private Navigator mNavigator;

    @Nullable @BindView(R.id.coordinator_layout_container) protected CoordinatorLayout mCoordinatorLayoutContainer;
    @Nullable @BindView(R.id.toolbar_all_actionbar) protected Toolbar mToolbar;
    @Nullable @BindView(R.id.progress_bar_all_loading) protected ProgressBar mProgressBar;
    @Nullable @BindView(R.id.linear_layout_all_error_state) protected LinearLayout mLinearLayoutErrorState;
    @Nullable @BindView(R.id.linear_layout_all_empty_state) protected LinearLayout mLinearLayoutEmptyState;

    @LayoutRes protected abstract int provideContentViewResource();

    @Override protected void onCreate(@Nullable final Bundle inState) {
        super.onCreate(inState);
        setContentView(provideContentViewResource());
        ButterKnife.bind(this);
        mNavigator = new Navigator(this);
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (getParentActivityIntent() == null) {
                onBackPressed();
            } else {
                NavUtils.navigateUpFromSameTask(this);
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected void setAsHomeActivity() {
        setupToolbar(R.drawable.main_hamburguer_24dp);
    }

    protected void setAsInitialFlowActivity() {
        setupToolbar(R.drawable.all_clear_24dp);
    }

    protected void setAsSubActivity() {
        setupToolbar(R.drawable.all_arrow_back_24dp);
    }

    @SuppressWarnings("ConstantConditions")
    private void setupToolbar(int drawableRes) {
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            getSupportActionBar().setHomeAsUpIndicator(drawableRes);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    protected Navigator navigate() {
        if (mNavigator == null) {
            mNavigator = new Navigator(this);
        }
        return mNavigator;
    }

    protected SettingsRepository settings() {
        return PresentationInjector.provideSettingsRepository();
    }

    protected EventBus eventBus() {
        return PresentationInjector.provideEventBus();
    }

    protected ConnectivityHelper connectivity() {
        return PresentationInjector.provideConnectivityHelper();
    }
}

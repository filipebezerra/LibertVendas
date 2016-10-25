package br.com.libertsolutions.libertvendas.app.presentation.activity;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import br.com.libertsolutions.libertvendas.app.R;
import br.com.libertsolutions.libertvendas.app.presentation.util.Navigator;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Filipe Bezerra
 */

public abstract class LibertVendasActivity extends AppCompatActivity {

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Nullable @BindView(R.id.toolbar) protected Toolbar mToolbar;

    private Navigator mNavigator;

    @Override
    protected void onCreate(@Nullable Bundle inState) {
        super.onCreate(inState);
        setContentView(provideContentViewResource());
        ButterKnife.bind(this);
        mNavigator = new Navigator(this);
    }

    @LayoutRes
    protected abstract int provideContentViewResource();

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected void setAsHomeActivity() {
        setupToolbar(R.drawable.ic_menu);
    }

    protected void setAsInitialFlowActivity() {
        setupToolbar(R.drawable.ic_clear);
    }

    protected void setAsSubActivity() {
        setupToolbar(R.drawable.ic_arrow_back);
    }

    @SuppressWarnings("ConstantConditions")
    private void setupToolbar(int drawableRes) {
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            getSupportActionBar().setHomeAsUpIndicator(drawableRes);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    public Navigator navigate() {
        if (mNavigator == null) {
            mNavigator = new Navigator(this);
        }
        return mNavigator;
    }
}

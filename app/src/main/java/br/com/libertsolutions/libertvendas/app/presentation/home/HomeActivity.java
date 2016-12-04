package br.com.libertsolutions.libertvendas.app.presentation.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.CardView;
import android.view.MenuItem;
import br.com.libertsolutions.libertvendas.app.Injection;
import br.com.libertsolutions.libertvendas.app.R;
import br.com.libertsolutions.libertvendas.app.presentation.activity.LibertVendasActivity;
import br.com.libertsolutions.libertvendas.app.presentation.view.SheetFloatingActionButton;
import butterknife.BindColor;
import butterknife.BindView;
import com.gordonwong.materialsheetfab.DimOverlayFrameLayout;
import com.gordonwong.materialsheetfab.MaterialSheetFab;

/**
 * @author Filipe Bezerra
 */
public class HomeActivity extends LibertVendasActivity
        implements NavigationView.OnNavigationItemSelectedListener, HomeContract.View {

    @BindView(R.id.fab) protected SheetFloatingActionButton mFloatingActionButton;
    @BindView(R.id.fab_sheet) protected CardView mFabSheet;
    @BindView(R.id.overlay) protected DimOverlayFrameLayout mFabOverlay;

    @BindView(R.id.drawer_layout) protected DrawerLayout mDrawerLayout;
    @BindView(R.id.nav_view) protected NavigationView mNavigationView;

    @BindColor(R.color.background_card) protected int mFabSheetColor;
    @BindColor(R.color.color_accent) protected int mAccentColor;

    private HomeContract.Presenter mPresenter;

    private MaterialSheetFab<SheetFloatingActionButton> mMaterialSheetFab;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setAsHomeActivity();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        mNavigationView.setNavigationItemSelectedListener(this);
        mNavigationView.getMenu().findItem(R.id.nav_home).setChecked(true);

        mMaterialSheetFab = new MaterialSheetFab<>(
                mFloatingActionButton, mFabSheet, mFabOverlay, mFabSheetColor, mAccentColor);

        mPresenter = new HomePresenter(Injection.provideSettingsRepository(this));
        mPresenter.attachView(this);
    }

    @Override protected int provideContentViewResource() {
        return R.layout.activity_home;
    }

    @Override public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else if (mMaterialSheetFab.isSheetVisible()) {
            mMaterialSheetFab.hideSheet();
        } else {
            super.onBackPressed();
        }
    }

    @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        item.setChecked(false);

        switch (item.getItemId()) {
            case R.id.nav_home: {
                break;
            }

            case R.id.nav_pedidos: {
                break;
            }

            case R.id.nav_clientes: {
                break;
            }

            case R.id.nav_produtos: {
                break;
            }

            case R.id.nav_settings: {
                break;
            }
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override public void navigateToInitialDataImportationFlow() {
        navigate().toInitialDataImportationFlow();
    }

    @Override public void showDrawer() {
        mDrawerLayout.openDrawer(GravityCompat.START);
    }

    @Override protected void onResume() {
        super.onResume();
        mPresenter.resume();
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        mPresenter.detach();
    }
}
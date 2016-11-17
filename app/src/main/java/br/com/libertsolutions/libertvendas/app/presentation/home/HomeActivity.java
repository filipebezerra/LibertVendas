package br.com.libertsolutions.libertvendas.app.presentation.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.CardView;
import android.view.MenuItem;
import android.view.View;
import br.com.libertsolutions.libertvendas.app.R;
import br.com.libertsolutions.libertvendas.app.presentation.activity.LibertVendasActivity;
import br.com.libertsolutions.libertvendas.app.presentation.activity.Navigator;
import br.com.libertsolutions.libertvendas.app.presentation.view.SheetFloatingActionButton;
import butterknife.BindColor;
import butterknife.BindView;
import butterknife.OnClick;
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

    @BindColor(R.color.background_card) protected int mFabSheetColor;
    @BindColor(R.color.color_accent) protected int mAccentColor;

    private HomeContract.Presenter mPresenter;

    private MaterialSheetFab<SheetFloatingActionButton> mMaterialSheetFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new HomePresenter(this);

        setAsHomeActivity();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, mToolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().findItem(R.id.nav_home).setChecked(true);

        mMaterialSheetFab = new MaterialSheetFab<>(
                mFloatingActionButton, mFabSheet, mFabOverlay, mFabSheetColor, mAccentColor);
    }

    @Override
    protected int provideContentViewResource() {
        return R.layout.activity_home;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (mMaterialSheetFab.isSheetVisible()) {
            mMaterialSheetFab.hideSheet();
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        item.setChecked(false);

        if (id == R.id.nav_home) {

        } else if (id == R.id.nav_pedidos) {
            mPresenter.clickNavigationMenuPedidos();
        } else if (id == R.id.nav_clientes) {
            mPresenter.clickNavigationMenuClientes();
        } else if (id == R.id.nav_produtos) {
            mPresenter.clickNavigationMenuProdutos();
        } else if (id == R.id.nav_settings) {
            mPresenter.clickNavigationMenuSettings();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override public void navigateToSettings() {
        navigate().toSettings(false);
    }

    @Override
    public void navigateToClientes() {
        navigate().toClientes();
    }

    @Override
    public void navigateToProdutos() {
        navigate().toProdutos();
    }

    @Override
    public void navigateToPedidos() {
        navigate().toPedidos();
    }

    @OnClick({ R.id.fab_sheet_item_novo_pedido, R.id.fab_sheet_item_novo_cliente })
    void onFabClick(View pView) {
        mMaterialSheetFab.hideSheet();
        if (pView.getId() == R.id.fab_sheet_item_novo_pedido) {
            navigate().toPedido();
        } else {
            navigate().toCliente(true);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Navigator.REQUEST_NEW_CLIENTE: {
                    mPresenter.getClienteFromResult(new ClienteExtrasExtractor(data));
                    break;
                }
                case Navigator.REQUEST_NEW_PEDIDO: {
                    mPresenter.getPedidoFromResult(new PedidoExtrasExtractor(data));
                    break;
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}

package br.com.libertsolutions.libertvendas.app.presentation.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.CardView;
import android.view.MenuItem;
import br.com.libertsolutions.libertvendas.app.Injection;
import br.com.libertsolutions.libertvendas.app.R;
import br.com.libertsolutions.libertvendas.app.presentation.activity.LibertVendasActivity;
import br.com.libertsolutions.libertvendas.app.presentation.activity.Navigator;
import br.com.libertsolutions.libertvendas.app.presentation.listaclientes.ListaClientesFragment;
import br.com.libertsolutions.libertvendas.app.presentation.listapedidos.TabsFragment;
import br.com.libertsolutions.libertvendas.app.presentation.listaprodutos.ListaProdutosFragment;
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

    @Override protected void onResume() {
        super.onResume();
        mPresenter.resume();
    }

    @Override public void showDrawer() {
        mDrawerLayout.openDrawer(GravityCompat.START);
    }

    @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home: {
                break;
            }

            case R.id.nav_pedidos: {
                mPresenter.handlePedidosNavigationItemSelected();
                break;
            }

            case R.id.nav_clientes: {
                mPresenter.handleClientesNavigationItemSelected();
                break;
            }

            case R.id.nav_produtos: {
                mPresenter.handleProdutosNavigationItemSelected();
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

    @Override public boolean isviewingListaPedidos() {
        return isViewingFragmentByTag(TabsFragment.TAG);
    }

    @Override public void navigateToListaPedidos() {
        navigate().toListaPedidos();
    }

    @Override public boolean isViewingListaClientes() {
        return isViewingFragmentByTag(ListaClientesFragment.TAG);
    }

    @Override public void navigateToListaClientes() {
        navigate().toListaClientes(false);
    }

    @Override public boolean isViewingListaProdutos() {
        return isViewingFragmentByTag(ListaProdutosFragment.TAG);
    }

    @Override public void navigateToListaProdutos() {
        navigate().toListaProdutos();
    }

    public boolean isViewingFragmentByTag(String tag) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);
        return fragment != null && fragment.isVisible();
    }

    @OnClick(R.id.fab_sheet_item_novo_cliente) void onNovoClienteSheetItemClicked() {
        mMaterialSheetFab.hideSheet();
        navigate().toCadastroCliente(null);
    }

    @OnClick(R.id.fab_sheet_item_novo_pedido) void onNovoPedidoSheetItemClicked() {
        mMaterialSheetFab.hideSheet();
        navigate().toCadastroPedido(null);
    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Navigator.REQUEST_EDITAR_CLIENTE:
            case Navigator.REQUEST_EDITAR_PEDIDO: {
                getSupportFragmentManager().findFragmentById(R.id.fragment_container)
                        .onActivityResult(requestCode, resultCode, data);
                break;
            }
            default: super.onActivityResult(requestCode, resultCode, data);
        }
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

    @Override protected void onDestroy() {
        super.onDestroy();
        mPresenter.detach();
    }
}
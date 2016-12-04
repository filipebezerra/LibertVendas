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
import android.widget.TextView;
import br.com.libertsolutions.libertvendas.app.Injection;
import br.com.libertsolutions.libertvendas.app.R;
import br.com.libertsolutions.libertvendas.app.presentation.activity.LibertVendasActivity;
import br.com.libertsolutions.libertvendas.app.presentation.activity.Navigator;
import br.com.libertsolutions.libertvendas.app.presentation.view.SheetFloatingActionButton;
import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
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

    @Override protected void onStart() {
        super.onStart();
        mPresenter.registerForEvents();
    }

    @Override protected void onStop() {
        super.onStop();
        mPresenter.unregisterForEvents();
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
        int id = item.getItemId();
        item.setChecked(false);

        //noinspection StatementWithEmptyBody
        if (id == R.id.nav_home) {

        } else if (id == R.id.nav_pedidos) {
            mPresenter.handlePedidosNavigationItemSelected();
        } else if (id == R.id.nav_clientes) {
            mPresenter.handleClientesNavigationItemSelected();
        } else if (id == R.id.nav_produtos) {
            mPresenter.handleProdutosNavigationItemSelected();
        } else if (id == R.id.nav_settings) {
            mPresenter.handleSettingsNavigationItemSelected();
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override public void showUsuarioLogado(String pNomeVendedor, String pNomeEmpresa) {
        ButterKnife.<TextView>findById(mNavigationView.getHeaderView(0), R.id.text_view_nome_vendedor)
                .setText(pNomeVendedor);
        ButterKnife.<TextView>findById(mNavigationView.getHeaderView(0), R.id.text_view_nome_empresa)
                .setText(pNomeEmpresa);
    }

    @Override public void showFeaturedMenu() {
        mDrawerLayout.openDrawer(GravityCompat.START);
    }

    @Override public void navigateToSettings() {
        navigate().toSettings(false);
    }

    @Override public void navigateToClientes() {
        navigate().toListaClientes(false);
    }

    @Override public void navigateToProdutos() {
        navigate().toListaProdutos(false);
    }

    @Override public void navigateToPedidos() {
        navigate().toPedidos();
    }

    @OnClick(R.id.fab_sheet_item_novo_pedido) void onNovoPedidoSheetItemClicked() {
        mMaterialSheetFab.hideSheet();
        navigate().toCadastroPedido();
    }

    @OnClick(R.id.fab_sheet_item_novo_cliente) void onNovoClienteSheetItemClicked() {
        mMaterialSheetFab.hideSheet();
        navigate().toCadastroCliente(null);
    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Navigator.REQUEST_EDITAR_CLIENTE) {
            getSupportFragmentManager().findFragmentById(R.id.fragment_container)
                    .onActivityResult(requestCode, resultCode, data);
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        mPresenter.detach();
    }
}

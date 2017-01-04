package br.com.libertsolutions.libertvendas.app.presentation.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.View;
import br.com.libertsolutions.libertvendas.app.DataInjection;
import br.com.libertsolutions.libertvendas.app.PresentationInjection;
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
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondarySwitchDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Filipe Bezerra
 */
public class HomeActivity extends LibertVendasActivity
        implements HomeContract.View, Drawer.OnDrawerItemClickListener {

    private static final int DRAWER_ITEM_HOME = 1;
    private static final int DRAWER_ITEM_ORDERS = 2;
    private static final int DRAWER_ITEM_CUSTOMERS = 3;
    private static final int DRAWER_ITEM_PRODUCTS = 4;
    private static final int DRAWER_ITEM_AUTO_SYNC = 5;
    private static final int DRAWER_ITEM_SETTINGS = 6;

    @BindView(R.id.fab) protected SheetFloatingActionButton mFloatingActionButton;
    @BindView(R.id.fab_sheet) protected CardView mFabSheet;
    @BindView(R.id.overlay) protected DimOverlayFrameLayout mFabOverlay;

    @BindColor(R.color.background_card) protected int mFabSheetColor;
    @BindColor(R.color.color_accent) protected int mAccentColor;

    private HomeContract.Presenter mPresenter;

    private Bundle mInState;

    private Drawer mDrawer;

    private AccountHeader mAccountHeader;

    private PrimaryDrawerItem mHomeDrawerItem;

    private PrimaryDrawerItem mOrdersDrawerItem;

    private PrimaryDrawerItem mCustomersDrawerItem;

    private PrimaryDrawerItem mProductsDrawerItem;

    private SecondarySwitchDrawerItem mAutoSyncDrawerItem;

    private SecondaryDrawerItem mSettingsDrawerItem;

    private MaterialSheetFab<SheetFloatingActionButton> mMaterialSheetFab;

    @Override protected int provideContentViewResource() {
        return R.layout.activity_home;
    }

    @Override protected void onCreate(@Nullable final Bundle inState) {
        mPresenter = new HomePresenter(
                PresentationInjection.provideSettingsRepository(),
                DataInjection.LocalRepositories.provideVendedorRepository(),
                DataInjection.LocalRepositories.providePedidoRepository());
        super.onCreate(mInState = inState);
        setAsHomeActivity();
        mPresenter.attachView(this);
        mPresenter.validateInitialSetup();
    }

    @Override public void startInitialConfiguration() {
        navigate().toSettings(true);
    }

    @Override public void startLogin() {
        navigate().toLogin();
    }

    @Override public void startDataImportation() {
        navigate().toImportacao();
    }

    @Override public void initializeDrawerHeader(
            final String userName, final List<String> companyNames) {
        List<IProfile> profiles = new ArrayList<>();
        for (String company : companyNames) {
            profiles.add(new ProfileDrawerItem()
                    .withName(userName)
                    .withEmail(company)
                    .withIcon(VectorDrawableCompat
                            .create(getResources(), R.drawable.ic_user, getTheme())));
        }

        mAccountHeader = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header_background)
                .withProfiles(profiles)
                .withSavedInstance(mInState)
                .withOnlyMainProfileImageVisible(true)
                .build();
    }

    @Override public void initializeDrawer(final boolean autoSync) {
        mHomeDrawerItem = new PrimaryDrawerItem()
                .withIdentifier(DRAWER_ITEM_HOME)
                .withName(R.string.drawer_item_home)
                .withIcon(VectorDrawableCompat
                        .create(getResources(), R.drawable.ic_home, getTheme()))
                .withSelectedIconColorRes(R.color.color_primary)
        ;

        mOrdersDrawerItem = new PrimaryDrawerItem()
                .withIdentifier(DRAWER_ITEM_ORDERS)
                .withName(R.string.drawer_item_pedidos)
                .withIcon(VectorDrawableCompat
                        .create(getResources(), R.drawable.ic_list, getTheme()))
                .withSelectedIconColorRes(R.color.color_primary)
        ;

        mCustomersDrawerItem = new PrimaryDrawerItem()
                .withIdentifier(DRAWER_ITEM_CUSTOMERS)
                .withName(R.string.drawer_item_clientes)
                .withIcon(VectorDrawableCompat
                        .create(getResources(), R.drawable.ic_customer, getTheme()))
                .withSelectedIconColorRes(R.color.color_primary)
        ;

        mProductsDrawerItem = new PrimaryDrawerItem()
                .withIdentifier(DRAWER_ITEM_PRODUCTS)
                .withName(R.string.drawer_item_produtos)
                .withIcon(VectorDrawableCompat
                        .create(getResources(), R.drawable.ic_box, getTheme()))
                .withSelectedIconColorRes(R.color.color_primary)
        ;

        mAutoSyncDrawerItem = new SecondarySwitchDrawerItem()
                .withIdentifier(DRAWER_ITEM_AUTO_SYNC)
                .withName(R.string.drawer_item_sincronizacao_automatica)
                .withIcon(VectorDrawableCompat
                        .create(getResources(), R.drawable.ic_sync, getTheme()))
                .withSelectedIconColorRes(R.color.color_primary)
                .withChecked(autoSync)
                .withSelectable(false)
                .withOnCheckedChangeListener((drawerItem, buttonView, isChecked) ->
                        mPresenter.handleAutoSyncChanged(isChecked))
        ;

        mSettingsDrawerItem = new SecondaryDrawerItem()
                .withIdentifier(DRAWER_ITEM_SETTINGS)
                .withName(R.string.drawer_item_configuracoes)
                .withIcon(VectorDrawableCompat
                        .create(getResources(), R.drawable.ic_settings, getTheme()))
                .withSelectable(false)
        ;

        mDrawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(mToolbar)
                .withAccountHeader(mAccountHeader)
                .addDrawerItems(
                        mHomeDrawerItem,
                        mOrdersDrawerItem,
                        mCustomersDrawerItem,
                        mProductsDrawerItem,
                        new DividerDrawerItem(),
                        mAutoSyncDrawerItem,
                        mSettingsDrawerItem
                )
                .withSavedInstance(mInState)
                .withShowDrawerOnFirstLaunch(true)
                .withOnDrawerItemClickListener(this)
                .withSelectedItem(DRAWER_ITEM_HOME)
                .withFireOnInitialOnClick(true)
                .build();
    }

    @Override public void initializeViews() {
        mMaterialSheetFab = new MaterialSheetFab<>(
                mFloatingActionButton, mFabSheet, mFabOverlay, mFabSheetColor, mAccentColor);
    }

    @Override public void initializeDrawerBadgeOrdersCounter(final int count) {
        mOrdersDrawerItem.withBadge(String.valueOf(count));
        mDrawer.updateItem(mOrdersDrawerItem);
    }

    @Override public boolean onItemClick(
            final View view, final int position, final IDrawerItem drawerItem) {
        switch ((int) drawerItem.getIdentifier()) {
            case DRAWER_ITEM_ORDERS: {
                mPresenter.handleOrdersNavigationItemSelected();
                break;
            }
            case DRAWER_ITEM_CUSTOMERS: {
                mPresenter.handleCustomersNavigationItemSelected();
                break;
            }
            case DRAWER_ITEM_PRODUCTS: {
                mPresenter.handleProductsNavigationItemSelected();
                break;
            }
            case DRAWER_ITEM_SETTINGS: {
                mPresenter.handleSettingsNavigationItemSelected();
                break;
            }
        }
        return false;
    }

    @OnClick(R.id.fab_sheet_item_novo_cliente) void onNewCustomerFabClicked() {
        mMaterialSheetFab.hideSheet();
        navigate().toCadastroCliente(null);
    }

    @OnClick(R.id.fab_sheet_item_novo_pedido) void onNewOrderFabClicked() {
        mMaterialSheetFab.hideSheet();
        navigate().toCadastroPedido(null);
    }

    @Override public boolean getAutoSyncCheckState() {
        return mAutoSyncDrawerItem.isChecked();
    }

    @Override public void setAutoSyncCheckState(final boolean autoSync) {
        mAutoSyncDrawerItem.withChecked(autoSync);
        mDrawer.updateItem(mAutoSyncDrawerItem);
    }

    @Override public boolean isViewingOrders() {
        return isViewingFragmentByTag(TabsFragment.TAG);
    }

    @Override public void navigateToOrders() {
        navigate().toOrders();
    }

    @Override public boolean isViewingCustomers() {
        return isViewingFragmentByTag(ListaClientesFragment.TAG);
    }

    @Override public void navigateToCustomers() {
        navigate().toCustomers();
    }

    @Override public boolean isViewingProducts() {
        return isViewingFragmentByTag(ListaProdutosFragment.TAG);
    }

    @Override public void navigateToProducts() {
        navigate().toProducts();
    }

    @Override public void navigateToSettings() {
        navigate().toSettings(false);
    }

    private boolean isViewingFragmentByTag(String tag) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);
        return fragment != null && fragment.isVisible();
    }

    @Override protected void onActivityResult(
            final int requestCode, final int resultCode, final Intent data) {
        switch (requestCode) {
            case Navigator.REQUEST_EDITAR_CLIENTE:
            case Navigator.REQUEST_EDITAR_PEDIDO:
                getSupportFragmentManager().findFragmentById(R.id.fragment_container)
                        .onActivityResult(requestCode, resultCode, data);
                break;
            default: mPresenter.handleViewAfterResulted(requestCode, resultCode);
        }
    }

    @Override public void onBackPressed() {
        if (mDrawer.isDrawerOpen()) {
            mDrawer.closeDrawer();
        } else if (mMaterialSheetFab.isSheetVisible()) {
            mMaterialSheetFab.hideSheet();
        } else {
            super.onBackPressed();
        }
    }

    @Override protected void onSaveInstanceState(Bundle outState) {
        if (mDrawer != null) {
            outState = mDrawer.saveInstanceState(outState);
        }
        if (mAccountHeader != null) {
            outState = mAccountHeader.saveInstanceState(outState);
        }
        super.onSaveInstanceState(outState);
    }

    @Override protected void onDestroy() {
        mPresenter.detach();
        super.onDestroy();
    }
}

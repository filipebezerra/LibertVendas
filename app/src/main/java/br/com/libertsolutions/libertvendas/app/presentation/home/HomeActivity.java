package br.com.libertsolutions.libertvendas.app.presentation.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v7.widget.CardView;
import android.view.View;
import br.com.libertsolutions.libertvendas.app.DataInjection;
import br.com.libertsolutions.libertvendas.app.PresentationInjection;
import br.com.libertsolutions.libertvendas.app.R;
import br.com.libertsolutions.libertvendas.app.presentation.activity.LibertVendasActivity;
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
    private static final int DRAWER_ITEM_PEDIDOS = 2;
    private static final int DRAWER_ITEM_CLIENTES = 3;
    private static final int DRAWER_ITEM_PRODUTOS = 4;
    private static final int DRAWER_ITEM_SINCRONIZAR_AUTOMATICAMENTE = 5;
    private static final int DRAWER_ITEM_CONFIGURACOES = 6;

    @BindView(R.id.fab) protected SheetFloatingActionButton mFloatingActionButton;
    @BindView(R.id.fab_sheet) protected CardView mFabSheet;
    @BindView(R.id.overlay) protected DimOverlayFrameLayout mFabOverlay;

    @BindColor(R.color.background_card) protected int mFabSheetColor;
    @BindColor(R.color.color_accent) protected int mAccentColor;

    private Drawer mDrawer;

    private AccountHeader mAccountHeader;

    private PrimaryDrawerItem mHomeDrawerItem;

    private PrimaryDrawerItem mPedidosDrawerItem;

    private PrimaryDrawerItem mClientesDrawerItem;

    private PrimaryDrawerItem mProdutosDrawerItem;

    private SecondarySwitchDrawerItem mSincronizacaoAutomaticaDrawerItem;

    private SecondaryDrawerItem mConfiguracoesDrawerItem;

    private MaterialSheetFab<SheetFloatingActionButton> mMaterialSheetFab;

    private HomeContract.Presenter mPresenter;

    private Bundle mInState;

    @Override protected int provideContentViewResource() {
        return R.layout.activity_home;
    }

    @Override protected void onCreate(@Nullable final Bundle inState) {
        super.onCreate(mInState = inState);
        setAsHomeActivity();
        mPresenter = new HomePresenter(
                PresentationInjection.provideSettingsRepository(this),
                DataInjection.LocalRepositories.provideVendedorRepository());
        mPresenter.attachView(this);
        mPresenter.initializeView();
    }

    @Override public void setupViews(final String nomeVendedor, final List<String> nomeEmpresas) {
        List<IProfile> profiles = new ArrayList<>();
        for (String empresa : nomeEmpresas) {
            profiles.add(new ProfileDrawerItem()
                    .withName(nomeVendedor)
                    .withEmail(empresa)
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

        mHomeDrawerItem = new PrimaryDrawerItem()
                .withIdentifier(DRAWER_ITEM_HOME)
                .withName(R.string.drawer_item_home)
                .withIcon(VectorDrawableCompat
                        .create(getResources(), R.drawable.ic_home, getTheme()))
                .withSelectedIconColorRes(R.color.color_primary)
        ;

        mPedidosDrawerItem = new PrimaryDrawerItem()
                .withIdentifier(DRAWER_ITEM_PEDIDOS)
                .withName(R.string.drawer_item_pedidos)
                .withIcon(VectorDrawableCompat
                        .create(getResources(), R.drawable.ic_list, getTheme()))
                .withSelectedIconColorRes(R.color.color_primary)
                .withBadge("7")
        ;

        mClientesDrawerItem = new PrimaryDrawerItem()
                .withIdentifier(DRAWER_ITEM_CLIENTES)
                .withName(R.string.drawer_item_clientes)
                .withIcon(VectorDrawableCompat
                        .create(getResources(), R.drawable.ic_customer, getTheme()))
                .withSelectedIconColorRes(R.color.color_primary)
        ;

        mProdutosDrawerItem = new PrimaryDrawerItem()
                .withIdentifier(DRAWER_ITEM_PRODUTOS)
                .withName(R.string.drawer_item_produtos)
                .withIcon(VectorDrawableCompat
                        .create(getResources(), R.drawable.ic_box, getTheme()))
                .withSelectedIconColorRes(R.color.color_primary)
        ;

        mSincronizacaoAutomaticaDrawerItem = new SecondarySwitchDrawerItem()
                .withIdentifier(DRAWER_ITEM_SINCRONIZAR_AUTOMATICAMENTE)
                .withName(R.string.drawer_item_sincronizacao_automatica)
                .withIcon(VectorDrawableCompat
                        .create(getResources(), R.drawable.ic_sync, getTheme()))
                .withSelectedIconColorRes(R.color.color_primary)
                .withChecked(true)
                .withSelectable(false)
        ;

        mConfiguracoesDrawerItem = new SecondaryDrawerItem()
                .withIdentifier(DRAWER_ITEM_CONFIGURACOES)
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
                        mPedidosDrawerItem,
                        mClientesDrawerItem,
                        mProdutosDrawerItem,
                        new DividerDrawerItem(),
                        mSincronizacaoAutomaticaDrawerItem,
                        mConfiguracoesDrawerItem
                )
                .withSavedInstance(mInState)
                .withShowDrawerOnFirstLaunch(true)
                .withOnDrawerItemClickListener(this)
                .withSelectedItem(DRAWER_ITEM_HOME)
                .withFireOnInitialOnClick(true)
                .build();

        mMaterialSheetFab = new MaterialSheetFab<>(
                mFloatingActionButton, mFabSheet, mFabOverlay, mFabSheetColor, mAccentColor);
    }

    @Override public void startInitialConfiguration() {
        navigate().toSettings();
    }

    @Override public void startLogin() {
        navigate().toLogin();
    }

    @Override public void startDataImportation() {
        navigate().toImportacao();
    }

    @Override public void finalizeView() {
        finish();
    }

    @Override protected void onActivityResult(
            final int requestCode, final int resultCode, final Intent data) {
        mPresenter.handleViewAfterResulted(requestCode, resultCode);
    }

    @OnClick(R.id.fab_sheet_item_novo_cliente) void onNovoClienteSheetItemClicked() {
        mMaterialSheetFab.hideSheet();
    }

    @OnClick(R.id.fab_sheet_item_novo_pedido) void onNovoPedidoSheetItemClicked() {
        mMaterialSheetFab.hideSheet();
    }

    @Override public boolean onItemClick(
            final View view, final int position, final IDrawerItem drawerItem) {
        switch ((int) drawerItem.getIdentifier()) {
            case DRAWER_ITEM_PRODUTOS: {
                navigate().toListaProdutos();
                break;
            }
            case DRAWER_ITEM_SINCRONIZAR_AUTOMATICAMENTE: {
                return true;
            }
            case DRAWER_ITEM_CONFIGURACOES: {
                navigate().toSettings();
                break;
            }
            case DRAWER_ITEM_CLIENTES: {
                break;
            }
        }
        return false;
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
        mPresenter.finalizeView();
        mPresenter.detach();
        super.onDestroy();
    }
}

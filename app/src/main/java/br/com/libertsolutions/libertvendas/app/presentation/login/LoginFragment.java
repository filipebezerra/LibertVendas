package br.com.libertsolutions.libertvendas.app.presentation.login;

import android.content.DialogInterface;
import android.support.annotation.StringRes;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.KeyEvent;
import br.com.libertsolutions.libertvendas.app.R;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Company;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Salesman;
import br.com.libertsolutions.libertvendas.app.presentation.base.BaseFragment;
import br.com.libertsolutions.libertvendas.app.presentation.exception.ValidationError;
import br.com.libertsolutions.libertvendas.app.presentation.util.AnswersEvents;
import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import com.afollestad.materialdialogs.MaterialDialog;
import java.io.IOException;
import java.util.List;
import retrofit2.HttpException;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import timber.log.Timber;

import static br.com.libertsolutions.libertvendas.app.data.RemoteDataInjector.provideSalesmanApi;
import static br.com.libertsolutions.libertvendas.app.domain.pojo.LoggedUser.create;
import static br.com.libertsolutions.libertvendas.app.presentation.login.CompletedLoginEvent.newEvent;
import static br.com.libertsolutions.libertvendas.app.presentation.util.AnswersEvents.LOGIN_METHOD_CPF_CNPJ;
import static rx.android.schedulers.AndroidSchedulers.mainThread;

/**
 * @author Filipe Bezerra
 */
public class LoginFragment extends BaseFragment {

    private static final int ONE_PROFILE = 1;

    public static final String TAG = LoginFragment.class.getName();

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    private Subscription mLoginSubscription;

    private MaterialDialog mProgressDialog;

    @BindView(R.id.input_layout_login_user_identity) TextInputLayout mInputLayoutUserIdentity;

    @BindView(R.id.input_layout_login_user_password) TextInputLayout mInputLayoutUserPassword;

    @Override protected int provideContentViewResource() {
        return R.layout.fragment_login;
    }

    @OnClick(R.id.button_login) void onButtonLoginClicked() {
        login();
    }

    @OnEditorAction(R.id.edit_text_login_user_password) boolean onEditTextPasswordAction(KeyEvent key) {
        if (key == null || key.getAction() == KeyEvent.ACTION_UP) {
            login();
        }
        return true;
    }

    private void login() {
        if (!connectivity().isOnline()) {
            new MaterialDialog.Builder(getContext())
                    .title(R.string.all_offline_message_title)
                    .content(R.string.all_offline_message)
                    .positiveText(R.string.all_retry)
                    .onPositive((dialog, which) -> login())
                    .negativeText(android.R.string.cancel)
                    .onNegative((dialog, which) -> getActivity().finish())
                    .neutralText(android.R.string.ok)
                    .show();
            return;
        }

        boolean identityIsSet = userIdentityIsSet();
        boolean passwordIsSet = userPasswordIsSet();
        if (identityIsSet && passwordIsSet) {
            subscribeLogin();
        }
    }

    private void subscribeLogin() {
        mLoginSubscription = getSalesmanAsObservable()
                .observeOn(mainThread())
                .subscribe(getSalesmanSubscriber());
    }

    private Subscriber<Salesman> getSalesmanSubscriber() {
        return new Subscriber<Salesman>() {
            @Override public void onStart() {
                startLogin();
            }

            @Override public void onError(final Throwable e) {
                processLoginError(e);
            }

            @Override public void onNext(final Salesman salesman) {
                processLoginResult(salesman);
            }

            @Override public void onCompleted() {}
        };
    }

    private void processLoginError(final Throwable e) {
        stopLogin();
        Timber.e(e, "Could not do login");
        AnswersEvents.unsuccessfulLogin(LOGIN_METHOD_CPF_CNPJ);

        MaterialDialog.Builder builder = new MaterialDialog.Builder(getContext())
                .neutralText(android.R.string.ok);

        if (e instanceof ValidationError) {
            builder.content(e.getMessage()).show();
            return;
        }

        if (e instanceof HttpException) {
            builder.content(R.string.all_server_error_message);
        } else if (e instanceof IOException) {
            builder.content(R.string.all_network_error_message);
        } else {
            builder.content(R.string.all_unknown_error_message);
        }

        builder
                .positiveText(R.string.all_retry)
                .onPositive((dialog, which) -> login())
                .negativeText(android.R.string.cancel)
                .onNegative((dialog, which) -> getActivity().finish())
                .show();
    }

    private void processLoginResult(Salesman salesman) {
        stopLogin();

        final List<Company> companies = salesman.getCompanies();
        if (companies.size() > ONE_PROFILE) {
            new MaterialDialog.Builder(getContext())
                    .title(R.string.login_select_default_company)
                    .items(companies)
                    .itemsCallbackSingleChoice(-1, (dialog, itemView, which, text) -> {
                        saveLogin(salesman, companies.get(which), companies.size());
                        return true;
                    })
                    .alwaysCallSingleChoiceCallback()
                    .show();
            return;
        }

        saveLogin(salesman, companies.get(0), ONE_PROFILE);
    }

    private void saveLogin(Salesman salesman, Company defaultCompany, int numberOfProfiles) {
        settings().setLoggedUser(salesman, defaultCompany);
        AnswersEvents.successfulLogin(LOGIN_METHOD_CPF_CNPJ, numberOfProfiles);
        eventBus().postSticky(newEvent(create(salesman, defaultCompany)));
    }

    private void startLogin() {
        mProgressDialog = new MaterialDialog.Builder(getContext())
                .progress(true, 0)
                .progressIndeterminateStyle(true)
                .title(R.string.login_working_progress_message)
                .content(R.string.all_please_wait)
                .cancelable(true)
                .canceledOnTouchOutside(false)
                .cancelListener(cancelLogin())
                .show();
    }

    private DialogInterface.OnCancelListener cancelLogin() {
        return dialog -> {
            if (mLoginSubscription != null && mLoginSubscription.isUnsubscribed()) {
                mLoginSubscription.unsubscribe();
                mLoginSubscription = null;
                stopLogin();
            }
        };
    }

    private void stopLogin() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

    private Observable<Salesman> getSalesmanAsObservable() {
        return provideSalesmanApi()
                .get(inputValue(mInputLayoutUserIdentity), inputValue(mInputLayoutUserPassword))
                .flatMap(salesmanDto -> {
                    if (salesmanDto.error) {
                        return Observable.error(ValidationError.newError(salesmanDto.message));
                    } else if (salesmanDto.salesman.getCompanies().isEmpty()) {
                        return Observable.error(ValidationError.newError(
                                getString(R.string.login_salesman_with_no_companies_error)));
                    } else {
                        return Observable.defer(() -> Observable.just(salesmanDto.salesman));
                    }
                });
    }

    private boolean userIdentityIsSet() {
        return inputFieldIsSet(mInputLayoutUserIdentity, R.string.login_user_identity_not_set_error);
    }

    private boolean userPasswordIsSet() {
        return inputFieldIsSet(mInputLayoutUserPassword, R.string.login_user_password_not_set_error);
    }

    private boolean inputFieldIsSet(TextInputLayout inputLayout, @StringRes int errorCaseNotSet) {
        if (TextUtils.isEmpty(inputValue(inputLayout))) {
            inputLayout.setError(getString(errorCaseNotSet));
            return false;
        } else {
            inputLayout.setError(null);
            return true;
        }
    }

    private String inputValue(TextInputLayout input) {
        return input.getEditText().getText().toString();
    }

    @Override public void onDestroyView() {
        if (mLoginSubscription != null && mLoginSubscription.isUnsubscribed()) {
            mLoginSubscription.unsubscribe();
        }
        super.onDestroyView();
    }
}

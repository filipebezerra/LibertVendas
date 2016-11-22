package br.com.libertsolutions.libertvendas.app.presentation.login;

import android.support.annotation.NonNull;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Vendedor;
import br.com.libertsolutions.libertvendas.app.presentation.events.UsuarioLogadoEvent;
import br.com.libertsolutions.libertvendas.app.presentation.util.ValidationError;
import java.io.IOException;
import org.greenrobot.eventbus.EventBus;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

/**
 * @author Filipe Bezerra
 */
class LoginSubscriber extends Subscriber<Vendedor> {
    private final LoginContract.View mView;

    private LoginSubscriber(LoginContract.View pView) {
        mView = pView;
    }

    static LoginSubscriber newLoginSubscriber(@NonNull LoginContract.View pView) {
        return new LoginSubscriber(pView);
    }

    @Override public void onStart() {
        mView.showLoading();
        mView.blockEditEntries();
    }

    @Override public void onError(Throwable pError) {
        mView.showErrorIndicator();
        mView.unblockEditEntries();
        if (pError instanceof HttpException) {
            mView.showServerError();
        } else if (pError instanceof IOException) {
            mView.showNetworkError();
        } else if (pError instanceof ValidationError) {
            mView.displayValidationError(pError.getMessage());
            mView.showFeedbackMessage(pError.getMessage());
        } else {
            mView.showUnknownError();
        }
    }

    @Override public void onNext(Vendedor pVendedor) {
        EventBus.getDefault().postSticky(UsuarioLogadoEvent.newEvent(pVendedor));
    }

    @Override public void onCompleted() {
        mView.showCompletedIndicator();
    }
}

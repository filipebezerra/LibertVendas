package br.com.libertsolutions.libertvendas.app.presentation.login;

import android.support.annotation.NonNull;
import br.com.libertsolutions.libertvendas.app.domain.pojo.Vendedor;
import br.com.libertsolutions.libertvendas.app.presentation.util.ValidationError;
import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.LoginEvent;
import java.io.IOException;
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

        String errorType;
        if (pError instanceof HttpException) {
            errorType = "server";
            mView.showServerError();
        } else if (pError instanceof IOException) {
            errorType = "network";
            mView.showNetworkError();
        } else if (pError instanceof ValidationError) {
            errorType = "validation";
            mView.displayValidationError(pError.getMessage());
            mView.showFeedbackMessage(pError.getMessage());
        } else {
            errorType = "unknown";
            mView.showUnknownError();
        }

        Answers.getInstance().logLogin(new LoginEvent()
                .putMethod("Digits")
                .putSuccess(false)
                .putCustomAttribute("errorType", errorType));
    }

    @Override public void onNext(Vendedor pVendedor) {}

    @Override public void onCompleted() {
        mView.showCompletedIndicator();
    }
}

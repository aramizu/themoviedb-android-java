package br.com.aramizu.themoviedb.presentation.ui.base;

import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import javax.inject.Inject;

import br.com.aramizu.themoviedb.R;
import br.com.aramizu.themoviedb.data.DataManager;
import br.com.aramizu.themoviedb.data.model.ErrorResponse;
import br.com.aramizu.themoviedb.presentation.ui.custom.dialogs.GenericDialogOkCancel;
import io.reactivex.disposables.CompositeDisposable;

public class BasePresenter<V extends MvpView> implements MvpPresenter<V> {

    private static final String LOG_TAG = "BasePresenter";

    private V mMvpView;
    private final DataManager dataManager;
    private final CompositeDisposable compositeDisposable;

    @Inject
    public BasePresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        this.dataManager = dataManager;
        this.compositeDisposable = compositeDisposable;
    }

    @Override
    public void onAttach(V mvpView) {
        mMvpView = mvpView;
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDetach() {
        compositeDisposable.dispose();
        mMvpView = null;
    }

    public boolean isViewAttached() {
        return mMvpView != null;
    }

    public V getMvpView() {
        return mMvpView;
    }

    public DataManager getDataManager() {
        return dataManager;
    }

    public CompositeDisposable getCompositeDisposable() {
        return compositeDisposable;
    }

    public void handlerThrowableError(V view, Throwable throwable) {
        if (throwable instanceof HttpException) {
            HttpException httpException = (HttpException) throwable;
            ErrorResponse error = getDataManager().parseError(httpException.response());
            if (error != null) {
                handleHttpExceptionWithErrorResponse(error);
            } else {
                view.onError(R.string.dialog_title_error, R.string.dialog_title_generic_message);
            }
        }
        else {
            view.onError(R.string.dialog_title_error, R.string.dialog_title_generic_message);
        }
    }

    public void handleHttpExceptionWithErrorResponse(final ErrorResponse error) {
        final GenericDialogOkCancel dialog = new GenericDialogOkCancel(
                getMvpView().getContext(),
                null,
                error.getStatusMessage(),
                null,
                "OK"
        );
        dialog.showDialog();
    }
}

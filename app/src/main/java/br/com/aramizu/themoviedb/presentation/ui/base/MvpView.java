package br.com.aramizu.themoviedb.presentation.ui.base;

import android.content.Context;
import android.support.annotation.StringRes;

/**
 * Base interface that any class that wants to act as a View in the MVP (Model View Presenter)
 * pattern must implement. Generally this interface will be extended by a more specific interface
 * that then usually will be implemented by an Activity or Fragment.
 */
public interface MvpView {
    void showLoading();
    void hideLoading();
    void onError(@StringRes int resTitleId, @StringRes int resMessageId);
    void onError(String title, String message);
    boolean isNetworkConnected();
    void hideKeyboard();
    void setToolbarTitle(@StringRes int resId);
    void setToolbarTitle(String message);
    String getString(@StringRes int resId);
    Context getContext();
}

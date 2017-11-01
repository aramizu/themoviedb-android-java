package br.com.aramizu.themoviedb.presentation.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;

import br.com.aramizu.themoviedb.R;
import br.com.aramizu.themoviedb.presentation.internal.di.components.ActivityComponent;
import br.com.aramizu.themoviedb.presentation.ui.home.now_playing.NowPlayingFragment;
import br.com.aramizu.themoviedb.presentation.ui.home.search.SearchFragment;

public abstract class BaseFragment extends Fragment implements MvpView {

    private BaseActivity activity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivity) {
            BaseActivity activity = (BaseActivity) context;
            this.activity = activity;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        activity.hideKeyboard();
    }

    @Override
    public void onPause() {
        super.onPause();
        activity.hideKeyboard();
    }

    @Override
    public void onDestroy() {
        onDetach();
        super.onDestroy();
    }

    /**
     * Show or hide menu overflow from toolbar starting from fragment
     * @param menu
     */
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.search);
        if (this instanceof SearchFragment)
            item.setVisible(false);
        if (this instanceof NowPlayingFragment)
            item.setVisible(true);
        super.onPrepareOptionsMenu(menu);
    }

    /**
     * Show loading on full screen to wait services
     */
    @Override
    public void showLoading() {
        if (activity != null) {
            activity.showLoading();
        }
    }

    /**
     * Hide loading on full screen to wait services
     */
    @Override
    public void hideLoading() {
        if (activity != null) {
            activity.hideLoading();
        }
    }

    /**
     * Show error dialog.
     * @param resId
     * @param type
     */
    @Override
    public void onError(@StringRes int resId, int type) {
        if (activity != null) {
            activity.onError(resId, type);
        }
    }

    /**
     * Show error dialog.
     * @param title
     * @param message
     */
    @Override
    public void onError(String title, String message) {
        if (activity != null) {
            activity.onError(title, message);
        }
    }

    /**
     * Check if device has any kind of network
     * @return
     */
    @Override
    public boolean isNetworkConnected() {
        if (activity != null) {
            return activity.isNetworkConnected();
        }
        onError(getString(R.string.dialog_title_error), getString(R.string.dialog_title_no_network_message));
        return false;
    }

    /**
     * Hide de SIP Keyboard
     */
    @Override
    public void hideKeyboard() {
        if (activity != null) {
            activity.hideKeyboard();
        }
    }

    /**
     * Detach the current fragment from his parent activity
     */
    @Override
    public void onDetach() {
        activity = null;
        super.onDetach();
    }

    /**
     * Abstract method to setup after creation of a Activity or Fragment
     */
    protected abstract void setUp();

    public ActivityComponent getActivityComponent() {
        return activity.getActivityComponent();
    }

    public BaseActivity getBaseActivity() {
        return activity;
    }

    @Override
    public void setToolbarTitle(@StringRes int resId) {
        getBaseActivity().setToolbarTitle(resId);
    }

    @Override
    public void setToolbarTitle(String message) {
        getBaseActivity().setToolbarTitle(message);
    }

    /**
     * Change any component from toolbar (home button, color etc)
     * @param toolbarStyle
     */
    @Override
    public void setToolbarStyle(int toolbarStyle) {
        getBaseActivity().setToolbarStyle(toolbarStyle);
    }

    public Context getContext() {
        return activity.getContext();
    }
}

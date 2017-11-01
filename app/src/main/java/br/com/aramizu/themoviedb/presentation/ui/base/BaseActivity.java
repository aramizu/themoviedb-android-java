package br.com.aramizu.themoviedb.presentation.ui.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import br.com.aramizu.themoviedb.R;
import br.com.aramizu.themoviedb.config.AndroidApplication;
import br.com.aramizu.themoviedb.presentation.internal.di.components.ActivityComponent;
import br.com.aramizu.themoviedb.presentation.internal.di.components.DaggerActivityComponent;
import br.com.aramizu.themoviedb.presentation.internal.di.module.ActivityModule;
import br.com.aramizu.themoviedb.utils.CommonUtils;
import br.com.aramizu.themoviedb.utils.NetworkUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity implements MvpView {

    @Nullable
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @Nullable
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    private ProgressDialog progressDialog;
    private ActivityComponent mActivityComponent;

    public static final int NOW_PLAYING_STYLE = 0;
    public static final int SEARCH_STYLE = 1;
    public static final int DETAILS_STYLE = 2;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
        setUpToolbar();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityComponent = DaggerActivityComponent.builder()
                .activityModule(new ActivityModule(this))
                .applicationComponent(AndroidApplication.get(this).getComponent())
                .build();
    }

    /**
     * Setup ActitonBar with the custom toolbar
     */
    private void setUpToolbar() {
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    public ActivityComponent getActivityComponent() {
        return mActivityComponent;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    /**
     * Show loading on full screen to wait services
     */
    @Override
    public void showLoading() {
        hideLoading();
        progressDialog = CommonUtils.showLoadingDialog(this);
    }

    /**
     * Hide loading on full screen to wait services
     */
    @Override
    public void hideLoading() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.cancel();
        }
    }

    /**
     * Show error dialog.
     * @param resTitleId
     * @param resMessageId
     */
    @Override
    public void onError(@StringRes int resTitleId, @StringRes int resMessageId) {
        showDialog(getString(resTitleId), getString(resMessageId));
    }

    /**
     * Show error dialog.
     * @param title
     * @param message
     */
    @Override
    public void onError(String title, String message) {
        showDialog(title, message);
    }

    /**
     * Show informative dialog.
     * @param title
     * @param message
     */
    private void showDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        if (!message.isEmpty())
            builder.setTitle(title);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Check if device has any kind of network
     * @return
     */
    @Override
    public boolean isNetworkConnected() {
        return NetworkUtils.isNetworkConnected(getApplicationContext());
    }

    /**
     * Hide de SIP Keyboard
     */
    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * Abstract method to setup after creation of a Activity or Fragment
     */
    protected abstract void setUp();

    @Override
    public void setToolbarTitle(@StringRes int resId) {
        setToolbarTitle(getString(resId));
    }

    @Override
    public void setToolbarTitle(String message) {
        toolbarTitle.setText(message);
    }

    /**
     * Change any component from toolbar (home button, color etc)
     * @param toolbarStyle
     */
    @Override
    public void setToolbarStyle(int toolbarStyle) {
        switch (toolbarStyle) {
            case NOW_PLAYING_STYLE:
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                getSupportActionBar().setHomeButtonEnabled(false);
                break;
            case SEARCH_STYLE:
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setHomeButtonEnabled(true);
                getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
                break;
            case DETAILS_STYLE:
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setHomeButtonEnabled(true);
                getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
                break;
        }
    }

    public Context getContext() {
        return this;
    }
}

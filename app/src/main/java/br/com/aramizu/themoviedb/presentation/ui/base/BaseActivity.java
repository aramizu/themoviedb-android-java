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

    private void setUpToolbar() {
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
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

    @Override
    public void showLoading() {
        hideLoading();
        progressDialog = CommonUtils.showLoadingDialog(this);
    }

    @Override
    public void hideLoading() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.cancel();
        }
    }

    @Override
    public void onError(@StringRes int resTitleId, @StringRes int resMessageId) {
        showDialog(getString(resTitleId), getString(resMessageId));
    }

    @Override
    public void onError(String title, String message) {
        showDialog(title, message);
    }

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

    @Override
    public boolean isNetworkConnected() {
        return NetworkUtils.isNetworkConnected(getApplicationContext());
    }

    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    protected abstract void setUp();

    @Override
    public void setToolbarTitle(@StringRes int resId) {
        setToolbarTitle(getString(resId));
    }

    @Override
    public void setToolbarTitle(String message) {
        toolbarTitle.setText(message);
    }

    public Context getContext() {
        return this;
    }
}

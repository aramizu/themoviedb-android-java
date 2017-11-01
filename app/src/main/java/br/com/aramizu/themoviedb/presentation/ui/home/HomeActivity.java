package br.com.aramizu.themoviedb.presentation.ui.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.ncapdevi.fragnav.FragNavController;
import com.ncapdevi.fragnav.FragNavTransactionOptions;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.com.aramizu.themoviedb.R;
import br.com.aramizu.themoviedb.presentation.ui.base.BaseActivity;
import br.com.aramizu.themoviedb.presentation.ui.base.BaseFragment;
import br.com.aramizu.themoviedb.presentation.ui.base.FragManagerListerner;
import br.com.aramizu.themoviedb.presentation.ui.home.now_playing.NowPlayingFragment;
import br.com.aramizu.themoviedb.presentation.ui.home.search.SearchFragment;

public class HomeActivity extends BaseActivity implements FragManagerListerner, HomeMvpView {

    @Inject
    HomeMvpPresenter<HomeMvpView> presenter;

    private List<Fragment> fragments;
    private FragNavController.Builder builder;
    private FragNavController fragNavController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getActivityComponent().inject(this);
        presenter.onAttach(this);

        fragments = new ArrayList<>(1);
        builder = FragNavController.newBuilder(savedInstanceState, getSupportFragmentManager(), R.id.container);
        setUp();
    }

    @Override
    protected void setUp() {
        fragments.add(NowPlayingFragment.newInstance());

        builder.rootFragments(fragments);
        fragNavController = builder.build();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (fragNavController != null) {
            fragNavController.onSaveInstanceState(outState);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                popFragment();
                break;
            case R.id.search:
                pushFragment(SearchFragment.newInstance());
                break;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        popFragment();
    }

    @Override
    public void pushFragment(BaseFragment fragment) {
        FragNavTransactionOptions.Builder transactionOptionsBuilder = FragNavTransactionOptions.newBuilder();
        FragNavTransactionOptions transactionOptions = transactionOptionsBuilder.build();
        fragNavController.pushFragment(fragment, transactionOptions);
    }

    @Override
    public void popFragment() {
        FragNavTransactionOptions.Builder transactionOptionsBuilder = FragNavTransactionOptions.newBuilder();
        FragNavTransactionOptions transactionOptions = transactionOptionsBuilder.build();

        if (fragNavController.getCurrentStack().size() > 1) {
            fragNavController.popFragment(transactionOptions);
        } else {
            // Clear the movies list from cache. The list stays on cache only on execution time
            presenter.clearMoviesFromPreferences();
            finish();
        }
    }
}

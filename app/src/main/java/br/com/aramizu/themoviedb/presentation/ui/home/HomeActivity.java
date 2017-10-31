package br.com.aramizu.themoviedb.presentation.ui.home;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import javax.inject.Inject;

import br.com.aramizu.themoviedb.R;
import br.com.aramizu.themoviedb.data.model.NowPlayingResponseModel;
import br.com.aramizu.themoviedb.presentation.ui.base.BaseActivity;
import br.com.aramizu.themoviedb.presentation.ui.custom.MoviesAdapter;
import br.com.aramizu.themoviedb.presentation.ui.custom.OnLoadMoreListenerInterface;
import butterknife.BindView;

public class HomeActivity extends BaseActivity implements HomeMvpView {

    @BindView(R.id.lst_movies)
    RecyclerView lstMovies;

    @Inject
    HomeMvpPresenter<HomeMvpView> presenter;

    private MoviesAdapter moviesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getActivityComponent().inject(this);
        presenter.onAttach(this);
        setUp();
    }

    @Override
    protected void setUp() {
        setToolbarTitle(R.string.home_title);

        moviesAdapter = new MoviesAdapter(this);

        moviesAdapter.setOnLoadMoreInterfaceListener(new OnLoadMoreListenerInterface() {
            @Override
            public void onLoadMore() {
                presenter.getNowPlayingMovies(moviesAdapter.getCurrentPage());
            }
        });

        lstMovies.setAdapter(moviesAdapter);
        lstMovies.setItemAnimator(new DefaultItemAnimator());
        lstMovies.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onResume() {
        super.onResume();
        if (moviesAdapter != null) moviesAdapter.clearMovies();
        presenter.getNowPlayingMovies(moviesAdapter.getCurrentPage());
    }

    @Override
    public void showNowPlayingMovies(NowPlayingResponseModel nowPlayingMovies) {
        moviesAdapter.addMovies(nowPlayingMovies.getResults());
        moviesAdapter.setCurrentPage(nowPlayingMovies.getPage());
        moviesAdapter.setTotalPages(nowPlayingMovies.getTotal_pages());
    }
}

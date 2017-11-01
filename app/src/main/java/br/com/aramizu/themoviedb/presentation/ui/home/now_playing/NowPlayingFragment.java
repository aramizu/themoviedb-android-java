package br.com.aramizu.themoviedb.presentation.ui.home.now_playing;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import javax.inject.Inject;

import br.com.aramizu.themoviedb.R;
import br.com.aramizu.themoviedb.data.model.Movie;
import br.com.aramizu.themoviedb.data.model.MoviesResponseModel;
import br.com.aramizu.themoviedb.presentation.ui.base.BaseActivity;
import br.com.aramizu.themoviedb.presentation.ui.base.BaseFragment;
import br.com.aramizu.themoviedb.presentation.ui.custom.MoviesAdapter;
import br.com.aramizu.themoviedb.presentation.ui.custom.OnLoadMoreListenerInterface;
import br.com.aramizu.themoviedb.presentation.ui.home.HomeActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

public class NowPlayingFragment extends BaseFragment implements NowPlayingMvpView {

    @BindView(R.id.lst_movies)
    RecyclerView lstMovies;

    @Inject
    NowPlayingMvpPresenter<NowPlayingMvpView> presenter;

    private MoviesAdapter moviesAdapter;
    private HomeActivity mParentActivity;

    private static final double AVERAGE_VOTE = 5.0;

    public static NowPlayingFragment newInstance() {
        Bundle args = new Bundle();
        NowPlayingFragment fragment = new NowPlayingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParentActivity = (HomeActivity) getBaseActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivityComponent().inject(this);
        presenter.onAttach(this);

        View view = inflater.inflate(R.layout.fragment_now_playing, container, false);

        ButterKnife.bind(this, view);

        setUp();

        return view;
    }

    @Override
    protected void setUp() {
        setToolbarTitle(R.string.now_playing_title);
        setToolbarStyle(BaseActivity.NOW_PLAYING_STYLE);

        moviesAdapter = new MoviesAdapter(mParentActivity);

        moviesAdapter.setOnLoadMoreInterfaceListener(new OnLoadMoreListenerInterface() {
            @Override
            public void onLoadMore() {
                if (isNetworkConnected())
                    presenter.getNowPlayingMovies(AVERAGE_VOTE, moviesAdapter.getCurrentPage());
            }
        });

        lstMovies.setAdapter(moviesAdapter);
        lstMovies.setItemAnimator(new DefaultItemAnimator());
        lstMovies.setLayoutManager(new LinearLayoutManager(mParentActivity));
    }

    @Override
    public void onResume() {
        super.onResume();
        List<Movie> movies = presenter.getMoviesFromPreference();

        if (movies != null && movies.size() > 0)
            moviesAdapter.addMovies(movies);
        else {
            if (moviesAdapter != null) moviesAdapter.clearMovies();
            if (isNetworkConnected())
                presenter.getNowPlayingMovies(AVERAGE_VOTE, moviesAdapter.getCurrentPage());
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void showNowPlayingMovies(MoviesResponseModel nowPlayingMovies) {
        moviesAdapter.addMovies(nowPlayingMovies.getResults());
        moviesAdapter.setCurrentPage(nowPlayingMovies.getPage());
        moviesAdapter.setTotalPages(nowPlayingMovies.getTotal_pages());
        presenter.saveMoviesOnPreferences(nowPlayingMovies.getResults());
    }
}

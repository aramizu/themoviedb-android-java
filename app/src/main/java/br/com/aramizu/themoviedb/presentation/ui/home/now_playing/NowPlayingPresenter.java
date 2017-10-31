package br.com.aramizu.themoviedb.presentation.ui.home.now_playing;

import java.util.ArrayList;

import javax.inject.Inject;

import br.com.aramizu.themoviedb.data.DataManager;
import br.com.aramizu.themoviedb.data.model.Movie;
import br.com.aramizu.themoviedb.data.model.MoviesResponseModel;
import br.com.aramizu.themoviedb.data.network.APIConstants;
import br.com.aramizu.themoviedb.presentation.ui.base.BasePresenter;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class NowPlayingPresenter<V extends NowPlayingMvpView> extends BasePresenter<V>
        implements NowPlayingMvpPresenter<V> {

    @Inject
    NowPlayingPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public void getNowPlayingMovies(final double averageVote, int page) {
        if (page == APIConstants.INITIAL_PAGINATION_INDEX)
            getMvpView().showLoading();

        getCompositeDisposable().add(getDataManager().getNowPlayingMovies(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Consumer<MoviesResponseModel>() {
                            @Override
                            public void accept(MoviesResponseModel nowPlayingMovies) throws Exception {
                                V view = getMvpView();
                                view.hideLoading();

                                filterMoviesByAverageVote(averageVote, nowPlayingMovies);

                                getMvpView().showNowPlayingMovies(nowPlayingMovies);
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                V view = getMvpView();
                                view.hideLoading();

                                handlerThrowableError(view, throwable);
                            }
                        }
                )
        );
    }

    private void filterMoviesByAverageVote(double averageVote, MoviesResponseModel nowPlayingMovies) {
        ArrayList<Movie> filteredMovies = new ArrayList<>(nowPlayingMovies.getResults());

        for (Movie movie : nowPlayingMovies.getResults()) {
            if (movie.getVote_average() < averageVote) {
                filteredMovies.remove(movie);
            }
        }

        nowPlayingMovies.setResults(filteredMovies);
    }
}

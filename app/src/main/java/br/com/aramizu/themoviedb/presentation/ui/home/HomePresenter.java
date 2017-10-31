package br.com.aramizu.themoviedb.presentation.ui.home;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.com.aramizu.themoviedb.data.DataManager;
import br.com.aramizu.themoviedb.data.model.Movie;
import br.com.aramizu.themoviedb.data.model.NowPlayingResponseModel;
import br.com.aramizu.themoviedb.presentation.ui.base.BasePresenter;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class HomePresenter<V extends HomeMvpView> extends BasePresenter<V>
        implements HomeMvpPresenter<V> {

    @Inject
    HomePresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public void getNowPlayingMovies(int page) {
        if (page == 0)
            getMvpView().showLoading();

        getCompositeDisposable().add(getDataManager().getNowPlayingMovies(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Consumer<NowPlayingResponseModel>() {
                            @Override
                            public void accept(NowPlayingResponseModel nowPlayingMovies) throws Exception {
                                V view = getMvpView();
                                view.hideLoading();

                                filterMoviesByAverageVote(nowPlayingMovies);

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

    private void filterMoviesByAverageVote(NowPlayingResponseModel nowPlayingMovies) {
        ArrayList<Movie> filteredMovies = new ArrayList<>(nowPlayingMovies.getResults());

        for (Movie movie : nowPlayingMovies.getResults()) {
            if (movie.getVote_average() < 5.0) {
                filteredMovies.remove(movie);
            }
        }

        nowPlayingMovies.setResults(filteredMovies);
    }
}
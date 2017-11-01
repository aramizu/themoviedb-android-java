package br.com.aramizu.themoviedb.presentation.ui.home.now_playing;

import java.util.List;

import br.com.aramizu.themoviedb.data.model.Movie;
import br.com.aramizu.themoviedb.presentation.internal.di.PerActivity;
import br.com.aramizu.themoviedb.presentation.ui.base.MvpPresenter;

@PerActivity
public interface NowPlayingMvpPresenter<V extends NowPlayingMvpView> extends MvpPresenter<V> {
    void getNowPlayingMovies(double averageVote, int page);
    List<Movie> getMoviesFromPreference();
    void saveMoviesOnPreferences(List<Movie> results);
}

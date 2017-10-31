package br.com.aramizu.themoviedb.presentation.ui.home.now_playing;

import br.com.aramizu.themoviedb.data.model.MoviesResponseModel;
import br.com.aramizu.themoviedb.presentation.ui.base.MvpView;

public interface NowPlayingMvpView extends MvpView {
    void showNowPlayingMovies(MoviesResponseModel nowPlayingMovies);
}

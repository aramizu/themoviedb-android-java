package br.com.aramizu.themoviedb.presentation.ui.home.search;

import br.com.aramizu.themoviedb.data.model.MoviesResponseModel;
import br.com.aramizu.themoviedb.presentation.ui.base.MvpView;

public interface SearchMvpView extends MvpView {
    void showNowPlayingMovies(MoviesResponseModel nowPlayingMovies);
}

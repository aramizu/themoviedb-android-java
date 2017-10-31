package br.com.aramizu.themoviedb.presentation.ui.home;

import br.com.aramizu.themoviedb.data.model.NowPlayingResponseModel;
import br.com.aramizu.themoviedb.presentation.ui.base.MvpView;

public interface HomeMvpView extends MvpView {
    void showNowPlayingMovies(NowPlayingResponseModel nowPlayingMovies);
}

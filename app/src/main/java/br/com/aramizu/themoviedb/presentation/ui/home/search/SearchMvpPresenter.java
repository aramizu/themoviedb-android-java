package br.com.aramizu.themoviedb.presentation.ui.home.search;

import br.com.aramizu.themoviedb.presentation.internal.di.PerActivity;
import br.com.aramizu.themoviedb.presentation.ui.base.MvpPresenter;

@PerActivity
public interface SearchMvpPresenter<V extends SearchMvpView> extends MvpPresenter<V> {
    void getMoviesByTitle(String title, int page);
}

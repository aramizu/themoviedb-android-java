package br.com.aramizu.themoviedb.presentation.ui.home;

import br.com.aramizu.themoviedb.presentation.internal.di.PerActivity;
import br.com.aramizu.themoviedb.presentation.ui.base.MvpPresenter;

@PerActivity
public interface HomeMvpPresenter<V extends HomeMvpView> extends MvpPresenter<V> {
    void clearMoviesFromPreferences();
}

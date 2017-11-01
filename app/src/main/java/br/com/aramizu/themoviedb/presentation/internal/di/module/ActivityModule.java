package br.com.aramizu.themoviedb.presentation.internal.di.module;

import android.app.Activity;
import android.content.Context;

import br.com.aramizu.themoviedb.presentation.internal.di.ActivityContext;
import br.com.aramizu.themoviedb.presentation.internal.di.PerActivity;
import br.com.aramizu.themoviedb.presentation.ui.home.HomeMvpPresenter;
import br.com.aramizu.themoviedb.presentation.ui.home.HomeMvpView;
import br.com.aramizu.themoviedb.presentation.ui.home.HomePresenter;
import br.com.aramizu.themoviedb.presentation.ui.home.now_playing.NowPlayingMvpPresenter;
import br.com.aramizu.themoviedb.presentation.ui.home.now_playing.NowPlayingMvpView;
import br.com.aramizu.themoviedb.presentation.ui.home.now_playing.NowPlayingPresenter;
import br.com.aramizu.themoviedb.presentation.ui.home.search.SearchMvpPresenter;
import br.com.aramizu.themoviedb.presentation.ui.home.search.SearchMvpView;
import br.com.aramizu.themoviedb.presentation.ui.home.search.SearchPresenter;
import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

@Module
public class ActivityModule {

    private Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @ActivityContext
    Context provideContext() {
        return activity;
    }

    @Provides
    Activity provideActivity() {
        return activity;
    }

    @Provides
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

    @Provides
    @PerActivity
    HomeMvpPresenter<HomeMvpView> provideHomePresenter(HomePresenter<HomeMvpView> presenter) {
        return presenter;
    }

    @Provides
    NowPlayingMvpPresenter<NowPlayingMvpView> provideNowPlayingPresenter(NowPlayingPresenter<NowPlayingMvpView> presenter) {
        return presenter;
    }

    @Provides
    SearchMvpPresenter<SearchMvpView> provideSearchPresenter(SearchPresenter<SearchMvpView> presenter) {
        return presenter;
    }
}

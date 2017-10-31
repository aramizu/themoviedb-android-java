package br.com.aramizu.themoviedb.presentation.internal.di.module;

import android.app.Activity;
import android.content.Context;

import br.com.aramizu.themoviedb.presentation.internal.di.ActivityContext;
import br.com.aramizu.themoviedb.presentation.internal.di.PerActivity;
import br.com.aramizu.themoviedb.presentation.ui.home.HomeMvpPresenter;
import br.com.aramizu.themoviedb.presentation.ui.home.HomeMvpView;
import br.com.aramizu.themoviedb.presentation.ui.home.HomePresenter;
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
    HomeMvpPresenter<HomeMvpView> provideLoginPresenter(HomePresenter<HomeMvpView> presenter) {
        return presenter;
    }
}

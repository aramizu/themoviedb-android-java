package br.com.aramizu.themoviedb.presentation.internal.di.components;

import br.com.aramizu.themoviedb.presentation.internal.di.PerActivity;
import br.com.aramizu.themoviedb.presentation.internal.di.module.ActivityModule;
import br.com.aramizu.themoviedb.presentation.ui.home.now_playing.NowPlayingFragment;
import br.com.aramizu.themoviedb.presentation.ui.home.search.SearchFragment;
import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(NowPlayingFragment fragment);
    void inject(SearchFragment fragment);
}

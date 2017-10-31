package br.com.aramizu.themoviedb.presentation.internal.di.components;

import br.com.aramizu.themoviedb.presentation.internal.di.PerActivity;
import br.com.aramizu.themoviedb.presentation.internal.di.module.ActivityModule;
import br.com.aramizu.themoviedb.presentation.ui.home.HomeActivity;
import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(HomeActivity homeActivity);
}

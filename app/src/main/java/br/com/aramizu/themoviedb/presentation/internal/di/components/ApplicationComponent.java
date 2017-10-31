package br.com.aramizu.themoviedb.presentation.internal.di.components;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import br.com.aramizu.themoviedb.config.AndroidApplication;
import br.com.aramizu.themoviedb.data.DataManager;
import br.com.aramizu.themoviedb.presentation.internal.di.ApplicationContext;
import br.com.aramizu.themoviedb.presentation.internal.di.module.ApplicationModule;
import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(AndroidApplication app);

    @ApplicationContext
    Context context();

    Application application();

    DataManager getDataManager();
}

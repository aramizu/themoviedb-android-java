package br.com.aramizu.themoviedb.config;

import android.app.Application;
import android.content.Context;

import br.com.aramizu.themoviedb.presentation.internal.di.components.ApplicationComponent;
import br.com.aramizu.themoviedb.presentation.internal.di.components.DaggerApplicationComponent;
import br.com.aramizu.themoviedb.presentation.internal.di.module.ApplicationModule;

/**
 * Application to set main components
 *  - Dagger.
 */
public class AndroidApplication extends Application {

    private ApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        configureInjectionDependency();
    }

    private void configureInjectionDependency() {
        component = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
        component.inject(this);
    }

    public static AndroidApplication get(Context context) {
        return (AndroidApplication) context.getApplicationContext();
    }

    public ApplicationComponent getComponent() {
        return component;
    }
}
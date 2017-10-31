package br.com.aramizu.themoviedb.presentation.internal.di.module;

import android.app.Application;
import android.content.Context;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import br.com.aramizu.themoviedb.BuildConfig;
import br.com.aramizu.themoviedb.config.AndroidApplication;
import br.com.aramizu.themoviedb.data.AppDataManager;
import br.com.aramizu.themoviedb.data.DataManager;
import br.com.aramizu.themoviedb.data.network.APIConstants;
import br.com.aramizu.themoviedb.data.network.ApiHelper;
import br.com.aramizu.themoviedb.data.network.AppApiHelper;
import br.com.aramizu.themoviedb.data.prefs.AppPreferencesHelper;
import br.com.aramizu.themoviedb.data.prefs.PreferencesHelper;
import br.com.aramizu.themoviedb.presentation.internal.di.ApplicationContext;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@Singleton
public class ApplicationModule {

    private AndroidApplication application;

    public ApplicationModule(AndroidApplication application) {
        this.application = application;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return application;
    }

    @Provides
    Application provideApplication() {
        return application;
    }

    @Provides
    @Singleton
    DataManager provideDataManager(AppDataManager appDataManager) {
        return appDataManager;
    }

    @Provides
    @Singleton
    ApiHelper provideApiHelper(AppApiHelper appApiHelper) {
        return appApiHelper;
    }

    @Provides
    @Singleton
    PreferencesHelper providePreferencesHelper(AppPreferencesHelper appPreferencesHelper) {
        return appPreferencesHelper;
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit() {
        OkHttpClient.Builder builder;
        builder = new OkHttpClient.Builder()
                .connectTimeout(2, TimeUnit.MINUTES)
                .writeTimeout(2, TimeUnit.MINUTES)
                .readTimeout(2, TimeUnit.MINUTES);
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(httpLoggingInterceptor);

        return new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.themoviedb.org/")
                .client(builder.build())
                .build();
    }
}

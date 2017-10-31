package br.com.aramizu.themoviedb.data;

import android.content.Context;

import javax.inject.Inject;

import br.com.aramizu.themoviedb.data.model.ErrorResponse;
import br.com.aramizu.themoviedb.data.model.NowPlayingResponseModel;
import br.com.aramizu.themoviedb.data.network.ApiHelper;
import br.com.aramizu.themoviedb.data.prefs.PreferencesHelper;
import br.com.aramizu.themoviedb.presentation.internal.di.ApplicationContext;
import io.reactivex.Observable;
import retrofit2.Response;

public class AppDataManager implements DataManager {

    private final Context mContext;
    private final PreferencesHelper mPreferencesHelper;
    private final ApiHelper mApiHelper;

    @Inject
    AppDataManager(@ApplicationContext Context context,
                          PreferencesHelper preferencesHelper,
                          ApiHelper apiHelper) {
        mContext = context;
        mPreferencesHelper = preferencesHelper;
        mApiHelper = apiHelper;
    }

    @Override
    public Observable<NowPlayingResponseModel> getNowPlayingMovies(int page) {
        return mApiHelper.getNowPlayingMovies(page);
    }

    @Override
    public ErrorResponse parseError(Response<?> response) {
        return mApiHelper.parseError(response);
    }
}

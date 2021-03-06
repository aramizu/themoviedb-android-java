package br.com.aramizu.themoviedb.data;

import android.content.Context;

import java.util.List;

import javax.inject.Inject;

import br.com.aramizu.themoviedb.data.model.ErrorResponse;
import br.com.aramizu.themoviedb.data.model.Movie;
import br.com.aramizu.themoviedb.data.model.MoviesResponseModel;
import br.com.aramizu.themoviedb.data.network.ApiHelper;
import br.com.aramizu.themoviedb.data.prefs.PreferencesHelper;
import br.com.aramizu.themoviedb.presentation.internal.di.ApplicationContext;
import io.reactivex.Observable;
import retrofit2.Response;

/**
 * Implementation of manager that implements all forms of data application (network, android preferences)
 */
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
    public Observable<MoviesResponseModel> getNowPlayingMovies(int page) {
        return mApiHelper.getNowPlayingMovies(page);
    }

    @Override
    public Observable<MoviesResponseModel> getMoviesByTitle(String query, int page) {
        return mApiHelper.getMoviesByTitle(query, page);
    }

    @Override
    public ErrorResponse parseError(Response<?> response) {
        return mApiHelper.parseError(response);
    }

    @Override
    public void saveMovies(List<Movie> movies) {
        mPreferencesHelper.saveMovies(movies);
    }

    @Override
    public List<Movie> retrieveMovies() {
        return mPreferencesHelper.retrieveMovies();
    }

    @Override
    public void clearPreferences() {
        mPreferencesHelper.clearPreferences();
    }
}

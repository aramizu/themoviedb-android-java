package br.com.aramizu.themoviedb.data.prefs;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import javax.inject.Inject;

import br.com.aramizu.themoviedb.data.model.Movie;
import br.com.aramizu.themoviedb.presentation.internal.di.ApplicationContext;

public class AppPreferencesHelper implements PreferencesHelper {

    private static final String PREF_NAME = "THE_MOVIE_DB_PREFS";

    private static final String KEY_MOVIES = "KEY_MOVIES";

    private final SharedPreferences mPrefs;

    @Inject
    AppPreferencesHelper(@ApplicationContext Context context) {
        mPrefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    private void setSerializableObject(String keyValue, List<Movie> object) {
        Gson gson = new Gson();
        String json = gson.toJson(object);
        mPrefs.edit()
                .putString(keyValue, json)
                .commit();
    }

    private Object getSerializableMoviesList(String keyValue) {
        Gson gson = new Gson();
        String json = mPrefs.getString(keyValue, "");

        Type type = new TypeToken<List<Movie>>() {}.getType();

        return gson.fromJson(json, type);
    }

    @Override
    public void saveMovies(List<Movie> movies) {
        setSerializableObject(KEY_MOVIES, movies);
    }

    @Override
    public List<Movie> retrieveMovies() {
        return (List<Movie>) getSerializableMoviesList(KEY_MOVIES);
    }

    @Override
    public void clearPreferences() {
        mPrefs.edit().clear().commit();
    }
}

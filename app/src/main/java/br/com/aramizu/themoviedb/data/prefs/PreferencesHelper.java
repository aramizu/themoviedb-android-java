package br.com.aramizu.themoviedb.data.prefs;

import java.util.List;

import br.com.aramizu.themoviedb.data.model.Movie;

public interface PreferencesHelper {
    void saveMovies(List<Movie> movies);
    List<Movie> retrieveMovies();
    void clearPreferences();
}

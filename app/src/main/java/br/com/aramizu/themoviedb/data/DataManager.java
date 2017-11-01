package br.com.aramizu.themoviedb.data;

import br.com.aramizu.themoviedb.data.network.ApiHelper;
import br.com.aramizu.themoviedb.data.prefs.PreferencesHelper;

/**
 * Manager that implements all forms of data application (network, android preferences)
 */
public interface DataManager extends PreferencesHelper, ApiHelper {
}

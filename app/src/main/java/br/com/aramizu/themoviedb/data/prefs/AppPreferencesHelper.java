package br.com.aramizu.themoviedb.data.prefs;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;

import javax.inject.Inject;

import br.com.aramizu.themoviedb.presentation.internal.di.ApplicationContext;

public class AppPreferencesHelper implements PreferencesHelper {

    private static final String PREF_NAME = "BREADBOARD_PREFS";

    private final SharedPreferences mPrefs;

    @Inject
    AppPreferencesHelper(@ApplicationContext Context context) {
        mPrefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    private void setSerializableObject(String keyValue, Object object) {
        Gson gson = new Gson();
        String json = gson.toJson(object);
        mPrefs.edit()
                .putString(keyValue, json)
                .commit();
    }

    private Object getSerializableObject(String keyValue, String className) {
        Gson gson = new Gson();
        String json = mPrefs.getString(keyValue, "");
        Object object = null;
        try {
            object = gson.fromJson(json, Class.forName(className));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Log.e("PREFS", "Erro ao serializar objeto", e);
        }
        return object;
    }
}

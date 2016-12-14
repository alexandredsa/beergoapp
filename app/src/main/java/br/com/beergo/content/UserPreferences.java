package br.com.beergo.content;

import android.content.SharedPreferences;

import com.google.gson.Gson;

import br.com.beergo.domain.dto.UserDetail;

/**
 * Created by alexandre on 13/12/16.
 */

public class UserPreferences {
    private SharedPreferences sharedPreferences;
    public static final String KEY_USER_DETAIL = "USER_DETAIL_PREFFS";
    private Gson gson;

    public UserPreferences(SharedPreferences sharedPreferences, Gson gson) {
        this.sharedPreferences = sharedPreferences;
        this.gson = gson;
    }

    public void saveUser(UserDetail userDetail) {
        saveObject(KEY_USER_DETAIL, userDetail);
    }

    public UserDetail getUser() {
        return retrieveObject(KEY_USER_DETAIL, UserDetail.class);
    }

    private <T> T retrieveObject(String key, Class clazz) {
        String jsonObj = sharedPreferences.getString(key, null);
        if (jsonObj == null)
            return null;

        return (T) gson.fromJson(jsonObj, clazz);
    }

    private void saveObject(String key, Object obj) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, gson.toJson(obj));
        editor.commit();
    }
}

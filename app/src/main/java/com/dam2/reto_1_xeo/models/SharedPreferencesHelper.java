package com.dam2.reto_1_xeo.models;

import android.content.Context;
import android.content.SharedPreferences;

import com.dam2.reto_1_xeo.models.LoginResponse.UserData;

public class SharedPreferencesHelper {

    private static final String PREFS_NAME = "user_data";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_USER_NAME = "user_name";
    private static final String KEY_USER_EMAIL = "user_email";
    private static final String KEY_USER_PHONE = "user_phone";
    private static final String KEY_USER_COUNTRY = "user_country";
    private static final String KEY_USER_CITY = "user_city";
    private static final String KEY_USER_PROVINCE = "user_province";

    public static void saveUserData(Context context, UserData userData) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_USER_ID, userData.getId());
        editor.putString(KEY_USER_NAME, userData.getNombre());
        editor.putString(KEY_USER_EMAIL, userData.getCorreo());
        editor.putString(KEY_USER_PHONE, userData.getTelefono());
        editor.putString(KEY_USER_COUNTRY, userData.getPais());
        editor.putString(KEY_USER_CITY, userData.getCiudad());
        editor.putString(KEY_USER_PROVINCE, userData.getProvincia());
        editor.apply();
    }

    public static UserData getUserData(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        int id = sharedPreferences.getInt(KEY_USER_ID, -1);
        String name = sharedPreferences.getString(KEY_USER_NAME, null);
        String email = sharedPreferences.getString(KEY_USER_EMAIL, null);
        String phone = sharedPreferences.getString(KEY_USER_PHONE, null);
        String country = sharedPreferences.getString(KEY_USER_COUNTRY, null);
        String city = sharedPreferences.getString(KEY_USER_CITY, null);
        String province = sharedPreferences.getString(KEY_USER_PROVINCE, null);

        if (id == -1 || name == null || email == null) {
            return null;  // No user data saved
        }

        UserData userData = new UserData();
        userData.setId(id);
        userData.setNombre(name);
        userData.setCorreo(email);
        userData.setTelefono(phone);
        userData.setPais(country);
        userData.setCiudad(city);
        userData.setProvincia(province);
        return userData;
    }

    public static void clearUserData(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
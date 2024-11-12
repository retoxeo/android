package com.dam2.reto_1_xeo.utils;

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
    private static final String KEY_USER_CALLE = "user_calle";
    private static final String KEY_USER_NUMERO = "user_numero";
    private static final String KEY_USER_CP = "user_cp";
    private static final String KEY_USER_FOTO = "user_foto";
    private static final String KEY_USER_APELLIDO1 = "user_apellido1";
    private static final String KEY_USER_APELLIDO2 = "user_apellido2";

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
        editor.putString(KEY_USER_CALLE, userData.getCalle());
        editor.putInt(KEY_USER_NUMERO, userData.getNumero());
        editor.putInt(KEY_USER_CP, userData.getCp());
        editor.putString(KEY_USER_FOTO, userData.getFoto());
        editor.putString(KEY_USER_APELLIDO1, userData.getApellido1());
        editor.putString(KEY_USER_APELLIDO2, userData.getApellido2());

        editor.apply();
    }

    public static UserData getUserData(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        int id = sharedPreferences.getInt(KEY_USER_ID, -1);
        String nombre = sharedPreferences.getString(KEY_USER_NAME, null);
        String correo = sharedPreferences.getString(KEY_USER_EMAIL, null);
        String telefono = sharedPreferences.getString(KEY_USER_PHONE, null);
        String pais = sharedPreferences.getString(KEY_USER_COUNTRY, null);
        String ciudad = sharedPreferences.getString(KEY_USER_CITY, null);
        String provincia = sharedPreferences.getString(KEY_USER_PROVINCE, null);
        String calle = sharedPreferences.getString(KEY_USER_CALLE, null);
        int numero = sharedPreferences.getInt(KEY_USER_NUMERO, -1);
        int cp = sharedPreferences.getInt(KEY_USER_CP, -1);
        String foto = sharedPreferences.getString(KEY_USER_FOTO, null);
        String apellido1 = sharedPreferences.getString(KEY_USER_APELLIDO1, null);
        String apellido2 = sharedPreferences.getString(KEY_USER_APELLIDO2, null);

        if (id == -1 || nombre == null || correo == null) {
            return null;
        }

        UserData userData = new UserData();
        userData.setId(id);
        userData.setNombre(nombre);
        userData.setCorreo(correo);
        userData.setTelefono(telefono);
        userData.setPais(pais);
        userData.setCiudad(ciudad);
        userData.setProvincia(provincia);
        userData.setCalle(calle);
        userData.setNumero(numero);
        userData.setCp(cp);
        userData.setFoto(foto);
        userData.setApellido1(apellido1);
        userData.setApellido2(apellido2);

        return userData;
    }

    public static void clearUserData(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
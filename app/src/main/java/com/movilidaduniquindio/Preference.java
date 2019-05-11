package com.movilidaduniquindio;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

public class  Preference {

    public static void removeShare(Context context, String preferenceFileName, String objectKey){
        SharedPreferences settings = context.getSharedPreferences(preferenceFileName, Context.MODE_PRIVATE);
        settings.edit().remove(objectKey).commit();
    }


    public static void saveToSharedPreference(Context contex, String preferenceFileName, String objectKey, String objectValue){
        SharedPreferences.Editor editor = contex.getSharedPreferences(preferenceFileName, 0).edit();
        editor.putString(objectKey, objectValue);
        editor.apply();
    }

    public static Object getFromPreference(Context context, String preferenceFileName, String objectKey) {
        SharedPreferences prefs = context.getSharedPreferences(preferenceFileName, 0);
        if (prefs != null) {
            String name = prefs.getString(objectKey, null);
            return name;
        }
        return null;
    }

    /**
     * GUARDAR SHARED PREFERENCE
     * @param contex
     * @param preferenceFileName
     * @param serializedObjectKey
     * @param object
     */
    public static void saveObjectToSharedPreference(Context contex, String preferenceFileName, String serializedObjectKey, Object object){
        SharedPreferences sharedPreferences = contex.getSharedPreferences(preferenceFileName, 0);
        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
        final Gson gson = new Gson();
        String serializedObject = gson.toJson(object);
        sharedPreferencesEditor.putString(serializedObjectKey, serializedObject);
        sharedPreferencesEditor.apply();
    }

    /**
     * OBTENER SHARED PREFERENCE
     * @param context
     * @param preferenceFileName
     * @param preferenceKey
     * @param classType
     * @param <GenericClass>
     * @return
     */
    public static <GenericClass> GenericClass getSavedObjectFromPreference(Context context, String preferenceFileName, String preferenceKey, Class<GenericClass> classType) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(preferenceFileName, 0);
        if (sharedPreferences.contains(preferenceKey)) {
            final Gson gson = new Gson();
            return gson.fromJson(sharedPreferences.getString(preferenceKey, ""), classType);
        }
        return null;
    }
}
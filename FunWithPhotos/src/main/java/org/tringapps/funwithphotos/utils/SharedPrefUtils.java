
package org.tringapps.funwithphotos.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * Created by tring-ajay on 7/30/14
 */
public final class SharedPrefUtils {

    private SharedPrefUtils() {
    }

    private static SharedPreferences getSharedPreference(Context context) {
        return context.getSharedPreferences("SharedPrefUtils", Context.MODE_PRIVATE);
    }

    public static void updateSharedPref(Context context, String key, String value) {
        SharedPreferences preferences = getSharedPreference(context);

        if (preferences != null) {
            Editor editor = preferences.edit();
            editor.putString(key, value);
            editor.commit();
        }
    }

    public static String getString(Context context, String key) {
        SharedPreferences preferences = getSharedPreference(context);

        if (preferences != null) {
            return preferences.getString(key, null);
        }

        return null;
    }
}

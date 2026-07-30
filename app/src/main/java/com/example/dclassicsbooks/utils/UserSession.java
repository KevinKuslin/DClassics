package com.example.dclassicsbooks.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.TextView;

import com.example.dclassicsbooks.R;

public final class UserSession {
    private static final String PREFS_NAME = "dclassics_session";
    private static final String KEY_USERNAME = "username";

    private UserSession() { }

    public static void saveUsername(Context context, String username) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .edit()
                .putString(KEY_USERNAME, username)
                .apply();
    }

    public static String getUsername(Context context) {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .getString(KEY_USERNAME, "Reeders");
    }

    public static void applyUsername(android.app.Activity activity) {
        String username = getUsername(activity);
        TextView homeGreeting = activity.findViewById(R.id.tvGreetingName);
        if (homeGreeting != null) homeGreeting.setText(username + "!");
        TextView drawerGreeting = activity.findViewById(R.id.tvDrawerGreeting);
        if (drawerGreeting != null) drawerGreeting.setText("Hello, " + username + "!");
    }
}

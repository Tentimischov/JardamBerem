package com.baktiyar.android.jardamberem.ui;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;

import java.util.Locale;

public class ConstantsJava {

    public static void setLocale(Context context, Locale locale) {
        Configuration config = context.getResources().getConfiguration();
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
            config.setLocale(locale);
        else
            config.locale = locale;
        config.locale = locale;
        context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            Resources resources = context.getApplicationContext().getResources();
            Configuration configuration = resources.getConfiguration();
            configuration.setLocale(locale);
            context.getApplicationContext().createConfigurationContext(configuration);
        } else {
            Locale.setDefault(locale);
            config.locale = locale;
            context.getResources().updateConfiguration(config,
                    context.getResources().getDisplayMetrics());
        }
    }


    public static void setLocale1(Context context, Locale locale) {
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLocale(locale); // API 17+ only.
        } else {
            configuration.locale = locale;
        }
        resources.updateConfiguration(configuration, displayMetrics);
    }
}

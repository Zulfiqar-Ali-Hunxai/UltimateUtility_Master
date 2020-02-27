package com.ahmedonics.apps.ultimateutilitymaster.activities.Password_Generator;

import android.os.Build;

import java.util.concurrent.ThreadLocalRandom;

import androidx.annotation.RequiresApi;

public class PasswordGenerator {
    private static final int MIN_CODE = 33 ,MAX = 126;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static String process(int length){

        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < length; i++){
            builder.append((char) ThreadLocalRandom.current().nextInt(MIN_CODE,MAX + 1)) ;
        }
        return builder.toString();

    }
}

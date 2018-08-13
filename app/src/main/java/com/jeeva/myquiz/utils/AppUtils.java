package com.jeeva.myquiz.utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Jeevanandham on 13/08/2018
 */
public class AppUtils {

    public static void dismissKeyboard(Context context, View focusedView) {
        if (null != focusedView) {
            InputMethodManager imm = (InputMethodManager) context
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(focusedView.getWindowToken(), 0);
            }
        }
    }

    public static String readStringFromAsset(Context context, String filePath) {
        try {
            InputStream inputStream = context.getAssets().open(filePath);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            return new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
package com.speex.papayaview.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by Byron on 2018/8/12.
 */

public class ScreenSizeUtil {

    public static void getWidthHeigth1(Context context, ScreenSizeCallback sizeCallback) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        int width = display.getWidth();
        int height = display.getHeight();
        if (sizeCallback != null) {
            sizeCallback.onSize(width, height);
        }
    }

    public static void getWidthHeigth2(Activity activity, ScreenSizeCallback sizeCallback) {
        WindowManager windowManager = activity.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        int width = display.getWidth();
        int height = display.getHeight();
        if (sizeCallback != null) {
            sizeCallback.onSize(width, height);
        }
    }

    public static void getWidthHeigth3(Activity activity, ScreenSizeCallback sizeCallback) {
        WindowManager windowManager = activity.getWindowManager();
        DisplayMetrics metrics = new DisplayMetrics();
        Display display = windowManager.getDefaultDisplay();
        display.getMetrics(metrics);
        int width = metrics.widthPixels;
        int heigth = metrics.heightPixels;
        if (sizeCallback != null) {
            sizeCallback.onSize(width, heigth);
        }
    }

    public static void getWidthHeigth4(Activity activity, ScreenSizeCallback sizeCallback) {
        Resources resources = activity.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float density = metrics.density;
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        if (sizeCallback != null) {
            sizeCallback.onSize(width, height);
        }
    }

    public interface ScreenSizeCallback {
        public void onSize(int width, int heigth);
    }
}

package com.example.administrator.dt.util;

import android.content.Context;
import android.graphics.BitmapFactory;


public class BitmapUtils {
    public static float getBitmapRatio(Context context, int resourceId) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(context.getResources(), resourceId, options);
        return options.outHeight/(float)options.outWidth;
    }
}

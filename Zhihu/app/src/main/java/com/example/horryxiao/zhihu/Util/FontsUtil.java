package com.example.horryxiao.zhihu.Util;

import android.content.Context;
import android.graphics.Typeface;

import java.lang.reflect.Field;


public final class FontsUtil {

    public static void setFonts(Context context,String rawfonts,String newfonts){
         final Typeface typeface=Typeface.createFromAsset(context.getAssets(),newfonts);
         replaceTypeface(rawfonts,typeface);
    }
    public static Typeface getTypeface(Context context, String newfonts){
        return Typeface.createFromAsset(context.getAssets(),newfonts);
    }
    protected  static void replaceTypeface(String rawfonts,final Typeface typeface){
        try {
            Field typeField=Typeface.class.getDeclaredField(rawfonts);
            typeField.setAccessible(true);
            typeField.set(null,typeface);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}

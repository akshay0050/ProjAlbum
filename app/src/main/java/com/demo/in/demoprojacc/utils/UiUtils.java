package com.demo.in.demoprojacc.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Akshay on 10/30/2018.
 */

public class UiUtils {

    public static void showToast(Context context,String msg){
        Toast.makeText(context,msg, Toast.LENGTH_SHORT).show();
    }
}

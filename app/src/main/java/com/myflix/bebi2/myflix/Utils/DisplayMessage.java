package com.myflix.bebi2.myflix.Utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by bebi2 on 12/19/2015.
 */
public class DisplayMessage {

    public static void DisplayMessageLong(Context context, String message){
        Toast.makeText(context,message,Toast.LENGTH_LONG).show();
    }
    public static void DisplayMessageShort(Context context, String message){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }
}

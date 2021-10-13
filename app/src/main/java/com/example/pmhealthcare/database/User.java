package com.example.pmhealthcare.database;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.example.pmhealthcare.Utils.FirebaseFirestoreUtils;

public class User {

    static SharedPreferences sharedPreferences;
    static SharedPreferences.Editor editor;

    public static final String DATABASE_NAME="UserData";

    public static String getName(Context context) {

        sharedPreferences=context.getSharedPreferences(DATABASE_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString("UserName","");
    }

    public static void setUserDp(Context context, String imageUri){
        sharedPreferences=context.getSharedPreferences(DATABASE_NAME,Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();

        editor.putString("UserProfilePic",imageUri);
        editor.apply();
    }

    public static Uri getUserDp(Context context){
        sharedPreferences=context.getSharedPreferences(DATABASE_NAME,Context.MODE_PRIVATE);

        return Uri.parse(sharedPreferences.getString("UserProfilePic",""));
    }
}

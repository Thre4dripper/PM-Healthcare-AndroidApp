package com.example.pmhealthcare.database;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.example.pmhealthcare.Utils.SharedPreferenceUtils;

public class User {

    static SharedPreferences sharedPreferences;
    static SharedPreferences.Editor editor;

    public static final String DATABASE_NAME="UserData";

    public static void setName(Context context, String name) {
        sharedPreferences=context.getSharedPreferences(DATABASE_NAME,Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();

        editor.putString("UserName",name);
        editor.apply();
    }

    public static String getName(Context context) {

        sharedPreferences=context.getSharedPreferences(DATABASE_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString("UserName","");
    }


    public static void setFatherName(Context context,String fatherName) {
        sharedPreferences=context.getSharedPreferences(DATABASE_NAME,Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();

        editor.putString("UserFatherName",fatherName);
        editor.apply();
    }

    public static String getFatherName(Context context) {

        sharedPreferences=context.getSharedPreferences(DATABASE_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString("UserFatherName","");
    }


    public static void setMotherName(Context context,String motherName) {
        sharedPreferences=context.getSharedPreferences(DATABASE_NAME,Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();

        editor.putString("UserMotherName",motherName);
        editor.apply();
    }

    public static String getMotherName(Context context) {

        sharedPreferences=context.getSharedPreferences(DATABASE_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString("UserMotherName","");
    }


    public static void setDOB(Context context, Spinner year, Spinner month, Spinner date) {
        sharedPreferences=context.getSharedPreferences(DATABASE_NAME,Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();

        String DOB = SharedPreferenceUtils.getDateOfBirth(context,year,month,date);
        editor.putString("DateOfBirth",DOB);
        editor.apply();
    }

    public static String getDOB(Context context) {

        sharedPreferences=context.getSharedPreferences(DATABASE_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString("DateOfBirth","");

    }


    public static void setGender(Context context, RadioButton r1, RadioButton r2, RadioButton r3) {
        sharedPreferences=context.getSharedPreferences(DATABASE_NAME,Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();

        String gender=SharedPreferenceUtils.getGender(context,r1,r2,r3);
        editor.putString("Gender",gender);
        editor.apply();
    }

    public static String getGender(Context context) {
        sharedPreferences=context.getSharedPreferences(DATABASE_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString("Gender","");
    }


    public static void setState(Context context,int statePosition) {
        sharedPreferences=context.getSharedPreferences(DATABASE_NAME,Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();

        editor.putInt("State",statePosition);
        editor.apply();
    }

    public static int getState(Context context) {
        sharedPreferences=context.getSharedPreferences(DATABASE_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getInt("State",0);
    }


    public static void setDistrict(Context context,int districtPosition) {
        sharedPreferences=context.getSharedPreferences(DATABASE_NAME,Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();

        editor.putInt("District",districtPosition);
        editor.apply();
    }

    public static int getDistrict(Context context) {
        sharedPreferences=context.getSharedPreferences(DATABASE_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getInt("District",0);
    }


    public static void setPinCode(Context context,int pinCode) {
        sharedPreferences=context.getSharedPreferences(DATABASE_NAME,Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();

        editor.putInt("PinCode",pinCode);
        editor.apply();
    }

    public static int getPinCode(Context context) {
        sharedPreferences=context.getSharedPreferences(DATABASE_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getInt("PinCode",0);
    }


    public static void setAddress(Context context,String address) {
        sharedPreferences=context.getSharedPreferences(DATABASE_NAME,Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();

        editor.putString("Address",address);
        editor.apply();
    }

    public static String getAddress(Context context) {
        sharedPreferences=context.getSharedPreferences(DATABASE_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString("Address","");
    }


    public static void setDoctor(Context context,boolean isDoctor){
        sharedPreferences=context.getSharedPreferences(DATABASE_NAME,Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();

        editor.putBoolean("IsDoctor",isDoctor);
        editor.apply();
    }

    public static boolean getDoctor(Context context){
        sharedPreferences=context.getSharedPreferences(DATABASE_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("IsDoctor",false);
    }
}

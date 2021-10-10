package com.example.pmhealthcare.Utils;

import android.content.Context;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Spinner;

import java.util.Arrays;

public class SharedPreferenceUtils {

    public static String getDateOfBirth(Context context, Spinner year, Spinner month, Spinner date){
        String DOB="";
        DOB+=date.getSelectedItem().toString();
        DOB=DOB+"-"+month.getSelectedItemPosition();
        DOB=DOB+"-"+year.getSelectedItem().toString();
        return DOB;
    }

    public static String getGender(Context context,RadioButton r1,RadioButton r2,RadioButton r3){
        String gender="";
        if(r1.isChecked())
            gender="Male";
        else if(r2.isChecked())
            gender="Male";
        else if(r3.isChecked())
            gender="Male";
        return gender;
    }

    public static String getDiseasesArrayString(Context context, CheckBox[] checkBoxes){
        int[] diseaseArray={0,0,0,0,0,0,0};
        StringBuilder diseases= new StringBuilder();

        for(int i=0;i<7;i++){
            if(checkBoxes[i].isChecked())
                diseaseArray[i]=1;
            diseases.append(diseaseArray[i]);
        }

            return diseases.toString();
    }
}

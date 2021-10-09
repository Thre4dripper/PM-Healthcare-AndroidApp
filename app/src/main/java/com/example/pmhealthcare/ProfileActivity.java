package com.example.pmhealthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class ProfileActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    TextInputEditText nameTextView,fatherNameTextView,motherNameTextView;
    Spinner yearSpinner,monthSpinner,dateSpinner;



    String[] years=new String[122];
    String[] months={"JAN","FEB","MAR","APR","MAY","JUNE","JULY","AUG","SEPT","OCT","NOV","DEC"};
    String[] dates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        nameTextView=findViewById(R.id.name_field);
        fatherNameTextView=findViewById(R.id.father_name_field);
        motherNameTextView=findViewById(R.id.mother_name_field);
        yearSpinner=findViewById(R.id.year_spinner);
        monthSpinner=findViewById(R.id.month_spinner);
        dateSpinner=findViewById(R.id.date_spinner);

        setDateSpinners();
    }

    public void setDateSpinners(){



        for (int i=0;i<=121;i++){
            years[i]=String.valueOf(2021-i);
        }
        ArrayAdapter<String> yearsAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,years);
        yearSpinner.setAdapter(yearsAdapter);

        ArrayAdapter<String> monthAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,months);
        monthSpinner.setAdapter(monthAdapter);

        yearSpinner.setOnItemSelectedListener(this);
        monthSpinner.setOnItemSelectedListener(this);
        dateSpinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if(parent==monthSpinner || parent==yearSpinner){
            int number=monthSpinner.getSelectedItemPosition();

            switch (months[number]) {
                case "JAN":
                case "MAR":
                case "MAY":
                case "JULY":
                case "AUG":
                case "OCT":
                case "DEC":
                    number = 31;
                    break;
                case "APR":
                case "JUNE":
                case "SEPT":
                case "NOV":
                    number = 30;
                    break;
                case "FEB":
                    number= Integer.parseInt(yearSpinner.getSelectedItem().toString());
                    if(number%100==0 && number%400==0)
                        number=29;
                    else if(number%4==0)
                        number=29;
                    else number=28;

                    break;
            }

            dates=new String[number];
            for(int i=0;i<number;i++)
                dates[i]=String.valueOf(i+1);

            ArrayAdapter<String> datesAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,dates);
            dateSpinner.setAdapter(datesAdapter);

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
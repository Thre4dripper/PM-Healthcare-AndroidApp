package com.example.pmhealthcare.Activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pmhealthcare.R;
import com.example.pmhealthcare.Utils.JsonParser;
import com.example.pmhealthcare.database.User;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Arrays;

public class ProfileActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    TextInputEditText nameField, heightField, weightField, fatherNameField, motherNameField, pinCodeField, addressField;
    Spinner yearSpinner, monthSpinner, dateSpinner, stateSpinner, districtSpinner;
    MaterialButton saveButton;
    RadioButton maleRadio, femaleRadio, othersRadio;

    TextView genderErrorLabel;
    CheckBox[] specialDiseases;


    String[] years = new String[122];
    String[] months = {"JAN", "FEB", "MAR", "APR", "MAY", "JUNE", "JULY", "AUG", "SEPT", "OCT", "NOV", "DEC"};
    String[] dates;

    int dateSelection = 0, districtSelection = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        nameField = findViewById(R.id.name_field);
        heightField = findViewById(R.id.height_field);
        weightField = findViewById(R.id.weight_field);

        yearSpinner = findViewById(R.id.year_spinner);
        monthSpinner = findViewById(R.id.month_spinner);
        dateSpinner = findViewById(R.id.date_spinner);

        genderErrorLabel = findViewById(R.id.gender_error_label);
        maleRadio = findViewById(R.id.male_radio);
        femaleRadio = findViewById(R.id.female_radio);
        othersRadio = findViewById(R.id.others_radio);

        fatherNameField = findViewById(R.id.father_name_field);
        motherNameField = findViewById(R.id.mother_name_field);

        stateSpinner = findViewById(R.id.state_spinner);
        districtSpinner = findViewById(R.id.district_spinner);

        pinCodeField = findViewById(R.id.pincode_text_field);
        addressField = findViewById(R.id.full_address);

        specialDiseases = new CheckBox[7];
        specialDiseases[0] = findViewById(R.id.weak_eyesight_checkbox);
        specialDiseases[1] = findViewById(R.id.diabetic_checkbox);
        specialDiseases[2] = findViewById(R.id.respiratory_disease_checkbox);
        specialDiseases[3] = findViewById(R.id.alzheimer_checkbox);
        specialDiseases[4] = findViewById(R.id.heart_disease_checkbox);
        specialDiseases[5] = findViewById(R.id.overweight_checkbox);
        specialDiseases[6] = findViewById(R.id.handicapped_checkbox);

        saveButton = findViewById(R.id.save_button);

        getUserInformation();
        saveButton.setOnClickListener(this);
    }

    /**
     * ========================================== METHODS FOR DOB SPINNERS =========================================
     **/
    public void setDateSpinners(int year, int month, int date) {

        for (int i = 0; i <= 121; i++) {
            years[i] = String.valueOf(2021 - i);
        }
        ArrayAdapter<String> yearsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, years);
        yearSpinner.setAdapter(yearsAdapter);
        yearSpinner.setSelection(2021 - year);

        ArrayAdapter<String> monthAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, months);
        monthSpinner.setAdapter(monthAdapter);
        monthSpinner.setSelection(month);

        yearSpinner.setOnItemSelectedListener(this);
        monthSpinner.setOnItemSelectedListener(this);
        dateSpinner.setOnItemSelectedListener(this);

        dateSelection = date - 1;
    }

    public void setGenderRadios(String gender) {

        switch (gender) {
            case "Male":
                maleRadio.setChecked(true);
                break;
            case "Female":
                femaleRadio.setChecked(true);
                break;
            case "Others":
                othersRadio.setChecked(true);
                break;
        }

    }

    /**
     * ==================================== METHODS FOR STATE AND DISTRICTS SPINNERS ================================
     **/
    public void setStateSpinner(int state, int district) {
        String[] states = JsonParser.getStatesFromJSON(this);

        ArrayAdapter<String> statesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, states);
        stateSpinner.setAdapter(statesAdapter);

        stateSpinner.setOnItemSelectedListener(this);
        stateSpinner.setSelection(state);

        districtSelection = district;

    }

    public void setSpecialDiseases(int[] diseases) {

        for (int i = 0; i < 7; i++)
            if (diseases[i] == 1)
                specialDiseases[i].setChecked(true);
    }

    /**
     * ========================================== SPINNERS ON ITEM SELECTED =========================================
     **/
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if (parent == monthSpinner || parent == yearSpinner) {
            int number = monthSpinner.getSelectedItemPosition();

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
                    number = Integer.parseInt(yearSpinner.getSelectedItem().toString());
                    if (number % 100 == 0 && number % 400 == 0)
                        number = 29;
                    else if (number % 4 == 0)
                        number = 29;
                    else number = 28;

                    break;
            }

            dates = new String[number];
            for (int i = 0; i < number; i++)
                dates[i] = String.valueOf(i + 1);

            ArrayAdapter<String> datesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, dates);
            dateSpinner.setAdapter(datesAdapter);
            dateSpinner.setSelection(dateSelection);

        } else if (parent == stateSpinner) {
            String[] districts = JsonParser.getDistrictsFromJSON(this, stateSpinner.getSelectedItem().toString().trim());

            ArrayAdapter<String> districtAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, districts);
            districtSpinner.setAdapter(districtAdapter);
            districtSpinner.setSelection(districtSelection);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        if (v == saveButton) {

            boolean allClear = true;

            if (nameField.getText().toString().equals("")) {
                allClear = false;
                nameField.setError("Required");
            } else nameField.setError(null);

            if (!(maleRadio.isChecked() || femaleRadio.isChecked() || othersRadio.isChecked())) {
                allClear = false;
                genderErrorLabel.setVisibility(View.VISIBLE);
            } else genderErrorLabel.setVisibility(View.GONE);

            if (fatherNameField.getText().toString().equals("")) {
                allClear = false;
                fatherNameField.setError("Required");
            } else fatherNameField.setError(null);

            if (motherNameField.getText().toString().equals("")) {
                allClear = false;
                motherNameField.setError("Required");
            } else motherNameField.setError(null);

            if (pinCodeField.getText().toString().equals("")) {
                allClear = false;
                pinCodeField.setError("Required");
            }
            else if(pinCodeField.getText().toString().length()<6)
            {
                allClear=false;
                pinCodeField.setError("Invalid PinCode");
            }
            else
                pinCodeField.setError(null);

            if (addressField.getText().toString().equals("")) {
                allClear = false;
                addressField.setError("Required");
            } else
                addressField.setError(null);

            if (allClear)
                new MaterialAlertDialogBuilder(this)
                        .setTitle("Save")
                        .setMessage("Do you want to Save current information")
                        .setPositiveButton("Yes", (dialogInterface, i) -> {
                            saveUserInformation();

                        })
                        .setNegativeButton("No", (dialogInterface, i) -> {
                            dialogInterface.dismiss();
                        })
                        .show();
        }
    }

    public void saveUserInformation() {

        User.setName(this, nameField.getText().toString());
        User.setHeight(this,Integer.parseInt(heightField.getText().toString()));
        User.setWeight(this,Integer.parseInt(weightField.getText().toString()));
        User.setDOB(this, yearSpinner, monthSpinner, dateSpinner);
        User.setGender(this, maleRadio, femaleRadio, othersRadio);

        User.setFatherName(this, fatherNameField.getText().toString());
        User.setMotherName(this, motherNameField.getText().toString());

        User.setState(this, stateSpinner.getSelectedItemPosition());
        User.setDistrict(this, districtSpinner.getSelectedItemPosition());
        User.setPinCode(this, Integer.parseInt(pinCodeField.getText().toString()));
        User.setAddress(this, addressField.getText().toString());

        User.setSpecialDiseases(this,specialDiseases);

        User.setDoctor(this, false);

        Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();

    }

    public void getUserInformation() {
        //personal details
        String name = User.getName(this);

        //date of birth
        int year = 2021;
        int month = 0;
        int date = 0;
        String DOB = User.getDOB(this);

        if (!TextUtils.isEmpty(DOB)) {
            String[] format = DOB.split("-");

            year = Integer.parseInt(format[2]);
            month = Integer.parseInt(format[1]);
            date = Integer.parseInt(format[0]);
        }

        //height and weight
        int height = User.getHeight(this);
        int weight = User.getWeight(this);

        //gender
        String gender = User.getGender(this);

        //family details
        String fatherName = User.getFatherName(this);
        String motherName = User.getMotherName(this);


        //state and district
        int statePosition = User.getState(this);
        int districtPosition = User.getDistrict(this);

        //pinCode and address
        int pinCode = User.getPinCode(this);
        String address = User.getAddress(this);

        //special diseases
        String specialDiseases = User.getSpecialDiseases(this);
        int[] diseasesArray = {0,0,0,0,0,0,0};

        for (int i = 0; i < 7; i++)
            diseasesArray[i] = specialDiseases.charAt(i)-48;


        //setting information from shared preferences
        nameField.setText(name);

        if (height != 0)
            heightField.setText(height + "");
        if (weight != 0)
            weightField.setText(weight + "");

        setDateSpinners(year, month, date);
        setGenderRadios(gender);

        fatherNameField.setText(fatherName);
        motherNameField.setText(motherName);

        setStateSpinner(statePosition, districtPosition);

        if (pinCode != 0)
            pinCodeField.setText(pinCode + "");

        addressField.setText(address);

        setSpecialDiseases(diseasesArray);
    }
}
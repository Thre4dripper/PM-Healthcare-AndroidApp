package com.example.pmhealthcare.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.example.pmhealthcare.Fragments.DigidocFragment;
import com.example.pmhealthcare.R;
import com.example.pmhealthcare.database.DoctorDetails;
import com.google.android.material.button.MaterialButton;

import de.hdodenhof.circleimageview.CircleImageView;

public class DoctorVisit extends AppCompatActivity implements LocationListener {

    CircleImageView doctorDp;
    TextView doctorName;
    TextView qualification, institution, speciality, status, district, address;
    MaterialButton visitButton;

    DoctorDetails doctorDetails;
    String[] userLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_visit);

        doctorDp = findViewById(R.id.visited_doctor_dp);
        doctorName = findViewById(R.id.visited_doctor_name);

        qualification = findViewById(R.id.visited_doctor_qualifications);
        institution = findViewById(R.id.visited_doctor_institution);
        speciality = findViewById(R.id.visited_doctor_speciality);
        status = findViewById(R.id.visited_doctor_status);
        district = findViewById(R.id.visited_doctor_district);
        address = findViewById(R.id.visited_doctor_address);
        visitButton = findViewById(R.id.visit_button);


        int doctorIndex = getIntent().getIntExtra("currentDigiDoctorIndex", -1);
        doctorDetails = DigidocFragment.doctorDetailsList.get(doctorIndex);

        InitUIElements();
    }

    public void InitUIElements() {
        Glide.with(this).load(doctorDetails.getDoctorDp()).into(doctorDp);
        doctorName.setText(doctorDetails.getDoctorName());

        qualification.setText(doctorDetails.getQualifications());
        institution.setText(doctorDetails.getInstitution());
        speciality.setText(doctorDetails.getSpecialization());
        status.setText(doctorDetails.getStatus());
        district.setText(doctorDetails.getDistrict());
        address.setText(doctorDetails.getAddress());

        //getUserLocation();
        visit();
    }

    public void getUserLocation(){
        //checking Location Permission
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            // Location permission granted
            Toast.makeText(this,"Permission granted.",Toast.LENGTH_SHORT).show();

            //getting Location
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                LocationManager lm=(LocationManager)getSystemService(LOCATION_SERVICE);
                lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,  this);
                Toast.makeText(this,"User Location Accessed",Toast.LENGTH_SHORT).show();
            }

            //getting location permission
        }else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                ActivityCompat.requestPermissions(DoctorVisit.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        451);
            }
            // Location permission not granted
            Toast.makeText(this,"Permission not granted.",Toast.LENGTH_SHORT).show();
        }
    }

    public void visit() {
        visitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String[] doctorLocation=doctorDetails.getLocationCoordinates().split(",");

                Uri gmmIntentUri = Uri.parse("geo:28.582075,78.573508");

                // Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                // Make the Intent explicit by setting the Google Maps package
                mapIntent.setPackage("com.google.android.apps.maps");

                // Attempt to start an activity that can handle the Intent
                startActivity(mapIntent);
            }
        });
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        this.userLocation=new String[2];
        this.userLocation[0]= String.valueOf(location.getLatitude());
        this.userLocation[1]= String.valueOf(location.getLongitude());
    }
}
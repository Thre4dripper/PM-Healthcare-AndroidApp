package com.example.pmhealthcare.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.pmhealthcare.Adapters.DegreesRecyclerAdapter;
import com.example.pmhealthcare.Fragments.ProfileFragment;
import com.example.pmhealthcare.Networking.Firebase;
import com.example.pmhealthcare.R;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class DoctorRegistration extends AppCompatActivity implements DegreesRecyclerAdapter.degreesOnClickInterface,
        View.OnClickListener,LocationListener {

    CircleImageView userDp;
    RecyclerView recyclerView;
    DegreesRecyclerAdapter recyclerAdapter;

    TextInputEditText qualification,institution,specialization,regNumber,helpline,experience;
    MaterialButton addDegreeButton,registerButton;

    LinearLayout locationButton;

    List<String> degreesList=new ArrayList<>();
    double[] location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_registration);

        userDp=findViewById(R.id.doctor_registration_Dp);
        recyclerView=findViewById(R.id.degrees_recycler_view);

        qualification =findViewById(R.id.qualification_edit_text);
        addDegreeButton=findViewById(R.id.add_degree_button);

        institution=findViewById(R.id.institution_edit_text);
        specialization=findViewById(R.id.specialization_edit_text);
        regNumber=findViewById(R.id.registration_number);
        helpline=findViewById(R.id.help_line_number);
        experience=findViewById(R.id.experience_edit_text);
        locationButton=findViewById(R.id.location_button);

        registerButton=findViewById(R.id.doctor_register_button);


        InitUIElements();
    }

    public void InitUIElements(){

        setUserDp();

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,LinearLayoutManager.HORIZONTAL));

        recyclerAdapter=new DegreesRecyclerAdapter(this,degreesList,this);
        recyclerView.setAdapter(recyclerAdapter);

        addDegreeButton.setOnClickListener(this);
        registerButton.setOnClickListener(this);
        locationButton.setOnClickListener(this);
    }

    /**=========================================== METHOD FOR REMOVE DEGREES ============================================**/
    @Override
    public void degreeOnClick(int position) {
        degreesList.remove(position);
        recyclerAdapter.notifyItemRemoved(position);
    }

    /**=========================================== ONCLICK METHOD ============================================**/
    @Override
    public void onClick(View v) {
        if(v==addDegreeButton) {
            if (!qualification.getText().toString().isEmpty()) {
                degreesList.add(0, qualification.getText().toString());
                qualification.setText("");
                recyclerAdapter.notifyItemInserted(0);
                recyclerView.scrollToPosition(0);
                qualification.setError(null);
            }
            else qualification.setError("Required");
        }
        else if(v==registerButton){
            Register();
        }
        else if(v==locationButton){
            //checking Location Permission
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                // Location permission granted
                Toast.makeText(this,"Permission granted.",Toast.LENGTH_SHORT).show();

                //getting Location
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    LocationManager lm=(LocationManager)getSystemService(LOCATION_SERVICE);
                    lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,  this);
                    Toast.makeText(this,"Location Pinned.",Toast.LENGTH_SHORT).show();
                }

                //getting location permission
            }else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    ActivityCompat.requestPermissions(DoctorRegistration.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            451);
                }
                // Location permission not granted
                Toast.makeText(this,"Permission not granted.",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
            this.location=new double[2];
            this.location[0]=location.getLatitude();
            this.location[1]=location.getLongitude();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK && requestCode==451){
            Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
        }
        else Toast.makeText(this, "Permission not Granted", Toast.LENGTH_SHORT).show();
    }

    /**===================================  METHOD FOR CHECKING VALIDITY OF ALL THE FIELDS ==================================**/
    public void Register(){

        boolean allClear=true;

        if(degreesList.size()==0){
            allClear=false;
            qualification.setError("Required");
        }
        else qualification.setError(null);

        if(institution.getText().toString().equals("")){
            allClear=false;
            institution.setError("Required");
        }
        else institution.setError(null);

        if(specialization.getText().toString().equals("")){
            allClear=false;
            specialization.setError("Required");
        }
        else specialization.setError(null);

        if(regNumber.getText().toString().equals("")){
            allClear=false;
            regNumber.setError("Required");
        }
        else regNumber.setError(null);

        if(helpline.getText().toString().equals("")){
            allClear=false;
            helpline.setError("Required");
        }
        else helpline.setError(null);

        if(experience.getText().toString().equals("")){
            allClear=false;
            experience.setError("Required");
        }
        else experience.setError(null);

        if(location==null){
            allClear=false;
            Toast.makeText(this,"Please Grant Location Permission",Toast.LENGTH_SHORT).show();
        }

        if(allClear)
        new MaterialAlertDialogBuilder(this)
                .setTitle("Register")
                .setMessage("Do you want proceed for Registration")
                .setPositiveButton("yes", (dialogInterface, i) -> {
                    SendRegisterMail();
                })
                .setNegativeButton("No", (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                })
                .show();
    }

    /**========================================= METHOD FOR CREATING REGISTRATION MAIL =========================================**/
    public void SendRegisterMail(){
        StringBuilder builder=new StringBuilder();
        builder.append("Degrees-");

        for(int i=0;i<degreesList.size();i++){
            builder.append(degreesList.get(i));

            if(i!=degreesList.size()-1)
                builder.append(",");
        }

        builder.append("\n");

        builder.append("Institution-")
                .append(institution.getText()).append("\n");

        builder.append("Specialization-")
                .append(specialization.getText()).append("\n");

        builder.append("Registration Number-")
                .append(regNumber.getText()).append("\n");

        builder.append("Helpline Number-")
                .append(helpline.getText()).append("\n");

        builder.append("Experience-")
                .append(experience.getText()).append("\n");

        builder.append("location-")
                .append(location[0]).append(",").append(location[1]);


        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.putExtra(Intent.EXTRA_EMAIL,new String[]{"ijlalahmad845@gmail.com"});
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Digi Doctor Registration");
        intent.putExtra(Intent.EXTRA_TEXT, builder.toString());

        startActivity(intent);
    }

    public void setUserDp() {

        if(ProfileFragment.userDpUri!=null)
            Glide.with(this).load(ProfileFragment.userDpUri).into(userDp);

    }

}
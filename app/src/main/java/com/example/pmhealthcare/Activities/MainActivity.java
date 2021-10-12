package com.example.pmhealthcare.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.pmhealthcare.Fragments.DigidocFragment;
import com.example.pmhealthcare.Fragments.HFRFragment;
import com.example.pmhealthcare.Fragments.ProfileFragment;
import com.example.pmhealthcare.Fragments.RecordsFragment;
import com.example.pmhealthcare.Fragments.TeleMedicFragment;
import com.example.pmhealthcare.Networking.Firebase;
import com.example.pmhealthcare.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new ProfileFragment()).commit();

        bottomNavigationView.setSelectedItemId(R.id.profile);

        InitFragments();
        AUTH();
    }

    /**====================================== METHOD FOR TRIGGERING FIREBASE AUTH ===================================**/
    public void AUTH() {

        Intent intent = Firebase.LoadFirebaseAUTHUI();
        if (intent != null)
            startActivityForResult(intent, Firebase.FIREBASE_REQUEST_CODE);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(!Firebase.FireBaseAUTHUIRESULT(this,resultCode,requestCode))
            finish();

    }

    public void InitFragments() {

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment fragment = null;
                if (item.getItemId() == R.id.hfr)
                    fragment = new HFRFragment();

                else if (item.getItemId() == R.id.digiDoc)
                    fragment = new DigidocFragment();

                else if (item.getItemId() == R.id.records)
                    fragment = new RecordsFragment();

                else if (item.getItemId() == R.id.tele)
                    fragment = new TeleMedicFragment();

                else if (item.getItemId() == R.id.profile)
                    fragment = new ProfileFragment();


                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, fragment).commit();
                return true;
            }
        });

    }

}
package com.example.pmhealthcare.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.pmhealthcare.Fragments.DigidocFragment;
import com.example.pmhealthcare.Fragments.HFRFragment;
import com.example.pmhealthcare.Fragments.ProfileFragment;
import com.example.pmhealthcare.Fragments.RecordsFragment;
import com.example.pmhealthcare.Fragments.TeleMedicFragment;
import com.example.pmhealthcare.R;
import com.firebase.ui.auth.AuthUI;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView=findViewById(R.id.bottomNavigationView);

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,new ProfileFragment()).commit();

        bottomNavigationView.setSelectedItemId(R.id.profile);

        InitFragments();
        AUTH();
       // System.out.println(FirebaseAuth.getInstance().getCurrentUser().getUid());
    }

    public void AUTH(){

        List<AuthUI.IdpConfig> providers= Arrays.asList(
                new AuthUI.IdpConfig.PhoneBuilder().build()
        );
        if(FirebaseAuth.getInstance().getCurrentUser()==null){
            Intent intent=AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(providers)
                    .build();

            startActivityForResult(intent,100);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK && requestCode==100){
            FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
            System.out.println(user.getUid());
            if(user.getMetadata().getCreationTimestamp()==user.getMetadata().getLastSignInTimestamp())
            Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, "Welcome Back", Toast.LENGTH_SHORT).show();
        }
        else {
           // Toast.makeText(this, "not logged in", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    public void InitFragments(){

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment fragment=null;
                if(item.getItemId()==R.id.hfr)
                    fragment=new HFRFragment();

                else if(item.getItemId()==R.id.digiDoc)
                    fragment=new DigidocFragment();

                else if(item.getItemId()==R.id.records)
                    fragment=new RecordsFragment();

                else if(item.getItemId()==R.id.tele)
                    fragment=new TeleMedicFragment();

                else if(item.getItemId()==R.id.profile)
                    fragment=new ProfileFragment();


                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,fragment).commit();
                return true;
            }
        });

    }

}
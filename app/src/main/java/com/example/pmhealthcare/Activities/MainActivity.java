package com.example.pmhealthcare.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.pmhealthcare.Fragments.DigidocFragment;
import com.example.pmhealthcare.Fragments.HFRFragment;
import com.example.pmhealthcare.Fragments.ProfileFragment;
import com.example.pmhealthcare.Fragments.RecordsFragment;
import com.example.pmhealthcare.Fragments.TeleMedicFragment;
import com.example.pmhealthcare.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

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
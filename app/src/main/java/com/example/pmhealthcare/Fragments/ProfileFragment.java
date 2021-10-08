package com.example.pmhealthcare.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.pmhealthcare.Activities.MainActivity;
import com.example.pmhealthcare.R;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;


public class ProfileFragment extends Fragment {

    CardView signOutBtn;
    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_profile, container, false);

        signOutBtn=view.findViewById(R.id.sign_out_button);


        setSignOutBtn();

        return view;
    }

    public void setSignOutBtn(){

        signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuthUI.getInstance().signOut(getActivity());
                Toast.makeText(getActivity(), "Signed Out", Toast.LENGTH_SHORT).show();
                getActivity().finish();
            }
        });

    }
}
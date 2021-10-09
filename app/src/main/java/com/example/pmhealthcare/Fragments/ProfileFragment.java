package com.example.pmhealthcare.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.pmhealthcare.ProfileActivity;
import com.example.pmhealthcare.R;
import com.firebase.ui.auth.AuthUI;
import com.google.android.material.button.MaterialButton;


public class ProfileFragment extends Fragment implements View.OnClickListener {

    CardView signOutBtn;
    MaterialButton editProfileButton, viewProfileButton;
    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_profile, container, false);

        signOutBtn=view.findViewById(R.id.sign_out_button);
        editProfileButton =view.findViewById(R.id.edit_profile_button);
        viewProfileButton =view.findViewById(R.id.view_profile_button);


        InitUIElements();


        return view;
    }

    public void InitUIElements(){

        signOutBtn.setOnClickListener(this);
        editProfileButton.setOnClickListener(this);
        viewProfileButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view==signOutBtn){
            AuthUI.getInstance().signOut(getActivity());
            Toast.makeText(getActivity(), "Signed Out", Toast.LENGTH_SHORT).show();
            getActivity().finish();
        }
        else if(view==editProfileButton){
            startActivity(new Intent(getActivity(), ProfileActivity.class));
        }
    }
}
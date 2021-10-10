package com.example.pmhealthcare.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.pmhealthcare.Activities.ProfileActivity;
import com.example.pmhealthcare.R;
import com.firebase.ui.auth.AuthUI;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;


public class ProfileFragment extends Fragment implements View.OnClickListener {

    CardView signOutBtn,feedbackBtn;
    MaterialButton editProfileButton, viewProfileButton;
    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_profile, container, false);


        editProfileButton =view.findViewById(R.id.edit_profile_button);
        viewProfileButton =view.findViewById(R.id.view_profile_button);
        signOutBtn=view.findViewById(R.id.sign_out_button);
        feedbackBtn=view.findViewById(R.id.feedback_button);


        InitUIElements();


        return view;
    }

    public void InitUIElements(){

        signOutBtn.setOnClickListener(this);
        editProfileButton.setOnClickListener(this);
        viewProfileButton.setOnClickListener(this);
        feedbackBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view==signOutBtn){

            new MaterialAlertDialogBuilder(getActivity())
                    .setTitle("Save")
                    .setMessage("Do you want to Sign Out")
                    .setPositiveButton("yes", (dialogInterface, i) -> {
                        AuthUI.getInstance().signOut(getActivity());
                        Toast.makeText(getActivity(), "Signed Out", Toast.LENGTH_SHORT).show();
                        getActivity().finish();
                    })
                    .setNegativeButton("No", (dialogInterface, i) -> {
                        dialogInterface.dismiss();
                    })
                    .show();

        }
        else if(view==editProfileButton){
            startActivity(new Intent(getActivity(), ProfileActivity.class));
        }
        else if(view==feedbackBtn){

            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.putExtra(Intent.EXTRA_EMAIL,new String[]{"ijlalahmad845@gmail.com"});
            intent.setData(Uri.parse("mailto:"));
            intent.putExtra(Intent.EXTRA_SUBJECT, "Feedback");

                startActivity(intent);
        }
    }
}
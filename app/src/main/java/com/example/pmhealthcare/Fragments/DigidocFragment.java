package com.example.pmhealthcare.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pmhealthcare.Activities.DoctorRegistration;
import com.example.pmhealthcare.Adapters.DigiDocRecyclerAdapter;
import com.example.pmhealthcare.R;


public class DigidocFragment extends Fragment implements View.OnClickListener {

    RecyclerView recyclerView;
    DigiDocRecyclerAdapter recyclerAdapter;

    CardView docRegistrationButton;

    public DigidocFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_digidoc, container, false);

        recyclerView=view.findViewById(R.id.digiDoc_recycler_view);
        docRegistrationButton =view.findViewById(R.id.register_doctor_button);

        InitUIElements();

        return view;
    }

    public void InitUIElements(){
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));

        recyclerAdapter=new DigiDocRecyclerAdapter(getContext());
        recyclerView.setAdapter(recyclerAdapter);

        OpenRegistrationPage();
    }

    public void OpenRegistrationPage(){
        docRegistrationButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v==docRegistrationButton){
            Intent intent=new Intent(getContext(), DoctorRegistration.class);
            startActivity(intent);
        }
    }
}
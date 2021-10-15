package com.example.pmhealthcare.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.pmhealthcare.Adapters.DegreesRecyclerAdapter;
import com.example.pmhealthcare.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class DoctorRegistration extends AppCompatActivity implements DegreesRecyclerAdapter.degreesOnClickInterface,
        View.OnClickListener {

    RecyclerView recyclerView;
    DegreesRecyclerAdapter recyclerAdapter;

    TextInputEditText qualificationsEditText,institutionEditText;
    MaterialButton addDegreeButton;

    List<String> degreesList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_registration);

        recyclerView=findViewById(R.id.degrees_recycler_view);
        qualificationsEditText=findViewById(R.id.qualification_edit_text);
        addDegreeButton=findViewById(R.id.add_degree_button);

        InitUIElements();
    }

    public void InitUIElements(){
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerAdapter=new DegreesRecyclerAdapter(this,degreesList,this);
        recyclerView.setAdapter(recyclerAdapter);

        addDegreeButton.setOnClickListener(this);
    }

    @Override
    public void degreeOnClick(int position) {
        degreesList.remove(position);
        recyclerAdapter.notifyItemRemoved(position);
    }

    @Override
    public void onClick(View v) {
        degreesList.add(0,qualificationsEditText.getText().toString());
        qualificationsEditText.setText("");
        recyclerAdapter.notifyItemInserted(0);
        recyclerView.scrollToPosition(0);
    }
}
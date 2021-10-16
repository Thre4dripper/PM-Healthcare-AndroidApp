package com.example.pmhealthcare.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.pmhealthcare.Activities.DoctorRegistration;
import com.example.pmhealthcare.Activities.DoctorVisit;
import com.example.pmhealthcare.Adapters.DigiDocRecyclerAdapter;
import com.example.pmhealthcare.R;
import com.example.pmhealthcare.database.DoctorDetails;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class DigidocFragment extends Fragment implements View.OnClickListener, DigiDocRecyclerAdapter.DigiDocOnClickInterface {

    private static final String TAG = "digiDoc";
    RecyclerView recyclerView;
    DigiDocRecyclerAdapter recyclerAdapter;

    CardView docRegistrationButton;

    public static List<DoctorDetails> doctorDetailsList=new ArrayList<>();

    public DigidocFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_digidoc, container, false);

        recyclerView=view.findViewById(R.id.digiDoc_recycler_view);
        docRegistrationButton =view.findViewById(R.id.doctor_registration_card_button);

        InitUIElements();

        return view;
    }

    /**==================================== METHOD FOR INITIALIZING UI ELEMENTS =================================**/
    public void InitUIElements(){
        doctorDetailsList.clear();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));

        recyclerAdapter=new DigiDocRecyclerAdapter(getContext(),doctorDetailsList,this);
        recyclerView.setAdapter(recyclerAdapter);

        OpenRegistrationPage();
        getDoctorsList();
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

    public void getDoctorsList(){
        FirebaseFirestore db=FirebaseFirestore.getInstance();

        Log.d(TAG, "getDoctorsList: ");
        db.collection("doctors").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> documentSnapshots=queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot snapshot:documentSnapshots){
                           doctorDetailsList.add(new DoctorDetails(snapshot.getString("doctorName"),
                                   Uri.parse(snapshot.getString("doctorDp")),
                                   snapshot.getString("healthID"),
                                   snapshot.getString("qualifications"),
                                   snapshot.getString("institution"),
                                   snapshot.getString("specialization"),
                                   snapshot.getLong("registrationNumber"),
                                   snapshot.getLong("helpLineNumber"),
                                   snapshot.getLong("experience"),
                                   snapshot.getString("locationCoordinates"),
                                   snapshot.getString("status"),
                                   snapshot.getString("district"),
                                   snapshot.getString("address")
                           ));
                        }
                        recyclerAdapter.notifyDataSetChanged();
                    }
                })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: failed");
            }
        });
    }

    @Override
    public void digiOnClick(int position) {
        Intent intent=new Intent(getContext(), DoctorVisit.class);
        intent.putExtra("currentDigiDoctorIndex",position);
        startActivity(intent);
    }
}
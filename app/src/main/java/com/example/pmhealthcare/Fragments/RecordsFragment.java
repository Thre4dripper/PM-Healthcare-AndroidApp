package com.example.pmhealthcare.Fragments;


import static android.app.Activity.RESULT_OK;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.pmhealthcare.Activities.TouchImageActivity;
import com.example.pmhealthcare.Adapters.RecordsRecyclerAdapter;
import com.example.pmhealthcare.Networking.Firebase;
import com.example.pmhealthcare.R;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class RecordsFragment extends Fragment implements View.OnClickListener, RecordsRecyclerAdapter.RecordsItemOnClickInterface {

    private static final String TAG = "recordsActivity";

    FloatingActionButton floatingActionButton;
    RecyclerView recyclerView;
    RecordsRecyclerAdapter recyclerAdapter;

    public static List<String> imageNames=new ArrayList<>();
    public static List<Uri> imageUriList=new ArrayList<>();
    ProgressDialog progressDialog;
    public RecordsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_records, container, false);

        floatingActionButton=view.findViewById(R.id.floatingActionButton);
        recyclerView=view.findViewById(R.id.records_recycler_view);
        progressDialog=new ProgressDialog(getContext());

        InitUIElements();
        return view;
    }

    public void InitUIElements(){
        imageUriList.clear();
        imageNames.clear();

        floatingActionButton.setOnClickListener(this);

        recyclerAdapter=new RecordsRecyclerAdapter(getContext(),imageUriList,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(recyclerAdapter);

        setImageNames();

    }


    @Override
    public void onClick(View v) {

        if(v==floatingActionButton){
            ImagePicker.with(this)
                    .galleryOnly()
                   // .crop()	    			//Crop image(Optional), Check Customization for more option
                    .compress(512)			//Final image size will be less than 1 MB(Optional)
                    //.maxResultSize(720, 720)	//Final image resolution will be less than 1080 x 1080(Optional)
                    .start(150);
            progressDialog.setTitle("Importing image");
            progressDialog.show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK  && requestCode==150){
           imageUriList.add(data.getData());
           recyclerAdapter.notifyItemInserted(imageUriList.size()-1);
            Firebase.FireBaseStoragePush(getContext(),data.getData(),imageNames);

            Log.d(TAG, imageNames+"");
        }
        progressDialog.dismiss();
    }

    @Override
    public void onCLick(int position, Uri imageUri) {
        Intent intent=new Intent(getContext(), TouchImageActivity.class);
        intent.putExtra("imageUri",imageUri);
        startActivity(intent);
    }

    public void FireBaseStoragePull(){
        FirebaseStorage firebaseStorage=FirebaseStorage.getInstance();
        StorageReference storageReference=firebaseStorage.getReference("users");

        for(int i=0;i<imageNames.size();i++){
            StorageReference imageRef=storageReference.child(imageNames.get(i));

            imageRef.getDownloadUrl()
                    .addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            imageUriList.add(uri);
                            recyclerAdapter.notifyDataSetChanged();
                        }
                    });
        }

    }

    public void setImageNames() {
        FirebaseFirestore db=FirebaseFirestore.getInstance();

        ProgressDialog progressDialog=new ProgressDialog(getContext());
        progressDialog.setTitle("Loading...");
        progressDialog.show();

        db.collection("users").document(Firebase.UNIQUE_HEALTH_ID).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.get("docs")!=null)
                        imageNames= (List<String>) documentSnapshot.get("docs");

                        if(documentSnapshot.get("docs")!=null)
                        FireBaseStoragePull();
                        progressDialog.dismiss();
                    }
                });
    }
}
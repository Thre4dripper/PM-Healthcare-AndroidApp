package com.example.pmhealthcare.Networking;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static android.content.Context.CONNECTIVITY_SERVICE;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.pmhealthcare.Activities.MainActivity;
import com.example.pmhealthcare.Fragments.RecordsFragment;
import com.example.pmhealthcare.R;
import com.example.pmhealthcare.database.RecordDetails;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Firebase{

    private static final String TAG = "firebase";
    public static final int FIREBASE_REQUEST_CODE=100;

    public static String UNIQUE_HEALTH_ID="";


    /**=================================== METHOD FOR FIREBASE AUTH UI =====================================**/
    public static Intent LoadFirebaseAUTHUI(){

        List<AuthUI.IdpConfig> providers= Arrays.asList(
                new AuthUI.IdpConfig.PhoneBuilder().build(),
                new AuthUI.IdpConfig.EmailBuilder().build()
        );

        if(FirebaseAuth.getInstance().getCurrentUser()==null){

            return AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(providers)
                    .build();
        }
        else
            UNIQUE_HEALTH_ID=FirebaseAuth.getInstance().getCurrentUser().getUid();

        return null;
    }

    /**=============================== METHOD FOR HANDLING FIREBASE AUTH RESULT =====================================**/
    public static boolean FireBaseAUTHUIRESULT(Context context,int resultCode, int requestCode){

        if (resultCode == RESULT_OK && requestCode==FIREBASE_REQUEST_CODE) {

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            UNIQUE_HEALTH_ID=user.getUid();
            if (user.getMetadata().getCreationTimestamp() == user.getMetadata().getLastSignInTimestamp())
                Toast.makeText(context, "Registration Successful", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(context, "Welcome Back", Toast.LENGTH_SHORT).show();

           return true;
        } else
            return resultCode != RESULT_CANCELED || requestCode != FIREBASE_REQUEST_CODE;
    }

    public static void setUserDP(Context context,Uri imageUri){
        FirebaseStorage firebaseStorage=FirebaseStorage.getInstance();
        StorageReference storageReference=firebaseStorage.getReference("users/"+UNIQUE_HEALTH_ID);

        StorageReference fileRef=storageReference.child("userDp");

        fileRef.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                Toast.makeText(context, "Uploaded", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**====================================== METHOD TO PUSH DATA TO FIRESTORE ===========================================**/
    public static void FireBaseFirestorePush(Context context,Map<String,Object> map){
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("users").document(UNIQUE_HEALTH_ID).set(map,SetOptions.merge());
    }

    /**====================================== METHOD TO PUSH RECORDS TO FIREBASE STORAGE =========================================**/
    public static void FireBaseStoragePush(Context context, Uri imageUri,List<RecordDetails> list){
        FirebaseStorage firebaseStorage=FirebaseStorage.getInstance();
        StorageReference storageReference=firebaseStorage.getReference("users/"+UNIQUE_HEALTH_ID);

        FirebaseFirestore db=FirebaseFirestore.getInstance();
        Map<String,Object> map=new HashMap<>();

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        if(isConnected) {
            String filename = System.currentTimeMillis() + "";
            StorageReference fileRef = storageReference.child(filename);

            fileRef.putFile(imageUri)
                    .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            Toast.makeText(context, "uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(context, "upload failed", Toast.LENGTH_SHORT).show();

                            list.remove(new RecordDetails(RecordsFragment.RecordName, filename, 0));
                            map.put("docs", list);
                            db.collection("users").document(UNIQUE_HEALTH_ID).set(map, SetOptions.merge());
                        }
                    });


            list.add(new RecordDetails(RecordsFragment.RecordName, filename, 0));
            map.put("docs", list);

            db.collection("users").document(UNIQUE_HEALTH_ID).set(map, SetOptions.merge());

            db.collection("users").document(Firebase.UNIQUE_HEALTH_ID).get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if (documentSnapshot.get("docs") != null) {
                                RecordsFragment.map = (List<Map<String, Object>>) documentSnapshot.get("docs");
                            }
                        }
                    });
        }
        else Toast.makeText(context, "No internet Connection Available", Toast.LENGTH_SHORT).show();

    }

    /**========================================== FIREBASE CLOUD STORAGE DELETE FUNCTION ====================================================**/

    public static void FireBaseStorageDelete(Context context,String deleteImageName,List<RecordDetails> list){
        FirebaseStorage firebaseStorage=FirebaseStorage.getInstance();
        StorageReference storageReference=firebaseStorage.getReference("users/"+UNIQUE_HEALTH_ID);

        StorageReference fileRef=storageReference.child(deleteImageName);
        fileRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(context, "removed", Toast.LENGTH_SHORT).show();
            }
        });

        FirebaseFirestore db=FirebaseFirestore.getInstance();

        Map<String,Object> map=new HashMap<>();

        map.put("docs",list);
        db.collection("users").document(UNIQUE_HEALTH_ID).set(map, SetOptions.merge());

    }
}

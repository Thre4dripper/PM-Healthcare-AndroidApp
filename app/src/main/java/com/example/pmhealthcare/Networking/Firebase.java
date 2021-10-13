package com.example.pmhealthcare.Networking;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Firebase{

    private static final String TAG = "firebase";
    public static final int FIREBASE_REQUEST_CODE=100;

    public static String UNIQUE_HEALTH_ID="";
    static Map<String,Object> resultMap;


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

    /**====================================== METHOD TO PUSH DATA TO FIRESTORE ===========================================**/
    public static void FireBaseFirestorePush(Context context,Map<String,Object> map){
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("users").document(UNIQUE_HEALTH_ID).set(map);
    }

    public static void FireBaseStoragePush(Context context, Uri imageUri){
        FirebaseStorage firebaseStorage=FirebaseStorage.getInstance();
        StorageReference storageReference=firebaseStorage.getReference("users");
        StorageReference fileRef=storageReference.child(System.currentTimeMillis()+"");

        ProgressDialog progressDialog=new ProgressDialog(context);
        progressDialog.setTitle("Uploading");
        progressDialog.show();

        fileRef.putFile(imageUri)
                .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        Toast.makeText(context, "uploaded", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                            double progress=(100.0 * snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                            progressDialog.setMessage((int)progress+"%");
                    }
                });


    }
}

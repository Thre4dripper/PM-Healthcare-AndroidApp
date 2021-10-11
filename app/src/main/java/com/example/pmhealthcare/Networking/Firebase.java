package com.example.pmhealthcare.Networking;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class Firebase {

    public static final int FIREBASE_REQUEST_CODE=100;

    /**=================================== METHOD FOR FIREBASE AUTH UI =====================================**/
    public static Intent LoadFirebaseAUTHUI(){

        List<AuthUI.IdpConfig> providers= Arrays.asList(
                new AuthUI.IdpConfig.PhoneBuilder().build()
        );

        if(FirebaseAuth.getInstance().getCurrentUser()==null){
            Intent intent=AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(providers)
                    .build();

            return intent;
        }
        return null;
    }

    /**=============================== METHOD FOR HANDLING FIREBASE AUTH RESULT =====================================**/
    public static boolean FireBaseAUTHUIRESULT(Context context,int resultCode, int requestCode){

        if (resultCode == RESULT_OK && requestCode==FIREBASE_REQUEST_CODE) {

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            System.out.println(user.getUid());
            if (user.getMetadata().getCreationTimestamp() == user.getMetadata().getLastSignInTimestamp())
                Toast.makeText(context, "Registration Successful", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(context, "Welcome Back", Toast.LENGTH_SHORT).show();

           return true;
        } else
            return resultCode != RESULT_CANCELED || requestCode != FIREBASE_REQUEST_CODE;
    }
}

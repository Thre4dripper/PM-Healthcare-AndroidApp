package com.example.pmhealthcare.Fragments;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.CONNECTIVITY_SERVICE;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.pmhealthcare.Activities.ProfileActivity;
import com.example.pmhealthcare.Networking.Firebase;
import com.example.pmhealthcare.R;
import com.firebase.ui.auth.AuthUI;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import de.hdodenhof.circleimageview.CircleImageView;


public class ProfileFragment extends Fragment implements View.OnClickListener {


    TextView userNameTextView;
    CardView signOutBtn,feedbackBtn;
    MaterialButton editProfileButton, viewProfileButton;
    CircleImageView userDp;


    public static final String USER_DETAILS_MODE_KEY="userDetailsModeKey";
    public static final int MODE_EDIT=0;
    public static final int MODE_VIEW=1;

    public static Uri userDpUri;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_profile, container, false);


        userNameTextView=view.findViewById(R.id.user_name_text_view);
        editProfileButton =view.findViewById(R.id.edit_profile_button);
        viewProfileButton =view.findViewById(R.id.view_profile_button);
        signOutBtn=view.findViewById(R.id.sign_out_button);
        feedbackBtn=view.findViewById(R.id.feedback_button);
        userDp=view.findViewById(R.id.user_profile_pic);


        InitUIElements();
        return view;
    }

    public void InitUIElements(){

        signOutBtn.setOnClickListener(this);
        editProfileButton.setOnClickListener(this);
        viewProfileButton.setOnClickListener(this);
        feedbackBtn.setOnClickListener(this);
        userDp.setOnClickListener(this);

        setUserName();

        setUserDp();
    }

    @Override
    public void onClick(View view) {
        //sign out button onclick
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

        //edit profile button on click
        else if(view==editProfileButton){
            Intent intent=new Intent(getActivity(),ProfileActivity.class);
            intent.putExtra(USER_DETAILS_MODE_KEY,MODE_EDIT);
            startActivity(intent);

        }

        //View Profile Button On click
        else if(view==viewProfileButton){
            Intent intent=new Intent(getActivity(),ProfileActivity.class);
            intent.putExtra(USER_DETAILS_MODE_KEY,MODE_VIEW);
            startActivity(intent);
        }

        //Feedback Button On click
        else if(view==feedbackBtn){

            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.putExtra(Intent.EXTRA_EMAIL,new String[]{"ijlalahmad845@gmail.com"});
            intent.setData(Uri.parse("mailto:"));
            intent.putExtra(Intent.EXTRA_SUBJECT, "Feedback");

                startActivity(intent);
        }

        //User DP On Click
        else if(view==userDp){
            ImagePicker.with(this)
                    .cropSquare()	    			//Crop image(Optional), Check Customization for more option
                    .compress(200)			//Final image size will be less than 1 MB(Optional)
                    //.maxResultSize(720, 720)	//Final image resolution will be less than 1080 x 1080(Optional)
                    .start(150);
        }
    }

/**================================= METHOD FOR SETTING USER DP ===============================================**/
    public void setUserDp() {

        FirebaseStorage firebaseStorage=FirebaseStorage.getInstance();
        StorageReference storageReference=firebaseStorage.getReference("users/"+Firebase.UNIQUE_HEALTH_ID);

        StorageReference fileRef=storageReference.child("userDp");

        //made a non cancellable progress dialog
        ProgressDialog progressDialog=new ProgressDialog(getContext());
        progressDialog.setTitle("Please Wait...");
        progressDialog.show();
        progressDialog.setCancelable(false);

        //checking internet connectivity
        ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        if(isConnected)
        {
            fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {

                    Glide.with(getContext()).load(uri).addListener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            progressDialog.dismiss();
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                           progressDialog.dismiss();
                            return false;
                        }
                    }).into(userDp);
                    userDpUri=uri;
                }
            });
        }
        else {
            Toast.makeText(getContext(),"Offline Mode Enabled",Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }


    }

    public void setUserName(){
        FirebaseFirestore db=FirebaseFirestore.getInstance();

        db.collection("users").document(Firebase.UNIQUE_HEALTH_ID).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.getString("name")!=null)
                            userNameTextView.setText(documentSnapshot.getString("name"));
                        else userNameTextView.setText("User Name");
                    }
                });
    }

    /**================================================ ON ACTIVITY RESULT ====================================**/
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK  && requestCode==150){
            userDp.setImageURI(data.getData());
            //User.setUserDp(getContext(),data.getData().toString());
            Firebase.setUserDP(getContext(),data.getData());
        }
    }
}
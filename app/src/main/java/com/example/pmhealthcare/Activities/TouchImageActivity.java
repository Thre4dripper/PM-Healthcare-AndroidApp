package com.example.pmhealthcare.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.pmhealthcare.R;
import com.ortiz.touchview.TouchImageView;

public class TouchImageActivity extends AppCompatActivity {

    TouchImageView touchImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch_image);

        touchImageView=findViewById(R.id.touch_image_view);

        Intent intent=getIntent();

        Glide.with(this).load((Uri) intent.getParcelableExtra("imageUri")).into(touchImageView);
    }
}
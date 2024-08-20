package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class intro extends AppCompatActivity {
View mContentView;
Handler handler;
/** @noinspection unused*/
ImageView imageView;
FirebaseAuth auth;
FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        imageView = findViewById(R.id.imageView);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();


        mContentView = getWindow().getDecorView();
        mContentView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LOW_PROFILE
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);

        handler = new Handler();

        handler.postDelayed(() -> {
            if (user != null) {
                if (user.isEmailVerified()) {
                    startActivity(new Intent(intro.this, MainActivity.class));
                } else {
                    startActivity(new Intent(intro.this, Register.class));
                }
            } else {
                startActivity(new Intent(intro.this, Register.class));
            }
            finish();
        }, 3000);
    }
}
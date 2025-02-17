package com.example.myapplication;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
public class devoloperdetails extends AppCompatActivity {
    ImageButton button;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_devoloperdetails);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.edittext), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        button=findViewById(R.id.button711);
        button.setOnClickListener(v -> finish());
        TextView developerEmail = findViewById(R.id.developerEmail);
        Button sendEmailButton = findViewById(R.id.sendEmailButton);
        ImageView developerImage = findViewById(R.id.developerImage);
// Set click listener to send an email
        sendEmailButton.setOnClickListener(v -> {
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
            emailIntent.setData(Uri.parse("mailto:" + developerEmail.getText().toString()));
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Inquiry");
            startActivity(Intent.createChooser(emailIntent, "Send Email"));
        });
        // Add interactive animation to the developer image
        developerImage.setOnClickListener(v -> {
            Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.image_zoom_in);
            developerImage.startAnimation(animation);
        });
        // Add ripple effect to Send Email button
        sendEmailButton.setOnTouchListener((v, event) -> {
            Animation anim = AnimationUtils.loadAnimation(this, R.anim.button_bounce);
            sendEmailButton.startAnimation(anim);
            return false;
        });


    }
}
package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class About extends AppCompatActivity {
    ImageButton button;
    Button buttonone, buttontwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        // Initialize the Back Button
        button = findViewById(R.id.button7);
        button.setOnClickListener(v -> finish());

        // Initialize buttons
        buttontwo = findViewById(R.id.button2);
        buttonone = findViewById(R.id.button1);

        buttontwo.setOnClickListener(v -> {
            String email = "bluebird711@gmail.com";

            Intent i = new Intent(Intent.ACTION_SENDTO);
            i.setData(Uri.parse("mailto:" + email));

            try {
                startActivity(i);
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(v.getContext(), "No email client installed.", Toast.LENGTH_SHORT).show();
            }
        });

        // Set Info Tile
        setInfoTile(R.id.name_infotwo, getString(R.string.text9));
    }

    // Move this method outside onCreate
    private void setInfoTile(int tileId, String value) {
        View tile = findViewById(tileId);

        TextView labelTextView = tile.findViewById(R.id.label);
        TextView valueTextView = tile.findViewById(R.id.value);
        labelTextView.setText(R.string.blue_bird);
        valueTextView.setText(value);
    }
}
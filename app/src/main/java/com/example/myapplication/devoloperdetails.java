package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class devoloperdetails extends AppCompatActivity {
    ImageButton button;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_devoloperdetails);

        // Initialize back button
        button = findViewById(R.id.button7);
        button.setOnClickListener(v -> finish());

        // Handle website link click
        TextView websiteTextView = findViewById(R.id.website_link);
        websiteTextView.setOnClickListener(v -> {
            String url = websiteTextView.getText().toString().trim();
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(browserIntent);
        });

        // Set labels, values, and icons for each info tile
        setInfoTile(R.id.name_info, "Name", getString(R.string.developer_name), R.drawable.user);
        setInfoTile(R.id.phone_info, "Mobile", getString(R.string.developer_phone), R.drawable.phone);
        setInfoTile(R.id.email_info, "Email", getString(R.string.developer_email), R.drawable.mail);
        setInfoTile(R.id.address_info, "Address", getString(R.string.developer_address), R.drawable.placeholder);
        setInfoTile(R.id.dob_info, "D.O.B", getString(R.string.developer_dob), R.drawable.calender);

        // Make email clickable
        View emailTile = findViewById(R.id.email_info);
        TextView emailTextView = emailTile.findViewById(R.id.value);
        emailTextView.setOnClickListener(v -> {
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
            emailIntent.setData(Uri.parse("mailto:" + getString(R.string.developer_email)));
            startActivity(emailIntent);
        });

        // Make phone number clickable
        View phoneTile = findViewById(R.id.phone_info);
        TextView phoneTextView = phoneTile.findViewById(R.id.value);
        phoneTextView.setOnClickListener(v -> {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:" + getString(R.string.developer_phone)));
            startActivity(callIntent);
        });
    }

    // Method to set values and icons dynamically
    private void setInfoTile(int tileId, String label, String value, int iconResId) {
        View tile = findViewById(tileId);
        TextView labelTextView = tile.findViewById(R.id.label);
        TextView valueTextView = tile.findViewById(R.id.value);
        ImageView iconImageView = tile.findViewById(R.id.icon);

        labelTextView.setText(label);
        valueTextView.setText(value);
        iconImageView.setImageResource(iconResId);
    }
}





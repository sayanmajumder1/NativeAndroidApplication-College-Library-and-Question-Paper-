package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Profile extends AppCompatActivity {
    TextView fullName, email;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;
    Button logout;
    ImageButton button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        button = findViewById(R.id.button090);
        button.setOnClickListener(v -> finish());  // Closes the Profile activity

        logout = findViewById(R.id.LoginBtn2);
        fullName = findViewById(R.id.mName);
        email = findViewById(R.id.mEmail2);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        if (fAuth.getCurrentUser() != null) {
            userID = fAuth.getCurrentUser().getUid();
            DocumentReference documentReference = fStore.collection("users").document(userID);
            documentReference.addSnapshotListener(this, (documentSnapshot, error) -> {
                if (error != null) {
                    Log.e("tag", "Listen failed: " + error);
                    return;
                }
                if (documentSnapshot != null && documentSnapshot.exists()) {
                    fullName.setText(documentSnapshot.getString("fName"));
                    email.setText(documentSnapshot.getString("email"));
                } else {
                    Log.d("tag", "onEvent: Document Does Not Exist");
                }
            });
        } else {
            Log.d("tag", "User not authenticated");
        }

        logout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(), Login.class));
            finish();
        });
        // Handle back button using the OnBackPressedDispatcher
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Custom back button behavior (e.g., close the activity)
                finish();  // This will simply close the Profile activity
            }
        };

        // Add the callback to the back button dispatcher
        getOnBackPressedDispatcher().addCallback(this, callback);
    }


}

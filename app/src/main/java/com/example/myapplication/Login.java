package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class Login extends AppCompatActivity {
    EditText mEmail, mPassword;
    Button mLoginBtn;
    TextView mCreateBtn;
    TextView forgetTextLink;
    FirebaseAuth fAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        mEmail = findViewById(R.id.Email);
        mPassword = findViewById(R.id.password);
        progressBar = findViewById(R.id.progressBar);
        mLoginBtn = findViewById(R.id.LoginBtn1);
        mCreateBtn = findViewById(R.id.createText);

        forgetTextLink = findViewById(R.id.forgotPassword1);

        fAuth = FirebaseAuth.getInstance();

        mLoginBtn.setOnClickListener(v -> {
            String email = mEmail.getText().toString();
            String password = mPassword.getText().toString();
            if (TextUtils.isEmpty(email)) {
                mEmail.setError("Email Is Required");
                return;
            }
            if (TextUtils.isEmpty(password)) {
                mPassword.setError("Password Is Require");
                return;
            }
            if (password.length() < 6) {
                mPassword.setError("Password Must Be >= 6 character");
                return;
            }
            progressBar.setVisibility(View.VISIBLE);
            fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    //Check The User Is Verified Or  Not Verified
                    FirebaseUser fuser = fAuth.getCurrentUser();
                    assert fuser != null;
                    if (fuser.isEmailVerified()) {
                        progressBar.setVisibility(View.GONE);
                        startActivitySecond();
                        finish();
                    } else {

                        Toast.makeText(Login.this, "Please Verify Your Email", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }

                } else {

                    Toast.makeText(Login.this, "Error" + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);

                }
            });
        });
        forgetTextLink.setOnClickListener(v -> {

            final EditText resetMail = new EditText(v.getContext());
            final AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
            passwordResetDialog.setTitle("Reset Password ?");
            passwordResetDialog.setMessage("Enter Your Email To Received Reset Link.");
            passwordResetDialog.setView(resetMail);

            passwordResetDialog.setPositiveButton("Send", (dialog, which) -> {
                // extract the email and send reset link
                String mail = resetMail.getText().toString();
                fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(aVoid -> Toast.makeText(Login.this, "Reset Link Sent To Your Email.", Toast.LENGTH_SHORT).show()).addOnFailureListener(e -> Toast.makeText(Login.this, "Error ! Reset Link is Not Sent" + e.getMessage(), Toast.LENGTH_SHORT).show());

            });

            passwordResetDialog.setNegativeButton("Close", (dialog, which) -> {
                // close the dialog
            });
            passwordResetDialog.create().show();

        });
        mCreateBtn.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), Register.class)));

    }
    private void startActivitySecond() {
        Intent intent = new Intent(Login.this, Profile.class);
        startActivity(intent);
        finish();

    }
}
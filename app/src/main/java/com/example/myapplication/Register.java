package com.example.myapplication;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
public class Register extends AppCompatActivity {
    public static final  String TAG="TAG";
EditText mFullName,mEmail,mPassword,mConfirmPass;
Button mRegisterBtn;
TextView mLoginBtn;
FirebaseAuth fAuth;
ProgressBar progressBar;
FirebaseFirestore fStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mFullName=findViewById(R.id.fullName);
        mEmail=findViewById(R.id.Email);
        mPassword=findViewById(R.id.password);
        mConfirmPass=findViewById(R.id.confirmPass);
        mRegisterBtn=findViewById(R.id.registerBtn);
        mLoginBtn=findViewById(R.id.createText);
        progressBar=findViewById(R.id.progressBar);
        // Firebase Authentication
        fAuth= FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        mRegisterBtn.setOnClickListener(v -> {
            String email =mEmail.getText().toString();
            String password =mPassword.getText().toString();
            String fullName =mFullName.getText().toString();
            String confirm =mConfirmPass.getText().toString();
            //Logic For Register
            if(TextUtils.isEmpty((fullName))){
                mFullName.setError("Full Name Is Required");
                return;}
            if(TextUtils.isEmpty(email)){
                mEmail.setError("Email Is Required");
                return;
            }
            if(TextUtils.isEmpty(password)){
                mPassword.setError("Password Is Require");
                return;
            }
            if(password.length()<6){
                mPassword.setError("Password Must Be in 6 Character");
                return;
            }
            if (confirm.isEmpty() || !password.equals(confirm)){
                mFullName.setError("Invalid PassWord");
                return;
            }
            //code For ProgressBar
            progressBar.setVisibility(View.VISIBLE);
            fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    Toast.makeText(Register.this,"User Create",Toast.LENGTH_SHORT).show();
                    startActivitySecond();

                    //Send The Verification Code

                    FirebaseUser fuser =fAuth.getCurrentUser();
                    assert fuser != null;
                    fuser.sendEmailVerification().addOnSuccessListener(aVoid -> Toast.makeText(Register.this, "Verified Email Has Been Send",Toast.LENGTH_SHORT).show()).addOnFailureListener(e -> Log.d(TAG,"onFailure:Email not Send " + e.getMessage()));


                  userID =fAuth.getCurrentUser().getUid();
                    DocumentReference documentReference = fStore.collection("users").document(userID);
                    Map<String,Object> user = new HashMap<>();
                    user.put("fName", fullName);
                    user.put("email",email);
                    documentReference.set(user).addOnSuccessListener(avoid ->
                            Log.d(TAG,"onSuccess:User Profile Is Created For " + userID)).addOnFailureListener(e ->
                            Log.d(TAG,"onFailure:Email not Send " + e.getMessage()));

                }else {
                    Toast.makeText(Register.this,"Error"+ Objects.requireNonNull(task.getException()).getMessage(),Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            });
        });
mLoginBtn.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(),Login.class)));
    }

    private void startActivitySecond() {
        Intent intent =new Intent(Register.this,Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }



}
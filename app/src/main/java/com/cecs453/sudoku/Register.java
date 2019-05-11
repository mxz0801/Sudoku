package com.cecs453.sudoku;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Register extends AppCompatActivity implements View.OnClickListener {

    private Button buttonRegister;
    private EditText editTextEmail;
    private EditText editTextDisplayName;
    private EditText editTextPassword;
    private EditText editTextPasswordConfirm;
    private TextView textViewSignIn;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private DatabaseReference userReference;
    private String uid;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        buttonRegister =  findViewById(R.id.ButtonRegister);
        editTextEmail =  findViewById(R.id.editTextEmail);
        editTextDisplayName = findViewById(R.id.editTextDisplayName);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextPasswordConfirm = findViewById(R.id.editTextRetypePassword);
        textViewSignIn =    findViewById(R.id.textViewSignIn);
        buttonRegister.setOnClickListener(this);
        textViewSignIn.setOnClickListener(this);
        userReference = FirebaseDatabase.getInstance().getReference();
    }

    private void registerUser(){
        final String email = editTextEmail.getText().toString().trim();
        final String display_name = editTextDisplayName.getText().toString();
        final String password = editTextPassword.getText().toString();
        String passwordConfirm = editTextPasswordConfirm.getText().toString();
        // Check if all the fields are entered
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Please enter email.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(display_name)) {
            Toast.makeText(this, "Please enter a display name.", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please enter password.", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(passwordConfirm)){
            Toast.makeText(this, "Please confirm password.", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!password.equals(passwordConfirm)){
            Toast.makeText(this, "Passwords do not match.", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("Registering User");
        progressDialog.show();
        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            user = firebaseAuth.getCurrentUser();
                            uid = user.getUid();
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(display_name)
                                    .setPhotoUri (Uri.parse("https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_960_720.png"))
                                    .build();
                            user.updateProfile(profileUpdates);
                            userReference.child("users").child(uid).child("displayname").setValue(display_name);
                            userReference.child("users").child(uid).child("uid").setValue(uid);
                            userReference.child("users").child(uid).child("profile_photo").setValue("https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_960_720.png");
                            loginUser(email,password);
                        }
                        else {
                            FirebaseAuthException e = (FirebaseAuthException )task.getException();
                            Toast.makeText(Register.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }
                });
    }
    private void loginUser(String email,String password) {
        progressDialog.setMessage("Logging In");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            //Toast.makeText(Login.this, "Login Success", Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(new Intent(Register.this,MainMenu.class));
                        }
                        else {
                            FirebaseAuthException e = (FirebaseAuthException )task.getException();
                            Toast.makeText(Register.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }
                });
    }
    @Override
    public void onClick(View view){
        if(view==buttonRegister){
            registerUser();
        }

        if(view==textViewSignIn){
            startActivity(new Intent(this, Login.class));
        }
    }
}

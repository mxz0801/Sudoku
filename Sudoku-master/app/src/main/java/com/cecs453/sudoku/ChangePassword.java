package com.cecs453.sudoku;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.core.view.Change;


import org.w3c.dom.Text;

public class ChangePassword extends Activity implements View.OnClickListener{
    private EditText oldPassword;
    private EditText newPassword;
    private EditText newPasswordConfirm;
    private Button update;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        user = firebaseAuth.getCurrentUser();
        oldPassword =  findViewById(R.id.editTextOldPassword);
        newPassword =  findViewById(R.id.editTextNewPassword);
        newPasswordConfirm = findViewById(R.id.editTextNewPasswordConfirm);
        update = findViewById(R.id.buttonUpdate);
        update.setOnClickListener(this);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width * .85), (int) (height * .7));
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;
        getWindow().setAttributes(params);
    }

    private void updatePassword() {
        final String oldPass = oldPassword.getText().toString();
        final String newPass = newPassword.getText().toString();
        final String newPassConf = newPasswordConfirm.getText().toString();
        if(TextUtils.isEmpty(oldPass)){
            Toast.makeText(this, "Please enter your old password.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(newPass)) {
            Toast.makeText(this, "Please enter your new password.", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(newPassConf)){
            Toast.makeText(this, "Please confirm your password.", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!newPass.equals(newPassConf)){
            Toast.makeText(this, "Password and confirmation password do not match.", Toast.LENGTH_SHORT).show();
            return;
        }
        if(newPass.equals(oldPass)){
            Toast.makeText(this, "New password is the same as the old password.", Toast.LENGTH_SHORT).show();
            return;
        }
        AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(),oldPass);
        progressDialog.setMessage("Updating Password");
        progressDialog.show();
        user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    user.updatePassword(newPass).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(ChangePassword.this, "Password has been successfully updated.", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                            else{
                                Toast.makeText(ChangePassword.this, "Error password not updated.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(ChangePassword.this, "Old password is incorrect.", Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }
        });
    }
    @Override
    public void onClick(View view){
        if(view==update){
            updatePassword();
        }
    }
}

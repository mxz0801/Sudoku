package com.cecs453.sudoku;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class MainMenu extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth firebaseAuth;
    private Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_main_menu);
        logout =  findViewById(R.id.buttonLogout);
        logout.setOnClickListener(MainMenu.this);
    }

    @Override
    public void onClick(View v) {
        if(v==logout){
            firebaseAuth.signOut();
            startActivity(new Intent(this, Login.class));
        }
    }
}

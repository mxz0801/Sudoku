package com.cecs453.sudoku;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainMenu extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;
    private ImageView sudokuLogo;
    private Chronometer timer;

    @Override
    protected void onStart() {
        super.onStart();
        timer.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();
        sudokuLogo = findViewById(R.id.logo);
        sudokuLogo.setImageResource(R.drawable.sudoku_horizontal);
        timer = findViewById(R.id.timer);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        ImageView headerImageView = headerView.findViewById(R.id.headerimageView);
        TextView headerDisplayName = headerView.findViewById(R.id.headerDisplayName);
        TextView headerEmail = headerView.findViewById(R.id.headerEmail);
        headerDisplayName.setText(currentUser.getDisplayName());
        headerEmail.setText(currentUser.getEmail());
        newGame();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            this.moveTaskToBack(true);
            //super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_newgame){
            newGame();
        }
        else if (id == R.id.nav_changePass) {
            startActivity(new Intent(getApplicationContext(),ChangePassword.class));
        } else if (id == R.id.nav_changeProfile) {
            startActivity(new Intent(getApplicationContext(),ChangeProfile.class));
        } else if (id == R.id.nav_help) {
            startActivity(new Intent(getApplicationContext(),Help.class));
        } else if (id == R.id.nav_about){
            startActivity(new Intent(getApplicationContext(),About.class));
        }
        else if (id == R.id.nav_logout) {
            firebaseAuth.signOut();
            startActivity(new Intent(this, Login.class));
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void newGame(){
        //timer.setBase((SystemClock.elapsedRealtime()));
        SudokuGenerator sudokuGenerator = new SudokuGenerator();
        int[][] solution = sudokuGenerator.generateGrid();
        int[][] sudoku = sudokuGenerator.removeElements(solution);
        for (int i = 0; i < sudoku.length; i++) {
            for (int j = 0; j < sudoku[i].length; j++) {
                System.out.print(sudoku[i][j] + " ");
            }
            System.out.println();
        }
    }
}


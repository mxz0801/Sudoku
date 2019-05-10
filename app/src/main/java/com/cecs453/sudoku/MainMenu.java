package com.cecs453.sudoku;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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
    private RecyclerView recyclerView;
    private MyRecyclerViewAdapter mAdapter;
    public int[][] sudokuBoard = new int[9][9];
    public String data[];

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
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 9));
        newGame();
        String[] data = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48"};

        mAdapter = new MyRecyclerViewAdapter(this,data);
        recyclerView.setAdapter(mAdapter);
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
        sudokuBoard = sudokuGenerator.removeElements(solution);
        for (int i = 0; i < sudokuBoard.length; i++) {
            for (int j = 0; j < sudokuBoard[i].length; j++) {
                System.out.print(sudokuBoard[i][j] + " ");
//                data
            }
            System.out.println();
        }
    }
}


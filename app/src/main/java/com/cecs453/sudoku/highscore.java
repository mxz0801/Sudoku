package com.cecs453.sudoku;

import android.content.Context;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class highscore extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private DatabaseReference mDatabase,myRef;
    private List<userProfile> ITEMS = new ArrayList<>();
    private Chronometer yourScore;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width * .85), (int) (height * .85));
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;
        getWindow().setAttributes(params);
        yourScore = findViewById(R.id.yourHighScore);
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();
        recyclerView = (RecyclerView) findViewById(R.id.highScoreRecycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        myRef = mDatabase.child("users");
        get_userProfiles(new OnDataLoadedListener() {
            @Override
            public void onFinishLoading(List<userProfile> data) {
                mAdapter = new highscoreAdapter(ITEMS);
                recyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
    public void get_userProfiles(final OnDataLoadedListener listener){
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> userIDS = dataSnapshot.getChildren();
                for(DataSnapshot user:userIDS) {
                    userProfile p = user.getValue(userProfile.class);
                    if(p.getHighscore()!=0){
                        ITEMS.add(p);
                    }
                    if(p.getUid().equals(currentUser.getUid())){
                        if(Integer.toString(p.getHighscore())!=null){
                            yourScore.setBase(SystemClock.elapsedRealtime() - p.getHighscore() * 1000);
                        }
                    }
                }
                if(listener != null) listener.onFinishLoading(ITEMS);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
    public interface OnDataLoadedListener{
        void onFinishLoading(List<userProfile> data);
        void onCancelled(FirebaseError firebaseError);
    }

}

package com.cecs453.sudoku;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class highscore extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private highscoreAdapter mAdapter;
    public static final List<userProfile> ITEMS = new ArrayList<>();


    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        mAdapter = new highscoreAdapter(ITEMS);
        View highscoreView = parent.findViewById(R.id.highScoreRecycler);
        RecyclerView recyclerView = (RecyclerView) highscoreView;

        recyclerView.setAdapter(mAdapter);
        return super.onCreateView(parent, name, context, attrs);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference myRef = mDatabase.child("users");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> userIDS = dataSnapshot.getChildren();
                for(DataSnapshot user:userIDS) {
                    userProfile p = user.getValue(userProfile.class);
                    ITEMS.add(p);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }
}

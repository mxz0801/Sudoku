package com.cecs453.sudoku;
import android.os.SystemClock;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class highscoreAdapter extends RecyclerView.Adapter<highscoreAdapter.ViewHolder> {

    private final List<userProfile> mValues;
    private DatabaseReference mDatabase;

    public highscoreAdapter(List<userProfile> items) {
        Comparator<userProfile> compareByHighScore = new Comparator<userProfile>() {
            @Override
            public int compare(userProfile o1, userProfile o2) {
                return Integer.toString(o1.getHighscore()).compareTo(Integer.toString(o2.getHighscore()));
            }
        };
        Collections.sort(items,compareByHighScore);
        mValues = items;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.highscore_item, parent, false);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mrank.setText("# "+position+1);
        holder.mItem = mValues.get(position);
        holder.mContentView.setText(mValues.get(position).getDisplayname());
        holder.score.setBase(SystemClock.elapsedRealtime() - (mValues.get(position).getHighscore() * 1000));
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mContentView;
        public userProfile mItem;
        public ImageView mImageView;
        public Chronometer score;
        public TextView mrank;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mImageView = view.findViewById(R.id.user_photo);
            mContentView = view.findViewById(R.id.high_score_name);
            score = view.findViewById(R.id.high_score_value);
            mrank = view.findViewById(R.id.rank);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
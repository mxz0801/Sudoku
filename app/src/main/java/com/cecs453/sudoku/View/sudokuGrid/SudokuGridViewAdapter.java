package com.cecs453.sudoku.View.sudokuGrid;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.cecs453.sudoku.R;

public class SudokuGridViewAdapter extends BaseAdapter {
    private Context context;
    public SudokuGridViewAdapter(Context context){
        this.context=context;
    }

    @Override
    public int getCount(){
        return 81;
    }

    @Override
    public Object getItem(int arg0){
        return null;
    }

    @Override
    public long getItemId(int arg0){
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View v = convertView;

        if(v == null){
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            v=inflater.inflate(R.layout.cell,parent,false);
        }
        return v;
    }

}

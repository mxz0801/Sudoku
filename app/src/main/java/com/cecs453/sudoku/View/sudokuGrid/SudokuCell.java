package com.cecs453.sudoku.View.sudokuGrid;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

public class SudokuCell extends View {
    public SudokuCell(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}


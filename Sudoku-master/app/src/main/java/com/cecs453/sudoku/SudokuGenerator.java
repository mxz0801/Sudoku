package com.cecs453.sudoku;

import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class SudokuGenerator {
    public static SudokuGenerator instance;
    private ArrayList<ArrayList<Integer>> Available = new ArrayList<ArrayList<Integer>>();
    private Random rand = new Random();

    public int[][] generateGrid(){
        int[][] sudoku = new int[9][9];
        int currentPosition = 0;
        clearGrid(sudoku);
        while(currentPosition < 81){
            if( Available.get(currentPosition).size() != 0 ){
                int i = rand.nextInt(Available.get(currentPosition).size());
                int number = Available.get(currentPosition).get(i);
                if( !checkConflict(sudoku, currentPosition , number)){
                    int xPos = currentPosition % 9;
                    int yPos = currentPosition / 9;
                    sudoku[xPos][yPos] = number;
                    Available.get(currentPosition).remove(i);
                    currentPosition++;
                }
                else{
                    Available.get(currentPosition).remove(i);
                }
            }else{
                for( int i = 1 ; i <= 9 ; i++ ){
                    Available.get(currentPosition).add(i);
                }
                currentPosition--;
            }
        }
        return sudoku;
    }
    private boolean checkConflict( int[][] sudoku , int currentPosition , final int number) {
        int xPosition =  currentPosition % 9;
        int yPosition = currentPosition / 9;
        if (checkHorizontal(sudoku,xPosition,yPosition,number)||checkVertical(sudoku,xPosition,yPosition,number)){
            return true;
        }
        else
            return false;
    }

    private boolean checkHorizontal(int[][] sudoku, int xPosition, int yPosition, int number) {
        for( int i = xPosition - 1; i >= 0 ; i-- ){
            if( number == sudoku[i][yPosition]){
                return true;
            }
        }
        return false;
    }
    private boolean checkVertical(int[][] sudoku, int xPosition, int yPosition, int number) {
        for( int i = yPosition - 1; i >= 0 ; i-- ){
            if( number == sudoku[xPosition][i]){
                return true;
            }
        }
        return false;
    }

    public int[][] removeElements( int[][] sudoku ){
        int i = 0;

        while( i < 20 ){
            int x = rand.nextInt(9);
            int y = rand.nextInt(9);

            if( sudoku[x][y] != 0 ){
                sudoku[x][y] = 0;
                i++;
            }
        }
        return sudoku;

    }

    private void clearGrid(int[][] sudoku) {
        Available.clear();
        // Populates the grid with 0
        for( int y =  0; y < 9 ; y++ ){
            for( int x = 0 ; x < 9 ; x++ ){
                sudoku[x][y] = 0;
            }
        }
        //Generates all possible combinations for sudoku
        for( int x = 0 ; x < 81 ; x++ ){
            Available.add(new ArrayList<Integer>());
            for( int i = 1 ; i <= 9 ; i++){
                Available.get(x).add(i);
            }
        }
    }
}

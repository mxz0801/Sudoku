package com.cecs453.sudoku.View.sudokuGrid;

import java.util.ArrayList;

public class Board {
    private int [][] cells = new int[9][9];

    public Board(){} // default

    public void setNumber(int r, int c, int number){
        cells [r][c] = number; // [1][2] = 3
    }

    // copy numbers from another a set of cells
    public void setNewCell(int [][] newCells){
        for(int i = 0; i < newCells.length; i++){
            for(int j = 0; j < newCells[i].length; j++){
                cells[i][j] = newCells[i][j];
            }
        }
    }

    // check full board, if it's empty, there is 0
    public boolean isBoardFull(){
        for(int i = 0; i < cells.length; i++){
            for(int j = 0; j < cells[i].length; j++){
                if(cells[i][j] == 0){
                    return false;
                }
            }
        }
        return true;
    }

    // check if is satisfy sudoku 9X9 rules
    public boolean isBoardCorrect(){
         // check horizontal
        for(int i = 0; i < cells.length; i++){
            // list of nine numbers
            ArrayList<Integer> nineNumbers = new ArrayList<>();
            for(int j = 0; j < cells[i].length; i++){
                int userNum = cells[i][j];
                if(nineNumbers.contains(userNum)){
                    return false; // repeating number
                }
                else{
                    nineNumbers.add(userNum); // add new number(1-9)
                }
            }
        }

        // check vertical
        for(int i = 0; i < cells.length; i++){
            // list of nine numbers
            ArrayList<Integer> nineNumbers = new ArrayList<>();
            for(int j = 0; j < cells[i].length; i++){
                int userNum = cells[j][i]; // is [j][i] not [i][j]
                if(nineNumbers.contains(userNum)){
                    return false; // repeating number
                }
                else{
                    nineNumbers.add(userNum); // add new number(1-9)
                }
            }
        }

        // gone through all checks, it is correct
        return true;
    }

    // not sure if it needs
//    @Override
//    public String toString() {
//        StringBuilder temp = new StringBuilder();
//        for (int i = 0; i < cells.length; i++) {
//            for (int j = 0; j < cells[i].length; j++) {
//                if (j == 0) {
//                    temp.append("\n");
//                }
//
//                int currentNumber = cells[i][j];
//                if (currentNumber == 0) {
//                    temp.append("-");
//                } else {
//                    temp.append(currentNumber);
//                }
//
//                if (j != (cells[i].length-1)) {
//                    temp.append(" ");
//                }
//            }
//        }
//        return temp.toString();
//    }

}

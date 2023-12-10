package Sudoku;

import java.util.Scanner;

public class Sudoku {
    static int subMatrixSize = 3;
    static int spaceCount;
    static int boardSize;
    static int[][] board;
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        boardSize = 9;
        generateGameBoard();
        printBoard();
        swapping();
        System.out.println();
        randomPlacing();
        printBoard();
        while (winningCheck()){
            System.out.println("Enter the Number : ");
            int inputNumber = in.nextInt();
            System.out.println("Enter the Row (0 - 8): ");
            int row = in.nextInt();
            System.out.println("Enter the Col (0 - 8): ");
            int col = in.nextInt();
            if(isPlaceable(inputNumber , row , col)){
                board[row][col] = inputNumber;
                spaceCount--;
                System.out.println("Successfully Placed");
                printBoard();
            }else{
                System.out.println("You can't place " + inputNumber + " there");
            }
        }
        System.out.println("Hurrah! You won the match");
    }
    private static boolean winningCheck(){
        if(spaceCount == 0){
            return false;
        }
        return true;
    }
    private static boolean isPlaceable(int number , int row , int col){
        for (int i = 0; i < boardSize; i++) {
            if(board[row][i] == number){
                return false;
            }
            if(board[i][col] == number){
                return false;
            }
        }
        int startRow = subMatrixSize * (row / subMatrixSize);
        int startCol = subMatrixSize * (row / subMatrixSize);
        for (int i = startRow; i < startRow + subMatrixSize; i++) {
            for (int j = startCol; j < startCol + subMatrixSize; j++) {
                if(board[i][j] == number){
                    return false;
                }
            }
        }
        return true;
    }
    private static void randomPlacing(){
        int hard = 8 * boardSize;
        for (int i = 0; i < hard; i++) {
            int row = (int) (Math.random() * boardSize);
            int col = (int) (Math.random() * boardSize);
            if(board[row][col] != 0){
                board[row][col] = 0;
                spaceCount++;
            }
        }
    }
    static void swapping(){
        int row = 0;
        for (int i = 0; i < subMatrixSize; i++) {
            int firstRow = ((int)(Math.random() * boardSize)) % subMatrixSize;
            int secondRow = (int)(Math.random() * boardSize) % subMatrixSize;
            for (int j = 0; j < boardSize; j++) {
                swap(firstRow + row , secondRow + row , j,j);
            }
            for (int j = 0; j < boardSize; j++) {
                swap(j , j , firstRow + row , secondRow + row);
            }
            row += subMatrixSize;
        }
    }
    static void swap(int firstRow , int secondRow , int firstCol , int secondCol){
            int temp = board[firstRow][firstCol];
            board[firstRow][firstCol] = board[secondRow][secondCol];
            board[secondRow][secondCol] = temp;
    }

    static void printBoard(){
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                int value = board[i][j];
                System.out.print("|");
                if(value == 0){
                    System.out.print("   ");
                }else {
                    System.out.print(" " + board[i][j] + " ");
                }

            }
            System.out.print("|");
            System.out.println();
        }
    }
    static void generateGameBoard(){
        board = new int[boardSize][boardSize];
        int number = 1;
        for (int row = 0; row < boardSize; row++) {
            int n = number;
            for (int col = 0; col < boardSize; col++) {
                if(n <= boardSize){
                    board[row][col] = n;
                }else{
                    board[row][col] = n - boardSize;
                }
                n++;
            }
            number = number + subMatrixSize <= boardSize ? number + subMatrixSize : number - 5;
        }
    }
}

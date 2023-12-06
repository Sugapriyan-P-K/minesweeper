package minesweeper;

import java.util.Scanner;

public class mineSweeper {
    static int count = 4;
    static int len = 4;
    static char[][] displayArray = {
            { '?', '?', '?', '?', '?' },
            { '?', '?', '?', '?', '?' },
            { '?', '?', '?', '?', '?' },
            { '?', '?', '?', '?', '?' },
            { '?', '?', '?', '?', '?' }
    };
    static char[][] dataArray = {
            { '1', '1', '1', ' ', ' ' },
            { '1', 'B', '1', ' ', ' ' },
            { '1', '1', '1', ' ', ' ' },
            { ' ', ' ', ' ', '1', '1' },
            { ' ', ' ', ' ', '1', 'B' }
    };

    // static
    static void flag(int row, int col) {
        if (row >= len || col >= len)
            System.out.println("INVALID INPUT");
        else {
            if (displayArray[row][col] == '?') {
                displayArray[row][col] = 'F';
                count++;
                displayBoard();
            } else if (displayArray[row][col] == 'F')
                System.out.println("Already flaged");
            else
                System.out.println("Can't perform"); // Already displayed value
        }
    }

    static void displayBoard() {
        System.out.println("_____________________");

        for (int i = 0; i < displayArray.length; i++) {
            System.out.println("|   |   |   |   |   |");
            for (int j = 0; j < displayArray.length; j++) {
                System.out.print("| " + displayArray[i][j] + " ");
            }
            System.out.println("\n|___|___|___|___|___|");
        }

    }

    static void unflag(int row, int col) {
        if (displayArray[row][col] == 'F') {
            displayArray[row][col] = '?';
        } else {
            System.out.println("No flag to unflag");
        }
    }

    static Boolean itHasBomb(int row, int col) {
        return dataArray[row][col] == 'B';
    }

    // static void displayBoard() {
    // for (int i = 0; i < displayArray.length; i++) {
    // for (int j = 0; j < displayArray.length; j++) {
    // System.out.print(displayArray[i][j] + " ");
    // }
    // System.out.println();
    // }
    // }

    static void burstBomb() {
        int n = dataArray.length;
        int m = dataArray[0].length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (dataArray[i][j] == 'B' && displayArray[i][j] != 'F')
                    displayArray[i][j] = 'B';

                if (displayArray[i][j] == 'F' && dataArray[i][j] != 'B') {
                    displayArray[i][j] = 'X';
                }
            }
        }
    }

    // static void unflag() {

    // }

    static void revealArea(int row, int col, boolean[][] isVisisted) {
        if (row == -1 || col == -1 || row == isVisisted.length || col == isVisisted.length) {
            return;
        }
        if (!isVisisted[row][col]) {
            if (dataArray[row][col] != 'B') {
                displayArray[row][col] = dataArray[row][col];
            }
            return;
        }
        isVisisted[row][col] = false;
        displayArray[row][col] = dataArray[row][col];
        revealArea(row - 1, col, isVisisted); // top
        revealArea(row, col - 1, isVisisted); // left
        revealArea(row, col + 1, isVisisted); // right
        revealArea(row + 1, col, isVisisted); // bottom
        revealArea(row - 1, col - 1, isVisisted); // top Left
        revealArea(row - 1, col + 1, isVisisted); // top right
        revealArea(row + 1, col - 1, isVisisted); // bottom left
        revealArea(row + 1, col + 1, isVisisted); // bottom right
        isVisisted[row][col] = true;
    }

    public static void main(String[] args) {
        System.out.println("Minesweeper");
        int row, col;
        boolean[][] isVisisted = {
                { false, false, false, true, true },
                { false, false, false, true, true },
                { false, false, false, true, true },
                { true, true, true, false, false },
                { true, true, true, false, false }
        };
        Scanner scan = new Scanner(System.in);
        while (true) {
            System.out.println("Enter the row and column input seperate by space (ex: 1 1)");
            row = scan.nextInt();
            col = scan.nextInt();
            String function = scan.next();
            char func = function.charAt(0);
            switch (func) {
                case ('M'):
                    if (itHasBomb(row, col)) {
                        burstBomb();
                        displayBoard();
                        System.out.println("Game Over");
                        return;
                    }
                    revealArea(row, col, isVisisted);
                    displayBoard();
                    break;
                case ('F'):
                    flag(row, col);
                    break;
                case ('U'):
                    unflag(row, col);
                    displayBoard();
                    break;
                default:
                    System.out.println("Wrong input");
                    break;
            }
        }
    }
}
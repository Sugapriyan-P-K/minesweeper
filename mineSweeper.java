// package minesweeper;

import java.util.Random;
import java.util.Scanner;

public class mineSweeper {
    static int correctFlags = 0,
            numberOfBombs;

    static char[][] displayArray = { {} };
    static char[][] dataArray = { {} };
    static int[][] bombs = { {} };

    // static
    static void flag(int row, int col) {
        int length = dataArray.length;
        if (row >= length || col >= length)
            System.out.println("INVALID INPUT");
        else {
            if (displayArray[row][col] == '?') {
                displayArray[row][col] = 'F';
                if (dataArray[row][col] == 'B') {
                    correctFlags++;
                }
                displayBoard();
            } else if (displayArray[row][col] == 'F')
                System.out.println("Already flaged");
            else
                System.out.println("Can't perform"); // Already displayed value
        }
    }

    static void displayDB() {
        for (int i = 0; i < dataArray.length; i++) {
            System.out.println("\t");
            for (int j = 0; j < dataArray.length; j++) {
                System.out.print("" + dataArray[i][j] + " | ");
            }
        }
        System.out.println("");
    }

    static void displayBoard() {
        for (int i = 0; i < displayArray.length; i++) {
            for (int j = 0; j < displayArray.length; j++) {
                System.out.print(" " + displayArray[i][j] + " ");
            }
            System.out.println();
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

    static char[][] calculateCellValue(int size, char[][] dataArray) {
        for (int everyBomb = 0; everyBomb < bombs.length; everyBomb++) {
            int rowEnd = bombs[everyBomb][0] + 1 < size ? bombs[everyBomb][0] + 1 : bombs[everyBomb][0];
            int row = bombs[everyBomb][0] - 1 >= 0 ? bombs[everyBomb][0] - 1 : bombs[everyBomb][0];
            int colEnd = bombs[everyBomb][1] + 1 < size ? bombs[everyBomb][1] + 1 : bombs[everyBomb][1];
            int col = bombs[everyBomb][1] - 1 >= 0 ? bombs[everyBomb][1] - 1 : bombs[everyBomb][1];
            for (int x1 = row; x1 <= rowEnd; x1++) {
                for (int y1 = col; y1 <= colEnd; y1++) {
                    if (dataArray[x1][y1] >= '1' && dataArray[x1][y1] != 'B') {
                        dataArray[x1][y1] = (char) (dataArray[x1][y1] + 1);
                    } else if (dataArray[x1][y1] != 'B') {
                        dataArray[x1][y1] = '1';
                    }
                }
            }
        }
        return dataArray;
    }

    static int[] getNearPosition(int row, int col, int size) {
        if (col - 1 > 0 && dataArray[row][col - 1] != 'B') {
            return new int[] { row, col - 1 };
        } else if (col + 1 < size && dataArray[row][col + 1] != 'B') {
            return new int[] { row, col + 1 };
        } else if (row - 1 > 0 && dataArray[row - 1][col] != 'B') {
            return new int[] { row - 1, col };
        } else if (row + 1 < size && dataArray[row + 1][col] != 'B') {
            return new int[] { row + 1, col };
        } else if (row - 1 > 0) {
            if (col - 1 > 0 && dataArray[row - 1][col - 1] != 'B') {
                return new int[] { row - 1, col - 1 };
            } else if (dataArray[row - 1][col + 1] != 'B') {
                return new int[] { row - 1, col + 1 };
            }
        }
        return new int[] { 0, 0 };

    }

    static char[][] getBombs(char[][] dataArray, int bombCount, int size) {
        Random random = new Random();
        for (int bomb = 0; bomb < bombCount; bomb++) {
            int row = random.nextInt(size);
            int col = random.nextInt(size);
            if (dataArray[row][col] == 'B') {
                int[] temp = getNearPosition(row, col, size);
                row = temp[0];
                col = temp[1];
                dataArray[row][col] = 'B';
                bombs[bomb][0] = row;
                bombs[bomb][1] = col;
                continue;
            }
            dataArray[row][col] = 'B';
            bombs[bomb][0] = row;
            bombs[bomb][1] = col;
        }
        return dataArray;
    }

    static char[][] generateDisplayArray(int size) {
        char[][] resultDisplayArray = new char[size][size];
        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {
                resultDisplayArray[row][column] = '?';
            }
        }
        return resultDisplayArray;
    }

    static char[][] generateDataArray(int size) {
        // have to return char array bombs with calculation
        char[][] resultDataArray = new char[size][size];
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                resultDataArray[row][col] = ' ';
            }
        }
        resultDataArray = getBombs(resultDataArray, (size / 2) + 3, size);
        for (int i = 0; i < bombs.length; i++) {
            System.out.print(bombs[i][0] + " ");
            System.out.println(bombs[i][1]);
        }
        resultDataArray = calculateCellValue(size, resultDataArray);
        return resultDataArray;
    }

    static boolean[][] generateBooleanArray(int size) {
        boolean[][] boolArray = new boolean[size][size];
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (dataArray[row][col] == ' ') {
                    boolArray[row][col] = true;
                } else {
                    boolArray[row][col] = false;
                }
            }
        }
        return boolArray;
    }

    static void revealArea(int row, int col, boolean[][] isVisisted, int size) {
        if (row == -1 || col == -1 || row == size || col == size) {
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
        revealArea(row - 1, col, isVisisted, size); // top
        revealArea(row, col - 1, isVisisted, size); // left
        revealArea(row, col + 1, isVisisted, size); // right
        revealArea(row + 1, col, isVisisted, size); // bottom
        revealArea(row - 1, col - 1, isVisisted, size); // top Left
        revealArea(row - 1, col + 1, isVisisted, size); // top right
        revealArea(row + 1, col - 1, isVisisted, size); // bottom left
        revealArea(row + 1, col + 1, isVisisted, size); // bottom right
        isVisisted[row][col] = true;
    }

    static int getNumberOfBombsToBePlaced(int size) {
        return (size / 2) + 3;
    }

    public static void main(String[] args) {
        System.out.println("Minesweeper");
        Scanner scan = new Scanner(System.in);
        int row, col;
        String level = scan.nextLine();
        int size = 15;
        if (level.equals("easy")) {
            size = 10; // 8 bombs
        } else if (level.equals("medium")) {
            size = 15; // 10 bombs
        } else if (level.equals("hard")) {
            size = 30; // 18 bombs
        }
        bombs = new int[getNumberOfBombsToBePlaced(size)][2];
        dataArray = generateDataArray(size);
        displayDB();
        displayArray = generateDisplayArray(size);
        boolean[][] isVisisted = generateBooleanArray(size);
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
                    revealArea(row, col, isVisisted, size);
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
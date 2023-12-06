package com.mycompany.minesweeper;
import java.util.Arrays;

public class Minesweeper {

    static int count = 4;

    static void unflag(int row, int col, char[][] curr, int[][] flagArr) {
        int index = 0;
        if (curr[row][col] == 'F') {
            curr[row][col] = '?';
            for (int i = 0; i < count; i++) {
                if (row == flagArr[i][0]) {
                    if (col == flagArr[i][1]) {
                        index = i;
                    }
                }
            }
            for (int i = index; i < count; i++) {
                flagArr[i] = flagArr[++index];
            }
            count--;
        } else {
            System.out.println("No flag to unflag");
        }
    }

    static void displayBoard(char[][] curr) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.print("| " + curr[i][j] + " |");
            }
            System.out.println("\n");
        }
    }

    public static void main(String[] args) {
        char curr[][] = {{'?', 'F', 'F', '?'},
        {'?', '?', 'F', '?'},
        {'?', '?', 'F', '?'},
        {'?', '?', '?', '?'}};
        int[][] flagArr = new int[5][];

        flagArr[0] = new int[]{0, 1};
        flagArr[1] = new int[]{0, 2};
        flagArr[2] = new int[]{1, 2};
        flagArr[3] = new int[]{2, 2};

        unflag(0, 2, curr, flagArr);
        displayBoard(curr);
        
        System.out.println(Arrays.deepToString(flagArr));
        
        unflag(2, 2, curr, flagArr);
        displayBoard(curr);
        
        System.out.println(Arrays.deepToString(flagArr));
    }
}

package minesweeper;

import java.util.Random;
import java.util.Scanner;

public class Minesweeper {
    static int correct_result = 0;
    static int flag_count=0;
    static int no_of_bomb=0;
    static int boardsize=0;
    static char[][] displayArray ;
    static boolean[][] isVisited;
    static char[][] dataArray;
    /* {
            { '?', '?', '?', '?', '?' },
            { '?', '?', '?', '?', '?' },
            { '?', '?', '?', '?', '?' },
            { '?', '?', '?', '?', '?' },
            { '?', '?', '?', '?', '?' }
    };
    /*static char[][] dataArray = {
            { '1', '1', '1', ' ', ' ' },
            { '1', 'B', '1', ' ', ' ' },
            { '1', '1', '1', ' ', ' ' },
            { ' ', ' ', ' ', '1', '1' },
            { ' ', ' ', ' ', '1', 'B' }
    };
    */

    //static int[][] bomb;
    static void difficultylevel(String str)
    {
        switch (str) {
            case "easy":
                no_of_bomb+=5;
                boardsize+=5;
                boardsetup();
                break;
            case "medium":
                no_of_bomb+=7;
                boardsize+=7;
                boardsetup();
                break;
            case "hard":
                no_of_bomb+=10;
                boardsize+=10;
                boardsetup();
                break;
        }
       
    }
    static void boardsetup()
    {
        Random rn=new Random();
        isVisited=new boolean[boardsize][boardsize];
        dataArray=new char[boardsize][boardsize];
        displayArray=new char[boardsize][boardsize];
        for (int i = 0; i < boardsize; i++) {
            for (int j = 0; j <boardsize; j++) {
                dataArray[i][j]=' ';
                displayArray[i][j]='?';
            }
        }
      for (int i = 0; i <no_of_bomb; i++)
        {  
            int row=rn.nextInt(boardsize);
            int col=rn.nextInt(boardsize);
            if(dataArray[row][col]==' ')
            {
            dataArray[row][col]='B';
            }
            else if((col+1)!=boardsize && dataArray[row][col]=='B')
            {
            dataArray[row][col+1]='B';
            }
            else if((row+1)!=boardsize && dataArray[row][col+1]=='B')
            {
                dataArray[row+1][col]='B';
            }
            else if((row-1)!=-1 && dataArray[row+1][col]=='B')
            {
                dataArray[row-1][col]='B';
            }
            else if((col-1)!=-1 && dataArray[row-1][col]=='B')
            {
                dataArray[row][col-1]='B';
            }
            else
            {
                i--;
            }
        }  
                  setvalueoverbomb();
      
    }
    static void setvalueoverbomb()
    {
        int length=dataArray.length;
        for (int i = 0; i <length; i++) {
            for (int j = 0; j <length; j++) {
                int value=0;
                if(dataArray[i][j]==' ')
                {
                    if((i+1)!=length && dataArray[i+1][j]=='B')
                        value++;
                    if((i-1)!=-1 && dataArray[i-1][j]=='B')
                        value++;
                    if((j+1)!=length && dataArray[i][j+1]=='B')
                        value++;
                    if((j-1)!=-1 && dataArray[i][j-1]=='B')
                        value++;
                    if((i-1)!=-1 &&(j+1)!=length&& dataArray[i-1][j+1]=='B')
                        value++;
                    if((i+1)!=length &&(j+1)!=length && dataArray[i+1][j+1]=='B')
                        value++;
                    if((i+1)!=length &&(j-1)!=-1 && dataArray[i+1][j-1]=='B')
                        value++;
                    if((i-1)!=-1 &&(j-1)!=-1 && dataArray[i-1][j-1]=='B')
                        value++;
                }
                dataArray[i][j]=value>0?(char)('0'+value):' ';
                isVisited[i][j]=(dataArray[i][j]==' ');
            }
            
        }
    }
    static void flag(int row, int col) {
            if(
              dataArray[row][col]=='B')
              correct_result++; 
        if (row >= boardsize || col >= boardsize)
            System.out.println("INVALID INPUT");
        else {
            if (displayArray[row][col] == '?') {
                displayArray[row][col] = 'F';
                flag_count++;
                displayBoard();
            } 
            else if (displayArray[row][col] == 'F')
                System.out.println("Already flaged");
            else
                System.out.println("Can't perform"); // Already displayed value
            }
        
               if(correct_result==no_of_bomb)
                {
                    System.out.println("CONGRAGULATION YOU WON THE GAME");
                    System.exit(0);
                }
               if(flag_count>=no_of_bomb)
        {
            System.out.println("Some flags are not correct ...\n unflag and change the flag position");
        }
    }

    static void displayBoard() {
        //System.out.println("_____________________");

        for (int i = 0; i <boardsize; i++) {
           System.out.print("| ");
            for (int j = 0; j <boardsize; j++) {
                System.out.print(displayArray[i][j] + " |");
            }
            System.out.println();
        }

    }

    static void unflag(int row, int col) {
        if (displayArray[row][col] == 'F') {
            displayArray[row][col] = '?';
            flag_count--;
        } else {
            System.out.println("No flag to unflag");
        }
    }

   static boolean isBomb(int row, int col) {
        return (dataArray[row][col]=='B');
    }

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

    static void revealArea(int row, int col) {
        if (row == -1 || col == -1 || row ==boardsize || col == boardsize) {
            return;
        }
        if (!isVisited[row][col]) {
            if (dataArray[row][col] != 'B') {
                displayArray[row][col] = dataArray[row][col];
            }
            return;
        }
        isVisited[row][col] = false;
        if(dataArray[row][row]!='B')
        displayArray[row][col] = dataArray[row][col];
        revealArea(row - 1, col); // top
        revealArea(row, col - 1); // left
        revealArea(row, col + 1); // right
        revealArea(row + 1, col); // bottom
        revealArea(row - 1, col - 1); // vertical top Left
        revealArea(row - 1, col + 1); // vertical top right
        revealArea(row + 1, col - 1); // vertical bottom left
        revealArea(row + 1, col + 1); // vertical bottom right
        isVisited[row][col] = true;
    }

    public static void main(String[] args) {
         Scanner scan = new Scanner(System.in);
        System.out.println("Minesweeper");
        System.out.println("enter difficulty level:ex(easy,medium,hard)");
        String difficulty=scan.nextLine();
        difficultylevel(difficulty);
        int row, col;
        while (true) {
            System.out.println("Enter the row and column input seperate by space (ex: 1 1)");
            row = scan.nextInt();
            col = scan.nextInt();
            String function = scan.next();
            char func = function.charAt(0);
            switch (func) {
                case ('M'):
                    if (isBomb(row,col))//isBomb(row, col) 
                    {
                        burstBomb();
                        displayBoard();
                        System.out.println("Game Over");
                        return;
                    }
                    revealArea(row, col);
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

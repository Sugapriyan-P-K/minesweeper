
import java.util.Scanner;

public class createBoard {

    static int n, row, col,bomb=0;
    static char[][] dataArray = {};
    static int[][] bombArray = {};

    public static void main(String ar[]) {
        Scanner ob = new Scanner(System.in);
        n = ob.nextInt();
        dataArray = new char[n][n];
        bombArray=new int[n][2];
        row = ob.nextInt();
        col = ob.nextInt();
        generateBoard(n, row, col);
        printBoard();

    }

    static void generateBoard(int n, int row, int col) {
        for (int i = 0; i < n; i++) {
            int rand_row = (int) (Math.random() * n);
            int rand_col = (int) (Math.random() * n);

            if (!isPos(rand_row, rand_col))
            {
                if (!isBomb(rand_row, rand_col)) 
                {
                    dataArray[rand_row][rand_col] = 'B';
                    countBomb(rand_row,rand_col);
                } 
                else
                {
                    movePosition(rand_row, rand_col + 1, n);
                }
            } 
            else 
            {
                movePosition(rand_row, rand_col + 1, n);
            }
        }
    }

    static void movePosition(int rand_row, int rand_col, int n) {

        for (int i = rand_row; i < n; i++) {
            int j;
            for (j = rand_col; j < n; j++)
            {
                if (!isBomb(i, j) && !isPos(i, j))
                {
                    dataArray[i][j] = 'B';
                    countBomb(i,j);
                    break;
                }

            }
            
            if (j == n) 
                j = 0;
            else 
                break;
           
            if (i == n) 
                i = 0;
            
        }

    }

    static boolean isBomb(int row, int col)
    {
        return dataArray[row][col] == 'B';
    }

    static boolean isPos(int rand_row, int rand_col) 
    {
        return (rand_row == row) && (rand_col == col);
    }

    static void printBoard() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (dataArray[i][j] != '\u0000') {
                    System.out.print("|" + dataArray[i][j]);
                } else {
                    System.out.print("| ");
                }
            }
            System.out.print("|\n");
        }
    }
    
    static void countBomb(int bomb_row,int bomb_col)
    {
            
            int row_st=(bomb_row-1<0)?bomb_row:bomb_row-1;
            int row_nd=(bomb_row+1==n)?bomb_row:bomb_row+1;
            int col_st=(bomb_col-1<0)?bomb_col:bomb_col-1;
            int col_nd=(bomb_col+1==n)?bomb_col:bomb_col+1;
            
            for(int j=row_st;j<=row_nd;j++)
            {
                for(int k=col_st;k<=col_nd;k++)
                {
                    if(dataArray[j][k] >='1')
                    {
                        if(dataArray[j][k]!='B')
                        {
                            dataArray[j][k]=(char)(dataArray[j][k]+1);
                        }
                    }
                    else
                    {
                        dataArray[j][k]='1';
                    }
                }
            }
        
    
    }

}

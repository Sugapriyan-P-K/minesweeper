import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class minesweeper {
    static int boardSize = 4;
    static int inputRow;
    static int inputCol;
    static char action;
    static int flagCount;
    static int plottedTiles = 0;
    static char[][] baseBoard = new char[boardSize][];
    static char[][] currentBoard = new char[boardSize][];
    static Scanner sc = new Scanner(System.in);
    static Random random = new Random();

    static void createBoard() {
        for (int i = 0; i < boardSize; i++) {
            baseBoard[i] = new char[boardSize];
            Arrays.fill(baseBoard[i], '0');
        }
    }

    static void placeBombs() {
        int row, col;
        int numberOfBombs = boardSize * 2;
        for (int i = 0; i < numberOfBombs; i++) {
            row = random.nextInt(boardSize);
            col = random.nextInt(boardSize);
            if (baseBoard[row][col] != 'B') {
                baseBoard[row][col] = 'B';
                placeNumbers(row, col);
            } else {
                int flag = 0;
                for (int currRow = 0; currRow < boardSize; currRow++) {
                    if (currRow != row && canBombPlaced(currRow, col)) {
                        flag = 1;
                        break;
                    }
                }
                if (flag == 0) {
                    for (int currCol = 0; currCol < boardSize; currCol++) {
                        if (currCol != col && canBombPlaced(row, currCol)) {
                            flag = 1;
                            break;
                        }
                    }
                }
                if (flag == 0) {
                    i++;
                }
            }

        }
    }

    static boolean canBombPlaced(int row, int col) {
        if (baseBoard[row][col] != 'B') {
            baseBoard[row][col] = 'B';
            placeNumbers(row, col);
            return true;
        }
        return false;
    }

    static void displayBoard(char[][] board) {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                System.out.print("| " + board[i][j] + " ");
            }
            System.out.println();
        }
    }

    static void placeNumbers(int row, int col) {
        for (int i = (row > 0) ? row - 1 : 0; i <= ((row + 1 == boardSize) ? row : row + 1); i++) {
            for (int j = (col > 0) ? col - 1 : 0; j <= ((col + 1 == boardSize) ? col : col + 1); j++) {
                if (i != row || j != col) {
                    if (baseBoard[i][j] != 'B') {
                        baseBoard[i][j] += 1;
                    }
                }
            }
        }
    }

    static void getInput() {
        System.out.println("Enter row: ");
        inputRow = sc.nextInt();
        System.out.println("Enter column: ");
        inputCol = sc.nextInt();
    }

    static boolean openTile(int row, int col) {
        if (baseBoard[row][col] == 'B') {
            burstBomb();
            return false;
        } else {
            currentBoard[row][col] = baseBoard[row][col];
            return true;
        }
    }

    static void burstBomb() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (currentBoard[i][j] == 'F' && baseBoard[i][j] != 'B') {
                    currentBoard[i][j] = 'X';
                } else {
                    if (currentBoard[i][j] == '?' && baseBoard[i][j] == 'B') {
                        currentBoard[i][j] = '*';
                    }
                }
            }
        }
        displayBoard(currentBoard);
    }

    static void flagTile(int row, int col) {
        if (currentBoard[row][col] == '?') {
            currentBoard[row][col] = 'F';
            flagCount++;
        } else if (currentBoard[row][col] == 'F') {
            System.out.println("Already flagged");
        }
    }

    static void unflagTile(int row, int col) {
        if (currentBoard[row][col] == 'F') {
            currentBoard[row][col] = '?';
            flagCount--;
        } else {
            System.out.println("No flag to unflag");
        }
    }

    static boolean isWon() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (currentBoard[i][j] == 'F' && baseBoard[i][j] == 'B') {
                    ++plottedTiles;
                }
            }
        }
        if (flagCount == plottedTiles) {
            return true;
        }
        return false;
    }

    static void startGame() {
        while (true) {
            displayBoard(currentBoard);
            getInput();
            System.out.println("1. Open - O \n2. Flag - F \n3. Unflag U");
            action = sc.next().charAt(0);
            switch (action) {
                case 'O':
                    if (!(openTile(inputRow, inputCol))) {
                        System.out.println("X-------------Game Over-------------X");
                        System.exit(0);
                    }
                    ;
                    break;
                case 'F':
                    flagTile(inputRow, inputCol);
                    if (flagCount == boardSize * 2 && isWon()) {
                        System.out.println("*-------------Game Won-------------*");
                        burstBomb();
                        System.exit(0);
                    }
                    break;
                case 'U':
                    unflagTile(inputRow, inputCol);
                    break;
                default:
                    System.out.println("Invalid Option");
                    break;
            }
        }
    }

    public static void main(String[] args) {
        createBoard();
        placeBombs();
        for (int i = 0; i < boardSize; i++) {
            currentBoard[i] = new char[boardSize];
            Arrays.fill(currentBoard[i], '?');
        }
        startGame();
    }
}

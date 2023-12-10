import java.util.Random;
import java.util.Scanner;
import java.util.Stack;

public class puzzlegame {
    static int size = 4;
    static int[][] baseBoard = new int[size][size];
    static int[][] currentBoard = new int[baseBoard.length][];;
    static int[] spaceIndex = { size - 1, size - 1 };
    static int row = size - 1;
    static int col = size - 1;
    static Random random = new Random();
    static Scanner sc = new Scanner(System.in);
    static Stack<Character> stack = new Stack<>();
    static Stack<Character> userMove = new Stack<>();

    static void createBoard() {
        int number = 1, i, j;
        for (i = 0; i < size; i++) {
            for (j = 0; j < size; j++) {
                baseBoard[i][j] = number++;
            }
        }
        baseBoard[size - 1][size - 1] = 0;
    }

    static void shuffle() {
        int i, action;
        char moves[] = { 'T', 'B', 'R', 'L' };
        for (i = 1; i <= 5; i++) {
            action = random.nextInt(4);
            char move = moves[action];
            switch (move) {
                case 'T':
                    if ((row - 1) >= 0) {
                        move(row, col, row - 1, col, baseBoard);
                        row = row - 1;
                        stackOps(stack, move, 'B');
                    }
                    break;
                case 'B':
                    if ((row + 1) < size) {
                        move(row, col, row + 1, col, baseBoard);
                        row = row + 1;
                        stackOps(stack, move, 'T');
                    }
                    break;
                case 'R':
                    if ((col + 1) < size) {
                        move(row, col, row, col + 1, baseBoard);
                        col = col + 1;
                        stackOps(stack, move, 'L');
                    }
                    break;
                case 'L':
                    if ((col - 1) >= 0) {
                        move(row, col, row, col - 1, baseBoard);
                        col = col - 1;
                        stackOps(stack, move, 'R');
                    }
                    break;
            }
        }
        spaceIndex[0] = row;
        spaceIndex[1] = col;
        setCurrentBoard();

    }

    static void setCurrentBoard() {
        for (int i = 0; i < currentBoard.length; ++i) {
            currentBoard[i] = new int[baseBoard[i].length];
            for (int j = 0; j < currentBoard[i].length; ++j) {
                currentBoard[i][j] = baseBoard[i][j];
            }
        }
    }

    static void move(int row1, int col1, int row2, int col2, int[][] board) {
        int curr = board[row1][col1];
        board[row1][col1] = board[row2][col2];
        board[row2][col2] = curr;
    }

    static void stackOps(Stack<Character> temp, char move, char check) {
        if (!(temp.empty()) && temp.peek() == check) {
            temp.pop();
        } else {
            temp.push(move);
        }
    }

    static void displayBoard(int[][] board) {
        int i, j;
        for (i = 0; i < size; i++) {
            for (j = 0; j < size; j++) {
                System.out.print("| " + board[i][j] + " | ");
            }
            System.out.println();
        }
    }

    static char getInput() {
        char input;
        System.out.println(
                "Enter the direction to shift. 1. T -Top 2. B - Bottom 3. R - Right 4. L - Left \nTo Undo - Press U \nFor Hint - Press H \nTo begin again, Press A : ");
        input = sc.next().charAt(0);
        return input;
    }

    static void resetBoard() {
        row = spaceIndex[0];
        col = spaceIndex[1];
        setCurrentBoard();
    }

    static void hint() {
        if (!(userMove.empty())) {
            undo();
        }
        char lastMove = !(stack.empty()) ? stack.peek() : ' ';
        switch (lastMove) {
            case 'T':
                System.out.println("Move Bottom");
                break;
            case 'B':
                System.out.println("Move Top");
                break;
            case 'R':
                System.out.println("Move Left");
                break;
            case 'L':
                System.out.println("Move Right");
                break;
            default:
                System.out.println("No hints");
                break;
        }
    }

    static void undo() {
        char lastMove = !(userMove.empty()) ? userMove.peek() : ' ';
        switch (lastMove) {
            case 'T':
                doAction('B');
                break;
            case 'B':
                doAction('T');
                break;
            case 'R':
                doAction('L');
                break;
            case 'L':
                doAction('R');
                break;
            default:
                System.out.println("No moves to undo");
                break;
        }
    }

    static void checkPlaces(int[][] board) {
        int i, j, number = 1, count = 0, won = 0;
        for (i = 0; i < size; i++) {
            for (j = 0; j < size; j++) {
                if (board[i][j] == number++) {
                    ++count;
                } else {
                    if (count == 15) {
                        won = 1;
                        break;
                    }
                    break;
                }
            }
        }
        if (won == 1) {
            System.out.println("Game Won");
            System.exit(0);
        }
    }

    static void doAction(char action) {
        switch (action) {
            case 'T':
                if (row - 1 < 0) {
                    System.out.println("Cannot shift from top");
                } else {
                    move(row, col, row - 1, col, currentBoard);
                    row = row - 1;
                    stackOps(userMove, action, 'B');
                }
                break;
            case 'B':
                if (row + 1 >= size) {
                    System.out.println("Cannot shift from bottom");
                } else {
                    move(row, col, row + 1, col, currentBoard);
                    row = row + 1;
                    stackOps(userMove, action, 'T');
                }
                break;
            case 'R':
                if (col + 1 >= size) {
                    System.out.println("Cannot shift from right");
                } else {
                    move(row, col, row, col + 1, currentBoard);
                    col = col + 1;
                    stackOps(userMove, action, 'L');
                }
                break;
            case 'L':
                if (col - 1 < 0) {
                    System.out.println("Cannot shift from left");
                } else {
                    move(row, col, row, col - 1, currentBoard);
                    col = col - 1;
                    stackOps(userMove, action, 'R');
                }
                break;
            case 'U':
                undo();
                break;
            case 'H':
                if (userMove.empty())
                    hint();
                else
                    undo();
                break;
            case 'A':
                resetBoard();
                break;
            default:
                System.out.println("Invalid action");
                break;
        }
    }

    static void start() {
        while (true) {
            displayBoard(currentBoard);
            char action = getInput();
            doAction(action);
            checkPlaces(currentBoard);
            System.out.println();
        }
    }

    public static void main(String[] args) {
        createBoard();
        shuffle();
        start();
    }
}

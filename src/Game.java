import java.util.Scanner;

public class Game {
    private boolean running;
    Board board;
    Scanner input = new Scanner(System.in);

    public Game() {
        running = true;
        board = new Board();
    }

    // Get a valid user input (1-9)
    public int getInput() {
        while (true) {
            System.out.print("Input a position [1-9]: ");
            int user_input = input.nextInt();
            if (user_input >= 1 && user_input <= 9) {
                if (board.values[user_input - 1].equals(Values.NONE)) {
                    return user_input;
                }
            }
        }
    }

    // The definitive way to clear the screen
    public void clearScreen() {
        for (int i = 0; i < 1; i++) {
            System.out.println();
        }
    }

    // This atrocity has to exist because I want to use magic squares
    // and I don't want to change how values are stored.
    int valueToInt(Values value) {
        return switch (value) {
            case CROSS -> 2;
            case CIRCLE -> 1;
            case NONE -> 0;
        };
    }

    // Check win using a magic square 'cause why not.
    // It's not even the best solution when the values are enums, but I just want to try :)
    public Values checkWin() {
        int[] magic_square = {8, 1, 6, 3, 5, 7, 4, 9, 2};

        for (int y = 0; y < board.board_size; y++) {
            int row_sum = 0;
            int col_sum = 0;
            for (int x = 0; x < board.board_size; x++) {

                // This is an atrocity
                row_sum += valueToInt(board.values[x + y * board.board_size]) * magic_square[x + y * 3];
                col_sum += valueToInt(board.values[y + x * board.board_size]) * magic_square[y + x * 3];
            }

            if (row_sum == 15 || col_sum == 15) {
                return Values.CIRCLE;
            }
            if (row_sum == 30 || col_sum == 30) {
                return Values.CROSS;
            }
        }

        // Check diagonals
        int mainDiagonalSum = 0;
        int antiDiagonalSum = 0;
        for (int i = 0; i < board.board_size; i++) {
            mainDiagonalSum += valueToInt(board.values[i + i * board.board_size]) * magic_square[i + i * 3];
            antiDiagonalSum += valueToInt(board.values[(i + 1) * board.board_size - 1 - i]) * magic_square[i + i * 3];
        }

        if (mainDiagonalSum == 15 || antiDiagonalSum == 15) {
            return Values.CIRCLE;
        } else if (mainDiagonalSum == 30 || antiDiagonalSum == 30) {
            return Values.CROSS;
        }
        return Values.NONE;
    }

    public void run() {
        while (running) {
            clearScreen();
            board.render();

            int pos = getInput() - 1; // user gets asked 1-9 for simplicity
            board.values[pos] = Values.CROSS;

            if (checkWin().equals(Values.CROSS)) {
                running = false;
                System.out.println("CROSS WON");
            } else if (checkWin().equals(Values.CIRCLE)) {
                running = false;
                System.out.println("CIRCLE WON");
            }
        }
    }
}

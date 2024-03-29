import java.util.Random;
import java.util.Scanner;

public class Game {
    private boolean running;
    Board board;

    Values player_value = Values.NONE;
    Values opponent_value = Values.NONE;

    Scanner input = new Scanner(System.in);

    public Game() {
        running = true;
        board = new Board();
    }

    // TODO: Fix inputting chars
    // Get a valid user input (1-9)
    public int getInput() {
        while (true) {

            while (!input.hasNextInt())
            {
                System.out.print("Input a position [1-9]: ");
                input.nextLine();
            }

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

    void opponentMove() {
        Random random = new Random();
        while (true) {
            int pos = random.nextInt(0, 8);
            if (board.values[pos].equals(Values.NONE)) {
                board.values[pos] = opponent_value;
                return;
            }
        }
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

        // Select player
        while (player_value.equals(Values.NONE)) {
            System.out.print("Select player [X, O]: ");
            switch (input.next().toLowerCase().charAt(0)) {
                case 'x' -> {
                    opponent_value = Values.CIRCLE;
                    player_value = Values.CROSS;
                }
                case 'o' -> {
                    opponent_value = Values.CROSS;
                    player_value = Values.CIRCLE;
                }
            }
        }

        // First render
        clearScreen();
        board.render();

        while (running) {

            int pos = getInput() - 1; // user gets asked 1-9 for simplicity
            board.values[pos] = player_value;

            if (!board.filled())
            {
                opponentMove();
            }

            clearScreen();
            board.render();

            Values winner = checkWin();

            // This is a mess
            if (winner.equals(Values.CROSS)) {
                running = false;
                System.out.println("X Won!");
            } else if (winner.equals(Values.CIRCLE)) {
                running = false;
                System.out.println("O Won!");
            } else if (winner.equals(Values.NONE)) {
                if (board.filled()) {
                    running = false;
                    System.out.println("Draw!");
                }
            }

        }
    }
}

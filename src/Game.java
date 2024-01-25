import java.util.Arrays;
import java.util.Scanner;

public class Game {
    private boolean running = false;
    Board board;
    Scanner input = new Scanner(System.in);

    public Game(int board_size) {
        running = true;
        board = new Board(board_size);
        Arrays.fill(board.board, Values.CROSS);
    }

    public int getInput() {
        int user_input = -1;
        while (user_input < 1 || user_input > 9) {
            System.out.print("Input a position [1-9]: ");
            user_input = input.nextInt();
        }
        return user_input;
    }

    public void clearScreen() {
        for (int i = 0; i < 20; i++)
        {
            System.out.println();
        }
    }

    public void run() {
        while (running) {
            clearScreen();
            board.render();
            int pos = getInput() - 1;
            board.board[pos] = Values.CIRCLE;
        }
    }
}

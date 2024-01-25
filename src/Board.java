import java.util.Arrays;

public class Board {
    public int board_size = 3;
    public Values[] values = new Values[board_size * board_size];

    public Board() {
        Arrays.fill(values, Values.NONE);
    }

    // Probably not the best solution
    public void render() {
        for (int y = 0; y < board_size; y++) {
            for (int ch = 0; ch <= board_size * 4; ch++) {
                System.out.print('-');
            }
            System.out.println();
            for (int x = 0; x < board_size; x++) {

                System.out.print("| ");

                switch (values[x + y * board_size]) {
                    case CIRCLE -> System.out.print("O ");
                    case CROSS -> System.out.print("X ");
                    case NONE -> System.out.print((x + y * board_size + 1) + " ");
                    default -> throw new IllegalStateException("Unexpected value: " + values[x + y * board_size]);
                }
            }
            System.out.println('|');
        }
        for (int ch = 0; ch <= board_size * 4; ch++) {
            System.out.print('-');
        }
        System.out.println();
    }
}

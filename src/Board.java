public class Board {
    public int board_size;
    public Values[] board;

    public Board(int board_size) {
        this.board_size = board_size;
        board = new Values[board_size * board_size];
    }

    public void render() {
        for (int y = 0; y < board_size; y++) {
            for (int ch = 0; ch <= board_size * 4; ch++)
            {
                System.out.print('-');
            }
            System.out.println();
            for (int x = 0; x < board_size; x++) {

                System.out.print("| ");

                switch (board[x + y * board_size]) {
                    case CIRCLE -> {
                        System.out.print("O ");
                    }
                    case CROSS -> {
                        System.out.print("X ");
                    }
                    case NONE -> {
                        System.out.print("  ");
                    }
                }
            }
            System.out.println('|');
        }
        for (int ch = 0; ch <= board_size * 4; ch++)
        {
            System.out.print('-');
        }
        System.out.println();
    }
}

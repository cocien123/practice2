public class GameOfLife {
    private int width;
    private int height;
    private boolean[][] board;

    GameOfLife(int width, int height) {
        this.width = width;
        this.height = height;
        this.board = new boolean[height][width];
    }

    public void setAlive(int x, int y) {
        board[y][x] = true;
    }

    public boolean isAlive(int x, int y) {
        return board[y][x];
    }

    public int countNeighbors(int x, int y) {
        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue;
                if (x + j >= 0 && x + j < width && y + i >= 0 && y + i < height) {
                    if (board[y + i][x + j]) count++;
                }
            }
        }
        return count;
    }

    public void nextGeneration() {
        boolean[][] newBoard = new boolean[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int neighbors = countNeighbors(x, y);
                if (board[y][x]) {
                    newBoard[y][x] = neighbors == 2 || neighbors == 3;
                } else {
                    newBoard[y][x] = neighbors == 3;
                }
            }
        }

        board = newBoard;
    }

    public void display() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                System.out.print(board[y][x] ? "O" : ".");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        GameOfLife game = new GameOfLife(10, 10);

        // 设置初始状态，例如一个小型的"滑翔机"模式
        game.setAlive(1, 0);
        game.setAlive(2, 1);
        game.setAlive(0, 2);
        game.setAlive(1, 2);
        game.setAlive(2, 2);

        // 运行游戏
        while (true) {
            game.display();
            game.nextGeneration();
            Thread.sleep(1000); // 延迟 1 秒
            System.out.println("\n\n\n");
        }
    }
}

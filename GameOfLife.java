import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;

class Cell {
    private int x, y;

    Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public List<Cell> getNeighbours() {
        List<Cell> neighbours = new ArrayList<>();
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx == 0 && dy == 0) continue;
                neighbours.add(new Cell(x + dx, y + dy));
            }
        }
        return neighbours;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cell)) return false;
        Cell cell = (Cell) o;
        return x == cell.x && y == cell.y;
    }

    @Override
    public int hashCode() {
        return 31 * x + y;
    }
}

public class GameOfLife {
    private Set<Cell> liveCells = new HashSet<>();

    public GameOfLife(List<Cell> initialLiveCells) {
        liveCells.addAll(initialLiveCells);
    }

    public Set<Cell> getLiveCells() {
        return liveCells;
    }

    public void nextGeneration() {
        Set<Cell> newLiveCells = new HashSet<>();
        Set<Cell> potentialCells = new HashSet<>(liveCells);

        for (Cell liveCell : liveCells) {
            potentialCells.addAll(liveCell.getNeighbours());
        }

        for (Cell cell : potentialCells) {
            int liveNeighbours = countLiveNeighbours(cell);
            if (liveCells.contains(cell)) {
                if (liveNeighbours == 2 || liveNeighbours == 3) {
                    newLiveCells.add(cell);
                }
            } else {
                if (liveNeighbours == 3) {
                    newLiveCells.add(cell);
                }
            }
        }

        liveCells = newLiveCells;
    }

    private int countLiveNeighbours(Cell cell) {
        int count = 0;
        for (Cell neighbour : cell.getNeighbours()) {
            if (liveCells.contains(neighbour)) {
                count++;
            }
        }
        return count;
    }

    private static void render(Set<Cell> cells) {
        final int width = 40;
        final int height = 40;
        char[][] display = new char[width][height];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                display[i][j] = '.';
            }
        }

        for (Cell cell : cells) {
            if (cell.getX() >= 0 && cell.getX() < width && cell.getY() >= 0 && cell.getY() < height) {
                display[cell.getY()][cell.getX()] = 'O';
            }
        }

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                System.out.print(display[i][j]);
            }
            System.out.println();
        }
    }

    public static void main(String[] args) throws InterruptedException {

        List<Cell> glider = List.of(new Cell(0, 2), new Cell(1, 0), new Cell(1, 2), new Cell(2, 1), new Cell(2, 2));
        GameOfLife game = new GameOfLife(glider);

        for (int i = 0; i < 30; i++) {
            System.out.println("Generation: " + (i + 1));
            render(game.getLiveCells());
            game.nextGeneration();
            Thread.sleep(1000);
        }
    }
}

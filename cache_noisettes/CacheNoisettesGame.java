import java.util.*;

public class CacheNoisettesGame {
    static final int SIZE = 4;
    static Tile[][] grid = new Tile[SIZE][SIZE];
    static List<Squirrel> squirrels = new ArrayList<>();
    static Set<Position> holes = Set.of(
        new Position(0, 1),
        new Position(1, 3),
        new Position(2, 0),
        new Position(3, 2)
    );
    static Position flower = null;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        setupBoard();

        while (true) {
            printBoard();
            if (checkWin()) {
                System.out.println("🎉 All nuts are in the holes! You win!");
                break;
            }

            System.out.print("Select squirrel (0 to " + (squirrels.size() - 1) + "): ");
            int idx = scanner.nextInt();
            if (idx < 0 || idx >= squirrels.size()) continue;

            System.out.print("Move (WASD): ");
            char move = scanner.next().toUpperCase().charAt(0);
            Direction dir = switch (move) {
                case 'W' -> Direction.UP;
                case 'A' -> Direction.LEFT;
                case 'S' -> Direction.DOWN;
                case 'D' -> Direction.RIGHT;
                default -> null;
            };

            if (dir != null) {
                Squirrel s = squirrels.get(idx);
                if (canMove(s, dir)) {
                    s.move(dir);
                    Position head = s.getHead();
                    if (isHole(head) && s.hasNut && !grid[head.getRow()][head.getCol()].hasNut) {
                        grid[head.getRow()][head.getCol()].hasNut = true;
                        s.hasNut = false;
                    }
                }
            }
        }
    }

    static void setupBoard() {
        for (int r = 0; r < SIZE; r++)
            for (int c = 0; c < SIZE; c++)
                grid[r][c] = new Tile(isHole(new Position(r, c)));

        squirrels.add(new Squirrel(new Position(3, 0), Direction.RIGHT));
        squirrels.add(new Squirrel(new Position(0, 3), Direction.DOWN));

        // Uncomment to use flower
        // flower = new Position(1, 1);
        // grid[flower.getRow()][flower.getCol()].hasFlower = true;
    }

    static boolean isHole(Position pos) {
        return holes.contains(pos);
    }

    static boolean canMove(Squirrel s, Direction dir) {
        for (Position p : s.getBodyPositions()) {
            Position np = p.move(dir);
            if (!np.inBounds() || (flower != null && np.equals(flower))) return false;
            for (Squirrel other : squirrels) {
                if (other == s) continue;
                if (other.getBodyPositions().contains(np)) return false;
            }
        }
        return true;
    }

    static boolean checkWin() {
        for (Position hole : holes)
            if (!grid[hole.getRow()][hole.getCol()].hasNut) return false;
        return true;
    }

    static void printBoard() {
        char[][] display = new char[SIZE][SIZE];
        for (int r = 0; r < SIZE; r++)
            for (int c = 0; c < SIZE; c++) {
                display[r][c] = grid[r][c].isHole ? 'O' : '.';
                if (grid[r][c].hasNut) display[r][c] = '*';
                if (grid[r][c].hasFlower) display[r][c] = 'F';
            }

        for (int i = 0; i < squirrels.size(); i++) {
            Squirrel s = squirrels.get(i);
            for (Position p : s.getBodyPositions())
                if (p.inBounds()) display[p.getRow()][p.getCol()] = (char) ('0' + i);
        }

        for (char[] row : display) {
            for (char ch : row) System.out.print(ch + " ");
            System.out.println();
        }
    }
}

class Tile {
    boolean isHole;
    boolean hasNut = false;
    boolean hasFlower = false;

    Tile(boolean isHole) {
        this.isHole = isHole;
    }
}

class Squirrel {
    private Position head;
    final Direction facing;
    boolean hasNut = true;

    Squirrel(Position head, Direction facing) {
        this.head = head;
        this.facing = facing;
    }

    Position getHead() {
        return head;
    }

    List<Position> getBodyPositions() {
        List<Position> list = new ArrayList<>();
        list.add(head);
        switch (facing) {
            case UP -> list.add(head.add(-1, 0));
            case DOWN -> list.add(head.add(1, 0));
            case LEFT -> list.add(head.add(0, -1));
            case RIGHT -> list.add(head.add(0, 1));
        }
        return list;
    }

    void move(Direction dir) {
        head = head.move(dir);
    }
}

class Position {
    private final int row;
    private final int col;

    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public boolean inBounds() {
        return row >= 0 && row < 4 && col >= 0 && col < 4;
    }

    public Position move(Direction d) {
        return switch (d) {
            case UP -> new Position(row - 1, col);
            case DOWN -> new Position(row + 1, col);
            case LEFT -> new Position(row, col - 1);
            case RIGHT -> new Position(row, col + 1);
        };
    }

    public Position add(int dr, int dc) {
        return new Position(row + dr, col + dc);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return row == position.row && col == position.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }
}

enum Direction {
    UP, DOWN, LEFT, RIGHT
}

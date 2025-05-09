import java.util.Scanner;

public class SquirrelGoNuts {

    // Map symbols
    static final char EMPTY = ' ';
    static final char WALL = '#';
    static final char PLAYER = 'S';
    static final char NUT = 'N';
    static final char HOLE = 'O';
    static final char NUT_IN_HOLE = '*';

    static char[][] map = {
        {'#','#','#','#','#','#','#'},
        {'#',' ',' ','N',' ','O','#'},
        {'#',' ','#','#',' ',' ','#'},
        {'#',' ',' ','S',' ',' ','#'},
        {'#','#','#','#','#','#','#'}
    };

    static int playerX = 3;
    static int playerY = 3;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            printMap();
            System.out.print("Move (WASD): ");
            String input = scanner.nextLine().toUpperCase();

            if (input.isEmpty()) continue;
            char move = input.charAt(0);

            int dx = 0, dy = 0;
            switch (move) {
                case 'W': dx = -1; break;
                case 'S': dx = 1; break;
                case 'A': dy = -1; break;
                case 'D': dy = 1; break;
                default: continue;
            }

            movePlayer(dx, dy);

            if (checkWin()) {
                printMap();
                System.out.println("🎉 You won!");
                break;
            }
        }

        scanner.close();
    }

    static void printMap() {
        for (char[] row : map) {
            for (char tile : row) {
                System.out.print(tile);
            }
            System.out.println();
        }
    }

    static void movePlayer(int dx, int dy) {
        int newX = playerX + dx;
        int newY = playerY + dy;

        char dest = map[newX][newY];

        if (dest == WALL) return;

        // Pushing a nut
        if (dest == NUT || dest == NUT_IN_HOLE) {
            int nutX = newX + dx;
            int nutY = newY + dy;
            char afterNut = map[nutX][nutY];

            if (afterNut == EMPTY || afterNut == HOLE) {
                // Move nut
                map[nutX][nutY] = (afterNut == HOLE) ? NUT_IN_HOLE : NUT;
                map[newX][newY] = EMPTY;
            } else {
                return; // Blocked
            }
        }

        // Move player
        map[playerX][playerY] = EMPTY;
        playerX = newX;
        playerY = newY;
        map[playerX][playerY] = PLAYER;
    }

    static boolean checkWin() {
        for (char[] row : map) {
            for (char tile : row) {
                if (tile == HOLE || tile == NUT) return false;
            }
        }
        return true;
    }
}

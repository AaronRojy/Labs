import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class d extends JPanel implements ActionListener, KeyListener, MouseListener {
    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 150;
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
    static final int DELAY = 75;

    final int[] x = new int[GAME_UNITS];
    final int[] y = new int[GAME_UNITS];

    int[][] boardArray = new int[6][6]; // [x axis][y axis]
    int bodyParts = 1;
    int squirrels = 1;
    int squirrelX;
    int squirrelY;
    int applesEaten;
    int appleX;
    int appleY;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;

    d() {
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(this);
        this.addMouseListener(this);

        startGame();
    }

    public void startGame() {
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();

        resetBoard();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
            g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
            g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
        }

        for (int i = 0; i < boardArray.length; i++) {
            for (int j = 0; j < boardArray[i].length; j++) {
                int cell = boardArray[i][j];
                int x = i * UNIT_SIZE;
                int y = j * UNIT_SIZE;

                if (cell == 1) {
                    g.setColor(Color.red);
                    g.fillOval(x, y, UNIT_SIZE, UNIT_SIZE);
                } else if (cell == 2) {
                    g.setColor(Color.blue);
                    g.fillOval(x, y, UNIT_SIZE, UNIT_SIZE);
                } else if (cell == 9) {
                    g.setColor(Color.gray);
                    g.fillRect(x, y, UNIT_SIZE, UNIT_SIZE);
                }
            }
        }
    }

    public void clearBoard() {
        for (int i = 0; i < boardArray.length; i++) {
            for (int j = 0; j < boardArray[i].length; j++) {
                boardArray[i][j] = 0;
            }
        }
    }

    public void resetBoard() {
        setBoardArrayEmpty();
        setBoardArrayBorders();
        generateRandomPositions();
        populateBoardArrayWithRandomHoles();
        populateBoardArrayWithRandomSquirrel();
        print3dArray();
    }

    public void generateRandomPositions() {
        appleX = random.nextInt(4) + 1;
        appleY = random.nextInt(4) + 1;
        squirrelX = random.nextInt(4) + 1;
        squirrelY = random.nextInt(4) + 1;
    }

    public void print3dArray() {
        for (int i = 0; i < boardArray.length; i++) {
            for (int j = 0; j < boardArray[i].length; j++) {
                System.out.print(boardArray[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public void setBoardArrayEmpty() {
        for (int i = 0; i < boardArray.length; i++) {
            for (int j = 0; j < boardArray[i].length; j++) {
                boardArray[i][j] = 0;
            }
        }
    }

    public void setBoardArrayBorders() {
        for (int i = 0; i < boardArray.length; i++) {
            for (int j = 0; j < boardArray[i].length; j++) {
                if (i == 0 || i == 5 || j == 0 || j == 5) {
                    boardArray[i][j] = 9; // border
                }
            }
        }
    }

    public void populateBoardArrayWithRandomHoles() {
        boardArray[appleX][appleY] = 1; // 1 = hole
    }

    public void populateBoardArrayWithRandomSquirrel() {
        boardArray[squirrelX][squirrelY] = 2; // 2 = squirrel
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // No animation loop needed right now
    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("Key pressed: " + e.getKeyCode());
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_W || key == KeyEvent.VK_UP) {
            System.out.println("Move Up");
            direction = 'U';
            move();
        } else if (key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) {
            System.out.println("Move Down");
            direction = 'D';
            move();
        } else if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) {
            System.out.println("Clear Screen");
            clearBoard();
            repaint();
        } else if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) {
            System.out.println("Redraw Board");
            resetBoard();
            repaint();
        }
    }

    public void move() {
        // Optional: update squirrel position based on direction
    }

    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}

    // Main method for running
    public static void main(String[] args) {
        JFrame frame = new JFrame("Game");
        d panel = new d();
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}

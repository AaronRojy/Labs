import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener, KeyListener, MouseListener {
    int SCREEN_WIDTH = 600;
    int SCREEN_HEIGHT = 600;
    int UNIT_SIZE = 100;
    int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;

    int[] x = new int[GAME_UNITS];
    int[] y = new int[GAME_UNITS];

    int[][] boardArray = new int[6][6]; // [x axis][y axis]
    int bodyParts = 1;
    int squirrels = 1;
    int squirrelX;
    int squirrelY;
    int nutX;
    int nutY;
    int holeX;
    int holeY;
    int flowerX;
    int flowerY;

    int nut_Code = 1;
    int squirrel_Code = 2;
    int hole_Code = 3;
    int border_Code = 9;
    int flower_code =6;//futioally same border
    int chain_length = 0;

    int nutsEaten;
    int direction = 1;//WASD  = 1234
    int levelCompleat = 0;

    boolean running = false;

    boolean legal_Move = false;

    Timer timer;
    Random random;

    GamePanel() {
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(this);
        //this.addMouseListener(this);

        startGame();
    }

    public void startGame() {
        //newNut();
        running = true;

        setBoardArrayEmpty();
        setBoardArrayBorders();
        print3dArray();

        levelOneSetup();
        print3dArray();

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) { 
        Image borderImg= new ImageIcon("C:\\Users\\44742\\OneDrive - Lancaster University\\Labs\\java\\cache_noisettes\\images\\border.png").getImage();
        Image nutImg = new ImageIcon("C:\\Users\\44742\\OneDrive - Lancaster University\\Labs\\java\\cache_noisettes\\images\\nut.png").getImage();
        Image squirrelImg = new ImageIcon("C:\\Users\\44742\\OneDrive - Lancaster University\\Labs\\java\\cache_noisettes\\images\\squirrel.png").getImage();
        Image holeImg = new ImageIcon("C:\\Users\\44742\\OneDrive - Lancaster University\\Labs\\java\\cache_noisettes\\images\\hole.png").getImage();
        Image emptyImg = new ImageIcon("C:\\Users\\44742\\OneDrive - Lancaster University\\Labs\\java\\cache_noisettes\\images\\grass.png").getImage();
        Image flowerImg = new ImageIcon("C:\\Users\\44742\\OneDrive - Lancaster University\\Labs\\java\\cache_noisettes\\images\\flower.png").getImage();

        Image squirrelHoleImg = new ImageIcon("C:\\Users\\44742\\OneDrive - Lancaster University\\Labs\\java\\cache_noisettes\\images\\squirrelHole.png").getImage();
        Image nutHoleImg = new ImageIcon("C:\\Users\\44742\\OneDrive - Lancaster University\\Labs\\java\\cache_noisettes\\images\\nutHole.png").getImage();
        if (running==true) {
            for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
                g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
            }
            int totalSquirrels = 0;
            int totalNut = 0;
            int totalHole = 0;
            for (int i = 0; i < boardArray.length; i++) {
                for (int j = 0; j < boardArray[i].length; j++) {
                    int cell = boardArray[i][j];

                    switch (cell) {
                        case 0:
                            g.drawImage(emptyImg, i * UNIT_SIZE, j * UNIT_SIZE, UNIT_SIZE, UNIT_SIZE, this);
                            break;
                        case 1:
                            g.drawImage(nutImg, i * UNIT_SIZE, j * UNIT_SIZE, UNIT_SIZE, UNIT_SIZE, this);
                            totalNut = totalNut + 1;
                            break;
                        case 2:
                            g.drawImage(squirrelImg, i * UNIT_SIZE, j * UNIT_SIZE, UNIT_SIZE, UNIT_SIZE, this);
                            totalSquirrels = totalSquirrels+ 1;
                            break;
                        case 3:
                            g.drawImage(holeImg, i * UNIT_SIZE, j * UNIT_SIZE, UNIT_SIZE, UNIT_SIZE, this);
                            break;
                        case 4:
                            g.drawImage(squirrelHoleImg, i * UNIT_SIZE, j * UNIT_SIZE, UNIT_SIZE, UNIT_SIZE, this);
                            totalSquirrels = totalSquirrels+ 1;
                            break;
                        case 5:
                            g.drawImage(nutHoleImg, i * UNIT_SIZE, j * UNIT_SIZE, UNIT_SIZE, UNIT_SIZE, this);
                            break;
                        case 6:
                            g.drawImage(flowerImg, i * UNIT_SIZE, j * UNIT_SIZE, UNIT_SIZE, UNIT_SIZE, this);
                            break;
                        case 9:
                            g.drawImage(borderImg, i * UNIT_SIZE, j * UNIT_SIZE, UNIT_SIZE, UNIT_SIZE, this);
                            break;
                        default:
                            System.out.println("Unknown cell value at [" + i + "][" + j + "]: " + cell);
                    }
                }
            }

            if (totalNut ==0){

                int cell = levelCompleat;
                switch (cell) {
                    case 0:
                        levelCompleat  = levelCompleat +1;
                        setBoardArrayEmpty();
                        setBoardArrayBorders();
                        levelTwoSetup();
                        repaint();
                        break;
                    case 1:
                        levelCompleat  = levelCompleat +1;
                        setBoardArrayEmpty();
                        setBoardArrayBorders();
                        levelThreeSetup();
                        repaint();
                        break;
                    case 2:
                        levelCompleat  = levelCompleat +1;
                        setBoardArrayEmpty();
                        setBoardArrayBorders();
                        levelFourSetup();
                        repaint();
                        break;
                    case 3:
                        levelCompleat  = levelCompleat +1;
                        setBoardArrayEmpty();
                        setBoardArrayBorders();
                        levelFiveSetup();
                        repaint();
                        break;
                    case 4:
                        levelCompleat  = levelCompleat +1;
                        setBoardArrayEmpty();
                        setBoardArrayBorders();
                        levelSixSetup();
                        repaint();
                        break;
                    case 5:

                        levelCompleat  = levelCompleat +1;
                        setBoardArrayEmpty();
                        setBoardArrayBorders();
                        levelSevenSetup();
                        repaint();
                        break;
                    case 6:
                        levelCompleat  = levelCompleat +1;
                        setBoardArrayEmpty();
                        setBoardArrayBorders();
                        levelEightSetup();
                        repaint();
                        break;
                    case 7:
                        setBoardArrayEmpty();
                        g.setColor(Color.white);
                        g.setFont(new Font("Ink Free", Font.BOLD, 75));
                        FontMetrics metrics = getFontMetrics(g.getFont());
                        g.drawString("You Win!", (SCREEN_WIDTH - metrics.stringWidth("You Win!")) / 2, SCREEN_HEIGHT / 2);
                        break;
                    default:
                        System.out.println("error with levelcomleat");

                        //game is finished 
                        /*gameOver();
                        g.setColor(Color.white);
                        g.setFont(new Font("Ink Free", Font.BOLD, 75));
                        FontMetrics metrics = getFontMetrics(g.getFont());
                        g.drawString("You Win!", (SCREEN_WIDTH - metrics.stringWidth("You Win!")) / 2, SCREEN_HEIGHT / 2);
                        break;*/
                }
            }
        }
    }

    public void newNut() {
        nutX = random.nextInt(SCREEN_WIDTH / UNIT_SIZE) * UNIT_SIZE;
        nutY = random.nextInt(SCREEN_HEIGHT / UNIT_SIZE) * UNIT_SIZE;

        squirrelX = random.nextInt(SCREEN_WIDTH / UNIT_SIZE) * UNIT_SIZE;
        squirrelY = random.nextInt(SCREEN_HEIGHT / UNIT_SIZE) * UNIT_SIZE;
    }

    public void levelOneSetup() {
        /*
        nutX = (random.nextInt(SCREEN_WIDTH / UNIT_SIZE) + 1) * UNIT_SIZE;
        nutY = (random.nextInt(SCREEN_HEIGHT / UNIT_SIZE) + 1) * UNIT_SIZE;

        squirrelX = (random.nextInt(SCREEN_WIDTH / UNIT_SIZE) + 1) * UNIT_SIZE;
        squirrelY = (random.nextInt(SCREEN_HEIGHT / UNIT_SIZE) + 1) * UNIT_SIZE;
         */
        nutX = 2;
        nutY = 2;
        generateRandomNuts();

        squirrelX = 3;
        squirrelY = 3;
        populateBoardArrayWithRandomSquirrel();

        holeX = 4;
        holeY = 4;
        populateBoardArrayWithRandomHoles();

    }

    public void levelTwoSetup() {
        /*
        nutX = (random.nextInt(SCREEN_WIDTH / UNIT_SIZE) + 1) * UNIT_SIZE;
        nutY = (random.nextInt(SCREEN_HEIGHT / UNIT_SIZE) + 1) * UNIT_SIZE;

        squirrelX = (random.nextInt(SCREEN_WIDTH / UNIT_SIZE) + 1) * UNIT_SIZE;
        squirrelY = (random.nextInt(SCREEN_HEIGHT / UNIT_SIZE) + 1) * UNIT_SIZE;
         */
        nutX = 2;
        nutY = 2;
        generateRandomNuts();

        squirrelX = 4;
        squirrelY = 4;
        populateBoardArrayWithRandomSquirrel();

        holeX = 3;
        holeY = 3;
        populateBoardArrayWithRandomHoles();

    }

    public void levelThreeSetup() {
        /*
        nutX = (random.nextInt(SCREEN_WIDTH / UNIT_SIZE) + 1) * UNIT_SIZE;
        nutY = (random.nextInt(SCREEN_HEIGHT / UNIT_SIZE) + 1) * UNIT_SIZE;

        squirrelX = (random.nextInt(SCREEN_WIDTH / UNIT_SIZE) + 1) * UNIT_SIZE;
        squirrelY = (random.nextInt(SCREEN_HEIGHT / UNIT_SIZE) + 1) * UNIT_SIZE;
         */
        nutX = 2;
        nutY = 2;
        generateRandomNuts();

        squirrelX = 1;
        squirrelY = 1;
        populateBoardArrayWithRandomSquirrel();

        holeX = 1;
        holeY = 3;
        populateBoardArrayWithRandomHoles();

    }

    public void levelFourSetup() {
        /*
        nutX = (random.nextInt(SCREEN_WIDTH / UNIT_SIZE) + 1) * UNIT_SIZE;
        nutY = (random.nextInt(SCREEN_HEIGHT / UNIT_SIZE) + 1) * UNIT_SIZE;

        squirrelX = (random.nextInt(SCREEN_WIDTH / UNIT_SIZE) + 1) * UNIT_SIZE;
        squirrelY = (random.nextInt(SCREEN_HEIGHT / UNIT_SIZE) + 1) * UNIT_SIZE;
         */
        nutX = 2;
        nutY = 2;
        generateRandomNuts();

        squirrelX = 3;
        squirrelY = 1;
        populateBoardArrayWithRandomSquirrel();

        holeX = 4;
        holeY = 4;
        populateBoardArrayWithRandomHoles();

    }

    public void levelFiveSetup() {
        /*
        nutX = (random.nextInt(SCREEN_WIDTH / UNIT_SIZE) + 1) * UNIT_SIZE;
        nutY = (random.nextInt(SCREEN_HEIGHT / UNIT_SIZE) + 1) * UNIT_SIZE;

        squirrelX = (random.nextInt(SCREEN_WIDTH / UNIT_SIZE) + 1) * UNIT_SIZE;
        squirrelY = (random.nextInt(SCREEN_HEIGHT / UNIT_SIZE) + 1) * UNIT_SIZE;
         */
        nutX = 1;
        nutY = 2;
        generateRandomNuts();

        squirrelX = 4;
        squirrelY = 4;
        populateBoardArrayWithRandomSquirrel();

        holeX = 1;
        holeY = 1;
        populateBoardArrayWithRandomHoles();

    }

    public void levelSixSetup() {
        nutX = 3;
        nutY = 2;
        generateRandomNuts();

        squirrelX = 4;
        squirrelY = 4;
        populateBoardArrayWithRandomSquirrel();

        holeX = 1;
        holeY = 4;
        populateBoardArrayWithRandomHoles();

        flowerX =3 ;
        flowerY =1 ;
        populateBoardArrayWithRandomFlower();

        flowerX =3 ;
        flowerY =3 ;
        populateBoardArrayWithRandomFlower();

    }

    public void levelSevenSetup() {
        nutX = 2;
        nutY = 2;
        generateRandomNuts();

        squirrelX = 4;
        squirrelY = 4;
        populateBoardArrayWithRandomSquirrel();

        holeX = 3;
        holeY = 3;
        populateBoardArrayWithRandomHoles();

        flowerX =1 ;
        flowerY = 1;
        populateBoardArrayWithRandomFlower();

        flowerX = 1;
        flowerY = 1;
        populateBoardArrayWithRandomFlower();
    }

    public void levelEightSetup() {
        nutX = 2;
        nutY = 2;
        generateRandomNuts();

        squirrelX = 4;
        squirrelY = 4;
        populateBoardArrayWithRandomSquirrel();

        holeX = 3;
        holeY = 3;
        populateBoardArrayWithRandomHoles();

        flowerX =1 ;
        flowerY = 1;
        populateBoardArrayWithRandomFlower();

        flowerX = 1;
        flowerY = 2;
        populateBoardArrayWithRandomFlower();
    }

    

    public void generateRandomNuts() {
        boardArray[nutX][nutY] = nut_Code;//nut = 1 in array
    }

    public void populateBoardArrayWithRandomSquirrel() {
        boardArray[squirrelX][squirrelY] = squirrel_Code;//squirrel = 2 in array
    }

    public void populateBoardArrayWithRandomHoles() {
        boardArray[holeX][holeY] = hole_Code;//hole = 3 in array
    }

    public void populateBoardArrayWithRandomFlower() {
        boardArray[flowerX][flowerY] = flower_code;//flower = 6 in array
    }

    public void checkCollisions(int direction) {
        legal_Move = false;
        chain_length = 0;

        int dx = 0;
        int dy = 0;

        // Set the direction of movement
        switch (direction) {
            case 1: dy = -1; break; // up
            case 2: dx = -1; break; // left
            case 3: dy = 1; break;  // down
            case 4: dx = 1; break;  // right
        }

        int currentX = squirrelX + dx;
        int currentY = squirrelY + dy;

        // Check for collisions or valid move
        while (isWithinBounds(currentX, currentY)) {
            int val = boardArray[currentX][currentY];

            if (val == 0) {
                // If it's an empty space or a hole, the move is legal
                legal_Move = true;
                return;
            } else if (val == hole_Code) {
                // If it's a nut or the squirrel going into the hole
                legal_Move = true;
                return;
            } else if (val == nut_Code || val == squirrel_Code) {
                // If it's a nut or the squirrel, add to the chain length
                chain_length++;
            } else if (val == border_Code||val == flower_code) {
                // If it's a border, stop the move and reset the chain length
                chain_length = 0;
                return;
            }

            currentX += dx;
            currentY += dy;
        }
    }

    public void move(int direction) {
        if (!legal_Move) return;

        int dx = 0, dy = 0;
        switch (direction) {
            case 1: dy = -1; break; // up
            case 2: dx = -1; break; // left
            case 3: dy = 1; break;  // down
            case 4: dx = 1; break;  // right
        }

        for (int i = chain_length; i >= 0; i--) {
            int fromX = squirrelX + dx * i;
            int fromY = squirrelY + dy * i;
            int toX = squirrelX + dx * (i + 1);
            int toY = squirrelY + dy * (i + 1);

            int movingVal = boardArray[fromX][fromY];
            int destinationVal = boardArray[toX][toY];

            // Reverse logic: convert from hole-forms to normal
            if (movingVal == 4) movingVal = 2; // squirrel in hole → squirrel
            else if (movingVal == 5) movingVal = 1; // nut in hole → nut

            // If moving into hole
            if (destinationVal == hole_Code) {
                if (movingVal == nut_Code) {
                    boardArray[toX][toY] = 5; // nut in hole
                } else if (movingVal == squirrel_Code) {
                    boardArray[toX][toY] = 4; // squirrel in hole
                }
            } else {
                boardArray[toX][toY] = movingVal; // regular move
            }

            // Leave behind a hole if moved out of 4 or 5, else empty
            int previousVal = boardArray[fromX][fromY];
            if (previousVal == 4 || previousVal == 5) {
                boardArray[fromX][fromY] = 3; // leave a hole
            } else {
                boardArray[fromX][fromY] = 0; // leave empty
            }
        }

        squirrelX += dx;
        squirrelY += dy;

        repaint();
    }

    private boolean isWithinBounds(int x, int y) {
        return x >= 0 && y >= 0 && x < boardArray.length && y < boardArray[0].length;
    }

    public void gameOver() {
        //running = false;
        setBoardArrayEmpty();
        repaint();
    }

    public void print3dArray() {
        for (int i = 0; i < boardArray.length; i++) {
            for (int j = 0; j < boardArray[i].length; j++) {
                System.out.print(boardArray[j][i] + " ");
            }
            System.out.println(); 
        }

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
                if (i == 0||i == 5||j == 0||j == 5)
                {
                    boardArray[i][j] = border_Code;    
                }
            }
        }
    }

    public void resetBoard() {
        setBoardArrayEmpty();
        setBoardArrayBorders();
        generateRandomNuts();
        populateBoardArrayWithRandomHoles();
        populateBoardArrayWithRandomSquirrel();
        print3dArray();
    }

    // KeyListener methods
    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("Key pressed: " + e.getKeyCode());

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_W || key == KeyEvent.VK_UP) {
            System.out.println("Move Up");
            direction = 1;
            checkCollisions(1);

            move(1);
            print3dArray();
            //repaint();
        } 
        else if (key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) {
            System.out.println("Move Down");
            direction = 3;
            checkCollisions(3);
            move(3);
            print3dArray();
            //repaint();
        } 
        else if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) {
            System.out.println("Move Left");
            direction = 2;
            checkCollisions(2);
            move(2);
            print3dArray();
            //repaint();
        } 
        else if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) {
            System.out.println("Move Right");
            direction = 4;
            checkCollisions(4);
            move(4);
            print3dArray();
            //repaint();

        } 
        else if (key == KeyEvent.VK_D || key == KeyEvent.VK_R) {
            System.out.println("reset");
            int cell = levelCompleat;
            switch (cell) {
                case 0:
                    setBoardArrayEmpty();
                    setBoardArrayBorders();
                    levelOneSetup();
                    repaint();
                    break;
                case 1:
                    //levelCompleat  = levelCompleat +1;
                    setBoardArrayEmpty();
                    setBoardArrayBorders();
                    levelTwoSetup();
                    repaint();
                    break;
                case 2:
                    //levelCompleat  = levelCompleat +1;
                    setBoardArrayEmpty();
                    setBoardArrayBorders();
                    levelThreeSetup();
                    repaint();
                    break;
                case 3:
                    //levelCompleat  = levelCompleat +1;
                    setBoardArrayEmpty();
                    setBoardArrayBorders();
                    levelFourSetup();
                    repaint();
                    break;
                case 4:
                    //levelCompleat  = levelCompleat +1;
                    setBoardArrayEmpty();
                    setBoardArrayBorders();
                    levelFiveSetup();
                    repaint();
                    break;
                case 5:
                    //levelCompleat  = levelCompleat +1;
                    setBoardArrayEmpty();
                    setBoardArrayBorders();
                    levelSixSetup();
                    repaint();
                    break;
                case 6:

                    //levelCompleat  = levelCompleat +1;
                    setBoardArrayEmpty();
                    setBoardArrayBorders();
                    levelSevenSetup();
                    repaint();
                    break;
                case 7:
                    //levelCompleat  = levelCompleat +1;
                    setBoardArrayEmpty();
                    setBoardArrayBorders();
                    levelEightSetup();
                    repaint();
                    break;
                default:
                    System.out.println("error with levelcomleat");
            }

        }

        /*wsad up down left right
        87
        83
        65
        68
        38
        40
        37
        39
         */
    }

    @Override
    public void actionPerformed(ActionEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) { }

    @Override
    public void keyTyped(KeyEvent e) { }

    @Override
    public void mouseClicked(MouseEvent e) { }

    @Override
    public void mousePressed(MouseEvent e) { }

    @Override
    public void mouseReleased(MouseEvent e) { }

    @Override
    public void mouseEntered(MouseEvent e) { }

    @Override
    public void mouseExited(MouseEvent e) { }
}

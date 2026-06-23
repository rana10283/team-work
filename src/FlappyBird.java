import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class FlappyBird extends JPanel
        implements ActionListener, KeyListener {

    private Image backgroundImg;
    private Image birdImg;
    private Image topPipeImg;
    private Image bottomPipeImg;

    private Bird bird;
    private PipeManager pipeManager;
    private ScoreManager scoreManager;

    private Timer gameLoop;
    private Timer pipeTimer;

    private int velocityX = -6;
    private int velocityY = 0;
    private int gravity = 1;

    private GameState gameState;

    public FlappyBird() {

        setPreferredSize(
                new Dimension(
                        GameSettings.BOARD_WIDTH,
                        GameSettings.BOARD_HEIGHT));

        setFocusable(true);
        addKeyListener(this);

        loadImages();

        bird = new Bird(
                GameSettings.BOARD_WIDTH / 8,
                GameSettings.BOARD_HEIGHT / 2,
                GameSettings.BIRD_WIDTH,
                GameSettings.BIRD_HEIGHT,
                birdImg);

        pipeManager = new PipeManager();
        scoreManager = new ScoreManager();

        gameState = GameState.PLAYING;

        pipeTimer = new Timer(1500, e -> placePipes());
        pipeTimer.start();

        gameLoop = new Timer(1000 / 60, this);
        gameLoop.start();
    }

    private void loadImages() {
        backgroundImg = new ImageIcon(getClass().getResource("fields.png")).getImage();
        birdImg = new ImageIcon(getClass().getResource("flappyBird.png")).getImage();
        topPipeImg = new ImageIcon(getClass().getResource("toppipe.png")).getImage();
        bottomPipeImg = new ImageIcon(getClass().getResource("bottompipe.png")).getImage();
    }

    private void placePipes() {

        int randomPipeY =
                (int) (0 - GameSettings.PIPE_HEIGHT / 4
                        - Math.random() * (GameSettings.PIPE_HEIGHT / 2));

        int openingSpace = GameSettings.BOARD_HEIGHT / 4;

        Pipe topPipe = new Pipe(
                GameSettings.BOARD_WIDTH,
                randomPipeY,
                GameSettings.PIPE_WIDTH,
                GameSettings.PIPE_HEIGHT,
                topPipeImg);

        Pipe bottomPipe = new Pipe(
                GameSettings.BOARD_WIDTH,
                randomPipeY + GameSettings.PIPE_HEIGHT + openingSpace,
                GameSettings.PIPE_WIDTH,
                GameSettings.PIPE_HEIGHT,
                bottomPipeImg);

        pipeManager.addPipe(topPipe);
        pipeManager.addPipe(bottomPipe);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    private void draw(Graphics g) {

        g.drawImage(backgroundImg, 0, 0,
                GameSettings.BOARD_WIDTH,
                GameSettings.BOARD_HEIGHT, null);

        bird.draw(g);

        for (Pipe pipe : pipeManager.getPipes()) {
            pipe.draw(g);
        }

        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 30));

        g.drawString("Score: " + scoreManager.getScore(), 20, 40);

        if (gameState == GameState.GAME_OVER) {

            String text = "Game Over: " + scoreManager.getScore();

            FontMetrics fm = g.getFontMetrics();
            int x = (GameSettings.BOARD_WIDTH - fm.stringWidth(text)) / 2;
            int y = GameSettings.BOARD_HEIGHT / 2;

            g.drawString(text, x, y);
        }
    }

    private void move() {

        velocityY += gravity;
        bird.setY(bird.getY() + velocityY);

        if (bird.getY() < 0) {
            bird.setY(0);
        }

        for (Pipe pipe : pipeManager.getPipes()) {

            pipe.setX(pipe.getX() + velocityX);

            if (!pipe.isPassed()
                    && bird.getX() > pipe.getX() + pipe.getWidth()) {

                scoreManager.increaseScore();
                pipe.setPassed(true);
            }

            if (CollisionDetector.isColliding(bird, pipe)) {
                gameState = GameState.GAME_OVER;
            }
        }

        if (bird.getY() > GameSettings.BOARD_HEIGHT) {
            gameState = GameState.GAME_OVER;
        }
    }

    private void restartGame() {

        bird.setY(GameSettings.BOARD_HEIGHT / 2);
        velocityY = 0;

        pipeManager.clearPipes();
        scoreManager.reset();

        velocityX = -6;

        gameState = GameState.PLAYING;

        gameLoop.start();
        pipeTimer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (gameState == GameState.PLAYING) {
            move();
            repaint();
        }

        if (gameState == GameState.GAME_OVER) {
            gameLoop.stop();
            pipeTimer.stop();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_SPACE) {

            velocityY = -9;

            if (gameState == GameState.GAME_OVER) {
                restartGame();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}
}
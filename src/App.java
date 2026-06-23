import javax.swing.JFrame;

public class App {

    public static void main(String[] args) {

        JFrame frame = new JFrame("Flappy Bird");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
 
        FlappyBird game = new FlappyBird();

        frame.add(game);
        frame.pack();

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        game.requestFocus();
    }
}
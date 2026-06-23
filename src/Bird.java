import java.awt.Graphics;
import java.awt.Image;

public class Bird extends GameObject {

    public Bird(int x, int y, int width, int height, Image img) {

        super(x, y, width, height, img);
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public void draw(Graphics g) {

        g.drawImage(img, x, y, width, height, null);
    }
}
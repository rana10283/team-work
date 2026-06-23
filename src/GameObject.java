import java.awt.Image;

public abstract class GameObject implements Drawable {

    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected Image img;

    public GameObject(int x, int y, int width, int height, Image img) {

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.img = img;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Image getImg() {
        return img;
    }
}
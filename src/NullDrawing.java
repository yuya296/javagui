import java.awt.*;
import java.awt.geom.Area;

public class NullDrawing extends MyDrawing {
    public NullDrawing() {
        this.region = new Area();
    }

    @Override
    public MyDrawing clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    @Override
    public void draw(Graphics g) {}

    @Override
    protected Shape getShape() {
        return new Area();
    }

    @Override
    public boolean contains(int x, int y) {
        return false;
    }

}

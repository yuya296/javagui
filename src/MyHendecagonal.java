import java.awt.*;

public class MyHendecagonal extends MyPolygonal {
    public MyHendecagonal(int xpt, int ypt, int size) {
        super(xpt, ypt, size, 11);
    }
    public MyHendecagonal(int xpt, int ypt, int size, int n, Color lineColor, Color fillColor, int lineWidth) {
        super(xpt, ypt, size, n, lineColor, fillColor, lineWidth);
    }
}
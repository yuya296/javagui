import java.awt.*;

public class MyRectangle extends MyDrawing {

    public MyRectangle(int xpt, int ypt) {
        super(xpt, ypt);
    }
    public MyRectangle(int xpt, int ypt, int wpt, int hpt, Color lineColor, Color fillColor, int lineWidth) {
        super(xpt, ypt, wpt, hpt, lineColor, fillColor, lineWidth);
    }
    public MyRectangle(int xpt, int ypt, Color lineColor, Color fillColor, int lineWidth) {
        super(xpt, ypt, lineColor, fillColor, lineWidth);
    }

    public void draw(Graphics g) {
        int x = getX();
        int y = getY();
        int w = getW();
        int h = getH();

        // 高さや横幅が負のときのための処理
        if (w < 0) {
            x += w;
            w *= -1;
        }
        if (h < 0) {
            y += h;
            h *= -1;
        }

        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(getStroke());
        g2.setColor(getFillColor());
        g2.fillRect(x,y,w,h);
        g2.setColor(getLineColor());
        g2.drawRect(x,y,w,h);
    }
}

import java.awt.*;

public class MyOval extends MyDrawing {

    // 長方形とは違い、中心の座標を引数に取ることにする。
    public MyOval(int xpt, int ypt, int wpt, int hpt) {
        super(xpt - wpt/2, ypt - hpt/2);
        setSize(wpt, hpt);
    }
    public MyOval(int xpt, int ypt, int diam) {
        this(xpt, ypt, diam, diam);
    }
    public MyOval(int xpt, int ypt, int wpt, int hpt, Color lineColor, Color fillColor, int lineWidth) {
        this(xpt, ypt, wpt, hpt);
        setLineColor(lineColor);
        setFillColor(fillColor);
        setLineWidth(lineWidth);
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
        g2.setStroke(new BasicStroke(getLineWidth()));
        g2.setColor(getFillColor());
        g2.fillOval(x, y, w, h);
        g2.setColor(getLineColor());
        g2.drawOval(x, y, w, h);
    }
}

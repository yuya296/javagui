// Task1 課題1

import java.awt.*;

public class MyStar2 extends MyDrawing {
    private MyPolygonal inner, outer;
    private double scale;
    private int n;

    public MyStar2(int xpt, int ypt, int wpt, int hpt, int n, double scale) {
        super(xpt, ypt);
        setSize(wpt, hpt);
        setN(n);
        setScale(scale);

        outer = new MyPolygonal(xpt, ypt, wpt, hpt, n);
        inner = new MyPolygonal(xpt, ypt, (int)(this.scale * wpt), (int)(this.scale * hpt), n);

        inner.rotate(-Math.PI / n);
    }

    public MyStar2(int xpt, int ypt, int wpt, int hpt) {
        this(xpt, ypt, wpt, hpt, 5, 0.5);
    }

    public MyStar2(int xpt, int ypt, int wpt, int hpt, int n, double scale, Color lineColor, Color fillColor) {
        this(xpt, ypt, wpt, hpt, n, scale);
        this.setLineColor(lineColor);
        this.setFillColor(fillColor);
    }

    public void setN(int n) {
        this.n = n;
    }
    public int getN() {
        return n;
    }

    public void setScale(double scale) {
        if (scale > 1) {
            scale = 1 / scale;
        }
        this.scale = scale;
    }

    public double getScale() {
        return scale;
    }

    public static int[] castedUnion(double[] a, double[] b, int n) {
        int[] res = new int[2 * n];
        for (int i = 0; i < n; i++) {
            res[2 * i] = (int)a[i];
            res[2 * i + 1] = (int)b[i];
        }
        return res;
    }

    public void draw(Graphics g) {
        inner.calc();
        outer.calc();
        int[] xPointsInt = castedUnion(inner.getXPoints(), outer.getXPoints(), n);
        int[] yPointsInt = castedUnion(inner.getYPoints(), outer.getYPoints(), n);

        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(getLineWidth()));
        g2.setColor(getFillColor());
        g2.fillPolygon(xPointsInt, yPointsInt, 2 * n);
        g2.setColor(getLineColor());
        g2.drawPolygon(xPointsInt, yPointsInt, 2 * n);
    }
}

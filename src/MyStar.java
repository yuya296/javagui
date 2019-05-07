// Task1 課題1

import java.awt.*;

public class MyStar extends MyDrawing {
    private MyPolygonal inner, outer;
    private int n;

    public MyStar(int xpt, int ypt, int n, int rad1, int rad2) {
        super(xpt, ypt);
        this.n = n;

        outer = new MyPolygonal(xpt, ypt, Math.max(rad1, rad2), n);
        inner = new MyPolygonal(xpt, ypt, Math.min(rad1, rad2), n);

        inner.rotate(-Math.PI / n);
    }

    public MyStar(int xpt, int ypt, int n, int rad1, int rad2, Color lineColor, Color fillColor) {
        this(xpt, ypt, n, rad1, rad2);
        this.setLineColor(lineColor);
        this.setFillColor(fillColor);
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

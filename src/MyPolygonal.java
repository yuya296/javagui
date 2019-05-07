// Task1 課題1

import java.awt.*;

public class MyPolygonal extends MyDrawing {
    private int size;       // 中心から各頂点までの長さ
    private int n;          // n角形 であることを表す
    private double[] xPoints, yPoints;  // 点を格納する配列, double型なのでint型にキャストして使うこと.

    public MyPolygonal(int xpt, int ypt, int size, int n, Color lineColor, Color fillColor, int lineWidth) {
        this(xpt, ypt, size, n);
        setLineColor(lineColor);
        setFillColor(fillColor);
        setLineWidth(lineWidth);
    }
    public MyPolygonal(int xpt, int ypt, int size, int n) {
        super(xpt, ypt);
        setSize(size);
        setN(n);
        setPoints(new double[n], new double[n]);
        calc();
    }

    // この多角形の座標を計算する
    private void calc() {
        double x = (double)getX();
        double y = (double)getY();
        double size = (double)getSize();

        double theta = 2 * Math.PI / (double)n;
        double sin = Math.sin(theta);
        double cos = Math.cos(theta);

        xPoints[0] = 0.0;
        yPoints[0] = -size;

        try {
            for (int i = 1; i < n; i++) {
                xPoints[i] = cos * xPoints[i-1] - sin * yPoints[i-1];
                yPoints[i] = sin * xPoints[i-1] + cos * yPoints[i-1];
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.print("IndexOutOfBoundsException at calc");
            System.exit(-1);
        }

        for (int i = 0; i < n; i++) {
            xPoints[i] += x;
            yPoints[i] += y;
        }
    }

    public void rotate(double rad) {
        double theta = rad;
        double sin = Math.sin(theta);
        double cos = Math.cos(theta);
        int x0 = getX();
        int y0 = getY();

        double tempX, tempY;

        for (int i = 0; i < this.n; i++) {
            tempX = xPoints[i] - x0;
            tempY = yPoints[i] - y0;
            xPoints[i] = cos * tempX - sin * tempY + x0;
            yPoints[i] = sin * tempX + cos * tempY + y0;

        }
    }

    private static int[] castedCopy(double[] arr) {
        int[] newArr = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            newArr[i] = (int)arr[i];
        }
        return newArr;
    }

    public void setPoints(double[] xPoints, double[] yPoints) {
        this.xPoints = xPoints;
        this.yPoints = yPoints;
    }
    public double[] getXPoints() {
        return xPoints;
    }
    public double[] getYPoints() {
        return yPoints;
    }

    public void setSize(int size) {
        this.size = size;
    }
    public int getSize() {
        return this.size;
    }

    public void setN(int n) {
        this.n = n;
    }
    public int getN() {
        return this.n;
    }



    public void draw(Graphics g) {

        int[] xPointsInt = castedCopy(xPoints);
        int[] yPointsInt = castedCopy(yPoints);

        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(getLineWidth()));
        g2.setColor(getFillColor());
        g2.fillPolygon(xPointsInt, yPointsInt, n);
        g2.setColor(getLineColor());
        g2.drawPolygon(xPointsInt, yPointsInt, n);
    }
}

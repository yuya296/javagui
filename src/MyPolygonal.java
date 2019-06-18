import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Area;


/* MyPolygonal */
public class MyPolygonal extends MyDrawing {
    private int n;  // n角形であることを表す
    private int r;  // 中心から各頂点までの長さ

    protected double[] xPoints, yPoints;  // 座標を格納する配列

    public MyPolygonal(int x, int y, int w, int h, Color lineColor, Color fillColor, int lineWidth, int lineNumber, float[] dashArray, boolean hasShade, int n) {
        super(x, y, w, h, lineColor, fillColor, lineWidth, lineNumber, dashArray, hasShade);
        setN(n);
        setPoints(new double[n], new double[n]);
//        calc();
        setSize(w, h);
    }

    public MyPolygonal(int xpt, int ypt) {
        this(xpt, ypt, 40, 40, 5);
    }
    public MyPolygonal(int xpt, int ypt, int wpt, int hpt) {
        this(xpt, ypt, wpt, hpt, 5);
    }
    public MyPolygonal(int xpt, int ypt, int wpt, int hpt, int n) {
        super(xpt, ypt);
        setN(n);
        setPoints(new double[n], new double[n]);
    }
    public MyPolygonal(MyPolygonal d) {
        super(d);
        setN(d.n);
        setR(d.r);
        setPoints(d.getXPoints(), d.getYPoints());
    }

    protected MyPolygonal copy() {
        System.out.println("copy polygon");
        return new MyPolygonal(this);
    }

    @Override
    public MyPolygonal clone() throws CloneNotSupportedException {
        return new MyPolygonal(this);
    }

    public void setN(int n) {
        this.n = n;
    }
    public void setR(int r) {
        this.r = r;
    }
    public int getN() {
        return n;
    }
    public int getR() {
        return r;
    }
    public void setPoints(double x[], double y[]) {
        this.xPoints = x;
        this.yPoints = y;
    }

    @Override
    public void setSize(int w, int h) {
        super.setSize(w, h);

    }

    // 大きさ1の単位多角形を作る
    public MyPolygonal createShape(int n) {
        double theta = 2 * Math.PI / (double)n;
        double sin = Math.sin(theta);
        double cos = Math.cos(theta);

        xPoints[0] = 0;
        yPoints[0] = -1;

        for (int i = 1; i < n; i++) {
            xPoints[i] = cos * xPoints[i-1] - sin * yPoints[i-1];
            yPoints[i] = sin * xPoints[i-1] + cos * yPoints[i-1];
        }
        return this;
    }

    // x, yを線の1つとしてもつ多角形をつくる
    public MyPolygonal createShape(int n, int x, int y) {
        double theta = 2 * Math.PI / (double)n;
        double sin = Math.sin(theta);
        double cos = Math.cos(theta);

        xPoints[0] = x - getX();
        yPoints[0] = y - getY();

        for (int i = 1; i < n; i++) {
            xPoints[i] = cos * xPoints[i-1] - sin * yPoints[i-1];
            yPoints[i] = sin * xPoints[i-1] + cos * yPoints[i-1];
        }

        return this.trans(getX(), getY());
    }

    public MyPolygonal scale(int w, int h) {
        // 中心が原点のときのみ
//        for (int i = 0; i < n; i++) {
//            xPoints[i] *= w;
//            yPoints[i] *= h;
//        }
//        return this;
        return scale((double)w, (double)h);
    }

    public MyPolygonal scale(double w, double h) {
        // 中心が原点のときのみ
        for (int i = 0; i < n; i++) {
            xPoints[i] *= w;
            yPoints[i] *= h;
        }
        return this;
    }

//    public MyPolygonal rotate(double theta) {
//        double sin = Math.sin(theta);
//        double cos = Math.cos(theta);
//
//        for (int i = 0; i < n; i++) {
//            xPoints[i] = cos * xPoints[i-1] - sin * yPoints[i-1];
//        }
//        return this;
//    }

    public MyPolygonal trans(int dx, int dy) {
        for (int i = 0; i < xPoints.length; i++) {
            xPoints[i] += dx;
            yPoints[i] += dy;
        }
        return this;
    }

    public static double[] trans(int d, double[] points) {
        double[] newarr = new double[points.length];
        for (int i = 0; i < points.length; i++) {
            newarr[i] = points[i] + d;
        }
        return points;
    }
    public static int[] trans(int d, int[] points) {
        int[] newarr = new int[points.length];
        for (int i = 0; i < points.length; i++) {
            newarr[i] = points[i] + d;
        }
        return newarr;
    }

    protected void calc(int x, int y) {
        createShape(n, x, y);
    }

    protected MyPolygonal calc(int n, int w, int h, int dx, int dy) {
        return createShape(n).scale(w, h).trans(dx, dy);
    }

    @Override
    public void move(int dx, int dy) {
        super.move(dx, dy);
        double[] xpts = getXPoints();
        double[] ypts = getYPoints();
        for (int i = 0; i < getN(); i++) {
            xpts[i] += (double)dx;
            ypts[i] += (double)dy;
        }
        setPoints(xpts, ypts);
    }

//    public void rotate(double rad) {
//        double sin = Math.sin(rad);
//        double cos = Math.cos(rad);
//        int x0 = getCenterX();
//        int y0 = getCenterY();
//
//        double[] xpts = getXPoints();
//        double[] ypts = getYPoints();
//
//        double tempX, tempY;
//
//        for (int i = 0; i < getN(); i++) {
//            // 図形の中心を原点に平行移動したものを仮の変数に保存
//            tempX = xpts[i] - x0;
//            tempY = ypts[i] - y0;
//
//            // 原点中心に回転、もとの位置に平行移動させたものを新たな値として保存
//            xpts[i] = cos * tempX - sin * tempY + x0;
//            ypts[i] = sin * tempX + cos * tempY + y0;
//        }
//
//        setPoints(xpts, ypts);
//    }

    // 位置配列をdouble型で返す deep copy
    public double[] getXPoints() {
        double[] rtn = new double[getN()];
        for (int i = 0; i < getN(); i++) {
            rtn[i] = xPoints[i];
        }
        return rtn;
    }
    public double[] getYPoints() {
        double[] rtn = new double[getN()];
        for (int i = 0; i < getN(); i++) {
            rtn[i] = yPoints[i];
        }
        return rtn;
    }

    // 位置配列をint型で返す deep copy
    public int[] getXPointsInt() {
        int[] rtn = new int[getN()];
        for (int i = 0; i < getN(); i++) {
            rtn[i] = (int)xPoints[i];
        }
        return rtn;
    }
    public int[] getYPointsInt() {
        int[] rtn = new int[getN()];
        for (int i = 0; i < getN(); i++) {
            rtn[i] = (int)yPoints[i];
        }
        return rtn;
    }

    public static int[] addArray(int[] arr, double s) {
        int[] newarr = new int[arr.length];
        for (int i = 0; i < arr.length; i++)
            newarr[i] = (int)(arr[i] + s);
        return newarr;
    }

    public static int[] scale(double[] arr, int center, double e) {
        int[] res = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            res[i] = (int)((arr[i] - center) * e + center);
        }
        return res;
    }

    public int[] toIntegerList(double[] arr) {
        int[] newarr = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            newarr[i] = (int)arr[i];
        }
        return newarr;
    }

    public Polygon scalePoints(int n, double[] x, double[] y, int x0, int y0, int gap) {
        double theta;
        int[] newx = new int[n];
        int[] newy = new int[n];
        for (int i = 0; i < n; i++) {
            theta = Math.atan2((y[i] - y0) , (x[i] - x0));
            newx[i] = (int)(x[i] + gap * Math.cos(theta));
            newy[i] = (int)(y[i] + gap * Math.sin(theta));
        }
        return new Polygon(newx, newy, n);
    }

    @Override
    public void setRegion() {
        this.region = new Polygon(getXPointsInt(), getYPointsInt(), getN());
    }



    @Override
    protected Shape getShape() {
        return new Polygon(getXPointsInt(), getYPointsInt(), getN());
    }

    public void draw(Graphics g) {
        int x = getX();
        int y = getY();
        int w = getW();
        int h = getH();

        int lineNumber, lineWidth;
        Color fillColor, lineColor;

        int[] xPointsInt = getXPointsInt();
        int[] yPointsInt = getYPointsInt();

        // Graphic2D
        Graphics2D g2 = (Graphics2D) g;

        // 線をセット
        g2.setStroke(new BasicStroke(
                getLineWidth(), BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER,
                1.f, getDashArray(), 0.0f
        ));

        // 影の描画
        if (hasShade()) {
            g2.setColor(Color.black);
            g2.fillPolygon(trans(10, xPointsInt), trans(10, yPointsInt), n);
        }

        // 塗りの描画 (FillColor == null なら透過)
        if ((fillColor = getFillColor()) != null) {
            g2.setColor(fillColor);
            g2.fillPolygon(xPointsInt, yPointsInt, n);
        }

        // 線の描画 (lineColor == null || lineWidth == 0 なら透過)
        if ((lineColor = getLineColor()) != null && (lineWidth = getLineWidth()) != 0) {

            g2.setColor(lineColor);
            lineNumber = getLineNumber();

            if (lineNumber %2 == 1)
                g2.drawPolygon(getXPointsInt(), getYPointsInt(), n);

            for (int i = 0; i < lineNumber / 2; i++) {
                int gap = lineNumber % 2 == 0 ? (2 * i + 1) * lineWidth : (2 * (i + 1)) * lineWidth;

                g2.drawPolygon(scalePoints(getN(), getXPoints(), getYPoints(), getX(), getY(),  gap));
                g2.drawPolygon(scalePoints(getN(), getXPoints(), getYPoints(), getX(), getY(), -gap));
            }
        }

        super.draw(g);
    }
}


/* PolygonalButton */
class PolygonalButton extends JButton {
    StateManager stateManager;

    public PolygonalButton(StateManager stateManager) {
        super("Polygonal");
        addActionListener(new PolygonalListener());
        this.stateManager = stateManager;
    }

    class PolygonalListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            stateManager.setState(new PolygonalState(stateManager));
        }
    }
}


/* PolygonalState */
class PolygonalState implements State {
    StateManager stateManager;
    MyPolygonal drawing;

    public PolygonalState(StateManager stateManager) {
        this.stateManager = stateManager;
    }

    @Override
    public void mouseDown(int x, int y) {
        stateManager.addDrawing(
                drawing = new MyPolygonal(x, y, 0, 0, stateManager.getLineColor(),
                        stateManager.getFillColor(), stateManager.getLineWidth(), stateManager.getLineNumber(),
                        stateManager.getDashArray(), stateManager.hasShade(), 7)
        );
    }

    @Override
    public void mouseUp(int x, int y) {
        drawing = null;
    }

    @Override
    public void mouseDrag(int x, int y) {
        drawing.calc(x, y);
    }
}

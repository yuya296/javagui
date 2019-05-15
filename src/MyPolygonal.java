import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/* MyPolygonal */
public class MyPolygonal extends MyDrawing {
    private int n;  // n角形であることを表す
    private int r;  // 中心から各頂点までの長さ

    private double[] xPoints, yPoints;  // 座標を格納する配列

    public MyPolygonal(int xpt, int ypt) {
        this(xpt, ypt, 40, 40, 5);
    }

    public MyPolygonal(int xpt, int ypt, int wpt, int hpt, int n) {
        super(xpt, ypt);
        setSize(wpt, hpt);
        setN(n);
        setPoints(new double[n], new double[n]);
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

    public void calc() {
        double theta = 2 * Math.PI / (double)n;
        double sin = Math.sin(theta);
        double cos = Math.cos(theta);

        double[] xpts = new double[getN()];
        double[] ypts = new double[getN()];

        xpts[0] = 0;
        ypts[0] = -1;

        try {
            for (int i = 1; i < n; i++) {
                xpts[i] = cos * xpts[i-1] - sin * ypts[i-1];
                ypts[i] = sin * xpts[i-1] + cos * ypts[i-1];
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.print("IndexOutOfBoundsException at calc");
            System.exit(-1);
        }

        for (int i = 0; i < n; i++) {
            xpts[i] = xpts[i] * getW() / 2 + getX() + getW() / 2;
            ypts[i] = ypts[i] * getH() / 2 + getY() + getH() / 2;
        }

        setPoints(xpts, ypts);
    }

    public void rotate(double rad) {
        double sin = Math.sin(rad);
        double cos = Math.cos(rad);
        int x0 = getCenterX();
        int y0 = getCenterY();

        double[] xpts = getXPoints();
        double[] ypts = getYPoints();

        double tempX, tempY;

        for (int i = 0; i < getN(); i++) {
            // 図形の中心を原点に平行移動したものを仮の変数に保存
            tempX = xpts[i] - x0;
            tempY = ypts[i] - y0;

            // 原点中心に回転、もとの位置に平行移動させたものを新たな値として保存
            xpts[i] = cos * tempX - sin * tempY + x0;
            ypts[i] = sin * tempX + cos * tempY + y0;
        }

        setPoints(xpts, ypts);
    }

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

    public void draw(Graphics g) {
        calc();

        int[] xPointsInt = getXPointsInt();
        int[] yPointsInt = getYPointsInt();

        Graphics2D g2 = (Graphics2D) g;
        if (isDashed())
            g2.setStroke(new MyDashStroke(getLineWidth()));
        else
            g2.setStroke(new BasicStroke(getLineWidth()));
        g2.setColor(getFillColor());
        g2.fillPolygon(xPointsInt, yPointsInt, n);
        g2.setColor(getLineColor());
        g2.drawPolygon(xPointsInt, yPointsInt, n);
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
    MyDrawing drawing;

    public PolygonalState(StateManager stateManager) {
        this.stateManager = stateManager;
    }

    @Override
    public void mouseDown(int x, int y) {
        stateManager.addDrawing(drawing = new MyPolygonal(x, y, 0, 0, 5));
    }

    @Override
    public void mouseUp(int x, int y) {
        stateManager.setState(null);
    }

    @Override
    public void mouseDrag(int x, int y) {
        MyDrawing d = stateManager.getDrawing();
        d.setSize(x - d.getX(), y - d.getY());
    }
}



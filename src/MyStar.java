import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/* MyStar */
public class MyStar extends MyPolygonal {
    private double scale;

    public MyStar(int x, int y, int w, int h, Color lineColor, Color fillColor, int lineWidth, int lineNumber, float[] dashArray, boolean hasShade, int n, double scale) {
        super(x, y, w, h, lineColor, fillColor, lineWidth, lineNumber, dashArray, hasShade, n*2);
        this.scale = scale;
//        calc(n, scale, w, h, x, y);
    }

    public MyStar(int xpt, int ypt) {
        this(xpt, ypt, 40, 40, 5*2, 0.5);
    }
    public MyStar(int xpt, int ypt, int wpt, int hpt) {
        this(xpt, ypt, wpt, hpt, 5*2, 0.5);
    }
    public MyStar(int xpt, int ypt, int wpt, int hpt, int n, double scale) {
        super(xpt, ypt, -wpt, -hpt, 2*n);
        setScale(scale);
//        calc(n, scale, wpt, hpt, xpt, ypt);
    }
    public MyStar(MyStar s) {
        super(s);
//        calc(s.getN(), s.getScale(), s.getW(), s.getH(), s.getX(), s.getY());
    }

    @Override
    protected MyPolygonal copy() {
        return new MyStar(this);
    }

    public double getScale() {
        return scale;
    }

    private void setScale(double scale) {
        if (scale < 1) {
            this.scale = scale;
        } else {
            this.scale = 1.0 / scale;
        }
    }

    @Override
    public MyStar createShape(int n) {
        double theta = 4 * Math.PI / (double)n;
        double scale = 0.5;
        double sin = Math.sin(theta);
        double cos = Math.cos(theta);

        // n = 0, 1 のとき
        xPoints[0] = 0;
        yPoints[0] = -1;
        xPoints[1] = (Math.cos(theta/2) * xPoints[0] - Math.sin(theta/2) * yPoints[0]) * scale;
        yPoints[1] = (Math.sin(theta/2) * xPoints[0] + Math.cos(theta/2) * yPoints[0]) * scale;

        // n != 0, 1 のとき : 原点中心に回転させる
        for (int i = 2; i < n; i++) {
            xPoints[i] = cos * xPoints[i-2] - sin * yPoints[i-2];
            yPoints[i] = sin * xPoints[i-2] + cos * yPoints[i-2];
        }
        return this;
    }

    @Override
    public MyStar createShape(int n, int x, int y) {
        double theta = 4 * Math.PI / (double)n;
        double scale = 0.5;
        double sin = Math.sin(theta);
        double cos = Math.cos(theta);

        // n = 0, 1 のとき
        xPoints[0] = x - getX();
        yPoints[0] = y - getY();
        xPoints[1] = (Math.cos(theta/2) * xPoints[0] - Math.sin(theta/2) * yPoints[0]) * scale;
        yPoints[1] = (Math.sin(theta/2) * xPoints[0] + Math.cos(theta/2) * yPoints[0]) * scale;

        // n != 0, 1 のとき : 原点中心に回転させる
        for (int i = 2; i < n; i++) {
            xPoints[i] = cos * xPoints[i-2] - sin * yPoints[i-2];
            yPoints[i] = sin * xPoints[i-2] + cos * yPoints[i-2];
        }
        return this.trans(getX(), getY());
    }

    @Override
    public MyStar trans(int dx, int dy) {
        return (MyStar) super.trans(dx, dy);
    }

    @Override
    public MyStar scale(int w, int h) {
        return (MyStar) super.scale(w, h);
    }

    @Override
    public MyStar scale(double w, double h) {
        return (MyStar) super.scale(w, h);
    }

    @Override
    public Polygon scalePoints(int n, double[] x, double[] y, int x0, int y0, int gap) {
        double theta;
        int[] newx = new int[n];
        int[] newy = new int[n];
        int e = 1;
        for (int i = 0; i < n; i++) {
            e = (i % 2 == 0) ? (int)(1/getScale()) : 1;
            theta = Math.atan2((y[i] - y0) , (x[i] - x0));
            newx[i] = (int)(x[i] + gap * Math.cos(theta) * e);
            newy[i] = (int)(y[i] + gap * Math.sin(theta) * e);
        }
        return new Polygon(newx, newy, n);
    }

    //    protected MyStar calc(int n, double scale, int w, int h, int dx, int dy) {
//        createShape(n).scale(w, h).trans(dx, dy);
//        return this;
//    }

//    @Override
//    protected void calc() {
//        double[] x,y;
//        calc(getN(), getScale(), getW(), getH(), getX(), getY());
//    }

    //    @Override
//    public void calc() {
//        double theta = 4 * Math.PI / (double)getN();
//        double sin = Math.sin(theta);
//        double cos = Math.cos(theta);
//        int n = getN();
//
//        double[] xpts = new double[n];
//        double[] ypts = new double[n];
//
//        // n = 0, 1 のとき
//        xpts[0] = 0;
//        ypts[0] = 1;
//        xpts[1] = (Math.cos(theta/2) * xpts[0] - Math.sin(theta/2) * ypts[0]) * scale;
//        ypts[1] = (Math.sin(theta/2) * xpts[0] + Math.cos(theta/2) * ypts[0]) * scale;
//
//        // n != 0, 1 のとき : 原点中心に回転させる
//        for (int i = 2; i < n; i++) {
//            xpts[i] = cos * xpts[i-2] - sin * ypts[i-2];
//            ypts[i] = sin * xpts[i-2] + cos * ypts[i-2];
//        }
//
//        int w = getW();
//        int h = getH();
//        int x0 = getCenterX();
//        int y0 = getCenterY();
//
//        // サイズを変更し、平行移動
//        for (int i = 0; i < n; i++) {
//            xpts[i] = xpts[i] * w / 2 + x0;
//            ypts[i] = ypts[i] * h / 2 + y0;
//        }
//
//        setPoints(xpts, ypts);
//    }

//    public void draw(Graphics g) {
////        calc();
//        int x = getX();
//        int y = getY();
//        int w = getW();
//        int h = getH();
//        int n = getN();
//
//        int lineNumber, lineWidth;
//        Color fillColor, lineColor;
//
//        int[] xPointsInt = getXPointsInt();
//        int[] yPointsInt = getYPointsInt();
//
//        // Graphic2D
//        Graphics2D g2 = (Graphics2D) g;
//
//        // 線をセット
//        g2.setStroke(new BasicStroke(
//                getLineWidth(), BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER,
//                1.f, getDashArray(), 0.0f
//        ));
//
//        // 影の描画
//        if (hasShade()) {
//            g2.setColor(Color.black);
//            g2.fillPolygon(trans(10, xPointsInt), trans(10, yPointsInt), n);
//        }
//
//        // 塗りの描画 (FillColor == null なら透過)
//        if ((fillColor = getFillColor()) != null) {
//            g2.setColor(fillColor);
//            g2.fillPolygon(xPointsInt, yPointsInt, n);
//        }
//
//        // 線の描画 (lineColor == null || lineWidth == 0 なら透過)
//        if ((lineColor = getLineColor()) != null && (lineWidth = getLineWidth()) != 0) {
//
//            g2.setColor(lineColor);
//            lineNumber = getLineNumber();
//
//            if (lineNumber %2 == 1)
//                g2.drawPolygon(getXPointsInt(), getYPointsInt(), n);
//
//            for (int i = 0; i < lineNumber / 2; i++) {
//                int gap = lineNumber % 2 == 0 ? ((2 * i + 1) * lineWidth) : ((2 * (i + 1)) * lineWidth);
//                gap *= 2;
//
//                MyStar p1 = new MyStar(this);
//                MyStar p2 = new MyStar(this);
//
//                p1.createShape(n).scale(w+gap, h+gap).trans(x, y);
//                p2.createShape(n).scale(w-gap, h-gap).trans(x, y);
//
//                g2.drawPolygon(p1.getXPointsInt(), p1.getYPointsInt(), n);
//                if (w - gap >= 0 && h - gap >= 0)   // 図形が十分小さいときに、図形の内側に表示される線が負の値で飛んでしまう減少を防ぐ
//                    g2.drawPolygon(p2.getXPointsInt(), p2.getYPointsInt(), n);
//            }
//        }
//    }
}

/* StarButton */
class StarButton extends JButton {
    StateManager stateManager;

    public StarButton(StateManager stateManager) {
        super("Star");

        addActionListener(new StarListener());

        this.stateManager = stateManager;
    }

    class StarListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            stateManager.setState(new StarState(stateManager));
        }
    }
}

/* StarState */
class StarState implements State {
    StateManager stateManager;
    MyStar drawing;

    public StarState(StateManager stateManager) {
        this.stateManager = stateManager;
    }

    @Override
    public void mouseDown(int x, int y) {
        stateManager.addDrawing(
                drawing = new MyStar(x, y, 0, 0, stateManager.getLineColor(),
                        stateManager.getFillColor(), stateManager.getLineWidth(), stateManager.getLineNumber(),
                        stateManager.getDashArray(), stateManager.hasShade(), 5, 0.5)
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

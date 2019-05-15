import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/* MyStar */
public class MyStar extends MyPolygonal {
    private double scale;

    public MyStar(int xpt, int ypt) {
        this(xpt, ypt, 40, 40, 5, 0.5);
    }
    public MyStar(int xpt, int ypt, int wpt, int hpt) {
        this(xpt, ypt, wpt, hpt, 5, 0.5);
    }
    public MyStar(int xpt, int ypt, int wpt, int hpt, int n, double scale) {
        super(xpt, ypt, -wpt, -hpt, 2*n);
        setScale(scale);
        setPoints(new double[n], new double[n]);
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
    public void calc() {
        double theta = 4 * Math.PI / (double)getN();
        double sin = Math.sin(theta);
        double cos = Math.cos(theta);
        int n = getN();

        double[] xpts = new double[n];
        double[] ypts = new double[n];

        // n = 0, 1 のとき
        xpts[0] = 0;
        ypts[0] = 1;
        xpts[1] = (Math.cos(theta/2) * xpts[0] - Math.sin(theta/2) * ypts[0]) * scale;
        ypts[1] = (Math.sin(theta/2) * xpts[0] + Math.cos(theta/2) * ypts[0]) * scale;

        // n != 0, 1 のとき : 原点中心に回転させる
        for (int i = 2; i < n; i++) {
            xpts[i] = cos * xpts[i-2] - sin * ypts[i-2];
            ypts[i] = sin * xpts[i-2] + cos * ypts[i-2];
        }

        int w = getW();
        int h = getH();
        int x0 = getCenterX();
        int y0 = getCenterY();

        // サイズを変更し、平行移動
        for (int i = 0; i < n; i++) {
            xpts[i] = xpts[i] * w / 2 + x0;
            ypts[i] = ypts[i] * h / 2 + y0;
        }

        setPoints(xpts, ypts);
    }

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
    MyDrawing drawing;

    public StarState(StateManager stateManager) {
        this.stateManager = stateManager;
    }

    @Override
    public void mouseDown(int x, int y) {
        stateManager.addDrawing(drawing = new MyStar(x, y, 0, 0));
    }

    @Override
    public void mouseUp(int x, int y) {}

    @Override
    public void mouseDrag(int x, int y) {
        MyDrawing d = stateManager.getDrawing();
        d.setSize(d.getX() - x, d.getY() - y);
    }
}

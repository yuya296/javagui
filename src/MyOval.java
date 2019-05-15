import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyOval extends MyDrawing {

    // 長方形とは違い、中心の座標を引数に取ることにする。
    public MyOval(int xpt, int ypt) {
        this(xpt, ypt, 40, 40);
    }
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
        if (isDashed())
            g2.setStroke(new MyDashStroke(getLineWidth()));
        else
            g2.setStroke(new BasicStroke(getLineWidth()));
        g2.setColor(getFillColor());
        g2.fillOval(x, y, w, h);
        g2.setColor(getLineColor());
        g2.drawOval(x, y, w, h);
    }
}

class OvalButton extends JButton {
    StateManager stateManager;

    public OvalButton(StateManager stateManager) {
        super("Oval");

        addActionListener(new OvalListener());

        this.stateManager = stateManager;
    }

    class OvalListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            stateManager.setState(new OvalState(stateManager));
        }
    }
}

class OvalState implements State {
    StateManager stateManager;

    public OvalState(StateManager stateManager) {
        this.stateManager = stateManager;
    }

    @Override
    public void mouseDown(int x, int y) {
        stateManager.addDrawing(new MyOval(x, y, 0, 0));
    }

    @Override
    public void mouseUp(int x, int y) {}

    @Override
    public void mouseDrag(int x, int y) {
        MyDrawing d = stateManager.getDrawing();
        d.setSize(x - d.getX(), y - d.getY());
    }
}
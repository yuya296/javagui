import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/* MyRectangle */
public class MyRectangle extends MyDrawing {

    public MyRectangle(int xpt, int ypt) {
        super(xpt, ypt);
    }
    public MyRectangle(int xpt, int ypt, int wpt, int hpt) {
        this(xpt, ypt);
        setSize(wpt, hpt);
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

        if (isDashed())
            g2.setStroke(new MyDashStroke(getLineWidth()));
        else
            g2.setStroke(new BasicStroke(getLineWidth()));

        g2.setColor(getFillColor());
        g2.fillRect(x,y,w,h);
        g2.setColor(getLineColor());
        g2.drawRect(x,y,w,h);
    }
}

/* RectButton */
class RectButton extends JButton {
    StateManager stateManager;

    public RectButton(StateManager stateManager) {
        super("Rectangle");

        addActionListener(new RectListener());

        this.stateManager = stateManager;
    }

    class RectListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            stateManager.setState(new RectState(stateManager));
        }
    }
}

/* RectState */
class RectState implements State {
    StateManager stateManager;
    MyDrawing drawing;

    public RectState(StateManager stateManager) {
        this.stateManager = stateManager;
    }

    @Override
    public void mouseDown(int x, int y) {
        stateManager.addDrawing(drawing = new MyRectangle(x, y, 0, 0));
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

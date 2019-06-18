import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class MyOval extends MyDrawing {

    // 長方形とは違い、中心の座標を引数に取ることにする。

    public MyOval(int x, int y, int w, int h, Color lineColor, Color fillColor, int lineWidth, int lineNumber, float[] dashArray, boolean hasShade) {
        super(x, y, w, h, lineColor, fillColor, lineWidth, lineNumber, dashArray, hasShade);
    }
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
    // copy constructor
    public MyOval(MyOval d) {
        super(d);
    }

    @Override
    public MyOval clone() throws CloneNotSupportedException {
        return new MyOval(this);
    }

    @Override
    public void setRegion() {
        int x = getX();
        int y = getY();
        int w = getW();
        int h = getH();
        if (w < 0) {
            x += w;
            w *= -1;
        }
        if (h < 0) {
            y += h;
            h *= -1;
        }
        this.region = new Ellipse2D.Double(x, y, w, h);
    }

    @Override
    protected Shape getShape() {
        return new Rectangle(getX(), getY(), getW(), getH());
    }

    public void draw(Graphics g) {
        int x = getX();
        int y = getY();
        int w = getW();
        int h = getH();
        int lineNumber, lineWidth;
        Color fillColor, lineColor;

        // 高さや横幅が負のときのための処理
        if (w < 0) {
            x += w;
            w *= -1;
        }
        if (h < 0) {
            y += h;
            h *= -1;
        }


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
            g2.fillOval(x+10, y+10, w, h);
        }

        // 塗りの描画 (FillColor == null なら透過)
        if ((fillColor = getFillColor()) != null) {
            g2.setColor(fillColor);
            g2.fillOval(x,y,w,h);
        }

        // 線の描画 (lineColor == null || lineWidth == 0 なら透過)
        if ((lineColor = getLineColor()) != null && (lineWidth = getLineWidth()) != 0) {

            g2.setColor(lineColor);

            // n 重線の描画
            if ((lineNumber = getLineNumber()) % 2 == 0) {
                // 偶数の場合
                for (int i = 0; i < lineNumber/2; i++) {
                    int gap = (2 * i + 1) * lineWidth;
                    g2.drawOval(x-gap, y-gap, w+gap*2, h+gap*2);
                    g2.drawOval(x+gap, y+gap, w-gap*2, h-gap*2);
                }
            } else {
                // 奇数の場合
                g2.drawOval(x, y, w, h);
                for (int i = 0; i < lineNumber/2; i++) {
                    int gap = (2 * (i + 1)) * lineWidth;
                    g2.drawOval(x-gap, y-gap, w+gap*2, h+gap*2);
                    g2.drawOval(x+gap, y+gap, w-gap*2, h-gap*2);
                }
            }

        }

        super.draw(g);
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
    private MyDrawing drawing;

    public OvalState(StateManager stateManager) {
        this.stateManager = stateManager;
    }

    @Override
    public void mouseDown(int x, int y) {
        stateManager.addDrawing(
                drawing = new MyOval(x, y, 0, 0, stateManager.getLineColor(),
                        stateManager.getFillColor(), stateManager.getLineWidth(), stateManager.getLineNumber(),
                        stateManager.getDashArray(), stateManager.hasShade())
        );
    }

    @Override
    public void mouseUp(int x, int y) {
        drawing = null;
    }

    @Override
    public void mouseDrag(int x, int y) {
        drawing.setSize(x - drawing.getX(), y - drawing.getY());
    }
}
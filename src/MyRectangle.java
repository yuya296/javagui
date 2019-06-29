import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/* MyRectangle */
public class MyRectangle extends MyDrawing {

    public MyRectangle(int xpt, int ypt, int wpt, int hpt, Color lineColor, Color fillColor, int lineWidth, int lineNumber, float[] dashArray, boolean hasShade){
        super(xpt, ypt, wpt, hpt, lineColor, fillColor, lineWidth, lineNumber, dashArray, hasShade);
    }

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
    // copy constructor
    public MyRectangle(MyRectangle d) {
        super(d);
    }

    @Override
    public MyRectangle clone() throws CloneNotSupportedException {
        return new MyRectangle(this);
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
        region = new Rectangle(x, y, w, h);
    }

    @Override
    protected Shape getShape() {
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
        return new Rectangle(x, y, w, h);
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
            g2.fillRect(x+10, y+10, w, h);
        }

        // 塗りの描画 (FillColor == null なら透過)
        if ((fillColor = getFillColor()) != null) {
            g2.setColor(fillColor);
            g2.fillRect(x,y,w,h);
        }

        // 線の描画 (lineColor == null || lineWidth == 0 なら透過)
        if ((lineColor = getLineColor()) != null && (lineWidth = getLineWidth()) != 0) {

            g2.setColor(lineColor);

            // n 重線の描画
            if ((lineNumber = getLineNumber()) % 2 == 0) {
                // 偶数の場合
                for (int i = 0; i < lineNumber/2; i++) {
                    int gap = (2 * i + 1) * lineWidth;
                    g2.drawRect(x-gap, y-gap, w+gap*2, h+gap*2);
                    g2.drawRect(x+gap, y+gap, w-gap*2, h-gap*2);
                }
            } else {
                // 奇数の場合
                g2.drawRect(x, y, w, h);
                for (int i = 0; i < lineNumber/2; i++) {
                    int gap = (2 * (i + 1)) * lineWidth;
                    g2.drawRect(x-gap, y-gap, w+gap*2, h+gap*2);
                    g2.drawRect(x+gap, y+gap, w-gap*2, h-gap*2);
                }
            }

        }

        super.draw(g);

    }
}

/* RectButton */
class RectButton extends JButton {
    private StateManager stateManager;

    public RectButton(StateManager stateManager) {
        super(new ImageIcon("./img/rect.png"));

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
    private StateManager stateManager;
    private MyDrawing drawing;

    public RectState(StateManager stateManager) {
        this.stateManager = stateManager;
    }

    @Override
    public void mouseDown(int x, int y) {
        stateManager.unSelectAll();
        stateManager.addDrawing(
                drawing = new MyRectangle(x, y, 0, 0, stateManager.getLineColor(),
                        stateManager.getFillColor(), stateManager.getLineWidth(), stateManager.getLineNumber(),
                        stateManager.getDashArray(), stateManager.hasShade())
        );
    }

    @Override
    public void mouseUp(int x, int y) {
        drawing = null; // 描画終了
    }

    @Override
    public void mouseDrag(int x, int y) {
        drawing.setSize(x - drawing.getX(), y - drawing.getY());
    }
}

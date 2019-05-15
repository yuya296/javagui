// task2-8

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MyApplication extends JFrame {
    private StateManager stateManager;
    private MyCanvas canvas;

    public MyApplication() {
        super("My Paint Application");

        canvas = new MyCanvas();
        canvas.setBackground(Color.white);

        JPanel jp = new JPanel();
        jp.setLayout(new FlowLayout());

        stateManager = new StateManager(canvas);

        jp.add(new RectButton(stateManager));
        jp.add(new OvalButton(stateManager));
        jp.add(new PolygonalButton(stateManager));
        jp.add(new StarButton(stateManager));

        JCheckBox dashCheck = new JCheckBox("dash line");
        dashCheck.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                stateManager.setDashed(e.getStateChange() == ItemEvent.SELECTED);
            }
        });
        jp.add(dashCheck);

        JCheckBox shapeCheck = new JCheckBox("shape");
        shapeCheck.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                stateManager.setHasShape(e.getStateChange() == ItemEvent.SELECTED);
            }
        });

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(jp, BorderLayout.NORTH);
        getContentPane().add(canvas, BorderLayout.CENTER);

        // カーソルの位置を追跡
        canvas.addMouseListener(new MouseAdapter() {    // 無名内部クラス new Xxx() {}
            @Override
            public void mousePressed(MouseEvent e) {
                stateManager.mouseDown(e.getX(), e.getY());
            }
        });

        // カーソルの動きを追跡
        canvas.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                stateManager.mouseDrag(e.getX(), e.getY());
            }
        });

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    public Dimension getPreferredSize() {
        return new Dimension(300, 400);
    }

    public static void main(String[] args) {
        MyApplication app = new MyApplication();
        app.pack();
        app.setVisible(true);
    }
}

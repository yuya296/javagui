// task2-8

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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

        RectButton rectButton = new RectButton(stateManager);
        jp.add(rectButton);

        OvalButton ovalButton = new OvalButton(stateManager);
        jp.add(ovalButton);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(jp, BorderLayout.NORTH);
        getContentPane().add(canvas, BorderLayout.CENTER);

        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                stateManager.mouseDown(e.getX(), e.getY());
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

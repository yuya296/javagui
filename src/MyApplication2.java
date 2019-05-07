// Task2-3 マウスイベントの例(2)

import sun.tools.jconsole.BorderedComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MyApplication2 extends JFrame {
    public MyApplication2() {
        super("My Painter");
        JPanel jp = new JPanel();
        jp.setLayout(new BorderLayout());
        MyCanvas canvas = new MyCanvas();

        jp.add(BorderLayout.CENTER, canvas);
        getContentPane().add(jp);

        MyMouseAdapter ma = new MyMouseAdapter(canvas);
        canvas.addMouseListener(ma);

        addWindowListener(
                new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        System.exit(1);
                    }
                }
        );
    }

    public static void main(String[] args) {
        MyApplication2 ma = new MyApplication2();
        ma.setSize(400, 300);
        ma.setVisible(true);
    }
}

class MyMouseAdapter extends MouseAdapter {
    private MyCanvas canvas;

    public MyMouseAdapter(MyCanvas canvas) {
        this.canvas = canvas;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        canvas.addDrawing(new MyRectangle(e.getX(), e.getY()));

        canvas.repaint();
    }
}
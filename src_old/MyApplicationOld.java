// Task1

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MyApplicationOld extends JFrame {
    public MyApplicationOld() {
        super("My Painter");

        JPanel jp = new JPanel();
        jp.setLayout(new BorderLayout());
        this.getContentPane().add(jp);

        MyCanvas canvas = new MyCanvas();
        jp.add(canvas, BorderLayout.CENTER);

        // ======
        MyRectangle mr = new MyRectangle(20, 20);
        mr.setLineWidth(8);
        mr.setStrokeRound();
        canvas.addDrawing(mr);
        canvas.addDrawing(new MyRectangle(80, 80).setStrokeBroken(4));

        // =======

        /* WindowListener */
        this.addWindowListener(
                new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        System.exit(1);
                    }
                }
        );
    }

    public static void main(String[] args) {
        MyApplicationOld app = new MyApplicationOld();
        app.setSize(400, 300);
        app.setVisible(true);
    }
}

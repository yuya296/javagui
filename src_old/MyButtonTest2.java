// Task2-5

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyButtonTest2 extends JFrame {
    public MyButtonTest2() {
        super("MyButtonTest");

        JPanel jp = new JPanel();
        jp.setLayout(new FlowLayout());
        getContentPane().add(jp);

        Button rectButton = new Button("Rectangle");
        rectButton.addActionListener(new RectButtonListener());
        jp.add(rectButton);

        Button circleButton = new Button("Circle");
        circleButton.addActionListener(new CircleButtonListener());
        jp.add(circleButton);

        Button triangleButton = new Button("Triangle");
        triangleButton.addActionListener(new TriangleButtonListener());
        jp.add(triangleButton);

        setSize(300, 250);
    }

    public static void main(String[] args) {
        MyButtonTest2 myapp = new MyButtonTest2();
        myapp.setVisible(true);
    }
}

class RectButtonListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(null, "Rect is pressed.");
    }
}

class CircleButtonListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(null, "Circle is pressed.");
    }
}

class TriangleButtonListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(null, "Triangle is pressed.");
    }
}
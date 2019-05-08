// Task2-4

import javax.swing.*;
import java.awt.*;

public class MyButtonTest extends JFrame {
    public MyButtonTest() {
        super("MyButtonTest");

        // FlowLayout 左から部品を配置
        JPanel jp = new JPanel();
        jp.setLayout(new FlowLayout());
        getContentPane().add(jp);

        Button rectButton = new Button("Rectangle");
        jp.add(rectButton);

        Button circleButton = new Button("Circle");
        jp.add(circleButton);

        Button triangleButton = new Button("Triangle");
        jp.add(triangleButton);

        setSize(500, 250);
    }

    public static void main(String[] args) {
        MyButtonTest myapp = new MyButtonTest();
        myapp.setVisible(true);
    }
}

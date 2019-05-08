// Task2-6

import javax.swing.*;
import java.awt.*;

public class MyCheckBoxTest extends JFrame {
    public MyCheckBoxTest() {
        super("MyCheckBoxTest");

        JPanel jp = new JPanel();
        jp.setLayout(new FlowLayout());
//        getContentPane().add(jp);

        JCheckBox dashCheck = new JCheckBox("dash line");
        jp.add(dashCheck);

        JCheckBox boldCheck = new JCheckBox("bold line");
        jp.add(boldCheck);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(jp, BorderLayout.NORTH);

        setSize(300, 300);
    }

    public static void main(String[] args) {
        MyCheckBoxTest myapp = new MyCheckBoxTest();
        myapp.setVisible(true);
    }
}

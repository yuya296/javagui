// Task2-2

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MyMouseEventTest extends JFrame {
    MyMouseEventTest() {
        super("Mouse Event Test");
        addMouseListener(new MyMouseListener());
        setSize(100, 100);
    }

    public static void main(String[] args) {
        MyMouseEventTest myapp = new MyMouseEventTest();
        myapp.setVisible(true);
    }
}

class MyMouseListener extends MouseAdapter {

    @Override
    public void mouseClicked(MouseEvent e) {
        // クリックされたとき
        System.out.println("Clicked!");
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // カーソルが Window に入ったとき
        System.out.println("Entered!");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // カーソルが Window から出たとき
        System.out.println("Exited!");
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // press
        System.out.println("Pressed!");
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // release
        System.out.println("Released!");
    }

}
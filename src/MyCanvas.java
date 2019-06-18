// Task1

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Enumeration;
import java.util.Vector;

public class MyCanvas extends JPanel implements KeyListener {
    Mediator mediator;

    public MyCanvas() {
        this.mediator = new Mediator(this);
        setBackground(Color.white);
    }

    public Mediator getMediator() {
        return mediator;
    }

    public void paint(Graphics g) {
        super.paint(g);

        Enumeration<MyDrawing> e = mediator.drawingsElements();
        while (e.hasMoreElements()) {
            MyDrawing d = e.nextElement();
            d.draw(g);
        }
    }
//
//    public void addDrawing(MyDrawing d) {
//        drawings.add(d);
//    }
//    public void addDrawing(int i, MyDrawing d) {
//        drawings.add(i, d);
//    }
//
//    public int getDrawingsSize() {
//        return drawings.size();
//    }
//
//    public void removeDrawing(MyDrawing d) {
//        drawings.remove(d);
//    }


    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("key pushed:\t" + e.getKeyCode());
        switch (e.getKeyCode()) {
            case 8:
                mediator.removeDrawing(); break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}

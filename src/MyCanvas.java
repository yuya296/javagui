// Task1

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Enumeration;
import java.util.Vector;

public class MyCanvas extends JPanel {
    private Mediator mediator;

    public MyCanvas() {
        this.mediator = new Mediator(this);
        setBackground(Color.white);
    }

    public Mediator getMediator() {
        return mediator;
    }

    public void paint(Graphics g) {
        super.paint(g);
        for (MyDrawing d : mediator.getDrawings()) d.draw(g);
    }

}

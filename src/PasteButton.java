import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PasteButton extends JButton {
    Mediator mediator;

    public PasteButton(Mediator mediator) {
        super("Paste");
        addActionListener(new PasteListener());
        this.mediator = mediator;
    }

    class PasteListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            MyDrawing d = mediator.selectedDrawing;
            mediator.paste(d.getX() + 40, d.getY() + 40);
        }
    }
}

class CutButton extends JButton {
    Mediator mediator;

    public CutButton(Mediator mediator) {
        super("Cut");
        addActionListener(new CutListener());
        this.mediator = mediator;
    }

    class CutListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            mediator.cut();
        }
    }
}

class CopyButton extends JButton {
    Mediator mediator;

    public CopyButton(Mediator mediator) {
        super("Copy");
        addActionListener(new CopyListener());
        this.mediator = mediator;
    }

    class CopyListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            mediator.copy();
        }
    }
}

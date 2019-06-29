import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PasteButton extends JButton {
    private Mediator mediator;

    public PasteButton(Mediator mediator) {
        super(new ImageIcon("./img/paste.png"));
        addActionListener(new PasteListener());
        this.mediator = mediator;
    }

    class PasteListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            mediator.paste();
        }
    }
}

class CutButton extends JButton {
    private Mediator mediator;

    public CutButton(Mediator mediator) {
        super(new ImageIcon("./img/cut.png"));
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
    private Mediator mediator;

    public CopyButton(Mediator mediator) {
        super(new ImageIcon("./img/copy.png"));
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

class DeleteButton extends JButton {
    private Mediator mediator;

    public DeleteButton(Mediator mediator) {
        super(new ImageIcon("./img/delete.png"));
        addActionListener(new CopyListener());
        this.mediator = mediator;
    }

    class CopyListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            mediator.removeSelectedDrawing();
        }
    }
}
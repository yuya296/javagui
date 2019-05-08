import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OvalButton extends JButton {
    StateManager stateManager;

    public OvalButton(StateManager stateManager) {
        super("Oval");

        addActionListener(new OvalListener());

        this.stateManager = stateManager;
    }

    class OvalListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            stateManager.setState(new OvalState(stateManager));
        }
    }
}

class OvalState implements State {
    StateManager stateManager;

    public OvalState(StateManager stateManager) {
        this.stateManager = stateManager;
    }

    @Override
    public void mouseDown(int x, int y) {
        stateManager.addDrawing(new MyOval(x, y));
    }

    @Override
    public void mouseUp(int x, int y) {}

    @Override
    public void mouseDrag(int x, int y) {}
}
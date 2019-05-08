import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RectButton extends JButton {
    StateManager stateManager;

    public RectButton(StateManager stateManager) {
        super("Rectangle");

        addActionListener(new RectListener());

        this.stateManager = stateManager;
    }

    class RectListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            stateManager.setState(new RectState(stateManager));
            System.out.println("clicked");
        }
    }
}

class RectState implements State {
    StateManager stateManager;

    public RectState(StateManager stateManager) {
        this.stateManager = stateManager;
    }

    @Override
    public void mouseDown(int x, int y) {
        stateManager.addDrawing(new MyRectangle(x, y));
    }

    @Override
    public void mouseUp(int x, int y) {}
    @Override
    public void mouseDrag(int x, int y) {}
}

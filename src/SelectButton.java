import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SelectButton extends JButton {
    private StateManager stateManager;

    public SelectButton(StateManager stateManager) {
        super("Select");
        addActionListener(new SelectListener());
        this.stateManager = stateManager;
    }

    class SelectListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            stateManager.setState(new SelectState(stateManager));
        }
    }
}

class SelectState implements State {
    private StateManager stateManager;
    private int pre_x, pre_y;

    public SelectState(StateManager stateManager) {
        this.stateManager = stateManager;
    }

    @Override
    public void mouseDown(int x, int y) {
        stateManager.setSelected(x, y);
        pre_x = x;
        pre_y = y;
    }

    @Override
    public void mouseDrag(int x, int y) {
        MyDrawing d = stateManager.getSelectedDrawing();
        d.move(x - pre_x, y - pre_y);
        pre_x = x;
        pre_y = y;
    }

    @Override
    public void mouseUp(int x, int y) {}
}
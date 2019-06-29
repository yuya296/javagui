import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

class DrawToolsPanel extends JPanel {
    private StateManager stateManager;
    private ArrayList<JButton> buttons;

    DrawToolsPanel(StateManager stateManager) {
        super();
        this.stateManager = stateManager;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.buttons = new ArrayList<>();

        addButton(new SelectButton(stateManager));
        addButton(new RectButton(stateManager));
        addButton(new OvalButton(stateManager));
        addButton(new PolygonalButton(stateManager));
        addButton(new StarButton(stateManager));
        add(new JSeparator(JSeparator.HORIZONTAL));
        addButton(new PasteButton(stateManager.getMediator()));
        addButton(new CopyButton(stateManager.getMediator()));
        addButton(new CutButton(stateManager.getMediator()));
        addButton(new DeleteButton(stateManager.getMediator()));


        setButton(buttons.get(0));
    }

    private void addButton(JButton button) {
        buttons.add(button);
        this.add(button);
    }

    void setButton(JButton button) {
        for (JButton b : buttons) b.setBackground(Color.white);
        button.setBackground(Color.red);
    }

}

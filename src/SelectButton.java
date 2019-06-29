import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SelectButton extends JButton {
    private StateManager stateManager;

    public SelectButton(StateManager stateManager) {
        super(new ImageIcon("img/select.png"));
        // icon: https://iconmonstr.com/
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
    private SelectStateAdapter selectState;

    public SelectState(StateManager stateManager) {
        this.stateManager = stateManager;
    }

    private void setSelectState(int x, int y) {
        MyDrawing d = stateManager.getDrawingAt(x, y);

        // クリックした箇所に何もないとき
        if (d == null) selectState = new NoObjectClickedState(stateManager, d);

        // クリックした箇所に図形があったとき
        else selectState = d.isSelected() ?
                    new SelectedObjectClickedState(stateManager, d) :
                    new NotSelectedObjectClickedState(stateManager, d);
    }

    @Override
    public void mouseDown(int x, int y) {
        setSelectState(x, y);   // selectState の更新
        selectState.mouseDown(x,y);
    }

    @Override
    public void mouseDrag(int x, int y) {
        selectState.mouseDrag(x,y);
    }

    @Override
    public void mouseUp(int x, int y) {
        selectState.mouseUp(x,y);
    }
}


/* オブジェクトの選択についてのAdapter */
abstract class SelectStateAdapter implements State {
    StateManager stateManager;
    protected int dx, dy;
    protected MyDrawing targetDrawing;

    public SelectStateAdapter(StateManager stateManager, MyDrawing targetDrawing) {
        this.stateManager = stateManager;
        this.targetDrawing = targetDrawing;
    }

    @Override
    public void mouseDown(int x, int y) {
        dx = x;
        dy = y;
    }

    @Override
    public void mouseDrag(int x, int y) {
        dx = x;
        dy = y;
    }

    @Override
    public void mouseUp(int x, int y) {}
}

/* 選択済みオブジェクトがクリックされたとき */
class SelectedObjectClickedState extends SelectStateAdapter {
    boolean moved = false;
    public SelectedObjectClickedState(StateManager stateManager, MyDrawing targetDrawing) {
        super(stateManager, targetDrawing);
        System.out.println("選択済みオブジェクトがクリックされました.");
    }

    @Override
    public void mouseDrag(int x, int y) {
        stateManager.move(x - dx, y - dy);
        moved = true;
        super.mouseDrag(x, y);
    }

    @Override
    public void mouseUp(int x, int y) {
        super.mouseUp(x,y);
        // Shiftキーが押されている かつ カーソルが動いていない
        if (stateManager.isShiftKeyPressed() && !moved)
            stateManager.unSelect(targetDrawing);
    }
}

/* 未選択オブジェクトがクリックされたとき */
class NotSelectedObjectClickedState extends SelectStateAdapter {
    public NotSelectedObjectClickedState(StateManager stateManager, MyDrawing targetDrawing) {
        super(stateManager, targetDrawing);
        System.out.println("未選択オブジェクトがクリックされました.");
    }

    @Override
    public void mouseDown(int x, int y) {
        super.mouseDown(x,y);

        // Shiftキーが押されていないなら, 全ての選択を外す
        if (!stateManager.isShiftKeyPressed()) stateManager.unSelectAll();

        // ターゲットのオブジェクトを選択
        stateManager.setSelected(x, y);
    }

    @Override
    public void mouseDrag(int x, int y) {
        stateManager.move(x - dx, y - dy);
        super.mouseDrag(x, y);
    }
}

/* 何もない場所がクリックされたとき */
class NoObjectClickedState extends SelectStateAdapter {
    private MyDrawing selectBox;
    static Color fillColor = new Color(0, 255, 255, 32);
    static Color lineColor = new Color(0, 255, 255);

    public NoObjectClickedState(StateManager stateManager, MyDrawing targetDrawing) {
        super(stateManager, targetDrawing);
        System.out.println("何もない場所がクリックされました.");
    }

    @Override
    public void mouseDown(int x, int y) {
        super.mouseDown(x, y);

        // Shiftキーが押されていないなら, 全ての選択を外す
        if (!stateManager.isShiftKeyPressed()) {
            stateManager.unSelectAll();
            System.out.println("unSelectedAll");
        }

        selectBox = new MyRectangle(x, y, 0, 0, lineColor, fillColor, 1, 1, null, false) {
            @Override
            int SIZE() {
                return 0;
            }
        };
        stateManager.addDrawing(selectBox);
    }

    @Override
    public void mouseDrag(int x, int y) {
        super.mouseDrag(x, y);
        selectBox.setSize(x - selectBox.getX(), y - selectBox.getY());
    }

    @Override
    public void mouseUp(int x, int y) {
        super.mouseUp(x, y);
        stateManager.removeDrawing(selectBox);
        stateManager.setIntersects(selectBox.getShape().getBounds());

    }
}
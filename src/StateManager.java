// Task2-8

public class StateManager {
    private State state;
    private MyCanvas canvas;

    public StateManager(MyCanvas canvas) {
        this.canvas = canvas;
        setState(new RectState(this));
    }

    void addDrawing(MyDrawing d) {
        canvas.addDrawing(d);   // キャンバスに加える
        canvas.repaint();   // キャンバスを再描画
    }

    void setState(State state) {
        this.state = state;
    }

    void mouseDown(int x, int y) {
        state.mouseDown(x, y);
    }

}

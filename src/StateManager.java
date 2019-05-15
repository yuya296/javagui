// Task2-8

public class StateManager {
    private State state;
    private MyCanvas canvas;
    private MyDrawing drawing;
    private boolean isDashed = false;
    private boolean hasShape = false;

    public StateManager(MyCanvas canvas) {
        this.canvas = canvas;
        setState(new RectState(this));
    }

    void addDrawing(MyDrawing d) {
        canvas.addDrawing(d);   // キャンバスに加える
        d.setDashed(isDashed);
        setDrawing(d);
        canvas.repaint();   // キャンバスを再描画
    }

    void setState(State state) {
        this.state = state;
    }

    public void setDrawing(MyDrawing drawing) {
        this.drawing = drawing;
    }

    public void setHasShape(boolean hasShape) {
        this.hasShape = hasShape;
    }

    void mouseDown(int x, int y) {
        state.mouseDown(x, y);
    }

    void mouseDrag(int x, int y) {
        state.mouseDrag(x, y);
        canvas.repaint();
    }

    MyDrawing getDrawing() {
        return drawing;
    }

    void setDashed(boolean b) {
        isDashed = b;
    }

}

interface State {
    public void mouseDown(int x, int y);
    public void mouseUp(int x, int y);
    public void mouseDrag(int x, int y);
}

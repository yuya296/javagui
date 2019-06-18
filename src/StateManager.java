// Task2-8

import java.awt.*;

/**
 * interface State
 */
interface State {
    public void mouseDown(int x, int y);
    public void mouseUp(int x, int y);
    public void mouseDrag(int x, int y);
}


/**
 * class StateManager
 */
public class StateManager {
    private State state;
    private MyCanvas canvas;
    private Mediator mediator;

    /* 描画中の図形 drawings : MyDrawing */
    private MyDrawing drawing;
    public MyDrawing getDrawing() {
        return drawing;
    }
    public void setDrawing(MyDrawing drawing) {
        this.drawing = drawing;
    }


    /* 線の太さ lineWidth : int */
    private int lineWidth = 1;  // 線の太さ
    public int getLineWidth() {
        return this.lineWidth;
    }
    public void setLineWidth(int lineWidth) {
        this.lineWidth = lineWidth;
        mediator.setLineWidth(lineWidth);
    }

    /* hasShade : boolean 影の有無 */
    private boolean hasShade = false;
    public void setShade(boolean hasShade) {
        this.hasShade = hasShade;
        mediator.setShade(hasShade);
    }
    public boolean hasShade() {
        return hasShade;
    }

    /* lineNumber : int n重線 */
    private int lineNumber = 1; // n 重線
    public int getLineNumber() {
        return lineNumber;
    }
    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
        mediator.setLineNumber(lineNumber);
    }

    /* lineColor : Color 線の色 */
    private Color lineColor = Color.black;
    public void setLineColor(Color lineColor) {
        this.lineColor = lineColor;
        mediator.setLineColor(lineColor);
    }
    public Color getLineColor() {
        return lineColor;
    }

    /* fillColor : Color 塗りの色 */
    private Color fillColor = Color.white;
    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
        mediator.setFillColor(fillColor);
    }
    public Color getFillColor() {
        return fillColor;
    }

    /* dashArray : float[] 破線の形状 */
    private float[] dashArray = null;
    public float[] getDashArray() {
        if (dashArray == null) return dashArray;

        float[] arr = new float[dashArray.length];
        for (int i = 0; i < dashArray.length; i++)
            arr[i] = dashArray[i] * this.lineWidth;
        return arr;
    }
    public void setDashArray(float[] dashArray) {
        this.dashArray = dashArray;
        mediator.setDashArray(dashArray);
    }

    /* コンストラクタ */
    public StateManager(MyCanvas canvas) {
        this.canvas = canvas;
        this.mediator = canvas.getMediator();
        setState(new SelectState(this));
    }

    /* 描画リストにオブジェクトを追加 */
    void addDrawing(MyDrawing d) {
        MyDrawing oldDrawing = getDrawing();    // 古いDrawing
        if (oldDrawing != null) {
            oldDrawing.setSelected(false);
        }

        canvas.getMediator().addDrawing(d);
        setDrawing(d);
        setSelectedDrawing(d);
        d.setSelected(true);
        canvas.repaint();   // キャンバスを再描画
    }

    /* state をセット */
    void setState(State state) {
        this.state = state;
    }
    public State getState() {
        return state;
    }

    /* mediator をセット */
    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }
    public Mediator getMediator() {

        return mediator;
    }

    /* mediator の setSelected() を呼び出す */
    private MyDrawing selectedDrawing = Mediator.NULL_DRAWING;
    public void setSelected(int x, int y) {
        mediator.setSelected(x, y);
    }
    public void setSelectedDrawing(MyDrawing selectedDrawing) {
        mediator.setSelectedDrawing(this.selectedDrawing = selectedDrawing);
    }
    public MyDrawing getSelectedDrawing() {
        return (this.selectedDrawing = mediator.getSelectedDrawing());
    }

    /**
     *  マウスの挙動
     */
    void mouseDown(int x, int y) {
        state.mouseDown(x, y);
    }
    void mouseDrag(int x, int y) {
        state.mouseDrag(x, y);
        canvas.repaint();
    }
    void mouseUp(int x, int y) {
        state.mouseUp(x, y);
    }
}

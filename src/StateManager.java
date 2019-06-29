// Task2-8

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.File;

/* interface State */
interface State {
    void mouseDown(int x, int y);
    void mouseUp(int x, int y);
    void mouseDrag(int x, int y);
}


/* class StateManager */
public class StateManager {
    private State state;
    private Mediator mediator;

    private boolean shiftKeyPressed = false;  // Shiftキーが押されているか

    void setShiftKeyPressed(boolean shiftKeyPressed) {
        this.shiftKeyPressed = shiftKeyPressed;
    }
    boolean isShiftKeyPressed() {
        return shiftKeyPressed;
    }

    private int lineWidth = 1;  // 線の太さ
    private boolean hasShade = false;   // 影の有無
    private int lineNumber = 1; // n 重線
    private Color lineColor = Color.black;
    private Color fillColor = Color.white;
    private float[] dashArray = null;
    private File currentFile;   // 操作中のファイル

    /* コンストラクタ */
    StateManager(Mediator mediator) {
        this.mediator = mediator;
        setState(new SelectState(this));
    }

    /* 線の太さ lineWidth : int */
    public int getLineWidth() {
        return this.lineWidth;
    }
    public void setLineWidth(int lineWidth) {
        this.lineWidth = lineWidth;
        mediator.setLineWidth(lineWidth);
        mediator.setDashArray(getDashArray());
    }

    /* hasShade : boolean 影の有無 */
    public void setShade(boolean hasShade) {
        this.hasShade = hasShade;
        mediator.setShade(hasShade);
    }
    public boolean hasShade() {
        return hasShade;
    }

    /* lineNumber : int n重線 */
    public int getLineNumber() {
        return lineNumber;
    }
    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
        mediator.setLineNumber(lineNumber);
    }

    /* lineColor : Color 線の色 */
    public void setLineColor(Color lineColor) {
        this.lineColor = lineColor;
        mediator.setLineColor(lineColor);
    }
    public Color getLineColor() {
        return lineColor;
    }

    /* fillColor : Color 塗りの色 */
    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
        mediator.setFillColor(fillColor);
    }
    public Color getFillColor() {
        return fillColor;
    }

    /* dashArray : float[] 破線の形状 */
    public float[] getDashArray() {
        if (dashArray == null) return dashArray;

        float[] arr = new float[dashArray.length];
        for (int i = 0; i < dashArray.length; i++)
            arr[i] = dashArray[i] * this.lineWidth;
        return arr;
    }
    public void setDashArray(float[] dashArray) {
        System.out.println("setDashArray(" + dashArray + ")");
        this.dashArray = dashArray;
        mediator.setDashArray(getDashArray());
    }

    // ==========

    /* 描画リストにオブジェクトを追加 */
    void addDrawing(MyDrawing d) {
        mediator.addDrawing(d);
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
    void setSelected(int x, int y) {
        mediator.setSelected(x, y);
    }

    MyDrawing getDrawingAt(int x, int y) {
        return mediator.getDrawingAt(x, y);
    }

    void unSelect(MyDrawing d) {
        if (isShiftKeyPressed()) mediator.unSelect(d);
    }

    void unSelectAll() {
        mediator.unSelectAll();
    }

    void selectAll() {
        mediator.selectAll();
    }

    void setIntersects(Rectangle2D r) {
        mediator.setIntersects(r);
    }

    // ファイル名の保存
    public void setCurrentFile(File currentFile) {
        this.currentFile = currentFile;
    }
    public File getCurrentFile() {
        return currentFile;
    }

    void open() {

    }

    public void save() {
        currentFile = mediator.save(currentFile);
    }


    public void removeDrawing(MyDrawing d) {
        mediator.removeDrawing(d);
    }

    public void move(int dx, int dy) {
        mediator.move(dx, dy);
    }
    public void move(int dx, int dy, MyDrawing d) {
        mediator.move(dx, dy, d);
    }

    /* マウスの挙動 */
    void mouseDown(int x, int y) {
        state.mouseDown(x, y);
    }
    void mouseDrag(int x, int y) {
        state.mouseDrag(x, y);
        mediator.repaint();
    }
    void mouseUp(int x, int y) {
        state.mouseUp(x, y);
    }
}

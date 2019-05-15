// Task1

import java.awt.*;

public class MyDrawingOld {
    private int x, y, w, h;
    private Color lineColor, fillColor;
    private int lineWidth;
    private int strokeCap, strokeJoin;
    private float miterlimit;
    private float[] dashArray;
    private float dash_phase;


    public MyDrawingOld(int x, int y, int w, int h, Color lineColor, Color fillColor, int lineWidth) {
        setLocation(x, y);
        setSize(w, h);
        setLineColor(lineColor);
        setFillColor(fillColor);
        setLineWidth(lineWidth);
        setStrokeCap(BasicStroke.CAP_SQUARE);
        setStrokeJoin(BasicStroke.JOIN_MITER);
        setMiterlimit(1.0f);
        setDashArray(null);
        setDash_phase(0);
    }
    public MyDrawingOld(int x, int y, Color lineColor, Color fillColor, int lineWidth) {
        this(x, y, 40, 40, lineColor, fillColor, lineWidth);
    }
    public MyDrawingOld(int x, int y) {
        this(x, y, 40, 40, Color.black, Color.white, 1);
    }
    public MyDrawingOld() {
        this(0, 0, 40, 40, Color.black, Color.white, 1);
    }

    public void draw(Graphics g){}

    public void move(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }

    // 場所の座標: int x, y
    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public int getX() {
        return this.x;
    }
    public int getY() {
        return this.y;
    }

    // 大きさ: int w, h
    public void setSize(int w, int h) {
        this.w = w;
        this.h = h;
    }
    public int getW() {
        return this.w;
    }
    public int getH() {
        return this.h;
    }

    // 線の色: Color lineColor
    public Color getLineColor() {
        return this.lineColor;
    }
    public void setLineColor(Color lineColor) {
        this.lineColor = lineColor;
    }

    // 塗り色: Color fillColor
    public Color getFillColor() {
        return this.fillColor;
    }
    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }

    // 線の太さ: int lineWidth
    public int getLineWidth() {
        return this.lineWidth;
    }
    public void setLineWidth(int lineWidth) {
        this.lineWidth = lineWidth;
    }

    // 両端の形: strokeCap
    public void setStrokeCap(int strokeCap) {
        this.strokeCap = strokeCap;
    }
    public int getStrokeCap() {
        return strokeCap;
    }

    // 先の接合部: strokeJoin
    public void setStrokeJoin(int strokeJoin) {
        this.strokeJoin = strokeJoin;
    }
    public int getStrokeJoin() {
        return strokeJoin;
    }

    // 接合トリミング制限値: miterlimit
    public void setMiterlimit(float miterlimit) {
        this.miterlimit = miterlimit;
    }
    public float getMiterlimit() {
        return miterlimit;
    }

    public BasicStroke getStroke() {
        return new BasicStroke(lineWidth, strokeCap, strokeJoin, miterlimit, dashArray, dash_phase);
    }

    // 破線パターンの配列: dashArray
    public void setDashArray(int i) {
        float[] arr = {(float)i};
        setDashArray(arr);
    }
    public void setDashArray(float[] dashArray) {
        this.dashArray = dashArray;
    }
    public float[] getDashArray() {
        return dashArray;
    }

    // 破線の開始位置: dash_phase
    public void setDash_phase(float dash_phase) {
        this.dash_phase = dash_phase;
    }
    public float getDash_phase() {
        return dash_phase;
    }


// ===

    // 線を破線にする
    public MyDrawingOld setStrokeBroken(int width) {
        setDashArray(width);
        return this;
    }
    public MyDrawingOld setStrokeBroken() {
        setStrokeBroken(6);
        return this;
    }

    // 線を実線にする
    public MyDrawingOld setStrokeSolid() {
        setDashArray(null);
        return this;
    }

    // 角付きの線にする
    public MyDrawingOld setStrokeSquare() {
        setStrokeCap(BasicStroke.CAP_SQUARE);
        setStrokeJoin(BasicStroke.JOIN_MITER);
        return this;
    }

    // 丸角の線にする
    public MyDrawingOld setStrokeRound() {
        setStrokeCap(BasicStroke.CAP_ROUND);
        setStrokeJoin(BasicStroke.JOIN_ROUND);
        return this;
    }
}

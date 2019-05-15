// Task1

import java.awt.*;

public class MyDrawing {
    private int x, y, w, h;
    private Color lineColor, fillColor;
    private int lineWidth;
    private boolean isDashed = false;   // 破線かどうか

    // Constructor
    public MyDrawing(int x, int y, int w, int h, Color lineColor, Color fillColor, int lineWidth) {
        setLocation(x, y);
        setSize(w, h);
        setLineColor(lineColor);
        setFillColor(fillColor);
        setLineWidth(lineWidth);
    }
    public MyDrawing(int x, int y, Color lineColor, Color fillColor, int lineWidth) {
        this(x, y, 40, 40, lineColor, fillColor, lineWidth);
    }
    public MyDrawing(int x, int y) {
        this(x, y, 40, 40, Color.black, Color.white, 1);
    }
    public MyDrawing() {
        this(0, 0, 40, 40, Color.black, Color.white, 1);
    }


    public void draw(Graphics g){}

    // 場所の移動
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
    // 図形の中心座標
    public int getCenterX() {
        return getX() - getW() / 2;
    }
    public int getCenterY() {
        return getY() - getH() / 2;
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


    // 破線状態の切り替え
    public void setDashed(boolean dashed) {
        isDashed = dashed;
    }

    // 破線状態の取得
    public boolean isDashed() {
        return isDashed;
    }

}

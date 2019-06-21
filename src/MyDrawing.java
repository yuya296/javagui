// Task1

import java.awt.*;
import java.awt.geom.Area;

public class MyDrawing {

    // 位置、サイズ
    private int x, y, w, h;

    private Color lineColor;    // 線の色
    private int lineWidth;      // 線の太さ
    private int lineNumber = 3; // n 重線
    private float[] dashArray = null;

    private Color fillColor;    // 図形の塗り色
    final int SIZE = 7; // 選択表示矩形に付く四角形の大きさ
    Shape region;


    // Constructor
    public MyDrawing(int x, int y, int w, int h, Color lineColor, Color fillColor, int lineWidth, int lineNumber, float[] dashArray, boolean hasShade) {
        setLocation(x, y);
        setSize(w, h);
        setLineColor(lineColor);
        setFillColor(fillColor);
        setLineWidth(lineWidth);
        setLineNumber(lineNumber);
        setDashArray(dashArray);
        setShade(hasShade);
    }
    public MyDrawing(int x, int y, int w, int h, Color lineColor, Color fillColor, int lineWidth) {
        this(x, y, w, h, lineColor, fillColor, lineWidth, 1, null, false);
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
    public MyDrawing(MyDrawing d) {
        this(d.getX(), d.getY(), d.getW(), d.getH(), d.getLineColor(), d.getFillColor(), d.getLineWidth(), d.getLineNumber(), d.getDashArray(), d.hasShade());
    }

    @Override
    public MyDrawing clone() throws CloneNotSupportedException {
        return new MyDrawing(this);
    }

    public void draw(Graphics g){
        if (isSelected) {

            // バウンドボックス
            Rectangle bounds = getShape().getBounds();
            int bx = bounds.x;
            int by = bounds.y;
            int bw = bounds.width;
            int bh = bounds.height;

            // 高さや横幅が負のときのための処理
            if (bw < 0) {
                bx += bw;
                bw *= -1;
            }
            if (bh < 0) {
                by += bh;
                bh *= -1;
            }

            Graphics2D g2 = (Graphics2D) g;

            g2.setStroke(new BasicStroke());
            g2.setColor(Color.GREEN);
            g2.fillRect(bx+bw/2-SIZE/2,by-SIZE/2,       SIZE, SIZE);
            g2.fillRect(bx-SIZE/2,    by+bh/2-SIZE/2,   SIZE, SIZE);
            g2.fillRect(bx+bw/2-SIZE/2,by+bh-SIZE/2,     SIZE, SIZE);
            g2.fillRect(bx+bw-SIZE/2,  by+bh/2-SIZE,     SIZE, SIZE);
            g2.fillRect(bx-SIZE/2,    by-SIZE/2,       SIZE, SIZE);
            g2.fillRect(bx+bw-SIZE/2,  by-SIZE/2,       SIZE, SIZE);
            g2.fillRect(bx-SIZE/2,    by+bh-SIZE/2,     SIZE, SIZE);
            g2.fillRect(bx+bw-SIZE/2,  by+bh-SIZE/2,     SIZE, SIZE);

            g2.drawRect(bx, by, bw, bh);
        }
    }

    // その図形の Shape 型の値を返す
    protected Shape getShape() {
        return new Area();
    }

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
//        setRegion();
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

    // n重線
    public int getLineNumber() {
        return lineNumber;
    }
    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    // Dash Array
    public void setDashArray(float[] dashArray) {
        this.dashArray = dashArray;
    }
    public float[] getDashArray() {
        return dashArray;
    }

    // 影
    private boolean hasShade;
    public boolean hasShade() {
        return hasShade;
    }
    public void setShade(boolean b) {
        this.hasShade = b;
    }

    // 選択されているか : isSelected
    private boolean isSelected;
    public void setSelected(boolean selected) {
        isSelected = selected;
    }
    public boolean isSelected() {
        return isSelected;
    }

    // (x, y) が図形に含まれているか
    public boolean contains(int x, int y) {
        setRegion();
        return region.contains(x, y);
    }

    public void setRegion() {}
}

import java.awt.*;
import java.util.Enumeration;
import java.util.Vector;

public class Mediator {
    Vector<MyDrawing> drawings;
    MyCanvas canvas;
    MyDrawing selectedDrawing = NULL_DRAWING;
    public static NullDrawing NULL_DRAWING = new NullDrawing();

    MyDrawing buffer = NULL_DRAWING;    // Cut / Copy / Paste 用の buffer

    public Mediator(MyCanvas canvas) {
        this.canvas = canvas;
        drawings = new Vector<>();
    }

    public Enumeration<MyDrawing> drawingsElements() {
        return drawings.elements();
    }

    public void addDrawing(MyDrawing d) {
        drawings.add(d);
        setSelectedDrawing(d);
        System.out.println("created:\t" + d);   // LOG
    }

    public void removeDrawing(MyDrawing d) {
        drawings.remove(d);
        repaint();
    }
    public void removeDrawing() {
        removeDrawing(selectedDrawing);
    }

    public MyDrawing getSelectedDrawing() {
        return selectedDrawing;
    }

    public void setSelectedDrawing(MyDrawing selectedDrawing) {
        this.selectedDrawing.setSelected(false);    // 直前の drawing の選択を外す
        this.selectedDrawing = selectedDrawing;     // 選択中の drawing として selectedDrawing を設定
        selectedDrawing.setSelected(true);  // drawing を選択状態にする
    }

    public void move(int dx, int dy) {
        selectedDrawing.move(dx, dy);
    }

    public void repaint() {
        canvas.repaint();
    }

    public void setSelected(int x, int y) {
        Enumeration<MyDrawing> e = drawingsElements();

        MyDrawing select = NULL_DRAWING;
        while (e.hasMoreElements()) {
            MyDrawing d = e.nextElement();
            if (d.contains(x,y)) select = d;
        }
        setSelectedDrawing(select);


        removeDrawing(select);
        addDrawing(select);

        repaint();
    }

    void clearBuffer() {
        buffer = NULL_DRAWING;
    }

    public boolean copy() {
        clearBuffer();
        try {
            buffer = selectedDrawing.clone();
            return true;
        } catch (CloneNotSupportedException e) { return false; }
    }

    public boolean cut() {
        boolean b = copy();
        removeDrawing(selectedDrawing);
        return b;
    }

    public boolean paste(int x, int y) {
        try {
            buffer.move(20, 20);
            MyDrawing clone = buffer.clone();
            addDrawing(clone);
            repaint();
            return true;
        } catch (CloneNotSupportedException e) { return false; }
    }


    // n重線
    public void setLineNumber(int lineNumber) {
        selectedDrawing.setLineNumber(lineNumber);
        repaint();
    }
    // 線の太さ
    public void setLineWidth(int lineWidth) {
        selectedDrawing.setLineWidth(lineWidth);
        repaint();
    }
    // 影の有無
    public void setShade(boolean hasShade) {
        selectedDrawing.setShade(hasShade);
        repaint();
    }
    // 破線パターン
    public void setDashArray(float[] dashArray) {
        selectedDrawing.setDashArray(dashArray);
        repaint();
    }
    // 線の色の変更
    public void setLineColor(Color lineColor) {
        selectedDrawing.setLineColor(lineColor);
        repaint();
    }
    // 塗りの色の変更
    public void setFillColor(Color fillColor) {
        selectedDrawing.setFillColor(fillColor);
        repaint();
    }
}
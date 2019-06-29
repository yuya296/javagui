import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.*;
import java.util.List;
import java.util.ArrayList;

public class Mediator {
    private MyCanvas canvas;    // キャンバス
    private List<MyDrawing> drawings;  // 描画リスト
    private List<MyDrawing> selectedDrawings;  // 選択中のオブジェクト
    private List<MyDrawing> buffers;   // クリップボード

    public Mediator(MyCanvas canvas) {
        this.canvas = canvas;
        drawings = new ArrayList<>();
        selectedDrawings = new ArrayList<>();
        buffers = new ArrayList<>();
    }

    public List<MyDrawing> getDrawings() {
        return drawings;
    }

    /* 新たな図形を描画 */
    public void addDrawing(MyDrawing d) {
        drawings.add(d);    // 描画リストに追加
        select(d);          // 選択状態にする
        repaint();          // 再描画
    }

    /* 描画リストから削除 */
    void removeDrawing(MyDrawing d) {
        drawings.remove(d);
        repaint();
    }
    void removeDrawings(List<MyDrawing> ds) {
        drawings.removeAll(ds);
        repaint();
    }
    void removeSelectedDrawing() {
        removeDrawings(selectedDrawings);
    }


    /* 選択 */
    void select(MyDrawing drawing) {
        selectedDrawings.add(drawing);
        drawing.setSelected(true);
        repaint();
    }
    /* 全て選択 */
    void selectAll() {
        unSelectAll();
        System.out.println("select all");
        for (MyDrawing d : drawings) select(d);
        repaint();
    }

    /* 選択解除 */
    void unSelect(MyDrawing drawing) {
        drawing.setSelected(false);
        selectedDrawings.remove(drawing);
        repaint();
    }
    /* 全て選択解除 */
    void unSelectAll() {
        for (MyDrawing d : selectedDrawings) d.setSelected(false);
        selectedDrawings.clear();
        repaint();
    }

    /* (x, y) にある MyDrawing オブジェクトを選択状態にする */
    boolean setSelected(int x, int y) {
        MyDrawing d = getDrawingAt(x, y);
        if (d == null) return false;   // オブジェクトが無いとき
        select(d);
        repaint();
        return true;
    }

    /* (x, y) にある MyDrawing オブジェクトを返す */
    MyDrawing getDrawingAt(int x, int y) {
        MyDrawing select = null;
        for (MyDrawing d : drawings) {
            if (d.contains(x, y)) select = d;
        }
        return select;
    }

    /* バッファを消去 */
    void clearBuffer() {
        buffers.clear();
    }

    /* コピー */
    public boolean copy() {
        clearBuffer();
        try {
            for (MyDrawing d : selectedDrawings) buffers.add(d.clone());
        } catch (CloneNotSupportedException e) { return false; }
        return true;
    }
    /* カット */
    boolean cut() {
        boolean b = copy();
        removeDrawings(selectedDrawings);
        return b;
    }
    /* ペースト */
    boolean paste() {
        unSelectAll();
        try {
            for (MyDrawing d : buffers) {
                d.move(20, 20); // 移動
                addDrawing(d.clone());   // 複製して描画リストに追加
            }
        } catch (CloneNotSupportedException e) { return false; }
        repaint();
        return true;
    }


    /* 選択範囲に含まれている図形の選択状態を切り替える */
    public void setIntersects(Rectangle2D r) {
        for (MyDrawing d : drawings) {
            if (d.region.intersects(r)) {
                switchSelected(d);
            }
        }
    }

    /* 選択状態を切り替える */
    public void switchSelected(MyDrawing d) {
        if (d.isSelected()) unSelect(d);
        else select(d);
    }


    /* 再描画 */
    public void repaint() {
        canvas.repaint();
    }

    /* 移動 */
    public void move(int dx, int dy) {
        for (MyDrawing drawing : selectedDrawings) drawing.move(dx, dy);
        repaint();
    }
    public void move(int dx, int dy, MyDrawing d) {
        d.move(dx, dy);
        repaint();
    }

    /* n重線 */
    public void setLineNumber(int lineNumber) {
        for (MyDrawing d : selectedDrawings) d.setLineNumber(lineNumber);
        repaint();
    }

    // 線の太さ
    public void setLineWidth(int lineWidth) {
        for (MyDrawing d : selectedDrawings) d.setLineWidth(lineWidth);
        repaint();
    }

    // 影の有無
    public void setShade(boolean hasShade) {
        for (MyDrawing d : selectedDrawings) d.setShade(hasShade);
        repaint();
    }

    // 破線パターン
    public void setDashArray(float[] dashArray) {
        for (MyDrawing d : selectedDrawings) d.setDashArray(dashArray);
        repaint();
    }

    // 線の色の変更
    public void setLineColor(Color lineColor) {
        for (MyDrawing d : selectedDrawings) d.setLineColor(lineColor);
        repaint();
    }

    // 塗りの色の変更
    public void setFillColor(Color fillColor) {
        for (MyDrawing d : selectedDrawings) d.setFillColor(fillColor);
        repaint();
    }


    // 開く
    public void open() {
        JFileChooser fc = new JFileChooser(".");
        int returnVal = fc.showOpenDialog(canvas);
        if (returnVal != JFileChooser.APPROVE_OPTION) return;   // キャンセル

        File file = fc.getSelectedFile();
        try {
            FileInputStream fin = new FileInputStream(file);
            ObjectInputStream in = new ObjectInputStream(fin);

            drawings = (ArrayList<MyDrawing>) in.readObject();
            fin.close();
        } catch (Exception e) {
            System.out.println("Failed to open.");
        }

        repaint();
    }

    // 保存
    public void save(String path) {
        try {
            FileOutputStream fout = new FileOutputStream(path);
            ObjectOutputStream out = new ObjectOutputStream(fout);

            out.writeObject(drawings);
            out.flush();
            fout.close();

        } catch (Exception e) {
            System.out.println("Failed to open \"" + path + "\"");
        }
    }

    public File save(File input) {
        File file;
        if ((file = input) == null){
            JFileChooser fc = new JFileChooser(".");
            int returnVal = fc.showSaveDialog(canvas);
            if (returnVal != JFileChooser.APPROVE_OPTION) return null;   // キャンセル

            file = fc.getSelectedFile();
        }

        try {
            FileOutputStream fout = new FileOutputStream(file);
            ObjectOutputStream out = new ObjectOutputStream(fout);

            out.writeObject(drawings);
            out.flush();
            fout.close();
        } catch (Exception e) {
            System.out.println("Failed to save.");
        }
        return file;
    }
}
// Task1

import javax.swing.*;
import java.awt.*;

public class SimpleDrawings extends JFrame {

    public SimpleDrawings() {

        /* JFrame(String title)
         * 指定したタイトルで新しいFrameを構築 */
        super("My Painter");


        /* パネルを宣言する。
         * レイアウトマネージャは BorderLayoutを選択 */
        JPanel jp = new JPanel(new BorderLayout());
        /* getContentPane() で ContentPane を取得。
         * コンポーネント "jp" を ContentPane に追加する */
        getContentPane().add(jp);


        /* MyCanvasTest を宣言 */
        MyCanvasTest canvas = new MyCanvasTest();
        /* public void add(Component comp, Object constraints)
         * コンポーネントcompを、このコンテナの最後に追加。
         * レイアウトマネージャ constraints に、コンポーネントの追加を通知する。*/
        jp.add(canvas, BorderLayout.CENTER);


        /* public void setSize(Dimension d)
         * Dimension を噛ませなくても設定可 */
        setSize(new Dimension(360, 280));
        /* JFrameは初期状態で不可視なので、可視にする */
        setVisible(true);

    }

    public static void main(String[] argv) {
        new SimpleDrawings();
    }
}

class MyCanvasTest extends JPanel {
    MyCanvasTest() {
        setBackground(Color.white);
    }

    public void paint(Graphics g) {
        super.paint(g);

        // 以下、処理を書く
        g.setColor(Color.black);
        g.drawLine(20,20,100,80);
        g.setColor(Color.green);
        g.drawRect(20,100,80,60);
    }
}

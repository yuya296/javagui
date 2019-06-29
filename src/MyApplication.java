// task2-8

import com.sun.javafx.PlatformUtil;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeListener;

public class MyApplication extends JFrame {
    private StateManager stateManager;
    private MyCanvas canvas;
    private Mediator mediator;

    public MyApplication() {
        super("My Paint Application");

        canvas = new MyCanvas();
        canvas.setBackground(Color.white);
        mediator = canvas.getMediator();
        stateManager = new StateManager(mediator);

        setJMenuBar(new MyMenuBar());

        JPanel root = new JPanel();
        root.setLayout(new BoxLayout(root, BoxLayout.PAGE_AXIS));

        JPanel jp2 = new JPanel();
        jp2.setLayout(new FlowLayout());
        JPanel jp3 = new JPanel();
        jp3.setLayout(new FlowLayout());

        root.add(jp2);
        root.add(jp3);

        DrawToolsPanel drawToolsPanel = new DrawToolsPanel(stateManager);

        // 影を表示する
        JCheckBox shadeCheck = new JCheckBox("shade");
        shadeCheck.addItemListener((ItemEvent e) -> stateManager.setShade(e.getStateChange() == ItemEvent.SELECTED));
        jp2.add(shadeCheck);


        // 線の種類を変える
        final float[][] DASH_ARRAY = {
                null,
                {5, 3},
                {5, 3, 1, 3},
                {5, 3, 1, 3, 1, 3}
        };
        final String[] DASH_PATTERN = {
                "━━━━━━━━",
                "━　━　━　━",
                "━　•　━　•　━",
                "━　•　•　━　•"
        };
        JComboBox lineType = new JComboBox(DASH_PATTERN);
        lineType.addItemListener((ItemEvent e) -> stateManager.setDashArray(DASH_ARRAY[lineType.getSelectedIndex()]));
        jp2.add(lineType);
        jp2.add(new JSeparator(JSeparator.VERTICAL));


        // 線の太さを変える
        JSpinner lineWidth = new JSpinner(new SpinnerNumberModel(1, 0, null, 1));
        lineWidth.setPreferredSize(new Dimension(40, 20));
        lineWidth.addChangeListener((ChangeEvent e) -> stateManager.setLineWidth((int)lineWidth.getValue()));
        jp2.add(lineWidth);
        jp2.add(new JLabel("pt"));
        jp2.add(new JSeparator(JSeparator.VERTICAL));


        // n重線にする
        JSpinner lineNumber = new JSpinner(new SpinnerNumberModel(1, 1, null, 1));
        lineNumber.setPreferredSize(new Dimension(40, 20));
        lineNumber.addChangeListener((ChangeEvent e) -> stateManager.setLineNumber((int)lineNumber.getValue()));
        jp2.add(lineNumber);
        jp2.add(new JLabel("lines"));


        // 色の追加
        ColorPanel colorPanel = new ColorPanel(stateManager, mediator);
        colorPanel.setLayout(new FlowLayout());


        jp3.add(colorPanel);


        // Layout
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(drawToolsPanel, BorderLayout.WEST);
        getContentPane().add(root, BorderLayout.NORTH);
        getContentPane().add(canvas, BorderLayout.CENTER);

        // カーソルの位置を追跡
        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                stateManager.mouseDown(e.getX(), e.getY());
                canvas.requestFocusInWindow();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                stateManager.mouseUp(e.getX(), e.getY());
                canvas.requestFocusInWindow();
            }
        });

        // カーソルの動きを追跡
        canvas.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                stateManager.mouseDrag(e.getX(), e.getY());
            }
        });

        // KeyListener の追加
        MyKeyListener keyListener = new MyKeyListener();
        this.addKeyListener(keyListener);
        jp2.addKeyListener(keyListener);
        jp3.addKeyListener(keyListener);
        root.addKeyListener(keyListener);
        canvas.addKeyListener(keyListener);
        drawToolsPanel.addKeyListener(keyListener);
        canvas.setFocusable(true);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exit(0);
            }
        });
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(800, 600);
    }

    public void exit(int status) {
        System.exit(status);
    }

    /* キーイベント */
    class MyKeyListener implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {}

        @Override
        public void keyPressed(KeyEvent e) {
            System.out.println("key pushed:\t" + e.getKeyCode());
            switch (e.getKeyCode()) {
                case 8: // Delete key
                    mediator.removeSelectedDrawing();
                    break;

                case 16:    // Shift key
                    stateManager.setShiftKeyPressed(true);
                    break;

                case 86:    // 'v' key
                    stateManager.setState(new SelectState(stateManager));

                    break;

                case 77:    // 'm' key
                    stateManager.setState(new RectState(stateManager));
                    break;

                case 76:    // 'l' key
                    stateManager.setState(new OvalState(stateManager));
                    break;

                case 80:    // 'p' key
                    stateManager.setState(new PolygonalState(stateManager));
                    break;

                case 87:    // 'w'
                case 81:    // 'q'
                    exit(0);
                    break;

                case 65:    // 'a'
                    if (e.isMetaDown()) {
                        stateManager.selectAll();
                        System.out.println("key pushed\t: Command + A");
                    }
                    break;

                case 83:    // 's'
                    if (e.isMetaDown()) {
                        stateManager.save();
                        System.out.println("** saved **");
                    }
                    break;

                case 79:
                    if (e.isMetaDown()) {
                        mediator.open();
                    }
                    break;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            switch (e.getKeyCode()) {
                case 16:
                    stateManager.setShiftKeyPressed(false);
                    break;
            }
        }
    }

    class MyMenuBar extends JMenuBar {
        public MyMenuBar() {
            super();

            JMenu file = new JMenu("File");

            JMenuItem save = new JMenuItem("Save");
            save.setMnemonic('S');
            save.addActionListener((ActionEvent e) -> stateManager.save());

            JMenuItem open = new JMenuItem("Open");
            save.setMnemonic('O');
            save.addActionListener((ActionEvent e) -> stateManager.open());

            file.add(save);
            file.add(open);


            this.add(file);

        }
    }

    public static void main(String[] args) {
        // MacOSXでのJava実行環境用のシステムプロパティの設定.
        if (PlatformUtil.isMac()) {
            // JFrameにメニューをつけるのではなく、一般的なOSXアプリ同様に画面上端のスクリーンメニューにする.
            System.setProperty("apple.laf.useScreenMenuBar", "true");

            // スクリーンメニュー左端に表記されるアプリケーション名を設定する
            System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Jaaaava");

            // https://www.saka-en.com/java/java-get-os/
            // https://seraphy.hatenablog.com/entry/20100622/p1
        }

        MyApplication app = new MyApplication();
        app.pack();
        app.setVisible(true);
    }
}

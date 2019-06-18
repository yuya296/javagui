// task2-8

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;

public class MyApplication extends JFrame {
    private StateManager stateManager;
    private MyCanvas canvas;

    Mediator mediator;

    public MyApplication() {
        super("My Paint Application");

        canvas = new MyCanvas();
        canvas.setBackground(Color.white);
        mediator = canvas.mediator;

        JPanel root = new JPanel();
        root.setLayout(new BoxLayout(root, BoxLayout.PAGE_AXIS));

        JPanel jp1 = new JPanel();
        jp1.setLayout(new FlowLayout());
        JPanel jp2 = new JPanel();
        jp2.setLayout(new FlowLayout());
        JPanel jp3 = new JPanel();
        jp3.setLayout(new FlowLayout());

        root.add(jp1);
        root.add(jp2);
        root.add(jp3);

        stateManager = new StateManager(canvas);

        jp1.add(new SelectButton(stateManager));
        jp1.add(new RectButton(stateManager));
        jp1.add(new OvalButton(stateManager));
        jp1.add(new PolygonalButton(stateManager));
        jp1.add(new StarButton(stateManager));

        // 影を表示する
        JCheckBox shadeCheck = new JCheckBox("shade");
        shadeCheck.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                stateManager.setShade(e.getStateChange() == ItemEvent.SELECTED);
            }
        });
        jp1.add(shadeCheck);


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
        lineType.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                stateManager.setDashArray(DASH_ARRAY[lineType.getSelectedIndex()]);
            }
        });
        jp2.add(lineType);
        jp2.add(new JSeparator(JSeparator.VERTICAL));


        // 線の太さを変える
        JSpinner lineWidth = new JSpinner(new SpinnerNumberModel(1, 0, null, 1));
        lineWidth.setPreferredSize(new Dimension(40, 20));
        lineWidth.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                stateManager.setLineWidth((int)lineWidth.getValue());
            }
        });
        jp2.add(lineWidth);
        jp2.add(new JLabel("pt"));
        jp2.add(new JSeparator(JSeparator.VERTICAL));


        // n重線にする
        JSpinner lineNumber = new JSpinner(new SpinnerNumberModel(1, 1, null, 1));
        lineNumber.setPreferredSize(new Dimension(40, 20));
        lineNumber.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                stateManager.setLineNumber((int)lineNumber.getValue());
            }
        });
        jp2.add(lineNumber);
        jp2.add(new JLabel("lines"));


        // 色の選択
//        String[] colorsName = {"Black", "Red", "Blue", "Green", "White", "More..."};
//        Color[] colors = {Color.black, Color.red, Color.blue, Color.green, Color.white, null};
//
//        JComboBox lineColor = new JComboBox(colorsName);
//        lineColor.addItemListener(new ItemListener() {
//            @Override
//            public void itemStateChanged(ItemEvent e) {
//                Color c = colors[lineColor.getSelectedIndex()];
//                if (c == null) c = JColorChooser.showDialog(null, "JColorChooser", Color.BLACK);
//                stateManager.setLineColor(c);
//            }
//        });
//        jp3.add(lineColor);
//
//        JComboBox fillColor = new JComboBox(colorsName);
//        fillColor.addItemListener(new ItemListener() {
//            @Override
//            public void itemStateChanged(ItemEvent e) {
//                Color c = colors[fillColor.getSelectedIndex()];
//                if (c == null) c = JColorChooser.showDialog(null, "JColorChooser", Color.WHITE);
//                stateManager.setFillColor(c);
//            }
//
//            private Color moreColor() {
//                Color c = JColorChooser.showDialog(null, "Color Chooser", Color.WHITE);
//
//            }
//        });
//        jp3.add(fillColor);
        ColorPanel colorPanel = new ColorPanel(stateManager, mediator);
        colorPanel.setLayout(new FlowLayout());


        jp3.add(new CutButton(mediator));
        jp3.add(new CopyButton(mediator));
        jp3.add(new PasteButton(mediator));

        jp3.add(colorPanel);



        // Layout
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(root, BorderLayout.NORTH);
        getContentPane().add(canvas, BorderLayout.CENTER);

        // カーソルの位置を追跡
        canvas.addMouseListener(new MouseAdapter() {    // 無名内部クラス new Xxx() {}
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
        canvas.addKeyListener(canvas);
        canvas.setFocusable(true);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(800, 600);
    }

    public static void main(String[] args) {
        MyApplication app = new MyApplication();
        app.pack();
        app.setVisible(true);
    }
}

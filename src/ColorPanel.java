import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ColorPanel extends JPanel {
    StateManager stateManager;
    Mediator mediator;
    DefaultComboBoxModel<MyColor> lineColorModel;       // 表示するカラーセット
    DefaultComboBoxModel<MyColor> fillColorModel;

    JComboBox lineColor, fillColor;

    public ColorPanel(StateManager stateManager, Mediator mediator) {
        this.stateManager = stateManager;
        this.mediator = mediator;

        // デフォルトのカラーセット
        MyColor[] initArray = {
                new MyColor("Black", Color.black, this),
                new MyColor("White", Color.white, this),
                new MyColor("Red", Color.red, this),
                new MyColor("Green", Color.green, this),
                new MyColor("Blue", Color.blue, this),
                new MoreColor(this)
        };

        // カラーセットからモデルを作成
        this.lineColorModel = new DefaultComboBoxModel<>(initArray);
        this.fillColorModel = new DefaultComboBoxModel<>(initArray);

        // モデルから JComboBox を初期化する
        lineColor = new JComboBox(lineColorModel);
        fillColor = new JComboBox(fillColorModel);
        // 表示規則を設定
        lineColor.setRenderer(new MyCellRenderer());
        fillColor.setRenderer(new MyCellRenderer());
        // 初期値を設定
        lineColor.setSelectedIndex(0);
        fillColor.setSelectedIndex(1);
        // パネルに追加
        this.add(lineColor);
        this.add(fillColor);

        // リスナーを追加
        lineColor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color c;
                if ((c = lineColorModel.getElementAt(lineColor.getSelectedIndex()).getValidColor()) != null) {
                    stateManager.setLineColor(c);
                } else {
                    lineColor.setSelectedIndex(0);
                }
            }
        });
        fillColor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color c;
                if ((c = fillColorModel.getElementAt(fillColor.getSelectedIndex()).getValidColor()) != null) {
                    stateManager.setFillColor(c);
                } else {
                    fillColor.setSelectedIndex(1);
                }
            }
        });
    }

    void addList(MyColor color) {
        // 状態を記憶
        int lastLineColor = lineColor.getSelectedIndex();
        int lastFillColor = fillColor.getSelectedIndex();

        // データを追加
        lineColorModel.insertElementAt(color,lineColorModel.getSize()-1);
        fillColorModel.insertElementAt(color,fillColorModel.getSize()-1);

        // 状態の復元
        lineColor.setSelectedIndex(lastLineColor);
        fillColor.setSelectedIndex(lastFillColor);
    }

    // リスト表示
    class MyCellRenderer implements ListCellRenderer {
        JLabel label;

        public MyCellRenderer() {
            label = new JLabel();
            label.setOpaque(true);
        }


        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus){


            if (isSelected){
                label.setText(value.toString());

                Color color = ((MyColor) value).getColor();

                if (color != null) {
                    label.setBackground(color);

                    label.setForeground(chooseTextColor(color.getRed(), color.getGreen(), color.getBlue()));
                }

            }else{
                label.setText(value.toString());

                label.setBackground(Color.lightGray);
                label.setForeground(Color.black);
            }

            return label;
        }

        // コントラストから適切な文字色を選択する
        Color chooseTextColor(int red, int green, int blue) {
            double R = toRgbItem(red);
            double G = toRgbItem(green);
            double B = toRgbItem(blue);

            int Lw = 1;
            int Lb = 0;
            double Lbg = 0.2126 * R + 0.7152 * G + 0.0722 * B;
            double cw = (Lw + 0.05)     /   (Lbg + 0.05);
            double cb = (Lbg + 0.05)    /   (Lb + 0.05);
            return cw < cb ? Color.black : Color.white;
        }

        double toRgbItem(int item) {
            double i = item / 255;
            return i < 0.03928 ? i / 12.92 : Math.pow((i + 0.055) / 1.055, 2.4);
        }

    }
}

class MyColor {
    protected String name;
    protected Color color;
    protected ColorPanel colorPanel;

    public MyColor(String name, Color color, ColorPanel colorPanel) {
        this.name = name;
        this.color = color;
        this.colorPanel = colorPanel;
    }

    public Color getValidColor() {
        return color;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public String toString() {
        return name;
    }

    public boolean equals(MyColor obj) {
        return this.name == obj.name;
    }
}

class MoreColor extends MyColor {

    public MoreColor(ColorPanel colorPanel) {
        super("More...", null, colorPanel);
    }

    @Override
    public Color getValidColor() {
        Color color = JColorChooser.showDialog(null, "Color Chooser", Color.white);
        if (color == null) return null;

        // 表示名 カラーコードで表す.
        String colorName = "#" +
                Integer.toHexString(color.getRed()) +
                Integer.toHexString(color.getGreen()) +
                Integer.toHexString(color.getBlue());

        colorPanel.addList(new MyColor(colorName, color, colorPanel));
        return color;
    }
}
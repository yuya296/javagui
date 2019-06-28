//import java.awt.*;
//
//class MyStroke {
//
//    public static final int SOLID_LINE = 0;
//    public static final int DASHED_LINE = 1;
//    public static final int ONE_DOT_CHAIN_LINE = 2;
//    public static final int TWO_DOT_CHAIN_LINE = 3;
//    public static final int DOUBLE_LINE = 4;
//    public static final int TRIPLE_LINE = 5;
//    public static final String[] LINE_LIST = {
//            "━━━━━━━━",
//            "━　━　━　━",
//            "━　•　━　•　━",
//            "━　•　•　━　•",
//            "=============",
//            "≡≡≡≡≡≡≡≡≡≡≡≡≡"
//    };
//
//    // Constructor : 呼び出さない
//    private MyStroke() {}
//
//    // Stroke を返す
//    public static Stroke getStroke(int strokeNum, int lineWidth) {
//        switch (strokeNum) {
//            case SOLID_LINE:
//                return new BasicStroke(lineWidth);
//
//            case DASHED_LINE:
//                return new MyDashStroke(lineWidth);
//
//            case ONE_DOT_CHAIN_LINE:
//                return new MyOneDotChainStroke(lineWidth);
//
//            case TWO_DOT_CHAIN_LINE:
//                return new MyTwoDotChainStroke(lineWidth);
//
//            case DOUBLE_LINE:
//                return new MyDoubleStroke(lineWidth);
//
//            case TRIPLE_LINE:
//                return new MyTripleStroke(lineWidth);
//        }
//        return null;
//    }
//    public static Stroke getStroke(int lineWidth) {
//        return getStroke(SOLID_LINE, lineWidth);
//    }
//
//}
//
//// 破線
//class MyDashStroke extends BasicStroke {
//    private static float pattern[] = {10, 15};
//
//    public MyDashStroke(float lineWidth) {
//        super(lineWidth, CAP_BUTT, JOIN_BEVEL, 1.0f, pattern, 0);
//    }
//}
//
//// 一点鎖線
//class MyOneDotChainStroke extends BasicStroke {
//    private static float pattern[] = {10, 10, 1, 10};
//
//    public MyOneDotChainStroke(float lineWidth) {
//        super(lineWidth, CAP_BUTT, JOIN_BEVEL, 1.0f, pattern, 0);
//    }
//}
//
//// 二点鎖線
//class MyTwoDotChainStroke extends BasicStroke {
//    private static float pattern[] = {10, 10, 1, 10, 1, 10};
//
//    public MyTwoDotChainStroke(float lineWidth) {
//        super(lineWidth, CAP_BUTT, JOIN_BEVEL, 1.0f, pattern, 0);
//    }
//}
//
//// 二重線
//class MyDoubleStroke implements Stroke {
//    private BasicStroke stroke1, stroke2;
//
//    public MyDoubleStroke(float w) {
//        stroke1 = new BasicStroke(w*5);
//        stroke2 = new BasicStroke(w);
//    }
//
//    public MyDoubleStroke(int w) {
//        this((float)w);
//    }
//
//    @Override
//    public Shape createStrokedShape(Shape s) {
//        Shape outline = stroke1.createStrokedShape(s);
//        return stroke2.createStrokedShape(outline);
//    }
//}
//
//// 三重線
//class MyTripleStroke implements Stroke {
//    private BasicStroke stroke1, stroke2, stroke3;
//
//    public MyTripleStroke(float w) {
//        this.stroke1 = new BasicStroke(w*5, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL);
//        this.stroke2 = new BasicStroke(w*3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL);
//        this.stroke3 = new BasicStroke(w, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL);
//    }
//
//    private BasicStroke getStroke(float w) {
//        return new BasicStroke(w, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL);
//    }
//
//    @Override
//    public Shape createStrokedShape(Shape s) {
//        Shape outline1 = stroke1.createStrokedShape(s);
//        Shape outline2 = stroke2.createStrokedShape(outline1);
//        return stroke3.createStrokedShape(outline2);
//    }
//}

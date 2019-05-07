import java.awt.*;

public class test {
    public static void main(String[] args) {
        BasicStroke bs = new BasicStroke(2);

        float[] arr = bs.getDashArray();
        System.out.println(arr);
    }
}

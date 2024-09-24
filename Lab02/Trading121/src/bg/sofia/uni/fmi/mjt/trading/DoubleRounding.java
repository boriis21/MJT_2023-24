package bg.sofia.uni.fmi.mjt.trading;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class DoubleRounding {
    private DoubleRounding() {}

    public static double round(double number) {
        BigDecimal rounded = new BigDecimal(Double.toString(number)).setScale(2, RoundingMode.HALF_UP);
        return rounded.doubleValue();
    }
}

package sg.nus.edu.iss.vttp_5a_day25_workshop.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class DecimalRounder {
    
    public static float roundTo2DecimalPlaces(float number){
        BigDecimal roundedNumber = BigDecimal.valueOf(number).setScale(2, RoundingMode.HALF_UP);
        return roundedNumber.floatValue();
    }
}

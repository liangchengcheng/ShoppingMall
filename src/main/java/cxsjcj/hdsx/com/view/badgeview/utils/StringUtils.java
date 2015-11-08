package cxsjcj.hdsx.com.view.badgeview.utils;


import cxsjcj.hdsx.com.view.badgeview.values.IValue;
import cxsjcj.hdsx.com.view.badgeview.values.TextValue;

public class StringUtils {

    public static boolean isNumber(IValue value) {
        if (value instanceof TextValue) {
            CharSequence str = ((TextValue) value).getValue();
            return isNumber(str);
        } else {
            return false;
        }
    }

    public static boolean isNumber(CharSequence str) {
        try {
            Double.parseDouble(str.toString());
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}

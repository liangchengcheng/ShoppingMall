package cxsjcj.hdsx.com.activity.shioce;
import net.sourceforge.pinyin4j.PinyinHelper;


public class LetterUtil
{
    public static String[] getFirstPinyin(char chinese)
    {
        return PinyinHelper.toHanyuPinyinStringArray(chinese);
    }

    public static boolean isLetter(char c)
    {
        return (c >= 65 && c <= 90) || (c >= 97 && c <= 112);
    }
}

package cxsjcj.hdsx.com.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Author:  梁铖城
 * Email:   1038127753@qq.com
 * Date:   2015年10月16日10:41:34
 * Description:  MD5加密算法
 */

public class Md5Utils {
	public static String encode(String text) {
		try {
			MessageDigest digest = MessageDigest.getInstance("md5");
			byte[] result = digest.digest(text.getBytes());
			StringBuilder sb = new StringBuilder();
			for (byte b : result) {
				int number = b & 0xff;
				String hex = Integer.toHexString(number);
				if (hex.length() == 1) {
					sb.append("0" + hex);
				} else {
					sb.append(hex);
				}
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return "";
		}
	}

}

package cxsjcj.hdsx.com.bean;

import java.io.Serializable;

/**
 * Author:  梁铖城
 * Email:   1038127753@qq.com
 * Date:    2015年10月31日20:13:41
 * Description: 选择位置的时候的javabean类
 */
public class City implements Serializable {
	public String name;
	public String pinyi;

	public City(String name, String pinyi) {
		super();
		this.name = name;
		this.pinyi = pinyi;
	}

	public City() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPinyi() {
		return pinyi;
	}

	public void setPinyi(String pinyi) {
		this.pinyi = pinyi;
	}

}

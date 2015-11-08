package cxsjcj.hdsx.com.bean;

/**
 * @author: 梁铖城
 * @date: 2015年8月28日22:04:46
 * @description:  省市县的三级联动
 * @blog: http:www.17yxb.cn
 */
public class CountryModel {
    public String county;

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    @Override
    public String toString() {
        return "CountyModel [county=" + county + "]";
    }
}

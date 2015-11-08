package cxsjcj.hdsx.com.bean;

import java.util.List;

/**
 * @author: 梁铖城
 * @date: 2015年8月28日22:04:02
 * @description: 省市县的三级联动
 * @blog: http:www.17yxb.cn
 */
public class ProvinceModel {
    public String province;
    List<CityModel> city_list;
    public String getProvince() {
        return province;
    }
    public void setProvince(String province) {
        this.province = province;
    }
    public List<CityModel> getCity_list() {
        return city_list;
    }
    public void setCity_list(List<CityModel> city_list) {
        this.city_list = city_list;
    }
    @Override
    public String toString() {
        return "ProvinceModel [province=" + province + ", city_list="
                + city_list + "]";
    }



}

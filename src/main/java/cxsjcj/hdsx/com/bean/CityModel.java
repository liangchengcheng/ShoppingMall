package cxsjcj.hdsx.com.bean;

import java.util.List;

/**
 * @author: 梁铖城
 * @date: 2015年8月28日22:04:29
 * @description: 城市的联动
 * @blog: http:www.17yxb.cn
 */
public class CityModel {
    public String city;
    List<CountryModel> county_list;

    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public List<CountryModel> getCounty_list() {
        return county_list;
    }
    public void setCounty_list(List<CountryModel> county_list) {
        this.county_list = county_list;
    }


}

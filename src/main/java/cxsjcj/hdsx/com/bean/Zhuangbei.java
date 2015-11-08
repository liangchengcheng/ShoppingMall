package cxsjcj.hdsx.com.bean;

import java.io.Serializable;

/**
 * Created by chengcheng on 2015/8/9.
 */
public class Zhuangbei implements Serializable {

    private String image;                    //图片的地址
    private String jiage;                    //价格
    private String miaoshu;                 //描述
    private String shangpinid;              //商品编号
    private String shijian;                 //时间
    private String title;                   //标题
    private String dealid;                  //处理的id
    private String buyid;                   //购买的id
    private String zhuangtai;              //商品的状态
    private String lianxi;                  //联系方式
    private String qufu;                    //服务器。现在估计用不到
    private String jiaoyifangshi;         //交易方式

    public String getDealid() {
        return dealid;
    }

    public void setDealid(String dealid) {
        this.dealid = dealid;
    }

    public String getBuyid() {
        return buyid;
    }

    public void setBuyid(String buyid) {
        this.buyid = buyid;
    }

    public String getZhuangtai() {
        return zhuangtai;
    }

    public void setZhuangtai(String zhuangtai) {
        this.zhuangtai = zhuangtai;
    }

    public String getLianxi() {
        return lianxi;
    }

    public void setLianxi(String lianxi) {
        this.lianxi = lianxi;
    }

    public String getQufu() {
        return qufu;
    }

    public void setQufu(String qufu) {
        this.qufu = qufu;
    }

    public String getJiaoyifangshi() {
        return jiaoyifangshi;
    }

    public void setJiaoyifangshi(String jiaoyifangshi) {
        this.jiaoyifangshi = jiaoyifangshi;
    }

    public String getImage() {
        return image;
    }

    public String getShijian() {
        return shijian;
    }

    public void setShijian(String shijian) {
        this.shijian = shijian;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getJiage() {
        return jiage;
    }

    public void setJiage(String jiage) {
        this.jiage = jiage;
    }

    public String getMiaoshu() {
        return miaoshu;
    }

    public void setMiaoshu(String miaoshu) {
        this.miaoshu = miaoshu;
    }

    public String getShangpinid() {
        return shangpinid;
    }

    public void setShangpinid(String shangpinid) {
        this.shangpinid = shangpinid;
    }

}


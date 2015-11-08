package cxsjcj.hdsx.com.bean;

import java.io.Serializable;

/**
 * Created by chengcheng on 2015/8/9.
 */
public class Jinbi implements Serializable {
    private String id;
    private String jiaoyifangshi;
    private String lianxi;
    private String mid;
    private String qufu;
    private String shangpinid;
    private String shoujia;
    private String shuliang;
    private String sid;
    private String title;
    private String zhuangtai;

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setZhuangtai(String zhuangtai) {
        this.zhuangtai = zhuangtai;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public void setShuliang(String shuliang) {
        this.shuliang = shuliang;
    }

    public void setShoujia(String shoujia) {
        this.shoujia = shoujia;
    }

    public void setShangpinid(String shangpinid) {
        this.shangpinid = shangpinid;
    }

    public void setQufu(String qufu) {
        this.qufu = qufu;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public void setLianxi(String lianxi) {
        this.lianxi = lianxi;
    }

    public void setJiaoyifangshi(String jiaoyifangshi) {
        this.jiaoyifangshi = jiaoyifangshi;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getSid() {
        return sid;
    }

    public String getShoujia() {
        return shoujia;
    }

    public String getLianxi() {
        return lianxi;
    }

    public String getJiaoyifangshi() {
        return jiaoyifangshi;
    }

    public String getMid() {
        return mid;
    }

    public String getQufu() {
        return qufu;
    }

    public String getShangpinid() {
        return shangpinid;
    }

    public String getShuliang() {
        return shuliang;
    }

    public String getZhuangtai() {
        return zhuangtai;
    }
}

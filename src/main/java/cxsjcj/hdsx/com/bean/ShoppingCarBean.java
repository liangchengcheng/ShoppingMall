package cxsjcj.hdsx.com.bean;

import java.io.Serializable;

/**
 * Created by chengcheng on 2015/5/2.
 */

public class ShoppingCarBean implements Serializable {

   private String GID;//商品id

   private String price;//零售价

   private String cost;//成本

   private String profit;//利润

   private String name;//商品名

   private String description;//商品描述

   private String ImageUrl;//商品图片地址

   private String IsDelete;//商品是否下架

   private String number;//商品数量

    private boolean isCheck;

    public boolean isCheck() {
        return isCheck;
    }

    public void setIsCheck(boolean isCheck) {
        this.isCheck = isCheck;
    }
    public void setGID(String GID) {
        this.GID = GID;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProfit(String profit) {
        this.profit = profit;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public void setIsDelete(String isDelete) {
        IsDelete = isDelete;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getGID() {
        return GID;
    }

    public String getPrice() {
        return price;
    }

    public String getCost() {
        return cost;
    }

    public String getProfit() {
        return profit;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public String getIsDelete() {
        return IsDelete;
    }

    public String getNumber() {
        return number;
    }
}

package cxsjcj.hdsx.com.bean;

import java.io.Serializable;

/**
 * @author: 梁铖城
 * @date: 2015年8月29日18:38:54
 * @description: 收货地址javabean
 * @blog: http:www.17yxb.cn
 */
public class AddressBean implements Serializable {
    private String AID;
    private String UID;
    private String Address;
    private String Addressee;
    private String Phone;
    private String Isdefault;
    public AddressBean() {

    }

    public AddressBean(String AID, String isdefault, String addressee, String address, String UID, String phone) {
        this.AID = AID;
        Isdefault = isdefault;
        Addressee = addressee;
        Address = address;
        this.UID = UID;
        Phone = phone;
    }

    public void setAID(String AID) {
        this.AID = AID;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public void setAddressee(String addressee) {
        Addressee = addressee;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public void setIsdefault(String isdefault) {
        Isdefault = isdefault;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getAID() {
        return AID;
    }

    public String getAddress() {
        return Address;
    }

    public String getUID() {
        return UID;
    }

    public String getAddressee() {
        return Addressee;
    }

    public String getPhone() {
        return Phone;
    }

    public String getIsdefault() {
        return Isdefault;
    }

}


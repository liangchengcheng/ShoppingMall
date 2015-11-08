package cxsjcj.hdsx.com.bean;

import java.io.Serializable;

/**
 * Author:  梁铖城
 * Email:   1038127753@qq.com
 * Date:    2015年11月1日14:57:22
 * Description:  实现瀑布流的类
 */
public class Product implements Serializable{
    private int img;
    private String title;

    public Product(int img, String title) {
        this.img = img;
        this.title = title;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}

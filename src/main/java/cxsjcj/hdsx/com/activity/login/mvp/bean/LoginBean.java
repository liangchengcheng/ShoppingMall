package cxsjcj.hdsx.com.activity.login.mvp.bean;

/**
 * @author: 梁铖城
 * @date: 2015年9月10日13:49:25
 * @description: 登入的数据
 * @blog: http:www.17yxb.cn
 */
public class LoginBean {
    /*用户名*/
    private String name;
     /*密码*/
    private String password;

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

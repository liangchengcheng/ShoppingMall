package cxsjcj.hdsx.com.activity.login.mvp.model;

import cxsjcj.hdsx.com.activity.login.mvp.bean.LoginBean;

/**
 * @author: 梁铖城
 * @date: 2015年9月10日14:06:05
 * @description: model层
 * @blog: http:www.17yxb.cn
 */
public interface ILoginModel {

    void setName(String name);

    void setPassword(String password);

    LoginBean load();


}

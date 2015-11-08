package cxsjcj.hdsx.com.contacts;

/**
 * APP配置表
 */
public class AppContacts {

    /*装备价格的请求地址直接get就行 */
    public static String ZB_PATH = "http://www.17yxb.cn/Zhuangbeihandler.ashx?pd=";

    /*金币价格的请求地址直接get就行 */
    public static String JB_PATH = "http://www.17yxb.cn/GameHandler.ashx?pd=";

    /*数据库的名字*/
    public static final String DB_NAME = "note";

    /*远程服务器上面的收货地址查询(测试用的)*/
    public static final String ADDRESS_MAIN_URL = "http://115.28.2.207:8001/Interface/AddressInfo.ashx?action=get&Calldate=";

    /*远程服务器上的增加收货地址（测试用的）*/
    public static final String ADDRESS_ADD = "http://115.28.2.207:8001/Interface/AddressInfo.ashx?action=add";
}

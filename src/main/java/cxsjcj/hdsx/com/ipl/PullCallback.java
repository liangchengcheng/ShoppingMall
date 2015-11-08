package cxsjcj.hdsx.com.ipl;

/**
 * @author 梁铖城
 * @description 对recyclerview的加载、刷新的接口
 * @data 2015年8月21日10:46:55
 */
public interface PullCallback {
    void onLoadMore();

    void onRefresh();

    boolean isLoading();

    boolean hasLoadedAllItems();
}

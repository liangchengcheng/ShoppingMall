package cxsjcj.hdsx.com.data;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;


import java.util.List;

import cxsjcj.hdsx.com.bean.Club;
import cxsjcj.hdsx.com.bean.Jinbi;
import cxsjcj.hdsx.com.ipl.ResponseCallback;
import cxsjcj.hdsx.com.network.GsonDecode;


/**
 * @author  梁铖城
 * @date 2015年8月22日23:30:40
 * @description 数据控制类：主要负责通过volley请求的数据
 */
public class DataManager {
    private String TAG = getClass().getSimpleName();
    private List mData;
    private List<Jinbi> mDataList;
    public void loadNewData(String URL_HEAD, final ResponseCallback callback) {
        loadData(URL_HEAD, 0, callback);
    }
    public void loadData(String URL_HEAD, final int index, final ResponseCallback callback) {
        GsonDecode<Club> mClubDd = new GsonDecode<Club>();
        mClubDd.getGsonData(URL_HEAD, Club.class, new Response.Listener<Club>() {
                    @Override
                    public void onResponse(Club club) {
                        if (mDataList == null) {
                            mDataList = club.ds;
                        } else {
                            mDataList.addAll(club.ds);
                        }
                        callback.onSuccess(mDataList);
                    }
                }
                , new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        callback.onError("time out");
                    }
                }
        );
    }
    public List<Jinbi> getData() {
        return mDataList;
    }
    public void loadOldData(String URL_HEAD, final ResponseCallback callback) {
        loadData(URL_HEAD, 0, callback);
    }

}

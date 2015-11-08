package cxsjcj.hdsx.com.activity.userinfo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;

import cxsjcj.hdsx.com.myapplication.R;

/**
 * @author: 梁铖城
 * @date: 2015年9月9日08:49:02
 * @description: 联系方式
 * @blog: http:www.17yxb.cn
 */
public class UserInfoConnectionFragment extends Fragment {
    private MapView mapView;
    private AMap aMap;
    public static final LatLng BEIJING = new LatLng(39.90403, 116.407525);// 北京市经纬度
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.user_connection,null);
        mapView= (MapView) view.findViewById(R.id.map);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mapView.onCreate(savedInstanceState);
        if (aMap == null) {
            aMap = mapView.getMap();
            drawMarkers();
        }
    }

    public void drawMarkers() {
        Marker marker = aMap.addMarker(new MarkerOptions()
                .position(BEIJING)
                .title("商家所在的位置")
                .icon(BitmapDescriptorFactory
                        .fromResource(R.drawable.location_n))
                .draggable(true));
        marker.showInfoWindow();// 设置默认显示一个infowinfow
    }

}

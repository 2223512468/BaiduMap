package com.jaja.baidumap;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteLine;
import com.baidu.mapapi.search.route.MassTransitRoutePlanOption;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteLine;
import com.baidu.mapapi.search.route.WalkingRoutePlanOption;
import com.baidu.mapapi.search.route.WalkingRouteResult;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, OnGetRoutePlanResultListener {

    private MapView mMapView;
    private Button btn1, btn2, btn3, btn4, btn5, btn6;
    private BaiduMap baiduMap;
    private RoutePlanSearch mSearch;
    private boolean flag = false;
    private boolean flag2 = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMapView = (MapView) findViewById(R.id.bmapView);
        baiduMap = mMapView.getMap();
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);
        btn5 = (Button) findViewById(R.id.btn5);
        btn6 = (Button) findViewById(R.id.btn6);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        baiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                //构建Marker图标
                BitmapDescriptor bitmap = BitmapDescriptorFactory
                        .fromResource(R.mipmap.ic_launcher);
                //构建MarkerOption，用于在地图上添加Marker
                OverlayOptions option = new MarkerOptions()
                        .position(latLng)
                        .icon(bitmap);
                //在地图上添加Marker，并显示
                baiduMap.addOverlay(option);
            }

            @Override
            public boolean onMapPoiClick(MapPoi mapPoi) {
                return false;
            }
        });

        mSearch = RoutePlanSearch.newInstance();
        mSearch.setOnGetRoutePlanResultListener(this);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
        mSearch.destroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn1:
                baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
                break;
            case R.id.btn2:
                baiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
                break;
            case R.id.btn3:
                baiduMap.setMapType(BaiduMap.MAP_TYPE_NONE);
                break;
            case R.id.btn4:
                //实时路况图
                baiduMap.setTrafficEnabled(flag = !flag);
                break;
            case R.id.btn5:
                //人口密集度，热力图
                baiduMap.setBaiduHeatMapEnabled(flag2 = !flag2);
                break;
            case R.id.btn6:
                //步行路线规划
                PlanNode stNode = PlanNode.withCityNameAndPlaceName("杭州", "云峰家园北");
                PlanNode enNode = PlanNode.withCityNameAndPlaceName("杭州", "江南大道东信大道口");
                mSearch.walkingSearch((new WalkingRoutePlanOption())
                        .from(stNode)
                        .to(enNode));
                mSearch.masstransitSearch(new MassTransitRoutePlanOption().from(stNode).to(enNode));
                break;
        }
    }

    @Override
    public void onGetWalkingRouteResult(WalkingRouteResult result) {
        List<WalkingRouteLine> lines = result.getRouteLines();
        WalkingRouteLine line = lines.get(0);
        Log.i("print", line.getDistance() + "");

    }

    @Override
    public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {

    }

    @Override
    public void onGetMassTransitRouteResult(MassTransitRouteResult result) {
        MassTransitRouteLine line = result.getRouteLines().get(0);

        Log.i("print", line.getPrice() + "-" + line.getArriveTime() + "-" + line.getTitle() + "-" + line.getDistance() + "-" + line.getPriceInfo() + "-" + line.getAllStep());

    }

    @Override
    public void onGetDrivingRouteResult(DrivingRouteResult drivingRouteResult) {

    }

    @Override
    public void onGetIndoorRouteResult(IndoorRouteResult indoorRouteResult) {

    }

    @Override
    public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {

    }

}

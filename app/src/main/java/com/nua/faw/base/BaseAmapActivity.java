package com.nua.faw.base;

import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.TextureMapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.Polyline;
import com.nua.faw.map.Mark;
import com.nua.faw.map.MarkLayer;
import com.nua.faw.utils.SizeUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：yuhb on 2019/6/14 15:41.
 * 描述：
 */
public abstract class BaseAmapActivity<P extends BasePresenter>
        extends BaseActivity<P> implements BaseView, AMap.OnMapLoadedListener,
        AMap.OnMarkerClickListener, AMap.InfoWindowAdapter,
        AMap.OnMapClickListener, AMap.OnMyLocationChangeListener, AMapLocationListener {

    private static final int SUCCESS_CODE = 0;
    protected static final String TAG = "BaseAmapFragment";
    private TextureMapView mapView;
    public final int LOCATION_TIME = 5 * 1000;
    public static int defaultMapZoom = 14;
    boolean onceOnly = false;
    long interval = 60 * 1000;
    long timeOut = 30000;
    protected AMap aMap;
    protected Location mLocation;
    private List<Integer> colores = new ArrayList<>();
    private Marker pathStart, pathEnd;
    private AMapLocationClient mLocationClient;
    Polyline polyline;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mapView = getTextureMapView();


        if (mapView != null) {
            mapView.onCreate(savedInstanceState);
            aMap = mapView.getMap();
            aMap.setOnMapLoadedListener(this::initMapLoad);
        }

        aMap.getUiSettings().setZoomControlsEnabled(false);
        aMap.setOnMapLoadedListener(this);
        aMap.setInfoWindowAdapter(this);
        aMap.setOnMarkerClickListener(this);
        aMap.getUiSettings().setGestureScaleByMapCenter(true);

    }


    protected abstract TextureMapView getTextureMapView();


    protected void initMyLocation() {
        MyLocationStyle myLocationStyle;
        //初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);
        // 连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。
        // （1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle = new MyLocationStyle();
        myLocationStyle.interval(LOCATION_TIME); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_SHOW);
//        myLocationStyle.interval(500);
        if (aMap != null) {
            aMap.setMyLocationStyle(myLocationStyle);
            aMap.setMyLocationEnabled(true);

        }//设置定位蓝点的Style
        //aMap.getUiSettings().setMyLocationButtonEnabled(true);设置默认定位按钮是否显示，非必需设置。
        // 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false

//        mLocationClient = new AMapLocationClient(mContext);
//        AMapLocationClientOption mOption = new AMapLocationClientOption();
//        mOption.setHttpTimeOut(timeOut);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
//        mOption.setOnceLocation(onceOnly);//可选，设置是否单次定位。默认是false
//        if (!onceOnly) {
//            mOption.setInterval(interval);
//        }
//        mLocationClient.setLocationOption(mOption);
//        mLocationClient.setLocationListener(this);
//        startLoc();
        onAfterLocation();
    }

    protected void onAfterLocation() {

    }


    /**
     * 初始化地图一些initMapLoad 在onActivityCreated后调用
     * initViews比initMapLoad先执行
     */
    protected abstract void initMapLoad();


    @Override
    public void onMyLocationChange(Location location) {
        this.mLocation = location;
//        MyApplication.LAT = mLocation.getLatitude();
//        MyApplication.LNG = mLocation.getLongitude();
    }


    /**
     * 方法必须重写
     */
    @Override

    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();

    }


    protected void setMapUiSetting() {
        UiSettings uiSettings = aMap.getUiSettings();//实例化UiSettings类对象
        uiSettings.setZoomControlsEnabled(false);//设置是否允许显示缩放按钮
        uiSettings.setLogoPosition(AMapOptions.LOGO_POSITION_BOTTOM_RIGHT);//设置Logo在底部右下角
        uiSettings.setLogoBottomMargin(SizeUtils.dp2px(getApplicationContext(), 120));//设置Logo距离底部120dp;
        //旋转手势
        uiSettings.setRotateGesturesEnabled(false);
        //倾斜手势
        uiSettings.setTiltGesturesEnabled(false);

    }


    public AMap getaMap() {
        return aMap;
    }


    public CameraUpdate createCameraUpdate(double lat, double lng, float zoom) {
        CameraPosition cp = new CameraPosition.Builder()
                .target(new LatLng(lat, lng)).zoom(zoom).tilt(0).build();
        return CameraUpdateFactory.newCameraPosition(cp);
    }

    protected void changeCamera(double lat, double lng, float zoom) {

        changeCamera(createCameraUpdate(lat, lng, zoom), null, false);
    }

    protected void changeMyCamera(float zoom) {
        if (mLocation != null && mLocation.getLatitude() != 0 && mLocation.getLongitude() != 0) {
            changeCamera(createCameraUpdate(mLocation.getLatitude(), mLocation.getLongitude(), zoom), null, false);
        }
    }

    protected void animateCamera(double lat, double lng, float zoom) {

        changeCamera(createCameraUpdate(lat, lng, zoom), null, true);
    }

    public void changeCamera(CameraUpdate update, AMap.CancelableCallback callback, boolean animate) {
        if (animate) {
            aMap.animateCamera(update, 1500, callback);
        } else {
            aMap.moveCamera(update);
        }
    }

    public void changeCamera(boolean up) {
        if (up) {
            aMap.moveCamera(CameraUpdateFactory.zoomTo(aMap.getCameraPosition().zoom + 1));
        } else {
            aMap.moveCamera(CameraUpdateFactory.zoomTo(aMap.getCameraPosition().zoom - 1));
        }

    }

    protected void printMarkLayer(MarkLayer markLayer, int ori) {
        Marker marker = null;
        Mark mark;
        LatLng latLng;
        Bitmap bmp;
        for (String name : markLayer.getMap().keySet()) {
            mark = markLayer.getMap().get(name);
            latLng = new LatLng(mark.getLat(), mark.getLng());
            if (mark.getIconRecouse() != 0) {
                marker = addMarker(latLng, mark.getTitle(), mark.getIconRecouse(), ori);
            } else if (mark.getBitmap() != null) {
                marker = addMarker(latLng, mark.getTitle(), mark.getBitmap(), ori);
            }

            //marker.setObject(markObject);
            mark.setHolder(marker);
        }
    }


    protected Marker addMarker(LatLng latLng, String title, int res, int angle) {
        MarkerOptions options = new MarkerOptions()
                .position(latLng)
                .title(title)
                .icon(BitmapDescriptorFactory.fromResource(res));
        Marker marker = aMap.addMarker(options);
        marker.setRotateAngle(angle);
        return marker;
    }

    protected Marker addMarker(LatLng latLng, String title, Bitmap res, int angle) {
        MarkerOptions options = new MarkerOptions().position(latLng).title(title).icon(BitmapDescriptorFactory.fromBitmap(res));
        Marker marker = aMap.addMarker(options);
        marker.setRotateAngle(angle);
        return marker;
    }

    protected void clear(MarkLayer markLayer) {
        for (Mark mark : markLayer.getMap().values()) {
            ((Marker) mark.getHolder()).remove();
        }
        markLayer.clear();
    }


    private boolean isFirstLoc = true;

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0
                    && amapLocation.getErrorCode() == SUCCESS_CODE) {
                mLocation = amapLocation;
                if (isFirstLoc) {
                    onLocationSuccess(amapLocation);
                    isFirstLoc = false;
                }
            } else {
                onLocationFail();
            }
        }
    }

    protected void onLocationSuccess(AMapLocation amapLocation) {

    }

    protected void onLocationFail() {

    }


    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    @Override
    public void onMapClick(LatLng latLng) {

    }

    @Override
    public void onMapLoaded() {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }
}

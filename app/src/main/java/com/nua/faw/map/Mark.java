package com.nua.faw.map;

import android.graphics.Bitmap;
import android.graphics.Matrix;

public class Mark {
    // 经纬度
    private double lat;
    private double lng;
    // 图片
    private Bitmap bitmap;
    private Bitmap icon;
    private int direction = 0;
    // 储存接口对象
    private Object holder;
    //储存数据
    private Object data;
    //显示在头上的数据
    private String title;
    private String time;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    private int iconRecouse;

    private String imgUrl;
    private String videoUrl;
    private String type;//类型

    public int getIconRecouse() {
        return iconRecouse;
    }

    private Mark(Bitmap bitmap, double lat, double lng) {
        this.bitmap = bitmap;
        this.icon = bitmap;
        this.lat = lat;
        this.lng = lng;
    }

    private Mark(int iconRecouse, double lat, double lng, String time) {
        this.iconRecouse = iconRecouse;
        this.lat = lat;
        this.lng = lng;
        this.time = time;

    }

    private Mark(int iconRecouse, double lat, double lng, String time, String title) {
        this.iconRecouse = iconRecouse;
        this.lat = lat;
        this.lng = lng;
        this.time = time;
        this.title = title;

    }

    private Mark(String imgurl, double lat, double lng) {
        this.imgUrl = imgurl;
        this.lat = lat;
        this.lng = lng;
    }

    private Mark(String imgurl, double lat, double lng, String videoUrl, String type) {
        this.imgUrl = imgurl;
        this.lat = lat;
        this.lng = lng;
        this.videoUrl = videoUrl;
        this.type = type;
    }

    public static Mark create(Bitmap bitmap, double lat, double lan) {
        return new Mark(bitmap, lat, lan);
    }

    public static Mark create(int iconRecouse, double lat, double lan, String time) {
        return new Mark(iconRecouse, lat, lan, time);
    }

    public static Mark create(String imgUrl, double lat, double lan, String videoUrl, String type) {
        return new Mark(imgUrl, lat, lan, videoUrl, type);
    }

    public static Mark create(int iconRecouse, double lat, double lan, String time, String title) {
        return new Mark(iconRecouse, lat, lan, time);
    }

    public static Mark create(double lat, double lng) {
        Mark mark = new Mark(lat, lng);
        return mark;
    }

    private Mark(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Object getHolder() {
        return holder;
    }

    public void setHolder(Object holder) {
        this.holder = holder;
    }

    public Bitmap getIcon() {

        return icon;
    }

    public int getDirection() {
        return direction;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDirection(int direction) {
        this.direction = direction;

        Matrix matrix = new Matrix();
        matrix.setRotate(direction);
        icon = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                bitmap.getHeight(), matrix, true);
    }

    public void setDirect(int direction) {
        this.direction = direction;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

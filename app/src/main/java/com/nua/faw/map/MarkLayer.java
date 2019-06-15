package com.nua.faw.map;

import com.nua.faw.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MarkLayer {

    private Map<String, Mark> mMarkMap = new HashMap<String, Mark>();
    private List<PathPoint> mPathLayer = new ArrayList<PathPoint>();
    private String startTime, endTime;
    private boolean hiddenStartFlag, hiddenEndFlag;

    private int startBitmap = R.mipmap.icon_mao_point_start;//xcgj_starting1;
    private int endBitmap = R.mipmap.icon_map_point_end;//xcgj_end1;

    public MarkLayer() {
    }

    public boolean isHiddenStartFlag() {
        return hiddenStartFlag;
    }

    public void setHiddenStartFlag(boolean hiddenStartFlag) {
        this.hiddenStartFlag = hiddenStartFlag;
    }

    public boolean isHiddenEndFlag() {
        return hiddenEndFlag;
    }

    public void setHiddenEndFlag(boolean hiddenEndFlag) {
        this.hiddenEndFlag = hiddenEndFlag;
    }

    // name不可为空
    public synchronized void addPoi(String name, Mark mark) {
        if (name == null) {
            throw new IllegalArgumentException("name can't be null");
        }
        mMarkMap.put(name, mark);
    }

    public synchronized Map<String, Mark> getMap() {
        return mMarkMap;
    }

    public synchronized void removePoi(String name) {
        mMarkMap.remove(name);
    }

    public synchronized Mark getPoi(String name) {
        return mMarkMap.get(name);
    }

    public synchronized void addPath(PathPoint point) {
        mPathLayer.add(point);
    }

    public synchronized List<PathPoint> getPathLayer() {
        return mPathLayer;
    }

    public synchronized void clearPath() {
        mPathLayer.clear();
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getStartBitmap() {
        return startBitmap;
    }

    public int getEndBitmap() {
        return endBitmap;
    }

    public void clear() {
        mMarkMap.clear();
        mPathLayer.clear();
    }

    public static class PathPoint {
        private double lat;
        private double lng;
        private double speed;
        private double dir;

        public PathPoint(double lat, double lng, double speed) {
            this.lat = lat;
            this.lng = lng;
            this.speed = speed;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLng() {
            return lng;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }

        public double getSpeed() {
            return speed;
        }

        public void setSpeed(double speed) {
            this.speed = speed;
        }

        public double getDir() {
            return dir;
        }

        public void setDir(double dir) {
            this.dir = dir;
        }
    }
}

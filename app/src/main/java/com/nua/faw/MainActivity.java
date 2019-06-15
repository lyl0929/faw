package com.nua.faw;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import com.amap.api.maps.TextureMapView;
import com.amap.api.maps.model.LatLng;
import com.nua.faw.base.BaseAmapActivity;
import com.nua.faw.base.BasePresenter;
import com.nua.faw.bean.MsgDataBean;
import com.nua.faw.utils.Logger;
import com.nua.faw.utils.RedirectException;
import com.xuhao.didi.core.iocore.interfaces.IPulseSendable;
import com.xuhao.didi.core.iocore.interfaces.ISendable;
import com.xuhao.didi.core.pojo.OriginalData;
import com.xuhao.didi.socket.client.impl.client.action.ActionDispatcher;
import com.xuhao.didi.socket.client.sdk.OkSocket;
import com.xuhao.didi.socket.client.sdk.client.ConnectionInfo;
import com.xuhao.didi.socket.client.sdk.client.OkSocketOptions;
import com.xuhao.didi.socket.client.sdk.client.action.SocketActionAdapter;
import com.xuhao.didi.socket.client.sdk.client.connection.IConnectionManager;
import com.xuhao.didi.socket.client.sdk.client.connection.NoneReconnect;

import java.nio.charset.Charset;

import butterknife.BindView;

public class MainActivity extends BaseAmapActivity {

    @BindView(R.id.textureMapView)
    TextureMapView mMapView;

    private ConnectionInfo mInfo;
    private OkSocketOptions mOkOptions;
    private IConnectionManager mManager;


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected TextureMapView getTextureMapView() {
        return mMapView;
    }

    //缩放的时候调用此方法
    @Override
    protected void initMapLoad() {


    }


    @Override
    protected void initViews(Bundle savedInstanceState) {

        initManager();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                connectSocket();
            }
        }, 1000);
    }


    private void initManager() {
        final Handler handler = new Handler();
        mInfo = new ConnectionInfo("192.168.1.100", Integer.parseInt("6722"));
        mOkOptions = new OkSocketOptions.Builder()
                .setReconnectionManager(new NoneReconnect())
                .setConnectTimeoutSecond(10)
                .setCallbackThreadModeToken(new OkSocketOptions.ThreadModeToken() {
                    @Override
                    public void handleCallbackEvent(ActionDispatcher.ActionRunnable runnable) {
                        handler.post(runnable);
                    }
                })
                .build();
        mManager = OkSocket.open(mInfo).option(mOkOptions);
        mManager.registerReceiver(adapter);
    }

    @Override
    protected boolean isregisterEventBus() {
        return false;
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }


    private SocketActionAdapter adapter = new SocketActionAdapter() {

        @Override
        public void onSocketConnectionSuccess(ConnectionInfo info, String action) {
//            mManager.send(new HandShakeBean());
//            Toast.makeText(_mActivity, "连接成功", Toast.LENGTH_LONG).show();
            //发送指令
            send("aaa");
            Log.e("socket", "连接成功");
        }

        @Override
        public void onSocketDisconnection(ConnectionInfo info, String action, Exception e) {
//            Toast.makeText(_mActivity, "异常断开", Toast.LENGTH_LONG).show();
            if (e != null) {
                if (e instanceof RedirectException) {
                    mManager.switchConnectionInfo(((RedirectException) e).redirectInfo);
                    mManager.connect();
                } else {
                    Logger.e("异常断开(Disconnected with exception):" + e.getMessage());
                }
            } else {
                Logger.e("正常断开(Disconnect Manually)");
            }
        }

        @Override
        public void onSocketConnectionFailed(ConnectionInfo info, String action, Exception e) {
//            Toast.makeText(_mActivity, "连接失败", Toast.LENGTH_LONG).show();
            Log.e("socket", "连接失败");
        }

        @Override
        public void onSocketReadResponse(ConnectionInfo info, String action, OriginalData data) {
            String str = new String(data.getBodyBytes(), Charset.forName("utf-8"));
//            logRece(str);
            Logger.e("onSocketReadResponse =" + str);
        }

        @Override
        public void onSocketWriteResponse(ConnectionInfo info, String action, ISendable data) {
            String str = new String(data.parse(), Charset.forName("utf-8"));
            Logger.e("onSocketWriteResponse =" + str);
//            logSend(str);
        }

        @Override
        public void onPulseSend(ConnectionInfo info, IPulseSendable data) {
            String str = new String(data.parse(), Charset.forName("utf-8"));
//            logSend(str);
        }
    };


    private void send(String msg) {
        if (mManager == null) {
            return;
        }
        if (!mManager.isConnect()) {
//            Toast.makeText(_mActivity, "连接失败", Toast.LENGTH_SHORT).show();
        } else {
            if (TextUtils.isEmpty(msg.trim())) {
                return;
            }
            MsgDataBean msgDataBean = new MsgDataBean(msg);
            mManager.send(msgDataBean);
        }
    }


    private void connectSocket() {

        if (mManager == null) {
            return;
        }
        if (!mManager.isConnect()) {
            initManager();
            mManager.connect();
        } else {
            mManager.disconnect();
        }
    }


    @Override
    public void onMapClick(LatLng latLng) {

    }

    //
    @Override
    public void onMapLoaded() {

    }
}

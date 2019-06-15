package com.nua.faw.base;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.nua.faw.R;
import com.nua.faw.dialog.MProgressDialog;
import com.nua.faw.utils.ActivityStack;
import com.nua.faw.widget.BaseToolbar;
import com.nua.faw.widget.TitleToolbar;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.AutoDisposeConverter;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

//import com.umeng.message.PushAgent;

/**
 * 作者：yuhb on 2018/2/27 09:40.
 * 邮箱：yuhb@njext.com
 * 描述：
 */

public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements BaseView,
        BaseToolbar.OnOptionItemClickListener {
    //    private PushAgent mPushAgent;
    protected P mPresenter;
    private Unbinder butterKnife;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        initSwipeBackFinish();
        super.onCreate(savedInstanceState);
        ActivityStack.getActivityManager().addActivity(this);
        if (getContentViewLayoutID() != 0) {
            setContentView(getContentViewLayoutID());
        } else {
            throw new IllegalArgumentException("You must return a right contentView layout resource Id");
        }
        butterKnife = ButterKnife.bind(this);
        mPresenter = createPresenter();
        if (isregisterEventBus()) {
            EventBus.getDefault().register(this);
        }
        //友盟push
//        mPushAgent = PushAgent.getInstance(this);
//        mPushAgent.onAppStart();
//        setStatusBar();
        initViews(savedInstanceState);
    }


//    @Override
//    public void onBackPressed() {

//        // 正在滑动返回的时候取消返回按钮事件
//        if (mSwipeBackHelper.isSliding()) {
//            return;
//        }
//        mSwipeBackHelper.backward();
//    }

//    protected void setStatusBar() {
//        StatusBarUtil.setColorForSwipeBack(this, getResources().getColor(R.color.colorPrimary), 0);
//        StatusBarUtil.setLightMode(this);
//    }
//
//    protected void setTranslucentStatusBar() {
//        StatusBarUtil.setTransparent(this);
//    }
//
//    protected void setStatusBarLightMode() {
//        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary));
//    }


    /**
     * 绑定样式文件
     *
     * @return id of layout resource
     */
    protected abstract int getContentViewLayoutID();

    /**
     * init all views and add events
     */
    protected abstract void initViews(Bundle savedInstanceState);

    protected abstract boolean isregisterEventBus();

    protected abstract P createPresenter();

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        if (null != mPresenter) {
            mPresenter.onDestory();
        }
        butterKnife.unbind();
        MProgressDialog.dismissProgress();
        ActivityStack.getActivityManager().removeActivity(this);

    }

    /**
     * 初始化 Toolbar
     */
    protected void initToolBar(TitleToolbar toolbar, String title) {
        toolbar.setOnOptionItemClickListener(this);
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onOptionItemClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.close:
//                Toast.makeText(this, "关闭", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    /**
     * 是否显示返回键
     *
     * @return
     */
    protected boolean showHomeAsUp() {
        return false;
    }

    @Override
    public void onError(int type) {

    }

    @Override
    public void toast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading(int type) {

    }

    @Override
    public void dismissLoading(int type) {

    }

    /**
     * Display setting dialog.
     */

    public void showPermissionDialog(Context context, final List<String> permissions) {
        List<String> permissionNames = Permission.transformText(context, permissions);
        String message = context.getString(R.string.message_permission_always_failed,
                TextUtils.join("\n", permissionNames));

        new AlertDialog.Builder(context).setCancelable(false)
                .setTitle(R.string.title_dialog)
                .setMessage(message)
                .setPositiveButton(R.string.setting, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setPermission();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }

    /**
     * Set permissions.
     */
    private void setPermission() {
        AndPermission.with(this).runtime().setting().start(Constant.REQ_CODE_SETTING);
    }

    /**
     * 绑定生命周期
     */
    @Override
    public <X> AutoDisposeConverter<X> bindAutoDispose() {
        return AutoDispose.autoDisposable(AndroidLifecycleScopeProvider
                .from(this, Lifecycle.Event.ON_DESTROY));
    }

}

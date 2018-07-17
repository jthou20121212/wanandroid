package com.jthou.wanandroid.ui.main.fragment;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.widget.AppCompatCheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jthou.wanandroid.R;
import com.jthou.wanandroid.app.Constants;
import com.jthou.wanandroid.base.fragment.ParentFragment;
import com.jthou.wanandroid.contract.main.SettingContract;
import com.jthou.wanandroid.model.entity.NightModeEvent;
import com.jthou.wanandroid.presenter.main.SettingPresenter;
import com.jthou.wanandroid.util.RxBus;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;


public class SettingFragment extends ParentFragment<SettingPresenter> implements CompoundButton.OnCheckedChangeListener, SettingContract.View {

    @BindView(R.id.cb_setting_cache)
    AppCompatCheckBox cbSettingCache;
    @BindView(R.id.cb_setting_image)
    AppCompatCheckBox cbSettingImage;
    @BindView(R.id.cb_setting_night)
    AppCompatCheckBox cbSettingNight;
    @BindView(R.id.ll_setting_feedback)
    LinearLayout llSettingFeedback;
    @BindView(R.id.tv_setting_clear)
    TextView tvSettingClear;
    @BindView(R.id.ll_setting_clear)
    LinearLayout llSettingClear;
    @BindView(R.id.tv_setting_update)
    TextView tvSettingUpdate;
    @BindView(R.id.ll_setting_update)
    LinearLayout llSettingUpdate;

    private File cacheFile;
    private String versionName;

    public SettingFragment() {
    }

    @Override
    protected int resource() {
        return R.layout.fragment_setting;
    }

    @Override
    protected void initDataAndEvent() {
        cacheFile = new File(Constants.PATH_CACHE);
        // tvSettingClear.setText(ACache.getCacheSize(cacheFile));
        cbSettingCache.setChecked(mPresenter.getAutoCacheState());
        cbSettingImage.setChecked(mPresenter.getNoImageState());
        cbSettingNight.setChecked(mPresenter.getNightModeState());
        cbSettingCache.setOnCheckedChangeListener(this);
        cbSettingImage.setOnCheckedChangeListener(this);
        cbSettingNight.setOnCheckedChangeListener(this);
        try {
            PackageManager pm = getActivity().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(getActivity().getPackageName(), PackageManager.GET_ACTIVITIES);
            versionName = pi.versionName;
            tvSettingUpdate.setText(String.format("当前版本号 v%s", versionName));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.ll_setting_feedback)
    void doFeedBack() {
        // ShareUtils.sendEmail(mContext, "选择邮件客户端:");
    }

    @OnClick(R.id.ll_setting_clear)
    void doClear() {
//        ACache.deleteDir(cacheFile);
//        tvSettingClear.setText(ACache.getCacheSize(cacheFile));
    }

    @OnClick(R.id.ll_setting_update)
    void doUpdate() {
        mPresenter.checkVersion(versionName);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.cb_setting_night:
                mPresenter.setNightModeState(b);
                NightModeEvent event = new NightModeEvent();
                event.setNightMode(b);
                RxBus.getDefault().post(event);
                break;
            case R.id.cb_setting_image:
                mPresenter.setNoImageState(b);
                break;
            case R.id.cb_setting_cache:
                mPresenter.setAutoCacheState(b);
                break;
        }
    }

    public static SettingFragment newInstance() {
        return new SettingFragment();
    }

//    @Override
//    public void showUpdateDialog(VersionBean bean) {
//        StringBuilder content = new StringBuilder("版本号: v");
//        content.append(bean.getCode());
//        content.append("\r\n");
//        content.append("版本大小: ");
//        content.append(bean.getSize());
//        content.append("\r\n");
//        content.append("更新内容:\r\n");
//        content.append(bean.getDes().replace("\\r\\n","\r\n"));
//        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(mContext);
//        builder.setTitle("检测到新版本!");
//        builder.setMessage(content);
//        builder.setNegativeButton("取消", null);
//        builder.setPositiveButton("马上更新", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                Activity mActivity = getActivity();
//                if (mActivity instanceof MainActivity) {
//                    ((MainActivity) mActivity).checkPermissions();
//                }
//            }
//        });
//        builder.show();
//    }

}

package com.jthou.wanandroid.ui.main.activity;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jthou.wanandroid.R;
import com.jthou.wanandroid.base.fragment.AbstractFragment;
import com.jthou.wanandroid.util.CommonUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class AboutFragment extends AbstractFragment {

    @BindView(R.id.id_tv_content)
    TextView mTvContent;
    @BindView(R.id.id_tv_project_address)
    TextView mTvProjectAddress;
    @BindView(R.id.id_tv_version)
    TextView mTvVersion;

    private Unbinder mUnbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_about, container, false);
        mUnbinder = ButterKnife.bind(this, inflate);
        return inflate;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTvContent.setText(Html.fromHtml(getString(R.string.about_content)));
        mTvProjectAddress.setText(Html.fromHtml(getString(R.string.project_address)));
        mTvProjectAddress.setMovementMethod(LinkMovementMethod.getInstance());
        try {
            String versionStr = _mActivity.getPackageManager().getPackageInfo(_mActivity.getPackageName(), 0).versionName;
            mTvVersion.setText(getString(R.string.version_info, versionStr));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.id_tv_zhouzi)
    public void zhouzi(View v) {
        CommonUtils.showSnackMessage(_mActivity, getString(R.string.zhouzi_buchi));
    }

    @Override
    public void onDestroyView() {
        if (mUnbinder != null)
            mUnbinder.unbind();
        super.onDestroyView();
    }

    public static AboutFragment newInstance() {
        return new AboutFragment();
    }
}

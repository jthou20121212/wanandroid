package com.jthou.wanandroid.ui.main.fragment;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.jthou.wanandroid.R;
import com.jthou.wanandroid.base.fragment.ParentFragment;
import com.jthou.wanandroid.contract.main.NavigationContract;
import com.jthou.wanandroid.model.entity.Navigation;
import com.jthou.wanandroid.presenter.main.NavigationPresenter;
import com.jthou.wanandroid.ui.main.adapter.LeftAdapter;
import com.jthou.wanandroid.ui.main.adapter.RightAdapter;
import com.jthou.wanandroid.util.StickyTitleDecoration;
import com.jthou.wanandroid.util.weight.ClickRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class NavigationFragment extends ParentFragment<NavigationPresenter> implements NavigationContract.View, ClickRecyclerView.OnItemClickListener {

    @BindView(R.id.id_recycleView_left)
    ClickRecyclerView mLeftRecyclerView;
    @BindView(R.id.id_recycleView_right)
    RecyclerView mRightRecyclerView;

    private List<Navigation> mLeftData;
    private List<Navigation> mRightData;

    private LeftAdapter mLeftAdapter;
    private RightAdapter mRightAdapter;

    private RecyclerView.LayoutManager mLeftLayoutManager;
    private LinearLayoutManager mRightLayoutManager;

    private int mTargetPosition = -1;

    @Override
    public void showNavigationData(List<Navigation> data) {
        mLeftData.addAll(data);
        mRightData.addAll(data);

        mLeftAdapter.notifyDataSetChanged();
        mRightAdapter.notifyDataSetChanged();

        showNormal();
    }

    @Override
    protected int resource() {
        return R.layout.fragment_navigation;
    }

    @Override
    protected void initDataAndEvent() {
        mLeftData = new ArrayList<>();
        mLeftAdapter = new LeftAdapter(R.layout.item_novigation_left, mLeftData);
        mLeftRecyclerView.setAdapter(mLeftAdapter);
        mLeftRecyclerView.setOnItemClickListener(this);
        DividerItemDecoration decoration = new DividerItemDecoration(_mActivity, DividerItemDecoration.VERTICAL);
        mLeftRecyclerView.addItemDecoration(decoration);
        mLeftLayoutManager = new LinearLayoutManager(_mActivity);
        mLeftRecyclerView.setLayoutManager(mLeftLayoutManager);

        mRightData = new ArrayList<>();
        mRightAdapter = new RightAdapter(_mActivity, mRightData);
        mRightRecyclerView.setAdapter(mRightAdapter);
        mRightLayoutManager = new LinearLayoutManager(_mActivity);
        mRightRecyclerView.setLayoutManager(mRightLayoutManager);
        mRightRecyclerView.addOnScrollListener(mRightOnScrollListener);
        StickyTitleDecoration stickyDecoration = new StickyTitleDecoration(new StickyTitleDecoration.GroupNameCallback() {

            @Override
            public String getGroupName(int position) {
                return mRightData.get(position).getName();
            }

            @Override
            public void onGroupNameChange(int position) {
                // 通知左边RecyclerView重新设置选中索引
                String titleName = mRightData.get(position).getName();
                for (int i = 0; i < mLeftData.size(); i++) {
                    Navigation navigation = mLeftData.get(i);
                    if (TextUtils.equals(navigation.getName(), titleName)) {
                        mLeftAdapter.setSelectPosition(i);
                        break;
                    }
                }
            }

        });
        mRightRecyclerView.addItemDecoration(stickyDecoration);

        mPresenter.getNavigationList();
        showLoading();
    }

    RecyclerView.OnScrollListener mRightOnScrollListener = new RecyclerView.OnScrollListener() {

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

            super.onScrollStateChanged(recyclerView, newState);
            // 右边联动左边的处理
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            // 左边联动右边，对else情况的处理
            super.onScrolled(recyclerView, dx, dy);
            if (mTargetPosition != -1) {
                int firstVisibleItemPosition = mRightLayoutManager.findFirstVisibleItemPosition();
                int position = mTargetPosition - firstVisibleItemPosition;
                mTargetPosition = -1;
                if (position >= 0 && position < mRightRecyclerView.getChildCount()) {
                    View childAt = mRightRecyclerView.getChildAt(position);
                    mRightRecyclerView.scrollBy(0, childAt.getTop());
                }
            }
        }

    };

    public static NavigationFragment newInstance() {
        return new NavigationFragment();
    }

    @Override
    public void onClick(RecyclerView.ViewHolder viewHolder) {
        int position = viewHolder.getLayoutPosition();
        mLeftAdapter.setSelectPosition(position);

        String name = mLeftData.get(position).getName();
        int rightPosition = 0;
        for (int i = 0; i < mRightData.size(); i++) {
            Navigation navigation = mRightData.get(i);
            if (TextUtils.equals(name, navigation.getName())) {
                rightPosition = i;
                break;
            }
        }

        int firstVisibleItemPosition = mRightLayoutManager.findFirstVisibleItemPosition();
        int lastVisibleItemPosition = mRightLayoutManager.findLastVisibleItemPosition();
        if (rightPosition <= firstVisibleItemPosition) {
            mRightRecyclerView.scrollToPosition(rightPosition);
        } else if (rightPosition <= lastVisibleItemPosition) {
            View childAt = mRightRecyclerView.getChildAt(rightPosition - firstVisibleItemPosition);
            mRightRecyclerView.scrollBy(0, childAt.getTop());
        } else {
            // 这里需要先滚动position所在的位置
            mRightRecyclerView.scrollToPosition(rightPosition);
            mTargetPosition = rightPosition;
        }
    }

    @Override
    public void reload() {
        showLoading();
        mPresenter.getNavigationList();
    }

}

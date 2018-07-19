package com.jthou.wanandroid.ui.main.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.jthou.wanandroid.R;
import com.jthou.wanandroid.app.Key;
import com.jthou.wanandroid.base.fragment.BaseDialogFragment;
import com.jthou.wanandroid.contract.main.SearchContract;
import com.jthou.wanandroid.model.entity.Article;
import com.jthou.wanandroid.model.entity.HotKey;
import com.jthou.wanandroid.presenter.main.SearchPresenter;
import com.jthou.wanandroid.ui.search.activity.SearchListActivity;
import com.jthou.wanandroid.util.CircularRevealAnim;
import com.jthou.wanandroid.util.CommonUtils;
import com.jthou.wanandroid.util.LogHelper;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

import butterknife.BindView;

public class SearchFragment extends BaseDialogFragment<SearchPresenter> implements SearchContract.View, CircularRevealAnim.AnimListener {

    @BindView(R.id.id_tagFlowLayout)
    TagFlowLayout mTagFlowLayout;
    @BindView(R.id.floating_search_view)
    FloatingSearchView mSearchView;

    private CircularRevealAnim mCircularRevealAnim;

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.DialogStyle);
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        //DialogSearch的宽
        int width = (int) (metrics.widthPixels * 0.98);
        assert window != null;
        window.setLayout(width, WindowManager.LayoutParams.MATCH_PARENT);
        window.setGravity(Gravity.TOP);
        //取消过渡动画 , 使DialogSearch的出现更加平滑
        window.setWindowAnimations(R.style.DialogEmptyAnimation);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mCircularRevealAnim = new CircularRevealAnim();
        mCircularRevealAnim.setAnimListener(this);

        mSearchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(SearchSuggestion searchSuggestion) {

            }

            @Override
            public void onSearchAction(String currentQuery) {
                mCircularRevealAnim.hide(mSearchView, getView());
                Intent intent = new Intent(_mActivity, SearchListActivity.class);
                intent.putExtra(Key.IT_KEYWORD, currentQuery);
                startActivity(intent);
            }
        });

        mPresenter.getHotKeyList();
    }

    @Override
    public void showHotKeyList(List<HotKey> hotWordList) {
        mTagFlowLayout.setAdapter(new TagAdapter<HotKey>(hotWordList) {
            @Override
            public View getView(FlowLayout parent, int position, HotKey o) {
                TextView textView = new TextView(_mActivity);
                textView.setText(o.getName());
                textView.setBackgroundColor(CommonUtils.randomColor());
                textView.setTextColor(Color.WHITE);
                int leftAndRight = CommonUtils.dp2px(10);
                int topAndBottom = CommonUtils.dp2px(6);
                textView.setPadding(leftAndRight, topAndBottom, leftAndRight, topAndBottom);
                int margin = CommonUtils.dp2px(5);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(margin, margin, margin, margin);
                textView.setLayoutParams(params);
                return textView;
            }
        });
        mTagFlowLayout.setOnTagClickListener((view, position, parent) -> {
            mCircularRevealAnim.hide(mSearchView, getView());
            Intent intent = new Intent(_mActivity, SearchListActivity.class);
            intent.putExtra(Key.IT_KEYWORD, hotWordList.get(position).getName());
            startActivity(intent);
            return true;
        });
    }

    @Override
    public void showSearchList(List<Article> searchList) {

    }

    @Override
    protected int resource() {
        return R.layout.fragment_search;
    }

    @Override
    public void onHideAnimationEnd() {

    }

    @Override
    public void onShowAnimationEnd() {

    }

}

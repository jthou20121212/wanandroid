package com.jthou.wanandroid.ui.main.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jthou.wanandroid.R;
import com.jthou.wanandroid.model.entity.KnowledgeHierarchy;
import com.jthou.wanandroid.util.CommonUtils;

import java.util.List;

/**
 * Created by user on 2018/5/23.
 */

public class KnowledgeHierarchyAdapter extends BaseQuickAdapter<KnowledgeHierarchy, BaseViewHolder> {

    public KnowledgeHierarchyAdapter(int layoutResId, @Nullable List data) {
        super(layoutResId, data);
    }

    public KnowledgeHierarchyAdapter(@Nullable List data) {
        super(data);
    }

    public KnowledgeHierarchyAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, KnowledgeHierarchy item) {
        if(item.getName() == null) {
            return;
        }
        helper.setText(R.id.item_knowledge_hierarchy_title, item.getName());
        helper.setTextColor(R.id.item_knowledge_hierarchy_title, CommonUtils.randomColor());
        if (item.getChildren() == null) {
            return;
        }
        StringBuilder content = new StringBuilder();
        for (KnowledgeHierarchy data: item.getChildren()) {
            content.append(data.getName()).append("   ");
        }
        helper.setText(R.id.item_knowledge_hierarchy_content, content.toString());
    }

}

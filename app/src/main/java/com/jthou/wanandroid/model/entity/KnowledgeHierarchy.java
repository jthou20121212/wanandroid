package com.jthou.wanandroid.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class KnowledgeHierarchy implements Parcelable {

    private List<KnowledgeHierarchy> children;
    private int courseId;
    private int id;
    private String name;
    private int order;
    private int parentChapterId;
    private int visible;

    public List<KnowledgeHierarchy> getChildren() {
        return children;
    }

    public void setChildren(List<KnowledgeHierarchy> children) {
        this.children = children;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getParentChapterId() {
        return parentChapterId;
    }

    public void setParentChapterId(int parentChapterId) {
        this.parentChapterId = parentChapterId;
    }

    public int getVisible() {
        return visible;
    }

    public void setVisible(int visible) {
        this.visible = visible;
    }

    @Override
    public String toString() {
        return "KnowledgeHierarchy{" +
                "children=" + children +
                ", courseId=" + courseId +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", order=" + order +
                ", parentChapterId=" + parentChapterId +
                ", visible=" + visible +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.children);
        dest.writeInt(this.courseId);
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeInt(this.order);
        dest.writeInt(this.parentChapterId);
        dest.writeInt(this.visible);
    }

    public KnowledgeHierarchy() {
    }

    protected KnowledgeHierarchy(Parcel in) {
        this.children = new ArrayList<KnowledgeHierarchy>();
        in.readList(this.children, KnowledgeHierarchy.class.getClassLoader());
        this.courseId = in.readInt();
        this.id = in.readInt();
        this.name = in.readString();
        this.order = in.readInt();
        this.parentChapterId = in.readInt();
        this.visible = in.readInt();
    }

    public static final Parcelable.Creator<KnowledgeHierarchy> CREATOR = new Parcelable.Creator<KnowledgeHierarchy>() {
        @Override
        public KnowledgeHierarchy createFromParcel(Parcel source) {
            return new KnowledgeHierarchy(source);
        }

        @Override
        public KnowledgeHierarchy[] newArray(int size) {
            return new KnowledgeHierarchy[size];
        }
    };
}

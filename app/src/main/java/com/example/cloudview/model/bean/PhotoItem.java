package com.example.cloudview.model.bean;

import java.io.Serializable;

public class PhotoItem implements Serializable {
    private String path;
    private String name;
    private long createDate;
    private boolean isSelected = false;

    @Override
    public String toString() {
        return "PhotoItem{" +
                "path='" + path + '\'' +
                ", name='" + name + '\'' +
                ", createDate=" + createDate +
                ", isSelected=" + isSelected +
                '}';
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }
}

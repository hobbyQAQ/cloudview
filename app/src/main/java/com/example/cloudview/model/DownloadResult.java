package com.example.cloudview.model;

import java.util.List;

public class DownloadResult {

    /**
     * success : true
     * msg : 上传下载成功
     * data : null
     */

    private boolean success;
    private String msg;
    private Object data;

    @Override
    public String toString() {
        return "SimpleResult{" +
                "success=" + success +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}

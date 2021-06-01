package com.example.cloudview.model;

import java.io.Serializable;
import java.util.List;

public class PhotoResult implements  Serializable{


    /**
     * success : true
     * msg : OK
     * data : [{"id":1,"date":"2018-02-15T11:14:34.000+0000","location":null,"path":"photo/1/IMG_20180215_191434.jpg","type":1},{"id":2,"date":"2018-02-15T11:15:37.000+0000","location":null,"path":"photo/1/IMG_20180215_191537.jpg","type":1},{"id":3,"date":"2018-02-15T11:31:02.000+0000","location":null,"path":"photo/1/IMG_20180215_193102.jpg","type":1}]
     */

    private boolean success;
    private String msg;
    private List<DataBean> data;

    @Override
    public String toString() {
        return "PhotoResult{" +
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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        /**
         * id : 1
         * date : 2018-02-15T11:14:34.000+0000
         * location : null
         * path : photo/1/IMG_20180215_191434.jpg
         * type : 1
         * love: 0
         * uid: 1
         */

        private int id;
        private String date;
        private Object location;
        private String path;
        private int type;
        private int love;
        private int uid;

        @Override
        public String toString() {
            return "DataBean{" +
                    "id=" + id +
                    ", date='" + date + '\'' +
                    ", location=" + location +
                    ", path='" + path + '\'' +
                    ", type=" + type +
                    ", love=" + love +
                    ", uid=" + uid +
                    '}';
        }

        public int getLove() {
            return love;
        }

        public void setLove(int love) {
            this.love = love;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public Object getLocation() {
            return location;
        }

        public void setLocation(Object location) {
            this.location = location;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }
    }
}

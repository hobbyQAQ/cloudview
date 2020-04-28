package com.example.cloudview.model;

import java.util.List;

public class PhotoResult {

    /**
     * success : true
     * msg : OK
     * data : [{"id":1,"date":"2020-04-20T08:30:48.000+0000","location":"福建南平","path":"http://localhost:8089/img/static/1/IMG_20180215_193102.jpg"},{"id":2,"date":"2020-04-15T08:54:34.000+0000","location":"上海","path":"http://localhost:8089/img/static/1/IMG_20180215_191537.jpg"},{"id":3,"date":"2020-04-09T13:16:03.000+0000","location":"南昌","path":"http://localhost:8089/img/static/1/IMG_20180215_193102.jpg"},{"id":4,"date":"2019-03-03T04:07:44.000+0000","location":null,"path":"http://localhost:8089/img/static/1/IMG_20190303_120744.jpg"},{"id":5,"date":"2019-03-16T06:47:46.000+0000","location":null,"path":"http://localhost:8089/img/static/1/IMG_20190316_144746.jpg"},{"id":6,"date":"2019-03-18T07:48:26.000+0000","location":null,"path":"http://localhost:8089/img/static/1/IMG_20190318_154826.jpg"},{"id":7,"date":"2019-03-22T00:54:48.000+0000","location":null,"path":"http://localhost:8089/img/static/1/IMG_20190322_085448.jpg"},{"id":8,"date":"2019-03-22T01:47:11.000+0000","location":null,"path":"http://localhost:8089/img/static/1/IMG_20190322_094711.jpg"},{"id":9,"date":"2019-04-15T11:11:10.000+0000","location":null,"path":"http://localhost:8089/img/static/1/IMG_20190415_191110.jpg"},{"id":10,"date":"2019-04-23T12:20:27.000+0000","location":null,"path":"http://localhost:8089/img/static/1/IMG_20190423_202027.jpg"}]
     */

    private boolean success;
    private String msg;
    private List<DataBean> data;

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

    public static class DataBean {
        /**
         * id : 1
         * date : 2020-04-20T08:30:48.000+0000
         * location : 福建南平
         * path : http://localhost:8089/img/static/1/IMG_20180215_193102.jpg
         */

        private int id;
        private String date;
        private String location;
        private String path;

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

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "id=" + id +
                    ", date='" + date + '\'' +
                    ", location='" + location + '\'' +
                    ", path='" + path + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "PhotoResult{" +
                "success=" + success +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}

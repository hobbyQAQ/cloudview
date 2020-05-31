package com.example.cloudview.model;

import androidx.annotation.NonNull;

import java.util.List;

public class FaceResult {

    @Override
    public String toString() {
        return "FaceResult{" +
                "success=" + success +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    /**
     * success : true
     * msg : OK
     * data : [{"faceToken":"076d0f505c694f3b09a5a66021a97ca9","left":1571,"top":2012,"width":100,"height":100,"pid":7,"rotation":-88,"path":"/face/1/076d0f505c694f3b09a5a66021a97ca9.jpg"},{"faceToken":"4562220837188aa5d1fa3d270bb70fcf","left":930,"top":1941,"width":97,"height":94,"pid":12,"rotation":-110,"path":"/face/1/4562220837188aa5d1fa3d270bb70fcf.jpg"},{"faceToken":"5c734e21f81795cb3061bd4c7722d691","left":196,"top":175,"width":66,"height":61,"pid":11,"rotation":-4,"path":"/face/1/5c734e21f81795cb3061bd4c7722d691.jpg"},{"faceToken":"845a72cea366eaf1be04ec12d3f9715e","left":1737,"top":1225,"width":102,"height":104,"pid":13,"rotation":-8,"path":"/face/1/845a72cea366eaf1be04ec12d3f9715e.jpg"},{"faceToken":"9c021c9db96386974de011f6598aae7d","left":70,"top":2168,"width":51,"height":45,"pid":3,"rotation":5,"path":"/face/1/9c021c9db96386974de011f6598aae7d.jpg"},{"faceToken":"aa403dd946a340f7755dca0d9542a04d","left":1497,"top":2321,"width":172,"height":163,"pid":9,"rotation":-122,"path":"/face/1/aa403dd946a340f7755dca0d9542a04d.jpg"},{"faceToken":"bb7f2d923c1ab0e2879fc2d32c0ed41d","left":1430,"top":1211,"width":96,"height":110,"pid":13,"rotation":0,"path":"/face/1/bb7f2d923c1ab0e2879fc2d32c0ed41d.jpg"},{"faceToken":"c1e12a6d7f8c399a9a33a7a594b419c1","left":1831,"top":1869,"width":465,"height":451,"pid":1,"rotation":0,"path":"/face/1/c1e12a6d7f8c399a9a33a7a594b419c1.jpg"},{"faceToken":"f278f3402a3aa9e916083d0775c0f696","left":1507,"top":2151,"width":493,"height":486,"pid":2,"rotation":-6,"path":"/face/1/f278f3402a3aa9e916083d0775c0f696.jpg"}]
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
         * faceToken : 076d0f505c694f3b09a5a66021a97ca9
         * left : 1571.0
         * top : 2012.0
         * width : 100.0
         * height : 100.0
         * pid : 7
         * rotation : -88
         * path : /face/1/076d0f505c694f3b09a5a66021a97ca9.jpg
         */

        private String faceToken;
        private double left;
        private double top;
        private double width;
        private double height;
        private int pid;
        private int rotation;
        private String path;

        public String getFaceToken() {
            return faceToken;
        }

        public void setFaceToken(String faceToken) {
            this.faceToken = faceToken;
        }

        public double getLeft() {
            return left;
        }

        public void setLeft(double left) {
            this.left = left;
        }

        public double getTop() {
            return top;
        }

        public void setTop(double top) {
            this.top = top;
        }

        public double getWidth() {
            return width;
        }

        public void setWidth(double width) {
            this.width = width;
        }

        public double getHeight() {
            return height;
        }

        public void setHeight(double height) {
            this.height = height;
        }

        public int getPid() {
            return pid;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }

        public int getRotation() {
            return rotation;
        }

        public void setRotation(int rotation) {
            this.rotation = rotation;
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
                    "faceToken='" + faceToken + '\'' +
                    ", left=" + left +
                    ", top=" + top +
                    ", width=" + width +
                    ", height=" + height +
                    ", pid=" + pid +
                    ", rotation=" + rotation +
                    ", path='" + path + '\'' +
                    '}';
        }
    }
}

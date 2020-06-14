package com.example.cloudview.model;

import java.io.Serializable;

public class UserResult {

    /**
     * success : true
     * msg : OK
     * data : {"id":1,"account":"942449150","password":"123456","nickname":"HappyMan","coverPath":null}
     */

    private boolean success;
    private String msg;
    private DataBean data;

    @Override
    public String toString() {
        return "UserResult{" +
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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        @Override
        public String toString() {
            return "DataBean{" +
                    "id=" + id +
                    ", account='" + account + '\'' +
                    ", password='" + password + '\'' +
                    ", nickname='" + nickname + '\'' +
                    ", coverPath=" + coverPath +
                    '}';
        }

        /**
         * id : 1
         * account : 942449150
         * password : 123456
         * nickname : HappyMan
         * coverPath : null
         */

        private int id;
        private String account;
        private String password;
        private String nickname;
        private String coverPath;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getCoverPath() {
            return coverPath;
        }

        public void setCoverPath(String coverPath) {
            this.coverPath = coverPath;
        }
    }
}

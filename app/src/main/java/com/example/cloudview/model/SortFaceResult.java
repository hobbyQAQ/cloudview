package com.example.cloudview.model;

import java.io.Serializable;
import java.util.List;

public class SortFaceResult {


    /**
     * success : true
     * msg : OK
     * data : [{"cid":1,"name":"吴宇斌","facelist":[{"faceToken":"a9638926ea70205f471839fcf1eb8a91","left":1701,"top":1727,"width":117,"height":120,"pid":4,"rotation":-92,"path":"face/1/a9638926ea70205f471839fcf1eb8a91.jpg"},{"faceToken":"aa96d18852c61cbc05b2d51da3f7b9ec","left":1516,"top":1760,"width":126,"height":125,"pid":5,"rotation":-95,"path":"face/1/aa96d18852c61cbc05b2d51da3f7b9ec.jpg"},{"faceToken":"21f32586ffa24ef4be2e27bb60e545ef","left":1300,"top":1876,"width":114,"height":113,"pid":6,"rotation":-88,"path":"face/1/21f32586ffa24ef4be2e27bb60e545ef.jpg"},{"faceToken":"4528523332d573a4bca84c1866eeda7b","left":1066,"top":1372,"width":232,"height":246,"pid":9,"rotation":-92,"path":"face/1/4528523332d573a4bca84c1866eeda7b.jpg"},{"faceToken":"a4082b3f6b262013dbc102c77fc5179b","left":2311,"top":1591,"width":203,"height":201,"pid":10,"rotation":-3,"path":"face/1/a4082b3f6b262013dbc102c77fc5179b.jpg"}]},{"cid":2,"name":"吴宇宁","facelist":[{"faceToken":"eef430dbd368ec838df91eb5456f3978","left":2045,"top":1596,"width":516,"height":487,"pid":1,"rotation":-88,"path":"face/1/eef430dbd368ec838df91eb5456f3978.jpg"},{"faceToken":"95b0a1641885aa47041d10936a3d299d","left":1513,"top":2148,"width":485,"height":473,"pid":2,"rotation":-7,"path":"face/1/95b0a1641885aa47041d10936a3d299d.jpg"},{"faceToken":"81aee98115f9cb4e588f7e0a4dcee51a","left":1832,"top":1869,"width":465,"height":451,"pid":3,"rotation":0,"path":"face/1/81aee98115f9cb4e588f7e0a4dcee51a.jpg"}]},{"cid":3,"name":"未命名3","facelist":[{"faceToken":"c3e00381cb6f4f2ada817f66c5cbfb9e","left":2326,"top":1365,"width":131,"height":123,"pid":7,"rotation":-2,"path":"face/1/c3e00381cb6f4f2ada817f66c5cbfb9e.jpg"},{"faceToken":"33577ba7c0a94258f76867b4dc0ea902","left":2200,"top":1467,"width":155,"height":169,"pid":8,"rotation":-4,"path":"face/1/33577ba7c0a94258f76867b4dc0ea902.jpg"},{"faceToken":"5316de4fe6cdaa6eaeb482968ed240a3","left":1422,"top":1327,"width":249,"height":245,"pid":9,"rotation":-100,"path":"face/1/5316de4fe6cdaa6eaeb482968ed240a3.jpg"},{"faceToken":"2d5e107cafbe6528c4f5e8edf819309e","left":1700,"top":1584,"width":214,"height":219,"pid":10,"rotation":-1,"path":"face/1/2d5e107cafbe6528c4f5e8edf819309e.jpg"}]},{"cid":4,"name":"未命名4","facelist":[{"faceToken":"9c0db31f8993032e4d79d196c06448bb","left":2989,"top":1642,"width":192,"height":207,"pid":9,"rotation":-83,"path":"face/1/9c0db31f8993032e4d79d196c06448bb.jpg"}]},{"cid":5,"name":"未命名5","facelist":[{"faceToken":"a6f63785633fb88269f8f448378a9873","left":1487,"top":1043,"width":618,"height":719,"pid":13,"rotation":63,"path":"face/1/a6f63785633fb88269f8f448378a9873.jpg"}]},{"cid":6,"name":"未命名6","facelist":[{"faceToken":"0d98620e9d08f803353ef90e96942b52","left":2262,"top":1438,"width":284,"height":331,"pid":13,"rotation":116,"path":"face/1/0d98620e9d08f803353ef90e96942b52.jpg"}]}]
     */

    private boolean success;
    private String msg;
    private List<DataBean> data;



    @Override
    public String toString() {
        return "SortFaceResult{" +
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

    public static class DataBean {
        /**
         * cid : 1
         * name : 吴宇斌
         * facelist : [{"faceToken":"a9638926ea70205f471839fcf1eb8a91","left":1701,"top":1727,"width":117,"height":120,"pid":4,"rotation":-92,"path":"face/1/a9638926ea70205f471839fcf1eb8a91.jpg"},{"faceToken":"aa96d18852c61cbc05b2d51da3f7b9ec","left":1516,"top":1760,"width":126,"height":125,"pid":5,"rotation":-95,"path":"face/1/aa96d18852c61cbc05b2d51da3f7b9ec.jpg"},{"faceToken":"21f32586ffa24ef4be2e27bb60e545ef","left":1300,"top":1876,"width":114,"height":113,"pid":6,"rotation":-88,"path":"face/1/21f32586ffa24ef4be2e27bb60e545ef.jpg"},{"faceToken":"4528523332d573a4bca84c1866eeda7b","left":1066,"top":1372,"width":232,"height":246,"pid":9,"rotation":-92,"path":"face/1/4528523332d573a4bca84c1866eeda7b.jpg"},{"faceToken":"a4082b3f6b262013dbc102c77fc5179b","left":2311,"top":1591,"width":203,"height":201,"pid":10,"rotation":-3,"path":"face/1/a4082b3f6b262013dbc102c77fc5179b.jpg"}]
         */

        private int cid;
        private String name;
        private List<FacelistBean> facelist;

        public DataBean(int cid, String name, List<FacelistBean> faces) {
            this.cid = cid;
            this.name = name;
            this.facelist = faces;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "cid=" + cid +
                    ", name='" + name + '\'' +
                    ", facelist=" + facelist +
                    '}';
        }

        public int getCid() {
            return cid;
        }

        public void setCid(int cid) {
            this.cid = cid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<FacelistBean> getFacelist() {
            return facelist;
        }

        public void setFacelist(List<FacelistBean> facelist) {
            this.facelist = facelist;
        }

        public static class FacelistBean implements Serializable{
            /**
             * faceToken : a9638926ea70205f471839fcf1eb8a91
             * left : 1701
             * top : 1727
             * width : 117
             * height : 120
             * pid : 4
             * rotation : -92
             * path : face/1/a9638926ea70205f471839fcf1eb8a91.jpg
             */

            private String faceToken;
            private int left;
            private int top;
            private int width;
            private int height;
            private int pid;
            private int rotation;
            private String path;

            @Override
            public String toString() {
                return "FacelistBean{" +
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

            public String getFaceToken() {
                return faceToken;
            }

            public void setFaceToken(String faceToken) {
                this.faceToken = faceToken;
            }

            public int getLeft() {
                return left;
            }

            public void setLeft(int left) {
                this.left = left;
            }

            public int getTop() {
                return top;
            }

            public void setTop(int top) {
                this.top = top;
            }

            public int getWidth() {
                return width;
            }

            public void setWidth(int width) {
                this.width = width;
            }

            public int getHeight() {
                return height;
            }

            public void setHeight(int height) {
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
        }
    }
}

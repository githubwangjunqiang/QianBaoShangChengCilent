package com.yunyouzhiyuan.qianbaoshangchengclient.entiy;

import java.util.List;

/**
 * Created by ${王俊强} on 2017/3/7.
 */

public class KTVSort {

    private List<SpecBean> spec;
    private List<RiqiBean> riqiBeen;

    public List<RiqiBean> getRiqiBeen() {
        return riqiBeen;
    }

    public void setRiqiBeen(List<RiqiBean> riqiBeen) {
        this.riqiBeen = riqiBeen;
    }

    public List<SpecBean> getSpec() {
        return spec;
    }

    public void setSpec(List<SpecBean> spec) {
        this.spec = spec;
    }

    public static class RiqiBean {
        private String date;
        private String week;
        private String id;
        private boolean idSechked;

        public boolean isIdSechked() {
            return idSechked;
        }

        public void setIdSechked(boolean idSechked) {
            this.idSechked = idSechked;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getWeek() {
            return week;
        }

        public void setWeek(String week) {
            this.week = week;
        }
    }

    public static class SpecBean {
        /**
         * id : 11
         * spec_id : 8
         * item : 小包
         */

        private String id;
        private String spec_id;
        private String name;
        private boolean isSekd;

        public boolean isSekd() {
            return isSekd;
        }

        public void setSekd(boolean sekd) {
            isSekd = sekd;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSpec_id() {
            return spec_id;
        }

        public void setSpec_id(String spec_id) {
            this.spec_id = spec_id;
        }

        public String getItem() {
            return name;
        }

        public void setItem(String name) {
            this.name = name;
        }
    }
}

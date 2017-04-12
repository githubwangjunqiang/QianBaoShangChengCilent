package com.yunyouzhiyuan.qianbaoshangchengclient.entiy;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ${王俊强} on 2017/3/7.
 */

public class KTVshuxing {

    /**
     * retcode : 2000
     * msg : 获取成功！
     * data : {"spec":[{"id":"11","spec_id":"8","item":"小包"},{"id":"12","spec_id":"8","item":"中包"},{"id":"13","spec_id":"8","item":"大包"},{"id":"14","spec_id":"9","item":"12:00-18:00"},{"id":"15","spec_id":"9","item":"18:00-00:00"},{"id":"16","spec_id":"9","item":"00:00-06:00"},{"id":"17","spec_id":"10","item":"0"},{"id":"18","spec_id":"10","item":"1"},{"id":"19","spec_id":"10","item":"2"},{"id":"20","spec_id":"10","item":"3"},{"id":"21","spec_id":"10","item":"4"},{"id":"22","spec_id":"10","item":"5"},{"id":"23","spec_id":"10","item":"6"}],"riqi":{"0":{"date":"03-12","week":"周日"},"1":{"date":"03-13","week":"周一"},"2":{"date":"03-07","week":"今天"},"3":{"date":"03-08","week":"周三"},"4":{"date":"03-09","week":"周四"},"5":{"date":"03-10","week":"周五"},"6":{"date":"03-11","week":"周六"}}}
     */

    private int retcode;
    private String msg;
    private DataBean data;

    public int getRetcode() {
        return retcode;
    }

    public void setRetcode(int retcode) {
        this.retcode = retcode;
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

    public static class DataBean {
        /**
         * spec : [{"id":"11","spec_id":"8","item":"小包"},{"id":"12","spec_id":"8","item":"中包"},{"id":"13","spec_id":"8","item":"大包"},{"id":"14","spec_id":"9","item":"12:00-18:00"},{"id":"15","spec_id":"9","item":"18:00-00:00"},{"id":"16","spec_id":"9","item":"00:00-06:00"},{"id":"17","spec_id":"10","item":"0"},{"id":"18","spec_id":"10","item":"1"},{"id":"19","spec_id":"10","item":"2"},{"id":"20","spec_id":"10","item":"3"},{"id":"21","spec_id":"10","item":"4"},{"id":"22","spec_id":"10","item":"5"},{"id":"23","spec_id":"10","item":"6"}]
         * riqi : {"0":{"date":"03-12","week":"周日"},"1":{"date":"03-13","week":"周一"},"2":{"date":"03-07","week":"今天"},"3":{"date":"03-08","week":"周三"},"4":{"date":"03-09","week":"周四"},"5":{"date":"03-10","week":"周五"},"6":{"date":"03-11","week":"周六"}}
         */

        private RiqiBean riqi;
        private List<SpecBean> spec;

        public RiqiBean getRiqi() {
            return riqi;
        }

        public void setRiqi(RiqiBean riqi) {
            this.riqi = riqi;
        }

        public List<SpecBean> getSpec() {
            return spec;
        }

        public void setSpec(List<SpecBean> spec) {
            this.spec = spec;
        }

        public static class RiqiBean {
            /**
             * 0 : {"date":"03-12","week":"周日"}
             * 1 : {"date":"03-13","week":"周一"}
             * 2 : {"date":"03-07","week":"今天"}
             * 3 : {"date":"03-08","week":"周三"}
             * 4 : {"date":"03-09","week":"周四"}
             * 5 : {"date":"03-10","week":"周五"}
             * 6 : {"date":"03-11","week":"周六"}
             */

            @SerializedName("0")
            private _$0Bean _$0;
            @SerializedName("1")
            private _$1Bean _$1;
            @SerializedName("2")
            private _$2Bean _$2;
            @SerializedName("3")
            private _$3Bean _$3;
            @SerializedName("4")
            private _$4Bean _$4;
            @SerializedName("5")
            private _$5Bean _$5;
            @SerializedName("6")
            private _$6Bean _$6;

            public _$0Bean get_$0() {
                return _$0;
            }

            public void set_$0(_$0Bean _$0) {
                this._$0 = _$0;
            }

            public _$1Bean get_$1() {
                return _$1;
            }

            public void set_$1(_$1Bean _$1) {
                this._$1 = _$1;
            }

            public _$2Bean get_$2() {
                return _$2;
            }

            public void set_$2(_$2Bean _$2) {
                this._$2 = _$2;
            }

            public _$3Bean get_$3() {
                return _$3;
            }

            public void set_$3(_$3Bean _$3) {
                this._$3 = _$3;
            }

            public _$4Bean get_$4() {
                return _$4;
            }

            public void set_$4(_$4Bean _$4) {
                this._$4 = _$4;
            }

            public _$5Bean get_$5() {
                return _$5;
            }

            public void set_$5(_$5Bean _$5) {
                this._$5 = _$5;
            }

            public _$6Bean get_$6() {
                return _$6;
            }

            public void set_$6(_$6Bean _$6) {
                this._$6 = _$6;
            }

            public static class _$0Bean {
                /**
                 * date : 03-12
                 * week : 周日
                 */

                private String date;
                private String week;

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

            public static class _$1Bean {
                /**
                 * date : 03-13
                 * week : 周一
                 */

                private String date;
                private String week;

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

            public static class _$2Bean {
                /**
                 * date : 03-07
                 * week : 今天
                 */

                private String date;
                private String week;

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

            public static class _$3Bean {
                /**
                 * date : 03-08
                 * week : 周三
                 */

                private String date;
                private String week;

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

            public static class _$4Bean {
                /**
                 * date : 03-09
                 * week : 周四
                 */

                private String date;
                private String week;

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

            public static class _$5Bean {
                /**
                 * date : 03-10
                 * week : 周五
                 */

                private String date;
                private String week;

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

            public static class _$6Bean {
                /**
                 * date : 03-11
                 * week : 周六
                 */

                private String date;
                private String week;

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
        }

        public static class SpecBean {
            /**
             * id : 11
             * spec_id : 8
             * item : 小包
             */

            private String id;
            private String spec_id;
            private String item;

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
                return item;
            }

            public void setItem(String item) {
                this.item = item;
            }
        }
    }
}

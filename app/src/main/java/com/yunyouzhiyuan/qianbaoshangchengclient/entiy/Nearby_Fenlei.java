package com.yunyouzhiyuan.qianbaoshangchengclient.entiy;

import java.util.List;

/**
 * Created by ${王俊强} on 2017/3/17.
 */

public class Nearby_Fenlei {

    /**
     * retcode : 2000
     * msg : 获取成功
     * data : [{"sc_id":"1","sc_name":"美食","sencond":[{"id":"2","mobile_name":"自助餐"},{"id":"3","mobile_name":"烤肉"},{"id":"4","mobile_name":"川湘菜"},{"id":"7","mobile_name":"快餐小吃"},{"id":"6","mobile_name":"甜点饮品"},{"id":"8","mobile_name":"海鲜"},{"id":"9","mobile_name":"北京菜"},{"id":"10","mobile_name":"烤鱼"}]},{"sc_id":"3","sc_name":"酒店","sencond":[{"id":"50","mobile_name":"全日制"},{"id":"51","mobile_name":"钟点房"}]},{"sc_id":"4","sc_name":"KTV","sencond":[{"id":"1","mobile_name":"美食"},{"id":"11","mobile_name":"外卖"},{"id":"21","mobile_name":"休闲娱乐"},{"id":"42","mobile_name":"酒店"},{"id":"64","mobile_name":"丽人/美发"},{"id":"73","mobile_name":"特产"}]},{"sc_id":"5","sc_name":"休闲娱乐","sencond":[{"id":"22","mobile_name":"足疗"},{"id":"23","mobile_name":"汗蒸"},{"id":"24","mobile_name":"酒吧"},{"id":"25","mobile_name":"密室逃脱"},{"id":"26","mobile_name":"KTV"},{"id":"27","mobile_name":"电玩"},{"id":"28","mobile_name":"电影"},{"id":"29","mobile_name":"健身"},{"id":"30","mobile_name":"密室"},{"id":"31","mobile_name":"手工"},{"id":"32","mobile_name":"温泉洗浴"}]}]
     */

    private int retcode;
    private String msg;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * sc_id : 1
         * sc_name : 美食
         * sencond : [{"id":"2","mobile_name":"自助餐"},{"id":"3","mobile_name":"烤肉"},{"id":"4","mobile_name":"川湘菜"},{"id":"7","mobile_name":"快餐小吃"},{"id":"6","mobile_name":"甜点饮品"},{"id":"8","mobile_name":"海鲜"},{"id":"9","mobile_name":"北京菜"},{"id":"10","mobile_name":"烤鱼"}]
         */

        private String sc_id;
        private String sc_name;
        private List<SencondBean> sencond;

        public String getSc_id() {
            return sc_id;
        }

        public void setSc_id(String sc_id) {
            this.sc_id = sc_id;
        }

        public String getSc_name() {
            return sc_name;
        }

        public void setSc_name(String sc_name) {
            this.sc_name = sc_name;
        }

        public List<SencondBean> getSencond() {
            return sencond;
        }

        public void setSencond(List<SencondBean> sencond) {
            this.sencond = sencond;
        }

        public static class SencondBean {
            /**
             * id : 2
             * mobile_name : 自助餐
             */

            private String id;
            private String mobile_name;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getMobile_name() {
                return mobile_name;
            }

            public void setMobile_name(String mobile_name) {
                this.mobile_name = mobile_name;
            }
        }
    }
}

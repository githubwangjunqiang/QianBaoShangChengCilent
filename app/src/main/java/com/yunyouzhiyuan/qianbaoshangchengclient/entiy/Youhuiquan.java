package com.yunyouzhiyuan.qianbaoshangchengclient.entiy;

import java.util.List;

/**
 * Created by ${王俊强} on 2017/2/11.
 */

public class Youhuiquan {

    /**
     * retcode : 2000
     * msg : 获取成功！
     * data : [{"id":"23","store_id":"1","send_end_time":"2016-09-30 00:00:00","name":"测试代金券10元","money":"10.00","use_end_time":"2016-10-30 00:00:00","condition":"1000.00","store_name":"TPSHP旗舰店"},{"id":"22","store_id":"1","send_end_time":"2016-09-30 00:00:00","name":"测试代金券10元","money":"10.00","use_end_time":"2016-10-30 00:00:00","condition":"1000.00","store_name":"TPSHP旗舰店"},{"id":"21","store_id":"1","send_end_time":"2016-09-30 00:00:00","name":"测试代金券10元","money":"10.00","use_end_time":"2016-10-30 00:00:00","condition":"1000.00","store_name":"TPSHP旗舰店"},{"id":"5","store_id":"1","send_end_time":"2016-08-19 00:00:00","name":"2搜豹公司赠送10元代金券","money":"10.00","use_end_time":"2016-09-19 00:00:00","condition":"100.00","store_name":"TPSHP旗舰店"},{"id":"4","store_id":"1","send_end_time":"2016-08-19 00:00:00","name":"2搜豹公司赠送10元代金券","money":"10.00","use_end_time":"2016-09-19 00:00:00","condition":"100.00","store_name":"TPSHP旗舰店"},{"id":"2","store_id":"1","send_end_time":"2016-08-19 00:00:00","name":"1搜豹公司赠送10元代金券","money":"10.00","use_end_time":"2016-09-19 00:00:00","condition":"100.00","store_name":"TPSHP旗舰店"},{"id":"3","store_id":"1","send_end_time":"2016-08-19 00:00:00","name":"1搜豹公司赠送10元代金券","money":"10.00","use_end_time":"2016-09-19 00:00:00","condition":"100.00","store_name":"TPSHP旗舰店"}]
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

    public  class DataBean {
        /**
         * id : 23
         * store_id : 1
         * send_end_time : 2016-09-30 00:00:00
         * name : 测试代金券10元
         * money : 10.00
         * use_end_time : 2016-10-30 00:00:00
         * condition : 1000.00
         * store_name : TPSHP旗舰店
         */

        private String id;
        private String store_id;
        private String send_end_time;
        private String name;
        private String money;
        private String use_end_time;
        private String condition;
        private String store_name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getStore_id() {
            return store_id;
        }

        public void setStore_id(String store_id) {
            this.store_id = store_id;
        }

        public String getSend_end_time() {
            return send_end_time;
        }

        public void setSend_end_time(String send_end_time) {
            this.send_end_time = send_end_time;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getUse_end_time() {
            return use_end_time;
        }

        public void setUse_end_time(String use_end_time) {
            this.use_end_time = use_end_time;
        }

        public String getCondition() {
            return condition;
        }

        public void setCondition(String condition) {
            this.condition = condition;
        }

        public String getStore_name() {
            return store_name;
        }

        public void setStore_name(String store_name) {
            this.store_name = store_name;
        }
    }
}

package com.yunyouzhiyuan.qianbaoshangchengclient.entiy;

import java.util.List;

/**
 * Created by ${王俊强} on 2017/2/24.
 */

public class DingDanYouhuiquan {

    /**
     * retcode : 2000
     * msg : 获取成功！
     * data : [{"name":"满100减30","money":"30.00","condition":"100.00","id":"1","store_id":"1"},{"name":"满100减30","money":"30.00","condition":"100.00","id":"5","store_id":"1"},{"name":"满100减30","money":"30.00","condition":"100.00","id":"9","store_id":"1"}]
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
         * name : 满100减30
         * money : 30.00
         * condition : 100.00
         * id : 1
         * store_id : 1
         */

        private String name;
        private String money;
        private String condition;
        private String id;
        private String store_id;

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

        public String getCondition() {
            return condition;
        }

        public void setCondition(String condition) {
            this.condition = condition;
        }

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
    }
}

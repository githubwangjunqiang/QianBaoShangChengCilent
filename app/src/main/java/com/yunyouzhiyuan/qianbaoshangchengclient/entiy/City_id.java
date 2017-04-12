package com.yunyouzhiyuan.qianbaoshangchengclient.entiy;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ${王俊强} on 2017/3/10.
 */

public class City_id implements Serializable{

    /**
     * retcode : 2000
     * msg : 获取成功！
     * data : [{"id":"2","name":"北京市"},{"id":"9419","name":"大庆市"}]
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

    public class DataBean implements Serializable{
        /**
         * id : 2
         * name : 北京市
         */

        private String id;
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}

package com.yunyouzhiyuan.qianbaoshangchengclient.entiy;

import java.util.List;

/**
 * Created by ${王俊强} on 2017/2/11.
 */
public class Yuee {

    /**
     * retcode : 2000
     * msg : 获取成功！
     * data : {"user_money":"238.90","consume_list":[{"log_id":"196","user_money":"0.01","change_time":"2016-07-05 11:36:45","desc":"退款到用户余额"},{"log_id":"197","user_money":"0.01","change_time":"2016-07-05 11:37:20","desc":"退款到用户余额"},{"log_id":"198","user_money":"0.01","change_time":"2016-07-05 11:40:45","desc":"退款到用户余额"},{"log_id":"199","user_money":"0.01","change_time":"2016-07-05 11:45:52","desc":"退款到用户余额"},{"log_id":"200","user_money":"0.01","change_time":"2016-07-05 13:58:14","desc":"退款到用户余额"},{"log_id":"201","user_money":"123.00","change_time":"2016-07-08 18:32:37","desc":"订单退货"},{"log_id":"202","user_money":"100.00","change_time":"2016-07-08 18:45:12","desc":"订单退货"},{"log_id":"203","user_money":"200.00","change_time":"2016-07-08 18:48:11","desc":"订单退货"},{"log_id":"204","user_money":"100.00","change_time":"2016-07-08 18:50:26","desc":"订单退货"},{"log_id":"206","user_money":"900.00","change_time":"2016-07-13 20:42:02","desc":"订单退货"}]}
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

    public  class DataBean {
        /**
         * user_money : 238.90
         * consume_list : [{"log_id":"196","user_money":"0.01","change_time":"2016-07-05 11:36:45","desc":"退款到用户余额"},{"log_id":"197","user_money":"0.01","change_time":"2016-07-05 11:37:20","desc":"退款到用户余额"},{"log_id":"198","user_money":"0.01","change_time":"2016-07-05 11:40:45","desc":"退款到用户余额"},{"log_id":"199","user_money":"0.01","change_time":"2016-07-05 11:45:52","desc":"退款到用户余额"},{"log_id":"200","user_money":"0.01","change_time":"2016-07-05 13:58:14","desc":"退款到用户余额"},{"log_id":"201","user_money":"123.00","change_time":"2016-07-08 18:32:37","desc":"订单退货"},{"log_id":"202","user_money":"100.00","change_time":"2016-07-08 18:45:12","desc":"订单退货"},{"log_id":"203","user_money":"200.00","change_time":"2016-07-08 18:48:11","desc":"订单退货"},{"log_id":"204","user_money":"100.00","change_time":"2016-07-08 18:50:26","desc":"订单退货"},{"log_id":"206","user_money":"900.00","change_time":"2016-07-13 20:42:02","desc":"订单退货"}]
         */

        private String user_money;
        private List<ConsumeListBean> consume_list;

        public String getUser_money() {
            return user_money;
        }

        public void setUser_money(String user_money) {
            this.user_money = user_money;
        }

        public List<ConsumeListBean> getConsume_list() {
            return consume_list;
        }

        public void setConsume_list(List<ConsumeListBean> consume_list) {
            this.consume_list = consume_list;
        }

        public  class ConsumeListBean {
            /**
             * log_id : 196
             * user_money : 0.01
             * change_time : 2016-07-05 11:36:45
             * desc : 退款到用户余额
             */

            private String log_id;
            private String user_money;
            private String change_time;
            private String desc;

            public String getLog_id() {
                return log_id;
            }

            public void setLog_id(String log_id) {
                this.log_id = log_id;
            }

            public String getUser_money() {
                return user_money;
            }

            public void setUser_money(String user_money) {
                this.user_money = user_money;
            }

            public String getChange_time() {
                return change_time;
            }

            public void setChange_time(String change_time) {
                this.change_time = change_time;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }
        }
    }
}

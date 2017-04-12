package com.yunyouzhiyuan.qianbaoshangchengclient.entiy;

import java.util.List;

/**
 * Created by ${王俊强} on 2017/3/16.
 */

public class Specialty_Fenlei {

    /**
     * retcode : 2000
     * msg : 获取成功
     * data : [{"id":"74","mobile_name":"特色美食","second":[{"id":"78","mobile_name":"肉制品"},{"id":"79","mobile_name":"干货 "}]},{"id":"75","mobile_name":"农林牧渔","second":[{"id":"80","mobile_name":" 茶 "},{"id":"81","mobile_name":"冲调品"}]},{"id":"76","mobile_name":"旅游特产","second":[{"id":"82","mobile_name":"旅游纪念品 "}]},{"id":"77","mobile_name":"医药特产","second":[{"id":"83","mobile_name":"中成药 "}]}]
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
         * id : 74
         * mobile_name : 特色美食
         * second : [{"id":"78","mobile_name":"肉制品"},{"id":"79","mobile_name":"干货 "}]
         */

        private String id;
        private String mobile_name;
        private List<SecondBean> second;
        private boolean isChecked;

        public boolean isChecked() {
            return isChecked;
        }

        public void setChecked(boolean checked) {
            isChecked = checked;
        }

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

        public List<SecondBean> getSecond() {
            return second;
        }

        public void setSecond(List<SecondBean> second) {
            this.second = second;
        }

        public  class SecondBean {
            /**
             * id : 78
             * mobile_name : 肉制品
             */

            private String id;
            private String mobile_name;
            private boolean isChecked;

            public boolean isChecked() {
                return isChecked;
            }

            public void setChecked(boolean checked) {
                isChecked = checked;
            }

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

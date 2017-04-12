package com.yunyouzhiyuan.qianbaoshangchengclient.entiy;

import java.io.Serializable;

/**
 * Created by ${王俊强} on 2017/2/15.
 */
public class UserInfo implements Serializable{

    /**
     * retcode : 2000
     * msg : 获取成功！
     * data : {"nickname":"测试人员","user_money":"238.90","head_pic":null,"birthday":"1970-01-01"}
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

    public  class DataBean implements Serializable{
        /**
         * nickname : 测试人员
         * user_money : 238.90
         * head_pic : null
         * birthday : 1970-01-01
         */

        private String nickname;
        private String user_money;
        private String head_pic;
        private String birthday;

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getUser_money() {
            return user_money;
        }

        public void setUser_money(String user_money) {
            this.user_money = user_money;
        }

        public String getHead_pic() {
            return head_pic;
        }

        public void setHead_pic(String head_pic) {
            this.head_pic = head_pic;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }
    }
}

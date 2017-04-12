package com.yunyouzhiyuan.qianbaoshangchengclient.entiy;

import java.util.List;

/**
 * Created by wangjunqiang on 2016/11/25.
 */
public class City {

    /**
     * retcode : 2000
     * msg : 获取成功
     * data : [{"region_id":"3","region_name":"北京"},{"region_id":"22","region_name":"安徽"},{"region_id":"149","region_name":"福建"},{"region_id":"245","region_name":"甘肃"},{"region_id":"347","region_name":"广东"},{"region_id":"530","region_name":"广西"},{"region_id":"654","region_name":"贵州"},{"region_id":"756","region_name":"海南"},{"region_id":"799","region_name":"河北"},{"region_id":"984","region_name":"河南"},{"region_id":"1167","region_name":"黑龙江"},{"region_id":"1310","region_name":"湖北"},{"region_id":"1431","region_name":"湖南"},{"region_id":"1569","region_name":"吉林"},{"region_id":"1643","region_name":"江苏"},{"region_id":"1781","region_name":"江西"},{"region_id":"1895","region_name":"辽宁"},{"region_id":"2012","region_name":"内蒙古"},{"region_id":"2126","region_name":"宁夏"},{"region_id":"2158","region_name":"青海"},{"region_id":"2210","region_name":"山东"},{"region_id":"2369","region_name":"山西"},{"region_id":"2504","region_name":"陕西"},{"region_id":"2622","region_name":"上海"},{"region_id":"2643","region_name":"四川"},{"region_id":"2845","region_name":"天津"},{"region_id":"2866","region_name":"西藏"},{"region_id":"2947","region_name":"新疆"},{"region_id":"3060","region_name":"云南"},{"region_id":"3206","region_name":"浙江"},{"region_id":"3314","region_name":"重庆"},{"region_id":"3356","region_name":"香港"},{"region_id":"3376","region_name":"澳门"},{"region_id":"3379","region_name":"台湾"}]
     */

    private int retcode;
    private String msg;
    /**
     * region_id : 3
     * region_name : 北京
     */

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

    public class DataBean {
        private String region_id;
        private String region_name;

        public String getRegion_id() {
            return region_id;
        }

        public void setRegion_id(String region_id) {
            this.region_id = region_id;
        }

        public String getRegion_name() {
            return region_name;
        }

        public void setRegion_name(String region_name) {
            this.region_name = region_name;
        }
    }
}


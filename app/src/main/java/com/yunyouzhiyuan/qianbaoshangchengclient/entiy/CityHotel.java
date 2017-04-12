package com.yunyouzhiyuan.qianbaoshangchengclient.entiy;

import java.util.List;

/**
 * Created by ${王俊强} on 2017/3/3.
 */

public class CityHotel {

    /**
     * retcode : 2000
     * msg : 获取成功
     * data : [{"id":"36679","name":"阿坝州","level":"2"},{"id":"46551","name":"阿克苏地区","level":"2"},{"id":"5799","name":"阿拉善盟","level":"2"},{"id":"47374","name":"阿勒泰地区","level":"2"},{"id":"41770","name":"阿里地区","level":"2"},{"id":"43379","name":"安康市","level":"2"},{"id":"14687","name":"安庆市","level":"2"},{"id":"38402","name":"安顺市","level":"2"},{"id":"22058","name":"安阳市","level":"2"},{"id":"6266","name":"鞍山市","level":"2"},{"id":"5418","name":"巴彦淖尔市","level":"2"},{"id":"46422","name":"巴音郭楞蒙古自治州","level":"2"},{"id":"36299","name":"巴中市","level":"2"},{"id":"8333","name":"白城市","level":"2"},{"id":"8144","name":"白山市","level":"2"},{"id":"43936","name":"白银市","level":"2"},{"id":"31033","name":"百色市","level":"2"},{"id":"14410","name":"蚌埠市","level":"2"},{"id":"4759","name":"包头市","level":"2"},{"id":"42119","name":"宝鸡市","level":"2"},{"id":"1772","name":"保定市","level":"2"},{"id":"39923","name":"保山市","level":"2"},{"id":"30688","name":"北海市","level":"2"},{"id":"6476","name":"本溪市","level":"2"},{"id":"38816","name":"毕节地区","level":"2"},{"id":"21123","name":"滨州市","level":"2"},{"id":"15764","name":"亳州市","level":"2"},{"id":"46380","name":"博尔塔拉蒙古自治州","level":"2"},{"id":"2629","name":"沧州市","level":"2"},{"id":"41178","name":"昌都地区","level":"2"},{"id":"46255","name":"昌吉州","level":"2"},{"id":"7532","name":"长春市","level":"2"},{"id":"25580","name":"长沙市","level":"2"},{"id":"3431","name":"长治市","level":"2"},{"id":"26683","name":"常德市","level":"2"},{"id":"11245","name":"常州市","level":"2"},{"id":"15499","name":"巢湖市","level":"2"},{"id":"7208","name":"朝阳市","level":"2"},{"id":"29915","name":"潮州市","level":"2"},{"id":"27147","name":"郴州市","level":"2"},{"id":"33008","name":"成都市","level":"2"},{"id":"2400","name":"承德市","level":"2"},{"id":"15871","name":"池州市","level":"2"},{"id":"4874","name":"赤峰市","level":"2"},{"id":"31478","name":"崇左市","level":"2"},{"id":"15005","name":"滁州市","level":"2"},{"id":"40441","name":"楚雄州","level":"2"},{"id":"35813","name":"达州市","level":"2"},{"id":"40852","name":"大理州","level":"2"},{"id":"6088","name":"大连市","level":"2"}]
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

    public class DataBean {
        /**
         * id : 36679
         * name : 阿坝州
         * level : 2
         */

        private String id;
        private String name;
        private String level;

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

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }
    }
}

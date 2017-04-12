package com.yunyouzhiyuan.qianbaoshangchengclient.ui.okhttp;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ${王俊强} on 2017/2/27.
 */

public class RequestParams {
    private Map<String, String> maps = new HashMap<>();
    private Map<String, Object> mapsobj = new HashMap<>();

    public Map<String, Object> getMapsobj() {
        return mapsobj;
    }

    public void setMapsobj(Map<String, Object> mapsobj) {
        this.mapsobj = mapsobj;
    }

    public Map<String, String> getMaps() {
        return maps;
    }

    public void setMaps(Map<String, String> maps) {
        this.maps = maps;
    }

    public static class Builder {

        protected RequestParams options;

        public Builder() {
            newRequestParams();
        }

        protected void newRequestParams() {
            options = new RequestParams();
        }

        public RequestParams build() {
            return options;
        }


        public Builder add(String key,String va) {
            options.maps.put(key,va);
            return this;
        }
        public Builder addFile(String key,File va) {
            options.mapsobj.put(key,va);
            return this;
        }
    }
}

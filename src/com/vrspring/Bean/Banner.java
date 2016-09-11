package com.vrspring.Bean;

/**
 * Created by xie on 2016/9/11.
 */
public class Banner {
    private String url;
    private String name;

    public Banner(String url, String name) {
        this.url = url;
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

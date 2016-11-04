package com.vrspring.Bean;

/**
 * Created by xie on 2016/9/11.
 */
public class Banner {

    private String url;
    private String name;
    private String detailUrl;

    public Banner(String url, String name, String detailUrl) {
        this.url = url;
        this.name = name;
        this.detailUrl = detailUrl;
    }

    public String getDetailUrl() {
        return detailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl;
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

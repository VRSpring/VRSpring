package com.vrspring.Bean;

import java.util.List;

/**
 * Created by xie on 2016/9/11.
 */
public class HomeBean {
    private List<Banner> banners;
    private List<IndexBean> list;

    public List<Banner> getBanners() {
        return banners;
    }

    public void setBanners(List<Banner> banners) {
        this.banners = banners;
    }

    public List<IndexBean> getList() {
        return list;
    }

    public void setList(List<IndexBean> list) {
        this.list = list;
    }
}

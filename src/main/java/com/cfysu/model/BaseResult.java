package com.cfysu.model;

import lombok.Data;

import java.util.List;

/**
 * Created by Administrator on 2019/11/30.
 */
public class BaseResult {
    private Integer totalPage;
    private List<Video> videoList;

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public List<Video> getVideoList() {
        return videoList;
    }

    public void setVideoList(List<Video> videoList) {
        this.videoList = videoList;
    }
}

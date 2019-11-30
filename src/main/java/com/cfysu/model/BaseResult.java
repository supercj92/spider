package com.cfysu.model;


import java.util.List;

/**
 * Created by Administrator on 2019/11/30.
 */
public class BaseResult {
    private Integer totalPage;
    private Integer currentPage;
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

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }
}

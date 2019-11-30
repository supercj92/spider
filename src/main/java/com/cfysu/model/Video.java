package com.cfysu.model;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2019/11/9.
 */
@Entity
public class Video implements Serializable {

    public final static int FAVORITE_YES = 1;
    public final static int FAVORITE_NO = 0;

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String viewKey;

    @Column
    private String title;

    @Column
    private String imgUrl;

    @Column
    private String duration;

    @Column
    private String info;

    @Column
    private String videoUrl;

    @Column
    private Date insertDate;
//    private int downloadId;
//    private int favorite;
//
//    private int progress;
//    private long speed;
//    private int soFarBytes;
//    private int totalFarBytes;
//    private int status;
//    private Date favoriteDate;
//    private Date addDownloadDate;
//    private Date finshedDownloadDate;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getViewKey() {
        return viewKey;
    }

    public void setViewKey(String viewKey) {
        this.viewKey = viewKey;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public Date getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(Date insertDate) {
        this.insertDate = insertDate;
    }

    //    public int getDownloadId() {
//        return downloadId;
//    }
//
//    public void setDownloadId(int downloadId) {
//        this.downloadId = downloadId;
//    }
//
//    public int getFavorite() {
//        return favorite;
//    }
//
//    public void setFavorite(int favorite) {
//        this.favorite = favorite;
//    }
//
//    public int getProgress() {
//        return progress;
//    }
//
//    public void setProgress(int progress) {
//        this.progress = progress;
//    }
//
//    public long getSpeed() {
//        return speed;
//    }
//
//    public void setSpeed(long speed) {
//        this.speed = speed;
//    }
//
//    public int getSoFarBytes() {
//        return soFarBytes;
//    }
//
//    public void setSoFarBytes(int soFarBytes) {
//        this.soFarBytes = soFarBytes;
//    }
//
//    public int getTotalFarBytes() {
//        return totalFarBytes;
//    }
//
//    public void setTotalFarBytes(int totalFarBytes) {
//        this.totalFarBytes = totalFarBytes;
//    }
//
//    public int getStatus() {
//        return status;
//    }
//
//    public void setStatus(int status) {
//        this.status = status;
//    }
//
//    public Date getFavoriteDate() {
//        return favoriteDate;
//    }
//
//    public void setFavoriteDate(Date favoriteDate) {
//        this.favoriteDate = favoriteDate;
//    }
//
//    public Date getAddDownloadDate() {
//        return addDownloadDate;
//    }
//
//    public void setAddDownloadDate(Date addDownloadDate) {
//        this.addDownloadDate = addDownloadDate;
//    }
//
//    public Date getFinshedDownloadDate() {
//        return finshedDownloadDate;
//    }
//
//    public void setFinshedDownloadDate(Date finshedDownloadDate) {
//        this.finshedDownloadDate = finshedDownloadDate;
//    }
}

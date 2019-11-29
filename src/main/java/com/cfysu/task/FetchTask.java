package com.cfysu.task;

import com.alibaba.fastjson.JSONObject;
import com.cfysu.dao.VideoDao;
import com.cfysu.model.Video;
import com.cfysu.service.HttpService;
import com.cfysu.service.ParseService;
import com.cfysu.util.RandomIPAdderssUtils;
import io.reactivex.Observable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2019/11/9.
 */
@Service
@EnableScheduling
public class FetchTask {

    @Autowired
    private HttpService httpService;

    @Autowired
    private ParseService parseService;

    @Autowired
    private VideoDao videoDao;


    @Scheduled(cron = "0 0/1 * * * ?")
    public void runTask(){
        try {
            fetchVideoInfo();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private  void fetchVideoInfo(){
        Observable<String> stringObservable = httpService.getmNoLimit91PornServiceApi().indexPhp();
        String indexHtml = stringObservable.blockingFirst();
        List<Video> videoList = parseService.parseIndex(indexHtml);

        for (Video item : videoList){
            Video videoFromDB = videoDao.findByViewKey(item.getViewKey());
            if(videoFromDB != null){
                continue;
            }
            Observable<String> videoPage = httpService.getmNoLimit91PornServiceApi().getVideoPlayPage(item.getViewKey(), RandomIPAdderssUtils.getRandomIPAdderss());
            String videoHtml = videoPage.blockingFirst();
            try {
                String videoUrl = parseService.parseVideoPlayUrl(videoHtml);
                item.setVideoUrl(videoUrl);
            } catch (Exception e) {
                e.printStackTrace();
            }
            videoDao.save(item);
        }
        System.out.println(JSONObject.toJSONString(videoList));

    }
}

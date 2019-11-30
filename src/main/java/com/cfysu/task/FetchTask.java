package com.cfysu.task;

import com.alibaba.fastjson.JSONObject;
import com.cfysu.dao.VideoDao;
import com.cfysu.model.BaseResult;
import com.cfysu.model.Video;
import com.cfysu.service.HttpService;
import com.cfysu.service.ParseService;
import com.cfysu.util.RandomIPAdderssUtils;
import io.reactivex.Observable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import retrofit2.HttpException;

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

    private Integer currentPage = 2;


    @Scheduled(cron = "0 0/1 * * * ?")
    public void runTask(){
        try {
            fetchVideoInfo();
        }catch (Exception e){
            System.out.println("task fail!!!current page:" + currentPage);
            if(e instanceof HttpException){
                System.out.println(((HttpException)e).response().raw().request().url());
            }
            e.printStackTrace();
        }
    }

    private  void fetchVideoInfo(){
        System.out.println("task start...current page:" + currentPage);
        Observable<String> stringObservable = httpService.getmNoLimitServiceApi().getCategoryPage("rf", "basic", currentPage, "m");
        String indexHtml = stringObservable.blockingFirst();
        BaseResult baseResult = parseService.parseCategory(indexHtml);

        Integer totalPage = baseResult.getTotalPage();
        if(currentPage < totalPage){
            currentPage ++;
        }
        if(currentPage.equals(totalPage)){
            currentPage = 2;
        }

        List<Video> videoList = baseResult.getVideoList();

        for (Video item : videoList){
            Video videoFromDB = videoDao.findByViewKey(item.getViewKey());
            if(videoFromDB != null){
                continue;
            }
            Observable<String> videoPage = httpService.getmNoLimitServiceApi().getVideoPlayPage(item.getViewKey(), RandomIPAdderssUtils.getRandomIPAdderss());
            String videoHtml = videoPage.blockingFirst();
            try {
                String videoUrl = parseService.parseVideoPlayUrl(videoHtml);
                item.setVideoUrl(videoUrl);
            } catch (Exception e) {
                e.printStackTrace();
            }
            videoDao.save(item);
        }
    }
}

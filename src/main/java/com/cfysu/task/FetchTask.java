package com.cfysu.task;

import com.cfysu.dao.VideoDao;
import com.cfysu.model.BaseResult;
import com.cfysu.model.Task;
import com.cfysu.model.Video;
import com.cfysu.service.HttpService;
import com.cfysu.service.ParseService;
import com.cfysu.util.RandomIPAdderssUtils;
import io.reactivex.Observable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import retrofit2.HttpException;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.net.SocketTimeoutException;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2019/11/9.
 */
@Slf4j
@Service
@EnableScheduling
public class FetchTask implements InitializingBean {

    @Autowired
    private HttpService httpService;

    @Autowired
    private ParseService parseService;

    @Autowired
    private VideoDao videoDao;

    private Integer currentPage = 50;

    private String category = "rf";

    private Integer totalPage = 0;

    public static final String CMD = "stop";

    private String[] catgoryArray = new String[]{"rf","mf","tf","md","top"};

    private Integer categoryIndex = 0;

    private Integer failCount = 0;


    @Scheduled(cron = "0 0/1 * * * ?")
    public void runTask(){
        try {
            fetchVideoInfo();
            failCount = 0;
        }catch (Exception e){
            log.error("task fail!!!current page:" + currentPage, e);
            if(e instanceof HttpException){
                log.error(((HttpException)e).response().raw().request().url().toString(), e);
            }
            if(++failCount > 50){
                this.currentPage++;
            }
        }
    }

    private void fetchVideoInfo(){
        if(CMD.equals(category)){
            return;
        }
        log.info("task start...current page:{}", currentPage);
        Observable<String> stringObservable = httpService.getmNoLimitServiceApi().getCategoryPage(category, "basic", currentPage, "m");
        String indexHtml = stringObservable.blockingFirst();
        BaseResult baseResult = parseService.parseCategory(indexHtml);

        Integer totalPage = baseResult.getTotalPage();
        this.totalPage = totalPage;

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
                item.setInsertDate(new Date());
                item.setCategory_src(category);
                videoDao.save(item);
            } catch (Exception e) {
                log.error("parseVideoPlayUrl error", e);
            }
        }

//        switch category and incr page
        if(currentPage < totalPage){
            currentPage ++;
        }
        if(currentPage.equals(totalPage)){
            if(categoryIndex == catgoryArray.length){
                categoryIndex = 0;
            }
            category = catgoryArray[categoryIndex];
            categoryIndex++;
            currentPage = 2;
        }
        log.info("task end...current page:{},total page:{},category:{}", currentPage, totalPage, category);
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
//        log.info("resume task from disk...");
//        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("/upload/task.data"));
//        Task task = (Task)objectInputStream.readObject();
//        if(task != null && task.getCategory() != null){
//            this.category = task.getCategory();
//        }
//
//        if(task != null && task.getCurrentPage() != null){
//            this.currentPage = task.getCurrentPage();
//        }
        log.info("task from disk...category:{},page:{}", category, currentPage);
    }
}

package com.cfysu.controller;

import javax.servlet.http.HttpServletRequest;

import com.cfysu.dao.VideoDao;
import com.cfysu.model.BaseResult;
import com.cfysu.model.Video;
import com.cfysu.task.FetchTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2019/11/9.
 */
@RestController
@RequestMapping("/res")
public class ResController {
    @Autowired
    private FetchTask fetchTask;

    @Autowired
    private VideoDao videoDao;

    @Value("${auth.token}")
    private String token;

    public static final String RES_SUCESS = "ok";

    public static final String RES_FAIL = "error";

    @GetMapping("/currentPage")
    public String setCurrentPage(@RequestParam Integer currentPage){
        fetchTask.setCurrentPage(currentPage);
        return RES_SUCESS;
    }

//    mf收藏;tf本月收藏;rf加精;md讨论;top本月最热;m=-1上月最热;
    @GetMapping("/category")
    public String setCategory(@RequestParam(defaultValue = "rf") String category){
        fetchTask.setCategory(category);
        return RES_SUCESS + ";mf收藏;tf本月收藏;rf加精;md讨论;top本月最热;m=-1上月最热";
    }

    @GetMapping("/login")
    public String login(HttpServletRequest request, @RequestParam String token){
        if(this.token.equals(token)){
            request.getSession().setAttribute("token", this.token);
            return RES_SUCESS;
        }
        return RES_FAIL;
    }

    @GetMapping("/taskInfo")
    public String getTask(){
        return fetchTask.getCategory() + ":" + fetchTask.getCurrentPage() + ":" + fetchTask.getTotalPage();
    }

    @GetMapping("/video")
    public BaseResult showVideoList(@RequestParam(defaultValue = "0") Integer currentPage, @RequestParam(defaultValue = "16") Integer pageSize){
        BaseResult baseResult = new BaseResult();
        Sort.Direction sort =  Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(currentPage, pageSize, sort, "insertDate");
        Page<Video> all = videoDao.findAll(pageable);
        baseResult.setVideoList(all.getContent());
        baseResult.setTotalPage(all.getTotalPages());
        baseResult.setCurrentPage(currentPage);
        return baseResult;
    }
}

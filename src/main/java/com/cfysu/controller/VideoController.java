package com.cfysu.controller;

import com.cfysu.dao.VideoDao;
import com.cfysu.model.BaseResult;
import com.cfysu.model.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Administrator on 2019/12/1.
 */
@Controller
@RequestMapping("res")
public class VideoController {

    @Autowired
    private VideoDao videoDao;

    @RequestMapping("/video")
    public String showVideoList(@RequestParam(defaultValue = "0") Integer currentPage, @RequestParam(defaultValue = "16") Integer pageSize, @RequestParam(defaultValue = "") String titleLike, Model model){

        BaseResult baseResult = new BaseResult();
        Sort.Direction sort =  Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(currentPage, pageSize, sort, "insertDate");
        Video video = new Video();
        video.setTitle(titleLike);
        ExampleMatcher exampleMatcher = ExampleMatcher.matching().withMatcher("title" ,ExampleMatcher.GenericPropertyMatchers.contains());
        Example<Video> example = Example.of(video, exampleMatcher);
        Page<Video> all = videoDao.findAll(example, pageable);
        baseResult.setVideoList(all.getContent());
        baseResult.setTotalPage(all.getTotalPages());
        baseResult.setTotalRecord((int)all.getTotalElements());
        baseResult.setCurrentPage(currentPage);
        model.addAttribute("baseResult", baseResult);
        model.addAttribute("titleLike", titleLike);
        return "video_list";
    }
}

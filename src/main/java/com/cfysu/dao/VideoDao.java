package com.cfysu.dao;


import com.cfysu.model.Video;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Administrator on 2019/11/9.
 */
public interface VideoDao extends JpaRepository<Video, Integer>{
    Video findByViewKey(String viewKey);
}

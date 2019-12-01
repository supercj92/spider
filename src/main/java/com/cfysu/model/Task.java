package com.cfysu.model;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;

/**
 * @Author canglong
 * @Date 2019/12/1
 */
@Builder
@Data
public class Task implements Serializable {
    private Integer currentPage;
    private String category;
}

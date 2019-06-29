package com.project.model.school;

import lombok.Data;

@Data
/**
 * 图片相册model
 */
public class Album {
    private String id;
    private String school_id;
    private String img_url;
    private int orders;

}

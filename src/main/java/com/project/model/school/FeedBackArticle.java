package com.project.model.school;

import lombok.Data;

@Data
public class FeedBackArticle {
    private String id;
    private String from_member_id;
    private String to_member_id;
    private String article_id;
    private String feedback;
    private String type;
    private String flag;
    private String create_time;
}

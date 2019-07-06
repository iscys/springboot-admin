package com.project.model.school;

import lombok.Data;

@Data
public class FeedBackTeacher {

    private String id;
    private String from_member_id;
    private String to_member_id;
    private String teacher_id;
    private String to_id;
    private String star;
    private String feedback;
    private String type;
    private String flag;
    private String annoy;//0 匿名 1不匿名
    private String level;//1-差评 2-中评 3-好评

    private String create_time;
}

package com.project.model.school;

import lombok.Data;

import java.util.ArrayList;

@Data
public class SchoolModel {
    
            private String id;
            private String school_name;
            private String school_icon;
            private String school_face;
            private String school_phone;
            private String school_describe;
            private String school_code;
            private String school_wxsmall_url;
            private String school_location;
            private String school_location_x;
            private String school_location_y;
            private String flag;
            private String create_time;
            private int feedback_count;//驾校评论数
            private String star;//驾校星级
            private String orders;//驾校星级
            private String mark;//标签
            private ArrayList<String> tag;//标签format集合





}

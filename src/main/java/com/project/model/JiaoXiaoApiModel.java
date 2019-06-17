package com.project.model;

import lombok.Data;

import java.util.List;

@Data
public class JiaoXiaoApiModel {
    private String error_code;
    private String reason;
    private List<QuestionAndAns> result;
}

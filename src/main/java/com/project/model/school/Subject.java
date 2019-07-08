package com.project.model.school;

import lombok.Data;

@Data
public class Subject {
    private String id;
    private String school_id;
    private String subject;
    private String subject_name;
    private String flag;
    private String mark;
    private String price;
    private String hours;//课时


    public static Object parseSubjectName(String subject_name) {
        String parse="";
        switch (subject_name){
            case "1":
                parse="科目二";
                break;
            case "2":
                parse="科目二单学时";
                break;
            case "3":
                parse="科目三";
                break;
            case "4":
                parse="科目三单学时";
                break;
            case "5":
                parse="总收费";
                break;


        }
        return parse;
    }
}

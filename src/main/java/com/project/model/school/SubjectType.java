package com.project.model.school;

import lombok.Data;

@Data
public class SubjectType {
    private String subject_type;
    private String pri ="0";

    public static void covert(SubjectType sub, SchoolSupport support) {

        String subject_type = sub.getSubject_type();
        switch(subject_type){
            case "A1":
                sub.setPri(support.getA1());
                break;
            case "A2":
                sub.setPri(support.getA2());
                break;
            case "A3":
                sub.setPri(support.getA3());

                break;
            case "B1":
                sub.setPri(support.getB1());

                break;
            case "B2":
                sub.setPri(support.getB2());

                break;
            case "C1":
                sub.setPri(support.getC1());

                break;
            case "C2":
                sub.setPri(support.getC2());

                break;
            case "D":
                sub.setPri(support.getD());

                break;
            case "E":
                sub.setPri(support.getE());

                break;
            case "F":
                sub.setPri(support.getF());

                break;

        }


    }
}

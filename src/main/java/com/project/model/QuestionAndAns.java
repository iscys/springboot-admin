package com.project.model;

import lombok.Data;
import lombok.ToString;
import org.springframework.util.StringUtils;

@Data
@ToString
public class QuestionAndAns {

    private String id;
    private String question;
    private String answer;
    private String format_answer;
    private String item1;
    private String item2;
    private String item3;
    private String item4;
    private String explains;
    private String url;
    private String model;//驾照类型c1 c2 b1 b2 a1 a2
    private String subject;//科目1 科目4
    private String type;//0 判断选择 1 单选 2 多选


    //接口备注：
    // 根据输入参数返回相关题目,
    // 答案对应：
    // "1": "A或者正确","2": "B或者错误","3": "C","4": "D","7": "AB","8": "AC","9": "AD","10": "BC","11": "BD","12": "CD","13": "ABC","14": "ABD","15": "ACD","16": "BCD","17": "ABCD"

    public static String formatAnswer(String answer){
        String res=null;
        switch (answer) {
            case "1":
               res="A";
                break;
            case "2":
                res="B";
                break;
            case "3":
                res="C";
                break;
            case "4":
                res="D";
                break;
            case "7":
                res="A,B";
                break;
            case "8":
                res="A,C";
                break;
            case "9":
                res="A,D";
                break;
            case "10":
                res="B,C";
                break;
            case "11":
                res="B,D";
                break;
            case "12":
                res="C,D";
                break;
            case "13":
                res="A,B,C";
                break;
            case "14":
                res="A,B,D";
                break;
            case "15":
                res="A,C,D";
                break;
            case "16":
                res="B,C,D";
                break;
            case "17":
                res="A,B,C,D";
                break;
        }

    return res;
    }

    public static void formatData(QuestionAndAns questionAndAns) {
        String format_answer = formatAnswer(questionAndAns.getAnswer());
        questionAndAns.setFormat_answer(format_answer);
        if(StringUtils.isEmpty(questionAndAns.getItem1())&&
                StringUtils.isEmpty(questionAndAns.getItem2())){
            questionAndAns.setItem1("正确");
            questionAndAns.setItem2("错误");
            questionAndAns.setType("0");

        }else if(format_answer.length()>=2){
            questionAndAns.setType("2");
        }else{
            questionAndAns.setType("1");
        }

    }
}

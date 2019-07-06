package com.project.model.IDCard;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class IDFront {
    private String log_id;
    private Result words_result;
    private String words_result_num;
    private String image_status;
    private String direction;


    /**
     * fuck 百度api 中文命名
     */
    @Data
    @ToString
    public class  Result {
        private Deatil 姓名;
        private Deatil 民族;
        private Deatil 住址;
        private Deatil 公民身份号码;
        private Deatil 性别;
        private Deatil 出生;

    }

    @Data
    @ToString
    public class  Deatil {
        private String words;
        private Location location;

    }

    @Data
    @ToString
    public class  Location {
        private String top;
        private Object left;
        private String width;
        private String height;

    }

}


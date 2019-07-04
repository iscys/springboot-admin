package com.project.model.school;

import com.project.utils.GsonUtils;
import lombok.Data;

@Data
public class Order {

             private String id;
            private String member_id;
            private String apply_id;
            private String order_sn;
            private String school_id;
            private String subject_id;
            private String price;
            private String status;
            private String remarks;
            private String time;
            private String subject_name;
            private String pay_time;
            private String confirm_time;
            private String refund_time;
            private String cancel_time;
            private String pay_method;
            private String origin;
            private String openid;
            private String clientIp;
             private String num;
            private String subject;



    @Override
    public String toString() {
        return GsonUtils.toJson(this);
    }
}

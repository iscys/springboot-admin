package com.project.model.school;

import lombok.Data;

@Data
public class Apply {

    private String idcard;
    private String name;
    private int cardtype;//1：身份证2：军官证3：护照4：居住证5：其他6：士兵证7境外人员护照
    private int sex;
    private String birdate;//yyyymmdd证件类型不是身份证必选
    private String phone;
    private String addr;
    private String traintype;// 培训车型下列编码单选：A1,A2,A3,B1,B2,C1,C2,C3,C4,C5,D,E,F,M,N,P
    private String orgcode;//培训机构编号
    private String applydate;//报名时间
    private String perdritype;//原准驾车型下列编码单选：A1,A2,A3,B1,B2,C1,C2,C3,C4,C5,D,E,F,M,N,P
    private String drilicnum;//驾驶证号
    private String licencedate;//驾驶证初领日期
    private String photo;//base64

}

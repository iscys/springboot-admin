package com.project.utils;

/**
 * layui 数据模板
 */
public class Layui {


    private int code;//状态码
    private String msg;//消息
    private int count;//数量
    private Object data;//数据信息


    public static Layui success(int count ,Object data){
        return new Layui(0,"",count,data);
    }

    public Layui(int code, String msg, int count, Object data) {
        this.code = code;
        this.msg = msg;
        this.count = count;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}

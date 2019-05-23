package com.project.mapper.wx;

import java.util.HashMap;

public interface WxMpper {
    void saveTuiGuangAchievement(HashMap<String, String> achieve);

    void saveTuiGuangUserInfo(HashMap<String, String> userInfo);

    void cancelTuiguang(HashMap<String, String> param);

    HashMap<String,String> getTuiGuangUserInfo(HashMap<String, String> userInfo);

    void updateTuiGuangUserInfo(HashMap<String, String> userInfo);
}

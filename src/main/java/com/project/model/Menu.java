package com.project.model;

import lombok.Data;

import java.util.List;

/**
 * 菜单Menu
 */
@Data
public class Menu {

    private String id;
    private String title;
    private String href;
    private String parent;
    private String orders;
    private String pri ="0";
    private List<Menu> children;


}

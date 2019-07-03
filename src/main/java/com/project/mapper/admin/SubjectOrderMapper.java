package com.project.mapper.admin;

import com.project.model.school.Order;
import com.project.utils.PageData;

import java.util.HashMap;
import java.util.List;

/**
 * 驾校套餐订单
 */
public interface SubjectOrderMapper {
    Order getOrderDetil(Order order);

    void updateOrder(Order order);

    void saveOrder(Order order);

    Integer getOrderCount(PageData pd);

    List<HashMap<String, String>> getOrderList(PageData pd);
}

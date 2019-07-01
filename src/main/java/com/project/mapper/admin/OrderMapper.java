package com.project.mapper.admin;

import com.project.model.school.Order;

public interface OrderMapper {
    Order getOrderDetil(Order order);

    void updateOrder(Order order);

    void saveOrder(Order order);
}

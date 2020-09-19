package com.wuxp.study.mockito.inject;

import com.wuxp.study.mockito.inject.dto.OrderInfo;

import java.util.List;

public class OrderService {

    private OrderDao orderDao;

    private UserDao userDao;

    public List<OrderInfo> getUserOrders(String userName) {
        Long userId = userDao.getUserId(userName);
        return orderDao.getUserOderIds(userId);
    }
}

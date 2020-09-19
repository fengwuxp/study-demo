package com.wuxp.study.mockito.inject;

import com.wuxp.study.mockito.inject.dto.OrderInfo;

import java.util.Collections;
import java.util.List;

public class OrderDao {

    public List<OrderInfo> getUserOderIds(Long userId) {
        System.out.println("获取订单的userId：" + userId);
        return Collections.emptyList();
    }
}

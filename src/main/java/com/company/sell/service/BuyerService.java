package com.company.sell.service;

import com.company.sell.dto.OrderDTO;

public interface BuyerService {
    OrderDTO findOrderOne(String openid,String orderId);
    OrderDTO cancelOrder(String openid,String orderId);
}

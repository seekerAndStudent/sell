package com.company.sell.service.impl;

import com.company.sell.dto.OrderDTO;
import com.company.sell.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class OrderServiceImpl implements OrderService {
    @Override
    public OrderDTO create(OrderDTO orderDTO) {
        return null;
    }

    @Override
    public OrderDTO findOne(String openId) {
        return null;
    }

    @Override
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {
        return null;
    }

    @Override
    public OrderDTO paid(OrderDTO orderDTO) {
        return null;
    }

    @Override
    public OrderDTO cancel(OrderDTO orderDTO) {
        return null;
    }

    @Override
    public OrderDTO finish(OrderDTO orderDTO) {
        return null;
    }
}

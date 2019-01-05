package com.company.sell.service.impl;

import com.company.sell.Enum.ResultEnum;
import com.company.sell.dto.OrderDTO;
import com.company.sell.exception.SellException;
import com.company.sell.service.BuyerService;
import com.company.sell.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BuyerServiceImpl implements BuyerService {
    @Autowired
    private OrderService orderService;

    @Override
    public OrderDTO findOrderOne(String openid, String orderId) {
        return checkOrderOwner(openid,orderId);
    }

    @Override
    public OrderDTO cancelOrder(String openid, String orderId) {
        OrderDTO orderDTO = checkOrderOwner(openid,orderId);
        if(orderDTO==null){
            log.error("【取消订单失败！查询不到该订单】,openid={},orderId={}",openid,orderId);
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        return orderDTO;
    }

    private OrderDTO checkOrderOwner(String openid, String orderId){
        //首先根据orderid查询订单，对比订单的openid和当前的openid
        OrderDTO orderDTO = orderService.findOne(orderId);
        if (orderDTO==null){
            return null;
        }
        if(!orderDTO.getBuyerOpenid().equals(openid)){
            log.error("【查询订单详情失败！当前用户与订单的openid不匹配】,openid={},orderId={}",openid,orderId);
            throw new SellException(ResultEnum.ORDER_OWNER_ERROR);
        }
        return orderDTO;
    }
}

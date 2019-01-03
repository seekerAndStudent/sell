package com.company.sell.service.impl;

import com.company.sell.dataobject.OrderDetail;
import com.company.sell.dto.OrderDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderServiceImplTest {
    @Autowired
    private OrderServiceImpl orderService;

    private final String BUYER_OPENID = "666666";
    @Test
    public void create() throws Exception{
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName("刘邦登童鞋");
        orderDTO.setBuyerAddress("凌霄宝殿22号");
        orderDTO.setBuyerPhone("17854118105");
        orderDTO.setBuyerOpenid(BUYER_OPENID);

        //模拟购物车
        List<OrderDetail> orderDetailList = new ArrayList<OrderDetail>();
        OrderDetail o1 = new OrderDetail();
        o1.setProductId("123456789");
        o1.setProductQuantity(1);
        orderDetailList.add(o1);

        orderDTO.setOrderDetailList(orderDetailList);

        OrderDTO resultOrderDto  = orderService.create(orderDTO);
        log.info("---------【订单完成】----------- result={}",resultOrderDto);
    }

    @Test
    public void findOne() {
    }

    @Test
    public void findList() {
    }

    @Test
    public void paid() {
    }

    @Test
    public void cancel() {
    }

    @Test
    public void finish() {
    }
}
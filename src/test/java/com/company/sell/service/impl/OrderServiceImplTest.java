package com.company.sell.service.impl;

import com.company.sell.Enum.OrderStatusEnum;
import com.company.sell.Enum.PayStatus;
import com.company.sell.dataobject.OrderDetail;
import com.company.sell.dto.OrderDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

        OrderDetail o2 = new OrderDetail();
        o2.setProductId("12345678910");
        o2.setProductQuantity(1);

        orderDetailList.add(o1);
        orderDetailList.add(o2);

        orderDTO.setOrderDetailList(orderDetailList);

        OrderDTO resultOrderDto  = orderService.create(orderDTO);
        log.info("---------【创建订单完成】----------- result={}",resultOrderDto);
    }

    @Test
    public void findOne() throws Exception{
        OrderDTO resultOrderDTO = orderService.findOne("1546497171361337778");
        log.info("---------【查询订单完成】----------- result={}",resultOrderDTO);
        Assert.assertEquals("1546497171361337778",resultOrderDTO.getOrderId());
    }

    @Test
    public void findList() {
        PageRequest pageRequest = PageRequest.of(0,2);
        Page<OrderDTO> orderDTOPage = orderService.findList(BUYER_OPENID,pageRequest);
        log.info("---------【查询订单列表完成】----------- result={}",orderDTOPage);
        Assert.assertNotEquals(0,orderDTOPage.getTotalElements());
    }

    @Test
    public void paid() {
        OrderDTO orderDTO = orderService.findOne("1546487796058655114");
        OrderDTO result = orderService.paid(orderDTO);
        Assert.assertEquals(PayStatus.SUCCESS.getCode(),result.getPayStatus());
    }

    @Test
    public void cancel() {
        OrderDTO orderDTO = orderService.findOne("1546496965365283237");
        orderService.cancel(orderDTO);
        log.info("---------【查询订单完成】----------- result={}",orderDTO);
        Assert.assertEquals(OrderStatusEnum.CANCEL.getCode(),orderDTO.getOrderStatus());
    }

    @Test
    public void finish() {
        OrderDTO orderDTO = orderService.findOne("1546487796058655114");
        OrderDTO result = orderService.finish(orderDTO);
        Assert.assertEquals(OrderStatusEnum.FINISHED.getCode(),result.getOrderStatus());
    }
}
package com.company.sell.repository;

import com.company.sell.dataobject.OrderDetail;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailRepositoryTest {

    @Autowired
    private OrderDetailRepository repository;

    @Test
    public void saveTest(){
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDetailId("1");
        orderDetail.setOrderId("123456");
        orderDetail.setProductId("123");
        orderDetail.setProductName("京酱肉丝");
        orderDetail.setProductIcon("www.baidu.com");
        orderDetail.setProductPrice(new BigDecimal(23));
        orderDetail.setProductQuantity(1);
        OrderDetail result = repository.save(orderDetail);
        Assert.assertNotNull(result);
    }

    @Test
    public void findByOrderId() {
        List<OrderDetail> list = repository.findByOrderId("123456");
        Assert.assertNotNull(list);
    }
}
package com.company.sell.service.impl;

import com.company.sell.Enum.ProductStatus;
import com.company.sell.dataobject.ProductInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceImplTest {

    @Autowired
    private ProductServiceImpl impl;
    @Test
    public void save() {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("12345678910");
        productInfo.setProductName("鸡公煲");
        productInfo.setProductStock(100);
        productInfo.setProductDescription("超级香的鸡公煲");
        productInfo.setProductStatus(1);
        productInfo.setCategoryType(2);
        productInfo.setProductPrice(new BigDecimal(100.2));

        impl.save(productInfo);
    }

    @Test
    public void findByProductStatus() {
       List<ProductInfo> list = impl.findByProductStatus(ProductStatus.UP.getCode());
       Assert.assertNotEquals(0,list.size());
    }

    @Test
    public void findOne() {
        ProductInfo productInfo = impl.findOne("123456789");
        Assert.assertNotNull(productInfo);
    }

    @Test
    public void findAll() {
        PageRequest request = new PageRequest(0,2);
        Page<ProductInfo> list = impl.findAll(request);
        System.out.println(list.getTotalElements());
        System.out.println(list.getTotalPages());
    }
}
package com.company.sell.repository;

import com.company.sell.dataobject.ProductInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoRepositoryTest {
    @Autowired
    private ProductInfoRepository repository;
    @Test
    public void save(){
       /* ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("123456789");
        productInfo.setProductName("黄焖鸡米饭");
        productInfo.setProductStock(100);
        productInfo.setProductDescription("杨铭宇黄焖鸡米饭");
        productInfo.setProductStatus(1);
        productInfo.setCategoryType(1);
        productInfo.setProductPrice(new BigDecimal(100.2));

        repository.save(productInfo);*/
    }

    @Test
    public void findByProductStatus() {
        List<ProductInfo> list = repository.findByProductStatus(1);
        Assert.assertNotEquals(0,list.size());
    }
}
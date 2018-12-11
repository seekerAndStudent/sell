package com.company.sell.repository;

import com.company.sell.dataobject.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryRepositoryTest {

    @Autowired
    private ProductCategoryRepository repository;

    @Test
    @Transactional
    public void findOneTest(){
        ProductCategory productCategory = repository.getOne(1);

        System.out.print(productCategory.toString());
    }
    @Test
    //@Transactional
    public void saveTest(){
//        ProductCategory productCategory = new ProductCategory();
//        productCategory.setCategoryId(3);
//        productCategory.setCategoryName("ddddd");
//        productCategory.setCategoryType(2);
//        repository.save(productCategory);
        ProductCategory productCategory =  new ProductCategory("淘宝爆款",1);
        //测试是否为null
        ProductCategory result = repository.save(productCategory);
        Assert.assertNotNull(result);
    }

    @Test
    public void findByCategoryTypeIn(){
        List<Integer> list = Arrays.asList(2,3,4);
        List<ProductCategory> result = repository.findByCategoryTypeIn(list);
        Assert.assertNotNull(result);
    }
}
package com.company.sell.service.impl;

import com.company.sell.dataobject.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryServiceImplTest {

    @Autowired
    private CategoryServiceImpl impl;
    @Test
    public void findOne() throws Exception {
        ProductCategory productCategory = impl.findOne(1);
        System.out.println();
        System.out.print(productCategory.toString());
        System.out.println();
        Assert.assertEquals(new Integer(1),productCategory.getCategoryId());
    }

    @Test
    public void findAll()throws Exception {
        List<ProductCategory> list = impl.findAll();
        Assert.assertNotEquals(0,list.size());
    }

    @Test
    public void findByCategoryTypeIn() throws Exception {
        List<ProductCategory> list = impl.findByCategoryTypeIn(Arrays.asList(1,2,3));
        Assert.assertNotEquals(0,list.size());
    }

    @Test
    public void save() throws Exception {
        ProductCategory productCategory = impl.save(new ProductCategory("京东爆款",2));
        Assert.assertNotNull(productCategory);

    }
}
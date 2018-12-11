package com.company.sell.repository;

import com.company.sell.dataobject.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//JpaRepository<ProductCategory,Integer>数据类型以及主键类型
public interface ProductCategoryRepository extends JpaRepository<ProductCategory,Integer> {

    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryList);


}

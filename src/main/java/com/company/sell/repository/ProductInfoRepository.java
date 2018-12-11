package com.company.sell.repository;

import com.company.sell.dataobject.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductInfoRepository extends JpaRepository<ProductInfo,String> {
    /* 通过商品状态查询 */
    List<ProductInfo> findByProductStatus(Integer productStatus);
}

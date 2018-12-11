package com.company.sell.service;

import com.company.sell.dataobject.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    ProductInfo save(ProductInfo productInfo);
    List<ProductInfo> findByProductStatus(Integer productStatus);
    ProductInfo findOne(String productId);
    Page<ProductInfo> findAll(Pageable pageable);
}

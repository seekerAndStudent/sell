package com.company.sell.service;

import com.company.sell.dataobject.ProductInfo;
import com.company.sell.dto.CartDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    ProductInfo save(ProductInfo productInfo);
    List<ProductInfo> findByProductStatus(Integer productStatus);
    ProductInfo findOne(String productId);
    Page<ProductInfo> findAll(Pageable pageable);

    /*增加库存*/
    void increaseStock(List<CartDTO> cartDTOList);
    /*删除库存*/
    void decreaseStock(List<CartDTO> cartDTOList);
}

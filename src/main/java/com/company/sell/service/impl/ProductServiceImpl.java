package com.company.sell.service.impl;

import com.company.sell.dataobject.ProductInfo;
import com.company.sell.repository.ProductInfoRepository;
import com.company.sell.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductInfoRepository repository;

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return repository.save(productInfo);
    }

    @Override
    public List<ProductInfo> findByProductStatus(Integer productStatus) {
        return repository.findByProductStatus(productStatus);
    }

    @Override
    public ProductInfo findOne(String productId) {
        return repository.getOne(productId);
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }
}

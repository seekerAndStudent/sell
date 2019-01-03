package com.company.sell.service.impl;

import com.company.sell.Enum.ResultEnum;
import com.company.sell.dataobject.ProductInfo;
import com.company.sell.dto.CartDTO;
import com.company.sell.exception.SellException;
import com.company.sell.repository.ProductInfoRepository;
import com.company.sell.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    //加库存
    public void increaseStock(List<CartDTO> cartDTOList) {

    }

    @Override
    //删库存,Transactional是事务的注解
    @Transactional
    public void decreaseStock(List<CartDTO> cartDTOList) {
        for(CartDTO cartDTO : cartDTOList){
            ProductInfo productInfo = repository.getOne(cartDTO.getProductId());
            //看数据库是否有这个商品
            if(productInfo==null){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            //如果有的话，进行减库存的操作
            Integer resultNum = productInfo.getProductStock() - cartDTO.getProductQuantity();
            if(resultNum<0){
                throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
            }
            productInfo.setProductStock(resultNum);
            repository.save(productInfo);
        }
    }
}

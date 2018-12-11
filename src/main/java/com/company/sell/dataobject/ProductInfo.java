package com.company.sell.dataobject;

import lombok.Data;
import org.hibernate.annotations.Proxy;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
@Entity
@Data
@Proxy(lazy = false)
public class ProductInfo {

    @Id
    private String productId;
    private String productName;
    private BigDecimal productPrice;
    /* 商品库存*/
    private Integer productStock;
    /* 商品描述*/
    private String productDescription;
    /* 商品小图*/
    private String productIcon;
    /* 商品状态*/
    private Integer productStatus;
    /* 商品类目*/
    private Integer categoryType;

}

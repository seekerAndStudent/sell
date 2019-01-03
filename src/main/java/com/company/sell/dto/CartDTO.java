package com.company.sell.dto;

import lombok.Getter;
/*用来传输商品ID和商品数量 */
@Getter
public class CartDTO {
    private String productId;
    private Integer productQuantity;

    public CartDTO(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}

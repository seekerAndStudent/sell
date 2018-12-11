package com.company.sell.Enum;

import lombok.Getter;

@Getter
public enum ProductStatus {
    UP(1,"上架中"),
    DOWN(0,"已下架")
    ;
    private Integer code;
    private String message;

    ProductStatus(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}

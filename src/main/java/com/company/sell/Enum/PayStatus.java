package com.company.sell.Enum;

import lombok.Getter;

@Getter
public enum PayStatus {
    WAIT(0,"等待支付"),
    SUCCESS(1,"支付成功"),
    ;
    private Integer code;
    private String message;

    PayStatus(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}

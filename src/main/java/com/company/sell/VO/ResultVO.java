package com.company.sell.VO;

import lombok.Data;

@Data
public class ResultVO<T> {
    /* 错误码*/
    private Integer code;
    /* 具体信息*/
    private String msg;

    private T data;

}

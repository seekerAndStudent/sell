package com.company.sell.dto;

import com.company.sell.dataobject.OrderDetail;
import com.company.sell.utils.serializer.DateToLongSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
//@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDTO {
    //订单ID
    private String orderId;
    //买家姓名
    private String buyerName;
    //买家电话
    private String buyerPhone;
    //买家地址
    private String buyerAddress;
    //买家微信openId
    private String buyerOpenid;
    //订单总金额
    private BigDecimal buyerAmount;
    //默认是新订单
    private Integer orderStatus;
    //默认是等待支付
    private Integer payStatus;
    @JsonSerialize(using = DateToLongSerializer.class)
    private Date createTime;
    @JsonSerialize(using = DateToLongSerializer.class)
    private Date updateTime;

    List<OrderDetail> orderDetailList;
}

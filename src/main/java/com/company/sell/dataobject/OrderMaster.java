package com.company.sell.dataobject;

import com.company.sell.Enum.OrderStatusEnum;
import com.company.sell.Enum.PayStatus;
import lombok.Data;
import org.hibernate.annotations.Proxy;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@Proxy(lazy = false)
public class OrderMaster {
    //订单ID
    @Id
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
    private Integer orderStatus = OrderStatusEnum.NEW.getCode();
    //默认是等待支付
    private Integer payStatus = PayStatus.WAIT.getCode();
    private Date createTime;
    private Date updateTime;
}

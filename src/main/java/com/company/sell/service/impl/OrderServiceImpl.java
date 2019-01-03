package com.company.sell.service.impl;
/*
* 这里是订单的处理
* */
import com.company.sell.Enum.OrderStatusEnum;
import com.company.sell.Enum.PayStatus;
import com.company.sell.Enum.ResultEnum;
import com.company.sell.dataobject.OrderDetail;
import com.company.sell.dataobject.OrderMaster;
import com.company.sell.dataobject.ProductInfo;
import com.company.sell.dto.CartDTO;
import com.company.sell.dto.OrderDTO;
import com.company.sell.exception.SellException;
import com.company.sell.repository.OrderDetailRepository;
import com.company.sell.repository.OrderMasterRepository;
import com.company.sell.service.OrderService;
import com.company.sell.service.ProductService;
import com.company.sell.utils.KeyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private OrderMasterRepository orderMasterRepository;

    /* 创建订单*/
    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {
        //设定订单的ID,因为一个主订单对应多个订单详情，所以主订单ID唯一
        String orderId = KeyUtil.genUniqueKey();
        //用来累加商品总价
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);
        /* 1.查询商品信息*/
        for(OrderDetail orderDetail : orderDTO.getOrderDetailList()){
            //根据商品id查询一下，如果为Null证明商品不存在，抛出异常
            ProductInfo productInfo = productService.findOne(orderDetail.getProductId());
            if(productInfo==null){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            //计算总价格
            orderAmount = productInfo.getProductPrice()
                                     .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                                     .add(orderAmount);
            //将订单详情插入数据库

            BeanUtils.copyProperties(productInfo,orderDetail);
            orderDetail.setDetailId(KeyUtil.genUniqueKey());
            orderDetail.setOrderId(orderId);
            orderDetailRepository.save(orderDetail);
        }
        /* 2.计算总价格*/

        /* 3.将主订单插入数据库*/
        //首先将传进来的DTO复制给master再填入orderid和Amount
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);
        orderMaster.setOrderId(orderId);
        orderMaster.setBuyerAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatus.WAIT.getCode());
        orderMasterRepository.save(orderMaster);
        /* 4.扣除订单的库存*/
        //有两种方式，一种是首先定义一个List然后将cartDTO一个一个装进去，
        // 一种是lambda表达式，这里使用的是lambda表达式
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream()
                                            .map(e -> new CartDTO(e.getProductId(),e.getProductQuantity()))
                                            .collect(Collectors.toList());

        productService.decreaseStock(cartDTOList);
        return orderDTO;
    }

    @Override
    public OrderDTO findOne(String openId) {
        return null;
    }

    @Override
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {
        return null;
    }

    @Override
    public OrderDTO paid(OrderDTO orderDTO) {
        return null;
    }

    @Override
    public OrderDTO cancel(OrderDTO orderDTO) {
        return null;
    }

    @Override
    public OrderDTO finish(OrderDTO orderDTO) {
        return null;
    }
}

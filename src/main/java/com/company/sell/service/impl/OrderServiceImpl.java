package com.company.sell.service.impl;
/*
* 这里是订单的处理
* */
import com.company.sell.Enum.OrderStatusEnum;
import com.company.sell.Enum.PayStatus;
import com.company.sell.Enum.ResultEnum;
import com.company.sell.converter.OrderMasterToOrderDTOConverter;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
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
        //因为拷贝会将null值也拷贝给orderMaster所以，OrderStatus和PayStatus需要重新赋值
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

    /*查询一个订单*/
    @Override
    public OrderDTO findOne(String orderId) {
        //首先查询订单主表
        OrderMaster orderMaster = orderMasterRepository.getOne(orderId);
        if(orderMaster==null){
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        //查询订单详情表
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);
        if(CollectionUtils.isEmpty(orderDetailList)){
            throw new SellException(ResultEnum.ORDERDETAIL_NOT_EXIST);
        }
        //将查询出来的值给orderDTO
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster,orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);

        return orderDTO;
    }
    /*查询一个订单列表，分页*/
    @Override
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findByBuyerOpenid(buyerOpenid,pageable);
        List<OrderDTO> orderDTOList = OrderMasterToOrderDTOConverter.converter(orderMasterPage.getContent());
        return new PageImpl<OrderDTO>(orderDTOList,pageable,orderMasterPage.getTotalElements());
    }


    @Override
    public OrderDTO paid(OrderDTO orderDTO) {
        return null;
    }

    @Override
    public OrderDTO cancel(OrderDTO orderDTO) {
        OrderMaster orderMaster = new OrderMaster();
        // 查询订单状态
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.info("【订单取消失败，订单状态不正确】，orderDTO={}",orderDTO);
            throw new SellException(ResultEnum.ORDERDETAIL_NOT_EXIST);
        }
        //修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster resultOrderMaster = orderMasterRepository.save(orderMaster);
        if(resultOrderMaster==null){
            log.info("【订单状态更新失败】，orderMaster={}",orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        //返还库存
        //首先检查订单详情是不是为空
        if(CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.info("【取消订单失败，订单详情为空】，orderDTO={}",orderDTO);
            throw new SellException(ResultEnum.ORDERDETAIL_NOT_EXIST);
        }
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList()
                                            .stream()
                                            .map(e -> new CartDTO(e.getProductId(),e.getProductQuantity()))
                                            .collect(Collectors.toList());
        productService.increaseStock(cartDTOList);
        //如果已经付款，退钱
        if(orderDTO.getPayStatus().equals(PayStatus.SUCCESS.getCode())){
            //TODO
        }
        return orderDTO;
    }

    @Override
    public OrderDTO finish(OrderDTO orderDTO) {
        return null;
    }
}

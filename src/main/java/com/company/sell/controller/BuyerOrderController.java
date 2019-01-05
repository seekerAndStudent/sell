package com.company.sell.controller;

import com.company.sell.Enum.ResultEnum;
import com.company.sell.VO.ResultVO;
import com.company.sell.converter.OrderFormToOrderDTOConverter;
import com.company.sell.dto.OrderDTO;
import com.company.sell.exception.SellException;
import com.company.sell.form.OrderForm;
import com.company.sell.service.BuyerService;
import com.company.sell.service.OrderService;
import com.company.sell.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private BuyerService buyerService;
    //创建订单
    @PostMapping("/create")
    public ResultVO<Map<String,String>> creat(@Valid OrderForm orderForm, BindingResult bindingResult){
        //是否有返回的错误信息
        if (bindingResult.hasErrors()){
            log.error("【创建订单失败，参数不正确】，orderForm={}",orderForm);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }
        OrderDTO orderDTO = OrderFormToOrderDTOConverter.conver(orderForm);
        if(CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.error("【创建订单失败，购物车不能为空】");
            throw new SellException(ResultEnum.CART_EMPTY);
        }
        OrderDTO resultOrderDTO = orderService.create(orderDTO);
        Map<String,String> map = new HashMap<>();
        map.put("orderId",resultOrderDTO.getOrderId());
        return ResultVOUtil.success(map);
    }
    //查询订单列表
    @GetMapping("/list")
    public ResultVO<List<OrderDTO>> list (@RequestParam("openid") String openid,
                                          @RequestParam(value = "page",defaultValue = "0") Integer page,
                                          @RequestParam(value = "size",defaultValue = "10") Integer size){
        //首先检查openid是否存在
        if(StringUtils.isEmpty(openid)){
            log.error("【查询订单列表失败，openid为空】");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        PageRequest pageRequest = PageRequest.of(page,size);
        Page<OrderDTO> resultPage = orderService.findList(openid,pageRequest);
        return ResultVOUtil.success(resultPage.getContent());
    }
    //查询订单详情
    @GetMapping("/detail")
    public ResultVO<OrderDTO> detail(@RequestParam("openid") String openid,
                                     @RequestParam("orderId") String orderId){
        OrderDTO orderDTO = buyerService.findOrderOne(openid,orderId);
        return ResultVOUtil.success(orderDTO);
    }
    //取消订单
    @PostMapping("/cancel")
    public ResultVO cancel(@RequestParam("openid") String openid,
                           @RequestParam("orderId") String orderId){
        buyerService.cancelOrder(openid,orderId);
        return ResultVOUtil.success();
    }
    //完成订单
}

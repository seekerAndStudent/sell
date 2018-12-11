package com.company.sell.controller;

import com.company.sell.Enum.ProductStatus;
import com.company.sell.VO.CategoryVO;
import com.company.sell.VO.ProductVO;
import com.company.sell.VO.ResultVO;
import com.company.sell.dataobject.ProductCategory;
import com.company.sell.dataobject.ProductInfo;
import com.company.sell.service.CategoryService;
import com.company.sell.service.ProductService;
import com.company.sell.utils.ResultVOUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public ResultVO list(){
        //1.查询状态为上架的商品
        List<ProductInfo> productInfoList = productService.findByProductStatus(ProductStatus.UP.getCode());
        //2.查询商品类目
        //传统方法是循环遍历productInfoList查询商品类目
        //精简方法
        List<Integer> categoryTypeList =
        productInfoList.stream().map(e -> e.getCategoryType()).collect(Collectors.toList());

        List<ProductCategory> productCategoryList = categoryService.findByCategoryTypeIn(categoryTypeList);
        //3.进行拼接
        List<CategoryVO> categoryVOList = new ArrayList<>();
        for(ProductCategory category : productCategoryList){
            CategoryVO categoryVO = new CategoryVO();
            categoryVO.setCategoryType(category.getCategoryType());
            categoryVO.setCategoryName(category.getCategoryName());
            //通过循环，将productInfo装入list
            List<ProductVO> productVOList = new ArrayList<>();
            for(ProductInfo productInfo : productInfoList){
                if(productInfo.getCategoryType().equals(category.getCategoryType())){
                    ProductVO productVO = new ProductVO();
                    BeanUtils.copyProperties(productInfo,productVO);
                    productVOList.add(productVO);
                }
            }
            categoryVO.setProductVOLists(productVOList);
            categoryVOList.add(categoryVO);
        }

        return ResultVOUtil.success(categoryVOList);
    }
}

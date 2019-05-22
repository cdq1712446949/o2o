package com.cdq.o2o.service;

import com.cdq.o2o.dto.ProductCategoryExecution;
import com.cdq.o2o.entity.ProductCategory;
import com.cdq.o2o.exceptions.ProductCategoryException;

import java.util.List;

public interface ProductCategoryService {

    /**
     * 通过shopId获取商品分类列表
     * @param shopId
     * @return
     */
    List<ProductCategory> getProductCategoryList(Long shopId);

    /**
     * 批量添加产品类别
     * @param list
     * @return
     * @throws ProductCategoryException
     */
    ProductCategoryExecution batchAddProductCategory(List<ProductCategory> list) throws ProductCategoryException;

    /**
     * 根据productCategoryId和shopid删除productCategory
     * @param productCategoryId
     * @param shopId
     * @return
     */
    ProductCategoryExecution deleteProductCategory(Long productCategoryId,Long shopId) throws ProductCategoryException;

}

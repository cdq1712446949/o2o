package com.cdq.o2o.service;

import com.cdq.o2o.dto.ImageHolder;
import com.cdq.o2o.dto.ProductExecution;
import com.cdq.o2o.entity.Product;
import com.cdq.o2o.exceptions.ProductException;
import java.util.List;

public interface ProductService {

    /**
     *
     * @param product
     * @param imageHolder
     * @param imageHolderList
     * @return
     * @throws ProductException
     */
    ProductExecution addProduct(Product product, ImageHolder imageHolder, List<ImageHolder> imageHolderList)
            throws ProductException;

    /**
     *
     * @param productId
     * @return
     */
    ProductExecution getProductById(Long productId);

    /**
     * 根据shopId获取商品列表
     * @param shopId
     * @return
     */
    ProductExecution getProductList(Long shopId);

}

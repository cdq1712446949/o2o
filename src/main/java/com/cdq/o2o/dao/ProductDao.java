package com.cdq.o2o.dao;

import com.cdq.o2o.entity.Product;

import java.util.List;

public interface ProductDao {

    /**
     * 插入产品记录
     * @param product
     * @return
     */
    int insertProduct(Product product);

    /**
     * 查询商品信息，查询条件：productId,productName,enableStatus,productCategoryId,shopId
     * @param product
     * @return
     */
    Product queryProduct(Product product);

    /**
     * 通过productId查询商品信息
     * @param productId
     * @return
     */
    Product queryProductByProductId(Long productId);

    /**
     * 通过shopid选取商品列表
     * @param shopId
     * @return
     */
    List<Product> queryProductList(Long shopId);

    /**
     * 修改商品信息
     * @param product
     * @return
     */
    int modifyProduct(Product product);

    /**
     * 删除商品
     * @param productId
     * @return
     */
    int deleteProduct(Long productId);

}

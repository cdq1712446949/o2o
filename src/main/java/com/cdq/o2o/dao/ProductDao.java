package com.cdq.o2o.dao;

import com.cdq.o2o.entity.Product;
import org.apache.ibatis.annotations.Param;

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
     * 分页获取商品列表，查询条件：shopId,productCategoryId,productName,enableStatus
     * @return
     */
    List<Product> queryProductList(@Param("productCondition") Product productCondition
            ,@Param("rowIndex")int rowIndex,@Param("pageSize")int pageSize);

    /**
     * 获取queryProductList总数
     * @param productCondition
     * @return
     */
    int queryListCount(@Param("productCondition") Product productCondition);

    /**
     * 修改商品信息,shop.shopId不能为空,productId不能为空
     * @param product
     * @return 影响行数
     */
    int modifyProduct(Product product);

    /**
     * 删除商品
     * @param productId
     * @return
     */
    int deleteProduct(Long productId);

}

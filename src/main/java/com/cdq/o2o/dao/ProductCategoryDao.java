package com.cdq.o2o.dao;

import com.cdq.o2o.entity.ProductCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductCategoryDao {

    /**
     * 通过shopId查询产品类别列表
     * @param shopId
     * @return
     */
    List<ProductCategory> queryProduceCategory(Long shopId);

    /**
     * 批量添加商品类别对象
     * @return
     */
    int batchInsertProductCategory(List<ProductCategory> list);

    /**
     * 店铺类别删除
     * @param productCategoryId
     * @return 影响行数
     */
    int deleteProductCategory(@Param("productCategoryId") Long productCategoryId,@Param("shopId")Long shopId);

}

package com.cdq.o2o.dao;

import com.cdq.o2o.entity.ProductImg;

import java.util.List;

public interface ProductImgDao {

    /**
     * 批量添加商品详细图片
     * @param list
     * @return
     */
    int batchInsertProductImg(List<ProductImg> list);

    /**
     * 根骨productId删除productImg
     * @param productId
     * @return
     */
    int deleteProductImgByProductId(Long productId);

}

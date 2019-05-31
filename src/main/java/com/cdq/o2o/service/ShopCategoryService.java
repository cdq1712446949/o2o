package com.cdq.o2o.service;

import com.cdq.o2o.entity.Shop;
import com.cdq.o2o.entity.ShopCategory;

import java.io.IOException;
import java.util.List;

public interface ShopCategoryService {

    /**
     * 获取所有店铺类别
     * @return
     */
    List<ShopCategory> getShopCategory(ShopCategory shopCategory);

    /**
     *
     * @return
     * @throws IOException
     */
    List<ShopCategory> getAllSecondLevelShopCategory(ShopCategory shopCategory) throws IOException;

}

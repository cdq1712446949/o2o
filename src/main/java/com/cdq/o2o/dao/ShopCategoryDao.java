package com.cdq.o2o.dao;

import com.cdq.o2o.entity.ShopCategory;

import java.util.List;

public interface ShopCategoryDao {

    /**
     * 获取所有店铺类别
     * @return
     */
    List<ShopCategory> getShopCategory();


}

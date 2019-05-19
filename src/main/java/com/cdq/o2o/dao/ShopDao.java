package com.cdq.o2o.dao;

import com.cdq.o2o.entity.Shop;

public interface ShopDao {

    /**
     * 新增店铺
     * @param shop
     * @return 影响行数
     */
    int insertShop(Shop shop);

    /**
     * 更新店铺信息
     * @param shop
     * @return 影响行数
     */
    int updateShop(Shop shop);

}

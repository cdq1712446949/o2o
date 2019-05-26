package com.cdq.o2o.service;

import com.cdq.o2o.dto.ImageHolder;
import com.cdq.o2o.dto.ShopExecution;
import com.cdq.o2o.entity.Shop;
import com.cdq.o2o.exceptions.ShopOperationException;

import java.io.InputStream;

public interface ShopService {

    /**
     * 分页获取店铺列表
     * @param shopCondition
     * @param rowIndex
     * @param pageSize
     * @return
     */
    ShopExecution getShopList(Shop shopCondition,int rowIndex,int pageSize);

    /**
     * 通过shopId查询店铺信息
     *
     * @param shopId
     * @return
     */
    Shop getShopById(Long shopId);

    /**
     * 修改店铺信息
     * @param shop
     * @return
     * @throws ShopOperationException
     */
    ShopExecution modifyShop(Shop shop, ImageHolder imageHolder) throws ShopOperationException;

    /**
     * 添加店铺信息
     *
     * @param shop
     * @return
     */
    ShopExecution addShop(Shop shop, ImageHolder imageHolder);

}

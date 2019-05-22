package com.cdq.o2o.dao;

import com.cdq.o2o.entity.Shop;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShopDao {

    /**
     * 分页查询店铺列表,可输入的条件：店铺名（模糊查询）,店铺状态，店铺类别，owner
     * @param shopCondition
     * @param rowIndex  从第几行开始提取数据
     * @param pageSize  提取多少行数据
     * @return
     */
    List<Shop> queryShopList(@Param("shopCondition")Shop shopCondition,
                             @Param("rowIndex")int rowIndex,@Param("pageSize")int pageSize);

    /**
     * 返回queryShopList总数
     * @param shopCondition
     * @return
     */
    int queryShopCount(@Param("shopCondition")Shop shopCondition);

    /**
     * 通过id查询单个店铺信息
     * @param shopId
     * @return
     */
    Shop queryShopById(Long shopId);

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

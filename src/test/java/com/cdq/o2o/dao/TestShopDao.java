package com.cdq.o2o.dao;

import com.cdq.o2o.entity.Area;
import com.cdq.o2o.entity.Shop;
import com.cdq.o2o.entity.ShopCategory;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

import static net.sf.ezmorph.test.ArrayAssertions.assertEquals;

public class TestShopDao extends BaseTest {

    @Autowired
    private ShopDao shopDao;

    @Test
    @Ignore
    public void testInsertShop(){
        Shop shop = new Shop();
        shop.setOwnerId(8L);
        Area area = new Area();
        area.setAreaId(4);
        ShopCategory sc = new ShopCategory();
        sc.setShopCategoryId(10);
        shop.setShopName("mytest1");
        shop.setShopDesc("mytest1");
        shop.setShopAddr("testaddr1");
        shop.setPhone("13810524526");
        shop.setShopImg("test1");
        shop.setLongitude(1D);
        shop.setLatitude(1D);
        shop.setCreateTime(new Date());
        shop.setLastEditTime(new Date());
        shop.setEnableStatus(0);
        shop.setAdvice("审核中");
        shop.setArea(area);
        shop.setShopCategory(sc);
        int effectedNum = shopDao.insertShop(shop);
        assertEquals(1, effectedNum);
    }

    @Test
    public void testUpdateShop(){
        Shop shop = new Shop();
        shop.setShopId(30L);
        shop.setOwnerId(8L);
        Area area = new Area();
        area.setAreaId(4);
        ShopCategory sc = new ShopCategory();
        sc.setShopCategoryId(10);
        shop.setShopName("测试姓名");
        shop.setShopDesc("测试描述");
        shop.setShopAddr("测试地址");
        shop.setPhone("测试电话");
        int effectedNum = shopDao.updateShop(shop);
        assertEquals(1, effectedNum);
    }

}

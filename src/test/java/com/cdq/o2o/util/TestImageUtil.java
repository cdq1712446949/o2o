package com.cdq.o2o.util;

import com.cdq.o2o.dao.BaseTest;
import com.cdq.o2o.entity.Shop;
import com.cdq.o2o.service.ShopService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class TestImageUtil extends BaseTest {

    @Autowired
    private ShopService shopService;

    @Test
    public void testDeleteFileOrPath(){
        Shop shop = shopService.getShopById(66L);
        String imgPath=shop.getShopImg();
        ImageUtil.deleteFileOrPath(imgPath);
    }

}

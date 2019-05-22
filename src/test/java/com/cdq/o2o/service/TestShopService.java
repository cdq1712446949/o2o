package com.cdq.o2o.service;

import com.cdq.o2o.dao.BaseTest;
import com.cdq.o2o.dto.ShopExecution;
import com.cdq.o2o.entity.Area;
import com.cdq.o2o.entity.Shop;
import com.cdq.o2o.entity.ShopCategory;
import com.cdq.o2o.enums.ShopStateEnum;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;

import static junit.framework.Assert.assertEquals;

public class TestShopService extends BaseTest {

    @Autowired
    private ShopService shopService;

    @Test
    public void testGetSopList(){
        Shop shop=new Shop();
        ShopCategory shopCategory=new ShopCategory();
        shopCategory.setShopCategoryId(10);
        shop.setShopCategory(shopCategory);
        ShopExecution se=shopService.getShopList(shop,1,2);
        System.out.println("总数："+se.getCount());
    }

    @Test
    public void testModifyShop() throws Exception {
        Shop shop = shopService.getShopById(66L);
        shop.setShopName("修改后的名称");
        File file = new File("D:\\projectdev\\iamge\\cat.jpg");
        InputStream ins = new FileInputStream(file);
        ShopExecution se = shopService.modifyShop(shop, ins, "cat.jpg");
        System.out.println(se.getShop().getShopName());
    }

    @Test
    @Ignore
    public void testAddShop() throws FileNotFoundException {
        Shop shop = new Shop();
        shop.setOwnerId(8L);
        Area area = new Area();
        area.setAreaId(4);
        ShopCategory sc = new ShopCategory();
        sc.setShopCategoryId(10);
        shop.setShopName("mytest12222");
        shop.setShopDesc("mytest1222");
        shop.setShopAddr("testaddr1222");
        shop.setPhone("13810524526");
        shop.setCreateTime(new Date());
        shop.setLastEditTime(new Date());
        shop.setEnableStatus(ShopStateEnum.CHECK.getStatus());
        shop.setAdvice("审核中");
        File file = new File("D:\\projectdev\\iamge\\cat.jpg");
        InputStream inputStream = new FileInputStream(file);
        ShopExecution se = shopService.addShop(shop, inputStream, file.getName());
        assertEquals(ShopStateEnum.CHECK.getStatus(), se.getStatus());

    }

}

package com.cdq.o2o.util;

import com.cdq.o2o.dao.BaseTest;
import com.cdq.o2o.dto.ImageHolder;
import com.cdq.o2o.entity.Shop;
import com.cdq.o2o.service.ShopService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class TestImageUtil extends BaseTest {

    @Autowired
    private ShopService shopService;

    @Test
    public void testDeleteFileOrPath(){
        Shop shop = shopService.getShopById(66L);
        String imgPath=shop.getShopImg();
        ImageUtil.deleteFileOrPath(imgPath);
    }

    @Test
    public void testGenerateNormalImg() throws FileNotFoundException {
        File file1 = new File("D:\\projectdev\\iamge\\cat.jpg");
        InputStream fis1 = new FileInputStream(file1);
        ImageHolder imageHolder=new ImageHolder("cat.jpg",fis1);
        String path=ImageUtil.generateNormalImg(imageHolder,PathUtil.getShopImagePath(66L));
    }

}

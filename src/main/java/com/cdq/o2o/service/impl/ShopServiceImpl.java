package com.cdq.o2o.service.impl;

import com.cdq.o2o.dao.ShopDao;
import com.cdq.o2o.dto.ShopExecution;
import com.cdq.o2o.entity.Shop;
import com.cdq.o2o.enums.ShopStateEnum;
import com.cdq.o2o.exceptions.ShopOperationException;
import com.cdq.o2o.service.ShopService;
import com.cdq.o2o.util.ImageUtil;
import com.cdq.o2o.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.InputStream;
import java.util.Date;

@Service
public class ShopServiceImpl implements ShopService {

    @Autowired
    private ShopDao shopDao;

    @Override
    @Transactional
    public ShopExecution addShop(Shop shop, InputStream image,String fileName) {
        //判断传入参数是否为空
        if (shop == null) {
            return new ShopExecution(ShopStateEnum.NULL_SHOPID);
        }
        try {
            //给店铺赋初试值
            shop.setEnableStatus(0);
            shop.setCreateTime(new Date());
            shop.setLastEditTime(new Date());
            //添加店铺
            int effectNum = shopDao.insertShop(shop);
            if (effectNum <= 0) {
                throw new ShopOperationException("店铺创建失败");
            } else {
                //添加店铺图片
                if (image != null) {
                    try {
                        //1.图片存储  2.返回图片url并赋值给shop对象
                        addShopImage(shop, image, fileName);
                    } catch (Exception e) {
                        throw new ShopOperationException("addShopImageError:" + e.toString());
                    }
                    effectNum = shopDao.updateShop(shop);
                    if (effectNum <= 0) {
                        throw new ShopOperationException("更新店铺失败");
                    }
                }
            }
        } catch (Exception e) {
            //必须抛出ShopOperationException才可以中断事务
            throw new ShopOperationException("addShopError:" + e.getMessage());
        }
        return new ShopExecution(ShopStateEnum.CHECK, shop);
    }

    private void addShopImage(Shop shop, InputStream image,String fileName) {
        //获取图片绝对路径
        String dest = PathUtil.getShopImagePath(shop.getShopId());
        System.out.println(dest);
        String shopImageAddr = ImageUtil.generateThumbnails(image, dest,fileName);
        shop.setShopImg(shopImageAddr);
    }
}

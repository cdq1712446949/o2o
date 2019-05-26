package com.cdq.o2o.service.impl;

import com.cdq.o2o.dao.ShopDao;
import com.cdq.o2o.dto.ImageHolder;
import com.cdq.o2o.dto.ShopExecution;
import com.cdq.o2o.entity.Shop;
import com.cdq.o2o.enums.ShopStateEnum;
import com.cdq.o2o.exceptions.ShopOperationException;
import com.cdq.o2o.service.ShopService;
import com.cdq.o2o.util.ImageUtil;
import com.cdq.o2o.util.PageCalculator;
import com.cdq.o2o.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

@Service
public class ShopServiceImpl implements ShopService {

    @Autowired
    private ShopDao shopDao;

    @Override
    public ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize) {
        int rowIndex=PageCalculator.calculatorRowIndex(pageIndex,pageSize);
        List<Shop> shopList=shopDao.queryShopList(shopCondition,rowIndex,pageSize);
        int count=shopDao.queryShopCount(shopCondition);
        ShopExecution se=new ShopExecution();
        if (shopList!=null){
            se.setShopList(shopList);
            se.setCount(count);
        }else{
            se.setStatus(ShopStateEnum.INNER_ERROR.getStatus());
        }
        return se;
    }

    @Override
    public Shop getShopById(Long shopId) {
        return shopDao.queryShopById(shopId);
    }

    @Override
    public ShopExecution modifyShop(Shop shop,ImageHolder imageHolder)
            throws ShopOperationException {
        //判断shop非空
        if (shop == null || shop.getShopId() == null) {
            return new ShopExecution(ShopStateEnum.NULL_SHOP);
        } else {
            try {
                //1.判断是否需要修改图片
                if (imageHolder.getImgName() != null && imageHolder.getInputStream() != null && !"".equals(imageHolder.getImgName())) {
                    Shop tempShop = shopDao.queryShopById(shop.getShopId());
                    //判断店铺图片非空
                    if (shop.getShopImg() != null) {
                        ImageUtil.deleteFileOrPath(tempShop.getShopImg());
                    }
                    addShopImage(shop, imageHolder);
                }
                //2.修改店铺信息
                shop.setLastEditTime(new Date());
                int effectNum = shopDao.updateShop(shop);
                if (effectNum <= 0) {
                    return new ShopExecution(ShopStateEnum.INNER_ERROR);
                } else {
                    shop = shopDao.queryShopById(shop.getShopId());
                    return new ShopExecution(ShopStateEnum.SUCCESS, shop);
                }
            } catch (Exception e) {
                throw new ShopOperationException("modify Shop Error:" + e.getMessage());
            }
        }
    }

    @Override
    @Transactional
    public ShopExecution addShop(Shop shop,ImageHolder imageHolder) {
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
                if (imageHolder.getInputStream() != null) {
                    try {
                        //1.图片存储  2.返回图片url并赋值给shop对象
                        addShopImage(shop, imageHolder);
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

    private void addShopImage(Shop shop, ImageHolder imageHolder) {
        //获取图片绝对路径
        String dest = PathUtil.getShopImagePath(shop.getShopId());
        String shopImageAddr = ImageUtil.generateThumbnails(imageHolder, dest);
        shop.setShopImg(shopImageAddr);
    }
}

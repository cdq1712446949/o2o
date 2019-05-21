package com.cdq.o2o.service.impl;

import com.cdq.o2o.dao.ShopCategoryDao;
import com.cdq.o2o.entity.ShopCategory;
import com.cdq.o2o.service.ShopCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShopCategoryServiceImpl implements ShopCategoryService {

    @Autowired
    private ShopCategoryDao shopCategoryDao;

    @Override
    public List<ShopCategory> getShopCategory() {
        List<ShopCategory> list = new ArrayList<>();
        list=shopCategoryDao.getShopCategory();
        return list;
    }

    @Override
    public List<ShopCategory> getAllSecondLevelShopCategory() {
        List<ShopCategory> list = new ArrayList<>();
        list=shopCategoryDao.getShopCategory();
        return list;
    }
}

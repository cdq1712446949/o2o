package com.cdq.o2o.dao;

import com.cdq.o2o.entity.ProductCategory;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//@FixMethodOrder自定义调试方法的运行顺序(根据字母顺序（abcd....）)
public class TestProductCategory extends BaseTest {

    @Autowired
    private ProductCategoryDao productCategoryDao;

    @Test
    public void testDeleteProductCategory(){
        int effectNum=productCategoryDao.deleteProductCategory(18L,16L);
        System.out.println("影响行数："+effectNum);
    }

    @Test
    public void testQueryProductCategory(){
        List<ProductCategory> list=productCategoryDao.queryProduceCategory(20L);
        System.out.println(list.size());
    }

    @Test
    public void testInsetProductCategory(){
        ProductCategory p1=new ProductCategory();
        p1.setPriority(10);
        p1.setCreateTime(new Date());
        p1.setLastEditTime(new Date());
        p1.setProductCategoryName("测试");
        p1.setShopId(66L);
        ProductCategory p2=new ProductCategory();
        p2.setPriority(13);
        p2.setCreateTime(new Date());
        p2.setLastEditTime(new Date());
        p2.setProductCategoryName("ceshi");
        p2.setShopId(66L);
        List<ProductCategory> list=new ArrayList<>();
        list.add(p1);
        list.add(p2);
        int en=productCategoryDao.batchInsertProductCategory(list);
        System.out.println("影响行数："+en);
    }

}

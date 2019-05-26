package com.cdq.o2o.dao;

import com.cdq.o2o.entity.Product;
import com.cdq.o2o.entity.ProductCategory;
import com.cdq.o2o.entity.Shop;
import com.cdq.o2o.entity.ShopCategory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;

import java.util.Date;
import java.util.List;

public class TestProductDao extends BaseTest {

    @Autowired
    private ProductDao productDao;

    @Test
    public void testInserProduct(){
        Product product=new Product();
        Shop shop=new Shop();
        shop.setShopId(66L);
        ProductCategory pc=new ProductCategory();
        pc.setProductCategoryId(21L);
        product.setCreateTime(new Date());
        product.setEnableStatus(1);
        product.setLastEditTime(new Date());
        product.setNormalPrice("22");
        product.setProductName("测试商品");
        product.setProductDesc("测试商品简介");
        product.setShop(shop);
        product.setProductCategory(pc);
        product.setPriority(22);
        int en=productDao.insertProduct(product);
        System.out.println("影响行数："+en);
    }

    @Test
    public void testProduct(){
        Product product=productDao.queryProductByProductId(17L);
        System.out.println(product.getProductName());
    }

    @Test
    public void testQueryProductList(){
        List<Product> list=productDao.queryProductList(15L);
        System.out.println("商品数量："+list.size());
    }

}

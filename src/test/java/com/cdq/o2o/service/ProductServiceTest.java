package com.cdq.o2o.service;

import com.cdq.o2o.dao.BaseTest;
import com.cdq.o2o.dto.ImageHolder;
import com.cdq.o2o.dto.ProductExecution;
import com.cdq.o2o.entity.Product;
import com.cdq.o2o.entity.ProductCategory;
import com.cdq.o2o.entity.Shop;
import com.cdq.o2o.exceptions.ProductException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductServiceTest extends BaseTest {

    @Autowired
    private ProductService productService;

    @Test
    public void testAddProductImgList() throws ProductException, FileNotFoundException {
        Product product = new Product();
        product.setCreateTime(new Date());
        product.setEnableStatus(1);
        product.setLastEditTime(new Date());
        product.setNormalPrice("22");
        product.setProductName("测试商品");
        product.setProductDesc("测试商品简介");
        product.setPriority(22);
        product.setProductId(16L);
        Shop shop = new Shop();
        shop.setShopId(66L);
        ProductCategory pc = new ProductCategory();
        pc.setProductCategoryId(20L);
        product.setProductCategory(pc);
        product.setShop(shop);
        File file1 = new File("D:\\projectdev\\iamge\\cat.jpg");
        InputStream fis1 = new FileInputStream(file1);
        File file2 = new File("D:\\projectdev\\iamge\\xingkong.jpg");
        InputStream fis2 = new FileInputStream(file2);
        File file3 = new File("D:\\projectdev\\iamge\\sky.jpg");
        InputStream fis3 = new FileInputStream(file3);
        ImageHolder imageHolder1 = new ImageHolder("cat.jpg", fis1);
        ImageHolder imageHolder2 = new ImageHolder("xingkong.jpg", fis2);
        ImageHolder imageHolder3 = new ImageHolder("sky.jpg", fis3);
        List<ImageHolder> list = new ArrayList<>();
        list.add(imageHolder1);
        list.add(imageHolder2);
        ProductExecution pe = productService.addProduct(product, imageHolder3, list);
        System.out.println(pe.getStateInfo());
    }

    @Test
    public void testModifyProduct() throws Exception{
        Product product = new Product();
        product.setNormalPrice("22");
        product.setProductName("正式商品");
        product.setProductDesc("商品简介");
        product.setProductId(16L);
        Shop shop = new Shop();
        shop.setShopId(66L);
        product.setShop(shop);
        File file1 = new File("D:\\projectdev\\iamge\\cat.jpg");
        InputStream fis1 = new FileInputStream(file1);
        File file2 = new File("D:\\projectdev\\iamge\\xingkong.jpg");
        InputStream fis2 = new FileInputStream(file2);
        File file3 = new File("D:\\projectdev\\iamge\\sky.jpg");
        InputStream fis3 = new FileInputStream(file3);
        ImageHolder imageHolder1 = new ImageHolder("cat.jpg", fis1);
        ImageHolder imageHolder2 = new ImageHolder("xingkong.jpg", fis2);
        ImageHolder imageHolder3 = new ImageHolder("sky.jpg", fis3);
        List<ImageHolder> list = new ArrayList<>();
        list.add(imageHolder1);
        list.add(imageHolder2);
        ProductExecution pe = productService.modifyProduct( product, imageHolder3, list);
        System.out.println(pe.getStateInfo());
    }
    @Test
    public void testFindProductById(){
        ProductExecution pe=productService.getProductById(22L);
        System.out.println(pe.getStateInfo());
    }

    @Test
    public void testGetProductList(){
        Product productCondition = new Product();
        Shop shop=new Shop();
        shop.setShopId(15L);
        productCondition.setShop(shop);
        ProductExecution productExecutio=productService.getProductList(productCondition,2,2);
        System.out.println("pe.getStateInfo:"+productExecutio.getStateInfo());
    }

}

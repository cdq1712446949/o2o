package com.cdq.o2o.dao;

import com.cdq.o2o.entity.ProductImg;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestProductImgDao extends BaseTest {

    @Autowired
    private ProductImgDao productImgDao;

    @Test
    public void testInsertProductImg(){
        ProductImg pi1=new ProductImg();
        pi1.setCreateTime(new Date());
        pi1.setImgAddr("测试地址");
        pi1.setImgDesc("描述测试");
        pi1.setPriority(22);
        pi1.setProductId(16L);
        ProductImg pi2=new ProductImg();
        pi2.setCreateTime(new Date());
        pi2.setImgAddr("测试地址");
        pi2.setImgDesc("描述测试");
        pi2.setPriority(25);
        pi2.setProductId(16L);
        List<ProductImg> list=new ArrayList<>();
        list.add(pi1);
        list.add(pi2);
        int en=productImgDao.batchInsertProductImg(list);
        System.out.println("影响行数："+en);
    }

    @Test
    public void testDeleteProductImg(){
        int en=productImgDao.deleteProductImgByProductId(16L);
        System.out.println("影响行数："+en);
    }

}

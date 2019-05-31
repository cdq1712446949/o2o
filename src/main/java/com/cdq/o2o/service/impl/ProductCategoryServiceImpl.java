package com.cdq.o2o.service.impl;

import com.cdq.o2o.dao.ProductCategoryDao;
import com.cdq.o2o.dao.ProductDao;
import com.cdq.o2o.dto.ProductCategoryExecution;
import com.cdq.o2o.entity.ProductCategory;
import com.cdq.o2o.enums.ProductCategoryStateEnum;
import com.cdq.o2o.exceptions.ProductCategoryException;
import com.cdq.o2o.exceptions.ProductException;
import com.cdq.o2o.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Autowired
    private ProductCategoryDao productCategoryDao;
    @Autowired
    private ProductDao productDao;

    @Override
    public List<ProductCategory> getProductCategoryList(Long shopId) {
        return productCategoryDao.queryProduceCategory(shopId);
    }

    @Override
    @Transactional
    public ProductCategoryExecution batchAddProductCategory(List<ProductCategory> list) throws ProductCategoryException {
        //判断列表是否为空，try catch中执行插入代码
        if (list != null && list.size() != 0) {
            //为list里面的记录添加创建时间
            for (ProductCategory pc:list){
                pc.setCreateTime(new Date());
                pc.setLastEditTime(new Date());
            }
            try {
                int effectNum = productCategoryDao.batchInsertProductCategory(list);
                if (effectNum>0){
                    return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS,list);
                }else{
                    throw new ProductCategoryException("店铺类别创建失败");
                }
            } catch (Exception e) {
                throw  new ProductCategoryException("batchAddProductCategory error:"+e.getMessage());
            }
        } else {
            return new ProductCategoryExecution(ProductCategoryStateEnum.EMPTY_LIST);
        }
    }

    @Override
    @Transactional
    public ProductCategoryExecution deleteProductCategory(Long productCategoryId, Long shopId) throws ProductCategoryException {
        //解除tb_product和tb_product_category的关联
        try{
            int en=productDao.updateProductCatgegoryToNull(productCategoryId);
            if (en<1){
                throw new ProductCategoryException("商品类别删除失败");
            }
        }catch (Exception e){
            throw new ProductCategoryException("商品类别删除失败"+e.toString());
        }
        //删除tb_product数据
        try{
            int effectNum=productCategoryDao.deleteProductCategory(productCategoryId,shopId);
            if (effectNum<=0){
                throw new ProductCategoryException("添加失败");
            }else{
                return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
            }
        }catch (Exception e){
            throw new ProductCategoryException("deleteProductCategory error:"+e.getMessage());
        }
    }


}

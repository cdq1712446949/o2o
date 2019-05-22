package com.cdq.o2o.dto;

import com.cdq.o2o.entity.ProductCategory;
import com.cdq.o2o.enums.ProductCategoryStateEnum;

import java.util.List;

public class ProductCategoryExecution {

    //状态标识
    private int state;
    //状态信息
    private String stateInfo;
    //产品类别列表
    private List<ProductCategory> productCategoryList;

    public ProductCategoryExecution(){}

    //操作失败时使用的构造器
    public ProductCategoryExecution(ProductCategoryStateEnum ps){
        this.state=ps.getState();
        this.stateInfo=ps.getStateInfo();
    }

    //操作成功时使用的构造器
    public ProductCategoryExecution(ProductCategoryStateEnum ps,List<ProductCategory> list) {
        this.productCategoryList=list;
        this.stateInfo=ps.getStateInfo();
        this.state=ps.getState();
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public List<ProductCategory> getProductCategoryList() {
        return productCategoryList;
    }
}

package com.cdq.o2o.dto;

import com.cdq.o2o.entity.Product;
import com.cdq.o2o.enums.ProductStateEnum;

import java.util.List;

public class ProductExecution {

    //状态信息
    private String stateInfo;
    //状态值
    private int state;
    //商品数量
    private int count;
    //操作的商品
    private Product product;
    //操作的商品列表
    private List<Product> productList;

    public ProductExecution() {

    }

    //失败时使用的构造器
    public ProductExecution(ProductStateEnum pse) {
        this.state = pse.getState();
        this.stateInfo = pse.getStateInfo();
    }

    public ProductExecution(ProductStateEnum pse, Product product) {
        this.state = pse.getState();
        this.stateInfo = pse.getStateInfo();
        this.product=product;
    }

    public ProductExecution(ProductStateEnum pse,List<Product> products) {
        this.state = pse.getState();
        this.stateInfo = pse.getStateInfo();
        this.productList=products;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public int getState() {
        return state;
    }

    public int getCount() {
        return count;
    }

    public Product getProduct() {
        return product;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setCount(int count) {
        this.count = count;
    }
}

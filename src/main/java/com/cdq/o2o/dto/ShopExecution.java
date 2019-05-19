package com.cdq.o2o.dto;

import com.cdq.o2o.entity.Shop;
import com.cdq.o2o.enums.ShopStateEnum;

import java.util.List;

public class ShopExecution {

    //结果标识
    private int status;

    //状态标识
    private String statusInfo;

    //店铺数量
    private int count;

    //操作的shop（作用于增删改店铺）
    private Shop shop;

    //操作的店铺列表（作用于查询）
    private List<Shop> shopList;

    public ShopExecution(){

    }

    //店铺操作失败时的构造器
    public ShopExecution(ShopStateEnum shopStateEnum){
        this.status=shopStateEnum.getStatus();
        this.statusInfo=shopStateEnum.getStatusInfo();
    }

    //店铺操作成功时的构造器
    public ShopExecution(ShopStateEnum shopStateEnum,Shop shop){
        this.status=shopStateEnum.getStatus();
        this.statusInfo=shopStateEnum.getStatusInfo();
        this.shop=shop;
    }

    //店铺操作成功时的构造器
    public ShopExecution(ShopStateEnum shopStateEnum,List<Shop> list){
        this.status=shopStateEnum.getStatus();
        this.statusInfo=shopStateEnum.getStatusInfo();
        this.shopList=list;
    }

    public int getStatus() {
        return status;
    }

    public String getStatusInfo() {
        return statusInfo;
    }

    public int getCount() {
        return count;
    }

    public Shop getShop() {
        return shop;
    }

    public List<Shop> getShopList() {
        return shopList;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setStatusInfo(String statusInfo) {
        this.statusInfo = statusInfo;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public void setShopList(List<Shop> shopList) {
        this.shopList = shopList;
    }
}

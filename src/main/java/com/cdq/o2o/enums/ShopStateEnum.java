package com.cdq.o2o.enums;

public enum ShopStateEnum {
    CHECK(0, "审核中"), OFFLINE(-1, "非法店铺"), SUCCESS(1, "操作成功"),
    PASS(2, "通过认证"), INNER_ERROR(-1001, "内部系统错误"), NULL_SHOPID(-1002, "shopId为空"),
    NULL_SHOP(-1003,"店铺信息为空");

    private int status;
    private String statusInfo;

    private ShopStateEnum(int status, String statusInfo) {
        this.status = status;
        this.statusInfo = statusInfo;
    }

    /**
     * 根据状态值返回状态信息
     * @param status
     * @return ShopStatusEnum
     */
    public static ShopStateEnum statusOf(int status) {
        for (ShopStateEnum shopStateEnum : values()){
            if (shopStateEnum.getStatus()==status){
                return shopStateEnum;
            }
        }
        return null;
    }

    public int getStatus() {
        return status;
    }

    public String getStatusInfo() {
        return statusInfo;
    }
}

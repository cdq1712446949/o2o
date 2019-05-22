package com.cdq.o2o.exceptions;

/**
 * 对数据库的增删改都用事务管理，如果发生错误回退，需要抛出RuntimeException
 */

public class ShopOperationException extends RuntimeException {

    public ShopOperationException(String msg){
        super(msg);
    }

}

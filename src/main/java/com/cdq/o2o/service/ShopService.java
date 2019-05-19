package com.cdq.o2o.service;

import com.cdq.o2o.dto.ShopExecution;
import com.cdq.o2o.entity.Shop;
import java.io.InputStream;

public interface ShopService {

    ShopExecution addShop(Shop shop, InputStream image,String fileName);

}

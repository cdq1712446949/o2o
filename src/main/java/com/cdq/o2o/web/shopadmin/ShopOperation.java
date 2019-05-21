package com.cdq.o2o.web.shopadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/shopadmin", method = RequestMethod.GET)
public class ShopOperation {

    @RequestMapping(value = "/shopoperation")
    public String shopOperation() {
        return "shop/shopoperation";       //spring-web.xml中定义了html路径
    }

}
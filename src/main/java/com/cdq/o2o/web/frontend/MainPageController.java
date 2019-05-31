package com.cdq.o2o.web.frontend;

import com.cdq.o2o.entity.HeadLine;
import com.cdq.o2o.entity.ShopCategory;
import com.cdq.o2o.service.HeadLineService;
import com.cdq.o2o.service.ShopCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/frontend")
@Controller
public class MainPageController {

    @Autowired
    private ShopCategoryService shopCategoryService;
    @Autowired
    private HeadLineService headLineService;

    @RequestMapping(value = "/listmainpageinfo" , method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> listMainPageInfo(HttpServletRequest request){
        Map<String,Object> modelMap=new HashMap<>();
        List<ShopCategory> shopCategories=new ArrayList<>();
        List<HeadLine> headLines=new ArrayList<>();
        //获取一级店铺列表
        try{
            shopCategories=shopCategoryService.getShopCategory(null);
            modelMap.put("shopCategoryList",shopCategories);
        }catch (Exception e){
            modelMap.put("success",false);
            modelMap.put("errMsg",e.toString());
            return modelMap;
        }
        //获取头条列表
        try{
            HeadLine headLineCondition=new HeadLine();
            headLineCondition.setEnableStatus(1);
            headLines=headLineService.getHeadLineList(headLineCondition);
            modelMap.put("success",true);
            modelMap.put("headLineList",headLines);
        }catch (Exception e){
            modelMap.put("success",false);
            modelMap.put("errMsg",e.toString());
            return modelMap;
        }
        return modelMap;
    }

}

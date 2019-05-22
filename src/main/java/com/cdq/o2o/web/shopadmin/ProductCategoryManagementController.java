package com.cdq.o2o.web.shopadmin;

import com.cdq.o2o.dto.ProductCategoryExecution;
import com.cdq.o2o.dto.Result;
import com.cdq.o2o.entity.ProductCategory;
import com.cdq.o2o.entity.Shop;
import com.cdq.o2o.enums.ProductCategoryStateEnum;
import com.cdq.o2o.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//路由设置为shopadmin表示这是店家管理系统下的操作
@Controller
@RequestMapping(value = "/shopadmin")
public class ProductCategoryManagementController {

    @Autowired
    private ProductCategoryService productCategoryService;

    @RequestMapping(value = "/deleteporductcategory",method = RequestMethod.POST)
    @ResponseBody
    private Map<String,Object> deletePorductCategory(Long productCategoryId,HttpServletRequest request){
        Map<String,Object> modelMap=new HashMap<>();
        if (productCategoryId!=null&&productCategoryId>0){
            try{
                Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
                ProductCategoryExecution pce=productCategoryService.deleteProductCategory(productCategoryId,currentShop.getShopId());
                if (pce.getState()==ProductCategoryStateEnum.SUCCESS.getState()){
                    modelMap.put("success",true);
                }else{
                    modelMap.put("success",false);
                    modelMap.put("errMsg",pce.getStateInfo());
                }
            }catch (Exception e){
                modelMap.put("success",false);
                modelMap.put("errMsg",e.getMessage());
            }
        }else{
            modelMap.put("success",false);
            modelMap.put("errMsg","请选择商品类型");
        }
        return modelMap;
    }

    /**
     * 批量添加产品类别
     * @param productCategoryList
     * @param request
     * @return
     */
    @RequestMapping(value = "/addproductcategorys",method = RequestMethod.POST)
    @ResponseBody
    private Map<String,Object> addProductCategorys(@RequestBody List<ProductCategory> productCategoryList,
                                                   HttpServletRequest request){
        Map<String,Object> modelMap=new HashMap<>();
        Shop currentShop=(Shop) request.getSession().getAttribute("currentShop");
        for (ProductCategory pc:productCategoryList){
            pc.setShopId(currentShop.getShopId());
        }
        if (productCategoryList!=null&&productCategoryList.size()!=0){
            try{
                ProductCategoryExecution pce=productCategoryService.batchAddProductCategory(productCategoryList);
                if (pce.getState()==ProductCategoryStateEnum.SUCCESS.getState()){
                    modelMap.put("success",true);
                }else{
                    modelMap.put("success",false);
                    modelMap.put("errMsg","添加失败");
                }
            }catch (Exception e){
                modelMap.put("success",false);
                modelMap.put("errMsg",e.getMessage());
                return modelMap;
            }
        }else{
            modelMap.put("success",false);
            modelMap.put("errMsg","至少添加一条");
        }
        return modelMap;
    }

    //ResponseBody是表示将返回结果转换为json对象
    @RequestMapping(value = "/getproductcategorylist", method = RequestMethod.GET)
    @ResponseBody
    private Result<List<ProductCategory>> getProductCategoryList(HttpServletRequest request) {
        List<ProductCategory> productCategoryList=new ArrayList<>();

        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        if (currentShop!=null&&currentShop.getShopId()!=null){
            productCategoryList=productCategoryService.getProductCategoryList(currentShop.getShopId());
            return new Result<List<ProductCategory>>(true,productCategoryList);
        }else {
            ProductCategoryStateEnum ps=ProductCategoryStateEnum.INNER_ERROR;
            return new Result<List<ProductCategory>>(false,ps.getState(),ps.getStateInfo());
        }
    }

}

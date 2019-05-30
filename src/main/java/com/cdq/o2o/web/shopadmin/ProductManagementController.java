package com.cdq.o2o.web.shopadmin;

import com.cdq.o2o.dto.ImageHolder;
import com.cdq.o2o.dto.ProductExecution;
import com.cdq.o2o.entity.Product;
import com.cdq.o2o.entity.Shop;
import com.cdq.o2o.enums.ProductStateEnum;
import com.cdq.o2o.service.ProductService;
import com.cdq.o2o.util.CodeUtil;
import com.cdq.o2o.util.HttpServletRequestUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/shopadmin")
public class ProductManagementController {

    @Autowired
    ProductService productService;

    private static int MAXIMAGECONTENT = 6;

    /**
     * 获取商品链表
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/getproductlist", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getProductList(HttpServletRequest request) {
        //TODO 测试用数据，前端获取
        Product productCondition = new Product();

        Map<String, Object> modelMap = new HashMap<>();
        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");

        productCondition.setShop(currentShop);

        if (currentShop == null || currentShop.getShopId() == 0) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请选择店铺");
        } else {
            try {
                ProductExecution pe = productService.getProductList(productCondition, 1, 10);
                if (pe.getState() == ProductStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                    modelMap.put("productList", pe.getProductList());
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", pe.getStateInfo());
                }
            } catch (Exception e) {
                modelMap.put("success", true);
                modelMap.put("errMsg", e.toString());
            }
        }
        return modelMap;
    }

    @RequestMapping(value = "/modifyproduct", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> modifyProduct(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        //判断是否是下架请求
        boolean statusChange = HttpServletRequestUtil.getBoolean(request, "statusChange");
        if (!statusChange&&!CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "验证码错误");
            return modelMap;
        }
        //声明接收参数，解析文件流的变量
        ObjectMapper objectMapper = new ObjectMapper();
        String productStr = HttpServletRequestUtil.getString(request, "productStr");
        if (productStr == null) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入商品信息");
            return modelMap;
        }
        List<ImageHolder> imageHolderList = new ArrayList<>();
        ImageHolder imageHolder = null;
        Product product = new Product();
        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        if (statusChange){
            try{
                product=objectMapper.readValue(productStr,Product.class);
                if (currentShop != null && currentShop.getShopId() != null) {
                    product.setShop(currentShop);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", "请选择要操作的店铺");
                    return modelMap;
                }
                ProductExecution pe=productService.modifyProduct(product,null,null);
                if (pe.getState()==ProductStateEnum.SUCCESS.getState()){
                    modelMap.put("success",true );
                    return modelMap;
                }else {
                    modelMap.put("success",false );
                    modelMap.put("errMsg",pe.getStateInfo());
                    return modelMap;
                }
            }catch (Exception e){
                modelMap.put("success",false );
                modelMap.put("errMsg",e.toString());
                return modelMap;
            }
        }
        try {
            //如果request中具备文件流执行对文件流的操作
            if (commonsMultipartResolver.isMultipart(request)) {
                //是否有缩略图文件流
                MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
                CommonsMultipartFile thumbnail = (CommonsMultipartFile) multipartHttpServletRequest.getFile("thumbnail");
                imageHolder = new ImageHolder(thumbnail.getOriginalFilename(), thumbnail.getInputStream());
                //是否有详情图文件流
                for (int i = 0; i < MAXIMAGECONTENT; i++) {
                    CommonsMultipartFile thumbnails = (CommonsMultipartFile) multipartHttpServletRequest.getFile("productImg" + i);
                    if (thumbnails != null) {
                        ImageHolder imageHolder1 = new ImageHolder(thumbnails.getOriginalFilename(), thumbnails.getInputStream());
                        imageHolderList.add(imageHolder1);
                    }
                }
            }
            //productStr字符串转换成product对象
            product = objectMapper.readValue(productStr, Product.class);
            if (currentShop != null && currentShop.getShopId() != null) {
                product.setShop(currentShop);
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", "请选择要操作的店铺");
                return modelMap;
            }
            //调用service层方法
            ProductExecution pe = productService.modifyProduct(product, imageHolder, imageHolderList);
            if (pe.getState() == ProductStateEnum.SUCCESS.getState()) {
                modelMap.put("success", true);
                return modelMap;
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", pe.getStateInfo());
                return modelMap;
            }
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }
    }

    /**
     * @param request
     * @return
     */
    @RequestMapping(value = "/getproductbyid")
    @ResponseBody
    private Map<String, Object> getProductById(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        Long productId = HttpServletRequestUtil.getLong(request, "productId");
        if (productId == 0) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "获取商品信息失败");
            return modelMap;
        }
        try {
            ProductExecution pe = productService.getProductById(productId);
            if (pe.getState() == ProductStateEnum.SUCCESS.getState()) {
                modelMap.put("success", true);
                modelMap.put("product", pe.getProduct());
                return modelMap;
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", pe.getStateInfo());
                return modelMap;
            }
        } catch (Exception e) {
            modelMap.put("success", true);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
    }

    /**
     * @param request
     * @return
     */
    @RequestMapping(value = "/addproduct", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> addProduct(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        Product product = new Product();
        //验证验证码
        if (!CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "验证码错误");
            return modelMap;
        }
        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        String productStr = HttpServletRequestUtil.getString(request, "productStr");
        List<ImageHolder> imageHolderList = new ArrayList<>();
        //ObjectMapper用来转化model对象
        ObjectMapper objectMapper = new ObjectMapper();
        ImageHolder thumbnail = null;
        MultipartHttpServletRequest multipartRequest = null;
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        try {
            //判断request中是否有文件流
            if (commonsMultipartResolver.isMultipart(request)) {
                //取出商品缩略图
                CommonsMultipartFile thumbnails = null;
                multipartRequest = (MultipartHttpServletRequest) request;
                //取出缩略图并生成ImageHolder
                thumbnails = (CommonsMultipartFile) multipartRequest.getFile("thumbnail");
                if (thumbnails != null) {
                    thumbnail = new ImageHolder(thumbnails.getOriginalFilename(), thumbnails.getInputStream());
                }
                //取出商品详情缩略图
                for (int i = 0; i < MAXIMAGECONTENT; i++) {
                    CommonsMultipartFile productImgFile = (CommonsMultipartFile) multipartRequest.getFile("productImg" + i);
                    if (productImgFile != null) {
                        ImageHolder imageHolder = new ImageHolder(productImgFile.getOriginalFilename(), productImgFile.getInputStream());
                        imageHolderList.add(imageHolder);
                    } else {
                        break;   //终止循环
                    }
                }
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", "上传图片不能为空");
            }
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        try {
            //把json字符串转换成对象
            product = objectMapper.readValue(productStr, Product.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        //非空判断
        if (product != null && thumbnail != null && imageHolderList.size() > 0) {
            try {
                product.setShop(currentShop);
                ProductExecution pe = productService.addProduct(product, thumbnail, imageHolderList);
                if (pe.getState() == ProductStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", pe.getStateInfo());
                }
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
                return modelMap;
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入商品信息");
            return modelMap;
        }
        return modelMap;
    }

}

package com.cdq.o2o.web.shopadmin;

import com.cdq.o2o.dto.ImageHolder;
import com.cdq.o2o.dto.ShopExecution;
import com.cdq.o2o.entity.Area;
import com.cdq.o2o.entity.PersonInfo;
import com.cdq.o2o.entity.Shop;
import com.cdq.o2o.entity.ShopCategory;
import com.cdq.o2o.enums.ShopStateEnum;
import com.cdq.o2o.service.AreaService;
import com.cdq.o2o.service.ShopCategoryService;
import com.cdq.o2o.service.ShopService;
import com.cdq.o2o.util.CodeUtil;
import com.cdq.o2o.util.HttpServletRequestUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/shopadmin")
public class ShopManagementController {

    @Autowired
    private ShopService shopService;

    @Autowired
    private ShopCategoryService shopCategoryService;

    @Autowired
    private AreaService areaService;

    //日志处理思路，创建日志工具类，传递日志信息
//    private Logger logger=LoggerFactory.getLogger(ShopManagementController.class);

    @RequestMapping(value = "/getshopmanagementinfo", method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> getShopManagementInfo(HttpServletRequest request){
        Map<String,Object> modelMap=new HashMap<>();
        long shopId=HttpServletRequestUtil.getLong(request,"shopId");
        if (shopId<=0){
            Object currentShopObj=request.getSession().getAttribute("currentShop");
            if (currentShopObj==null){
                modelMap.put("redirect",true);
                modelMap.put("url","/o2o/shopadmin/shoplist");
            }else{
                Shop currentShop=(Shop)currentShopObj;
                modelMap.put("redirect",false);
                modelMap.put("shopId",currentShop.getShopId());
            }
        }else{
            Shop currentShop=new Shop();
            currentShop.setShopId(shopId);
            request.getSession().setAttribute("currentShop",currentShop);
            modelMap.put("redirect",false);
        }
        return modelMap;
    }

    /**
     * requestMapping中的value值都是小写
     * 注册店铺信息
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/registershop", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> registerShop(HttpServletRequest httpServletRequest) {
        Map<String, Object> modelMap = new HashMap<>();
        //验证验证码
        if (!CodeUtil.checkVerifyCode(httpServletRequest)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "验证码错误!");
            return modelMap;
        }
        //1.接收并转化参数
        String shopStr = HttpServletRequestUtil.getString(httpServletRequest, "shopStr");
        ObjectMapper objectMapper = new ObjectMapper();
        Shop shop = null;
        try {
            shop = objectMapper.readValue(shopStr, Shop.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        //处理图片
        //CommonsMultipartFile是spring能处理的文件流
        CommonsMultipartFile commonsMultipartFile = null;
        CommonsMultipartResolver commonsMultipartResolver =
                new CommonsMultipartResolver(httpServletRequest.getSession().getServletContext());
        //判断是否具备文件流
        if (commonsMultipartResolver.isMultipart(httpServletRequest)) {
            MultipartRequest multipartRequest = (MultipartRequest) httpServletRequest;
            commonsMultipartFile = (CommonsMultipartFile) multipartRequest.getFile("shopImg");
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "上传图片不能为空");
            return modelMap;
        }
        //2.注册店铺
        //判断参数是否为空
        if (shop != null && commonsMultipartFile != null) {
            //方便测试，后期改成在session中获取owner
            PersonInfo owner = (PersonInfo)httpServletRequest.getSession().getAttribute("user");
            shop.setOwner(owner);
            ShopExecution se = null;
            try {
                ImageHolder imageHolder=new ImageHolder(commonsMultipartFile.getOriginalFilename(),commonsMultipartFile.getInputStream());
                se = shopService.addShop(shop,imageHolder);
                if (se.getStatus() == ShopStateEnum.CHECK.getStatus()) {
                    modelMap.put("success", true);
                    //登陆用户可以操作的店铺列表
                    @SuppressWarnings("unchecked")
                    List<Shop> shopList=(List<Shop>) httpServletRequest.getSession().getAttribute("shopList");
                    if (shopList==null||shopList.size()==0){
                        shopList=new ArrayList<>();
                    }
                    shopList.add(shop);
                    httpServletRequest.getSession().setAttribute("shopList",shopList);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", se.getStatusInfo());
                }
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
                System.out.println(e.getMessage());
                return modelMap;
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入店铺信息");
            return modelMap;
        }
        return modelMap;
    }

    /**
     * 根据用户获取店铺列表
     * @param request
     * @return
     */
    @RequestMapping(value = "/getshoplist", method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> getShopList(HttpServletRequest request){
        Map<String,Object> modelMap=new HashMap<>();
        PersonInfo user=new PersonInfo();
        user.setUserId(8L);
        user.setName("test");
        request.getSession().setAttribute("user",user);
        user=(PersonInfo) request.getSession().getAttribute("user");
        try {
            Shop shopCondition=new Shop();
            shopCondition.setOwner(user);
            ShopExecution se=shopService.getShopList(shopCondition,0,100);
            modelMap.put("success",true);
            modelMap.put("user",user);
            modelMap.put("shopList",se.getShopList());
        }catch (Exception e){
            modelMap.put("success",false);
            modelMap.put("errMsg",e.getMessage());
        }
        return modelMap;
    }

    /**
     * 获取店铺分类信息和店铺区域信息
     *
     * @return
     */
    @RequestMapping(value = "/getshopinitinfo", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getShopInitInfo() {
        Map<String, Object> modelMap = new HashMap<>();
        List<ShopCategory> shopCategoryList = new ArrayList<>();
        List<Area> areaList = new ArrayList<>();
        shopCategoryList = shopCategoryService.getShopCategory(null);
        areaList = areaService.getAreaList();
        modelMap.put("shopCategoryList", shopCategoryList);
        modelMap.put("areaList", areaList);
        modelMap.put("success", true);
        return modelMap;
    }

    /**
     * 通过shopId获取单个店铺信息
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/getshopbyid", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getShopById(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        Long shopId = HttpServletRequestUtil.getLong(request, "shopId");
        if (shopId > -1) {
            try {
                Shop shop = shopService.getShopById(shopId);
                List<Area> areaList = areaService.getAreaList();
                modelMap.put("success", true);
                modelMap.put("shop", shop);
                modelMap.put("areaList", areaList);
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "获取失败");
        }
        return modelMap;
    }

    /**
     * 修改店铺信息
     *tomcat默认session过期时间三十分钟
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/modifyshop", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> modifyShop(HttpServletRequest httpServletRequest) {
        Map<String, Object> modelMap = new HashMap<>();
        //验证验证码
        if (!CodeUtil.checkVerifyCode(httpServletRequest)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "验证码错误!");
            return modelMap;
        }
        //1.接收并转化参数
        String shopStr = HttpServletRequestUtil.getString(httpServletRequest, "shopStr");
        ObjectMapper objectMapper = new ObjectMapper();
        Shop shop = null;
        try {
            shop = objectMapper.readValue(shopStr, Shop.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        //处理图片
        //CommonsMultipartFile是spring能处理的文件流
        CommonsMultipartFile commonsMultipartFile = null;
        CommonsMultipartResolver commonsMultipartResolver =
                new CommonsMultipartResolver(httpServletRequest.getSession().getServletContext());
        //判断是否具备文件流
        if (commonsMultipartResolver.isMultipart(httpServletRequest)) {
            MultipartRequest multipartRequest = (MultipartRequest) httpServletRequest;
            commonsMultipartFile = (CommonsMultipartFile) multipartRequest.getFile("shopImg");
        }
        //2.修改店铺信息
        //判断参数是否为空
        if (shop != null && shop.getShopId() != null) {
            ShopExecution se = null;
            try {
                if (commonsMultipartFile == null) {
                    se = shopService.modifyShop(shop, null);
                }else{
                    ImageHolder imageHolder=new ImageHolder(commonsMultipartFile.getOriginalFilename(),commonsMultipartFile.getInputStream());
                    se = shopService.modifyShop(shop,imageHolder);
                }
                if (se.getStatus() == ShopStateEnum.SUCCESS.getStatus()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", se.getStatusInfo());
                }
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
                System.out.println(e.getMessage());
                return modelMap;
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入店铺id");
            return modelMap;
        }
        return modelMap;
    }

//    private static void inputStreamToFile(InputStream ins, File file) {
//        FileOutputStream os = null;
//        try {
//            os = new FileOutputStream(file);
//            int bytesRead = 0;
//            byte[] buffer = new byte[1024];
//            while ((bytesRead = ins.read(buffer)) != -1) {
//                os.write(buffer, 0, bytesRead);
//            }
//        } catch (Exception e) {
//            throw new RuntimeException("调用inputStreamToFile产生异常：" + e.getMessage());
//        } finally {
//            try {
//                if (os != null) {
//                    os.close();
//                }
//                if (ins != null) {
//                    ins.close();
//                }
//            } catch (IOException e) {
//                throw new RuntimeException("inputStreamToFile关闭IO差生异常：" + e.getMessage());
//            }
//        }
//    }

}

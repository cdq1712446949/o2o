package com.cdq.o2o.service.impl;

import com.beust.jcommander.internal.Nullable;
import com.cdq.o2o.dao.ProductDao;
import com.cdq.o2o.dao.ProductImgDao;
import com.cdq.o2o.dto.ImageHolder;
import com.cdq.o2o.dto.ProductExecution;
import com.cdq.o2o.entity.Product;
import com.cdq.o2o.entity.ProductImg;
import com.cdq.o2o.enums.ProductStateEnum;
import com.cdq.o2o.exceptions.ProductException;
import com.cdq.o2o.service.ProductService;
import com.cdq.o2o.util.ImageUtil;
import com.cdq.o2o.util.PageCalculator;
import com.cdq.o2o.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;
    @Autowired
    private ProductImgDao productImgDao;

    /**
     * 添加商品
     * 1.处理缩略图
     * 2.向tb_product表添加数据，获取productId
     * 3.批量处理商品详情图片
     * 4根据productId向tb_product_img添加记录
     *
     * @param product
     * @param imageHolder
     * @param imageHolderList
     * @return
     * @throws ProductException
     */
    @Override
    @Transactional   //了解下spring事务管理
    public ProductExecution addProduct(Product product, ImageHolder imageHolder, List<ImageHolder> imageHolderList)
            throws ProductException {
        //非空判断，product不能为空，product.shop不能为空，product.shop.getShopId()不能为空
        if (product != null && product.getShop() != null && product.getShop().getShopId() != null) {
            //给商品添加默认属性
            product.setCreateTime(new Date());
            product.setLastEditTime(new Date());
            //默认为上架状态
            product.setEnableStatus(1);
            //若是商品缩略图不为空则添加缩略图
            if (imageHolder != null) {
                addThumbnali(product, imageHolder);
            }
            try {
                //创建商品信息
                int en = productDao.insertProduct(product);
                if (en <= 0) {
                    return new ProductExecution(ProductStateEnum.INNER_ERROR);
                }
            } catch (Exception e) {
                throw new ProductException("添加商品失败" + e.toString());
            }
            //判断商品缩略图列表是否为空
            if (imageHolderList != null && imageHolderList.size() > 0) {
                addProductImgList(product, imageHolderList);
            }
            return new ProductExecution(ProductStateEnum.SUCCESS);
        } else {
            return new ProductExecution(ProductStateEnum.EMPTY);
        }
    }

    @Override
    public ProductExecution getProductById(Long productId) {
        ProductExecution pe;
        if (productId != null && productId >= 0) {
            try {
                Product product = productDao.queryProductByProductId(productId);
                if (product != null) {
                    pe = new ProductExecution(ProductStateEnum.SUCCESS, product);
                } else {
                    pe = new ProductExecution(ProductStateEnum.EMPTY);
                }
            } catch (Exception e) {
                pe = new ProductExecution(ProductStateEnum.INNER_ERROR);
            }
        } else {
            pe = new ProductExecution(ProductStateEnum.EMPTY);
        }
        return pe;
    }

    /**
     * 组合查询
     *
     * @param productCondition
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @Override
    public ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize) {
        ProductExecution pe = null;
        List<Product> productList = new ArrayList<>();
        int rowIndex = PageCalculator.calculatorRowIndex(pageIndex, pageSize);
        try {
            productList = productDao.queryProductList(productCondition, rowIndex, pageSize);
            pe = new ProductExecution(ProductStateEnum.SUCCESS, productList);
        } catch (Exception e) {
            pe = new ProductExecution(ProductStateEnum.INNER_ERROR);
        }
        return pe;
    }

    /**
     * 1.非空判断(currentShop在controller层添加进product中)
     * 2.如果缩略图文件流不为空，根据productId在数据库取出完整数据，然后删除该商品缩略图
     * 3.如果详情图片文件流不为空，根据tempProduct删除详情图片
     * 4.更新数据库信息
     *
     * @param product         必须有productId，shop.shopId,修改后的数据
     * @param imageHolder     可以为空
     * @param imageHolderList 可以为空
     * @return
     * @throws ProductException
     */
    @Override
    @Transactional
    public ProductExecution modifyProduct(Product product, @Nullable ImageHolder imageHolder, @Nullable List<ImageHolder> imageHolderList) throws ProductException {
        ProductExecution pe = null;
        Product tempProduct = null;
        if (product != null && product.getShop() != null && product.getShop().getShopId() != null) {
            //给商品赋值
            product.setLastEditTime(new Date());
            //如果缩略图文件流不为空，根据productId在数据库取出完整数据，然后删除该商品缩略图,再为product添加缩略图信息
            if (imageHolder != null && imageHolder.getInputStream() != null) {
                tempProduct = productDao.queryProductByProductId(product.getProductId());
                //根据数据库缩略图信息删除缩略图
                if (tempProduct.getImgAddr() != null) {
                    ImageUtil.deleteFileOrPath(tempProduct.getImgAddr());
                }
                addThumbnali(product, imageHolder);
            }
            //如果详情图片文件流不为空，根据tempProduct删除详情图片
            if (imageHolderList != null && imageHolderList.size() > 0) {
                deleteProductImgList(product.getProductId());
                addProductImgList(product,imageHolderList);
            }
            try{
                int en=productDao.modifyProduct(product);
                if (en>0){
                    pe=new ProductExecution(ProductStateEnum.SUCCESS);
                }else {
                    throw new ProductException("更新商品失败：");
                }
            }catch (Exception e){
                throw new ProductException("修改商品信息出错："+e.toString());
            }
        } else {
            pe = new ProductExecution(ProductStateEnum.EMPTY);
        }
        return pe;
    }

    /**
     * 根据productId删除文件以及数据库记录
     * @param productId
     */
    private void deleteProductImgList(Long productId) {
        //获取图片信息
        List<ProductImg> list=productImgDao.queryProductImgList(productId);
        if (list!=null&&list.size()>0){
            //删除图片
            for (ProductImg pi:list){
                ImageUtil.deleteFileOrPath(pi.getImgAddr());
            }
        }
        //删除数据库记录
        productImgDao.deleteProductImgByProductId(productId);
    }

    private void addProductImgList(Product product, List<ImageHolder> imageHolderList) {
        String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
        List<ProductImg> productImgList = new ArrayList<>();
        for (ImageHolder productImgHolder : imageHolderList) {
            String imgAddr = ImageUtil.generateNormalImg(productImgHolder, dest);
            ProductImg productImg = new ProductImg();
            productImg.setCreateTime(new Date());
            productImg.setProductId(product.getProductId());
            productImg.setImgAddr(imgAddr);
            productImgList.add(productImg);
        }
        //判断ProductImg列表是否为空
        if (productImgList.size() > 0) {
            try {
                int en = productImgDao.batchInsertProductImg(productImgList);
                if (en < 0) {
                    throw new ProductException("创建商品详情缩略图失败");
                }
            } catch (Exception e) {
                throw new ProductException("创建商品详情缩略图失败");
            }
        }
    }

    private void addThumbnali(Product product, ImageHolder imageHolder) {
        String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
        String productImageAddr = ImageUtil.generateThumbnails(imageHolder, dest);
        product.setImgAddr(productImageAddr);
    }
}

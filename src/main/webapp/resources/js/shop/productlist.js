/**
 *
 */
$(function () {
    //获取商品列表url
    var getProductListUrl = '/o2o/shopadmin/getproductlist';
    //编辑商品url
    var modifyProductUrl='/o2o/shopadmin/productoperation?productId=';
    //添加商品url
    var addProductUrl='/o2o/shopadmin/productoperation';
    //下架商品url
    var lowerProductUrl='/o2o/shopadmin/lowerproduct?productId=';
    function initProductList() {
        $.getJSON(getProductListUrl, function (data) {
            if (data.success) {
                var productList = data.productList;
                var temphtml = '';
                productList.map(function (item, index) {
                    temphtml += '<div class="row row-product"><div class="col-40">'
                        + item.productName + '</div><div class="col-20">'
                        + item.priority
                        + '</div><div class="col-40">'
                        + '<a href="'+modifyProductUrl+item.productId+'" class="row-true">编辑</a>'+'|'
                        + '<a href="'+lowerProductUrl+item.productId+'" class="row-true">下架</a>'+'|'
                        + '<a href="'+modifyProductUrl+item.productId+'" class="row-true">编辑</a>'
                        + '</div></div>';
                    $('.product-wrap').append(temphtml);
                });
            } else {
                $.toast("获取商品列表失败" + data.errMsg);
            }
        });
    }
    initProductList();
});
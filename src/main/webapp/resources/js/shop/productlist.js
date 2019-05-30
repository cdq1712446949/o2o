/**
 *
 */
$(function () {
    //获取商品列表url
    var getProductListUrl = '/o2o/shopadmin/getproductlist';
    //编辑商品url
    var modifyProductUrl = '/o2o/shopadmin/productoperation?productId=';
    //添加商品url
    var addProductUrl = '/o2o/shopadmin/productoperation';
    //下架商品url
    var lowerProductUrl = '/o2o/shopadmin/modifyproduct?productId=';
    var enableSta = '&&enableStatus=';
    var statusChange = '&&statusChange=true';
    lowerProduct = function (productId, enableStatus) {
        var formData = new FormData();
        formData.append("statusChange", true);
        var productStr = {
            productId: productId,
            enableStatus: enableStatus
        };
        productStr=JSON.stringify(productStr);
        formData.append("productStr", productStr);
        $.ajax({
            url: lowerProductUrl,
            type: 'POST',
            data: formData,
            contentType: false,
            processData: false,
            cache: false,
            success: function (data) {
                if (data.success) {
                    $.toast("操作成功");
                    initProductList();
                } else {
                    $.toast("操作失败:" + data.errMsg);
                }
            }
        });
    };

    function initProductList() {
        $.getJSON(getProductListUrl, function (data) {
            if (data.success) {
                var productList = data.productList;
                var temphtml = '';
                var isLower = '下架';
                var enableStatus = 0;
                productList.map(function (item, index) {
                    if (item.enableStatus == 0) {
                        isLower = '上架';
                        enableStatus = 1;
                    }else{
                        isLower = '下架';
                        enableStatus = 0;
                    }
                    temphtml += '<div class="row row-product"><div class="col-40">'
                        + item.productName + '</div><div class="col-20">'
                        + item.priority
                        + '</div><div class="col-40">'
                        + '<a href="' + modifyProductUrl + item.productId + '" class="row-true">编辑</a>' + '|'
                        + '<a href="javascript:void(0);" onclick="lowerProduct(' + item.productId + ',' + enableStatus + ')" class="row-true">' + isLower + '</a>' + '|'
                        + '<a href="' + modifyProductUrl + item.productId + '" class="row-true">预览</a>'
                        + '</div></div>';

                });
                $('.product-wrap').empty();
                $('.product-wrap').append(temphtml);
            } else {
                $.toast("获取商品列表失败" + data.errMsg);
            }
        });
    }

    initProductList();

});



/**
 *
 */

$(function () {
    var productId = getQueryString("productId");
    //根据是否有productId选择执行函数
    //获取商品信息utl
    var initPriductUrl = '/o2o/shopadmin/getproductbyid?productId=' + productId;
    //添加商品url
    var addProductUrl = '/o2o/shopadmin/addproduct';
    //修改商品url
    var modifyProductUrl = '/o2o/shopadmin/modifyproduct';
    //获取商品类别列表
    var getCategoryListUrl = '/o2o/shopadmin/getproductcategorylist';
    var isEdit = false;
    if (productId) {
        getProductInfo();
        isEdit = true;
    } else {
        getProductCategoryList();
        isEdit = false;
    }

    function getProductInfo() {
        $.getJSON(initPriductUrl, function (data) {
            if (data.success) {
                var product = data.product;
                $('#product-name').val(product.productName);
                $('#product-desc').val(product.productDesc);
                $('#product-priority').val(product.priority);
                $('#product-normal-price').val(product.normalPrice);
                $('#product-now-price').val(product.promotionPrice);
                var optionSelected = product.productCategory.productCategoryId;
                initProductCategory(optionSelected);
            } else {
                $.toast("获取商品信息失败" + data.errMsg);
            }
        });
    }

    function initProductCategory(optionSelected){
        $.getJSON(getCategoryListUrl, function (data) {
            if (data.success) {
                var productCategoryList = data.data;
                var optionHtml = '';
                var isSelected='selected';
                productCategoryList.map(function (item, index) {
                   if (optionSelected==item.productCategoryId){
                       optionHtml += '<option data-value="'
                           + item.productCategoryId
                           + '"'
                           + isSelected
                           + '>'
                           + item.productCategoryName
                           + '</option>';
                   }else{
                       optionHtml += '<option data-value="'
                           + item.productCategoryId + '">'
                           + item.productCategoryName + '</option>';
                   }
                });
                $('#product-category').html(optionHtml);
            } else {
                $.toast("获取商品类别失败" + data.errMsg);
            }
        });
    }

    function getProductCategoryList() {
        $.getJSON(getCategoryListUrl, function (data) {
            if (data.success) {
                var productCategoryList = data.data;
                var optionHtml = '';
                productCategoryList.map(function (item, index) {
                    optionHtml += '<option data-value="'
                        + item.productCategoryId + '">'
                        + item.productCategoryName + '</option>';
                });
                $('#product-category').html(optionHtml);
            } else {
                $.toast("获取商品类别失败" + data.errMsg);
            }
        });
    }

    $('.detail-img-div').on('change', '.detail-img:last-child', function () {
        if ($('.detail-img').length < 6) {
            $('#detail-img').append('<input type="file" class="detail-img">');
        }
    });

    $('#submit').click(
        function() {
            var product = {};
            product.productName = $('#product-name').val();
            product.productDesc = $('#product-desc').val();
            product.priority = $('#product-priority').val();
            product.normalPrice = $('#product-normal-price').val();
            product.promotionPrice = $('#product-now-price').val();
            product.productCategory = {
                productCategoryId : $('#product-category').find('option').not(
                    function() {
                        return !this.selected;
                    }).data('value')
            };
            product.productId = productId;

            var thumbnail = $('#product-img')[0].files[0];
            console.log(thumbnail);
            var formData = new FormData();
            formData.append('thumbnail', thumbnail);
            $('.detail-img').map(
                function(index, item) {
                    if ($('.detail-img')[index].files.length > 0) {
                        formData.append('productImg' + index,
                            $('.detail-img')[index].files[0]);
                    }
                });
            formData.append('productStr', JSON.stringify(product));
            var verifyCodeActual = $('#j-kaptcha').val();
            if (!verifyCodeActual) {
                $.toast('请输入验证码！');
                return;
            }
            formData.append("verifyCodeActual", verifyCodeActual);
            $.ajax({
                url : isEdit?modifyProductUrl:addProductUrl,
                type : 'POST',
                data : formData,
                contentType : false,
                processData : false,
                cache : false,
                success : function(data) {
                    if (data.success) {
                        $.toast('提交成功！');
                        $('#captcha_img').click();
                    } else {
                        $.toast('提交失败！'+data.errMsg);
                        $('#captcha_img').click();
                    }
                }
            });
        });
});
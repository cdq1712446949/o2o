/**
 *
 */
//开始加载文档后执行
$(function () {
    var shopId = getQueryString("shopId");
    var isEdit = shopId ? true : false;
    var initUrl = '/o2o/shopadmin/getshopinitinfo';
    var registerShopUrl = '/o2o/shopadmin/registershop';
    var modifyShopUrl = '/o2o/shopadmin/modifyshop';
    var shopInfoUrl = '/o2o/shopadmin/getshopbyid?shopId=' + shopId;
    if (isEdit) {
        getShopInfo()
    } else {
        getShopInitInfo();
    }

    //修改店铺获取数据
    function getShopInfo() {
        $.getJSON(shopInfoUrl, function (data) {
            if (data.success) {
                var shop=data.shop;
                $('#shop-name').val(shop.shopName);
                $('#shop-addr').val(shop.shopAddr);
                $('#shop-phone').val(shop.phone);
                $('#shop-desc').val(shop.shopDesc);
                var tempHtml = '';
                var tempAreaHtml = '';
                tempHtml = '<option data-id="'
                    + shop.shopCategory.shopCategoryId + '">'
                    + shop.shopCategory.shopCategoryName + '</option>';
                tempAreaHtml = '<option data-id="'
                    + shop.area.areaId + '">'
                    + shop.area.areaName + '</option>';
                $('#shop-category').attr('disabled', 'disabled');
                $('#shop-category').html(tempHtml);
                $('#area').html(tempAreaHtml);
                $('#area').attr(shop.areaId);
            }
        });
    }

    //注册店铺获取数据
    function getShopInitInfo() {
        $.getJSON(initUrl, function (data) {
            if (data.success) {
                var tempHtml = '';
                var tempAreaHtml = '';
                //.map方法类似于foreach
                data.shopCategoryList.map(function (item, index) {
                    tempHtml += '<option data-id="' + item.shopCategoryId + '">' + item.shopCategoryName + '</option>';
                });
                data.areaList.map(function (item, index) {
                    tempAreaHtml += '<option data-id="' + item.areaId + '">' + item.areaName + '</option>';
                });
                $('#shop-category').html(tempHtml);
                $('#area').html(tempAreaHtml);
            }
        });
    }

    //在某个元素后面添加元素  .after();
    //检查必填元素
    function checkItem() {
        var isSubmit = true;
        if ($("#shop-name").val() == "" || $("#shop-name").val() == null) {
            isSubmit = false;
            $('#shop-name').after('<a style="color: red;font-size: small">*必须填写</a>');
        }
        if ($("#shop-phone").val() == "" || $("#shop-phone").val() == null) {
            isSubmit = false;
            $('#shop-phone').after('<a style="color: red;font-size: small">*必须填写</a>')
        }
        if ($("#shop-addr").val() == "" || $("#shop-addr").val() == null) {
            isSubmit = false;
            $('#shop-addr').after('<a style="color: red;font-size: small">*必须填写</a>')
        }
        if ($("#shop-desc").val() == "" || $("#shop-desc").val() == null) {
            isSubmit = false;
            $('#shop-desc').after('<a style="color: red;font-size: small">*必须填写</a>')
        }
        if ($("#shop-img").val() == "" || $("#shop-img").val() == null) {
            isSubmit = false;
            $('#shop-img').after('<a style="color: red;font-size: small">*必须填写</a>')
        }
        return isSubmit;
    }

    //提交按钮事件
    $('#submit').click(function () {
        var result = checkItem();
        if (!result) {
            return;
        }
        var shop = {};
        shop.shopName = $('#shop-name').val();
        shop.shopAddr = $('#shop-addr').val();
        shop.phone = $('#shop-phone').val();
        shop.shopDesc = $('#shop-desc').val();
        shop.shopCategory = {
            shopCategoryId: $('#shop-category').find('option').not(function () {
                return !this.selected;
            }).data('id')
        }
        shop.area = {
            areaId: $('#area').find('option').not(function () {
                return !this.selected;
            }).data('id')
        };
        if (isEdit){
            shop.shopId=shopId;
        }
        var shopJson = JSON.stringify(shop);
        var shopImg = $('#shop-img')[0].files[0];
        var fd = new FormData();
        fd.append("shopImg", shopImg);
        fd.append("shopStr", shopJson);
        console.log(fd.get('shopStr'));
        var verifyCodeActual = $('#j-kaptcha').val();
        if (!verifyCodeActual) {
            $.toast('请输入验证码');
            return;
        }
        fd.append("verifyCodeActual", verifyCodeActual);
        console.log(fd.get("verifyCodeActual"));
        $.ajax({
            url: (isEdit ? modifyShopUrl : registerShopUrl),
            type: 'POST',
            data: fd,
            contentType: false,
            processData: false,
            cache: false,
            success: function (data) {
                if (data.success) {
                    $.toast("提交成功！");
                } else {
                    $.toast("提交失败" + data.errMsg);
                    $('#kaptcha-img').click()
                }
            }
        });
    });
});
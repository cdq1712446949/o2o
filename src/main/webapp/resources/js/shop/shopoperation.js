/**
 *
 */
//开始加载文档后执行
$(function () {
    var initUrl = '/shopadmin/getshopinitinfo';
    var registerShopUrl = '/shopadmin/registershop';
    alert(initUrl);
    getShopInitInfo();

    function getShopInitInfo() {
        $.getJSON(initUrl, function (data) {
            if (data.success) {
                var tempHtml = '';
                var tempAreaHtml = '';
                //.map方法类似于foreach
                data.shopCategoryList.map(function (item, index) {
                    tempHtml += '<option data-id="' + item.shopCategoryId + '>' + item.shopCategoryName + '</option>';
                });
                data.areaList.map(function (item, index) {
                    tempAreaHtml += '<option data-id="' + item.areaId + '>' + item.areaName + '</option>';
                });
                $('#shop-category').html(tempHtml);
                $('#shop-area').html(tempAreaHtml);
            }
        });
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
            }
            var shopImg = $('#shop-img')[0].files[0];
            var formData = new FormData();
            formData.append("shopImg", shopImg);
            formData.append("shopStr", shop);
            $.ajax({
                url: registerShopUrl,
                type: 'POST',
                data: formData,
                contentType: false,
                proceesData: false,
                cache: false,
                success: function (data) {
                    if (data.success) {
                        $.toast("提交成功！");
                    } else {
                        $.toast("提交失败" + data.errMsg);
                    }
                }
            })
        });
        //在某个元素后面添加元素  .after();
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
    }
});
var data = {};

var s = new Swiper('.shortcut-container',{
    grabCursor: true,
    mousewheel: true,
    centeredSlides: true,
    slidesPerView: 'auto',
    on: {
        slideChangeTransitionStart: function () {
            selectCate(this.activeIndex);
        }
    }
});

var c = new Swiper('.bookmark-category-container', {
    mousewheel: true,
    slidesPerView: 5,
    on: {
        tap: function () {
            $('.bookmark-category-item-active').removeClass('bookmark-category-item-active');
            this.slides[this.clickedIndex].classList.add('bookmark-category-item-active');
            selectBook(this.clickedIndex);
        }
    }
});

function selectCate (index) {
    if (!data.shortcutList) return;
    var target = data.shortcutList[index].bookmarkCategoryId;
    for (var i = 0; i < data.bookmarkCategoryList.length; i++) {
        if (data.bookmarkCategoryList[i].bookmarkCategoryId === target) {
            $('.bookmark-category-item-active').removeClass('bookmark-category-item-active');
            c.slides[i].classList.add('bookmark-category-item-active');
        }
    }
}

function selectBook (index) {
    if (!data.bookmarkCategoryList) return;
    var target = data.bookmarkCategoryList[index].bookmarkCategoryId;
    for (var i = 0; i < data.shortcutList.length; i++) {
        if (data.shortcutList[i].bookmarkCategoryId === target) {
            s.slideTo(i);
            break;
        }
    }
}

$.ajax({
    url: '/bookmark/init',
    type: 'post',
    success: function (data) {
        if (data.code === '000') {
            window.data = data.result;
        }
    }
});

layui.use(['layer', 'form', 'laydate'], function (){
    var layer = layui.layer
        ,form = layui.form
        ,laydate = layui.laydate;

    function setForm ($form, result, parent) {
        var value;
        for (i in result) {
            if (result.hasOwnProperty(i)) {
                value = result[i];
                if (typeof value == 'object' && value.constructor === Object) {
                    setForm($form, value, i);
                } else {
                    if (parent.length > 0) {
                        $form.find('[name="' + parent + '.' + i + '"]').val(value);
                    } else {
                        var ele = $form.find('[name="' + i + '"]');
                        if (ele.prop('type') === 'text') {
                            ele.val(value);
                        }
                        if (ele.prop('type') === 'checkbox') {
                            if (value === 1) {
                                ele.prop('checked', true);
                            } else {
                                ele.prop('checked', false);
                            }
                        }
                    }
                }
            }
        }
    }

    $('.addBookmark').click(function () {
        layer.open({
            type: 1,
            title: '书签',
            closeBtn: 0,
            shadeClose: true,
            area: ['600px', '420px'],
            content: $('.bookmark-form')
        });
    });

    laydate.render({
        elem: '#createDate'
        ,type: 'datetime'
    });

    //监听提交
    form.on('submit', function (data){
        layer.msg(JSON.stringify(data.field));
        $.ajax({
            url: '/bookmark/add',
            type: 'post',
            data: data.field,
            success: function (data) {
                if (data.code === '000') {
                    setForm($('.bookmark-form'), data.result, '');
                    form.render();
                }
            }
        });
        return false;
    });
});
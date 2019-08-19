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
    if (!data.shortcuts) return;
    var target = data.shortcuts[index].bookmarkCategoryId;
    for (var i = 0; i < data.bookmarkCategories.length; i++) {
        if (data.bookmarkCategories[i].bookmarkCategoryId === target) {
            $('.bookmark-category-item-active').removeClass('bookmark-category-item-active');
            c.slides[i].classList.add('bookmark-category-item-active');
        }
    }
}

function selectBook (index) {
    if (!data.bookmarkCategories) return;
    var target = data.bookmarkCategories[index].bookmarkCategoryId;
    for (var i = 0; i < data.shortcuts.length; i++) {
        if (data.shortcuts[i].bookmarkCategoryId === target) {
            s.slideTo(i);
            break;
        }
    }
}

function findBookById(id) {
    var bookmarkId = parseInt(id);
    if (data.bookmarks) {
        for (var i = 0; i < data.bookmarks.length; i++) {
            if (data.bookmarks[i].bookmarkId === bookmarkId) {
                return data.bookmarks[i];
            }
        }
    }
    if (data.shortcuts) {
        for (i = 0; i < data.shortcuts.length; i++) {
            if (data.shortcuts[i].bookmarkId === bookmarkId) {
                return data.shortcuts[i];
            }
        }
    }
}

function deleteBook(id, $parent) {
    $.ajax({
        url: '/bookmark/delete',
        type: 'post',
        data: {
            bookmarkId: id
        },
        success: function (data) {
            if (data.code === '000') {
                $parent.addClass('hidden');
                setTimeout(function () {
                    $parent.remove();
                }, 300);
                notice.open({duration: 5, content: '<div class="notice-title">成功删除书签</div>'});
            } else {
                notice.open({duration: 5, content: '<div class="notice-title">删除失败，请在控制台查看详情</div>'});
            }
        }
    });
}

function openBlank(id) {
    var bookmarkId = parseInt(id);
    if (!data.bookmarks) return;
    for (var i = 0; i < data.bookmarks.length; i++) {
        if (data.bookmarks[i].bookmarkId === bookmarkId) {
            window.open(data.bookmarks[i].url);
            break;
        }
    }
}

function openSelf(id) {
    var bookmarkId = parseInt(id);
    if (!data.bookmarks) return;
    for (var i = 0; i < data.bookmarks.length; i++) {
        if (data.bookmarks[i].bookmarkId === bookmarkId) {
            window.location.href = data.bookmarks[i].url;
            break;
        }
    }
}

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
                        if (ele.prop('type') === 'checkbox') {
                            if (value === 1) {
                                ele.prop('checked', true);
                            } else {
                                ele.prop('checked', false);
                            }
                        } else {
                            ele.val(value);
                        }
                    }
                }
            }
        }
    }

    function setButton () {
        var elements = document.getElementsByClassName('delete-button');
        for (var i = 0; i < elements.length; i++) {
            elements[i].addEventListener('click', function () {
                var $parent = $(this).parents('.item');
                var id  = $(this).parent().attr('data-id');
                layer.confirm('确定删除该书签？', {
                    btn: ['确定','取消'],
                    move: false,
                    shadeClose: true,
                }, function(){
                    layer.close(layer.index);
                    deleteBook(id, $parent);
                });
            });
        }

        elements = document.getElementsByClassName('info-button');
        for (i = 0; i < elements.length; i++) {
            elements[i].addEventListener('click', function () {
                var id  = $(this).parent().attr('data-id');
                layer.open({
                    type: 1,
                    title: '书签',
                    content: $('.bookmark-form')
                });
                setForm($('.bookmark-form'), findBookById(id), '');
                form.render();
            });
        }

        elements = document.getElementsByClassName('open-blank-button');
        for (i = 0; i < elements.length; i++) {
            elements[i].addEventListener('click', function () {
                var id  = $(this).parent().attr('data-id');
                openBlank(id);
            });
        }

        elements = document.getElementsByClassName('open-self-button');
        for (i = 0; i < elements.length; i++) {
            elements[i].addEventListener('click', function () {
                var id  = $(this).parent().attr('data-id');
                openSelf(id);
            });
        }
    }

    $.ajax({
        url: '/bookmark/init',
        type: 'post',
        success: function (data) {
            if (data.code === '000') {
                window.data = data.result;
                setButton();
            }
        }
    });

    var l;

    $('.addBookmark').click(function () {
        l = layer.open({
            type: 1,
            title: '书签',
            // area: ['600px', '420px'],
            content: $('.bookmark-form')
        });
    });

    laydate.render({
        elem: '#createDate'
        ,type: 'datetime'
    });

    //监听提交
    form.on('submit', function (data){
        $.ajax({
            url: '/bookmark/add',
            type: 'post',
            data: data.field,
            success: function (data) {
                if (data.code === '000') {
                    notice.open({duration: 5, content: '<div class="notice-title">成功添加书签</div>'});
                    setForm($('.bookmark-form'), data.result, '');
                    form.render();
                    layer.close(l);
                } else {
                    notice.open({duration: 5, content: '<div class="notice-title">添加失败，请在控制台查看详情</div>'});
                }
            }
        });
        return false;
    });
});
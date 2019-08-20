var data = {};
var vw = $(document).width(),
    vh = $(document).height(),
    area = ['700px', '560px'];

if (vw < 900) {
    area[0] = 0.9 * vw + 'px';
    area[1] = 0.8 * vh + 'px';
}

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
                notice.open({duration: 5, content: '<div class="notice-title">' + data.msg + '</div>'});
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

function setForm ($form, result, parent) {
    var value;
    for (i in result) {
        if (result.hasOwnProperty(i)) {
            value = result[i];
            if (value == null) break;
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

function setFavicon (result) {
    $('#faviconUrl').prop('src', result ? result.favicon.faviconUrl : '');
    $('#faviconBlurUrl').prop('src', result ? result.favicon.faviconBlurUrl : '');
    $('#faviconOriginalUrl').prop('src', result ? result.favicon.faviconOriginalUrl : '');
}

var img_input = document.querySelector("#upload-favicon");
var bookmarkId;

img_input.addEventListener('change', function (e) {
    var files = this.files;
    if (files.length) {
        var file = files[0];
        var reader = new FileReader();
        if(!/image\/\w+/.test(file.type)){
            notice.open({duration: 5, content: '<div class="notice-title">请选择图片类型文件</div>'});
            return false;
        }
        reader.onload = function (e) {
            $('#faviconOriginalUrl').prop('src', e.target.result);
        };
        reader.readAsDataURL(file);
    }
});

function uploadFavicon() {
    if (!bookmarkId) return;
    if (img_input.files.length === 0) {
        notice.open({duration: 5, content: '<div class="notice-title">请选择文件</div>'});
    } else {
        var form = new FormData();
        form.append("file", img_input.files.item(0));
        form.append("bookmarkId", bookmarkId);
        $.ajax({
            url: '/bookmark/uploadFavicon',
            type: 'post',
            data: form,
            processData: false,
            contentType: false,
            success: function (data) {
                if (data.code === '000') {
                    notice.open({duration: 5, content: '<div class="notice-title">成功修改图标</div>'});
                    setForm($('.bookmark-form'), data.result, '');
                    setFavicon(data.result);
                } else {
                    notice.open({duration: 5, content: '<div class="notice-title">' + data.msg + '</div>'});
                }
            }
        })
    }
}

layui.use(['layer', 'form', 'laydate'], function (){
    var layer = layui.layer
        ,form = layui.form
        ,laydate = layui.laydate;

    var l, type = 1;

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
                type = 2;
                $('#commitForm').text('提交修改');
                layer.open({
                    type: 1,
                    area: area,
                    title: '书签',
                    content: $('.bookmark-form'),
                    end: function () {
                        document.querySelector(".bookmark-form").reset();
                        setFavicon();
                    }
                });
                var bookmark = findBookById(id);
                setForm($('.bookmark-form'), bookmark, '');
                setFavicon(bookmark);
                bookmarkId = bookmark.bookmarkId;
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

    document.getElementById('set-button').addEventListener('click', function () {
        if (!bookmarkId) return;
        var faviconUrl = $('input[name="favicon.faviconOriginalUrl"]').val();
        layer.confirm('确定变更faviconUrl:' + faviconUrl + '？', {
            btn: ['确定','取消'],
            move: false,
            shadeClose: true,
        }, function(){
            $.ajax({
                url: '/bookmark/changeFavicon',
                type: 'post',
                data: {
                    bookmarkId: bookmarkId,
                    faviconUrl: faviconUrl
                },
                success: function (data) {
                    layer.close(layer.index);
                    if (data.code === '000') {
                        notice.open({duration: 5, content: '<div class="notice-title">成功修改图标</div>'});
                        setForm($('.bookmark-form'), data.result, '');
                        setFavicon(data.result);
                    } else {
                        notice.open({duration: 5, content: '<div class="notice-title">' + data.msg + '</div>'});
                    }
                }
            })
        });
    });

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

    $('.addBookmark').click(function () {
        type = 1;
        $('#commitForm').text('提交');
        l = layer.open({
            type: 1,
            area: area,
            title: '书签',
            content: $('.bookmark-form'),
            end: function () {
                document.querySelector(".bookmark-form").reset();
                setFavicon();
            }
        });
    });

    laydate.render({
        elem: '#createDate'
        ,type: 'datetime'
    });

    //监听提交
    form.on('submit', function (data){
        if (type === 1) {
            $.ajax({
                url: '/bookmark/add',
                type: 'post',
                data: data.field,
                success: function (data) {
                    if (data.code === '000') {
                        notice.open({duration: 5, content: '<div class="notice-title">成功添加书签</div>'});
                        setForm($('.bookmark-form'), data.result, '');
                        setFavicon(data.result);
                        bookmarkId = data.result.bookmarkId;
                        form.render();
                    } else {
                        notice.open({duration: 5, content: '<div class="notice-title">' + data.msg + '</div>'});
                    }
                }
            });
        } else {
            $.ajax({
                url: '/bookmark/update',
                type: 'post',
                data: data.field,
                success: function (data) {
                    if (data.code === '000') {
                        notice.open({duration: 5, content: '<div class="notice-title">成功修改书签</div>'});
                        setForm($('.bookmark-form'), data.result, '');
                        form.render();
                    } else {
                        notice.open({duration: 5, content: '<div class="notice-title">' + data.msg + '</div>'});
                    }
                }
            });
        }

        return false;
    });
});
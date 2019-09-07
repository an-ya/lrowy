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
            var cateId = $(this.slides[this.activeIndex]).data('cateId');
            for (var i = 0; i < c.slides.length; i++) {
                if ($(c.slides[i]).data('cateId') === cateId) {
                    $('.bookmark-category-item-active').removeClass('bookmark-category-item-active');
                    c.slides[i].classList.add('bookmark-category-item-active');
                }
            }
        }
    }
});

var c = new Swiper('.bookmark-category-container', {
    mousewheel: true,
    slidesPerView: 5,
    on: {
        tap: function () {
            var slide = this.slides[this.clickedIndex];
            $('.bookmark-category-item-active').removeClass('bookmark-category-item-active');
            slide.classList.add('bookmark-category-item-active');
            var cateId = $(slide).data('cateId');
            for (var i = 0; i < s.slides.length; i++) {
                if ($(s.slides[i]).data('cateId') === cateId) {
                    s.slideTo(i);
                    break;
                }
            }
        }
    }
});

function deleteBook (id, success) {
    $.ajax({
        url: '/bookmark/delete',
        type: 'post',
        data: {
            bookmarkId: id
        },
        success: function (data) {
            if (data.code === '000') {
                success();
                notice.success({title: '成功删除书签'});
            } else {
                notice.fail({title: '发生错误', desc: data.msg});
            }
        }
    });
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
            notice.fail({title: '请选择图片文件'});
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
                    notice.success({title: '成功修改图标'});
                    setForm($('.bookmark-form'), data.result, '');
                    setFavicon(data.result);
                } else {
                    notice.fail({title: '发生错误', desc: data.msg});
                }
            }
        })
    }
}

layui.use(['layer', 'form', 'laydate'], function () {
    var layer = layui.layer
        ,form = layui.form
        ,laydate = layui.laydate;

    var l, type = 1;

    function setButton () {
        $('.delete-bookmark-button').click(function () {
            var id  = $(this).parent().data('id');
            var $parent = $('#bookmark-' + id);
            layer.confirm('确定删除该书签？', {
                btn: ['确定','取消'],
                move: false,
                shadeClose: true,
            }, function(){
                deleteBook(id, function () {
                    layer.close(layer.index);
                    $parent.addClass('hidden');
                    setTimeout(function () {
                        $parent.remove();
                    }, 300);
                });
            });
        });

        $('.delete-shortcut-button').click(function () {
            var id  = $(this).parent().data('id');
            layer.confirm('确定删除该书签？', {
                btn: ['确定','取消'],
                move: false,
                shadeClose: true,
            }, function(){
                deleteBook(id, function () {
                    layer.close(layer.index);
                    for (var i = 0; i < s.slides.length; i++) {
                        if ($(s.slides[i]).data('id') === id) {
                            s.removeSlide(i);
                            break;
                        }
                    }
                });
            });
        });

        $('.info-button').click(function () {
            var id  = $(this).parent().data('id');
            $.ajax({
                url: '/bookmark/findById',
                type: 'post',
                data: {
                    bookmarkId: parseInt(id)
                },
                success: function (data) {
                    if (data.code === '000') {
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
                        setForm($('.bookmark-form'), data.result, '');
                        setFavicon(data.result);
                        bookmarkId = data.result.bookmarkId;
                        form.render();
                    }
                }
            });
        });

        $('.open-blank-button').click(function () {
            window.open($(this).parent().data('url'), '_blank');
        });

        $('.open-self-button').click(function () {
            window.location.href = $(this).parent().data('url');
        });
    }

    setButton();
    setDropdown();

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
                        notice.success({title: '成功修改图标'});
                        setForm($('.bookmark-form'), data.result, '');
                        setFavicon(data.result);
                    } else {
                        notice.fail({title: '发生错误', desc: data.msg});
                    }
                }
            });
        });
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
    form.on('submit', function (data) {
        if (type === 1) {
            $.ajax({
                url: '/bookmark/add',
                type: 'post',
                data: data.field,
                success: function (data) {
                    if (data.code === '000') {
                        type = 2;
                        $('#commitForm').text('提交修改');
                        notice.success({title: '成功添加书签'});
                        setForm($('.bookmark-form'), data.result, '');
                        setFavicon(data.result);
                        bookmarkId = data.result.bookmarkId;
                        form.render();
                    } else {
                        notice.fail({title: '发生错误', desc: data.msg});
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
                        notice.success({title: '成功修改书签'});
                        setForm($('.bookmark-form'), data.result, '');
                        form.render();
                    } else {
                        notice.fail({title: '发生错误', desc: data.msg});
                    }
                }
            });
        }

        return false;
    });
});
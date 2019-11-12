var pattern = new RegExp("^(#/)([0-9]+)$"), hash = window.location.hash, type = 0, articleId = 0, articleFormBtn = $('#article-form-btn'), tags = [], pageNo = 1, pageSize = 2, totalCount;
if (hash === '') {
    type = 1;
    articleFormBtn.text('新增文章');
} else if (pattern.test(hash)) {
    articleId = parseInt(hash.replace(pattern, '$2'));
    init(articleId);
} else {
    type = 1;
    articleFormBtn.text('新增文章');
    notice.fail({title:'参数错误'});
}

function init(id) {
    setArticleByTags(1);
    var loading = message.loading({loadingText: '加载中 . . .'});
    $.ajax({
        url: '/article/init',
        type: 'post',
        data: {
            articleId: id
        },
        success: function (data) {
            message.close(loading);
            if (data.code === '000') {
                type = 2;
                articleFormBtn.text('修改文章');
                editor.setData(data.result.content);
                setForm($('.article-form'), data.result, '');
                iframeGo('/article/' + data.result.articleId);
            } else {
                type = 1;
                articleFormBtn.text('新增文章');
                notice.fail({title: '发生错误', desc: data.msg});
            }
        }
    });
}

var iframe = document.getElementById('browser'), iwindow = iframe.contentWindow, iframe_open = true,
    editor, resizer, editorArea, vw = $(window).width(), vh = $(window).height();

function openIframe () {
    if (iframe_open) {
        iframe_open = false;
        resizer.hide();
        $('.browser-wrapper').hide();
        editor.resize(vw, vh - $('.header').height());
    } else {
        iframe_open = true;
        resizer.show();
        $('.browser-wrapper').show();
        editor.resize(vw * .7, vh - $('.header').height());
    }
}

function refreshIframe () {
    try {
        iwindow.location.reload();
    } catch (e) {
        notice.fail({title: '无法刷新，将丢失路径', desc: '在跨域模式下无法执行刷新操作!'});
    }
}

function keyup_submit (e) {
    var evt = window.event || e;
    if (evt.keyCode === 13) {
        iframe.src = $('#browser-url').val();
        $('#browser-url').val('http://');
    }
}

function iframeGo (url) {
    iframe.src = url;
}

function iframeBack () {
    try {
        iwindow.history.back();
    } catch (e) {
        window.history.back();
    }
}

function iframeForward () {
    try {
        iwindow.history.forward();
    } catch (e) {
        window.history.forward();
    }
}

function iframeReset () {
    iframeGo('/article/' + articleId);
}

function setSize (vw, vh, ev) {
    window.vw = vw;
    window.vh = vh;
    if (vw > 900 && iframe_open) {
        ev.editor.resize(vw * .7, vh - $('.header').height());
    } else {
        ev.editor.resize(vw, vh - $('.header').height());
    }
}

function setTag(list, name) {
    var string = '';
    for (var i = 0; i < list.length ; i++) string += '<span class="tag">' + list[i].name + '<i class="iconfont" data-id="">&#xe601;</i></span>';
    $('[name="'+ name + '"]').html(string);
}

function setPageList() {
    var string = '';
    var lastPage = Math.ceil(totalCount / pageSize);
    string += pageNo === 1 ? '<a class="iconfont page disabled" data-page="prev">&#xe60e;</a>' : '<a class="iconfont page" data-page="prev">&#xe60e;</a>';
    string += pageNo === 1 ? '<span class="page current" data-page="1">1</span>' : '<span class="page" data-page="1">1</span>';
    if (pageNo > 4) string += '<span class="page page-space">…</span>';
    for (var i = pageNo - 2; i <= pageNo + 2; i++) {
        if (i > 1 && i < lastPage) string += pageNo === i ? '<span class="page current" data-page="' + i + '">' + i + '</span>' : '<span class="page" data-page="' + i + '">' + i + '</span>';
    }
    if (pageNo < lastPage - 3) string += '<span class="page page-space">…</span>';
    if (lastPage > 1) string += pageNo === lastPage ? '<span class="page current" data-page="' + lastPage + '">' + lastPage + '</span>' : '<span class="page" data-page="' + lastPage + '">' + lastPage + '</span>';
    string += pageNo === lastPage ? '<a class="iconfont page disabled" data-page="next">&#xe60b;</a>' : '<a class="iconfont page" data-page="next">&#xe60b;</a>';
    $('.page-list').html(string);
    $('.page').click(function () {
        var page = $(this).data('page');
        if ($(this).hasClass('disabled')) return;
        if (page === 'prev') page = pageNo - 1;
        if (page === 'next') page = pageNo + 1;
        setArticleByTags(page);
    });
}

function setArticleByTags(page) {
    $('.h1 img').show();
    $.ajax({
        url: '/article/get',
        type: 'post',
        data: {
            tags: tags,
            pageNo: page,
            pageSize: pageSize
        },
        success: function (data) {
            $('.h1 img').hide();
            if (data.code === '000') {
                if (data.result && data.result.length > 0) {
                    var articles = data.result, tags, string = '', i = 0, j = 0;
                    for (i = 0; i < articles.length; i++) {
                        tags = articles[i].tags;
                        string += '<div class="article"><div class="t line1">' + articles[i].title + '</div><div class="bottom">';
                        for (j = 0; j < tags.length; j++) {
                            string += '<span class="tag">' + tags[j].name + '</span>';
                        }
                        if (!articles[i]) string += '<span class="desc line1">' + articles[i].description + '</span>';
                        string += '</div></div>';
                    }
                    $('.article-list').html(string);
                    pageNo = data.pageNo;
                    pageSize = data.pageSize;
                    totalCount = data.totalCount;
                    setPageList();
                } else {
                    $('.article-list').html('<div class="none">无匹配文章</div>');
                    $('.page-list').html('');
                }
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
            } else if (value.constructor === Array) {
                setTag(value, i);
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

function saveArticle (editor, success) {
    var loading = message.loading({loadingText: '保存中 . . .'});
    var html = editor.getData();
    $.ajax({
        url: '/article/add',
        type: 'post',
        data: {
            content: html
        },
        success: function (data) {
            message.close(loading);
            if (data.code === '000') {
                type = 2;
                window.location.hash = '#/' + data.result.articleId;
                setForm($('.article-form'), data.result, '');
                notice.success({title: '成功新增文章'});
                if (success) success();
            } else {
                notice.fail({title: '发生错误', desc: data.msg});
            }
        }
    });
}

function updateArticle (params, success) {
    var loading = message.loading({loadingText: '保存中 . . .'});
    $.ajax({
        url: '/article/update',
        type: 'post',
        data: params,
        success: function (data) {
            message.close(loading);
            if (data.code === '000') {
                if (!params.content) {
                    setForm($('.article-form'), data.result, '');
                } else {
                    try {
                        iwindow.setArticle(data.result.articleId, params.content);
                    } catch (e) {}
                }
                notice.success({title: '成功更改文章'});
                if (success) success();
            } else {
                notice.fail({title: '发生错误', desc: data.msg});
            }
        }
    });
}

if (vw <= 900) {
    editor = CKEDITOR.replace('editor', {width: vw, height: vh, resize_dir: 'horizontal', resize_minWidth: 200});
} else {
    editor = CKEDITOR.replace('editor', {width: vw * .7 - 2, height: vh, resize_dir: 'horizontal', resize_minWidth: 200});
}

CKEDITOR.on('instanceReady', function (ev) {
    resizer = editor.ui.space('resizer');
    editorArea = $('.cke_wysiwyg_frame');
    resizer.$.addEventListener('mousedown', function () {
        iframe.classList.add('disable');
        editorArea.addClass('disable');
    });
    document.addEventListener('mouseup', function () {
        iframe.classList.remove('disable');
        editorArea.removeClass('disable');
    });
    setSize($(window).width(), $(window).height(), ev);
    window.addEventListener('resize', function () { setSize($(window).width(), $(window).height(), ev); });
});

editor.addCommand('saveContent', {
    exec: function (edt) {
        if (type === 1) {
            saveArticle(edt);
        } else if (type === 2) {
            updateArticle({articleId: articleId, content: edt.getData()});
        }
    }
});

editor.ui.addButton('saveContentBtn', {
    label: '保存',
    toolbar: 'about',
    command: 'saveContent',
    icon: 'samples/img/save.png'
});

// new Swiper('.tags', {
//     preventClicks: false,
//     slidesPerColumn: 2,
//     slidesPerView: 'auto',
//     on: {
//         tap: function () {
//             var $this = $(this.slides[this.clickedIndex]);
//             var id = parseInt($this.data('id'));
//             if ($this.hasClass('tag-item-active')) {
//                 _.pull(tags, id);
//                 $this.removeClass('tag-item-active');
//             } else {
//                 tags.push(id);
//                 $this.addClass('tag-item-active');
//             }
//             setArticleByTags();
//             this.slideTo(this.clickedIndex);
//         }
//     }
// });
var $tagItem = $('.tag-item');
$tagItem.mdRipple();
$tagItem.on('mousedown touchstart', function () {
    var $this = $(this);
    var id = parseInt($this.data('id'));
    $this.on('mouseup mouseleave touchend', function () {
        if ($this.hasClass('tag-item-active')) {
            _.pull(tags, id);
            $this.removeClass('tag-item-active');
        } else {
            tags.push(id);
            $this.addClass('tag-item-active');
        }
        setArticleByTags();
        $this.off('mouseup mouseleave touchend');
    });
});

layui.use(['form', 'laydate'], function () {
    var form = layui.form
        ,laydate = layui.laydate;

    laydate.render({elem: '#createDate', type: 'datetime'});

    form.on('submit', function (data) {
        if (type === 1) {
            saveArticle(data.field, function () {
                form.render();
            });
        } else if (type === 2) {
            updateArticle(data.field, function () {
                form.render();
            });
        }

        return false;
    });
});
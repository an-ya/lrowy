var pattern = new RegExp("^(#/)([0-9]+)$"), hash = window.location.hash, type = 0, articleId = 0, articleFormBtn = $('#article-form-btn'), tags = [], currentTags = [], pageNo = 1, pageSize = 4, totalCount;
var form = layui.form, laydate = layui.laydate;

if (hash === '') {
    type = 1;
    articleFormBtn.text('新增文章');
} else if (pattern.test(hash)) {
    getArticle(parseInt(hash.replace(pattern, '$2')));
} else {
    type = 1;
    articleFormBtn.text('新增文章');
    notice.fail({title:'参数错误'});
}
setArticleByTags(1);

function getArticle(id) {
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
                window.location.hash = '#/' + id;
                articleId = id;
                type = 2;
                articleFormBtn.text('修改文章');
                editor.setData(data.result.content);
                setForm($('.article-form'), data.result, '');
                form.render();
                setTagDeleteEvent();
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

function setArticles(articles) {
    var tags, string = '', i = 0, j = 0, c = '';
    for (i = 0; i < articles.length; i++) {
        tags = articles[i].tags;
        c = articles[i].articleId === articleId ? 'article article-active' : 'article';
        string += '<div class="' + c + '"><div class="t line1" data-id="' + articles[i].articleId + '">' + articles[i].title + '</div><div class="bottom">';
        for (j = 0; j < tags.length; j++) {
            string += '<span class="tag">' + tags[j].name + '</span>';
        }
        if (articles[i].description) string += '<span class="desc line1">' + articles[i].description + '</span>';
        string += '</div></div>';
    }
    $('.article-list').html(string);
    $('.article .t').click(function () {
        $('.article').removeClass('article-active');
        $(this).parent().addClass('article-active');
        getArticle($(this).data('id'));
    });
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
            tags: tags.map(function (item) {
                return item.id;
            }),
            pageNo: page,
            pageSize: pageSize
        },
        success: function (data) {
            $('.h1 img').hide();
            if (data.code === '000') {
                if (data.result && data.result.length > 0) {
                    pageNo = data.pageNo;
                    pageSize = data.pageSize;
                    totalCount = data.totalCount;
                    setArticles(data.result);
                    setPageList();
                } else {
                    $('.article-list').html('<div class="none">无匹配文章</div>');
                    $('.page-list').html('');
                }
            }
        }
    });
}

function setTag(list, name) {
    currentTags = list;
    var string = '';
    for (var i = 0; i < list.length ; i++) string += '<span class="tag">' + list[i].name + '<i class="iconfont" data-id="' + list[i].articleTagId + '" data-name="' + list[i].name + '">&#xe601;</i></span>';
    $('[name="'+ name + '"]').html(string);
}

function setTagDeleteEvent() {
    $('.article-form .tag i').click(function () {
        var $this = $(this);
        var id = $this.data('id');
        var name = $this.data('name');
        layer.confirm('确定解除标签：' + name + '？', {
            btn: ['确定','取消'],
            move: false,
            shadeClose: true,
        }, function(){
            $.ajax({
                url: '/article/tag/delete',
                type: 'post',
                data: {
                    articleId: articleId,
                    articleTagId: id,
                },
                success: function (data) {
                    layer.close(layer.index);
                    if (data.code === '000') {
                        setArticleByTags(pageNo);
                        $this.parents('.tag').remove();
                        notice.success({title: '成功解除标签'});
                    } else {
                        notice.fail({title: '发生错误', desc: data.msg});
                    }
                }
            });
        });
    });
}

function addTagEvent() {
    var string = '确定绑定标签', has = false, list = [];
    if (tags.length === 0) {
        layer.msg('未选中任何标签');
        return;
    }
    for (var i = 0; i < tags.length; i++) {
        has = false;
        for (var j = 0; j < currentTags.length; j++) {
            if (tags[i].id === currentTags[j].articleTagId) {
                has = true;
                break;
            }
        }
        if (!has) list.push(tags[i]);
    }
    for (i = 0; i < list.length; i++) {
        if (i !== 0) string += '、';
        string += list[i].name;
    }
    string += '?';
    if (list.length === 0) {
        layer.msg('文章已经绑定选中标签');
        return;
    }
    layer.confirm(string, {
        btn: ['确定','取消'],
        move: false,
        shadeClose: true,
    }, function(){
        $.ajax({
            url: '/article/tag/add',
            type: 'post',
            data: {
                articleId: articleId,
                tags: list.map(function (item) {
                    return item.id;
                }),
            },
            success: function (data) {
                layer.close(layer.index);
                if (data.code === '000') {
                    setArticleByTags(pageNo);
                    setTag(data.result.tags, 'tags');
                    setTagDeleteEvent();
                    notice.success({title: '成功绑定标签'});
                } else {
                    notice.fail({title: '发生错误', desc: data.msg});
                }
            }
        });
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

function saveArticle (editor) {
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
                form.render();
                setArticleByTags(pageNo);
                notice.success({title: '成功新增文章'});
            } else {
                notice.fail({title: '发生错误', desc: data.msg});
            }
        }
    });
}

function updateArticle (params) {
    var loading = message.loading({loadingText: '保存中 . . .'});
    $.ajax({
        url: params.content ? '/article/update/content' : '/article/update/info',
        type: 'post',
        data: params,
        success: function (data) {
            message.close(loading);
            if (data.code === '000') {
                if (params.content) {
                    $('.article-form').find('[name="updateDate"]').val(data.result.updateDate);
                    try {
                        iwindow.setArticle(params.articleId, params.content);
                    } catch (e) {}
                } else {
                    setArticleByTags(pageNo);
                    setForm($('.article-form'), data.result, '');
                }
                form.render();
                notice.success({title: '成功更改文章'});
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

function removeTagData(data, id) {
    var temp = [];
    for (var i = 0; i < data.length; i++) {
        if (data[i].id !== id) {
            temp.push(data[i]);
        }
    }
    return temp;
}

var $tagItem = $('.tag-item');
$tagItem.mdRipple();
$tagItem.on('mousedown touchstart', function () {
    var $this = $(this);
    var id = parseInt($this.data('id'));
    var name = $this.data('name');
    var longPress = false, end = false;
    setTimeout(function () {
        longPress = true;
        if (!end) $this.addClass('tag-item-long-press');
    }, 300);
    $this.on('mouseup mouseleave touchend', function () {
        end  = true;
        $this.removeClass('tag-item-long-press');
        if ($this.hasClass('tag-item-active')) {
            if (longPress) {
                tags = removeTagData(tags, id);
                $this.removeClass('tag-item-active');
            } else {
                tags = [];
                $tagItem.removeClass('tag-item-active');
            }
        } else {
            if (longPress) {
                tags.push({id: id, name: name});
                $this.addClass('tag-item-active');
            } else {
                tags = [];
                tags.push({id: id, name: name});
                $tagItem.removeClass('tag-item-active');
                $this.addClass('tag-item-active');
            }
        }
        setArticleByTags();
        $this.off('mouseup mouseleave touchend');
    });
});

laydate.render({elem: '#createDate', type: 'datetime'});
laydate.render({elem: '#updateDate', type: 'datetime'});

form.on('submit', function (data) {
    if (type === 1) {
        saveArticle(data.field);
    } else if (type === 2) {
        var field = data.field;
        if (data.field.autoUpdateDate === 'on') field.updateDate = new Date().Format("yyyy-MM-dd hh:mm:ss");
        updateArticle(field);
    }

    return false;
});
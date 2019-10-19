var pattern = new RegExp("^(#/)([0-9]+)$"), hash = window.location.hash, type = 0, articleId = 0, articleFormBtn = $('#article-form-btn');
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

function setSize (vw, vh, ev) {
    window.vw = vw;
    window.vh = vh;
    if (vw > 900 && iframe_open) {
        ev.editor.resize(vw * .7, vh - $('.header').height());
    } else {
        ev.editor.resize(vw, vh - $('.header').height());
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

function saveArticle (editor, success) {
    var loading = message.loading({loadingText: '保存中 . . .'});
    $.ajax({
        url: '/article/add',
        type: 'post',
        data: {
            content: editor.getData()
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
                if (!params.content) setForm($('.article-form'), data.result, '');
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
            updateArticle({content: edt.getData()});
        }
    }
});

editor.ui.addButton('saveContentBtn', {
    label: '保存',
    toolbar: 'about',
    command: 'saveContent',
    icon: 'samples/img/save.png'
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
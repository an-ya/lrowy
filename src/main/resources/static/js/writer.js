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
        var loading = message.loading({loadingText: '保存中 . . .'});
        // setTimeout(function () {
        //     $('#browser').html(edt.getData());
        //     $('pre code').each(function () {
        //         hljs.highlightBlock(this);
        //     });
        //     MathJax.Hub.Queue(["Typeset", MathJax.Hub, $('#browser')[0]]);
        //     message.close(loading);
        // }, 600);
    }
});

editor.ui.addButton('saveContentBtn', {
    label: '保存',
    toolbar: 'about',
    command: 'saveContent',
    icon: 'samples/img/save.png'
});

layui.use(['layer', 'form', 'laydate'], function(){
    var layer = layui.layer
        ,form = layui.form
        ,laydate = layui.laydate;

    laydate.render({elem: '#createDate', type: 'datetime'});
});
(function($, window) {
    var $container = $('<div class="preview_container">' +
        '    <div class="preview_mask" onclick="preview.hide()"></div>' +
        '    <div class="preview_inner" id="img_container">' +
        '        <div class="preview_close" onclick="preview.hide()"><i class="iconfont">&#xe611;</i></div>' +
        '        <div class="preview_content"></div>' +
        '    </div>' +
        '    <div class="preview_opr">' +
        '        <ul class="preview_opr_list">' +
        '            <li class="preview_opr_item"><i class="iconfont" id="prev" title="上一张" onclick="preview.prev()">&#xe779;</i></li>' +
        '            <li class="preview_opr_item"><i class="iconfont" id="next" title="下一张" onclick="preview.next()">&#xe775;</i></li>' +
        '        </ul>' +
        '    </div>' +
        '</div>'),
        $content = $container.find('.preview_content');
    $('body').append($container);

    window.preview = {
        check: function ($current) {
            if ($current.is(':first-child')) {
                $('#prev').addClass('disabled');
            } else {
                $('#prev').removeClass('disabled');
            }
            if ($current.is(':last-child')) {
                $('#next').addClass('disabled');
            } else {
                $('#next').removeClass('disabled');
            }
        },
        init: function (array, index) {
            if (array.length === 0) return;
            if (index < 0) index = 0;
            if (index > array.length) index = array.length;
            var result = '';
            for (var i = 0; i < array.length; i++) {
                result += '<img class="' + (i === index ? 'show' : '') + '" src="' + array[i] + '" alt=""/>'
            }
            $content.html(result);
            this.check($('.preview_content img').eq(index));
            $container.addClass('show');
        },
        hide: function () {
            $('.preview_container').removeClass('show');
            $('.preview_content img').removeClass('show');
        },
        prev: function () {
            var $current = $('.preview_content .show');
            if ($current.length > 0) {
                var $prew = $current.prev();
                if ($prew.length > 0) {
                    $('.preview_content img').removeClass('show');
                    $prew.addClass('show');
                    this.check($prew);
                }
            }
        },
        next: function () {
            var $current = $('.preview_content .show');
            if ($current.length > 0) {
                var $next = $current.next();
                if ($next.length > 0) {
                    $('.preview_content img').removeClass('show');
                    $next.addClass('show');
                    this.check($next);
                }
            }
        }
    };
})(jQuery, window);
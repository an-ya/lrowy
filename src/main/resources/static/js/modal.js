(function($, window) {
    var $container = $('<div class="modal-container">\n' +
        '    <div class="modal-mask"></div>\n' +
        '    <div class="modal-inner">\n' +
        '        <div class="modal-content">\n' +
        '            <i class="iconfont modal-close" onclick="modal.close()">&#xe611;</i>\n' +
        '            <div class="modal-header"></div>\n' +
        '            <div class="modal-body"></div>\n' +
        '            <div class="modal-footer"><div class="modal-btn">确定</div></div>\n' +
        '        </div>\n' +
        '    </div>\n' +
        '</div>'),
        $header = $container.find('.modal-header'),
        $body = $container.find('.modal-body'),
        $mask = $container.find('.modal-mask'),
        $footer = $container.find('.modal-footer'),
        $btn = $container.find('.modal-btn'),
        $close = $container.find('.modal-close');
    $('body').append($container);

    window.modal = {
        open: function (params) {
            if (!params) params = {};
            if (params.title) $header.html(params.title);
            if (params.content) {
                if (params.content instanceof jQuery) {
                    var $content = params.content.clone(true);
                    $body.html($content);
                    $content.show();
                } else {
                    $body.html(params.content);
                }
            }
            if (params.confirm && params.confirm instanceof Function) {
                $footer.show();
                var that = this;
                $btn.unbind('click').bind('click', function () {
                    params.confirm();
                    that.close();
                });
            } else {
                $footer.hide();
            }
            $mask.click(this.close);
            $close.click(this.close);
            $container.addClass('show');
        },
        close: function () {
            setTimeout(function () {
                $body.empty();
            }, 300);
            $container.removeClass('show');
        }
    };
})(jQuery, window);
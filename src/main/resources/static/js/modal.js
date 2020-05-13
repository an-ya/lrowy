(function($, window) {
    var $container = $('<div class="modal-container">\n' +
        '    <div class="modal-mask"></div>\n' +
        '    <div class="modal-inner">\n' +
        '        <div class="modal-content">\n' +
        '            <i class="modal-close" onclick="closeModal()">&#xe611;</i>\n' +
        '            <div class="modal-header">标题</div>\n' +
        '            <div class="modal-body"></div>\n' +
        '        </div>\n' +
        '    </div>\n' +
        '</div>'),
        $header = $container.find('.modal-header'),
        $body = $container.find('.modal-body'),
        $mask = $container.find('.modal-mask'),
        $close = $container.find('.modal-close');
    $('body').append($container);

    window.modal = {
        open: function ($content, title) {
            $header.html(title);
            $body.html($content.html());
            $mask.click(this.close);
            $close.click(this.close);
            $container.addClass('show');
        },
        close: function () {
            $container.removeClass('show');
        }
    };
})(jQuery, window);
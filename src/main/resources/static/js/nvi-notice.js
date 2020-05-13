(function($, window) {
    var $container = $('<div class="notice"></div>');
    $('body').append($container);

    window.notice = {
        open: function (params) {
            if (!params) params = {};
            var $notice = $('<div class="notice-notice">' +
                '   <div class="notice-notice-content">' +
                '       <div class="notice-notice-content-wrapper"></div>' +
                '       <i class="iconfont notice-close">&#xe611;</i>' +
                '   </div>' +
                '   <div class="notice-notice-before">' +
                '</div>'),
                $content = $notice.find('.notice-notice-content'),
                $before = $notice.find('.notice-notice-before'),
                $wrapper = $notice.find('.notice-notice-content-wrapper'),
                $close = $notice.find('.notice-close');

            if (params.color) {
                $content.css('border-left', '5px solid ' + params.color);
                $before.css('background', params.color);
            }

            if (params.content) {
                $wrapper.html(params.content);
            } else {
                if (params.icon) $wrapper.append(params.icon);
                if (params.title) $wrapper.append('<div class="notice-title">' + params.title + '</div>');
                if (!params.desc) params.desc = '&nbsp;';
                $wrapper.append('<div class="notice-desc">' + params.desc + '</div>');
            }

            var that = this;
            if (typeof params.duration !== 'number' || params.duration < 0) {
                setTimeout(function () {
                    that.close($notice);
                }, 5000);
            } else if (params.duration === 0) {
                $close.addClass('notice-close-show');
                $close.bind('click', function () {
                    that.close($notice);
                });
            } else if (params.duration > 0) {
                setTimeout(function () {
                    that.close($notice);
                }, params.duration * 1000);
            }

            $container.append($notice);
            $notice.css('height', $wrapper.outerHeight());
            setTimeout(function () {
                $notice.addClass('show');
            }, 14);
            return $notice;
        },
        success: function (params) {
            if (!params) params = {};
            params.icon = '<i class="notice-icon" style="color:#1E9fff;font-weight:normal;">&#xe657;</i>';
            return this.open(params);
        },
        fail: function (params) {
            if (!params) params = {};
            params.color = '#f90';
            params.icon = '<i class="notice-icon" style="color:#f90;font-weight:normal;">&#xe64d;</i>';
            return this.open(params);
        },
        close: function ($notice, success) {
            $notice.removeClass('show');
            $notice.css({'height': 0, 'margin-bottom': 0});
            setTimeout(function () {
                $notice.remove();
                if (success && success instanceof Function) success();
            }, 800);
        }
    };
})(jQuery, window);
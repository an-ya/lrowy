(function($, window) {
    var $container = $('<div class="ivu-message"></div>');
    $('body').append($container);

    window.message = {
        open: function (params) {
            if (!params) params = {};
            var $message = $('<div class="ivu-message-notice"><div class="ivu-message-notice-content"></div></div>'),
                $content = $message.find('.ivu-message-notice-content');

            if (params.content) $content.html(params.content);
            if (params.icon) $content.prepend(params.icon);
            var that = this;
            if (typeof params.duration !== 'number' || params.duration < 0) {
                setTimeout(function () {
                    that.close($message);
                }, 5000);
            } else if (params.duration === 0) {
                var $close = $('<i class="iconfont ivu-message-notice-close">&#xe611;</i>');
                $content.append($close);
                $close.bind('click', function () {
                    that.close($message);
                });
            } else if (params.duration > 0) {
                setTimeout(function () {
                    that.close($message);
                }, params.duration * 1000);
            }

            $container.append($message);
            $message.css('height', $message.outerHeight());
            $message.addClass('move-message-enter-active');
            setTimeout(function () {
                $message.removeClass('move-message-enter-active');
            }, 300);
            return $message;
        },
        loading: function (params) {
            if (!params) params = {};
            params.content = '<span class="loading icon"/><span>loading . . .</span>';
            return this.open(params);
        },
        success: function (params) {
            if (!params) params = {};
            params.icon = '<i class="iconfont ivu-message-notice-icon" style="color:#1E9fff;font-weight:normal;">&#xe657;</i>';
            return this.open(params);
        },
        fail: function (params) {
            if (!params) params = {};
            params.icon = '<i class="iconfont ivu-message-notice-icon" style="color:#f90;font-weight:normal;">&#xe64d;</i>';
            return this.open(params);
        },
        close: function ($message, success) {
            $message.addClass('move-message-leave-active');
            if (!$message.is(':last-child')) $message.css({'height': 0, 'padding': 0});
            setTimeout(function () {
                $message.remove();
                if (success && success instanceof Function) success();
            }, 300);
        }
    };
})(jQuery, window);
(function($, window) {
    window.message = {
        dom: null,
        string: ['<div class="ivu-message"></div>', '<div class="ivu-message-notice"><div class="ivu-message-notice-content"></div></div>'],
        init: function () {
            if (!this.dom) {
                this.dom = $(this.string[0]);
                $('body').append(this.dom);
            }
        },
        open: function (params) {
            if (!params) params = {};
            this.init();
            var e = $(this.string[1]);
            e.children().eq(0).html(params.content);
            if (params.icon) e.children().eq(0).prepend(params.icon);
            this.dom.append(e);
            e.addClass('move-message-enter-active');
            e.css('height', e.outerHeight());
            setTimeout(function () {
                e.removeClass('move-message-enter-active');
            }, 300);
            var that = this;
            if (typeof params.duration !== 'number' || params.duration < 0) {
                setTimeout(function () {
                    that.close(e);
                }, 5000);
            } else if (params.duration === 0) {
                e.children().eq(0).append('<i class="iconfont ivu-message-notice-close">&#xe601;</i>');
                e.find('.ivu-message-notice-close').bind('click', function () {
                    that.close(e);
                });
            } else if (params.duration > 0) {
                setTimeout(function () {
                    that.close(e);
                }, params.duration * 1000);
            }
            return e;
        },
        loading: function (params) {
            if (!params) params = {};
            params.content = '<img src="images/loading.gif"/><span>loading . . .</span>';
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
        close: function (e, success) {
            e.addClass('move-message-leave-active');
            if (e.next().length !== 0) {
                e.css({'height': 0, 'padding': 0});
            }
            setTimeout(function () {
                e.remove();
                if (success && success instanceof Function) {
                    success();
                }
            }, 300);
        }
    };
})(jQuery, window);
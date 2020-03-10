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
            this.init();
            var e = $(this.string[1]);
            e.children().eq(0).html(params.content);
            this.dom.append(e);
            e.addClass('move-message-enter-active');
            e.css('height', e.outerHeight());
            setTimeout(function () {
                e.removeClass('move-message-enter-active');
            }, 300);
            if (typeof params.duration === 'number' && params.duration > 0) {
                var that = this;
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
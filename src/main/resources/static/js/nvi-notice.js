(function($) {
    window.notice = {
        dom: null,
        string: ['<div class="notice"></div>', '<div class="notice-notice"><div class="notice-notice-content"><div class="notice-notice-content-wrapper"></div></div><div class="notice-notice-before"></div>'],
        init: function () {
            if (!this.dom) {
                this.dom = $(this.string[0]);
                $('body').append(this.dom);
            }
        },
        open: function (params) {
            this.init();
            var e = $(this.string[1]);
            e.find(".notice-notice-content-wrapper").html(params.content);
            this.dom.append(e);
            setTimeout(function () {
                e.children().eq(0).addClass('notice-notice-content-show');
                e.children().eq(1).addClass('notice-notice-before-show');
                e.find(".notice-notice-content-wrapper").addClass('notice-notice-content-wrapper-show');
            }, 14);
            if (typeof params.duration === 'number' && params.duration > 0) {
                var that = this;
                setTimeout(function () {
                    that.close(e);
                }, params.duration * 1000);
            }
            return e;
        },
        close: function (e) {
            e.children().eq(0).removeClass('notice-notice-content-show');
            e.children().eq(1).removeClass('notice-notice-before-show');
            e.find(".notice-notice-content-wrapper").removeClass('notice-notice-content-wrapper-show');
            setTimeout(function () {
                e.remove();
            }, 600);
        }
    };
})(jQuery);
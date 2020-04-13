(function($, window) {
    window.notice = {
        dom: null,
        string: ['<div class="notice"></div>', '<div class="notice-notice"><div class="notice-notice-content"><div class="notice-notice-content-wrapper"></div><i class="iconfont notice-close">&#xe601;</i></div><div class="notice-notice-before"></div>'],
        init: function () {
            if (!this.dom) {
                this.dom = $(this.string[0]);
                $('body').append(this.dom);
            }
        },
        open: function (params) {
            if (!params) params = {};
            this.init();
            var e = $(this.string[1]), content = e.children().eq(0), before = e.children().eq(1);
            if (params.color) {
                content.css('border-left', '5px solid ' + params.color);
                before.css('background', params.color);
            }
            var wrapper = e.find('.notice-notice-content-wrapper');
            if (params.content) {
                wrapper.html(params.content);
            } else {
                if (params.icon) wrapper.append(params.icon);
                if (params.title) wrapper.append('<div class="notice-title">' + params.title + '</div>');
                if (!params.desc) params.desc = '&nbsp;';
                wrapper.append('<div class="notice-desc">' + params.desc + '</div>');
            }
            this.dom.append(e);
            var that = this;
            setTimeout(function () {
                e.css('height', wrapper.outerHeight());
                content.addClass('notice-notice-content-show');
                before.addClass('notice-notice-before-show');
                e.find('.notice-notice-content-wrapper').addClass('notice-notice-content-wrapper-show');
                e.find('.notice-title').addClass('notice-title-show');
                e.find('.notice-desc').addClass('notice-desc-show');

                if (typeof params.duration !== 'number' || params.duration < 0) {
                    setTimeout(function () {
                        that.close(e);
                    }, 5000);
                } else if (params.duration === 0) {
                    var close = e.find('.notice-close');
                    close.addClass('notice-close-show');
                    close.bind('click', function () {
                        that.close(e);
                    });
                } else if (params.duration > 0) {
                    setTimeout(function () {
                        that.close(e);
                    }, params.duration * 1000);
                }
            }, 14);
            return e;
        },
        success: function (params) {
            if (!params) params = {};
            params.icon = '<i class="iconfont notice-icon" style="color:#1E9fff;font-weight:normal;">&#xe657;</i>';
            return this.open(params);
        },
        fail: function (params) {
            if (!params) params = {};
            params.color = '#f90';
            params.icon = '<i class="iconfont notice-icon" style="color:#f90;font-weight:normal;">&#xe64d;</i>';
            return this.open(params);
        },
        close: function (e) {
            e.addClass('notice-notice-hidden');
            e.children().eq(0).removeClass('notice-notice-content-show');
            e.children().eq(1).removeClass('notice-notice-before-show');
            e.find('.notice-notice-content-wrapper').removeClass('notice-notice-content-wrapper-show');
            e.find('.notice-close').removeClass('notice-close-show');
            e.find('.notice-title').removeClass('notice-title-show');
            e.find('.notice-desc').removeClass('notice-desc-show');
            setTimeout(function () {
                e.remove();
            }, 800);
        }
    };
})(jQuery, window);
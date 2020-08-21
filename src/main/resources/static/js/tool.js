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
            params.icon = '<i class="iconfont notice-icon" style="color:#1E9fff;font-weight:normal;">&#xe657;</i>';
            return this.open(params);
        },
        fail: function (params) {
            if (!params) params = {};
            params.color = '#f90';
            params.icon = '<i class="iconfont notice-icon" style="color:#f90;font-weight:normal;">&#xe64d;</i>';
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
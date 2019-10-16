var vh = $(window).height(), vw = $(window).width(), isScrolling = false, scrollTop = 0, articleCategory = $('.article-category'), articleCategoryItem = articleCategory.children(), articleCategoryItemNum = articleCategoryItem.length;
var area = ['700px', '560px'];
if (vw < 900) {
    area[0] = 0.9 * vw + 'px';
    area[1] = 0.8 * vh + 'px';
}
var page = 2, pageMin = 1, mode = 2, pageMax = Math.ceil($('.article-category-item-wrapper').length / 5), changingPage = false;
var $parallax = $('.parallax'), parallax = [], $viewP = $('.view-p'), viewP = [];
$parallax.css('visibility', 'visible');
for (var i = 0; i < $parallax.length; i++) { parallax.push(parallaxInitOne($($parallax[i]))); }
for (i = 0; i < $viewP.length; i++) { viewP.push(viewInitOne($($viewP[i]))); }
scrollEvent();

$(document).scroll(scrollEvent);
$(window).resize(function () {
    vh = $(window).height();
    vw = $(window).width();
    if (vw < 900) {
        area[0] = 0.9 * vw + 'px';
        area[1] = 0.8 * vh + 'px';
    }
    scrollEvent();
});

function scrollEvent() {
    scrollTop = $(this).scrollTop();

    // parallax.each(function () { parallaxOne($(this)); });
    for (var i = 0; i < parallax.length; i++) { parallaxOne(parallax[i]); }

    if (isScrolling) return;
    for (i = 0; i < viewP.length; i++) { viewOne(viewP[i]); }
}

function prevCatePage () {
    if (page === pageMin) return;
    if (changingPage) return;
    changingPage = true;
    articleCategoryItem.each(function (index) {
        var active = $(this).children().eq(page - 1);
        var prev = $(this).children().eq(page - 2);
        var delay;
        if (mode === 1) {
            delay = (index + 1) * 100
        } else {
            delay = (articleCategoryItemNum - index) * 100;
        }
        prev.addClass('active');
        prev.css('animation-delay', delay + 'ms');
        if (mode === 1) {
            prev.addClass('right-fade-in');
        } else {
            prev.addClass('left-fade-in');
        }
        active.css('animation-delay', delay + 'ms');
        active.addClass('left-fade-out');
        setTimeout(function () {
            if (mode === 1) {
                prev.removeClass('right-fade-in');
                if (index === articleCategoryItemNum - 1) {
                    changingPage = false;
                }
            } else {
                prev.removeClass('left-fade-in');
                if (index === 0) {
                    changingPage = false;
                }
            }
            active.removeClass('active');
            active.removeClass('left-fade-out');
        }, 350 + delay);
    });
    page -= 1;
    if (page === pageMin) $('#category-page-prev').addClass('disabled');
    if (page !== pageMax) $('#category-page-next').removeClass('disabled');
}

function nextCatePage () {
    if (page === pageMax) return;
    if (changingPage) return;
    changingPage = true;
    articleCategoryItem.each(function (index) {
        var active = $(this).children().eq(page - 1);
        var next = $(this).children().eq(page);
        var delay = (index + 1) * 100;
        next.addClass('active');
        next.css('animation-delay', delay + 'ms');
        if (mode === 1) {
            next.addClass('left-fade-in');
        } else {
            next.addClass('right-fade-in');
        }
        active.css('animation-delay', delay + 'ms');
        active.addClass('right-fade-out');
        setTimeout(function () {
            if (mode === 1) {
                next.removeClass('left-fade-in');
            } else {
                next.removeClass('right-fade-in');
            }
            active.removeClass('active');
            active.removeClass('right-fade-out');
            if (index === articleCategoryItemNum - 1) {
                changingPage = false;
            }
        }, 350 + delay);
    });
    page += 1;
    if (page === pageMax) $('#category-page-next').addClass('disabled');
    if (page !== pageMin) $('#category-page-prev').removeClass('disabled');
}

function getViewResult($this) {
    var x = $this.data('viewX');
    var y = $this.data('viewY');
    var opacity = $this.data('viewOpacity');
    var animIn = $this.data('viewAnimIn');
    var animOut = $this.data('viewAnimOut');

    if (animIn !== undefined && animOut !== undefined) {
        return {
            type: 1,
            animIn: animIn,
            animOut: animOut
        }
    } else {
        var cssO = {}, cssE = {}, translate, translateX, translateY;

        if (x !== undefined) {
            if (isParcent(x)) x = toPoint(x) * vw;
            translateX = x + 'px';
        }
        if (y !== undefined) {
            if (isParcent(y)) y = toPoint(y) * vh;
            translateY = y + 'px';
        }

        if (translateX && translateY) {
            translate =  'translate(' + translateX + ',' + translateY + ')';
        } else if (translateX) {
            translate = 'translate(' + translateX + ',0)';
        } else if (translateY) {
            translate = 'translate(0,' + translateY + ')';
        }

        if (translate) {
            cssO = {'-webkit-transform': 'translate(0, 0)', 'transform': 'translate(0, 0)'};
            cssE = {'-webkit-transform': translate, 'transform': translate};
        }

        if (opacity !== undefined) {
            cssO['opacity'] = 1;
            cssE['opacity'] = opacity;
        }

        return {
            type: 2,
            cssE: cssE,
            cssO: cssO
        }
    }
}

function viewInitOne ($this) {
    var children = [];
    var target = $this.data('viewTarget');
    var attr = $this.data('viewAttr');
    var mode = $this.data('viewMode');
    var delay = $this.data('viewDelay');
    var top = $this.offset().top, bottom = $this.height() + top;

    if (target === 'self') target = $this;
    else target = $this.children();
    if (mode === 'static') top = 10000;

    target.each(function () {
        var _this = $(this);
        var css;
        if (attr === 'children') css = getViewResult(_this);
        else css = getViewResult($this);
        _this['_css'] = css;
        children.push(_this);
    });

    $this['_target'] = children;
    $this['_attr'] = attr;
    $this['_mode'] = mode;
    $this['_delay'] = delay;
    $this['_top'] = top;
    $this['_bottom'] = bottom;
    return $this;
}

function viewOne ($this) {
    var target = $this['_target'];
    var top = $this['_top'], bottom = $this['_bottom'];

    for (var i = 0; i < target.length; i++) {
        var css = target[i]['_css'];
        if (css.type === 1) {
            if (scrollTop > top || scrollTop + vh < bottom) {
                target[i].removeClass(css.animIn);
                target[i].addClass(css.animOut);
            } else {
                target[i].removeClass(css.animOut);
                target[i].addClass(css.animIn);
            }
        } else {
            if (scrollTop > top || scrollTop + vh < bottom) {
                target[i].css(css.cssE);
            } else {
                target[i].css(css.cssO);
            }
        }
    }
}

function scrollToMain (index) {
    var target = vh * index;
    var begin = [], t = 0, delay, end = [];
    isScrolling = true;

    for (var i = 0; i < viewP.length; i++) {
        var $this = viewP[i];
        var mode = $this['_mode'];
        var top = $this['_top'], bottom = $this['_bottom'];
        if ((scrollTop < top && scrollTop + vh > bottom && target > top && mode !== 'static') || (scrollTop < top && scrollTop + vh > bottom && target + vh < bottom )) {
            begin.push($this);
        } else if ((scrollTop > top && target + vh > bottom && target < top && mode !== 'static') || (scrollTop + vh < bottom && target + vh > bottom && target < top)) {
            end.push($this);
        }
    }

    scrollTop = target;
    for (i = 0; i < begin.length; i++) {
        setTimeout(function (i) { viewOne(begin[i]); }, t, i);
        delay = begin[i]['_delay'];
        if (delay !== undefined) t += delay;
    }
    setTimeout(function () {
        $('html,body').stop().animate({scrollTop: vh * index}, 1000, 'swing', function () {
            for (var i = 0; i < end.length; i++) { viewOne(end[i]); }
            isScrolling = false;
        });
    }, t);
}

function isParcent(result) {
    var pattern = new RegExp(/^-?\d+(\.\d+)?%$/);
    return pattern.test(result);
}

function toPoint(percent){
    var str = percent.replace("%","");
    str = str / 100;
    return str;
}

function getRange(scrollTop, offset, range) {
    if (scrollTop < offset - range) {
        return 0
    } else if (scrollTop >= offset - range && scrollTop <= offset) {
        return 1
    } else if (scrollTop >= offset && scrollTop <= offset + range) {
        return 2
    } else {
        return 3
    }
}

function parallaxInitOne($this) {
    var offset = $this.data('parallaxOffset');
    var x = $this.data('parallaxX');
    var y = $this.data('parallaxY');
    var opacityO = $this.data('parallaxOpacityO');
    var opacityE = $this.data('parallaxOpacityE');
    var scaleO = $this.data('parallaxScaleO');
    var scaleE = $this.data('parallaxScaleE');
    var range = $this.data('parallaxRange');
    var mode = $this.data('parallaxMode');

    if (offset !== undefined) {
        if (offset === 'parent.top') {
            offset = $this.parent().offset().top;
        }
        if (offset === 'top') {
            offset = $this.offset().top;
        }
        if (isParcent(offset)) {
            offset = toPoint(offset) * vh;
        }
    } else {
        offset = 0;
    }
    if (x !== undefined && isParcent(x)) x = toPoint(x) * vw;
    if (y !== undefined && isParcent(y)) y = toPoint(y) * vh;
    if (range !== undefined) {
        if (range === 'parent.height') {
            range = $this.parent().height();
        }
        if (range === 'height') {
            range = $this.height();
        }
        if (isParcent(range)) {
            range = toPoint(range) * vh;
        }
    } else {
        range = vh;
    }

    $this['_offset'] = offset;
    $this['_x'] = x;
    $this['_y'] = y;
    $this['_opacityO'] = opacityO;
    $this['_opacityE'] = opacityE;
    $this['_scaleO'] = scaleO;
    $this['_scaleE'] = scaleE;
    $this['_range'] = range;
    $this['_mode'] = mode;
    return $this;
}

function parallaxOne($this) {
    var offset = $this['_offset'];
    var x = $this['_x'];
    var y = $this['_y'];
    var opacityO = $this['_opacityO'];
    var opacityE = $this['_opacityE'];
    var scaleO = $this['_scaleO'];
    var scaleE = $this['_scaleE'];
    var range = $this['_range'];
    var mode = $this['_mode'];

    var css = {}, translate, translateX, translateY, opacity, scale, r = getRange(scrollTop, offset, range);
    if (x !== undefined) {
        if (mode === 'static' && (r === 0 || r === 1)) translateX = '0px';
        if (mode === 'symmetry') {
            if (r === 0) translateX = x + 'px';
            if (r === 1) translateX = (offset - scrollTop) * x / range + 'px';
        }
        if (r === 2) translateX = (scrollTop - offset) * x / range + 'px';
        if (r === 3) translateX = x + 'px';
    }
    if (y !== undefined) {
        if (mode === 'symmetry') {
            if (r === 0) translateY = y + offset + range - scrollTop + 'px';
            if (r === 1) translateY = (offset - scrollTop) * y / range + 'px';
        }
        if (r === 2) translateY = (scrollTop - offset) * y / range + 'px';
    }
    if (mode === 'static' && (r === 0 || r === 1)) translateY = offset - scrollTop + 'px';
    if (mode !== undefined && r === 3) {
        if (y === undefined) y = 0;
        translateY = y + offset + range - scrollTop + 'px';
    }

    if (translateX && translateY) {
        translate =  'translate3d(' + translateX + ',' + translateY + ',0)';
    } else if (translateX) {
        translate = 'translate3d(' + translateX + ',0,0)';
    } else if (translateY) {
        translate = 'translate3d(0,' + translateY + ',0)';
    }

    if (scaleO !== undefined && scaleE !== undefined) {
        if (mode === 'static' && (r === 0 || r === 1)) scale = scaleO;
        if (mode === 'symmetry') {
            if (r === 0) scale = scaleE;
            if (r === 1) scale = (offset - scrollTop) / range * (scaleE - scaleO) + scaleO;
        }
        if (r === 2) scale = (scrollTop - offset) / range * (scaleE - scaleO) + scaleO;
        if (r === 3) scale = scaleE;
        scale = 'scale(' + scale + ')';
    }

    if (translate && scale) {
        css = {'-webkit-transform': translate + ' ' + scale, 'transform': translate + ' ' + scale};
    } else if (translate) {
        css = {'-webkit-transform': translate, 'transform': translate};
    } else if (scale) {
        css = {'-webkit-transform': scale, 'transform': scale};
    }

    if (opacityO !== undefined && opacityE !== undefined) {
        if (mode === 'symmetry') {
            if (r === 0) opacity = opacityE;
            if (r === 1) opacity = (offset - scrollTop) / range * (opacityE - opacityO) + opacityO;
        } else if (r === 0 || r === 1) {
            opacity = opacityO;
        }
        if (r === 2) opacity = (scrollTop - offset) / range * (opacityE - opacityO) + opacityO;
        if (r === 3) opacity = opacityE;
        if (opacity > 1) opacity = 1;
        css['opacity'] = opacity;
    }

    $this.css(css);
}

$('.main-back').on('mousewheel DOMMouseScroll', function (e) {
    e.preventDefault();
    if (isScrolling) return;
    var wheel = e.originalEvent.wheelDelta || -e.originalEvent.detail;
    var delta = Math.max(-1, Math.min(1, wheel));
    var index;
    if (delta < 0) {
        if (scrollTop >= $(document).height() - vh) return;
        index = parseInt(scrollTop / vh) + 1;
    } else {
        if (scrollTop <= 0) return;
        index = parseInt(scrollTop / vh) - 1;
        if (index < 0) index = 0;
    }
    scrollToMain(index);
});


$('.article-title, .article-category').on('mousewheel DOMMouseScroll', function (e) {
    e.preventDefault();
    var wheel = e.originalEvent.wheelDelta || -e.originalEvent.detail;
    var delta = Math.max(-1, Math.min(1, wheel));
    if (delta < 0) {
        nextCatePage();
    } else {
        prevCatePage();
    }
});

$(document).ready(function () {
    new Swiper('.banner-container', {
        // autoplay: {
        //     delay: 5000
        // },
        effect: 'fade',
        fade: {
            crossFade: true,
        },
        mousewheel: true,
        speed: 600,
        parallax : true,
        scrollbar: {
            el: '.banner-container .swiper-scrollbar',
            hide: true,
            draggable: true
        },
        navigation: {
            nextEl: '.banner-content .swiper-button-next',
            prevEl: '.banner-content .swiper-button-prev',
        }
    });

    layui.use(['layer', 'form'], function () {
        var layer = layui.layer
            ,form = layui.form;

        var $form = $('.article-category-form'), type = 1;

        $('.test').click(function () {
            layer.open({
                type: 1,
                area: area,
                title: '文章分类',
                scrollbar: false,
                content: $form,
                end: function () {
                    document.querySelector(".article-category-form").reset();
                }
            });
        });

        form.on('submit', function (data) {
            if (type === 1) {
                $.ajax({
                    url: '/articleCategory/add',
                    type: 'post',
                    data: data.field,
                    success: function (data) {
                        if (data.code === '000') {
                            type = 2;
                            $('#commitForm').text('修改');
                            notice.success({title: '成功添加分类'});
                            $form.find('[name=articleCategoryId]').val(data.result.articleCategoryId);
                        } else {
                            notice.fail({title: '发生错误', desc: data.msg});
                        }
                    }
                });
            } else {
                console.log(data.field);
            }

            return false;
        });
    });
});
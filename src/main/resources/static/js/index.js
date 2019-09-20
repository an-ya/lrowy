$(document).ready(function () {
    new Swiper('.banner-container', {
        autoplay: {
            delay: 5000
        },
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

    layui.use('layer', function () {
        var layer = layui.layer;

        $('.test').click(function () {

        });
    });
});

var page = 2, pageMin = 1, mode = 2, pageMax = Math.ceil($('.article-category-item-wrapper').length / 5), changingPage = false;

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

var vh = $(window).height(), vw = $(window).width(), isScrolling = false, scrollTop = 0, articleCategory = $('.article-category'), articleCategoryItem = articleCategory.children(), articleCategoryItemNum = articleCategoryItem.length;
var viewP = $('.view-p');

function preScrollEvent (scrollTop) {
    if (isScrolling) return;

    viewP.each(function () {
        var $this = $(this);
        var x = $this.data('viewX');
        var y = $this.data('viewY');
        var opacity = $this.data('viewOpacity');
    });
    // var target = articleCategory.offset().top;
    // if (scrollTop > target) {
    //     articleCategoryItem.each(function () {
    //         $(this).css({'opacity': 0, '-webkit-transform': 'translateY(-50px)', 'transform': 'translateY(-50px)'});
    //     });
    // } else {
    //     articleCategoryItem.each(function () {
    //         $(this).css({'opacity': 1, '-webkit-transform': 'translateY(0)', 'transform': 'translateY(0)'});
    //     });
    // }

    var target = $('.article-title').offset().top;
    if (scrollTop > target) {
        $('.category-page').removeClass('elastic-show');
        $('.category-page').addClass('elastic-hide');
        $('.article-title .t').removeClass('fade-in');
        $('.article-title .t').addClass('fade-out');
    } else {
        $('.category-page').removeClass('elastic-hide');
        $('.category-page').addClass('elastic-show');
        $('.article-title .t').removeClass('fade-out');
        $('.article-title .t').addClass('fade-in');
    }
}

function scrollToMain (index) {
    var scrollTop = $(this).scrollTop();
    isScrolling = true;

    if (scrollTop < $('.article-title').offset().top && vh * index > articleCategory.offset().top) {
        $('.category-page').removeClass('elastic-show');
        $('.category-page').addClass('elastic-hide');
        $('.article-title .t').removeClass('fade-in');
        $('.article-title .t').addClass('fade-out');
        setTimeout(function () {
            articleCategoryItem.each(function () {
                $(this).css({'opacity': 0, '-webkit-transform': 'translateY(-50px)', 'transform': 'translateY(-50px)'});
            });
        }, 300);
        setTimeout(function () {
            parallax.css('will-change', 'transform, opacity');
            $('html,body').animate({scrollTop: vh * index}, 1000);
            setTimeout(function () {
                isScrolling = false;
                parallax.css('will-change', 'auto');
            }, 1000);
        }, 300);
    } else if (scrollTop > articleCategory.offset().top && vh * index < $('.article-title').offset().top) {
        parallax.css('will-change', 'transform, opacity');
        $('html,body').animate({scrollTop: vh * index}, 1000, function () {
            articleCategoryItem.each(function () {
                $(this).css({'opacity': 1, '-webkit-transform': 'translateY(0)', 'transform': 'translateY(0)'});
            });
            setTimeout(function () {
                $('.category-page').removeClass('elastic-hide');
                $('.category-page').addClass('elastic-show');
                $('.article-title .t').removeClass('fade-out');
                $('.article-title .t').addClass('fade-in');
            }, 500);
        });
        setTimeout(function () {
            isScrolling = false;
            parallax.css('will-change', 'auto');
        }, 1000);
    } else {
        $('html,body').animate({scrollTop: vh * index}, 1000);
        setTimeout(function () {
            isScrolling = false;
        }, 1000);
    }
}

var parallax = $('.parallax');
scrollEvent();
parallax.css('visibility', 'visible');

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

function scrollEvent () {
    scrollTop = $(this).scrollTop();

    parallax.each(function () {
        var $this = $(this);
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

        var css = {}, translate, translateX, translateY, opacity, scale, r = getRange(scrollTop, offset, range);
        if (x !== undefined) {
            if (mode === 'static' && (r === 0 || r === 1)) translateX = '0px';
            if (mode === 'symmetry') {
                if (r === 0) translateX = x + 'px';
                if (r === 1) translateX = (offset - scrollTop) * x / range + 'px';
            }
            if (r === 2) translateX = (scrollTop - offset) * x / range + 'px';
            if (mode !== undefined && r === 3) translateX = x + 'px';
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
            translate =  'translate(' + translateX + ',' + translateY + ')';
        } else if (translateX) {
            translate = 'translate(' + translateX + ',0)';
        } else if (translateY) {
            translate = 'translate(0,' + translateY + ')';
        }

        if (scaleO !== undefined && scaleE !== undefined) {
            if (mode === 'static' && (r === 0 || r === 1)) scale = scaleO;
            if (mode === 'symmetry') {
                if (r === 0) scale = scaleE;
                if (r === 1) scale = (offset - scrollTop) / range * (scaleE - scaleO) + scaleO;
            }
            if (r === 2) scale = (scrollTop - offset) / range * (scaleE - scaleO) + scaleO;
            if (mode !== undefined && r === 3) scale = scaleE;
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
            if (mode !== undefined && r === 3) opacity = opacityE;
            if (opacity > 1) opacity = 1;
            css['opacity'] = opacity;
        }

        $this.css(css);
    });
    preScrollEvent(scrollTop);
}

$(document).scroll(scrollEvent);
$(window).resize(function () {
    vh = $(window).height();
    vw = $(window).width();
    scrollEvent();
});

$('.main-back').on('mousewheel DOMMouseScroll', onMouseScroll);
function onMouseScroll(e){
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
}

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
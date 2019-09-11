$(document).ready(function () {
    new Swiper('.banner-container', {
        autoplay: {
            delay: 5000
        },
        effect: 'fade',
        fade: {
            crossFade: true,
        },
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

var vh = $(window).height(), vw = $(window).width(), isScrolling = false, articleCategory = $('.article-category'), articleCategoryItem = articleCategory.children();

function preScrollEvent (scrollTop) {
    if (isScrolling) return;
    var target = articleCategory.offset().top;
    if (scrollTop > target) {
        articleCategoryItem.each(function () {
            $(this).css({'opacity': 0, '-webkit-transform': 'translateY(-50px)', 'transform': 'translateY(-50px)'});
        });
    } else {
        articleCategoryItem.each(function () {
            $(this).css({'opacity': 1, '-webkit-transform': 'translateY(0)', 'transform': 'translateY(0)'});
        });
    }

    target = $('.article-title').offset().top;
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
            $('html,body').animate({scrollTop: vh * index}, 1000);
            setTimeout(function () {
                isScrolling = false;
            }, 1000);
        }, 300);
    } else if (scrollTop > articleCategory.offset().top && vh * index < $('.article-title').offset().top) {
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
        }, 1000);
    } else {
        $('html,body').animate({scrollTop: vh * index}, 1000);
        setTimeout(function () {
            isScrolling = false;
        }, 1000);
    }
}

var parallax = $('.parallax');

function isParcent(result) {
    var pattern = new RegExp(/^-?\d+(\.\d+)?%$/);
    return pattern.test(result);
}

function toPoint(percent){
    var str = percent.replace("%","");
    str = str / 100;
    return str;
}

function scrollEvent () {
    var scrollTop = $(this).scrollTop();

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

        console.log(scrollTop);
        console.log(offset);

        var css = {}, translate, translateX, translateY, opacity, scale;
        if (x !== undefined && offset !== undefined && scrollTop > offset - range && scrollTop <= offset + range) translateX = Math.abs(scrollTop - offset) * x / range + 'px';
        if (y !== undefined && offset !== undefined && scrollTop > offset - range && scrollTop <= offset + range) translateY = Math.abs(scrollTop - offset) * y / range + 'px';

        if (translateX && translateY) {
            translate =  'translate(' + translateX + ',' + translateY + ')';
        } else if (translateX) {
            translate = 'translate(' + translateX + ')';
        } else if (translateY) {
            translate = 'translate(0,' + translateY + ')';
        }

        if (scaleO !== undefined && scaleE !== undefined && range !== undefined && offset !== undefined) {
            scale = Math.abs(scrollTop - offset) / range * (scaleE - scaleO) + scaleO;
            scale = 'scale(' + scale + ')';
        }

        if (translate && scale) {
            css = {'-webkit-transform': translate + ' ' + scale, 'transform': translate + ' ' + scale};
        } else if (translate) {
            css = {'-webkit-transform': translate, 'transform': translate};
        } else if (scale) {
            css = {'-webkit-transform': scale, 'transform': scale};
        }

        if (scrollTop > offset - range && scrollTop <= offset + range && opacityO !== undefined && opacityE !== undefined && range !== undefined && offset !== undefined) {
            opacity = Math.abs(scrollTop - offset) / range * (opacityE - opacityO) + opacityO;
            if (opacity > 1) opacity = 1;
            css['opacity'] = opacity;
        }

        console.log(css);
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
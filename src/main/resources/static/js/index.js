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
    var pattern = new RegExp(/^\d+%$/);
    return pattern.test(result);
}

function toPoint(percent){
    var str=percent.replace("%","");
    str= str/100;
    return str;
}

function scrollEvent () {
    var scrollTop = $(this).scrollTop(), translateX = -scrollTop / vh * 300 + 'px', translateY = -scrollTop / vh * vw * .4 + 'px', opacity = (1 - scrollTop / vh * 1.6) * .3;
    $('.mark-wrapper').css({'-webkit-transform': 'translate(' + translateX + ',' + translateY + ')', 'transform': 'translate(' + translateX + ',' + translateY + ')', 'opacity': opacity});

    parallax.each(function () {
        var $this = $(this);
        var scrollOffset = $this.data('parallaxScrollOffset');
        var x = $this.data('parallaxX');
        var y = $this.data('parallaxY');
        var opacityMin = $this.data('parallaxOpacityMin');
        var oacityMax = $this.data('parallaxOpacityMax');
        var opacityRange = $this.data('parallaxOpacityRange');

        if (scrollOffset && isParcent(scrollOffset)) {
            scrollOffset = toPoint(scrollOffset) * vh;
        }
        if (x && isParcent(x)) {
            x = toPoint(x) * vw;
        }
        if (y && isParcent(y)) {
            y = toPoint(y) * vh;
        }

        var css = {}, translateX, translateY;
        if (x && scrollOffset) translateX = (scrollTop - scrollOffset) / x;
        if (y && scrollOffset) translateY = (scrollTop - scrollOffset) / y;

        if (translateX && translateY) {
            css = {'-webkit-transform': 'translate(' + translateX + ',' + translateY + ')', 'transform': 'translate(' + translateX + ',' + translateY + ')'};
        } else if (translateX) {
            css = {'-webkit-transform': 'translate(' + translateX + ')', 'transform': 'translate(' + translateX + ')'};
        } else if (translateY) {
            css = {'-webkit-transform': 'translate(0,' + translateY + ')', 'transform': 'translate(0,' + translateY + ')'};
        }
    });

    var target = $('.banner-container').offset().top, height = $('.banner-container').height(), opacity2 = 1 - (scrollTop - target) / height, translateX2 = (scrollTop - target) / height * 46 + 'px';
    if (opacity2 < 0) opacity2 = 0;
    if (opacity2 > 1) opacity2 = 1;
    if (scrollTop > target) {
        $('.banner-content').css('opacity', opacity2);
        $('.swiper-button-prev').css({'-webkit-transform': 'translateX(' + translateX2 + ')', 'transform': 'translateX(' + translateX2 + ')'});
        $('.swiper-button-next').css({'-webkit-transform': 'translateX(-' + translateX2 + ')', 'transform': 'translateX(-' + translateX2 + ')'});
    } else {
        $('.banner-content').css({'opacity': 1});
    }
    preScrollEvent(scrollTop);
}

$(document).scroll(scrollEvent);
$(window).resize(function () {
    vh = $(window).height();
    vw = $(window).width();
});
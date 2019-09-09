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
    var target = articleCategory.offset().top;
    if (isScrolling) target -= vh * .4;
    if (target < 0) target = 0;
    if (scrollTop > target) {
        articleCategoryItem.each(function () {
            $(this).css({'opacity': 0, '-webkit-transform': 'translateY(-50px)', 'transform': 'translateY(-50px)'});
        });
    } else {
        articleCategoryItem.each(function () {
            $(this).css({'opacity': 1, '-webkit-transform': 'translateY(0)', 'transform': 'translateY(0)'});
        });
    }
}

function scrollToMain (index) {
    isScrolling = true;
    $('html,body').animate({scrollTop: vh * index}, 1000);
    setTimeout(function () {
        isScrolling = false;
    }, 1000);
}

function scrollEvent () {
    var scrollTop = $(this).scrollTop(), translateX = -scrollTop / vh * 300 + 'px', translateY = -scrollTop / vh * vw * .4 + 'px', opacity = (1 - scrollTop / vh * 1.6) * .3;
    $('.mark-wrapper').css({'-webkit-transform': 'translate(' + translateX + ',' + translateY + ')', 'transform': 'translate(' + translateX + ',' + translateY + ')', 'opacity': opacity});
    preScrollEvent(scrollTop);
}

$(document).scroll(scrollEvent);
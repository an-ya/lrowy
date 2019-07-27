$(document).ready(function () {
    new Swiper('.swiper-container', {
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
            el: '.swiper-scrollbar',
            hide: true,
            draggable: true
        },
        navigation: {
            nextEl: '.swiper-button-next',
            prevEl: '.swiper-button-prev',
        }
    });

    $('.test').click(function () {
        $.ajax({
            url: '/bookmark/add',
            type: 'post',
            data: {
                url: 'https://blog.csdn.net/qq_37385726/article/details/82020214'
            },
            success: function (data) {
                if (data.code === '000') {
                    console.log(data.result);
                }
            }
        });
    });
});
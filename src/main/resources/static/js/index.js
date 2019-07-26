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
            url: '/bookmark/init',
            type: 'post',
            success: function (data) {
                if (data.code === '000') {
                    console.log(data.result);
                }
            }
        });
    });
});
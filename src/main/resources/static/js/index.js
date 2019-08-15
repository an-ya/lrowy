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

    var sInfo = {
        eleWidth: 0,
        allEleWidth: 0,
        wrapperEleWidth: 0
    };
    getShortcutInfo();
    window.addEventListener('resize',getShortcutInfo());
    function getShortcutInfo() {
        var $wrapper = $('.shortcut-container .swiper-wrapper');
        var length = $wrapper.children().length;
        var eleWidth = 0, allEleWidth = 0, width, wrapperEleWidth;
        width = $wrapper.css('width');
        wrapperEleWidth = parseFloat(width.slice(0, width.indexOf('px')));
        if (length > 0) {
            width = $('.shortcut-container .shortcut:first').css('width');
            eleWidth = parseFloat(width.slice(0, width.indexOf('px')));
            allEleWidth = eleWidth * length;
        }
        sInfo = {eleWidth: eleWidth, allEleWidth: allEleWidth, wrapperEleWidth: wrapperEleWidth};
    }

    new Swiper('.shortcut-container',{
        freeMode: true,
        mousewheel: true,
        slidesPerView: 'auto',
        freeModeMomentum: false,
        watchSlidesProgress: true,
        on:{
            progress: function(progress) {
                for (var i = 0; i <= this.slides.length; i++) {
                    if (sInfo.allEleWidth * progress + sInfo.wrapperEleWidth * (1 - progress) >= (i + 1) * sInfo.eleWidth - 20) {
                        $(this.slides[i]).addClass('show');
                    } else {
                        $(this.slides[i]).removeClass('show');
                    }
                }
            }
        }
    });

    layui.use('layer', function (){
        var layer = layui.layer;

        $('.test').click(function () {
            $.ajax({
                url: '/bookmark/add',
                type: 'post',
                data: {
                    url: 'https://cn.vuejs.org/'
                },
                success: function (data) {
                    if (data.code === '000') {
                        console.log(data.result);
                    }
                }
            });
            // $.ajax({
            //     url: '/bookmark/add',
            //     type: 'post',
            //     data: {
            //         url: 'https://www.swiper.com.cn/'
            //     },
            //     success: function (data) {
            //         if (data.code === '000') {
            //             console.log(data.result);
            //         }
            //     }
            // });
            // layer.open({
            //     type: 2,
            //     title: '<div><img style="width: 20px;height: 20px;display: inline-block;" src="images/ball.png">很多时候，我们想最大化看，比如像这个页面。</div>',
            //     shadeClose: true,
            //     shade: false,
            //     maxmin: true, //开启最大化最小化按钮
            //     area: ['800px', '600px'],
            //     content: 'http://localhost:8080/'
            // });
        });
    });

    // notice.open({duration: 5, content: '<div class="notice-title">Welcome!</div>'});
});
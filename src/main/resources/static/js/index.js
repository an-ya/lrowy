var vh = $(window).height()
    ,vw = $(window).width()
    ,area = ['700px', '560px'];
if (vw < 900) {
    area[0] = 0.9 * vw + 'px';
    area[1] = 0.8 * vh + 'px';
}
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

function scrollEvent () {
    scrollTop = $(this).scrollTop();
}

var layer = layui.layer
    ,form = layui.form
    ,$form = $('.article-category-form')
    ,type = 1;

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
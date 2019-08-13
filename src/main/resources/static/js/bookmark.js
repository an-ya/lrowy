var data = {};

var s = new Swiper('.shortcut-container',{
    grabCursor: true,
    mousewheel: true,
    centeredSlides: true,
    slidesPerView: 'auto',
    on: {
        slideChangeTransitionStart: function () {
            selectCate(this.activeIndex);
        }
    }
});

var c = new Swiper('.bookmark-category-container', {
    mousewheel: true,
    slidesPerView: 5,
    on: {
        tap: function () {
            $('.bookmark-category-item-active').removeClass('bookmark-category-item-active');
            this.slides[this.clickedIndex].classList.add('bookmark-category-item-active');
            selectBook(this.clickedIndex);
        }
    }
});

function selectCate (index) {
    if (!data.shortcutList) return;
    var target = data.shortcutList[index].bookmarkCategoryId;
    for (var i = 0; i < data.bookmarkCategoryList.length; i++) {
        if (data.bookmarkCategoryList[i].bookmarkCategoryId === target) {
            $('.bookmark-category-item-active').removeClass('bookmark-category-item-active');
            c.slides[i].classList.add('bookmark-category-item-active');
        }
    }
}

function selectBook (index) {
    if (!data.bookmarkCategoryList) return;
    var target = data.bookmarkCategoryList[index].bookmarkCategoryId;
    for (var i = 0; i < data.shortcutList.length; i++) {
        if (data.shortcutList[i].bookmarkCategoryId === target) {
            s.slideTo(i);
            break;
        }
    }
}

$.ajax({
    url: '/bookmark/init',
    type: 'post',
    success: function (data) {
        if (data.code === '000') {
            window.data = data.result;
        }
    }
});
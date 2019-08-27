document.addEventListener('DOMContentLoaded', function () { FastClick.attach(document.body); }, false);

var menuButtonDom = '<li class="header-item"><button class="menu-button"><span>Menu</span></button></li>';

init();

function init() {
    $.ajax({
        url: '/init',
        type: 'post',
        success: function (data) {
            if (data.code === '000') {
                if (data.result) {
                    setMenuButton();
                } else {
                    setCLick();
                }
            }
        }
    });
}

function setMenuButton () {
    var menuButton = document.querySelector('.menu-button');
    var sidebar = document.querySelector('.sidebar');
    document.body.addEventListener('click', function (e) {
        var target = e.target;
        if (!(menuButton.contains(target) || menuButton === target) && !sidebar.contains(target)) {
            sidebar.classList.remove('sidebar-open');
            menuButton.classList.remove('menu-button-open');
        }
    });
    menuButton.addEventListener('click', function () {
        sidebar.classList.toggle('sidebar-open');
        menuButton.classList.toggle('menu-button-open');
    });

    var start = {}, end = {};
    document.body.addEventListener('touchstart', function (e) {
        start.x = e.changedTouches[0].clientX;
        start.y = e.changedTouches[0].clientY;
    });

    document.body.addEventListener('touchend', function (e) {
        end.y = e.changedTouches[0].clientY;
        end.x = e.changedTouches[0].clientX;

        var xDiff = end.x - start.x;
        var yDiff = end.y - start.y;

        if (Math.abs(xDiff) > Math.abs(yDiff)) {
            if (xDiff > 0 && start.x <= 80) {
                sidebar.classList.add('sidebar-open');
                menuButton.classList.add('menu-button-open');
            } else {
                sidebar.classList.remove('sidebar-open');
                menuButton.classList.remove('menu-button-open');
            }
        }
    });
}

function setOneDropdown (e) {
    var $dropdown = $(e.nextElementSibling);
    var target = false, ele = $dropdown.clone(true), height = $dropdown.height(), t, transform = {};
    e.addEventListener('click', function () {
        clearTimeout(t);
        if (!target) {
            this.focus();

            if (ele.hasClass('hidden')) {
                var top, left;
                if ($(this).offset().top + $(this).height() + height + 40 >= $(document).height()) {
                    transform = {'-webkit-transform': 'translateY(-20px)', 'transform': 'translateY(-20px)'};
                    top = $(this).offset().top - height - 20;
                } else {
                    transform = {'-webkit-transform': 'translateY(20px)', 'transform': 'translateY(20px)'};
                    top = $(this).offset().top + $(this).height();
                }

                if ($(this).offset().left - (140 - $(this).width()) <= 0) {
                    left = $(this).offset().left;
                } else {
                    left = $(this).offset().left - (140 - $(this).width())
                }

                if (left < 10) left = 10;
                if (left > $(document).width() - 150) left = $(document).width() - 150;

                ele.css({'visibility': 'visible', 'top': top, 'left': left});
                ele.css(transform);
                $('body').append(ele);
                setTimeout(function () {
                    ele.removeClass('hidden');
                    ele.css({'-webkit-transform': 'translateY(0)', 'transform': 'translateY(0)'});
                }, 14);
            } else {
                ele.addClass('hidden');
                ele.css(transform);
            }
        }
        target = false;
    });
    e.addEventListener('blur', function () {
        target = true;
        setTimeout(function () { target = false; }, 100);
        ele.addClass('hidden');
        ele.css(transform);
        t = setTimeout(function () { ele.detach(); }, 300);
    });
}

function setDropdown () {
    var dropdownTrigger = document.getElementsByClassName('dropdown-trigger');
    for (var i = 0; i < dropdownTrigger.length; i++) {
        setOneDropdown(dropdownTrigger[i]);
    }
}

function setCLick () {
    $('#login').click(function () {
        $.ajax({
            url: '/login',
            type: 'post',
            success: function (data) {
                if (data.code === '000') {
                    $('#login').parents('.header-item').replaceWith(menuButtonDom);
                    setMenuButton();
                }
            }
        });
    });
}


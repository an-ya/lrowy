document.addEventListener('DOMContentLoaded', function () { FastClick.attach(document.body); }, false);

var menuButtonDom = '<li class="header-item"><button class="menu-button"><span>Menu</span></button></li>';

init();

function init() {
    $.ajax({
        url: '/init',
        type: 'post',
        success: function (data) {
            if (data.code === '000') {
                setDropdown();
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

function setDropdown () {
    var dropdownTrigger = document.getElementsByClassName('dropdown-trigger');
    var Target = new Array(dropdownTrigger.length);
    for (var i = 0; i < dropdownTrigger.length; i++) {
        Target[i] = false;
        (function (arg) {
            dropdownTrigger[i].addEventListener('click', function () {
                if (!Target[arg]) {
                    this.focus();
                    var dropdown = this.nextElementSibling;
                    dropdown.classList.toggle('hidden');
                }
                Target[arg] = false;
            });
            dropdownTrigger[i].addEventListener('blur', function () {
                Target[arg] = true;
                var dropdown = this.nextElementSibling;
                dropdown.classList.add('hidden');
                setTimeout(function () {
                    Target[arg] = false;
                }, 100);
            });
        })(i);
    }
}

function setCLick () {
    document.querySelector('#login').addEventListener('click', function () {
        $.ajax({
            url: '/login',
            type: 'post',
            success: function (data) {
                if (data.code === '000') {
                    $('#login').parent().parent().parent().replaceWith(menuButtonDom);
                    setMenuButton();
                }
            }
        });
    });
}


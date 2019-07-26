document.addEventListener('DOMContentLoaded', function () { FastClick.attach(document.body); }, false);

let uA = navigator.userAgent,
    isMobile = uA.match(/AppleWebKit.*Mobile.*/),
    tap = isMobile ? 'touchstart' : 'mousedown',
    menuButtonDom = '<li class="header-item"><button class="menu-button"><span>Menu</span></button></li>';

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
                    setDropdown();
                    setCLick();
                }
            }
        }
    });
}

function setMenuButton () {
    let menuButton = document.querySelector('.menu-button');
    let sidebar = document.querySelector('.sidebar');
    document.body.addEventListener('click', function (e) {
        let target = e.target;
        if (!(menuButton.contains(target) || menuButton === target) && !sidebar.contains(target)) {
            sidebar.classList.remove('sidebar-open');
            menuButton.classList.remove('menu-button-open');
        }
    });
    menuButton.addEventListener('click', function () {
        sidebar.classList.toggle('sidebar-open');
        menuButton.classList.toggle('menu-button-open');
    });

    let start = {}, end = {};
    document.body.addEventListener('touchstart', function (e) {
        start.x = e.changedTouches[0].clientX;
        start.y = e.changedTouches[0].clientY;
    });

    document.body.addEventListener('touchend', function (e) {
        end.y = e.changedTouches[0].clientY;
        end.x = e.changedTouches[0].clientX;

        let xDiff = end.x - start.x;
        let yDiff = end.y - start.y;

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
    let dropdownTrigger = document.getElementsByClassName('dropdown-trigger');
    let Target = new Array(dropdownTrigger.length);
    for (let i = 0; i < dropdownTrigger.length; i++) {
        Target[i] = false;
        dropdownTrigger[i].addEventListener('click', function () {
            if (!Target[i]) {
                this.focus();
                let dropdown = this.nextElementSibling;
                dropdown.classList.toggle('hidden');
            }
            Target[i] = false;
        });
        dropdownTrigger[i].addEventListener('blur', function () {
            Target[i] = true;
            let dropdown = this.nextElementSibling;
            dropdown.classList.add('hidden');
            setTimeout(function () {
                Target[i] = false;
            }, 100);
        });
    }
}

function setCLick () {
    document.querySelector('#login').addEventListener(tap, function () {
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


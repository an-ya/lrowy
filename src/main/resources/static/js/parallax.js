var isScrolling = false, scrollTop = 0;
var $parallax = $('.parallax'), parallax = [], $viewP = $('.view-p'), viewP = [];
$parallax.css('visibility', 'visible');
for (var i = 0; i < $parallax.length; i++) { parallax.push(parallaxInitOne($($parallax[i]))); }
for (i = 0; i < $viewP.length; i++) { viewP.push(viewInitOne($($viewP[i]))); }
scrollEvent();

$(document).scroll(scrollEvent);
$(window).resize(function () {
    vh = $(window).height();
    vw = $(window).width();
    scrollEvent();
});

function scrollEvent () {
    scrollTop = $(this).scrollTop();

    // parallax.each(function () { parallaxOne($(this)); });
    for (var i = 0; i < parallax.length; i++) { parallaxOne(parallax[i]); }

    if (isScrolling) return;
    for (i = 0; i < viewP.length; i++) { viewOne(viewP[i]); }
}

function getViewResult ($this) {
    var x = $this.data('viewX');
    var y = $this.data('viewY');
    var opacity = $this.data('viewOpacity');
    var animIn = $this.data('viewAnimIn');
    var animOut = $this.data('viewAnimOut');

    if (animIn !== undefined && animOut !== undefined) {
        return {
            type: 1,
            animIn: animIn,
            animOut: animOut
        }
    } else {
        var cssO = {}, cssE = {}, translate, translateX, translateY;

        if (x !== undefined) {
            if (isPercent(x)) x = toPoint(x) * vw;
            translateX = x + 'px';
        }
        if (y !== undefined) {
            if (isPercent(y)) y = toPoint(y) * vh;
            translateY = y + 'px';
        }

        if (translateX && translateY) {
            translate =  'translate(' + translateX + ',' + translateY + ')';
        } else if (translateX) {
            translate = 'translate(' + translateX + ',0)';
        } else if (translateY) {
            translate = 'translate(0,' + translateY + ')';
        }

        if (translate) {
            cssO = {'-webkit-transform': 'translate(0, 0)', 'transform': 'translate(0, 0)'};
            cssE = {'-webkit-transform': translate, 'transform': translate};
        }

        if (opacity !== undefined) {
            cssO['opacity'] = 1;
            cssE['opacity'] = opacity;
        }

        return {
            type: 2,
            cssE: cssE,
            cssO: cssO
        }
    }
}

function viewInitOne ($this) {
    var children = [];
    var target = $this.data('viewTarget');
    var attr = $this.data('viewAttr');
    var mode = $this.data('viewMode');
    var delay = $this.data('viewDelay');
    var top = $this.offset().top, bottom = $this.height() + top;

    if (target === 'self') target = $this;
    else target = $this.children();
    if (mode === 'static') top = 10000;

    target.each(function () {
        var _this = $(this);
        var css;
        if (attr === 'children') css = getViewResult(_this);
        else css = getViewResult($this);
        _this['_css'] = css;
        children.push(_this);
    });

    $this['_target'] = children;
    $this['_attr'] = attr;
    $this['_mode'] = mode;
    $this['_delay'] = delay;
    $this['_top'] = top;
    $this['_bottom'] = bottom;
    return $this;
}

function viewOne ($this) {
    var target = $this['_target'];
    var top = $this['_top'], bottom = $this['_bottom'];

    for (var i = 0; i < target.length; i++) {
        var css = target[i]['_css'];
        if (css.type === 1) {
            if (scrollTop > top || scrollTop + vh < bottom) {
                target[i].removeClass(css.animIn);
                target[i].addClass(css.animOut);
            } else {
                target[i].removeClass(css.animOut);
                target[i].addClass(css.animIn);
            }
        } else {
            if (scrollTop > top || scrollTop + vh < bottom) {
                target[i].css(css.cssE);
            } else {
                target[i].css(css.cssO);
            }
        }
    }
}

function isPercent (result) {
    var pattern = new RegExp(/^-?\d+(\.\d+)?%$/);
    return pattern.test(result);
}

function toPoint (percent) {
    var str = percent.replace("%","");
    str = str / 100;
    return str;
}

function getRange (scrollTop, offset, range) {
    if (scrollTop < offset - range) {
        return 0
    } else if (scrollTop >= offset - range && scrollTop <= offset) {
        return 1
    } else if (scrollTop >= offset && scrollTop <= offset + range) {
        return 2
    } else {
        return 3
    }
}

function parallaxInitOne ($this) {
    var offset = $this.data('parallaxOffset');
    var x = $this.data('parallaxX');
    var y = $this.data('parallaxY');
    var opacityO = $this.data('parallaxOpacityO');
    var opacityE = $this.data('parallaxOpacityE');
    var scaleO = $this.data('parallaxScaleO');
    var scaleE = $this.data('parallaxScaleE');
    var range = $this.data('parallaxRange');
    var mode = $this.data('parallaxMode');

    if (offset !== undefined) {
        if (offset === 'parent.top') {
            offset = $this.parent().offset().top;
        }
        if (offset === 'top') {
            offset = $this.offset().top;
        }
        if (isPercent(offset)) {
            offset = toPoint(offset) * vh;
        }
    } else {
        offset = 0;
    }
    if (x !== undefined && isPercent(x)) x = toPoint(x) * vw;
    if (y !== undefined && isPercent(y)) y = toPoint(y) * vh;
    if (range !== undefined) {
        if (range === 'parent.height') {
            range = $this.parent().height();
        }
        if (range === 'height') {
            range = $this.height();
        }
        if (isPercent(range)) {
            range = toPoint(range) * vh;
        }
    } else {
        range = vh;
    }

    $this['_offset'] = offset;
    $this['_x'] = x;
    $this['_y'] = y;
    $this['_opacityO'] = opacityO;
    $this['_opacityE'] = opacityE;
    $this['_scaleO'] = scaleO;
    $this['_scaleE'] = scaleE;
    $this['_range'] = range;
    $this['_mode'] = mode;
    return $this;
}

function parallaxOne ($this) {
    var offset = $this['_offset'];
    var x = $this['_x'];
    var y = $this['_y'];
    var opacityO = $this['_opacityO'];
    var opacityE = $this['_opacityE'];
    var scaleO = $this['_scaleO'];
    var scaleE = $this['_scaleE'];
    var range = $this['_range'];
    var mode = $this['_mode'];

    var css = {}, translate, translateX, translateY, opacity, scale, r = getRange(scrollTop, offset, range);
    if (x !== undefined) {
        if (mode === 'static' && (r === 0 || r === 1)) translateX = '0px';
        if (mode === 'symmetry') {
            if (r === 0) translateX = x + 'px';
            if (r === 1) translateX = (offset - scrollTop) * x / range + 'px';
        }
        if (r === 2) translateX = (scrollTop - offset) * x / range + 'px';
        if (r === 3) translateX = x + 'px';
    }
    if (y !== undefined) {
        if (mode === 'symmetry') {
            if (r === 0) translateY = y + offset + range - scrollTop + 'px';
            if (r === 1) translateY = (offset - scrollTop) * y / range + 'px';
        }
        if (r === 2) translateY = (scrollTop - offset) * y / range + 'px';
    }
    if (mode === 'static' && (r === 0 || r === 1)) translateY = offset - scrollTop + 'px';
    if (mode !== undefined && r === 3) {
        if (y === undefined) y = 0;
        translateY = y + offset + range - scrollTop + 'px';
    }

    if (translateX && translateY) {
        translate =  'translate3d(' + translateX + ',' + translateY + ',0)';
    } else if (translateX) {
        translate = 'translate3d(' + translateX + ',0,0)';
    } else if (translateY) {
        translate = 'translate3d(0,' + translateY + ',0)';
    }

    if (scaleO !== undefined && scaleE !== undefined) {
        if (mode === 'static' && (r === 0 || r === 1)) scale = scaleO;
        if (mode === 'symmetry') {
            if (r === 0) scale = scaleE;
            if (r === 1) scale = (offset - scrollTop) / range * (scaleE - scaleO) + scaleO;
        }
        if (r === 2) scale = (scrollTop - offset) / range * (scaleE - scaleO) + scaleO;
        if (r === 3) scale = scaleE;
        scale = 'scale(' + scale + ')';
    }

    if (translate && scale) {
        css = {'-webkit-transform': translate + ' ' + scale, 'transform': translate + ' ' + scale};
    } else if (translate) {
        css = {'-webkit-transform': translate, 'transform': translate};
    } else if (scale) {
        css = {'-webkit-transform': scale, 'transform': scale};
    }

    if (opacityO !== undefined && opacityE !== undefined) {
        if (mode === 'symmetry') {
            if (r === 0) opacity = opacityE;
            if (r === 1) opacity = (offset - scrollTop) / range * (opacityE - opacityO) + opacityO;
        } else if (r === 0 || r === 1) {
            opacity = opacityO;
        }
        if (r === 2) opacity = (scrollTop - offset) / range * (opacityE - opacityO) + opacityO;
        if (r === 3) opacity = opacityE;
        if (opacity > 1) opacity = 1;
        css['opacity'] = opacity;
    }

    $this.css(css);
}
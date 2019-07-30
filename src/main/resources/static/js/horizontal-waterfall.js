/*!
   --------------------------------
   horizontal-waterfall.js
   --------------------------------
   + 参考自Waterfall.js:https://github.com/raphamorim/waterfall.js/blob/master/src/waterfall.js
   + version 1.0.0
*/

(function (root, factory) {
    if (typeof define === 'function' && define.amd) {
        define('waterfall', function () {
            return factory
        })
    } else if (typeof module === 'object' && module.exports) {
        module.exports = factory
    } else {
        root.waterfall = factory
    }
}(this, function waterfall (container) {
    if (typeof (container) === 'string') {
        container = document.querySelector(container)
    }

    function style (el) {
        return window.getComputedStyle(el)
    }

    function margin (name, el) {
        return parseFloat(style(el)['margin' + name]) || 0
    }

    function px (n) { return parseFloat(n) + 'px' }
    function y (el) { return parseFloat(el.style.top) }
    function x (el) { return parseFloat(el.style.left) }
    function width (el) { return parseFloat(style(el).width) }
    function height (el) { return parseFloat(style(el).height) }
    function bottom (el) { return y(el) + height(el) + margin('Bottom', el) }
    function right (el) { return x(el) + width(el) + margin('Right', el) }

    function sort (l) {
        l.sort(function (a, b) {
            var rightDiff = right(b) - right(a)
            return rightDiff || y(b) - y(a)
        })
    }

    function Boundary (firstColumn) {
        var els = firstColumn
        sort(els)

        this.add = function (el) {
            els.push(el)
            sort(els)
            els.pop()
        }

        this.min = function () { return els[els.length - 1] }
        this.max = function () { return els[0] }
    }

    function placeEl (el, top, left) {
        el.style.position = 'absolute'
        el.style.top = px(top)
        el.style.left = px(left)
    }

    function placeFirstElement (el) {
        placeEl(el, 0, margin('Left', el))
    }

    function placeAtTheFirstColumn (prev, el) {
        placeEl(el, bottom(prev) + margin('Top', el), x(prev))
    }

    function placeAtTheSmallestColumn (minEl, el) {
        placeEl(el, minEl.style.top, right(minEl) + margin('Left', el))
    }

    function adjustContainer (container, maxEl) {
        container.style.position = 'relative'
        container.style.width = px(right(maxEl) + margin('Right', maxEl))
    }

    function thereIsSpace (els, i) {
        return bottom(els[i - 1]) + height(els[i]) <= height(container)
    }

    var els = container.children

    if (els.length) {
        placeFirstElement(els[0])
    }

    for (var i = 1; i < els.length && thereIsSpace(els, i); i++) {
        placeAtTheFirstColumn(els[i - 1], els[i])
    }

    var firstColumn = [].slice.call(els, 0, i)
    var boundary = new Boundary(firstColumn)

    for (; i < els.length; i++) {
        placeAtTheSmallestColumn(boundary.min(), els[i])
        boundary.add(els[i])
    }

    adjustContainer(container, boundary.max())
}))
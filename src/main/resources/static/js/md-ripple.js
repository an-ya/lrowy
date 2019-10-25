/**
 * Material Design Button Ripple jQuery Plugin - CodePen
 * BY Young Park
 * https://codepen.io/parkyoung555/pen/brMXZv
 */

(function($){
    "use strict";

    $.fn.extend({
        mdRipple: function(options){
            options = $.extend({
                // Default options

            }, options);
            var namespace = 'mdRipple',
                coreClasses = {
                    button: 'md-ripple-active',
                    iconButton: 'md-icon-button',
                    container: 'md-ripple-container',
                    ripple: 'md-ripple',
                    focus: 'md-focus'
                },
                coreAttributes = {
                    color: 'ripple-color',
                    noRipple: 'no-ripple'
                },
                rippleTemplate = '<svg preserveAspectRatio="none" viewBox="25 25 50 50"><circle cx="50" cy="50" r="25"/></svg>',
                mouseDown = false;

            // General element actions
            _initialize(this);

            // Individual element actions
            this.each(function(){
                var elem = $(this);
                elem.on(_namespaceEvents('mousedown'), _mouseDown);
                elem.on(_namespaceEvents('focus'), _drawFocus);
                elem.on(_namespaceEvents('blur'), _clearContainer);
            });

            return this;

            /////////////////////////////////

            function _initialize(elems){
                // Unbind anything within plugin namespace to prevent duplicate event bindings
                elems.off('.' + namespace);
                _addRippleContainerTo(elems);
            }

            function _addRippleContainerTo(elem) {
                var rippleContainer = elem.find('.' + coreClasses.container).get(0);
                if (!rippleContainer) {
                    rippleContainer = document.createElement('div');
                    rippleContainer.className = coreClasses.container;
                    elem.addClass(coreClasses.button).append($(rippleContainer));
                }
            }

            function _getMouseClickCoords(elem, e) {
                var container = elem,
                    coords = {};

                if (!e || elem.hasClass(coreClasses.iconButton)){
                    coords.x = container.outerWidth() / 2;
                    coords.y = container.outerHeight() / 2;
                } else {
                    coords.x = e.pageX - container.offset().left;
                    coords.y = e.pageY - container.offset().top;
                }

                return coords;
            }

            function _mouseDown(e) {
                var elem = $(this);
                if (!mouseDown && !elem.is(":disabled") && !elem.data(coreAttributes.noRipple)) {
                    mouseDown = true;
                    var coords = _getMouseClickCoords(elem, e);
                    _removeFocus(elem);
                    _drawRipple(elem, coords);
                }
                e.preventDefault();
            }

            function _drawRipple(elem, coords) {
                var container = elem.find('.' + coreClasses.container).first(),
                    color = elem.data(coreAttributes.color),
                    ripple = $(rippleTemplate),
                    containerDims = {
                        width: container.outerWidth(),
                        height: container.outerHeight()
                    },
                    initialDiameter = (containerDims.width > containerDims.height ? containerDims.width : containerDims.height) * .5,
                    greatestWidth, greatestHeight, diameter = 1, scale = 1,
                    vm = this;

                ripple.addClass(coreClasses.ripple).css({
                    'fill': !!color ? color : '',
                    'transform': 'translate(' + coords.x + 'px, ' + coords.y + 'px) translate(-50%, -50%)',
                    'opacity': .5,
                    'width': initialDiameter,
                    'height': initialDiameter
                });
                container.append(ripple);

                if(coords.x >= containerDims.width / 2){
                    greatestWidth = coords.x;
                }
                else{
                    greatestWidth = containerDims.width - coords.x;
                }
                if(coords.y >= containerDims.height / 2){
                    greatestHeight = coords.y;
                }
                else{
                    greatestHeight = containerDims.height - coords.y;
                }

                diameter = Math.sqrt(Math.pow(greatestWidth, 2) + Math.pow(greatestHeight, 2)) * 2.05;
                scale = (diameter / initialDiameter);

                // Forces the browser to calculate an actual value
                ripple.css('opacity');
                ripple.css('transform');
                ripple.css({
                    'opacity': 1,
                    'transform': 'translate(' + coords.x + 'px,' + coords.y + 'px) translate(-50%,-50%) scale(' + scale + ')'
                });

                elem.bind(_namespaceEvents('mouseup mouseleave'), function(e){
                    mouseDown = false;
                    _hideRipple(ripple);
                    $(this).unbind(_namespaceEvents('mouseup mouseleave'));
                });
            }

            function _drawFocus(){
                var elem = $(this);

                if(!elem.is(":active")) {
                    var container = elem.find('.' + coreClasses.container).first(),
                        color = elem.data(coreAttributes.color),
                        ripple = $(rippleTemplate),
                        coords = _getMouseClickCoords(elem);

                    // Destroy all previous instances of focus ripple
                    container.find('.' + coreClasses.focus).remove();

                    ripple.addClass(coreClasses.focus).css({
                        'fill': !!color ? color : '',
                        'transform': 'translate(' + coords.x + 'px, ' + coords.y + 'px) translate(-50%,-50%)',
                        'width': coords.x * 2 * .9,
                        'height': coords.x * 2 * .9
                    });

                    container.append(ripple);
                }
            }

            function _hideRipple(elem) {
                elem.css('opacity', 0);
                elem.one(_namespaceEvents('transitionend webkitTransitionEnd oTransitionEnd MSTransitionEnd'), function() {
                    $(this).remove();
                });
            }

            function _removeFocus(elem) {
                elem.find('.' + coreClasses.container + ' .' + coreClasses.focus).remove();
            }

            function _clearContainer() {
                $(this).find('.' + coreClasses.container).find('.' + coreClasses.focus + ', .' + coreClasses.ripple).remove();
            }

            function _namespaceEvents(eventString) {
                return eventString.trim().split(/\s+/).map(function(a){ return a + '.' + namespace; }).reduce(function(a, b){ return a + ' ' + b });
            }
        }
    });
})(jQuery);
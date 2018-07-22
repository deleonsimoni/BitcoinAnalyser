angular.module('angular.qrcode', [])
    .directive('qrcode', ['$window', function($window) {

        var options = {
            render: 'canvas',
            ecLevel: 'H',
            minVersion: 6,
            fill: '#000',
            text: null,
            size: 250,
            mode: 4,
            mSize: parseInt(10, 10) * 0.01,
            image: $(new Image()).context
        };

        return {
            restrict: 'E',
            template: '<canvas class="qrcode"></canvas>',
            link: function(scope, element, attrs) {
                var domElement = element[0],
                    $canvas = element.find('canvas'),
                    canvas = $canvas[0],
                    setVersion = function(value) {
                        options.minVersion = Math.max(1, Math.min(parseInt(value, 10), 40)) || 5;
                    },
                    setData = function(value) {
                        if (!value) {
                            return;
                        }
                        options.text = value;
                    },
                    setImage = function(value) {
                        if (!value) return;
                        var image = $(new Image());
                        image.load(function(){
                            render();
                        })
                        options.image = image.attr('src',value).context
                    },
                    setFill = function(value) {
                        if (!value) return;
                        options.fill = value
                    },
                    setSize = function(value) {
                        options.size = parseInt(value, 10);
                        if (canvas) canvas.width = canvas.height = options.size;
                    },
                    setCorrection = function(value) {
                        options.ecLevel = value;
                    },
                    render = function() {
                        try {
                            setSize(options.size);
                            $($canvas).qrcode(options);
                        } catch (error) {
                            console.error(error);
                        }

                    };

                attrs.$observe('version', function(value) {
                    if (!value) {
                        return;
                    }

                    setVersion(value);
                    render();
                });

                attrs.$observe('data', function(value) {
                    if (!value) {
                        return;
                    }

                    setData(value);
                    render();
                });

                attrs.$observe('image', function(value) {
                    if (!value) {
                        return;
                    }

                    setImage(value);
                    render();
                });

                attrs.$observe('fill', function(value) {
                    if (!value) {
                        return;
                    }

                    setFill(value);
                    render();
                });

                attrs.$observe('correction', function(value) {
                    if (!value) {
                        return;
                    }

                    setCorrection(value);
                    render();
                });

                attrs.$observe('size', function(value) {
                    if (!value) {
                        return;
                    }

                    setSize(value);
                    render();
                });

            }
        };
    }]);

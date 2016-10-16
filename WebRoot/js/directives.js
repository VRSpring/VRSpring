vr.directive('endRepeat', ['$timeout', function ($timeout) {
        return {
            restrict: 'A',
            link: function (scope, element, attr) {
                if (scope.$last === true) {
                    $timeout(function () {
                        scope.$emit('ngRepeatFinished');
                    });
                }
            }
        }
    }])
    .directive('qrcode', ['$window', function ($window) {

        var canvas2D = !!$window.CanvasRenderingContext2D,
            levels = {
                'L': 'Low',
                'M': 'Medium',
                'Q': 'Quartile',
                'H': 'High'
            },
            drawImage = function (context, img, sw, sh, w, h) {
                context.drawImage(img, 0, 0, sw, sh, 0, 0, w, h);
            }
        draw = function (context, startx, starty, qr, modules, tile) {
            for (var row = 0; row < modules; row++) {
                for (var col = 0; col < modules; col++) {
                    var w = (Math.ceil((col + 1) * tile) - Math.floor(col * tile)),
                        h = (Math.ceil((row + 1) * tile) - Math.floor(row * tile));

                    context.fillStyle = qr.isDark(row, col) ? '#000' : '#fff';
                    context.fillRect(startx + Math.round(col * tile),
                        starty + Math.round(row * tile), w, h);
                }
            }
        };

        return {
            restrict: 'E',
            template: '<canvas class="qrcode"></canvas>',
            link: function (scope, element, attrs) {
                var domElement = element[0],
                    $canvas = element.find('canvas'),
                    canvas = $canvas[0],
                    context = canvas2D ? canvas.getContext('2d') : null,
                    download = 'download' in attrs,
                    href = attrs.href,
                    link = download || href ? document.createElement('a') : '',
                    trim = /^\s+|\s+$/g,
                    error,
                    version,
                    errorCorrectionLevel,
                    data,
                    size,
                    modules,
                    tile,
                    qr,
                    img='<img src="">',
                    setVersion = function (value) {
                        version = Math.max(1, Math.min(parseInt(value, 10), 40)) || 5;
                    },
                    setErrorCorrectionLevel = function (value) {
                        errorCorrectionLevel = value in levels ? value : 'M';
                    },
                    setData = function (value) {
                        if (!value) {
                            return;
                        }

                        data = value.replace(trim, '');
                        qr = qrcode(version, errorCorrectionLevel);
                        qr.addData(data);

                        try {
                            qr.make();
                        } catch (e) {
                            error = e.message;
                            return;
                        }

                        error = false;
                        modules = qr.getModuleCount();
                    },
                    setSize = function (value) {
                        size = parseInt(value, 10) || modules * 2;
                        tile = size / modules;
                    },
                    setCanvasSize = function (width, height) {
                        canvas.width = width
                        canvas.height = height;
                    },
                    render = function () {
                        var w = $bg.width;
                        var h = $bg.height;
                        setCanvasSize(w, h);
                        if (!qr) {
                            return;
                        }
                        if (error) {
                            if (link) {
                                link.removeAttribute('download');
                                link.title = '';
                                link.href = '#_';
                            }
                            if (!canvas2D) {
                                domElement.innerHTML = '<img src width="' + size + '"' +
                                    'height="' + size + '"' +
                                    'class="qrcode">';
                            }
                            scope.$emit('qrcode:error', error);
                            return;
                        }
                        if (href) {
                            domElement.download = 'qrcode.png';
                            domElement.title = 'Download QR code';
                            domElement.href = href;
                        }
                        if (canvas2D) {
                            drawImage(context, $bg, $bg.width, $bg.height, w, h);
                            var startx = 0.5 * (w - modules * tile);
                            var starty = (h - modules * tile) -   Math.floor(h*0.03);
                            draw(context, startx, starty, qr, modules, tile);
                            if (download) {
                                $(domElement).find('img')[0].src = canvas.toDataURL('image/png');
                                canvas.remove();
                                return;
                            }
                        }

                    };
                if (link) {
                    link.className = 'qrcode-link';
                    $canvas.wrap(link);
                    if(download){
                        $canvas.parent().prepend(img);
                    }
                    domElement = domElement.firstChild;
                }
                $bg = new Image;
                $bg.src = attrs.bg;
                $bg.onload = function () {
                    size=Math.floor($bg.width*0.3);
                    setSize(size);
                    render();
                }
                setVersion(attrs.version);
                setErrorCorrectionLevel(attrs.errorCorrectionLevel);
                setSize(attrs.size);

                attrs.$observe('version', function (value) {
                    if (!value) {
                        return;
                    }

                    setVersion(value);
                    setData(data);
                    setSize(size);
                    render();
                });

                attrs.$observe('errorCorrectionLevel', function (value) {
                    if (!value) {
                        return;
                    }

                    setErrorCorrectionLevel(value);
                    setData(data);
                    setSize(size);
                    render();
                });

                attrs.$observe('data', function (value) {
                    if (!value) {
                        return;
                    }

                    setData(value);
                    setSize(size);
                    render();
                });

                attrs.$observe('size', function (value) {
                    if (!value) {
                        return;
                    }

                    setSize(value);
                    render();
                });

                attrs.$observe('href', function (value) {
                    if (!value) {
                        return;
                    }

                    href = value;
                    render();
                });
            }
        };
    }]);
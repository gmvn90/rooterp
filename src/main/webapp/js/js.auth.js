(function($) {
    var _checkAuth = false;
    var _pageCode;
    // Attrs
    $.fn.attrs = function(attrs) {
        var t = $(this);
        if (attrs) {
// Set attributes
            t.each(function(i, e) {
                var j = $(e);
                for (var attr in attrs) {
                    j.attr(attr, attrs[attr]);
                }
                ;
            });
            return t;
        } else {
// Get attributes
            var a = {},
                    r = t.get(0);
            if (r) {
                r = r.attributes;
                for (var i in r) {
                    var p = r[i];
                    if (typeof p.nodeValue !== 'undefined')
                        a[p.nodeName] = p.nodeValue;
                }
            }
            return a;
        }
    };
    //check auth
    //k is event ("click","dblclick")
    //options: action, auth_func, not_auth_func
    $.fn.auth = function(options) {
        var element = this;
        var k = options.action;
        var auth_func = (options.auth_func != undefined && $.isArray(options.auth_func)) ? options.auth_func : []
        var not_auth_func = (options.not_auth_func != undefined && $.isArray(options.not_auth_func)) ? options.not_auth_func : []
        for (var i = 0; i < element.length; i++) {
            var el = element[i];
            var result = _checkFullAuthorization($(el));
            if (!result) {
                var arr = $._data($(document).get(0), "events")[k];
                for (var j = 0; j < arr.length; j++) {
                    if (arr[j].selector != undefined) {
                        //if id
                        if (arr[j].selector == "#" + el.id || $.inArray(arr[j].selector.slice(1),
                                ($(el).attrs().class != undefined) ? $(el).attrs().class.split(" ") : "") > -1) {
                            if ($._data($(document).get(0), "events")[k][j] != undefined) {
                                $._data($(document).get(0), "events")[k][j] = {};
                            }
                            if ($._data($("#" + el.id).get(0), "events") != undefined) {
                                $._data($("#" + el.id).get(0), "events")[k] = {};
                            }
                        }
                        //if class
//                    if (arr[j].selector == "." + el.class) {
//                        $(el.name + "." + el.class).addClass("disabled");
//                    }
                    }
                }
                if (auth_func.length > 0) {
                    _applyFunction(auth_func);
                }
            } else {
                if (not_auth_func.length > 0) {
                    _applyFunction(not_auth_func);
                }
            }
        }

    }

    function _isAuthorized() {
//console.log("in isAuthorized. checkAuth = " + checkAuth);
        if (!_checkAuth) {
//        jqXHR.abort();
            //console.log('abort ajax call.');
            $('#preloader').fadeOut(300);
            $('#preloader').remove();
            return false;
        }
    }

    function _checkFullAuthorization(element) {
        var result = _checkConnection();
        if ((element !== null) && (element !== undefined) && (element.attr("name") !== '') && (element.attr("name") !== undefined)) {
            var arr = element.attr("name").split('_');
            _pageCode = arr.pop();
//        xmlHttpRequest = jqXHR;
            if (!isNaN(Number(_pageCode))) {
                if (result) {
                    _checkSessionAndAuthorization(_pageCode);
                    _isAuthorized();
                }
            }
        }
        return _checkAuth;
    }

    function _checkSessionAndAuthorization(page_code) {
//check session
        _checkAuth = _checkSession();
        if (_checkAuth) {
//console.log("has session.");
//check authorization
            _checkAuth = _checkAuthorization(page_code);
//            if (checkAuth) {
////do main function
//                //console.log("has permission.");
//            } else {
//                alert("You don't have permission. Please contact administartor for further information.");
////            jqXHR.abort();
//            }
        }
        return _checkAuth;
    }

    function _checkSession() {
        var hasSession;
        $.ajax({
            type: 'POST',
            async: false,
            url: getAbsolutePath() + "/userSession.htm",
            success: function(response) {
//console.log(response);
                hasSession = (response === 'true');
            }
        });
        return hasSession;
    }

    function _checkAuthorization(page_code) {
        var hasAuthorization;
        $.ajax({
            type: 'POST',
            async: false,
            data: {page_code: page_code},
            url: getAbsolutePath() + "/authorization.htm",
            success: function(response) {
                hasAuthorization = (response === 'true');
            }
        });
        return hasAuthorization;
    }

    function _checkConnection() {
        var result = false;
        $.ajax({
            cache: false,
            async: false,
            url: getAbsolutePath() + '/test.html',
            timeout: 1000,
            success: function(html) {
                result = true;
            }
            ,
            error: function(XMLHttpRequest, textStatus, errorThrown) {
//console.log(textStatus);
                if ((textStatus === 'timeout') || (textStatus === 'error')) {
                    if ($("#preloader1").length === 0) {
                        _errorDialog('0.6');
                    }
                }
            }
        });
        return result;
    }


    function _applyFunction(f) {
        if (f != null) {
            for (var i = 0; i < f.length; i++) {
                window[f[i].name].apply(this, f[i].params);
            }
        }
    }

    function _errorDialog(opac) {

        if ($("#overlay1").length === 0) {
            $('body').append('<div id="overlay1" style="display: block !important; opacity:' + opac + '"></div>');
        }
        //var opacity = (opac !== null) ? parseFloat(opac) : 1;

        //$('#overlay').css('opacity', opacity);
        //$("body").append("<div id='message' style='display: none'><span>Connect to server failed. wait a moment for reconnecting...</span></div>");
//    $("#message").append("<input style=\"width: 80px; margin-right: auto; margin-left: auto\" type=\"button\" onclick='checkFullAuthorizationEmptyParam()' value='Try Again'/>");
//    $("#message").attr("style", "position: absolute; border: 1px solid white;padding: 15px;background: #000;-webkit-border-radius: 10px;-moz-border-radius: 10px;opacity: .8;color: #fff; z-index: 9999; left: 40%; top: 30%; font-size: 11px");
        if ($("#preloader1").length == 0) {
            $("body").append("<div id=\"preloader1\" style='width: 270px'><span>Lost connection...</span><input style=\"width: 80px; float: right; margin-top: -3px\" type=\"button\" onclick='_checkFullAuthorizationEmptyParam()' value='Try Again'/></div>");
            $("#preloader1 input[type=button]").button();
        }
        $('#preloader1').fadeIn(600);
    }
    
    function _closeOpacity() {
        $('#detailBox').fadeOut(200);
        $('#overlay').fadeOut(400);
        $('#overlay').remove();
        $('#detailBox').remove();
    }
    
    
function _checkFullAuthorizationEmptyParam() {
    //console.log(pageCode);
    result = checkConnection();
    if (result) {
        _checkSessionAndAuthorization(_pageCode);
    }
}

})(jQuery);
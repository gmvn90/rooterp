var checkAuth = false;
var disconnect = false;
var pageCode = '';
var xmlHttpRequest = '';
var request;

// First, checks if it isn't implemented yet.
if (!String.prototype.format) {
  String.prototype.format = function() {
    var args = arguments;
    return this.replace(/{(\d+)}/g, function(match, number) { 
      return typeof args[number] != 'undefined'
        ? args[number]
        : match
      ;
    });
  };
}

function checkNumber(string) {
    var reg = /^-?\d+\.?\d*$/;
    return reg.test(string);
}

var specialCase = ["", "-"];


$(document).ready(function() {
    $('.numberOnly').each(function() {
       var elem = $(this);
       var that = this;
       // Save current value of element
       elem.data('oldVal', elem.val());
       var timeoutSetNumber;

       // Look for changes in the value
        elem.bind("propertychange change click keyup input paste", function(event){
            console.log(event);
          // If value has changed...
            if (elem.data('oldVal') != elem.val()) {
               // Updated stored value
               
               console.log(elem.data('oldVal'), elem.val(), checkNumber(elem.val()));
               if(! checkNumber(elem.val())) {
                    if(specialCase.indexOf(elem.val()) >  -1) {
                        timeoutSetNumber = setTimeout(function() {
                            elem.val(elem.data('oldVal'));
                        }, 2000);
                    } else {
                        elem.val(elem.data('oldVal'));
                    }
                    
               } else {
                    elem.data('oldVal', elem.val());
                    clearTimeout(timeoutSetNumber);
               }
               
               // Do action
            }
        });
    });
    
});

function goToPage(pageWithHttp) {
    location.href = pageWithHttp;
}

// is used in object compress
function getFormData(unindexed_array){
    var indexed_array = {};

    $.map(unindexed_array, function(n, i){
        indexed_array[n['name']] = n['value'];
    });

    return indexed_array;
}

function getJsonFormData(unindexed_array){
    return JSON.stringify(getFormData(unindexed_array));
}




$.getScript(getAbsolutePath() + "/js/jquery.bind-first-0.2.0.js", function() {
    $("input:button[name^=perm_]").liveFirst('click', function() {
        //console.log("beforeSend.");
        checkFullAuthorization($(this));
        //console.log("ID: " + $(this).attr("name"));
//            isAuthorized();
        //console.log("end beforeSend.");
    });
    $("tr[class*=data_row]").liveFirst("dblclick", function() {
        //console.log("beforeSend.");
        checkFullAuthorization($(this));
//            isAuthorized();
        //console.log("end beforeSend.");
    });
    $("ul.menu li a,ul.ref_list li").liveFirst('click', function() {
        //console.log("beforeSend.");
        checkConnection();
//        if (!checkAuth){
//            $(this).off("click");
//        }
//            isAuthorized();
//        console.log("end beforeSend.");
    });
});

$(document).on("click", ".filter li,.ref_list li", function() {
    var child = $(this).parent("ul").children("li");
    for (var i = 0; i < child.length; i++) {
        if ($(child[i]).hasClass("chosen")) {
            $(child[i]).toggleClass("chosen");
        }
    }
    $(this).addClass("chosen");
}).on("keypress", ".numberOnly", function(eve) {
    
}).on("keyup", ".numberOnly", function(eve) {
    
}).on("keyup", ".text_filter_child", function() {
    //var id = $(this).attr("id").split("_")[0];
    searchFilter("#" + $(this).siblings(".filter").attr("id"), $(this).val().toLowerCase());
}).on("keyup", ".text_filter", function() {
    doFilter("#" + $(this).siblings(".filter").attr("id"), $(this).val().toLowerCase());
}).on("click", ".data_row", function() {
    $(".chosen_row").toggleClass("chosen_row");
    $(this).addClass("chosen_row");
});

$(document).ready(function() {

    $(".timePicker").timepicker();

    $("input:button[name^=perm_]").auth({action: "click"});
//
//    if (!checkSession() && !document.URL.split("/")[document.URL.split("/").length - 1] === "login.htm") {
//        showLogin();
//    }
    $(":text:visible:enabled:not(.date_picker_text)").filter(function() {
        return $(this).parents().filter(function() {
            return this.style.display == "none";
        }).size() == 0;
    }).eq(0).focus();
    $(".date_picker_text").datepicker({
        dateFormat: "dd-mm-yy",
        changeMonth: true,
        changeYear: true,
        yearRange: "1945:2050"
    });
    $("input[type=button]").button();
    $("input[type=submit]").button();

    $("#btn_search_date").click(function() {
        openDialog("market_form", "search_basis");
    });

    $("#btn_search_stock").click(function() {
        openDialog("stock_form", "search_historical");
    });

    //show tool tip
    $('.masterTooltip').hover(function() {
        // Hover over code
        var title = $(this).attr('title');
        $(this).data('tipText', title).removeAttr('title');
        $('<p class="tooltip"></p>')
                .text(title)
                .appendTo('body')
                .fadeIn('slow');
    }, function() {
        // Hover out code
        $(this).attr('title', $(this).data('tipText'));
        $('.tooltip').remove();
    }).mousemove(function(e) {
        var mousex = e.pageX + 20; //Get X coordinates
        var mousey = e.pageY + 10; //Get Y coordinates
        $('.tooltip')
                .css({
            top: mousey,
            left: mousex
        });
    });

    getCurrentDateTimeText(".current_date_time");
});

function tableSroll(id, jsonData) {
    $(id).tableScroll(jsonData);
}

function create_hidden_form(form_info, elements_info) {

    if ($("#" + form_info.id) === true) {
        $("#" + form_info.id).attr("action", form_info.action);
        $("#" + form_info.id).attr("method", form_info.method);
    } else {
        $("body").append("<form id='" + form_info.id + "' action='" + form_info.action + "' method='" + form_info.method + "'></form>");
    }
    if (elements_info !== null && elements_info.length > 0) {
        for (var i = 0; i < elements_info.length; i++) {
            var obj = elements_info[i];
            $("#" + form_info.id).append("<input type='hidden' id='" + obj.id + "' name='" + obj.name + "' value='" + obj.value + "'>");
        }
    }
}


function getSelection(form_id, target_id, action, selected_value, functionName, params, asynchronus) {
    if (selected_value != "-1") {
        $("#" + target_id).html("");
    }
    var as = false;
    $("#" + form_id).append("<input type='hidden' name='selected_value' value='" + selected_value + "'/>");
    //console.log("selected value : " + $("input[name=selected_value]").val());
    //console.log(as);
    if ($("#overlay").length === 0) {
        $('body').append('<div id="overlay"></div><div id="preloader">Loading..</div>');
    }
    $('#overlay').css('opacity', 0.6).fadeIn(400);
    $('#preloader').fadeIn(600);
    request = $.ajax({
        type: 'POST',
        async: as,
        data: $("#" + form_id).serialize(),
        url: getAbsolutePath() + "/" + action + ".htm",
        success: function(msg) {
            if (msg.trim() !== "") {
                $("#" + target_id).append(msg);
            }
        }
    }).done(function() {
        $('#preloader').fadeOut(300);
        $('#overlay').fadeOut(400);
        $('#overlay').remove();
        $("input[type=button]").button();
        $("input[name=selected_value]").remove();
        $(".date_picker_text").datepicker({
            dateFormat: "dd-mm-yy",
            changeMonth: true,
            changeYear: true,
            yearRange: "1945:2050"
        });
        $(".timePicker").timepicker();
        if (functionName != null) {
            if (params != null) {
                window[functionName].apply(this, params);
            } else {
                window[functionName]();
            }
        }
    });
}

function getSelectionNotFade(form_id, target_id, action, selected_value, functionName, params, asynchronus) {
    if (selected_value != "-1") {
        $("#" + target_id).html("");
    }
    var as = false;
    $("#" + form_id).append("<input type='hidden' name='selected_value' value='" + selected_value + "'/>");
    //console.log("selected value : " + $("input[name=selected_value]").val());
    //console.log(as);
    if ($("#overlay").length === 0) {
        $('body').append('<div id="overlay"></div><div id="preloader">Loading..</div>');
    }
//    $('#overlay').css('opacity', 0.6).fadeIn(400);
//    $('#preloader').fadeIn(600);
    request = $.ajax({
        type: 'POST',
        async: as,
        data: $("#" + form_id).serialize(),
        url: getAbsolutePath() + "/" + action + ".htm",
        success: function(msg) {
            if (msg.trim() !== "") {
                $("#" + target_id).append(msg);
            }
        }
    }).done(function() {
        $("input[type=button]").button();
        $("input[name=selected_value]").remove();
        if (functionName != null) {
            if (params != null) {
                window[functionName].apply(this, params);
            } else {
                window[functionName]();
            }
        }
    });
}

function applyFunction(f) {
    if (f != null) {
        for (var i = 0; i < f.length; i++) {
            window[f[i].name].apply(this, f[i].params);
        }
    }
}

function sendRequest(form_id, target_id, action, functions, asynchronus) {
    var as = true;
    if (asynchronus != undefined) {
        as = asynchronus;
    }
    if ($("#overlay").length === 0) {
        $('body').append('<div id="overlay"></div><div id="preloader">Loading..</div>');
    }
    $('#overlay').css('opacity', 0.6).fadeIn(400);
    $('#preloader').fadeIn(600);
    request = $.ajax({
        type: 'POST',
        async: as,
        data: $("#" + form_id).serialize(),
        url: getAbsolutePath() + "/" + action + ".htm",
        success: function(msg) {
            if (msg.trim() !== "") {
                $("#" + target_id).append(msg);
            }
        }
    }).done(function() {
        $('#preloader').fadeOut(300);
        $('#overlay').fadeOut(400);
        $('#overlay').remove();
        $("input[type=button]").button();
        $('#preloader').remove();
        $(".date_picker_text").datepicker({
            dateFormat: "dd-mm-yy",
            changeMonth: true,
            changeYear: true,
            yearRange: "1945:2050"
        });
        $(".timePicker").timepicker();
        applyFunction(functions);
    });
}

function sendRequestHtmlWithoutAuth(form_id, target_id, action, functions) {
    //console.log("sendRequestHtml.");

    if ($("#overlay") !== true) {
        $('body').append('<div id="overlay"></div><div id="preloader">Loading..</div>');
    }
    $('#overlay').css('opacity', 0.6).fadeIn(400);
    $('#preloader').fadeIn(600);
    request = $.ajax({
        type: 'POST',
        data: $("#" + form_id).serialize(),
        url: getAbsolutePath() + "/" + action + ".htm",
        success: function(msg) {
            //console.log("sendRequestHtml success.");
            if (msg.trim() !== "") {
                $("#" + target_id).html(msg);
            }
        }
    }).done(function() {
        $('#preloader').fadeOut(300);
        $('#overlay').fadeOut(400);
        $('#overlay').remove();
        $('#preloader').remove();
        $("input[type=button]").button();
        $(".date_picker_text").datepicker({
            dateFormat: "dd-mm-yy",
            changeMonth: true,
            changeYear: true,
            yearRange: "1945:2050"
        });
        $(".timePicker").timepicker();
        applyFunction(functions);

    });

}
// Status: 
//   -1: do nothing, means just send request
//    0: replace html, 
//    1: append to target_id element, 
//    2: is used for text field (get text), 
//    3: set value for an input type
//    4: open dialog, (optional params will be style of dialog)
//          optional syntax for open dialog: {"opac":"0.6", "styles":[{"name":"display","style":"block"}]}
//    5: add data to body with msg format 'info1,info2,info3'
//          optional will be the name of these info with syntax: ["info1_name","info2_name","info3_name"]
//    6: return message when completed
// json is json object
function sendJsonRequest(target_id, action, json, status, functions, asynchronous, optional) {
    var opac = 0.6;
    if (optional != undefined) {
        opac = optional.opac;
    }
    var as = true;
    if (asynchronous != undefined) {
        as = asynchronous;
    }
    if ($("#overlay").length == 0) {
        $('body').append('<div id="overlay"></div><div id="preloader">Loading..</div>');
    }
    $('#overlay').css('opacity', opac).fadeIn(400);
    $('#preloader').fadeIn(600);
    request = $.ajax({
        type: 'POST',
        async: as,
        data: json,
        url: getAbsolutePath() + "/" + action + ".htm",
        success: function(msg) {

            switch (status) {
                case 0:
                    $("#" + target_id).html(msg);
                    break;
                case 1:
                    //console.log("#" + target_id + ": " + $("#" + target_id).text());
                    $("#" + target_id).append(msg);
                    break;
                case 2:
                    $("#" + target_id).text(msg);
                    break;
                case 3:
                    $("#" + target_id).val(msg);
                    break;
                case 4:
                    if (optional != undefined) {
                        if ($('#detailBox').length == 0) {
                            $("#" + target_id).append("<div id='detailBox'></div>");
                        }
                        $("#detailBox").html(msg);
                        var dialog_styles = optional.styles;
                        if (dialog_styles !== undefined && dialog_styles !== null && dialog_styles.length > 0) {
                            for (var i = 0; i < dialog_styles.length; i++) {
                                $("#detailBox").css(dialog_styles[i].name, dialog_styles[i].style);
                            }
                        }

                        $('#preloader').fadeOut(300);
                        $('#detailBox').fadeIn(400);
                    }
                    break;
                case 5:
                    var msg_array = JSON.parse(msg);
                    //console.log("JSON : " + msg);
                    if (optional != undefined && optional.length == msg_array.length) {
                        for (var i = 0; i < msg_array.length; i++) {
                            $.data(document.body, optional[i], msg_array[i]);
                        }
                    }
                    break;
                case 6:
                    callMessageDialog({type: "info", message: msg});
                    break
            }

        }
    }).done(function() {

        if (status != 4) {
            $('#overlay').fadeOut(400);
            $('#overlay').remove();
        }
        $('#preloader').fadeOut(300);
        $("input[type=button]").button();
        $("input[type=submit]").button();
        $('#preloader').remove();
        $(".date_picker_text").datepicker({
            dateFormat: "dd-mm-yy",
            changeMonth: true,
            changeYear: true,
            yearRange: "1945:2050"
        });
        $(".timePicker").timepicker();
        applyFunction(functions);
    });
}

function sendRequestHtml(form_id, target_id, action) {
    //console.log("sendRequestHtml.");
    if (checkAuth) {
        if ($("#overlay") !== true) {
            $('body').append('<div id="overlay"></div><div id="preloader">Loading..</div>');
        }
        $('#overlay').css('opacity', 0.6).fadeIn(400);
        $('#preloader').fadeIn(600);
        request = $.ajax({
            type: 'POST',
            data: $("#" + form_id).serialize(),
            url: getAbsolutePath() + "/" + action + ".htm",
            success: function(msg) {
                //console.log("sendRequestHtml success.");
                if (msg.trim() !== "") {
                    $("#" + target_id).html(msg);
                }
            }
        }).done(function() {
            $('#preloader').fadeOut(300);
            $('#overlay').fadeOut(400);
            $('#overlay').remove();
            $('#preloader').remove();
            $("input[type=button]").button();
            $(".date_picker_text").datepicker({
                dateFormat: "dd-mm-yy",
                changeMonth: true,
                changeYear: true,
                yearRange: "1945:2050"
            });
            $(".timePicker").timepicker();
        });
    }
}

function sendRequestNotFade(form_id, target_id, action, functions) {
    $("#action").attr("value", action);
    if ($("#overlay") !== true) {
        $('body').append('<div id="overlay"></div><div id="preloader">Loading..</div>');
    }
    //    $('#overlay').css('opacity',0.6).fadeIn(400);
    //    $('#preloader').fadeIn(600);
    $.ajax({
        type: 'POST',
        data: $("#" + form_id).serialize(),
        url: getAbsolutePath() + "/" + action + ".htm",
        success: function(msg) {
            if (msg.trim() !== "") {
                $("#" + target_id).html(msg);
            }
        }
    }).done(function() {
        $("input[type=button]").button();
        $('.masterTooltip').hover(function() {
            // Hover over code
            var title = $(this).attr('title');
            $(this).data('tipText', title).removeAttr('title');
            $('<p class="tooltip"></p>')
                    .text(title)
                    .appendTo('body')
                    .fadeIn('slow');
        }, function() {
            // Hover out code
            $(this).attr('title', $(this).data('tipText'));
            $('.tooltip').remove();
        }).mousemove(function(e) {
            var mousex = e.pageX + 20; //Get X coordinates
            var mousey = e.pageY + 10; //Get Y coordinates
            $('.tooltip')
                    .css({
                top: mousey,
                left: mousex
            });
        });
        $(".timePicker").timepicker();
        applyFunction(functions);
    });
}

//khong can check authorization.
function getInputTextValue(form_id, target_id, action, functions, callback) {
    $("#action").attr("value", action);
    if ($("#overlay") !== true) {
        $('body').append('<div id="overlay"></div><div id="preloader">Loading..</div>');
    }
    $('#overlay').css('opacity', 0.6).fadeIn(400);
    $('#preloader').fadeIn(600);

    $.ajax({
        type: 'POST',
        data: $("#" + form_id).serialize(),
        url: getAbsolutePath() + "/" + action + ".htm",
        success: function(msg) {
            //console.log("input value :" + msg);
            if (msg.trim() !== "") {
                $("#" + target_id).val(msg);
            }
        }
    }).done(function() {
        $('#preloader').fadeOut(300);
        $('#overlay').fadeOut(400);
        $('#overlay').remove();
        $('#preloader').remove();
        applyFunction(functions);
        $(".timePicker").timepicker();
        if (callback != undefined) {
            callback();
        }
    });

}

function saveAndAppend(form_id, target_id, action) {
    $("#action").attr("value", action);
    if ($("#overlay") != true) {
        $('body').append('<div id="overlay"></div><div id="preloader">Loading..</div>');
    }
    $('#overlay').css('opacity', 0.6).fadeIn(400);
    $('#preloader').fadeIn(600);
    request = $.ajax({
        type: 'POST',
        data: $("#" + form_id).serialize(),
        url: getAbsolutePath() + "/" + $("#" + form_id).attr("action"),
        success: function(msg) {
            $("#" + target_id).append(msg);
        }
    }).done(function() {

        $('#preloader').fadeOut(300);
        $('#overlay').fadeOut(400);
        $('#overlay').remove();
        $('#preloader').remove();
        $("input[type=button]").button();
        $(".timePicker").timepicker();
    });
}

function getSelections(form_id, target_id, action, select_id, select_value, functionName, params) {
    $("#action").attr("value", action);
    $.ajax({
        type: 'POST',
        data: $("#" + form_id).serialize(),
        url: getAbsolutePath() + "/" + $("#" + form_id).attr("action"),
        success: function(msg) {
            $("#" + target_id).html(msg);

        }
    }).done(function() {
        getSelectedOption(target_id, select_id, select_value);

        if (functionName !== undefined && functionName !== null) {
            //console.log("params : " + params[0]);
            if (params !== undefined && params !== null) {
                window[functionName].apply(this, params);
            } else {
                window[functionName]();
            }
        }
    });
}

function getSelectedOption(target_id, select_id, select_value) {
    var flag = false;

    if (select_id === "true")
        select_id = 1;
    if (select_id === "false")
        select_id = 0;

    $("#" + target_id + " option").each(function() {

        if (target_id === "dropdown_hedge_price") {
            //console.log($(this).attr("value"));
        }
//        console.log($(this.val()));
        if ($(this).attr("value") == select_id) {
//            $("#" + target_id).val($(this).val());
            $(this).attr('selected', true);
//            $(this).select();
            flag = true;
        }
    });

    if (!flag) {
        $("#" + target_id).append("<option value='" + select_id + "' selected>" + select_value + "</option>");
    }
}

function errorDialog(opac) {

    if ($("#overlay1").length === 0) {
        $('body').append('<div id="overlay1" style="display: block !important; opacity:' + opac + '"></div>');
    }
    //var opacity = (opac !== null) ? parseFloat(opac) : 1;

    //$('#overlay').css('opacity', opacity);
    //$("body").append("<div id='message' style='display: none'><span>Connect to server failed. wait a moment for reconnecting...</span></div>");
//    $("#message").append("<input style=\"width: 80px; margin-right: auto; margin-left: auto\" type=\"button\" onclick='checkFullAuthorizationEmptyParam()' value='Try Again'/>");
//    $("#message").attr("style", "position: absolute; border: 1px solid white;padding: 15px;background: #000;-webkit-border-radius: 10px;-moz-border-radius: 10px;opacity: .8;color: #fff; z-index: 9999; left: 40%; top: 30%; font-size: 11px");
    if ($("#preloader1").length == 0) {
        $("body").append("<div id=\"preloader1\" style='width: 270px'><span>Lost connection...</span><input style=\"width: 80px; float: right; margin-top: -3px\" type=\"button\" onclick='checkFullAuthorizationEmptyParam()' value='Try Again'/></div>");
        $("#preloader1 input[type=button]").button();
    }
    $('#preloader1').fadeIn(600);
}

function openDialog(form_id, action, opac, dialog_styles) {
    //console.log('start open dialog');
    $("#action").attr("value", action);

    if ($("#overlay").length == 0) {
        $('body').append('<div id="overlay"></div><div id="preloader">Loading..</div>');
    }
    var opacity = (opac !== null) ? opac : 1;
    $('#overlay').css('opacity', opacity).fadeIn(400, function() {
        $('#preloader').fadeIn(600);
        request = $.ajax({
            type: 'POST',
            cache: false,
            data: $("#" + form_id).serialize(),
            url: getAbsolutePath() + "/" + action + ".htm",
            success: function(msg) {
                //console.log("open dialog.");

                if ($('#detailBox').length === 0) {
                    $("#" + form_id).append("<div id='detailBox'></div>");
                }
                $("#detailBox").html(msg);
                //console.log(dialog_styles);
                if (dialog_styles !== undefined && dialog_styles !== null && dialog_styles.length > 0) {
                    for (var i = 0; i < dialog_styles.length; i++) {
                        //console.log(dialog_styles[i].name);
                        $("#detailBox").css(dialog_styles[i].name, dialog_styles[i].style);
                    }
                }

                $('#preloader').fadeOut(300);
                $('#detailBox').fadeIn(400);
            }
        }).done(function() {
            $("#overlay").click(function() {
                closeOpacity();
            });
            $("input[type=button]").button();
            $(".date_picker_text").datepicker({
                dateFormat: "dd-mm-yy",
                changeMonth: true,
                changeYear: true,
                yearRange: "1945:2050"
            });
            $(".close_opacity").click(function() {
                closeOpacity();
            });

            $('#preloader').remove();

        });
    });
}

function openDialog1(form_id, action, opac, dialog_styles) {
    //console.log('start open dialog');
    $("#action").attr("value", action);

    if ($("#overlay1").length == 0) {
        $('body').append('<div id="overlay1"></div><div id="preloader">Loading..</div>');
    }
    var opacity = (opac !== null) ? opac : 1;
    $('#overlay1').css('opacity', opacity).fadeIn(400, function() {
        $('#preloader1').fadeIn(600);
        request = $.ajax({
            type: 'POST',
            cache: false,
            data: $("#" + form_id).serialize(),
            url: getAbsolutePath() + "/" + action + ".htm",
            success: function(msg) {
                //console.log("open dialog.");

                if ($('#detailBox1').length === 0) {
                    $("#" + form_id).append("<div id='detailBox1'></div>");
                }
                $("#detailBox1").html(msg);
                //console.log(dialog_styles);
                if (dialog_styles !== undefined && dialog_styles !== null && dialog_styles.length > 0) {
                    for (var i = 0; i < dialog_styles.length; i++) {
                        //console.log(dialog_styles[i].name);
                        $("#detailBox1").css(dialog_styles[i].name, dialog_styles[i].style);
                    }
                }
                $('#detailBox1').css("z-index", "100005");
                $('#overlay1').css("z-index", "100001");
                $('#preloader1').fadeOut(300);
                $('#detailBox1').fadeIn(400);
            }
        }).done(function() {
            $("#overlay1").click(function() {
                $('#detailBox1').fadeOut(200);
                $('#overlay1').fadeOut(400);
                $('#overlay1').remove();
                $('#detailBox1').remove();
            });
            $(".close_opacity").click(function() {
                $('#detailBox1').fadeOut(200);
                $('#overlay1').fadeOut(400);
                $('#overlay1').remove();
                $('#detailBox1').remove();
            });
            $('#preloader1').remove();
        });
    });
}

function closeOpacity() {
    $('#detailBox').fadeOut(200);
    $('#overlay').fadeOut(400);
    $('#overlay').remove();
    $('#preloader').remove();
    $('#detailBox').remove();

}

function formatDateFromDatabase(target_id, value, flag) {
    var date = new Date(value);
    if (flag) {
        $("#" + target_id).text($.format.date(date, "dd-MMM-yy hh:mm:ss a"));
    } else {
        $("#" + target_id).text($.format.date(date, "dd-MMM-yy"));
    }
}

function formatDateFromDatabaseTxt(target_id, value, flag) {
    var date = new Date(value);
    //console.log(date);
    if (flag) {
        $("#" + target_id).attr("value", $.format.date(date, "dd-MMM-yy hh:mm:ss a"));
    } else {
        $("#" + target_id).attr("value", $.format.date(date, "dd-MMM-yy"));
    }
}

/** 
 * type:
 *      1: text
 *      2: input value
 * @param {HTML element}  target
 * @param {int} type
 * @returns {date}
 */
function getCurrentDateTimeText(target, type) {
    var now = new Date;
    switch (type) {
        case 1:
            $(target).text($.format.date(now, "dd-MMM-yy hh:mm:ss a"));
            break
        case 2:
            $(target).val($.format.date(now, "dd-MMM-yy hh:mm:ss a"));
            break;
    }
}
/** 
 * type:
 *      1: text
 *      2: input value
 * @param {HTML element}  target
 * @param {int} type
 * @returns {date}
 */
function getCurrentDateText(target, type) {
    var now = new Date;
    switch (type) {
        case 1:
            $(target).text($.format.date(now, "dd-MMM-yy"));
            break
        case 2:
            $(target).val($.format.date(now, "dd-MMM-yy"));
            break;
    }

}

function setDefaultValueOnBlur(target_id, default_value) {
    $("#" + target_id).on("blur", function() {
        setDefaultValue(target_id, default_value)
    })
}
function setDefaultValue(target_id, default_value) {
    if ($("#" + target_id).val().trim() == '') {
        $("#" + target_id).attr("value", default_value);
    }
}

function countTotal(className, return_target_id) {
    var total = 0;
    $("." + className).each(function() {
        total = parseFloat(total) + parseFloat($(this).val());
    })
    $("#" + return_target_id).text(Number(total).toFixed(2));
}

function countTotalForLabel(className, return_target_id) {
    var total = 0;
    $("." + className).each(function() {
        var wtAvg = $(this).text();

        if (wtAvg.charAt(0) == '(' || wtAvg.charAt(wtAvg.length - 1) == ')') {
            wtAvg = wtAvg.replace('(', '');
            wtAvg = wtAvg.replace(')', '')
            total = parseFloat(total) + parseFloat('-' + wtAvg);
        } else {
            total = parseFloat(total) + parseFloat(wtAvg);
        }
    })
    total = total.toFixed(2);
    if (total < 0) {
        total = '(' + total.toString().substring(1) + ')';
    }
    $("#" + return_target_id).text(total);
}

function copyValue(res_id, dest_id) {
    $("#" + dest_id).val($("#" + res_id).val());
}

function tonsToLots(tons) {
    //console.log(tons);
//    console.log(tons % 10);
    if (tons % 10 < 5) {
        return Math.floor(tons / 10);
    } else {
        return Math.ceil(tons / 10);
    }
}

function toUSD(price, exchangeRate) {
    return Number(price) / Number(exchangeRate);
}

function showLogin() {
    if ($("#overlay") !== true) {
        $('body').append('<div id="overlay"></div><div id="preloader">Loading..</div>');
    }
    $('#overlay').css('opacity', 0.6).fadeIn(400, function() {
        $('#preloader').fadeIn(600);
        $.ajax({
            type: 'POST',
            cache: false,
            url: getAbsolutePath() + "/show_login.htm",
            success: function(msg) {

                if ($('#detailBox') !== true) {
                    $("form").append("<div id='detailBox'></div>");
                }
                $("#detailBox").html(msg);

                $('#preloader').fadeOut(300);
                $('#detailBox').fadeIn(400);
            }
        }).done(function() {
            $("#overlay").click(function() {
                closeOpacity();
            });
            $("input[type=button]").button();
            $(".close_opacity").click(function() {
                closeOpacity();
            });
            $('#preloader').remove();
        });
    });
}

function checkFullAuthorizationEmptyParam() {
    //console.log(pageCode);
    result = checkConnection();
    if (result) {
        checkSessionAndAuthorization(pageCode, xmlHttpRequest);
    }
}

function checkFullAuthorization(element) {
    result = checkConnection();
    if ((element !== null) && (element !== undefined) && (element.attr("name") !== '') && (element.attr("name") !== undefined)) {
        var arr = element.attr("name").split('_');

        pageCode = arr.pop();
//        xmlHttpRequest = jqXHR;
        if (!isNaN(Number(pageCode))) {
            if (result) {
                checkSessionAndAuthorization(pageCode);
                isAuthorized();
            }
        }
    }
}

function checkConnection() {
    var result = false;
    $.ajax({
        cache: false,
        async: false,
        url: getAbsolutePath() + '/test.html',
        timeout: 1000,
        success: function(html) {
            result = true;
            $("#overlay1").remove();
            $("#preloader1").remove();
        }
        ,
        error: function(XMLHttpRequest, textStatus, errorThrown) {
            //console.log(textStatus);
            if ((textStatus === 'timeout') || (textStatus === 'error')) {
                if ($("#preloader1").length === 0) {
                    errorDialog('0.6');
                }
            }
        }
    });
    return result;
}

function checkAuthorization(page_code) {
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

function checkSession() {
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

function checkSessionAndAuthorization(page_code) {
//check session
    checkAuth = checkSession();
    if (checkAuth) {
        //console.log("has session.");
        //check authorization
        checkAuth = checkAuthorization(page_code);
        if (checkAuth) {
//do main function
            //console.log("has permission.");
        } else {
            //callMessageDialog({message: "You don't have permission. Please contact administartor for further information.", type: "warning"});
            $.warning("You don't have permission. Please contact administartor for further information.");
//            jqXHR.abort();
        }
        return checkAuth;
    } else {
        showLogin();
    }
}

//function redirect(page_code) {
//    checkConnection();
//    $.ajax({
//        type: 'POST',
//        cache: false,
//        data: {page_code: page_code},
//        url: getAbsolutePath() + "/redirect.htm",
//        success: function(response) {
//            var page = JSON.parse(response);
//            checkSessionAndAuthorization(page.page_code);
//            if (checkAuth) {
//                //console.log(page.servlet);
//                //console.log(page.page_code);
//                var forms = {"id": "hidden_form", "action": getAbsolutePath() + "/" + page.url + ".htm", "method": "get"};
//                var elements = new Array();
//                elements.push(
//                        {"id": "url", "name": "url", "value": page.url},
//                {"id": "page_code", "name": "page_code", "value": page.page_code}
//                );
//                create_hidden_form(forms, elements);
//                $("#hidden_form").submit();
//            }
//        }
//    });
//}

function isAuthorized() {
    //console.log("in isAuthorized. checkAuth = " + checkAuth);
    if (!checkAuth) {
        //closeOpacity();
//        jqXHR.abort();
        //console.log('abort ajax call.');
        $('#preloader').fadeOut(300);
        $('#preloader').remove();

        return false;
    }
}

function tonsToBags(tons, kg_per_bag, target_id) {
    if (!isNaN(Number(tons)) && !isNaN(Number(kg_per_bag))) {
        //console.log((tons * 1000) + " / " + kg_per_bag);
        $("#" + target_id).val(Math.ceil((tons * 1000) / kg_per_bag));
    } else {
        $("#" + target_id).val(0);
    }
}

function selectValue(el, value) {
    if ($(el).is("select")) {
        $(el + " option").each(function() {
            if ($(this).val() == value) {
                $(this).prop("selected", true);
            }
        });
    }
    if ($(el).is("ul")) {
        $(el + "li.chosen").toggleClass("chosen")
        $(el + " li[id=" + value + "]").addClass("chosen");
    }
}

function selectText(id, value) {
    $("#" + id + " option").each(function() {
        if ($(this).text() === value) {
            $(this).attr("selected", true);
        }
    });
}
function checkCompleteInst(d) {
    var result = $.data(document.body, "result");
    //console.log(result);
    if (result == -1) {
        $.warning("Please Complete Weight Note Before Complete This");
        //callMessageDialog({type: "warning", message: "Please Complete Weight Note Before Complete This"});
        $(d).prop("checked", false);
    } else {
        changeChkBoxVal(d);
    }
}

function changeChkBoxVal(d) {
    //console.log($(d).val());
    var val = $(d).val();
    $(d).attr("value", (val == 0) ? "1" : "0");
}

//check the length of ul
//for example view processing.jsp
function checklistlength(ul_id) {
    var count = 0;
    $("#" + ul_id + " li").each(function() {
        count++;
    });
    return count;
}

function addNewInputElement(appendId, type, id, name, value) {
    var type_str = "<input type='" + type + "' ";
    var id_str = "id='" + id + "' ";
    var name_str = "name='" + name + "' ";
    var value_str = "value='" + value + "' />";
    //console.log(type_str + id_str + name_str + value_str);
    $("#" + appendId).append(type_str + id_str + name_str + value_str);
}

function removeElementByIds(ids) {
    var id_array = ids.split("_");
    for (var i = 0; i < id_array.length; i++) {
        removeElementId(id_array[i]);
    }
}

//remove an element id
function removeElementId(id) {
    $("#" + id).remove();
}

//get list of ref number 
function getRefList(form_id, target_id, action, functions) {
    var count = checklistlength(target_id);
    if ($("#result_no").length > 0) {
        $("#result_no").attr("value", count);
    } else {
        $("#" + form_id).append("<input type='hidden' id='result_no' name='result_no' value='" + count + "'/>");
    }
    sendRequest(form_id, target_id, action, functions);
}

//reload ref list
function reloadRefList(form_id, target_id, action, functions) {
    if ($("#result_no").length > 0) {
        $("#result_no").attr("value", 0);
    } else {
        $("#" + form_id).append("<input type='hidden' id='result_no' name='result_no' value='0'/>");
    }
    sendRequestHtmlWithoutAuth(form_id, target_id, action, functions);
}

function loadInsRefList(form_id, target_id, action, wn_type, functions) {
    $("#" + target_id).html("");
    if ($("#wn_type").length > 0) {
        $("#wn_type").attr("value", wn_type);
    } else {
        $("#" + form_id).append("<input type='hidden' id='wn_type' name='wn_type' value='" + wn_type + "'/>");
    }
    $("#" + target_id).html("<li class='chosen' id='ins_-1'>All</li>");
    sendRequest(form_id, target_id, action, functions);
}

function cleanElementHTML(id) {
    $("#" + id).html("");
}


function chooseFirstElement(target_id) {
    $("#" + target_id + " li:first-child").addClass("chosen");
}

//only used for <ul>
function chooseElement(target_id, element_id) {
    $("#" + target_id + " li").each(function() {
        if ($(this).attr("id") == element_id) {
            $("#" + target_id + " li.chosen").toggleClass("chosen");
            $(this).addClass("chosen");
            return;
        }
    });
}

function searchFilter(element, text) {

    $(element + " li").each(function() {
        if (text !== "") {
            if ($(this).text().toLowerCase().indexOf(text) < 0) {
                $(this).css("display", "none");
            } else {
                $(this).css("display", "");
            }
        } else {
            $(this).css("display", "");
        }
    });

}

function doFilter(element, text) {

    $(element + " option").each(function() {
        if (text !== "") {
            if ($(this).text().toLowerCase().indexOf(text) < 0) {
                $(this).css("display", "none");
            } else {
                $(this).css("display", "");
            }
        } else {
            $(this).css("display", "");
        }
    });

}

function scrollElTop(parent, child) {
    //remove link, if any
    if ($(parent + " " + child).attr("link") != undefined) {
        $(parent + " " + child).attr("link", "");
    }
    if (child == "li.chosen") {
        $(parent + " li").each(function() {
            if ($(this).hasClass("chosen")) {
                $(parent).scrollTo($(parent + " " + child), 300);
            }
        });
    } else {
        $(parent).scrollTo($(parent + " " + child), 300);
    }
}

function doDataTableFilter(table, parent) {
    table.fnFilter($(parent + ".dataTables_filter label input").val());
}
/**
 * compared_attr: value, id, class, text
 * attr_class could be the name of the class that could be added to the chosen li, and toggle from the others.
 * status: 
 *        0 means nothing, 
 *        1 means that using function to return compared_data
 * data 
 * @param {type} target
 * @param {type} data is a function ({"name":"function_name","params":[params_array]}) that return data.
 * @param {type} status
 * @param {type} compared_attr
 * @param {type} attr_class
 * @returns {undefined}
 */
function chooseElFromList(target, data, status, compared_attr, attr_class) {
    var el, compared_data;
    if (status == 1) {
        compared_data = window[data.name].apply(this, data.params);
        //console.log("compared_data = " + compared_data);
    } else {
        compared_data = data;
    }
    $(target).each(function() {
        switch (compared_attr) {
            case "value":
                el = $(this).val();
                break;
            case "id":
                el = $(this).attr("id");
                break;
            case "class":
                el = $(this).attr("class");
                break;
            case "text":
                el = $(this).text();
                break;
        }
        if (el == compared_data) {
            $(target + "." + attr_class).toggleClass(attr_class);
            $(this).addClass(attr_class);
        }
    });
}

function callMessageDialog(options) {
    if ($('#message_box').length !== 0) {
        _removeElementId("message_box");
    }
    var template = "<div id='message_box' class='" + options.type + " box center'>" + options.message + "</div>";
    if ($("#overlay").length === 0) {
        $('body').append("<div id='overlay'></div>");
    }
    $('#overlay').css('opacity', 0.6).fadeIn(400);

    $('body').append(template);

    $("#overlay").click(function() {
        $('#message_box').slideUp('fast', function() {
            $('#message_box').remove();
        });
        $('#overlay').fadeOut(800, function() {
            $('#overlay').remove();
        });
    });

    $('#message_box').slideDown(400);
    $("input[type=button]").button();
}

/**
 * 
 * @param {json} body_data_name data to map to the template
 * @param {json} options options for dialog
 * @returns {a dialog}
 */
function callDialog(body_data_name, options) {
    if ($('#message_box').length !== 0) {
        _removeElementId("message_box");
    }
    var template = "<div id='message_box' class='" + options.type + " box center'>\n" +
            "    {{:title}}\n" +
            "<ul class='list_error'>\n" +
            "    {{for msg}}\n" +
            "    <li>\n" +
            "        {{:a_row}}\n" +
            "    </li>\n" +
            "    {{/for}}\n" +
            "</ul></div>";

    if ($("#overlay").length === 0) {
        $('body').append("<div id='overlay'></div>");
    }
    $('#overlay').css('opacity', 0.6).fadeIn(400);

    $("body").append($.templates(template).render($.data(document.body, body_data_name)));
    if (options.autoCloseOverlay !== undefined) {
        if (options.autoCloseOverlay) {
            $("#overlay").click(function() {
                $('#message_box').slideUp('fast', function() {
                    $('#message_box').remove();
                });
                $('#overlay').fadeOut('slow', function() {
                    $('#overlay').remove();
                });
            });
        }
    }

    $('#message_box').slideDown('fast');
    $("input[type=button]").button();
}

function callSimpleDialog(options) {

    if ($('#detailBox').length == 0) {
        $(options.target).append("<div id='detailBox'></div>");
    }
    $("#detailBox").html(options.template);
    var dialog_styles = options.styles;
    if (dialog_styles !== undefined && dialog_styles !== null && dialog_styles.length > 0) {
        for (var i = 0; i < dialog_styles.length; i++) {
            $("#detailBox").css(dialog_styles[i].name, dialog_styles[i].style);
        }
    }

    if ($("#overlay").length == 0) {
        $('body')
                .append(
                '<div id="overlay"></div><div id="preloader">Loading..</div>');
    }
    $('#overlay').css('opacity', 0.6).fadeIn(400);
    $('#preloader').fadeIn(600);
    $('#preloader').fadeOut(300);
    $('#detailBox').fadeIn(400);
    $("input[type=button]").button();
    $("#overlay").click(function() {
        $('#detailBox').slideUp('fast', function() {
            $('#detailBox').remove();
        });
        $('#overlay').fadeOut('slow', function() {
            $('#overlay').remove();
        });
    });
}

function reloadAuth() {
    $("input:button[name^=perm_]").auth({action: "click"});
}

function generateSelection(target, table, vl, options, func) {
//    var offOverlay = options.offOverlay != undefined ? options.offOverlay : true;
//    var action = options.action != undefined ? options.action : "selection";
//    var type = options.type != undefined ? options.type : "select";
//    var id_prefix = options.prefix != undefined ? options.prefix : "";
    var opt = {
        all: false,
        data: {},
        offOverlay: true,
        action: "selection",
        type: "select",
        prefix: "",
        reWriteData: false,
        dataName: "",
        allText: "All",
        async: false
    };
    if (options != undefined) {
        $.extend(opt, options);
    }
    var tmpl = "";
    if (opt.all != undefined && !opt.all) {
        if (opt.type == "select") {
            tmpl = $.templates("<option value='{{:id}}'>{{:name}}</option>");
        }
        if (opt.type == "list") {
            tmpl = $.templates("<li id='" + opt.prefix + "{{:id}}'>{{:name}}</li>");
        }
    } else {
        if (opt.type == "select") {
            var ext = "<option value='-1'>" + opt.allText + "</option>";
            if (opt.data != undefined && opt.data.value != undefined) {
                ext = "<option value='" + opt.data.value + "'>" + (opt.data.text != undefined) ? opt.data.text : "" + "</option>";
            }
            tmpl = $.templates("<option value='{{:id}}'>{{:name}}</option>");
        }
        if (opt.type == "list") {
            var ext = "<li id='" + opt.prefix + "-1'>" + opt.allText + "</li>";
            if (opt.data != undefined && opt.data.value != undefined) {
                ext = "<li id='" + opt.prefix + opt.data.value + "'>" + (opt.data.text != undefined) ? opt.data.text : "" + "</li>";
            }
            tmpl = $.templates("<li id='" + opt.prefix + "{{:id}}'>{{:name}}</li>");
        }
    }
    var dataName = opt.dataName != "" ? opt.dataName : table;
    if (!$.data(document.body, dataName) || opt.reWriteData == true) {
        $.sendRequest({
            asynchronous: opt.async,
            action: opt.action,
            data: {type: table},
            optional: {turnOffLayout: true},
            functions: [
                {name: "selectValue", params: [target, vl]}
            ]
        }, function(msg) {
            var data = JSON.parse(msg);
            if (ext != undefined) {
                $(target).html(ext);
            }
            $(target).append(tmpl.render(data.select));
            $.data(document.body, dataName, data.select);
            if (func != undefined) {
                func();
            }
            return opt.offOverlay;
        });
    } else {
        if (ext != undefined) {
            $(target).html(ext);
        }
        $(target).append(tmpl.render($.data(document.body, dataName)));
        selectValue(target, vl);
        if (func != undefined) {
            func();
        }
    }
}

function disableSubmitAfterClick(btn) {
    btn.form.submit();
    btn.disabled = true;
}

function colorizeDuplicate(table_id) {
    var table = $("#" + table_id);
    var trs = table.find(".instruction-item");
    trs.each(function(item) {
        var ref = $(this).data("instrucion_ref");
        if(ref != null) {
            if($(".instruction-ref-" + ref).length > 1) {
                console.log(ref + ">1");
                $(".instruction-ref-" + ref).addClass("instruction-duplicate");
            } else {
                console.log(ref + "=1");
            }
        }
        
    });
}


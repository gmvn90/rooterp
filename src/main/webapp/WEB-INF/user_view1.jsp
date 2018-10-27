<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>User Management</title>
        <style type="text/css">
            .permission_col_1{width: 47%;}
            /*.permission_col_2{width: 52%;}*/
            .ui-buttonset .ui-button {margin-right: -0.35em !important;}
            #tbody_authorization td {padding-bottom: 1px; padding-top: 1px;}
        </style>
    </head>
    <body>
        <jsp:include page="header.jsp">
            <jsp:param name="url" value="19"/>
            <jsp:param name="page" value="13"/>
        </jsp:include>
        <form id="user_form" action="User" method="post">
            <input type="hidden" id="action" name="action" value=""/>
            <input type="hidden" id="pwd" name="pwd" value=""/>
            <input type="hidden" id="id" name="id" value=""/>
            <div style='display:none' id='hid'>
                
            </div>

            <div id="main_content">
                <div style="width: 100%; display: inline-block;">
                    <div id="user_div" class="percentage_20" style="float: left;height: 535px">
                        <div class="border_1_solid_black" id="list_user_div" style="height: 100%; overflow: auto;">
                        </div>

                    </div>

                    <div id="info_div" class="percentage_75 border_1_solid_black" style="float: right; padding-left: 15px; padding-right: 15px;">

                    </div>
                </div>
                <div style="width: 100%; display: inline-block;">
                    <div class="percentage_20" style="float: left">
                        <div>
                            <div style="float: left; width: 50%;">
                                <input class="small_button" type="button" style="margin-top: 2px; margin-bottom: 2px; float: right;" id="btn_add_user" name="btn_add_user" value="+" />
                            </div>
                            <div style="float: right; width: 50%;">
                                <input class="small_button" type="button" style="margin-top: 2px; margin-bottom: 2px; float: left;" id="btn_remove_user" name="perm_13" value="-" />
                            </div>
                        </div>
                    </div>
                    <div class="percentage_70 right" style="height: 30px; margin: 0px 45px 15px 0px; padding-left: 15px">
                        <input style="float: right" type="button" id="btn_save" value="Save Changes"/>
                    </div>
                </div>
            </div>
        </form>

        <jsp:include page="footer.jsp"></jsp:include>

        <script type="text/javascript">
            var default_info = {
                user_id: -1,
                username: '',
                password: '',
                full_name: '',
                birth_date: '',
                mobile: '',
                email: '',
                access: {}
            };

            var renderUserInfo = function(options) {
                var selector = options.selector;
                var data = options.data;
                var file = getAbsolutePath() + "/js/templates/admin/admin.user.info.html";
                $.when($.get(file))
                        .done(function(tmplData) {
                    $.templates({user_info: tmplData});
                    $(selector).html($.render.user_info(data));
                    $("input[type=button]").button();
                    $("input[type=submit]").button();
                    $('#user_div').css('height', $('#info_div').css('height'));
                });
            };

            function loadMoreIndent() {
                $(".link").each(function(i) {
                    var arr = $(this).attr("id").split(".");
                    var id = arr[0] + "-" + arr[1];
                    console.log(id);
                    sendJsonRequest($(this).attr("id"), "get_indent_lv3", {id: $(this).attr("id").split("_")[1].split(".")[1]}, 5, [
                        {name: "loadUserIndent3", params: [this]}
                    ], true, ["indent"]);
                });
                $(".permission[class*=link] td:first").prepend("<a class='mpcth-icon-plus-squared more_indent' style='margin-right: 10px; color: #3383bb'></a>");
            }

            function loadUserIndent3(el) {
                var file = getAbsolutePath() + "/js/templates/admin/access_info.html";
                var arr = $(el).attr("id").split(".");
                var id = "#" + arr[0] + "-" + arr[1];
                var html;
                $.when($.get(file))
                        .done(function(tmplData) {
                    $.templates({user_access: tmplData});
                    html = $.render.user_access($.data(document.body, "indent"));
                    $(html).insertAfter(id);
                });
                
            }

            $(document).on("click", "#btn_save", function() {
                if ($('#user_form').valid()) {
                    if ($('#usr_password').val() != "") {
                        $('#pwd').val($.md5($('#usr_password').val()));
                    }
                    if ($(this).val() == "Create Account") {
                        $("#id").val("");
                    }
                    $.ajax({
                        type: 'POST',
                        data: $("#user_form").serialize(),
                        url: getAbsolutePath() + "/update_account.htm",
                        success: function(msg) {
                            if (msg.trim() != "") {
                                callMessageDialog({type:"info",message:msg});
                            }
                        }
                    }).done(function() {
                        loadUserList('user_form', 'list_user_div', 'load_user_list');
                        $("input[type=button]").button();
                        $("input[type=submit]").button();
                    });
                }
            });
            $(document).ready(function() {
                renderUserInfo({data: default_info, selector: "#info_div"});
                $(".change_pwd_line").attr("style", "height: 30px; display: none !important");
                $("#btn_save").prop('disabled', true);
                $("#btn_save").prop('aria-disabled', false);
                $("#btn_save").attr('class', 'ui-button ui-widget ui-state-default ui-corner-all ui-button-disabled ui-state-disabled');
                $('#check').change(function() {
                    if ($(this).val() === '0') {
                        checkNone();
                    } else if ($(this).val() === '1') {
                        checkAll();
                    } else {
                        loadUserPermission();
                    }
                });

                $("#btn_remove_user").click(function() {
//                    var element = $(this);
//                    //chay check connection
//                    request = $.ajax({
//                        type: 'POST',
//                        async: false,
//                        cache: false,
//                        data: {action: "show_login"},
//                        url: "Login",
//                        beforeSend: function(jqXHR, settings) {
//                            console.log("beforeSend.");
//                            checkFullAuthorization(element,jqXHR);
//                            isAuthorized(jqXHR);                            
//                            console.log("end beforeSend.");
//                        },
//                        success: function(response) {
//                            console.log("response");
//                        }
//                    });
                });

                $('#show_dialog').click(function() {
                    openDialog("user_form", "show_permission_dialog", 0.6, [
                        {name: "top", style: "20%"}
                    ]);
                });
                $("input[type=submit]").button();
                loadUserList();
                $("#btn_change_password").on("click", function() {
                    if ($(this).val() == "Change Password") {
                        $(this).val("Cancel");
                        $(this).siblings().text("");
                        $(".password").each(function() {
                            $(this).removeAttr("style");
                        });
                    } else if ($(this).val() == "Cancel") {
                        $(this).val("Change Password");
                        $(".password").each(function() {
                            $(this).attr("style", "display: none !important");
                            $(this).children("input").val("");
                        });
                    }
                });

                $("#btn_add_user").on("click", function() {
                    $("#id").val("");
                    $(".change_pwd_line").attr("style", "height: 30px; display: none !important");
                    $("#btn_save").val("Create Account");
                    $("#btn_change_picture").val("Add Picture");
                    $("#btn_change_picture").css("margin-left", "12px");
                    $(".password").each(function() {
                        $(this).removeAttr("style");
                        $(this).children("input").val("");
                    });
                    $("#userName").val("");
                    $("#fullName").val("");
                    $("#dob").val("");
                    $("#phone").val("");
                    $("#email").val("");
                    $("#active").prop('checked', true);
                    $(".image_div").find("#img_picture").remove();
                    $("#btn_change_picture").before('<img class="border_1_solid_black" width="118px" height="118px" style="margin-bottom: 2px"  id="img_picture"/>');

                    //load blank permission (default is none for all page)
                    loadUserPermission();
                });

                $("#user_form").validate({
                    rules: {
                        userName: {
                            required: true,
                            minlength: 2
                        },
                        password: {
                            required: true,
                            minlength: 5
                        },
                        confirm_password: {
                            required: true,
                            minlength: 5,
                            equalTo: "#usr_password"
                        },
                        email: {
                            email: true
                        },
                    },
                    messages: {
                        userName: {
                            required: "Please enter a username",
                            minlength: "Your username must consist of at least 2 characters"
                        },
                        password: {
                            required: "Please provide a password",
                            minlength: "Your password must be at least 5 characters long"
                        },
                        confirm_password: {
                            required: "Please provide a password",
                            minlength: "Your password must be at least 5 characters long",
                            equalTo: "Please enter the same password as above"
                        },
                        email: {
                            email: "Please enter a valid email address",
                        },
                    }
                });
            })

            function loadUserInfo(id) {
                if ($("#btn_change_password").val() == "Cancel") {
                    $("#btn_change_password").val("Change Password");
                    $(".password").each(function() {
                        $(this).attr("style", "display: none !important");
                        $(this).children("input").val("");
                    });
                }
                //use it for getting data on click btn_save event
                $("#id").val(id);
                $("#btn_save").val("Save Changes");
                sendJsonRequest("", "load_user_info", {id: id}, 5, [
                    {name: "loadUserFromData", params: []},
                    {name: "loadUserList", params: []},
                    {name: "loadUserPermission", params: []},
                    //{name: "loadMoreIndent", params: []}
                ], true, ["user_data"]);
            }

            function loadUserFromData() {
                var user = $.data(document.body, "user_data");
                $("#userName").val(user.userName);
                $("#fullName").val(user.fullName);
                $("#dob").val(user.dob);
                $("#phone").val(user.phone);
                $("#email").val(user.email);
                $("#pwd").val(user.password);
                $("#active").prop('checked', user.active);
                $("#show_dialog").button("enable");
            }

            function loadUserList() {
                sendJsonRequest("list_user_div", "load_user_list", {}, 0);
            }

            function checkAll() {
                $('.permission').each(function() {
                    $(this).find('input:radio').last().prop('checked', true);
                });
            }

            function checkNone() {
                $('.permission').each(function() {
                    $(this).find('input:radio').first().prop('checked', true);
                })
            }

            function loadUserPermission() {
                sendJsonRequest("tbody_authorization", "load_user_permission", {id: $("#id").val()}, 0, [
                    {name: "disable_btn_save", params: []},
                    {name: "loadMoreIndent", params: []}
                ]);
            }
            function disable_btn_save() {
                if ($("#btn_save").prop('disabled')) {
                    $("#btn_save").prop('disabled', false);
                    $("#btn_save").prop('aria-disabled', false);
                    $("#btn_save").attr('class', 'ui-button ui-widget ui-state-default ui-corner-all');
                }
            }
        </script>
    </body>
</html>

<%-- 
    Document   : profile
    Created on : Oct 24, 2013, 3:04:00 PM
    Author     : kiendn
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>User Profile</title>
    </head>
    <body>
        <jsp:include page="header.jsp">
            <jsp:param name="url" value="24"/>
            <jsp:param name="page" value="21"/>
        </jsp:include>
        <style>
            .hid {display: none}
            div#profile{
                position: absolute;
                top: 15px;
                left: 15px;
                width: 770px;
                height: 370px;
                border: 1px solid #d5d5d5;
                padding: 10px;
            }
            .ui-button {
                font-size: 11px !important;
            }
            table#tbl_user_info{position: absolute; top: 15px; right: 15px}
            div.image_div {position: absolute; top: 15px; left: 15px}
            .file-wrapper input {
                filter: alpha(opacity=1);
                -moz-opacity: 0.01;
                opacity: 0.01;
                cursor: pointer;
                height: 100%;
                position: absolute;
                right: 0;
                top: 0;
                font-size: 100px;
            }
            .file-wrapper {
                cursor: pointer;
                display: inline-block;
                overflow: hidden;
                position: relative;
            }
            .file-wrapper #btn_change_picture {
                position: initial;
            }
            .ui-buttonset .ui-button {margin-right: -0.35em !important;}
            div#btn_section{position: absolute; bottom: 0; left: 0; width: 100%}
            #btn_save{margin-left: auto; margin-right: auto}
        </style>
        <div id="main_content" style="width: 800px !important; min-height: 400px; overflow: hidden; position: relative">
            <div id="profile">
                <table style="margin-right: 20px" id="tbl_user_info">
                    <tr>
                        <td>Username</td>
                        <td><input type="text" id="userName" name="user_name" rq_type=text value="" readonly=""/></td>
                    </tr>
                    <tr>
                        <td>Full Name</td>
                        <td><input type="text" id="fullName" name="full_name" rq_type=text value=""/></td>
                    </tr>
                    <tr>
                        <td>Birth Date</td>
                        <td><input type="text" id="dob" name="birth_date" class="date_picker_text" rq_type=text value=""/></td>
                    </tr>
                    <tr>
                        <td>Phone Number</td>
                        <td><input type="text" id="phone" name="phone" rq_type=text value=""/></td>
                    </tr>
                    <tr>
                        <td>Email</td>
                        <td><input type="text" id="email" name="email" rq_type=text value=""/></td>
                    </tr>
                    <tr class="hid">
                        <td>Password</td>
                        <td><input type="password" id="password" name="password" value="" rq_type=text auto-fill="false" autocomplete="off"/></td>
                    </tr>
                    <tr class="hid">
                        <td>Confirm Password</td>
                        <td><input type="password" id="confirm_password" name="confirm_password" value="" autocomplete="off"/></td>
                    </tr>
                    <tr>
                        <td></td>
                        <td><input type="button" id="change_password" value="Change Password"/></td>
                    </tr>
                </table>
                <div class="image_div">
                    <img class="border_1_solid_black profile_picture" src="images/maleLogo.jpg" width="118px" height="118px" style="margin-bottom: 2px"  id="img_picture"/>
                    <form method="post" id="upload_form" action="upload.json" enctype="multipart/form-data">
                        <span class="file-wrapper">
                            <input id="fileupload" type="file" name="file" data-url="upload.json">
                            <span class="right_element" style="margin-top: 2px; margin-bottom: 2px" id="btn_change_picture" name="btn_change_picture">Change Picture</span>
                        </span>
                    </form>
                </div>
                <div id="btn_section">
                    <input type="button" id="btn_save" value="Save"/>
                </div>
            </div>
        </div>

        <jsp:include page="footer.jsp"/>
        <script>
            $(document).ready(function() {
                $('#btn_change_picture').button();
                $.sendRequest({
                    action: "get_user_profile",
                    status: "append_data",
                    optional: ["user_info"],
                    functions: function() {
                        $.parseData({data: $.data(document.body, "user_info")});
                    }
                });
                $('#fileupload').fileupload({
                    dataType: 'json',
                    add: function(e, data) {
                        var isValidated = true;
                        console.log(data.files);
                        $.each(data.files, function(index, file) {
                            var maxFileSize = 2097152; //2MB = 2097152
                            var fileTypes = /(\.|\/)(gif|jpe?g|png)$/i;
                            if (file.size > maxFileSize) {
                                isValidated = false;
                                data.context = alert('Maximum file size is 2 MB.');
                                return isValidated;
                            }
                            if (!file.name.match(fileTypes)) {
                                isValidated = false;
                                data.context = alert('Not support file type.');
                                return isValidated;
                            }
                        });
                        if (isValidated) {
                            if ($("#overlay").length === 0) {
                                $('body').append('<div id="overlay"></div><div id="preloader">Loading..</div>');
                            }
                            $('#overlay').css('opacity', 0.6).fadeIn(400);
                            $('#preloader').fadeIn(600);
                            //                    data.context = $('<p/>').text('Uploading...').appendTo(document.body);
                            data.submit();
                        }
                    },
                    done: function(e, data) {
                        var json = data.result;
                        $('#preloader').fadeOut(300);
                        $('#overlay').fadeOut(400);
                        $('#overlay').remove();
                        $('#preloader').remove();
                        $('#img_picture').attr('src', json.url);
                    }
                });
            });

            $(document).on("click", "#change_password", function() {
                $(this).attr("value", ($(this).val() == "Close") ? "Change Password" : "Close");
                $(".hid").each(function() {
                    if ($(this).css("display") == "none") {
                        $(this).show(400);
                    } else {
                        $("#password").val("");
                        $("#confirm_password").val("");
                        $(this).hide(400);
                        $.data(document.body, "user_info")["changedPass"] = false;
                    }
                });
            }).on("click", "#btn_save", function() {
                var validate = [];
                if ($.data(document.body, "user_info").changedPass) {
                    validate = [
                        {element: "#fullName", msg: "Please Enter Your Name"},
                        {element: "#password", msg: "Please Enter Your New Password"},
                        {element: "#confirm_password", msg: "Please Confirm Your New Password"},
                        {
                            element: "#password",
                            msg: "Password and Confirm should be matched",
                            opts: {
                                type: "==",
                                value: $("#confirm_password").val()
                            }
                        },
                        {
                            element: "#password",
                            msg: "Password must be between 6 to 12 characters",
                            opts: {
                                type: "len",
                                limit: {
                                    min: 5,
                                    max: 12
                                }
                            }
                        }
                    ];
                } else {
                    validate = [{element: "#fullName", msg: "Please Enter Your Name"}];
                }
                $.validation(validate, function(validator) {
                    if (validator) {
//                        for (var i = 0; i < data.length; i++) {
//                            var new_info = $.getData();
//                            if (data[i].element == "#password") {
//                                new_info["password"] = $.md5($("#password").val());
//                            }
//                        }
                        var new_info = $.getData();
                        if ($("#password").val() != ""){
                            new_info["password"] = $.md5($("#password").val());
                        }
                        $.extend($.data(document.body,"user_info"),new_info);
                        $.sendRequest({
                            action: "update_profile",
                            data: $.data(document.body,"user_info"),
                        },function(msg){
                            var data = JSON.parse(msg);
                            if (data.type == "error"){
                                $.error(data.msg);
                            }
                            if (data.type == "success"){
                                $.success(data.msg);
                            }
                        });
                    }
                });


            }).on("keyup", "#password", function() {
                if ($("#password").val() != "") {
                    $.data(document.body, "user_info")["changedPass"] = true;
                } else {
                    $.data(document.body, "user_info")["changedPass"] = false;
                }
            });
        </script>
    </body>
</html>

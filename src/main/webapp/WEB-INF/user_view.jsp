<%-- 
    Document   : user_view
    Created on : Sep 18, 2013, 2:18:31 PM
    Author     : kiendn
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>User Management</title>
</head>
<body>
<jsp:include page="header.jsp">
    <jsp:param name="url" value="19"/>
    <jsp:param name="page" value="13"/>
</jsp:include>
<script>
    $('<link>')
            .appendTo($('head'))
            .attr({type: 'text/css', rel: 'stylesheet'})
            .attr('href', getAbsolutePath() + '/styles/reset.css');
</script>
<style>
    .hid {
        display: none
    }

    .subtopic h4 {
        width: 100%
    }

    .rootUL .subtopic h4 .accesslist {
        margin-right: 19px !important
    }

    .rootUL .subtopic .subtopics subtopic h4 .accesslist {
        margin-right: 8px !important
    }

    ul.usr_list {
        font-size: 12px;
        background-color: #f2f2f2;
        border: 1px solid #e8e8e8;
        display: block;
        width: 225px !important;
        height: 500px;
        overflow: auto;
        padding: 0 0 0 10px;
        margin-top: 15px;
        clear: both;
    }

    ul.usr_list li.user_item.selected, ul.usr_list li.user_item:hover {
        background: white
    }

    ul.usr_list li.user_item:hover {
        cursor: pointer
    }

    ul.usr_list li.user_item {
        margin: 0;
        padding: 0 0 0 10px;
        width: 100%;
        height: 45px;
        position: relative;
        line-height: 28px;
        border-width: 1px 0 1px 0;
        border-style: solid;
        border-color: #fff #e8e8e8 #e8e8e8 #e8e8e8;
        display: list-item;
        list-style-position: inside;
        background: transparent;
        vertical-align: middle;
        left: -1px;
        list-style: decimal inside;
        color: #2c7c91;
    }

    ul.usr_list li.first {
        border-color: #e8e8e8 #e8e8e8 #e8e8e8 #e8e8e8;
    }

    table tbody tr > td {
        padding-bottom: 15px !important;
    }
</style>
<form id="user_form">
    <div id="main_content" style="width: 1250px !important; min-height: 800px; overflow: hidden">
        <div class="left border" style="width: 250px !important; margin-left: 0px; margin-right: 5px; padding: 10px">
            <div class="pc100">
                <ul id="list_user_div" class="usr_list">
                </ul>
            </div>
            <div style="margin-top: 15px">
                <input type="button" id="btn_add_new" value="Add"/>
                <input type="button" id="btn_delete" value="Del"/>
            </div>
        </div>
        <div class="right border" style="width: 970px;height: auto; overflow: hidden; min-height: 650px">
            <div style="width: 100%; overflow: hidden; height: auto; padding-bottom: 10px">
                <div id="user_info"
                     style="width: 100%; overflow: hidden; height: auto; padding-bottom: 10px; padding-left: 25px; border-bottom: 1px solid #d5d5d5">
                    <div style="width: 130px; float: left; padding-top: 30px; margin-right: 50px">
                        <img class="border_1_solid_black" src="images/maleLogo.jpg" width="118px" height="118px"
                             style="margin-bottom: 2px" id="img_picture"/>
                        <input type="button" id="change_picture" value="Change Picture"/>
                    </div>
                    <div style="float: left; height: auto; overflow: hidden; min-height: 250px;width: 400px; padding-top: 15px; padding-left: 20px">
                        <table style="margin-right: 20px">
                            <tr>
                                <td>Username</td>
                                <td><input type="text" id="userName" name="user_name" rq_type=text value=""/></td>
                            </tr>
                            <tr>
                                <td>Full Name</td>
                                <td><input type="text" id="fullName" name="full_name" rq_type=text value=""/></td>
                            </tr>
                            <tr>
                                <td>Birth Date</td>
                                <td><input type="text" id="dob" name="birth_date" class="date_picker_text" rq_type=text
                                           value=""/></td>
                            </tr>
                            <tr>
                                <td>Phone Number</td>
                                <td><input type="text" id="phone" name="phone" rq_type=text value=""/></td>
                            </tr>
                            <tr>
                                <td>Email</td>
                                <td><input type="text" id="email" name="email" rq_type=text value=""/></td>
                            </tr>
                            <tr>
                                <td>Active</td>
                                <td><input type="checkbox" id="active" name="active" rq_type=text value=""/></td>
                            </tr>
                            <tr>
                                <td>Only use for client site</td>
                                <td><input type="checkbox" id="isOnlyForClientSite" name="isOnlyForClientSite" rq_type=text value=""/></td>
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
                            <tr>
                                <td>Client</td>
                                <td><input type="checkbox" id="client" name="client" rq_type=text value=""/></td>
                            </tr>
                            <tr id='client_company' class='hid'>
                                <td>Company</td>
                                <td><select id='company' rq_type=text></select></td>
                            </tr>

                        </table>
                    </div>
                </div>
                <!-- access section -->
                <div id="permission_section">
                </div>
            </div>
            <div class="pc100">
                <input class="right" style="margin: 0 27px 15px 0" type="button" id="btn_save" value="Save"/>
            </div>
        </div>
    </div>

</form>
<script type="text/x-jsrender" id="tmpl_user_list">
                        {{for users}}
                {{if order == "1"}}
                <li class="user_item first" id="user_{{:id}}">
                    {{else}}
                    <li class="user_item" id="user_{{:id}}">
                        {{/if}}
                        <img src="images/maleLogo.jpg" width="35px" height="35px" style="position: absolute; top: 5px; left: 0"/>
                        <p style="position: absolute; top: 5px; left: 40px; font-size: 13px">{{:username}}</p>
                        <p style="position: absolute; top: 20px; left: 40px; font-size: 11px; color: black !important">{{:fullname}}</p>
                    </li>
                    {{/for}}

</script>
<script type="text/x-jsrender" id="tmpl_access_list">
            <h3 style='margin-left: 20px'>Permission</h3>
            <div class="CategorySelectorBody">
            <div class="section">
            {{for access_list}}
            <h3 class="parentTopic{{:id}} subtitle">
            {{if hasChild == "true"}}
            <div class="parentTopic{{:id}} toggle collapsed"></div>
            {{else}}
            <div class="parentTopic{{:id}} toggle expand disabled"></div>
            {{/if}}
            <span>{{:name}}</span>
            <ul class="accesslist">
            {{if style == "0"}}
            <li class="first">
            <input type="radio" name="permission_{{:id}}" id="rd_{{:id}}_0" value="0"/>
            <label for="rd_{{:id}}_0">None</label>
            </li>
            <li>
            <input type="radio" name="permission_{{:id}}" id="rd_{{:id}}_1" value="1"/>
            <label for="rd_{{:id}}_1">Read Only</label>
            </li>
            <li>
            <input type="radio" name="permission_{{:id}}" id="rd_{{:id}}_2" value="2"/>
            <label for="rd_{{:id}}_2">Full Access</label>
            </li>
            {{else style == "1"}}
            <li class="first">
            <input type="radio" name="permission_{{:id}}" id="rd_{{:id}}_0" value="0"/>
            <label for="rd_{{:id}}_0">No</label>
            </li>
            <li><input type="radio" name="permission_{{:id}}" id="rd_{{:id}}_1" value="1"/>
            <label for="rd_{{:id}}_1">Yes</label>
            </li>
            {{/if}}
            </ul>
            {{if hasChild == "true"}}
            <ul class="rootUL parentTopic{{:id}} subtopics" style="display: none;">
            {{for child}}
            {{if order == "1"}}
            <li class="subtopic parentTopic{{:id}} first">
            {{else order == length && length !== "1"}}
            <li class="subtopic parentTopic{{:id}} last">
            {{else}}
            <li class="subtopic">
            {{/if}}
            <h4>
            {{if hasChild == "true"}}
            <div class="parentTopic{{:id}} toggle collapsed"></div>
            {{else}}
            <div class="parentTopic{{:id}} toggle expand disabled"></div>
            {{/if}}
            <span>{{:name}}</span>
            <ul class="accesslist">
            {{if style == "0"}}
            <li class="first">
            <input type="radio" name="permission_{{:id}}" id="rd_{{:id}}_0" value="0"/>
            <label for="rd_{{:id}}_0">None</label>
            </li>
            <li>
            <input type="radio" name="permission_{{:id}}" id="rd_{{:id}}_1" value="1"/>
            <label for="rd_{{:id}}_1">Read Only</label>
            </li>
            <li>
            <input type="radio" name="permission_{{:id}}" id="rd_{{:id}}_2" value="2"/>
            <label for="rd_{{:id}}_2">Full Access</label>
            </li>
            {{else style == "1"}}
            <li class="first">
            <input type="radio" name="permission_{{:id}}" id="rd_{{:id}}_0" value="0"/>
            <label for="rd_{{:id}}_0">No</label>
            </li>
            <li><input type="radio" name="permission_{{:id}}" id="rd_{{:id}}_1" value="1"/>
            <label for="rd_{{:id}}_1">Yes</label>
            </li>
            {{/if}}
            </ul>
            </h4>

            {{if hasChild == "true"}}
            <ul class="parentTopic{{:id}} subtopics" style="display: none;">
            {{for child}}
            {{if order == "1"}}
            <li class="subtopic parentTopic{{:id}} first">
            {{else order == length && length !== "1"}}
            <li class="subtopic parentTopic{{:id}} last">
            {{else}}
            <li class="subtopic">
            {{/if}}
            <h4>
            {{if hasChild == "true"}}
            <div class="parentTopic{{:id}} toggle collapsed"></div>
            {{else}}
            <div class="parentTopic{{:id}} toggle expand disabled"></div>
            {{/if}}
            <span>{{:name}}</span>
            <ul class="accesslist" style="width: 590px">
            {{if style == "0"}}
            <li class="first">
            <input type="radio" name="permission_{{:id}}" id="rd_{{:id}}_0" value="0"/>
            <label for="rd_{{:id}}_0">None</label>
            </li>
            <li>
            <input type="radio" name="permission_{{:id}}" id="rd_{{:id}}_1" value="1"/>
            <label for="rd_{{:id}}_1">Read Only</label>
            </li>
            <li>
            <input type="radio" name="permission_{{:id}}" id="rd_{{:id}}_2" value="2"/>
            <label for="rd_{{:id}}_2">Full Access</label>
            </li>
            {{else style == "1"}}
            <li class="first">
            <input type="radio" name="permission_{{:id}}" id="rd_{{:id}}_0" value="0"/>
            <label for="rd_{{:id}}_0">No</label>
            </li>
            <li><input type="radio" name="permission_{{:id}}" id="rd_{{:id}}_1" value="1"/>
            <label for="rd_{{:id}}_1">Yes</label>
            </li>
            {{/if}}
            </ul>
            </h4>
            {{/for}}
            </ul>
            {{/if}}
            </li>
            {{/for}}
            </ul>
            {{/if}}
            </h3>
            {{/for}}
            </div>
            </div>

</script>
<jsp:include page="footer.jsp"></jsp:include>
<script>
    var mess;
    $(document).ready(function () {
        $.data(document.body, "user_info", {});
        loadUserList();
        loadAccessList();
        generateSelection("#company", "CompanyMaster", "company_-1", {prefix: "company_"});
    }).on("click", "#change_password", function () {
        $(this).attr("value", ($(this).val() == "Close") ? "Change Password" : "Close");
        $(".hid").each(function () {
            if ($(this).css("display") == "none") {
                $(this).show(400);
            } else {
                $("#password").val("");
                $(this).hide(400);
            }
        });
    }).on("click", ".toggle", function () {
        if (!$(this).hasClass("disabled")) {
            if ($(this).hasClass("collapsed")) {
                $(this).removeClass("collapsed");
                $(this).addClass("expand");
            } else {
                $(this).addClass("collapsed");
                $(this).removeClass("expand");
            }
            var classStr = $(this).attr("class");
            //$("." + classStr).toggleClass("collapsed");
            var parentTopic = classStr.split(" ")[0];
            var display = $("." + parentTopic + ".subtopics").css("display");
            if (display == 'none') {
                $("." + parentTopic + ".subtopics").css("display", "block");
            } else {
                $("." + parentTopic + ".subtopics").css("display", "none");
            }
        }
    }).on("click", ".user_item", function () {
        $(".user_item").each(function () {
            if ($(this).hasClass("selected")) {
                $(this).toggleClass("selected");
            }
        });
        $(this).addClass("selected");
        var id = $(this).attr("id").split("_")[1];
        loadUserInfo(id);
    }).on("click", "#btn_add_new", function () {
        $.parseData({
            data: $.getEmptyData({
                extend: [
                    {key: "perms", value: []}
                ]
            })
        });
        $("input:radio").removeAttr("checked");
        $.data(document.body, "user_info", {});
    }).on("click", "#btn_save", function () {
        updatePerms();
    }).on("click", "#client", function () {
        if ($(this).prop("checked") == false) {
            $("#client_company").addClass("hid");
        } else {
            $("#client_company").toggleClass("hid");
        }
    });
    function expand_collapse() {
        $("[class*=parentTopic] .toogle").each(function () {
            console.log($(this).attr("class"));
        });
    }
    function loadUserList() {
        $.sendRequest({
            target: "#list_user_div",
            action: "load_user_list",
            status: "apply_template",
            optional: {opt: "replace", tmpl: "#tmpl_user_list"}
        });
    }
    function loadAccessList() {
        $.sendRequest({
            target: "#permission_section",
            action: "load_access_list",
            status: "apply_template",
            optional: {opt: "replace", tmpl: "#tmpl_access_list"}
        });
    }

    function loadUserInfo(id) {
        $.sendRequest({
            action: "load_user_info",
            data: {user_id: id},
            status: "append_data",
            optional: ["user_info"],
            functions: [
                {name: "parseUserInfo", params: []}
            ]
        });
    }
    function parseUserInfo() {
        var user_info = $.data(document.body, "user_info");
        $.parseData({data: user_info});
        var perms = user_info.perms;
        for (var i = 0; i < perms.length; i++) {
            for (var j = 0; j < $("input:radio[name=permission_" + perms[i].page + "]").length; j++) {
                if ($("input:radio[name=permission_" + perms[i].page + "]:nth(" + j + ")").attr("value") == perms[i].perm) {
                    $("input:radio[name=permission_" + perms[i].page + "]")[j].checked = true;
                }
            }
        }
    }
    function updatePerms() {
        //specify new or update
        //if is update check pwd, if new check pwd is null
        var perms = [];
        var name = "";
        var pwd = ($("#password").val() != "") ? $.md5($("#password").val()) : "";
        if (pwd != "") {
            if ($("#password").val() != $("#confirm_password").val()) {
                $.warning("Password is incorrect");
            } else {
                $("#password").val(pwd);
            }
        }

        $("input:radio[name^=permission_]").each(function () {
            if (name == "" || name != $(this).attr("name")) {
                name = $(this).attr("name");
                var value = $("input:radio[name=" + name + "]:checked").val();
                perms.push({page: name.split("_")[1], perm: (value != undefined) ? value : 0});
            }
            //console.log(name);
        });
        $.extend($.data(document.body, "user_info"), $.getData({
            extend: [
                {key: "perms", value: JSON.stringify(perms)}
            ]
        }));
        $.data(document.body, "user_info").password = pwd;
        console.log(user_info);
        $.sendRequest({
            action: "update_permission",
            data: $.data(document.body, "user_info"),
            status: "simple_dialog",
            optional: "info"
        });
    }
</script>
</body>
</html>

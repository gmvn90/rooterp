<%-- 
    Document   : allocation_detail
    Created on : Jul 13, 2013, 11:09:15 PM
    Author     : kiendn
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <style>
            ul.inst_info li {width: 250px; height: 25px;}
            ul.inst_info li:hover{background: white !important; color:black !important}
            ul.inst_info li span{width: 60px; display: block; vertical-align: middle; float: left; line-height: 20px}
            ul.inst_info li input{width: 190px; float: right; text-align: right}
        </style>
    </head>
    <body>
        <jsp:include page="header.jsp">
            <jsp:param name="url" value="15"/>
            <jsp:param name="page" value="8"/>
        </jsp:include>
        <c:set var="ins" value="${ins}"></c:set>
        <c:set var="ins_type" value="${ins_type}"></c:set>
            <form id="allocation_form">
                <div style="width: 1500px ! important; padding: 15px; background: none repeat scroll 0% 0% white; height: auto; overflow: hidden; min-height: 800px" class="container_16 border" id="wrapper">
                    <div style="width: 100%; overflow: hidden; height: auto; border-bottom: 1px solid #B8C2CC; padding-bottom: 10px">
                        <table class="table_filter">
                            <thead>
                                <tr style="font-size: 13px">
                                    <th width="100px">Type</th>
                                    <th width="190px">Reference Number</th>
                                    <th width="190px">Grade</th>
                                    <th width="190px">Instruction Info</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td>
                                        <input type="text" class="text_filter_child">
                                        <ul id="type_filter" class="filter" name="perm_40">
                                            <li id="inst_type_P" class="${ins_type eq "P" ? "chosen" : ""}">Processing</li>
                                        <li id="inst_type_E" class="${ins_type eq "E" ? "chosen" : ""}">Export</li>
                                    </ul>
                                </td>
                                <td>
                                    <input type="text" style="width: 190px" class="text_filter_child">
                                    <ul id="instruction_ref_filter" class="filter">
                                    </ul>
                                </td>
                                <td>
                                    <input type="text" style="width: 190px;" class="text_filter_child">
                                    <ul id="grade_filter" class="filter">
                                        <li id="grade_-1" class="chosen">All</li>
                                    </ul>
                                </td>
                                <td width="250px">
                                    <ul class="inst_info">
                                        <li>
                                            <span width="50px">Grade</span>
                                            <input type="text" id="inst_grade" class="formatNumber" value="18 grade" readonly=""/>
                                        </li>
                                        <li>
                                            <span width="50px">Quantity</span>
                                            <input type="text" id="inst_quantity" class="formatNumber" value="1000" readonly=""/>
                                        </li>
                                        <li>
                                            <span width="50px">Allocated</span>
                                            <input type="text" id="inst_allocated" class="formatNumber"  value="1000" readonly=""/>
                                        </li>
                                        <li>
                                            <span width="50px">Pending</span>
                                            <input type="text" id="inst_pending" class="formatNumber"  value="1000" readonly=""/>
                                        </li>
                                    </ul>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <div style="width: 100%;overflow: hidden; height: auto; border-bottom: 1px solid #B8C2CC; padding-bottom: 10px">
                    <h3>Available Weight Note</h3>
                    <table id="available_wn">
                        <thead>
                            <tr>
                                <th></th>
                                <th>WN Ref</th>
                                <th>Stock Date </th>
                                <th>Grade</th>
                                <th>Stock Tons</th>
                                <th>Black</th>
                                <th>Brown</th>
                                <th>FM</th>
                                <th>Broken</th>
                                <th>Moist</th>
                                <th>Old Crop</th>
                                <th>S20+</th>
                                <th>S20</th>
                                <th>S19</th>
                                <th>S18</th>
                                <th>S17</th>
                                <th>S16</th>
                                <th>S15</th>
                                <th>S14</th>
                                <th>S13</th>
                                <th>S12</th>
                                <th>S12-</th>
                                <th>Sel</th>
                                <th>...</th>
                            </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
                    <input type="button" id="btn_allocate_wn" value="Allocate" style="margin-top: 20px" name="perm_52"/>
                </div>
                <div style="width: 100%;overflow: hidden; height: auto; border-bottom: 1px solid #B8C2CC; padding-bottom: 10px">
                    <h3>Allocated Weight Note</h3>
                    <table id="allocated_wn">
                        <thead>
                            <tr>
                                <th></th>
                                <th>WN Ref</th>
                                <th>Stock Date </th>
                                <th>Grade</th>
                                <th>Stock Tons</th>
                                <th>Black</th>
                                <th>Brown</th>
                                <th>FM</th>
                                <th>Broken</th>
                                <th>Moist</th>
                                <th>Old Crop</th>
                                <th>S20+</th>
                                <th>S20</th>
                                <th>S19</th>
                                <th>S18</th>
                                <th>S17</th>
                                <th>S16</th>
                                <th>S15</th>
                                <th>S14</th>
                                <th>S13</th>
                                <th>S12</th>
                                <th>S12-</th>
                                <th>Sel</th>
                                <th>...</th>
                            </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
                    <input type="button" id="btn_deallocate_wn" value="De-Allocate" style="margin-top: 20px" name="perm_53"/>
                </div>
            </div>
        </form>
        <jsp:include page="footer.jsp"/>

        <script>
            $.getScript(getAbsolutePath() + "/js/allocation.js");
            $(document).ready(function() {

                $.sendRequest({
                    target: "#instruction_ref_filter",
                    action: "get_pi_si_ref",
                    data: {"id": '${ins}', "type": '${ins_type}'},
                    status: "replace_html",
                    asynchronous: false,
                }, function(msg) {
                    scrollElTop("#instruction_ref_filter","li.chosen");
                    getInstructionInfo();
                    $.sendRequest({
                        target: "#grade_filter",
                        action: "get_grade_by_ins_id",
                        data: {id: "${ins}", type: "${ins_type}"},
                        status: "append_html"
                    }, function(msg) {
                        var type = $("#inst_type_M").hasClass("chosen");
                        if (!type) {
                            scrollElTop("#grade_filter", "li.chosen");
                        } else {
                            $("#grade_-1").remove();
                        }
                        loadTable();
                    });
                    return true;
                });
//                sendJsonRequest("instruction_ref_filter", "get_pi_si_ref", {"id": '${ins}', "type": '${ins_type}'}, 0, [
//                    {"name": "scrollElTop", "params": ["#instruction_ref_filter", "li.chosen"]},
//                    {"name": "sendJsonRequest", "params": ["grade_filter", "get_grade_by_ins_id", {"id": '${ins}', "type": '${ins_type}'}, 1, [{"name": "scrollElTop", "params": ["#grade_filter", "li.chosen"]}], false]},
//                    {"name": "getInstructionInfo", "params": []}
//                ], false);
            });
            $("#type_filter li").on("click", function() {
                console.log("type_filter" + $(this).attr("id").split("_")[2]);
                getFilterValue($(this).attr("id").split("_")[2]);

            });
            function getFilterValue(type_id) {
                sendJsonRequest("instruction_ref_filter", "alloc_get_inst_by_type",
                        {type: type_id}
                , 0, [
                    {name: "remove_inst_head", params: []}
                ]);
            }
            function remove_inst_head() {
                $("#instruction_ref_filter li#inst_-1").remove();
            }
            function updateInstInfo() {
                $(".inst_info").html($.templates.inst_info.render($.data(document.body, "instruction_info")));
                $.formatNumber(3);
            }
            function getInstructionInfo() {
////                $("#instruction_ref_filter li").each(function() {
////                    if ($(this).hasClass("chosen")) {
//                sendJsonRequest("", "get_inst_info", {
//                    id: $("#instruction_ref_filter li.chosen").attr("id").split("_")[1],
//                    type: $("#type_filter li.chosen").attr("id").split("_")[2]
//                }, 5, [
//                    {name: "updateInstInfo", params: []}
//                ], true, ["instruction_info"]);
////                    }
////                });
                $.sendRequest({
                    action: "get_inst_info",
                    data: {
                        id: $("#instruction_ref_filter li.chosen").attr("id").split("_")[1],
                        type: $("#type_filter li.chosen").attr("id").split("_")[2]
                    },
                    status: "append_data",
                    optional: ["instruction_info"],
                    functions: [
                        {name: "updateInstInfo", params: []}
                    ]
                });

            }
            function getInstructionInfoEl(el) {
////                $("#instruction_ref_filter li").each(function() {
////                    if ($(this).hasClass("chosen")) {
//                sendJsonRequest("", "get_inst_info", {
//                    id: $(el).attr("id").split("_")[1],
//                    type: $("#type_filter li.chosen").attr("id").split("_")[2]
//                }, 5, [
//                    {name: "updateInstInfo", params: []}
//                ], true, ["instruction_info"]);
////                    }
////                });
                $.sendRequest({
                    action: "get_inst_info",
                    data: {
                        id: $(el).attr("id").split("_")[1],
                        type: $("#type_filter li.chosen").attr("id").split("_")[2]
                    },
                    status: "append_data",
                    optional: ["instruction_info"],
                    functions: [
                        {name: "updateInstInfo", params: []}
                    ]
                });

            }
            function show_wnr_result() {
                var data = $.data(document.body, "wnr_result");
                var type = "info";
                if (data.msg != undefined && data.msg.length > 0) {
                    type = "error";
                }
                $.fn.callDialog($.data(document.body, "wnr_result"), {type: type});
            }
            function reenable_button(elm) {
                console.log("with remove: ", elm);
                $(elm).removeClass("disabled");
            }
        </script>
    </body>
</html>

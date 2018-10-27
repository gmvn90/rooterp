<%-- 
    Document   : stock
    Created on : Jun 28, 2013, 11:11:33 PM
    Author     : kiendn
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Stock Page</title>
    </head>
    <body>
        <jsp:include page="header.jsp">
            <jsp:param name="url" value="9"/>
            <jsp:param name="page" value="2"/>
        </jsp:include>
        <form id="stock_form" method="POST">
            <div  style='width: 1500px !important; margin-left: 200px; display: -webkit-flex; text-align: center'>
                <div id="main_content" style='width: 450px !important'>
                    Client/Pledge
                    <select name="client" id="client" style="width: 80%">
                        <option value="-1">All</option>
                    </select>
                    <div class="center" style="height: 200px; margin-top: 60px">
                        <font style="text-decoration: underline">Stock Report</font>
                        <ul style="line-height: 30px; text-align: left; margin-left: 25%" class="list_date_filter">
                            <li>
                                Date
                            </li>
                            <li>
                                <label>
                                    <input type="text" id="stock_date_filter" class="date_picker_text" readonly="true" val=""/>
                                    <a href="javascript:void(0);" class="clear_date_filter">Reset</a>
                                </label>
                            </li>
                        </ul>
                        <input class="" type="button" id="print_stock_report" value="Print" name="" style="margin-top: 20px; margin-left: 48%"/>
                    </div>

                    <div id="rp_detail" style="min-height: 400px; border-top: 1px solid gray; padding-top: 50px">
                        <font style="text-decoration: underline; margin-left: 35%">Import/Export Report</font>
                        <div style="text-align: center; width: 400px !important; height: 40px; overflow: hidden; margin-top: 20px" class="center">
                            Type
                            <select name="exim_report_type" id="exim_report_type" style="width: 60%">
                                <option value="IM">Import</option>
                                <option value="EX">Export</option>
                            </select>
                        </div>
                        <ul style="line-height: 30px; text-align: left; margin-left: 25%" class="list_date_filter">
                            <li>
                                From Date
                            </li>
                            <li>
                                <label>
                                    <input type="text" id="from_date_filter" class="date_picker_text" readonly="true" val=""/>
                                    <a href="javascript:void(0);" class="clear_date_filter">Reset</a>
                                </label>
                            </li>
                            <li>
                                To Date
                            </li>
                            <li>
                                <label>
                                    <input type="text" id="to_date_filter" class="date_picker_text" readonly="true" val=""/>
                                    <a href="javascript:void(0);" class="clear_date_filter">Reset</a>
                                </label>
                            </li>
                        </ul>
                        <input class="" type="button" id="print_exim_report" value="Print" name="" style="margin-top: 20px; margin-left: 63%"/>
                    </div>
                </div>
                <div id="main_content" style='width: 1000px !important'>
                    <div class="pc100" style="height: 50px">
                        <div class="center">
                            Warehouse: <select name="session" id="session" style="width: 30%; margin-right: 140px">
                            </select>
                            <input type="button" class="right" name="perm_42" id="create_new_map" value="Create new map"/>
                        </div>
                    </div>
                    <div class="pc100 center" style="width: 1000px">
                        <div class="left" style="width: 850px">
                            <label id="update_info" style="display: none">(Last updated: minhdn)</label>
                            <table class="warehousemap" border="1" style="margin-left: 20%">
                                <tbody id="drawcell">

                                </tbody>
                            </table>

                        </div>
                        <div class="right" style="width: 150px; text-align: left">
                            <table class="note_table">
                                <tbody>
                                    <tr>
                                        <td class="storage"></td>
                                        <td>Storage</td>
                                    </tr>
                                    <tr>
                                        <td class="room_machine"></td>
                                        <td>Room or Machine</td>
                                    </tr>
                                    <tr>
                                        <td class="out_of_use"></td>
                                        <td>Out of use</td>
                                    </tr>
                                    <tr>
                                        <td class="door"></td>
                                        <td>Door</td>
                                    </tr>
                                    <tr>
                                        <td class="wall"></td>
                                        <td>Wall</td>
                                    </tr>
                                </tbody>
                            </table>
                            <input type="button" name="perm_41" id="btn_edit" value="Edit"/>
                            <input type="button" name="perm_60" id="btn_delete" value="Delete"/>
                        </div>
                    </div>
                    <input type="hidden" id="ss_id" name="ss_id" value=""/>
                    <input type="hidden" id="id_cell_view" name="id_cell_view" value=""/>
                </div>
            </div>
        </form>
        <jsp:include page="footer.jsp"/>
        <script>
            var flag_id_div;
            var flag_map_id;
            $(document).ready(function() {
                getSelection("stock_form", "session", "selection_warehouse_map", -1);
                $.sendRequest({
                    status: "append_data",
                    action: "check_client_user",
                    optional: ["check_client_user_status"],
                    functions: [
                        {name: "afterCheckingClientUser", params: []}
                    ]
                });

            }).on("change", "#session", function() {
                if ($("#session").val() !== "-1") {
                    $("#ss_id").val($(this).val());
                    flag_map_id = $(this).val();
                    sendRequestHtmlWithoutAuth("stock_form", "drawcell", "bind_map_data");
                } else {
                    $("#ss_id").val("");
                    $("#drawcell").html("");
                }
            }).on("change", "#client", function() {
                if ($("#session").val() !== "-1") {
                    $("#ss_id").val($("#session").val());
                    flag_map_id = $("#session").val();
                    sendRequestHtmlWithoutAuth("stock_form", "drawcell", "bind_map_data");
                }
            }).on("click", "#btn_add_new", function() {
                $("#ss_id").val($(this).val());

                sendRequestHtmlWithoutAuth("stock_form", "drawcell", "add_new_map");
            }).on("click", "#btn_edit", function() {
                if ($("#session").val() !== "") {
                    var styles = new Array();
                    styles.push(
                            {"name": "height", "style": "90%"},
                    {"name": "width", "style": "850px"},
                    {"name": "top", "style": "130px"},
                    {"name": "overflow", "style": "auto"},
                    {"name": "font-size", "style": "13px"}
                    );
                    openDialog("stock_form", "edit_warehouse_map", "0.6", styles);
                }
            }).on("click", "#btn_delete", function() {
                if ($("#ss_id").val() !== "") {
                    var r = confirm("Delete warehouse map " + $("#session option:selected").html() + "?");
                    if (r == true) {
                        //get map id
                        var map_id = $("#session option:selected").val();
                        sendJsonRequest("", "delete_warehouse_map", {"map_id": map_id}, 5, [
                            {name: "afterDeletingWarehouseMap", params: []}
                        ], true, ["delete_response"]);
                    } else {
                    }
                }
            }).on("click", "#btn_save", function() {
                $("#map_id").val(flag_map_id);
                $("#strJSON").val(JSON.stringify(cellsChangesJson));
                sendRequestHtmlWithoutAuth("stock_form", "drawcell", "apply_cell_changes", [{'name': 'closeOpacity', 'params': []}]);
            }).on("click", "#area_choose", function() {
                var styles = new Array();
                styles.push(
                        {"name": "max-height", "style": "850px"},
                {"name": "width", "style": "850px"},
                {"name": "top", "style": "130px"},
                {"name": "overflow", "style": "auto"},
                {"name": "font-size", "style": "13px"}
                );
                openDialog("stock_form", "open_area_selection", "0.6", styles);
            }).on("click", "table.warehousemap td", function() {
                if ($(this).hasClass("storage")) {
                    $("#id_cell_view").val($(this).attr("id"));
                    var styles = new Array();
                    styles.push(
                            {"name": "max-height", "style": "850px"},
                    {"name": "width", "style": "850px"},
                    {"name": "top", "style": "130px"},
                    {"name": "overflow", "style": "auto"},
                    {"name": "font-size", "style": "13px"}
                    );
                    openDialog("stock_form", "view_area_weight_note_receipts", "0.6", styles);
                }

            }).on("click", "#create_new_map", function() {
                var styles = new Array();
                styles.push(
                        {"name": "max-height", "style": "850px"},
                {"name": "width", "style": "850px"},
                {"name": "top", "style": "130px"},
                {"name": "overflow", "style": "auto"},
                {"name": "font-size", "style": "13px"}
                );
                openDialog("stock_form", "add_new_map", "0.6", styles);
            }).on("click", "#submit_creating", function() {
                $("#session").html("");
                sendJsonRequest("drawcell", "create_map_and_cell", {
                    "name": $("#new_map_name").val(),
                    "col": $("#col_input").val(),
                    "row": $("#row_input").val(),
                    "wall_hor": $("#wallpoint_hor_input").val(),
                    "wall_ver": $("#wallpoint_ver_input").val()}, 0,
                        [
                            {"name": "getSelection", "params": ["stock_form", "session", "selection_warehouse_map", -1]}, 
                            {'name': 'closeOpacity', 'params': []}, 
                            {"name": "changeitem", "params": []}
                        ]);
            }).on("click", ".clear_date_filter", function() {
                $(this).siblings("input").val("");
            }).on("click", "#print_stock_report", function() {
                var input = $("#stock_date_filter").val().split("-");
                var input_date = parseInt(input[2] + input[1] + input[0]);
                var check_date = 20131202;
                if ((input_date >= check_date) || input !== "") {
                    var now = new Date();
                    now = now.getDate() + "-" + now.getMonth() + "-" + now.getYear();
                    $.sendRequest({
                        status: "export",
                        action: "get_sro",
                        data: {
                            "date": $("#stock_date_filter").val(),
                            "client": $("#client option:selected").val(),
                            "map": $("#session option:selected").val()
                        }
                    });
                } else {
                    $.warning("The date should be after 01-12-2013");
                }

            }).on("click", "#print_exim_report", function() {

                $.sendRequest({
                    status: "export",
                    action: "get_exim_report",
                    data: {
                        "type": $("#exim_report_type").val(),
                        "from": $("#from_date_filter").val(),
                        "to": $("#to_date_filter").val(),
                        "client": $("#client option:selected").val(),
                        "client_name": $("#client option:selected").html()
                    }
                });
            });

            function changeitem() {
                $("#session").val($('#session option:last-child').val());
                $("#ss_id").val($('#session option:last-child').val());
            }

            function afterDeletingWarehouseMap() {
                if ($.data(document.body, "delete_response") == "1") {
                    $("#drawcell").html("");
                    $("#session").html("");
                    getSelection("stock_form", "session", "selection_warehouse_map", -1);
                    $.success("Delete successfully!");
                } else if ($.data(document.body, "delete_response") == "2") {
                    $.error("Cannot delete map which is containing Weight Note Receipt!");
                }
            }

            function afterCheckingClientUser() {
                if ($.data(document.body, "check_client_user_status") == "0") {
                    getSelection("stock_form", "client", "selection_company", -1);
                } else {
                    var client_info = $.data(document.body, "check_client_user_status");
                    $("#client").html("<option value='" + client_info.id + "'>" + client_info.name + "</option>");
                }
            }
        </script>
    </body>
</html>

<%-- 
    Document   : weighing_detail
    Created on : Jul 5, 2013, 4:05:25 PM
    Author     : kiendn
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!--
To change this template, choose Tools | Templates
and open the template in the editor.
-->
<!DOCTYPE html>
<html>
    <head>
        <title></title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <style>
            table.table_detail_content {line-height: 20px; margin-top: 15px; font-size: 13px}
            table.table_detail_content,table.table_detail_content td {border-collapse: collapse}
            table.table_detail_content td {padding: 5px}
            table.table_detail_content td.firstcol {width: 150px;}
            table.table_detail_content td.secondcol {width: 200px; padding-left: 20px !important;}
            div#printEl{
                position: fixed;
                width: 100%;
                height: 100%;
                background: white;
                top:0;
                left:0;
            }
        </style>
    </head>
    <body>
        <jsp:include page="header.jsp">
            <jsp:param name="url" value="14"/>
            <jsp:param name="page" value="7"/>
        </jsp:include>
        <c:set var="initial_id" value="${initial_id}"></c:set>
        <c:set var="initial_type" value="${initial_type}"></c:set>
            <form id="weighing_form" method="POST">
                <div style="width: 1600px ! important; padding: 15px; background: none repeat scroll 0% 0% white; height: auto; overflow: hidden" class="container_16 border" id="wrapper">
                    <!-- left layer -->
                    <div style="width: 260px;padding-bottom: 10px;" class="left border">
                        <!-- 3 parts: title at the top, ref_list on the middle, add and del button at the bottom -->
                        <!-- title -->
                        <div class="title">
                            WN Ref
                        </div>
                        <!-- ref_list section would have 2 parts: input element to input search String and the list that displays the result -->
                        <div class="pc100">
                            <input type="text" style="width: 240px; margin-left: 5px" name="filter_ref" id="filter_ref">
                            <input type="hidden" id="waypoint"/>
                            <ul style="margin-top: 15px; margin-left: 5px; padding-left: 10px; width: 222px !important" id="process_list" class="ref_list">

                            </ul>
                        </div>
                        <div style="margin-top: 15px" class="pc100 movHide">
                            <input type="button" value="Del" id="btn_delete" class="right" style="margin-right: 10px" name="perm_36">
                            <input type="button" value="Add" id="btn_add_new" class="right" name="perm_35">
                        </div>
                    </div>
                    <!-- right layer -->
                    <div style="width: 1300px !important; margin-right: 0px !important;" class="grid_14 right">
                        <table class="table_filter">
                            <thead>
                                <tr style="font-size: 13px">
                                    <th width="100px">Type</th>
                                    <th width="190px" id="instruction_title" class="movHide">Delivery Instruction</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td>
                                        <input type="text" class="text_filter_child">
                                        <ul id="type_filter" name="perm_40">
                                            <li id="wnType_1" class="chosen">Import</li>
                                            <li id="wnType_2">In-Process</li>
                                            <li id="wnType_3">Ex-Process</li>
                                            <li id="wnType_4">Export</li>
                                        </ul>
                                    </td>
                                    <td class="movHide">
                                        <input type="text" style="width: 190px" class="text_filter_child">
                                        <ul id="instruction_ref_filter" class="filter">
                                        </ul>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                        <div id="wn_content" style="width: 1300px !important; min-height: 450px; height: auto; overflow: hidden;margin-right: 0px; margin-top: 15px" class="border">

                        </div>
                        <div class="right" style="width: 1300px; margin-top: 20px;">
                            <input class="left" type="button" id="btnSaveWN" value="Save" name="perm_38" permit="0"/>
                            <input class="right" id="btn_view_list" type="button" value="List View" />
                            <input class="right" type="button" id='print_wn' value="WN" />
                            <input class="right" type="button" id='print_wnr' value="WNR" />

                        </div>
                    </div>
                </div>
                <input type="hidden" id="wn_id" name="wn_id" value="${initial_id}"/>
            <input type="hidden" id="wn_fwtype" name="wn_fwtype" value="${initial_type}"/>
            <input type="hidden" id="cell_id_name" name="cell_id_name" value=""/>
            <input type="hidden" id="map_name" name="map_name" value=""/>
            <input type="hidden" id="delete_desc" name="delete_desc" value=""/>
        </form>
        <jsp:include page="footer.jsp"/>
        <script>
            var pressed = false;
            var chars = [];
            var row = 0;
            $.getScript(getAbsolutePath() + "/js/weighing.js");
            var flag_ordinate;
            var flag_id_div;
            var flag_map_name;
            var count = 0;
            $(document).ready(function () {
                $.sendRequest({
                    action: "get_packing_map",
                    status: "append_data",
                    optional: ["packing_data"]
                });
                //load instruction
                if ($("#wn_fwtype").val() !== "" && $("#wn_fwtype").val() !== null) {
                    $("#type_filter li.chosen").toggleClass("chosen");
                    var wn_type_check = $("#wn_fwtype").val();
                    var wn_type;
                    switch (wn_type_check) {
                        case "IM":
                            wn_type = "1";
                            break;
                        case "IP":
                            wn_type = "2";
                            break;
                        case "XP":
                            wn_type = "3";
                            break;
                        case "EX":
                            wn_type = "4";
                            break;
                        case "M":
                            wn_type = "5";
                            break;
                    }
                    $("#wnType_" + wn_type).addClass("chosen");
                }

                var wn_type = $("#type_filter li.chosen").attr("id").split("_")[1];

                loadByWnType(wn_type);
                //load instruction base on type
                loadInsRefList("weighing_form", "instruction_ref_filter", "get_ins_ref", wn_type);

//    sendRequest("weighing_form", "grade_filter", "wn_get_grade_filter");
                //sendRequest("weighing_form", "supplier_filter", "get_supplier_filter");
                resetRow();
                reloadWeightNoteRefList();
                //check_forward();
//            }).on("click", "#area", function () {
////                if ($("#cell_id_name").val() != 980 && $("#map_name").val() != 11) {
//                var styles = new Array();
//                styles.push(
//                        {"name": "height", "style": "90%"},
//                {"name": "width", "style": "850px"},
//                {"name": "top", "style": "130px"},
//                {"name": "overflow", "style": "auto"},
//                {"name": "font-size", "style": "13px"}
//                );
//                openDialog("weighing_form", "open_area_selection", "0.6", styles);
////                }
//            }).on("change", "#map_select", function () {
//                if ($("#map_select").val() !== "") {
//                    $("#map_id").val($(this).val());
//                    $("#map_name").val($("#map_select :selected").text());
//                    flag_map_name = $("#map_select :selected").text();
//                    sendRequestNotFade("weighing_form", "area_map", "bind_map_data_area");
//                } else {
//                    $("#map_id").val("");
//                    $("#area_map").html("");
//                }
//            }).on("click", "table.warehousemap_editable_area td", function () {
//                if ($(this).hasClass("storage")) {
//                    $("table.warehousemap_editable_area td.chosen div.listCell").html("");
//                    $("table.warehousemap_editable td.chosen").toggleClass("chosen");
//                    $("#current").val($(this).attr("class"));
//                    $(this).addClass("chosen");
//                    sendRequestNotFade("weighing_form", $(this).attr("id") + "_list", "generate_list_on_area");
//                    flag_ordinate = $(this).attr("ordinate");
////                    flag_id_div = $(this);
//                    $("#cell_id").val($(this).attr("id"));
//                } else {
//                    $("table.warehousemap_editable_area td.chosen div.listCell").html("");
//                }
//            }).on("click", "#li_choose", function (e) {
//                flag_area_change = "changed";
//                e.stopPropagation();
////                $("#cell_id_name").val($(flag_id_div).attr("id"));
////                $("#id_warehousecell").val($(flag_id_div).attr("id"));
//                var map_name = $("#map_select :selected").text();
//                $("#cell_id_name").val($("#li_choose").parent().parent().attr("id").split("_")[0]);
//                $("#area").val(map_name + "-" + flag_ordinate);
//                closeOpacity();
//            }).on("click", "#li_view_weight_note", function (e) {
//                e.stopPropagation();
//                $("table.warehousemap_editable_area td.chosen div.listCell").html("");
//                $("table.warehousemap_editable td.chosen").toggleClass("chosen");
//                $("#current").val(flag_id_div.attr("class"));
//                $(flag_id_div).addClass("chosen");
//                var styles = new Array();
//                styles.push(
//                        {"name": "max-height", "style": "850px"},
//                {"name": "width", "style": "850px"},
//                {"name": "top", "style": "130px"},
//                {"name": "overflow", "style": "auto"},
//                {"name": "font-size", "style": "13px"}
//                );
//                openDialog1("weighing_form", "generate_area_weight_note_receipts", "0.6", styles);
            }).on("click", "#btn_view_list", function () {
                if (!$(this).hasClass("disabled")) {
                    $("#weighing_form").attr("action", getAbsolutePath() + "/weighing_listview.htm");
                    $("#weighing_form").submit();
                }
            }).on("click", "#wn_status", function () {
                //change the value
                var stt = $(this).val();

                if (stt == 1) {//checked - uncomplete an WN
                    $.error("Cannot Uncomplete This Weight Note, please contact Administrator!");
                    $(this).prop("checked", true);
//check the weight note is IM or others, if it's IM then check the DI, if DI complete then nothing happens.
//   - get the type (1 is import) and inst_id from the filter
//                    var type = $("#type_filter li.chosen").attr("id").split("_")[1];
//                    if (type == 1) {
//                        var inst_id = $("#instruction_ref_filter li.chosen").attr("id").split("_")[1];
//                        sendJsonRequest("", "get_di_status", {id: inst_id}, 5, [
//                            {name: "uncheckStatus", params: [this]}
//                        ], true, ["di_status"]);
//                    } else {
//                        //uncheck this
//                        $(this).attr("value", 0);
//                        $(this).prop("checked", false);
//                    }
                }
                if (stt == 0) {//unchecked - complete an WN
                    //check length of wnr
                    if ($("#weighing_section").length > 0) {
                        //check type
                        var type = $("#type_filter li.chosen").attr("id").split("_")[1];
                        if (type == 1 || type == 3 || type == 4) {
                            //check QR status
                            //get wn_id
                            var wn_id = $("#process_list li.chosen").attr("id").split("_")[1];
                            sendJsonRequest("", "get_qr_status", {wn_id: wn_id}, 5, [
                                {name: "checkStatus", params: [this]}
                            ], true, ["qr_status"]);
                        } else {
                            $(this).attr("value", 1);
                            $(this).prop("checked", true);
                        }
                    } else {
                        $.error("Cannot Complete This Weight Note");
                        //callMessageDialog({type: "error", message: "Cannot Complete This Weight Note"});
                        $(this).prop("checked", false);
                    }
                }
            }).on("click", "#btn_delete", function () {
                $("#wn_id").val("");
                if ($("#process_list li.chosen").html() !== undefined) {
                    var r = $.callConfirm("Delete weightnote " + $("#process_list li.chosen").html() + "?", function (status, reason) {
                        if (status === true) {
                            //get wn id
                            var wn_id = $("#process_list li.chosen").attr("id").split("_")[1];
                            $.sendRequest({
                                action: "delete_wn",
                                data: {wn_id: wn_id, reason: reason},
                                status: "append_data",
                                optional: ["delete_response"],
                                functions: [
                                    {name: "afterDeletingWn", params: []}
                                ]
                            });
//                        sendJsonRequest("", "delete_wn", {"wn_id": wn_id}, 5, [
//                            {name: "afterDeletingWn", params: []}
//                        ], true, ["delete_response"]);
                        }
                    });
                }
            }).on("click", ".wnr_delete", function () {
                var wn_info = $.data(document.body, "wn_info")[0];
                var wnr_id = $(this).parent().parent().attr("id").split("_")[2];
                var wn_id = (wn_info != undefined) ? wn_info.id : $(".ref_list li.chosen").attr("id").split("_")[1];
                checkWnEditable(wn_id);
                if ($.data(document.body, "wn_editable") == "true") {
                    if (!$(this).hasClass("disabled")) {
                        var r = $.callConfirm("Delete weightnote receipt " + $(this).parent().parent().children().eq(1).children().val() + "?", function (status, reason) {
                            if (status == true)
                            {
                                //get wnr id for deleting
//                            var wnr_id = $(this).parent().parent().attr("id").split("_")[2];
                                //get wn id for reloading wn detail
//                            var wn_id = $(".ref_list li.chosen").attr("id").split("_")[1];
                                //do change and reload after that
                                $.sendRequest({
                                    action: "delete_wnr",
                                    data: {wn_id: wn_id, wnr_id: wnr_id, reason: reason},
                                    status: "append_data",
                                    optional: ["delete_response"]
                                }, function (msg) {
                                    afterDeletingWnr();
                                    return false;
                                });
//                        sendJsonRequest("", "delete_wnr", {"wn_id": wn_id, "wnr_id": wnr_id}, 5, [
//                            {"name": "afterDeletingWnr", "params": []}
//                        ], true, ["delete_response"]);
                            }
                        });
                    }
                } else {
                    $.warning("This weight note is untouchable, please contact administrator!");
                }

            }).on("click", ".wnr_save", function () {
                var wnr_id = $(this).parent().parent().attr("id").split("_")[2];
                var packing = $("#wnr_packing_" + wnr_id).val();
                var noOfBag = $("#wnr_noOfBag_" + wnr_id).val();
                var gross = $("#wnr_gross_" + wnr_id).val();
                var tare = $("#wnr_tare_" + wnr_id).val();
                var wn_info = $.data(document.body, "wn_info")[0];
                var wn_id = (wn_info != undefined) ? wn_info.id : $(".ref_list li.chosen").attr("id").split("_")[1];
                checkWnEditable(wn_id);
                if ($.data(document.body, "wn_editable") == "true") {
                    if (!$(this).hasClass("disabled")) {
                        $.sendRequest({
                            action: "update_wnr",
                            data: {
                                wnr_id: wnr_id,
                                packing: packing,
                                noOfBag: noOfBag,
                                gross: gross,
                                tare: tare
                            },
                            status: "append_data",
                            optional: ["delete_response"]
                        }, function (msg) {
                            if (msg === "1") {
                                $.success("Update successfully!");
                                var wn_id = $(".ref_list li.chosen").attr("id").split("_")[1];
                                getWn(wn_id, false);
                            } else {
                                $.error("Cannot update weight note receipt!");
                            }
                            return false;
                        });

                    }
                } else {
                    $.warning("This weight note is untouchable, please contact administrator!");
                }

            }).on("click", "#print_wnr", function () {
                var file = getAbsolutePath() + "/js/templates/weighing/wnr_list.html";
                $.when($.get(file))
                        .done(function (tmplData) {
                            if ($("#printEl").length == 0) {
                                $("body").append("<div id='printEl'></div>");
                            }
                            $.sendRequest({
                                target: "#printEl",
                                action: "print_wnr_list",
                                status: "apply_template",
                                data: {wn_id: $("#process_list .chosen").attr("id").split("_")[1]},
                                optional: {opt: "replace", tmpl: tmplData + ""},
                                functions: [
                                    {name: "printEl", params: []}
                                ]
                            });
                        });

            }).on("click", "#truck_mode_none", function () {
                if ($(this).prop("checked") == true) {
                    var type = $.data(document.body, "wn_info")[0].type;
                    if (type == 'IM') {
                        $("#wnr_processing_head").remove();
                        $("#wnr_processing_column").remove();
                        //if wn is new --> weighing section is empty
                        if ($("#weighing_section").html().trim() == "") {
                            $("#area").val("");
                            $("#cell_id_name").val("");
                            $("#id_warehousemap").val("");
                        }
                        if ($("#wnr_bags_column").length == 0) {
                            $("#wnr_pallet_head").before(function () {
                                return "<th id='wnr_packing_head'>Packing</th><th id='wnr_bags_head'>No Of Bags</th>"
                            });
                            $("#wnr_pallet_column").before(function () {
                                var truck_bags = '<td id="wnr_bags_column"><input type="text" name="wnr_noOfBag" id="wnr_noOfBag" class="numberOnly" style="width: 100px !important"/></td>';
                                var truck_pack = '<td id="wnr_pack_column"><select style="width: 150px" name="wnr_packing" id="wnr_packing">' + $("#packing").html() + '</select></td>';
                                return truck_pack + truck_bags;
                            });
                        }
                        $("#gross_text").text("Gross weight");
                        $("#wnr_pallet_head").text("Pallet ID");
                        $("#wnr_pallet_column").html('<input type="text" name="wnr_palletId" id="wnr_palletId" style="width: 150px !important"/>');
                    } else if (type == 'EX') {
                        $("#wnr_text").text("WNR");
                        $("#gross_text").text("Gross weight");
                        $("#wnr_packing_head").remove();
                        $("#wnr_bags_head").remove();
                        $("#wnr_bags_column").remove();
                        $("#wnr_pack_column").remove();
                        if ($("#truck_no").length > 0) {
                            $("#truck_no").attr("id", "allocated_wnr_id");
                        }
                    }
                }
            }).on("click", "#truck_mode_gross", function () {
                if ($(this).prop("checked") == true) {
//                    if ($("#snap").length > 0) {
//                        window.external.showCamera();
//                    }
                    var type = $.data(document.body, "wn_info")[0].type;
                    if (type == 'IM') {
                        //if wn is new --> weighing section is empty
                        if ($("#weighing_section").html().trim() == "") {
                            $.sendRequest({
                                action: "get_area_for_bridge",
                                status: "handle_response",
                                func: function () {
                                    if (msg != "") {
                                        var data = JSON.parse(msg);
                                        $("#area").val(data.area);
                                        $("#cell_id_name").val(data.cell_id);
                                        $("#id_warehousemap").val(data.map);
                                    } else {
                                        $.error("Error on getting area for processing");
                                    }
                                }
                            });
                        }
                        if ($("#wnr_bags_column").length == 0) {
                            $("#wnr_pallet_head").before(function () {
                                return "<th id='wnr_packing_head'>Packing</th><th id='wnr_bags_head'>No Of Bags</th>"
                            });
                            $("#wnr_pallet_column").before(function () {
                                var truck_bags = '<td id="wnr_bags_column"><input type="text" name="wnr_noOfBag" id="wnr_noOfBag" style="width: 100px !important"/></td>';
                                var truck_pack = '<td id="wnr_pack_column"><select style="width: 150px" name="wnr_packing" id="wnr_packing">' + $("#packing").html() + '</select></td>';
                                return truck_pack + truck_bags;
                            });
                        }
                        $("#gross_text").text("Gross weight");
                        $("#wnr_pallet_head").text("Truck");
                        $("#wnr_pallet_column").html('<input type="text" name="wnr_truck_no" id="wnr_truck_no" style="width: 150px !important" readonly/>');
                        $("#wnr_truck_no").val($("#truck_no").val());
                        $("#wnr_processing_head").remove();
                        $("#wnr_processing_column").remove();
                    } else if (type == 'EX') {
                        $("#wnr_text").text("WNR");
                        $("#gross_text").text("Gross weight");
                        $("#wnr_packing_head").remove();
                        $("#wnr_bags_head").remove();
                        $("#wnr_bags_column").remove();
                        $("#wnr_pack_column").remove();
                        if ($("#truck_no").length > 0) {
                            $("#truck_no").attr("id", "allocated_wnr_id");
                        }
                    }
                }
            }).on("click", "#truck_mode_tare", function () {
//                if ($("#snap").length > 0) {
//                    window.external.showCamera();
//                }
                var type = $.data(document.body, "wn_info")[0].type;
                if (type == 'IM') {
                    //if wn is new --> weighing section is empty
                    if ($("#weighing_section").html().trim() == "") {
                        $("#area").val("");
                        $("#cell_id_name").val("");
                        $("#id_warehousemap").val("");
                    }
                    $("#wnr_pallet_head").text("WNR");
                    $("#wnr_pallet_column").html('<input type="text" name="wnr_id" id="wnr_id" style="width: 150px !important"/>');
                    $("#gross_text").text("Tare weight");
                    $("#wnr_packing_head").remove();
                    $("#wnr_bags_head").remove();
                    $("#wnr_bags_column").remove();
                    $("#wnr_pack_column").remove();

                    $("#wnr_pallet_head").after(function () {
                        return "<th id='wnr_processing_head'>Processing Instruction</th>";
                    });

                    $("#wnr_pallet_column").after(function () {
                        return '<td id="wnr_processing_column"><select id="wnr_processing" style="width: 150px !important"><option value="-1" selected></option></select></td>';
                    });

                    $.sendRequest({
                        action: "load_pending_pi",
                        status: "handle_response",
                        func: function () {
                            if (msg == -1) {
                                $.warning("There aren't any pending processing! Please create one!");
                            } else {
                                var data = JSON.parse(msg);
                                console.log(msg);
                                console.log(data);
                                $.data(document.body, "pi_data", data);
                                var tmpl = "{{for pi_list}}<option value='{{:id}}'>{{:ref_number}}</option>{{/for}}";
                                $("#wnr_processing").append($.templates(tmpl).render(data));
                            }
                        }
                    });
                } else if (type == 'EX') {
                    $("#allocated_wnr_id").attr("id", "truck_no");
                    $("#wnr_text").text("Truck");
                    $("#wnr_text").after(function () {
                        return "<th id='wnr_packing_head'>Packing</th><th id='wnr_bags_head'>No Of Bags</th>"
                    });
                    $($("#truck_no").parent()).after(function () {
                        var truck_bags = '<td id="wnr_bags_column"><input type="text" name="wnr_noOfBag" id="wnr_noOfBag" style="width: 100px !important"/></td>';
                        var truck_pack = '<td id="wnr_pack_column"><select style="width: 150px" name="wnr_packing" id="wnr_packing">' + $("#packing").html() + '</select></td>';
                        return truck_pack + truck_bags;
                    });
                }
            }).on("click", ".ref_list li", function () {
                flag_area_change = "nochange";
                var wn_id = $(this).attr("id").split("_")[1];
                var type = $("#type_filter li.chosen").attr("id").split("_")[1];
                console.log("filter ref click : " + wn_id);
                $(".ref_list li.chosen").toggleClass("chosen");
                $(this).addClass("chosen");
                if (type != 5) {
                    getWn(wn_id);
                } else {
                    //get movement
                    $.sendRequest({
                        action: "getCoreInfo",
                        data: {id: wn_id, value: "MovementView"},
                        optional: {url_type: "json"}
                    }, function (msg) {
                        //get wnrs belongs to mov_id
                        var data = msg;
                        $.sendRequest({
                            action: "getMovWnr",
                            data: {id: wn_id}
                        }, function (msg) {
                            $.extend(data, JSON.parse(msg));
                            $.data(document.body, "mov_info", data);
                            var file = getAbsolutePath() + "/js/templates/weighing/movement_info.html";
                            $.when($.get(file))
                                    .done(function (tmplData) {
                                        var tmp = $.templates(tmplData);
                                        $("#wn_content").html(tmp.render(data));
                                        $("#btnMove").button();
                                    });
                        });
                        return true;
                    });
                }
                //sendJsonRequest("wn_content", "get_wn", {"wn_id": wn_id}, 0, [{"name": "select_inst", "params": []}, {"name": "scrollElTop", "params": ["#instruction_ref_filter", "li.chosen"]}, {name: "reloadAuth", params: []}]);
            }).on("click", "#btnMove", function () {
                $.validation([
                    {element: "#wnr_ref", msg: "Please Enter Weight Note Receipt Reference"},
                    {element: "#wnr_palletId", msg: "Please Enter Pallet"},
                    {element: "#wnr_gross", msg: "Please Enter The Weight"},
                ], function () {
                    //check wnr
                    $.sendRequest({
                        action: "check_movement_wnr",
                        data: {mov: $.data(document.body, "mov_info").id, ref: $("#wnr_ref").val()}
                    }, function (msg) {
                        if (msg > 0) {
                            var data = {
                                wnr_id: msg,
                                type: $.data(document.body, "mov_info").type,
                                mov: $.data(document.body, "mov_info").id,
                                area: $.data(document.body, "mov_info").area,
                                areaCode: $.data(document.body, "mov_info").areaCode
                            };
                            if ($("#wnr_palletId").length > 0) {
                                data["pallet"] = $("#wnr_palletId").val();
                            }
                            if ($("#wnr_gross").length > 0) {
                                data["gross"] = $("#wnr_gross").val();
                            }
                            $.sendRequest({
                                action: "update_movement_wnr",
                                data: data
                            }, function (msg) {
                                try {
                                    var data = JSON.parse(msg);
                                    $.success("Update Succeeded");
                                    //reload WNR
                                    $.data(document.body, "mov_info").wnrs.push(data);
                                    console.log(data);
                                } catch (e) {
                                    $.error(msg);
                                }
                            });
                        } else {
                            $.error("Weight Note Receipt does not exist in this movement");
                        }
                        return false;
                    });
                });
//            }).on("keypress", "#allocated_wnr_id", function (e) {
//                chars.push(String.fromCharCode(e.which));
//                if (pressed == false) {
//                    setTimeout(function () {
//                        if (chars.length >= 10) {
//
//                            var barcode = chars.join("");
//                            $("#allocated_wnr_id").val(barcode);
//                            var path = "/upload_snap.htm";
//                            var wn_info = $.data(document.body, "wn_info")[0];
//                            checkWnEditable(wn_info.id);
//                            checkGradeExist(wn_info.id);
//                            checkPackingExist(wn_info.id);
//                            if ($.data(document.body, "wn_editable") == "true") {
//                                if ($.data(document.body, "gradeEmpty") == "true") {
//                                    var validate = $.validation([
//                                        {element: "#truck_no", msg: "Please Enter Truck Number"},
//                                        {element: "#area", msg: "Please Choose Area"},
//                                        {element: "#wnr_noOfBag", msg: "Please Enter Number Of Bags"},
//                                        {element: "#wnr_palletId", msg: "Please Enter Pallet"},
//                                        {element: "#wnr_gross", msg: "Please Enter The Weight"},
//                                        {element: "#wnr_id", msg: "Please Enter Weight note receipt reference"},
//                                        {element: "#allocated_wnr_id", msg: "Please Enter Weight note receipt reference"},
//                                        {
//                                            element: "#wnr_processing",
//                                            msg: "Processing Instruction value is invalid",
//                                            opts: {
//                                                type: "!=",
//                                                value: -1
//                                            }
//                                        },
//                                        {
//                                            element: "#grade",
//                                            msg: "Grade is empty",
//                                            opts: {
//                                                type: "!=",
//                                                value: -1
//                                            }
//                                        }
//                                    ]);
//                                    if (validate) {
//                                        var checked_type = $("input[name=truck_mode]:checked").val();
//                                        if (checked_type == undefined || checked_type == 0) { /* if None */
//                                            weightNone();
//                                        }
//                                        if (checked_type == 1) { /* if Gross */
//                                            if ($("#snap").length > 0) {
//                                                $("#snap").val(window.external.snapping(wn_info.ref_number + "_gross", path));
//                                            }
//                                            weightGross();
//                                        }
//                                        if (checked_type == 2) { /* if Tare */
//                                            if ($("#snap").length > 0) {
//                                                $("#snap").val(window.external.snapping(wn_info.ref_number + "_tare", path));
//                                                alert("after snap: " + $("#snap").val());
//                                            }
//                                            weightTare();
//                                        }
//                                    }
//                                } else {
//                                    $.warning("Please Update Grade Before Adding New WNR");
//                                }
//                            } else {
//                                $.warning("This weight note is untouchable, please contact administrator!");
//                            }
//                        }
//                        chars = [];
//                        pressed = false;
//                    }, 500);
//                }
//                pressed = true;
            });

            function weightTare() {
                var type = $.data(document.body, "wn_info")[0].type;
                var wn_id = $.data(document.body, "wn_info")[0].id;
                var data = {};
                if (type == 'IM') {
                    if ($("#weighing_section").html().trim() != "") {
                        if ($("#weighing_section tr").length == 1) {
                            var wnr = $("#wnr_id").val();
                            data = {wn_id: wn_id, wnr: wnr};
                            $.sendRequest({
                                action: "check_wnr",
                                data: data,
                                status: "append_data",
                                optional: ["response_data"],
                                functions: [
                                    {name: "updateIMTare", params: [wn_id]}
                                ]
//                                func: function() {
//                                    if (msg > 0) {                        /* wnr does exist */
//                                        $.sendRequest({
//                                            action: "bridge_tare",
//                                            data: {
//                                                type: 'IM',
//                                                processing: $("#wnr_processing").val(),
//                                                tare: $("#wnr_gross").val(),
//                                                wn_id: wn_id,
//                                                wnr_id: msg
//                                            },
//                                            status: "append_data",
//                                            optional: ["response_data"],
//                                            functions: [
//                                                {name: "finishUpdateTare", params: []}
//                                            ]
//                                        });
//                                    } else {
//                                        $.error("Weight Note Receipt does not exist");
//                                    }
//                                }
                            });
                        } else {
                            $.error("Cannot use weight bridge in this weight note");
                        }
                    } else {
                        $.warning("Please update gross weight before get tare weight");
                    }
                } else if (type == 'EX') {
                    if ($("#weighing_section").html().trim() == "") {
                        data = {
                            type: 'EX',
                            packing: $("#wnr_packing").val(),
                            wn_id: wn_id,
                            truck_no: $("#truck_no").val(),
                            noOfBags: $("#wnr_noOfBag").val(),
                            tare: $("#wnr_gross").val()
                        };
                        $.sendRequest({
                            target: "#wnr_content",
                            action: "bridge_tare",
                            data: data,
                            status: "handle_response",
                            func: function () {
                                if (msg == "-1") {
                                    $.error("Update Failed");
                                } else {
                                    var data = JSON.parse(msg);
                                    var file = getAbsolutePath() + "/js/templates/weighing/wnr_row.html";
                                    $.data(document.body, "wn_info")[0].wnrs.push(data);
                                    $.when($.get(file))
                                            .done(function (tmplData) {
                                                $("#weighing_section").append($.templates(tmplData).render(data));
                                                $("input[type=button]").button();
                                                getPackingOption({
                                                    target: "#wnr_packing_" + data.id,
                                                    value: data.packing
                                                });
                                                recountWnrField();
                                                do_printing_json(data);
                                            });
                                }
                            }
                        });
                    } else {
                        $.warning("The list is full! Please create or choose another weight note");
                    }
                }
            }

            function finishUpdateTare() {
                var res = $.data(document.body, "response_data");
                if (res != -1) {
                    $.success("New Weight Note Receipt " + res.new_wnr + " has been successfully created");
                } else {
                    $.error("Update Failed");
                }
            }

            function updateIMTare(wn_id) {
                var msg = $.data(document.body, "response_data");
                if (msg > 0) {                        /* wnr does exist */
                    var data = {
                        type: 'IM',
                        processing: $("#wnr_processing").val(),
                        tare: $("#wnr_gross").val(),
                        wn_id: wn_id,
                        wnr_id: msg
                    };
                    if ($("#snap").length > 0) {
                        data["snap"] = $("#snap").val();
                    }
                    $.sendRequest({
                        action: "bridge_tare",
                        data: data,
                        status: "append_data",
                        optional: ["response_data"],
                        functions: [
                            {name: "finishUpdateTare", params: []}
                        ]
                    });
                } else {
                    $.error("Weight Note Receipt does not exist");
                }
            }
            function updateExportGross(wn_id) {
                var data = $.data(document.body, "response_data");
                if (data > 0) {                        /* wnr does exist */
                    $.sendRequest({
                        action: "bridge_gross",
                        data: {
                            type: 'EX',
                            gross: $("#wnr_gross").val(),
                            wn_id: wn_id,
                            wnr_id: data
                        },
                        status: "append_data",
                        optional: ["response_data"],
                        functions: [
                            {name: "afterUpdateExportBridge", params: []}
                        ]
                    });
                } else {
                    $.error("Weight Note Receipt does not exist");
                }
            }

            function afterUpdateExportBridge() {
                var msg = $.data(document.body, "response_data");
                if (msg == 1) {
                    $.success("Update Completed");
                } else {
                    $.error("Update Failed");
                }
            }

            function print_wnr(id) {
                var wnrs = $.data(document.body, "wn_info")[0].wnrs;
                for (var i = 0; i < wnrs.length; i++) {
                    if (wnrs[i].id == id) {
                        do_printing_json(wnrs[i]);
                    }
                }
            }


            function do_printing_json(wnr) {
                var wnr_json = {
                    wn_ref: $.data(document.body, "wn_info")[0].ref_number,
                    wnr: wnr.ref_number,
                    quality: "Robusta",
                    grade: $("#grade option:selected").text(),
                    gross: wnr.gross,
                    tare: wnr.tare,
                    net: wnr.net,
                    date: wnr.date,
                    quantity: wnr.bags,
                    printDate: $.format.date(new Date, "dd-MMM-yy hh:mm:ss a")
                }
                if ($("#printEl").length == 0) {
                    $("body").append("<div id='printEl'></div>");
                }

                $("#printEl").html($.templates.wnr.render(wnr_json));
                $("#printEl div.barcode").barcode(wnr_json.wnr, "code128", {output: "css"});
                //alert($("#printEl").attr("id"));
                if ($("#wsf").length == 0) {
                    $("#printEl").printElement({pageTitle: "SWCBD"});
                    $("#printEl").remove();
                    $("#printStatus").remove();
                } else {
                    //change value wsf to pending (0) if it's 1 (complete) to prevent print twice (unknown reason)
                    if ($("#wsf").val() == 1) {
                        $("#wsf").val(0);
                    }
                    hide_el();
                    window.external.printing();
                }
            }

            function printEl() {
                $("#printEl").printThis({importCSS: true, pageTitle: "SWCBD", removeInline: false});
                $("#printEl").remove();
                $("#printStatus").remove();
            }

            function check_forward() {
                if ($("#wn_id").val() != null && $("#wn_id").val() != "") {
                    console.log(++count);
                    flag_area_change = "nochange";
                    var wn_id = $("#wn_id").val();
                    $(".ref_list li.chosen").toggleClass("chosen");
                    $("#wn_" + wn_id).addClass("chosen");
                    scrollElTop("#process_list", "li.chosen");
                    getWn(wn_id);
//                    $.sendRequest({
//                        target: "#wn_content",
//                        action: "get_wn",
//                        data: {wn_id: wn_id},
//                        status: "append_html",
//                        functions: [
//                            {"name": "select_inst", "params": []},
//                            {"name": "scrollElTop", "params": ["#instruction_ref_filter", "li.chosen"]}
//                        ]
//                    });
                    //sendJsonRequest("wn_content", "get_wn", {"wn_id": wn_id}, 0, [{"name": "select_inst", "params": []}, {"name": "scrollElTop", "params": ["#instruction_ref_filter", "li.chosen"]}]);
                    $("#wn_id").val("");
                }
            }

            function afterDeletingWn() {
                if ($.data(document.body, "delete_response") == "1") {
                    $("#wn_content").html("");
                    $(".ref_list li.chosen").toggleClass("chosen");
                    $.success("Delete successfully!");
                    //callMessageDialog({type: "success", message: "Delete successfully!"});
                    resetRow();
                    reloadWeightNoteRefList();
                } else if ($.data(document.body, "delete_response") == "2") {
                    var wn_id = $(".ref_list li.chosen").attr("id").split("_")[1];
                    $.sendRequest({
                        action: "delete_wn_surround",
                        data: {wn_id: wn_id},
                        status: "append_data",
                        optional: ["detail_response"],
                        functions: [
                            {name: "callDialog", params: ["detail_response", {type: "error", autoCloseOverlay: true}]}
                        ]
                    });
//                    sendJsonRequest("weighing_form", "delete_wn_surround", {"wn_id": wn_id}, 5, [
//                        {name: "callDialog", params: ["detail_response", {type: "error", autoCloseOverlay: true}]}
//                    ], true, ["detail_response"]);
                }
            }

            function afterDeletingWnr() {
                if ($.data(document.body, "delete_response").status === "success") {
                    $.success("Delete successfully!");
                    var wn_id = $(".ref_list li.chosen").attr("id").split("_")[1];
                    getWn(wn_id, false);
                } else {
                    $.error("Cannot delete allocated weight note receipt!");
                }
            }
            function afterSavingWnr() {
                if ($.data(document.body, "delete_response") === "1") {
                    $.success("Update successfully!");
                    var wn_id = $(".ref_list li.chosen").attr("id").split("_")[1];
                    getWn(wn_id, false);
                } else {
                    $.error("Cannot update weight note receipt!");
                }
            }
            function uncheckStatus(el) {
                var inst_status = $.data(document.body, "di_status");
                if (inst_status == 0) {
                    $(el).prop("checked", false);
                    $(el).attr("value", 0);
                }
                if (inst_status == 1) {
                    $.warning("You must uncheck DI " + $("#instruction_ref_filter li.chosen").text() + " status before uncheck this weight note status");
                    //callMessageDialog({type: "warning", message: "You must uncheck DI " + $("#instruction_ref_filter li.chosen").text() + " status before uncheck this weight note status"});
                    $(el).prop("checked", true);
                }
            }
            function checkStatus(el) {
                var qr_status = $.data(document.body, "qr_status");
                if (qr_status == 1) {
                    $(el).prop("checked", true);
                    $(el).attr("value", 1);
                }
                if (qr_status == 0) {
                    $.warning("You must complete quality report before complete this weight note");
                    //callMessageDialog({type: "warning", message: "You must complete quality report before complete this weight note"});
                    $(el).prop("checked", false);
                }
            }

            function getPackingOption(opts) {
                if (opts.target != undefined) {
                    var tmpl = $.templates("<option value='{{:id}}'>{{:name}}</option>");
                    $(opts.target).html(tmpl.render($.data(document.body, "packing_data").packing));
                    if (opts.value != undefined) {
                        $(opts.target + " option").each(function () {
                            if ($(this).val() == opts.value) {
                                $(this).prop("selected", true);
                            }
                        });
                    }
                }
            }
            function getWn(wn_id, flag) {
                var res = (flag != undefined && typeof (flag) == "boolean") ? flag : true;
                var id = (wn_id != undefined) ? wn_id : $("#wn_id").val();
                var file = getAbsolutePath() + "/js/templates/weighing/wn_info.html";
                $.when($.get(file))
                        .done(function (tmplData) {
                            //$('#wn_content').html($.templates(tmplData).render($.data(document.body, "new_wn")));
                            $.sendRequest({
                                target: "#wn_content",
                                action: "get_wn",
                                data: {wn_id: id},
                                status: "apply_template",
                                optional: {opt: "replace", tmpl: tmplData, dataBody: "wn_info"},
//                        functions: [
//                            {name: "select_inst", params: []},
//                            {name: "scrollElTop", params: ["#instruction_ref_filter", "li.chosen"]},
//                            {name: "reloadAuth", params: []}
//                        ]
                            }, function (msg) {
                                select_inst();
                                scrollElTop("#instruction_ref_filter", "li.chosen");
                                reloadAuth();
                                return res;
                            });
                        });

            }

            function checkWnEditable(wn_id) {
                $.sendRequest({
                    asynchronous: false,
                    action: "check_wn_editable",
                    data: {id: wn_id}
                }, function (m) {
                    $.data(document.body, "wn_editable", m);
                    return false;
                });
            }

            function checkGradeExist(wn_id) {
                $.sendRequest({
                    asynchronous: false,
                    action: "check_wn_grade_empty",
                    data: {id: wn_id}
                }, function (m) {
                    $.data(document.body, "gradeEmpty", m);
                    return false;
                });
            }

            function checkPackingExist(wn_id) {
                $.sendRequest({
                    asynchronous: false,
                    action: "check_wn_packing_empty",
                    data: {id: wn_id}
                }, function (m) {
                    $.data(document.body, "gradeEmpty", m);
                    return false;
                });
            }

            function reloadWeightNoteRefList(f) {
//    console.log("searchStr : " + $("#filter_ref").val());
                var json = {
                    type: $("#type_filter li.chosen").attr("id").split("_")[1],
                    status: $("#status_filter li.chosen").attr("id").split("_")[1],
                    instruction: $("#instruction_ref_filter li.chosen").attr("id").split("_")[1],
                    grade: $("#grade_filter li.chosen").attr("id").split("_")[1],
                    supplier: $("#supplier_filter li.chosen").attr("id").split("_")[1],
                    searchStr: $("#filter_ref").val(),
                    pledge: $("#pledge_filter li.chosen").attr("id").split("_")[1]
                };
                console.log(f);
                if (f == true) {  //when click on type
                    $.sendRequest({
                        target: "#process_list",
                        action: "get_wn_ref_list",
                        data: json,
                        status: "append_html"
                    }, function (msg) {
                        $("#wn_content").html("");
                        scrollLoading();
                        return true;
                    });
                } else if ($.isArray(f)) {
                    $.sendRequest({
                        target: "#process_list",
                        action: "get_wn_ref_list",
                        data: json,
                        status: "append_html",
                        func: f
                    }, function (msg) {
                        scrollLoading();
                        return true;
                    });
                    /*  sendJsonRequest("process_list", "get_wn_ref_list", json, 1, f);
                     applyFunction(f); */
                } else if ($.isFunction(f)) {
                    $.sendRequest({
                        target: "#process_list",
                        action: "get_wn_ref_list",
                        data: json,
                        status: "append_html"
                    }, function (msg) {
                        scrollLoading();
                        f();
                        return true;
                    });
                } else {
                    $.sendRequest({
                        target: "#process_list",
                        action: "get_wn_ref_list",
                        data: json,
                        status: "append_html"
                    }, function (msg) {
                        check_forward();
                        scrollLoading();
                        return true;
                    });
                    /* sendJsonRequest("process_list", "get_wn_ref_list", json, 1, [{"name": "check_forward", "param": ""}]); */
                }
            }
            function scrollLoading() {
                console.log("start scrolling");
                var param = '&type=' + $("#type_filter li.chosen").attr("id").split("_")[1]
                        + '&status=' + $("#status_filter li.chosen").attr("id").split("_")[1]
                        + '&instruction=' + $("#instruction_ref_filter li.chosen").attr("id").split("_")[1]
                        + '&grade=' + $("#grade_filter li.chosen").attr("id").split("_")[1]
                        + '&supplier=' + $("#supplier_filter li.chosen").attr("id").split("_")[1]
                        + '&searchStr=' + $("#filter_ref").val()
                        + '&pledge=' + $("#pledge_filter li.chosen").attr("id").split("_")[1];
                if ($("#process_list li").length !== row) {
                    row = $("#process_list li").length;
                    var link = "<a href=\"waypoint.htm?row=" + row + param + "\" class=\"waypoint\" style=\"display: none\"></a>";
                    $("#process_list").append(link);
                    $('#process_list').jscroll({
                        contentSelector: 'test',
                        nextSelector: 'a.waypoint',
                        contentSelector: 'li',
                                autoTrigger: true,
                        callback: function () {
                            
                        }
                    });
                }
            }

            function resetRow() {
                //reset jscroll - clear scroll dat
                $("#process_list").data("jscroll", {});
                $("#process_list").html("");
            }

            function loadByWnType(wn_type) {
                switch (wn_type) {
                    case "1":
                        $("#instruction_title").html("Delivery Instruction");
                        $("#head_supplier").html("Supplier");
                        $(".movHide").removeClass("hid");
                        if (!$(".pledge").hasClass("hid")) {
                            $(".pledge").addClass("hid");
                        }
                        break;
                    case "2":
                    case "3":
                        $("#instruction_title").html("Processing Instruction");
                        $("#head_supplier").html("Supplier");
                        $(".movHide").removeClass("hid");
                        if (!$(".pledge").hasClass("hid")) {
                            $(".pledge").addClass("hid");
                        }
                        break;
                    case "4":
                        $("#instruction_title").html("Shipping Instruction");
                        $("#head_supplier").html("Supplier");
                        $(".movHide").removeClass("hid");
                        if (!$(".pledge").hasClass("hid")) {
                            $(".pledge").addClass("hid");
                        }

                        break;
                }
            }
        </script>
    </body>
</html>


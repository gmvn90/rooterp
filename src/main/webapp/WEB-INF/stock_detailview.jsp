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
        <style>
            .dataTable {border-collapse: collapse}
            .dataTable tr th {border-collapse: collapse; border: 1px solid black;}
            .dataTable tr th {font-size: 13px;}
            .dataTable tr td {padding: 5px;}
            .dataTable tr td.formatNumber {text-align: right}
            .dataTable tr.data_row td {font-size: 11px; border-collapse: collapse; border: 1px solid black;}
            .dataTable thead {background: #EEEEEE}
            .dataTable tbody tr.data_row:hover{cursor: pointer; background: #e5eff8}
            .empty_col {border: none !important; background: white; color: white}
            .dataTable tr.footer {font-weight: bold}
        </style>
        <style>
            .dataTable1 {border-collapse: collapse}
            .dataTable1 tr th {border-collapse: collapse; border: 1px solid black;}
            .dataTable1 tr th {font-size: 13px;}
            .dataTable1 tr td {padding: 5px;}
            .dataTable1 tr td.formatNumber {text-align: right}
            .dataTable1 tr.data_row td {font-size: 11px; border-collapse: collapse; border: 1px solid black;}
            .dataTable1 thead {background: #EEEEEE}
            .dataTable1 tbody tr.data_row:hover{cursor: pointer; background: #e5eff8}
            .empty_col {border: none !important; background: white; color: white}
            .dataTable1 tr.footer {font-weight: bold}
            .dataTable1 tfoot {background: #EEEEEE}
        </style>
    </head>
    <body>
        <jsp:include page="header.jsp">
            <jsp:param name="url" value="9"/>
            <jsp:param name="page" value="2"/>
        </jsp:include>
        <form id="stock_form" method="POST">
            <div  style='width: 1800px !important; margin-left: 100px; display: -webkit-flex; text-align: center'>
                <div id="main_content" style='width: 1650px !important; display: -webkit-flex'>
                    <div style="width: 150px; height: 500px">
                        <input type="button" class="left" name="perm_42" id="create_new_map" value="Create new map"/><br/>
                        <div class="left" style="width: 130px; text-align: right; margin-top: 200px">
                            <table class="note_table">
                                <tbody>
                                    <tr>
                                        <td>Storage</td>
                                        <td class="storage"></td>
                                    </tr>
                                    <tr>
                                        <td>Room/Machine</td>
                                        <td class="room_machine"></td>
                                    </tr>
                                    <tr>
                                        <td>Out of use</td>
                                        <td class="out_of_use"></td>
                                    </tr>
                                    <tr>
                                        <td>Door</td>
                                        <td class="door"></td>
                                    </tr>
                                    <tr>
                                        <td>Wall</td>
                                        <td class="wall"></td>
                                    </tr>
                                </tbody>
                            </table>
                            <input type="button" name="perm_41" id="btn_edit" value="Edit"/>
                            <input type="button" name="perm_60" id="btn_delete" value="Delete"/>
                        </div>
                    </div>
                    <div style="width: 520px">
                        <div style="width: 520px; margin-top: 20px">
                            <table class="warehousemap" border="1">
                                <tbody id="drawcell">

                                </tbody>
                            </table>
                        </div>
                    </div>

                    <div class="right" style='width: 1000px'>
                        <div style="width: 100%; overflow: hidden; height: auto; border-bottom: 1px solid #B8C2CC; padding-bottom: 10px">
                            <table class="table_filter">
                                <thead>
                                    <tr style="font-size: 13px">
                                        <th width="190px">Map</th>
                                        <th width="190px">Client</th>
                                        <th width="190px">Pledge</th>
                                        <th width="190px">Grade</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <td width="190px">
                                            <input id="warehouse_filter_text" type="text" style="width: 190px;" class="text_filter_child">
                                            <ul id="warehouse_filter" class="filter">
                                            </ul>
                                        </td>
                                        <td width="190px">
                                            <input id="client_filter_text" type="text" style="width: 190px;" class="text_filter_child">
                                            <ul id="client_filter" class="filter">
                                            </ul>
                                        </td>
                                        <td width="190px">
                                            <input id="pledge_filter_text" type="text" style="width: 190px" class="text_filter_child">
                                            <ul id="pledge_filter" class="filter">
                                            </ul>
                                        </td>
                                        <td width="190px">
                                            <input id="grade_filter_text" type="text" style="width: 190px;" class="text_filter_child">
                                            <ul id="grade_filter" class="filter">
                                            </ul>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                        <div id="wnrlist_area" style="width: 100%;overflow: hidden; height: auto; border-bottom: 1px solid #B8C2CC; padding-bottom: 10px; display: none">
                            <h3>List weight note receipts</h3>
                            <label id="area_label" class="left"></label>
                            <table id="available_wnr">
                                <thead>
                                    <tr>
                                        <th></th>
                                        <th>WNR Reference</th>
                                        <th>Net weight</th>
                                        <th>Grade</th>
                                        <th>Pledge</th>
                                        <th>Client</th>
                                        <th></th>
                                    </tr>
                                </thead>
                                <tbody>
                                </tbody>
                            </table>
                        </div>

                    </div>
                </div>
            </div>
        </div>
        <input type="hidden" id="ss_id" name="ss_id" value=""/>
        <input type="hidden" id="id_cell_view" name="id_cell_view" value=""/>
    </form>
    <jsp:include page="footer.jsp"/>
    <script>
        var wnr_table = null;
        var list_selected = [];
        $(document).ready(function() {
            //loadFilterList();
            loadClientFilter();
            loadPledgeFilter();
            loadGradeFilter();
            loadWarehouseFilter();
        }).on("click", "#reweight_all", function() {
            var stt = $(this).prop("checked");
            $("input.reweight_row").each(function() {
                $(this).prop("checked", stt);
            });
        }).on("click", ".choose_cell", function() {
            $("#areaCode").val($("#map_select option:selected").text() + "-" + $(this).parent().parent("td").attr("ordinate"));
            $("#area").val($(this).parent().parent("td").attr("id"));
            $.closeDialog("#message_box");
        }).on("click", "#warehouse_filter li", function() {

            var map_id = $(this).attr("id").split("_")[1];
            $.sendRequest({
                action: "reloadGradeAndClientFilterList",
                data: {
                    map_id: map_id
                }
            }, function(msg) {
                try {
                    var data = JSON.parse(msg);
                    var grade = data.grade;
                    var client = data.client;

                    var clientTmpl = $.templates("<li id='clientFilter_{{:id}}' class='{{:class}}'>{{:name}}</li>");
                    var gradeTmpl = $.templates("<li id='gradeFilter_{{:id}}' class='{{:class}}'>{{:name}}</li>");

                    $("#grade_filter").html(gradeTmpl.render(grade));
                    $("#client_filter").html(clientTmpl.render(client));

                    bindMapData(map_id);
                    return true;
                } catch (e) {
                    $.error(e);
                }
            });
        }).on("click", "#client_filter li", function() {
            var map_id = $("#warehouse_filter li.chosen").attr("id").split("_")[1];
            $("#available_wnr tbody").html("");
            $.sendRequest({
                action: "reloadGradeFilterList",
                data: {
                    map_id: map_id,
                    client_id: $(this).attr("id").split("_")[1]
                }
            }, function(msg) {
                try {
                    var data = JSON.parse(msg);
                    var grade = data.grade;

                    var gradeTmpl = $.templates("<li id='gradeFilter_{{:id}}' class='{{:class}}'>{{:name}}</li>");

                    $("#grade_filter").html(gradeTmpl.render(grade));


                } catch (e) {
                    $.error(e);
                }
                bindMapData(map_id);
                return true;
            });

        }).on("click", "#pledge_filter li", function() {
            $("#wnrlist_area").hide();
            var map_id = $("#warehouse_filter li.chosen").attr("id").split("_")[1];
            bindMapData(map_id);
            $("#overlay").remove();
        }).on("click", "#grade_filter li", function() {
            $("#wnrlist_area").hide();
            var map_id = $("#warehouse_filter li.chosen").attr("id").split("_")[1];
            bindMapData(map_id);
            $("#overlay").remove();
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
            var wh_id = $("#warehouse_filter li.chosen").attr("id").split("_")[1];
            if (wh_id !== "-1") {
                var r = confirm("Delete warehouse map " + $("#warehouse_filter li.chosen").html() + "?");
                if (r == true) {
                    //get map id
                    var map_id = $("#warehouse_filter li.chosen").attr("id").split("_")[1];
                    sendJsonRequest("", "delete_warehouse_map", {"map_id": map_id}, 5, [
                        {name: "afterDeletingWarehouseMap", params: []}
                    ], true, ["delete_response"]);

                    $.sendRequest({
                        action: "delete_warehouse_map",
                        data: {
                            "map_id": map_id
                        }
                    }, function(msg) {
                        if (msg === "1") {
                            $("#drawcell").html("");
                            loadFilterList();
                        } else if (msg === "2") {
                            $.error("Cannot delete map which is containing Weight Note Receipt!");
                        }
                    })
                } else {
                }
            }
        }).on("click", "#btn_save", function() {
            $("#map_id").val(flag_map_id);
            $("#strJSON").val(JSON.stringify(cellsChangesJson));
            sendRequestHtmlWithoutAuth("stock_form", "drawcell", "apply_cell_changes", [{'name': 'closeOpacity', 'params': []}]);
        }).on("click", "table.warehousemap td", function() {
            $("#wnrlist_area").show();
            $("#area_label").html("Area: " + $(this).attr("ordinate") + "-" + $("#warehouse_filter li.chosen").html());
            if ($(this).hasClass("storage")) {
                $("#id_cell_view").val($(this).attr("id"));
                if (wnr_table === null) {

                    wnr_table = $("#available_wnr").dataTable({
                        "bAutoWidth": false,
                        "aLengthMenu": [[10, 20, 60], [10, 20, 60]],
                        "bProcessing": true,
                        "bServerSide": true,
                        "sAjaxSource": "wnr_list_source.htm",
                        "fnServerParams": function(aoData) {
                            aoData.push(
                                    {"name": "grade", "value": $("#grade_filter li.chosen").attr("id").split("_")[1]},
                            {"name": "cli", "value": $("#client_filter li.chosen").attr("id").split("_")[1]},
                            {"name": "ple", "value": $("#pledge_filter li.chosen").attr("id").split("_")[1]},
                            {"name": "area", "value": $("#id_cell_view").val()}
                            );
                        },
                        "aoColumnDefs": [
                            {"sClass": "formatNumber", "aTargets": [2]},
                            {"sWidth": "150px", "aTargets": [4, 5]},
                            {"sWidth": "150px", "aTargets": [1]},
                            {"sWidth": "300px", "aTargets": [3]},
                            {"sWidth": "70px", "aTargets": [2]},
                            {"sWidth": "30px", "aTargets": [0]},
                            {"sWidth": "100px", "aTargets": [6]},
                            { "bVisible": false, "aTargets": [ 6 ] }
                        ],
                        "fnDrawCallback": function(oSettings, json) {
                            $("input[type=button]").button();
                            $("td.formatNumber").each(function() {
                                var vl = accounting.toFixed(parseFloat($(this).text()), 3);
                                $(this).text(accounting.formatMoney(vl, "", 3));
                            });
                        },
                        "sPaginationType": "full_numbers",
                        "sDom": 'Rlfrtip',
                        "oColVis": {
                            "fnLabel": function(index, title, th) {
                                if (title != "") {
                                    return (index + 1) + '. ' + title;
                                }
                            }
                        }
                    });
                } else {
                    doDataTableFilter(wnr_table);
                }

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
            sendJsonRequest("drawcell", "create_map_and_cell", {
                "name": $("#new_map_name").val(),
                "col": $("#col_input").val(),
                "row": $("#row_input").val(),
                "wall_hor": $("#wallpoint_hor_input").val(),
                "wall_ver": $("#wallpoint_ver_input").val()}, 0,
                    [
                        {"name": "loadFilterList", "params": []},
                        {'name': 'closeOpacity', 'params': []}
                    ]);
        });

        function changeitem() {
            $("#session").val($('#session option:last-child').val());
            $("#ss_id").val($('#session option:last-child').val());
        }

        function afterDeletingWarehouseMap() {
            if ($.data(document.body, "delete_response") === "1") {
                $("#drawcell").html("");
                $("#session").html("");
                getSelection("stock_form", "session", "selection_warehouse_map", -1);
                $.success("Delete successfully!");
            } else if ($.data(document.body, "delete_response") === "2") {
                $.error("Cannot delete map which is containing Weight Note Receipt!");
            }
        }

        function loadClientFilter() {
            $("#client_filter").html("<li id='clientFilter_-1' class='chosen'>All</li><li>Loading...</li>");
            $.sendRequest({
                action: "loadFilterStock",
                data: {type: "client"}
            }, function(msg) {
                try {
                    var data = JSON.parse(msg);
                    var clientTmpl = $.templates("<li id='clientFilter_{{:id}}' class='{{:class}}'>{{:name}}</li>");
                    $("#client_filter").html(clientTmpl.render(data));
                    return true;
                } catch (e) {
                    console.error(e);
                    $.error(e);
                }
            });
        }
        
        function loadPledgeFilter() {
            $("#pledge_filter").html("<li id='pledgeFilter_-1' class='chosen'>All</li><li>Loading...</li>");
            $.sendRequest({
                action: "loadFilterStock",
                data: {type: "pledge"}
            }, function(msg) {
                try {
                    var data = JSON.parse(msg);
                    var pledgeTmpl = $.templates("<li id='pledgeFilter_{{:id}}' class='{{:class}}'>{{:name}}</li>");
                    $("#pledge_filter").html(pledgeTmpl.render(data));
                    return true;
                } catch (e) {
                    console.error(e);
                    $.error(e);
                }
            });
        }
        
        function loadGradeFilter() {
            $("#grade_filter").html("<li id='gradeFilter_-1' class='chosen'>All</li><li>Loading...</li>");
            $.sendRequest({
                action: "loadFilterStock",
                data: {type: "grade"}
            }, function(msg) {
                try {
                    var data = JSON.parse(msg);
                    var gradeTmpl = $.templates("<li id='gradeFilter_{{:id}}' class='{{:class}}'>{{:name}}</li>");
                    $("#grade_filter").html(gradeTmpl.render(data));
                    return true;
                } catch (e) {
                    console.error(e);
                    $.error(e);
                }
            });
        }
        
        function loadWarehouseFilter() {
            $("#warehouse_filter").html("<li id='warehouseFilter_-1' class='chosen'>All</li><li>Loading...</li>");
            $.sendRequest({
                action: "loadFilterStock",
                data: {type: "warehouse"}
            }, function(msg) {
                try {
                    var data = JSON.parse(msg);
                    var warehouseTmpl = $.templates("<li id='warehouseFilter_{{:id}}' class='{{:class}}'>{{:name}}</li>");
                    $("#warehouse_filter").html(warehouseTmpl.render(data));
                    return true;
                } catch (e) {
                    console.error(e);
                    $.error(e);
                }
            });
        }

        function loadFilterList() {
            $.sendRequest({
                action: "loadFilterStockList"
            }, function(msg) {
                try {
                    var data = JSON.parse(msg);
                    var grade = data.grade;
                    var client = data.client;
                    var pledge = data.pledge;
                    var warehouse = data.warehouse;

                    var warehouseTmpl = $.templates("<li id='warehouseFilter_{{:id}}' class='{{:class}}'>{{:name}}</li>");
                    var pledgeTmpl = $.templates("<li id='pledgeFilter_{{:id}}' class='{{:class}}'>{{:name}}</li>");
                    var clientTmpl = $.templates("<li id='clientFilter_{{:id}}' class='{{:class}}'>{{:name}}</li>");
                    var gradeTmpl = $.templates("<li id='gradeFilter_{{:id}}' class='{{:class}}'>{{:name}}</li>");

                    $("#client_filter").html(clientTmpl.render(client));
                    $("#pledge_filter").html(pledgeTmpl.render(pledge));
                    $("#grade_filter").html(gradeTmpl.render(grade));
                    $("#warehouse_filter").html(warehouseTmpl.render(warehouse));
                    return true;
                } catch (e) {
                    $.error(e);
                }
            });
        }

        function bindMapData(map_id) {
            $("#wnrlist_area").hide();
            $("#movement_area").hide();
            list_selected.length = 0;
            $("#selected_wnr_body").html("");
            $("#update_client").val(-1);
            $("#update_pledge").val(-1);
            $("#areaCode").val("");
            $("#area").val(0);
            $("#drawcell").html("loading...");
            if (map_id !== "-1") {
                $.sendRequest({
                    target: "#drawcell",
                    action: "bind_map_data_detail",
                    data: {
                        ss_id: map_id,
                        client: $("#client_filter li.chosen").attr("id").split("_")[1],
                        pledge: $("#pledge_filter li.chosen").attr("id").split("_")[1],
                        grade: $("#grade_filter li.chosen").attr("id").split("_")[1]
                    },
                    status: "replace_html"
                }, function() {

                });
            } else {
                $("#drawcell").html("");
            }
        }

    </script>
</body>
</html>

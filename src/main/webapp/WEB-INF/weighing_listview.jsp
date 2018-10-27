<%-- 
    Document   : delivery_listview
    Created on : Aug 5, 2013, 2:45:38 PM
    Author     : duhc
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Mill System</title>
        <style>
            #btn_add_new,#btn_delete {min-width: 50px !important}
        </style>
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
    </head>
    <body>
        <jsp:include page="header.jsp">
            <jsp:param name="url" value="14"/>
            <jsp:param name="page" value="16"/>
        </jsp:include>
        <form id="weighingDetail_form" action="forward_instruction.htm" method="POST">
            <div style="width: 1290px ! important; padding: 15px; background: none repeat scroll 0% 0% white; height: auto; overflow: hidden; min-height: 400px" class="container_16 border" id="wrapper">
                <div style="width: 100%; overflow: hidden; height: auto; border-bottom: 1px solid #B8C2CC; padding-bottom: 10px">
                    <table class="table_filter">
                        <thead>
                            <tr style="font-size: 13px">
                                <th width="100px">Type</th>
                                <th width="190px">Instruction</th>
                                <th width="190px">Status</th>
                                <th width="190px">Grade</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td width="100px">
                                    <input id="type_filtertext" type="text" class="text_filter_child">
                                    <ul id="type_filter" class="filter" name="perm_40">
                                        <li id="type_IM" class="chosen">Import</li>
                                        <li id="type_IP">In-Process</li>
                                        <li id="type_XP">Ex-Process</li>
                                        <li id="type_EX">Export</li>
                                    </ul>
                                </td>
                                <td width="100px">
                                    <input id="instruction_filtertext" type="text" class="text_filter_child">
                                    <ul id="instruction_filter" class="filter">
                                        <li id="instruction_-1" class="chosen">All</li>
                                    </ul>
                                </td>
                                <td width="100px">
                                    <input id="stt_filtertext" type="text" class="text_filter_child">
                                    <ul id="stt_filter" class="filter">
                                        <li id="stt_-1" class="chosen">All</li>
                                        <li id="stt_0">Pending</li>
                                        <li id="sstt_1">Completed</li>
                                    </ul>
                                </td>
                                <td width="190px">
                                    <input id="grade_filtertext" type="text" style="width: 190px;" class="text_filter_child">
                                    <ul id="grade_filter" class="filter">
                                        <li id="grade_-1" class="chosen">All</li>
                                    </ul>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <div style="width: 100%;overflow: hidden; height: auto; border-bottom: 1px solid #B8C2CC; padding-bottom: 10px">
                    <h3>Weighing List</h3>
                    <table id="available_weighing">
                        <thead>
                            <tr>
                                <th></th>
                                <th>WN Ref</th>
                                <th>QR Ref</th>
                                <th>Inst Ref</th>
                                <th>Grade</th>
                                <th>WN Date</th>
                                <th>Packing</th>
                                <th>Num</th>
                                <th>Gross</th>
                                <th>Tare</th>
                                <th>Net</th>
                            </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
                </div>
                <input class="button" id="card_view" type="button" value="Card View" name="perm_7" style="margin-top: 10px"/>
            </div>
            <input type="hidden" id="fw_wntype" name="fw_wntype" value=""/>
            <input type="hidden" id="fw_type" name="fw_type" value=""/>
            <input type="hidden" id="fw_id" name="fw_id" value=""/>
            <input type="hidden" id="fw_instref" name="fw_instref" value=""/>
        </form>
        <jsp:include page="footer.jsp"/>
        <script>
            var weighing_table;
            $(document).ready(function() {
                //$("#type_filter").auth({action:"click"})
                //$("#type_filter li.chosen").attr("id").split("_")[1];
                sendJsonRequest("instruction_filter", "load_inst_by_type", {"type": $("#type_filter li.chosen").attr("id").split("_")[1]}, 0, [
                    {"name": "scrollElTop", "params": ["#instruction_filter", "li.chosen"]},
                    {"name": "sendJsonRequest", "params": ["grade_filter", "wn_get_grade_filter", {}, 1, [
                                {"name": "scrollElTop", "params": ["#grade_filter", "li.chosen"]}
                            ], false]}
                ], false);
                weighing_table = $("#available_weighing").dataTable({
                    "bAutoWidth": false,
                    "aLengthMenu": [[10, 25, 50], [10, 25, 50]],
                    "bProcessing": true,
                    "bServerSide": true,
                    "sAjaxSource": "weighing_list_source.htm",
                    "fnServerParams": function(aoData) {
                        aoData.push(
                                {"name": "grade", "value": $("#grade_filter li.chosen").attr("id").split("_")[1]},
                        {"name": "type", "value": $("#type_filter li.chosen").attr("id").split("_")[1]},
                        {"name": "inst_id", "value": $("#instruction_filter li.chosen").attr("id").split("_")[1]},
                        {"name": "stt", "value": $("#stt_filter li.chosen").attr("id").split("_")[1]}
                        );
                    },
                    "aoColumnDefs": [
                        {"sClass": "formatNumber", "aTargets": [8, 9, 10]},
                        {"sWidth": "120px", "aTargets": [1, 3, 6]},
                        {"sWidth": "80px", "aTargets": [5, 7, 9, 10]},
                        {"sWidth": "100px", "aTargets": [2]},
                        {"sWidth": "400px", "aTargets": [4]}
                    ],
                    "fnDrawCallback": function(oSettings, json) {
                        //alert("drawback");
                        $("input[type=button]").button();
                        $("td.formatNumber").each(function() {
                            var vl = accounting.toFixed(parseFloat($(this).text()), 3);
                            $(this).text(accounting.formatMoney(vl, "", 3));
                        });
                        $("tbody tr[class*=data_row]").each(function() {
                            $(this).attr('name', 'perm_7');
                            $(this).attr("title", "Double Click To View Detail");
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
            });
            $(document).on("click", "#grade_filter li", function() {
                doDataTableFilter(weighing_table);
            }).on("click", "#instruction_filter li", function() {
                doDataTableFilter(weighing_table);
            }).on("click", "#stt_filter li", function() {
                doDataTableFilter(weighing_table);
            }).on("click", "#type_filter li", function() {
                $("#stt_filter li.chosen").toggleClass("chosen");
                $("#stt_-1").addClass("chosen");
                $("#grade_filter li.chosen").toggleClass("chosen");
                $("#grade_-1").addClass("chosen");
                sendJsonRequest("instruction_filter", "load_inst_by_type", {"type": $("#type_filter li.chosen").attr("id").split("_")[1]}, 0, [
                    {name: "sendJsonRequest", params: ["grade_filter", "wn_get_grade_by_type",
                            {type: $("#type_filter li.chosen").attr("id").split("_")[1]}
                            , 0]}
                ], false);
                doDataTableFilter(weighing_table);
            }).on("dblclick", ".data_row", function() {
                $("#fw_type").val("weighing");
                $("#fw_id").val($(this).attr("id").split("_")[1]);
                $("#fw_wntype").val($("#type_filter li.chosen").attr("id").split("_")[1]);
                $("#fw_instref").val($(this).children().first().next().next().next().html());
                $("#weighingDetail_form").attr("action","weighing/detail.htm");
                $("#weighingDetail_form").submit();
            }).on("click", "#card_view", function() {
                if (!$(this).hasClass("disabled")) {
                    $("#fw_type").val("weighing");
                    $("#fw_id").val("");
                    $("#fw_wntype").val("");
                    $("#weighingDetail_form").attr("action", "weighing/detail.htm");
                    $("#weighingDetail_form").submit();
                }
            });
        </script>
    </body>
</html>

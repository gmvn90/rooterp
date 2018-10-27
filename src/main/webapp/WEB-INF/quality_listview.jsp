<%-- 
    Document   : delivery_listview
    Created on : Aug 5, 2013, 2:45:38 PM
    Author     : minhdn
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
            <jsp:param name="url" value="18"/>
            <jsp:param name="page" value="15"/>
        </jsp:include>
        <form id="qualityDetail_form" action="forward_instruction.htm" method="POST">
            <div style="width: 1500px ! important; padding: 15px; background: none repeat scroll 0% 0% white; height: auto; overflow: hidden; min-height: 400px" class="container_16 border" id="wrapper">
                <div style="width: 100%; overflow: hidden; height: auto; border-bottom: 1px solid #B8C2CC; padding-bottom: 10px">
                    <table class="table_filter">
                        <thead>
                            <tr style="font-size: 13px">
                                <th width="100px">Type</th>
                                <th width="190px">Status</th>
                                <th width="190px" id="sup_cli">Supplier</th>
                                <th width="190px">Buyer</th>
                                <th width="190px">Grade</th>
                                <th width="190px">QR Date</th>
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
                                        <li id="type_WR">Whse Receipt</li>
                                        <li id="type_WC">Weight Cert.</li>
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
                                    <input id="sup_filtertext" type="text" style="width: 190px" class="text_filter_child">
                                    <ul id="sup_filter" class="filter">
                                        <li id="sup_-1" class="chosen">All</li>
                                    </ul>
                                </td>
                                <td width="190px">
                                    <input id="buyer_filtertext" type="text" style="width: 190px" class="text_filter_child">
                                    <ul id="buyer_filter" class="filter">
                                        <li id="buyer_-1" class="chosen">All</li>
                                    </ul>
                                </td>
                                <td width="190px">
                                    <input id="grade_filtertext" type="text" style="width: 190px;" class="text_filter_child">
                                    <ul id="grade_filter" class="filter">
                                        <li id="grade_-1" class="chosen">All</li>
                                    </ul>
                                </td>
                                <td width="190px">
                                    <table style="border: 1px solid white">
                                        <tbody>
                                            <tr>
                                                <td style="border: 1px solid white"><span style="font-size: 12px">From:</span></td>
                                                <td style="border: 1px solid white"><input type="text" id="from_date_filter" class="date_picker_text" readonly="true" val="" style="width: 100px"/><a href="javascript:void(0);" class="clear_date_filter">Reset</a></td>
                                            </tr>
                                            <tr>
                                                <td style="border: 1px solid white"><span style="font-size: 12px">To:</span></td>
                                                <td style="border: 1px solid white"><input type="text" id="to_date_filter" class="date_picker_text" readonly="true" val="" style="width: 100px"/><a href="javascript:void(0);" class="clear_date_filter">Reset</a></td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </td>
                            </tr>
                        </tbody>
                    </table><input class="button" id="export2excel" type="button" value="Export" name="" style="margin-top: 10px"/>
                </div>
                <div style="width: 100%;overflow: hidden; height: auto; border-bottom: 1px solid #B8C2CC; padding-bottom: 10px">
                    <h3>Quality Report List</h3>
                    <table id="available_quality">
                        <thead>
                            <tr>
                                <th></th>
                                <th>QR Ref</th>
                                <th>WN Ref</th>
                                <th id="sup_cli2">Supplier</th>
                                <th>Grade</th>
                                <th>QR Date</th>
                                <th>Black</th>
                                <th>Brown</th>
                                <th>FM</th>
                                <th>Broken</th>
                                <th>Moist</th>
                                <th>O.Crop</th>
                                <th>Moldy</th>
                                <th>20+</th>
                                <th>20</th>
                                <th>19</th>
                                <th>18</th>
                                <th>17</th>
                                <th>16</th>
                                <th>15</th>
                                <th>14</th>
                                <th>13</th>
                                <th>12</th>
                                <th>12-</th>
                            </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
                </div>
                <input class="button" id="card_view" type="button" value="Card View" name="perm_9" style="margin-top: 10px"/>
            </div>
            <input type="hidden" id="fw_qrtype" name="fw_qrtype" value=""/>
            <input type="hidden" id="fw_type" name="fw_type" value=""/>
            <input type="hidden" id="fw_id" name="fw_id" value=""/>
        </form>
        <jsp:include page="footer.jsp"/>
        <script>
            var quality_table;
            $(document).ready(function () {
                sendJsonRequest("sup_filter", "qr_get_sup_filter", {"type": $("#type_filter li.chosen").attr("id").split("_")[1]}, 1, [
                    {"name": "scrollElTop", "params": ["#sup_filter", "li.chosen"]},
                    {"name": "sendJsonRequest", "params": ["buyer_filter", "qr_get_buyer_filter", {"type": $("#type_filter li.chosen").attr("id").split("_")[1]}, 1, [
                        {"name": "scrollElTop", "params": ["#buyer_filter", "li.chosen"]}
                    ], false]},
                    {"name": "sendJsonRequest", "params": ["grade_filter", "qr_get_grade_filter", {"type": $("#type_filter li.chosen").attr("id").split("_")[1]}, 1, [
                                {"name": "scrollElTop", "params": ["#grade_filter", "li.chosen"]}
                            ], false]}
                ], false);
                quality_table = $("#available_quality").dataTable({
                    "bAutoWidth": false,
                    "aLengthMenu": [[10, 25, 50], [10, 25, 50]],
                    "bProcessing": true,
                    "bServerSide": true,
                    "sAjaxSource": "quality_list_source.htm",
                    "fnServerParams": function (aoData) {
                        aoData.push(
                                {"name": "grade", "value": $("#grade_filter li.chosen").attr("id").split("_")[1]},
                        {"name": "type", "value": $("#type_filter li.chosen").attr("id").split("_")[1]},
                        {"name": "sup", "value": $("#sup_filter li.chosen").attr("id").split("_")[1]},
                        {"name": "buyer", "value": $("#buyer_filter li.chosen").attr("id").split("_")[1]},
                        {"name": "stt", "value": $("#stt_filter li.chosen").attr("id").split("_")[1]},
                        {"name": "from_date", "value": $("#from_date_filter").val()},
                        {"name": "to_date", "value": $("#to_date_filter").val()}
                        );
                    },
                    "aoColumnDefs": [
                        {"sWidth": "120px", "aTargets": [1, 3]},
                        {"sWidth": "80px", "aTargets": [5]},
                        {"sWidth": "100px", "aTargets": [2]},
                        {"sWidth": "350px", "aTargets": [4]},
                        {"sWidth": "35px", "aTargets": [6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23]}
                    ],
                    "fnDrawCallback": function (oSettings, json) {
                        //alert("drawback");
                        $("input[type=button]").button();
                        $("td.formatNumber").each(function () {
//                            var vl = accounting.toFixed(parseFloat($(this).text()), 3);
//                            $(this).text(accounting.formatMoney(vl, "",3));
                        });
                        $("tbody tr[class*=data_row]").each(function () {
                            $(this).attr('name', 'perm_9');
                            $(this).attr("title", "Double Click To View Detail");
                        });
                        //show tool tip
                        $('.masterTooltip').hover(function () {
                            // Hover over code
                            var title = $(this).attr('title');
                            $(this).data('tipText', title).removeAttr('title');
                            $('<p class="tooltip"></p>')
                                    .text(title)
                                    .appendTo('body')
                                    .fadeIn('slow');
                        }, function () {
                            // Hover out code
                            $(this).attr('title', $(this).data('tipText'));
                            $('.tooltip').remove();
                        }).mousemove(function (e) {
                            var mousex = e.pageX + 20; //Get X coordinates
                            var mousey = e.pageY + 10; //Get Y coordinates
                            $('.tooltip')
                                    .css({
                                        top: mousey,
                                        left: mousex
                                    })
                        });
                    },
                    "sPaginationType": "full_numbers",
                    "sDom": 'Rlfrtip',
                    "oColVis": {
                        "fnLabel": function (index, title, th) {
                            if (title != "") {
                                return (index + 1) + '. ' + title;
                            }
                        }
                    }
                });
            });
            $(document).on("click", "#grade_filter li", function () {
                doDataTableFilter(quality_table);
            }).on("click", "#sup_filter li", function () {
                doDataTableFilter(quality_table);
            }).on("click", "#buyer_filter li", function () {
                doDataTableFilter(quality_table);
            }).on("click", "#stt_filter li", function () {
                doDataTableFilter(quality_table);
            }).on("change", "#from_date_filter", function () {
                doDataTableFilter(quality_table);
            }).on("change", "#to_date_filter", function () {
                doDataTableFilter(quality_table);
            }).on("click", ".clear_date_filter", function () {
                $(this).siblings("input").val("");
                doDataTableFilter(quality_table);
            }).on("click", "#type_filter li", function () {
                $("#stt_filter li.chosen").toggleClass("chosen");
                $("#stt_-1").addClass("chosen");
                $("#sup_filter").html("<li id='sup_-1' class='chosen'>All</li>");
                $("#grade_filter").html("<li id='grade_-1' class='chosen'>All</li>");
                var type_check = $("#type_filter li.chosen").attr("id").split("_")[1];
                if (type_check === "IM" || type_check === "WR") {
                    $("#sup_cli").html("Supplier");
                    $("#sup_cli2").html("Supplier");
                } else {
                    $("#sup_cli").html("Client");
                    $("#sup_cli2").html("Client");
                }
                sendJsonRequest("sup_filter", "qr_get_sup_filter", {"type": $("#type_filter li.chosen").attr("id").split("_")[1]}, 1, [
                    {"name": "scrollElTop", "params": ["#sup_filter", "li.chosen"]},
                    {"name": "sendJsonRequest", "params": ["grade_filter", "qr_get_grade_filter", {"type": $("#type_filter li.chosen").attr("id").split("_")[1]}, 1, [
                                {"name": "scrollElTop", "params": ["#grade_filter", "li.chosen"]},
                                {"name": "doDataTableFilter", "params": [quality_table]}
                            ], false]}
                ], false);
                //doDataTableFilter(quality_table);
            }).on("dblclick", ".data_row", function () {
                $("#fw_type").val("qr");
                $("#fw_id").val($(this).attr("id").split("_")[1]);
                $("#fw_qrtype").val($("#type_filter li.chosen").attr("id").split("_")[1]);
                $("#qualityDetail_form").attr("action", "quality_report/detail.htm");
                $("#qualityDetail_form").submit();
            }).on("click", "#card_view", function () {
                $("#fw_type").val("qr");
                $("#fw_id").val("");
                $("#fw_qrtype").val("");
                $("#qualityDetail_form").attr("action", "quality_report/detail.htm");
                $("#qualityDetail_form").submit();
            }).on("click", "#export2excel", function () {
                var from = $("#from_date_filter").val();
                var to = $("#to_date_filter").val();
                if (from === "" || to === "") {
                    alert("Please input from and to date!!");
                } else {
                    var data = {
                        grade: $("#grade_filter li.chosen").attr("id").split("_")[1],
                        type: $("#type_filter li.chosen").attr("id").split("_")[1],
                        sup: $("#sup_filter li.chosen").attr("id").split("_")[1],
                        buyer: $("#buyer_filter li.chosen").attr("id").split("_")[1],
                        stt: $("#stt_filter li.chosen").attr("id").split("_")[1],
                        from_date: $("#from_date_filter").val(),
                        to_date: $("#to_date_filter").val()
                    };
                    $.sendRequest({
                        action: "exportQualityList",
                        status: "export",
                        data: data
                    });
                }

            });
        </script>
    </body>
</html>

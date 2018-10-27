<%-- 
    Document   : quality_report
    Created on : Jul 17, 2013, 9:38:37 AM
    Author     : minhdn
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <style>
            a.mpcth-icon-plus-squared, a.mpcth-icon-minus-squared {
                text-decoration: none;
                color: #599FCF;
                font-size: 18px;
            }
        </style>
        <title></title>
    </head>
    <body>
        <jsp:include page="header.jsp">
            <jsp:param name="url" value="18"/>
            <jsp:param name="page" value="9"/>
        </jsp:include>
        <input type="hidden" id="initial_id" value="${initial_id}"/>
        <input type="hidden" id="initial_type" value="${initial_type}"/>
        <c:set var="initial_id" value="${initial_id}"></c:set>
        <c:set var="initial_type" value="${initial_type}"></c:set>
            <form id="quality_form" method="POST">
                <div style="width: 1320px ! important; padding: 15px; background: none repeat scroll 0% 0% white; height: auto; overflow: hidden" class="container_16 border" id="wrapper">
                    <div style="width: 190px;padding-bottom: 10px; height: 450px" class="left border">
                        <div class="title center">
                            QR Type
                        </div>
                        <div class="pc100">
                            <ul  class="ref_list" id="type_filter" style="margin-left: 5px; padding-left: 10px; width: 158px !important; height: 407px" name="perm_40">
                                <li id="qrType_IM">Import</li>
                                <li id="qrType_IP">In-Process</li>
                                <li id="qrType_XP">Ex-Process</li>
                                <li id="qrType_EX">Export</li>
                                <li id="qrType_WR">Whse Receipt</li>
                                <li id="qrType_WC">Weight Cert.</li>
                            </ul>
                        </div>
                    </div>
                    <div style="width: 220px;padding-bottom: 10px; margin-left: 20px; height: 450px" class="left border">
                        <div class="title center">
                            QR Ref
                        </div>
                        <div class="pc100">
                            <input id="type_filtertext" type="text" class="text_filter_child" style="width: 210px; margin-left: 5px">
                            <ul class="filter ref_list" id="qrRefList" style="margin-left: 5px; padding-left: 10px; width: 188px !important; height: 384px" >

                            </ul>
                        </div>
                    </div>
                    <div style="width: 870px !important; margin-right: 0px !important;font-size: 9pt !important" class="right grid_14 border">
                        <div class="title center">
                            Detail
                        </div>
                        <div id="qr_content" style="min-height: 500px">
                            <div style="width: 850px !important; min-height: 80px; height: auto; overflow: hidden;margin-right: 9px; border-top: 1px solid gray" class="center">
                                <div class="left" style="width: 425px;">
                                    <table class="detail_info_table" style="margin-left: 60px">
                                        <tr>
                                            <td class="" align="right">QR Ref</td>
                                            <td class="fontBolder" align="left"></td>
                                        </tr>
                                        <tr>
                                            <td class=""  align="right">Date</td>
                                            <td class="fontBolder" align="left"></td>
                                        </tr>
                                        <tr>
                                            <td class="" align="right">Supplier</td>
                                            <td class="fontBolder" align="left"></td>
                                        </tr>
                                        <tr>
                                            <td class="" align="right">Supplier Ref</td>
                                            <td class="fontBolder" align="left"></td>
                                        </tr>
                                        <tr>
                                            <td class="" align="right">Truck No</td>
                                            <td class="fontBolder" align="left"></td>
                                        </tr>
                                        <tr>
                                            <td class="" align="right">Container No</td>
                                            <td class="fontBolder" align="left"></td>
                                        </tr>
                                    </table>
                                </div>
                                <div class="right" style="width: 425px;">
                                    <table class="detail_info_table" style="margin-left: 60px">
                                        <tr>
                                            <td class="" align="right">WN Ref</td>
                                            <td class="fontBolder" align="left"></td>
                                        </tr>
                                        <tr>
                                            <td class=""  align="right">Origin</td>
                                            <td class="fontBolder" align="left"></td>
                                        </tr>
                                        <tr>
                                            <td class="" align="right">Quality</td>
                                            <td class="fontBolder" align="left"></td>
                                        </tr>
                                        <tr>
                                            <td class="" align="right">Grade</td>
                                            <td class="fontBolder" align="left"></td>
                                        </tr>
                                    </table>
                                </div>
                            </div>
                            <div style="width: 850px !important; min-height: 300px; height: auto; overflow: hidden;margin-right: 9px; border-top: 1px solid gray" class="center">
                                <div class="left" style="width: 425px;">
                                    <table class="detail_info_table" style="margin-left: 60px">
                                        <tr>
                                            <td class="" align="right">Black (%)</td>
                                            <td class="" align="left"><input type="text" class="numberOnly" name="black" id="black" value="" style="width: 40%"/></td>
                                        </tr>
                                        <tr>
                                            <td class="" align="right">Brown (%)</td>
                                            <td class="" align="left"><input type="text" class="numberOnly" name="brown" id="brown" value="" style="width: 40%"/></td>
                                        </tr>
                                        <tr>
                                            <td class="" align="right">Broken (%)</td>
                                            <td class="" align="left"><input type="text" class="numberOnly" name="broken" id="broken" value="" style="width: 40%"/></td>
                                        </tr>
                                        <tr>
                                            <td class="" align="right">FM (%)</td>
                                            <td class="" align="left"><input type="text" class="numberOnly" name="fm" id="fm" value="" style="width: 40%"/></td>
                                        </tr>
                                        <tr>
                                            <td class="" align="right">Moisture (%)</td>
                                            <td class="" align="left"><input type="text" class="numberOnly" name="moisture" id="moisture" value="" style="width: 40%"/></td>
                                        </tr>
                                        <tr>
                                            <td class="" align="right">Moldy (%)</td>
                                            <td class="" align="left"><input type="text" class="numberOnly" name="moldy" id="moldy" value="" style="width: 40%"/></td>
                                        </tr>
                                        <tr>
                                            <td class="" align="right">Old Crop (%)</td>
                                            <td class="" align="left"><input type="text" class="numberOnly" name="old_crop" id="old_crop" value="" style="width: 40%"/></td>
                                        </tr>
                                        <tr>
                                            <td class="" align="right">Worm (%)</td>
                                            <td class="" align="left"><input type="text" class="numberOnly" name="worm" id="worm" value="" style="width: 40%"/></td>
                                        </tr>
                                        <tr>
                                            <td class="" align="right">Other Bean (%)</td>
                                            <td class="" align="left"><input type="text" class="numberOnly" name="other_bean" id="other_bean" value="" style="width: 40%"/></td>
                                        </tr>
                                        <tr>
                                            <td class="" align="right">Cherry (%)</td>
                                            <td class="" align="left"><input type="text" class="numberOnly" name="cherry" id="cherry" value="" style="width: 40%"/></td>
                                        </tr>
                                        <tr>
                                            <td class="" align="right">Black & Broken (%)</td>
                                            <td class="" align="left"><input type="text" class="numberOnly" name="black_broken" id="black_broken" value="" style="width: 40%"/></td>
                                        </tr>
                                        <tr>
                                            <td class="" align="right">Defect (%)</td>
                                            <td class="" align="left"><input type="text" class="numberOnly" name="defect" id="defect" value="" style="width: 40%"/></td>
                                        </tr>
                                    </table>
                                </div>
                                <div class="right" style="width: 425px;">
                                    <table class="detail_info_table" style="margin-left: 60px">
                                        <tr>
                                            <td class="" align="right">Above Screen 20 (%)</td>
                                            <td class="" align="left"><input type="text" class="single numberOnly" name="above_sc20" id="above_sc20" value="" style="width: 40%"/></td>
                                        </tr>
                                        <tr>
                                            <td class="" align="right">Screen 20 (%)</td>
                                            <td class="" align="left"><input type="text" class="single numberOnly" name="sc20" id="sc20" value="" style="width: 40%"/></td>
                                        </tr>
                                        <tr>
                                            <td class="" align="right">Screen 19 (%)</td>
                                            <td class="" align="left"><input type="text" class="single numberOnly" name="sc19" id="sc19" value="" style="width: 40%"/></td>
                                        </tr>
                                        <tr>
                                            <td class="" align="right">Screen 18 (%)</td>
                                            <td class="" align="left"><input type="text" class="single numberOnly" name="sc18" id="sc18" value="" style="width: 40%"/></td>
                                        </tr>
                                        <tr>
                                            <td class="" align="right">Screen 17 (%)</td>
                                            <td class="" align="left"><input type="text" class="single numberOnly" name="sc17" id="sc17" value="" style="width: 40%"/></td>
                                        </tr>
                                        <tr>
                                            <td class="" align="right">Screen 16 (%)</td>
                                            <td class="" align="left"><input type="text" class="single numberOnly" name="sc16" id="sc16" value="" style="width: 40%"/></td>
                                        </tr>
                                        <tr>
                                            <td class="" align="right">Screen 15 (%)</td>
                                            <td class="" align="left"><input type="text" class="single numberOnly" name="sc15" id="sc15" value="" style="width: 40%"/></td>
                                        </tr>
                                        <tr>
                                            <td class="" align="right">Screen 14 (%)</td>
                                            <td class="" align="left"><input type="text" class="single numberOnly" name="sc14" id="sc14" value="" style="width: 40%"/></td>
                                        </tr>
                                        <tr>
                                            <td class="" align="right">Screen 13 (%)</td>
                                            <td class="" align="left"><input type="text" class="single numberOnly" name="sc13" id="sc13" value="" style="width: 40%"/></td>
                                        </tr>
                                        <tr>
                                            <td class="" align="right">Screen 12 (%)</td>
                                            <td class="" align="left"><input type="text" class="single numberOnly" name="sc12" id="sc12" value="" style="width: 40%"/></td>
                                        </tr>
                                        <tr>
                                            <td class="" align="right">Below Screen 12 (%)</td>
                                            <td class="" align="left"><input type="text" class="single numberOnly" name="below_sc12" id="below_sc12" value="" style="width: 40%"/></td>
                                        </tr>
                                        <tr>
                                            <td class="" align="right"></td>
                                            <td class="fontBolder formatNumber" name="total_per" id="total_per" align="left">0.00</td>
                                        </tr>
                                    </table>
                                </div>
                            </div>
                            <div style="width: 850px !important; min-height: 100px; height: auto; overflow: hidden;margin-right: 9px; border-top: 1px solid gray" class="center">
                                <table class="quality_table center" border="0">
                                    <thead>
                                        <tr>
                                            <th></th>
                                            <th>Cup 1</th>
                                            <th>Cup 2</th>
                                            <th>Cup 3</th>
                                            <th>Cup 4</th>
                                            <th>Cup 5</th>
                                            <th>Rejected</th>
                                            <th></th>
                                        </tr>
                                    </thead>
                                    <tbody id="test_case">

                                    </tbody>
                                </table>
                            </div>
                            <div style="width: 850px !important; min-height: 100px; height: auto; overflow: hidden;margin-right: 9px; border-top: 1px solid gray" class="center">
                                <table class="detail_info_table" style="margin-left: 60px">
                                    <tr>
                                        <td class="" align="right">Remark</td>
                                        <td align="left">
                                            <textarea name="remark" id="remark" style="width: 575px; height: 50px"></textarea>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class=""  align="right">Complete</td>
                                        <td align="left">
                                            <input type="checkbox" value="{status}" name="status"  onclick="changeChkBoxVal(this)"/>
                                        </td>
                                    </tr>
                                </table>
                            </div>
                        </div>

                    </div>
                    <div class="right" style="width: 850px; margin-top: 20px;">
                        <input class="left" type="button" id="save" value="Save" name="perm_30"/>
                        <input class="right" id="btn_view_list" type="button" value="List View" />
                        <input class="right" type="button" value="Print" name="perm_58"/>
                        <input type="hidden" id="totalcup" name="totalcup" value=""/>
                        <input type="hidden" id="qrid" name="qrid" value=""/>
                        <input type="hidden" id="rejected_value" name="rejected_value" value=""/>
                    </div>
                </div>
            </form>
        <jsp:include page="footer.jsp"/>
        <script>
            var total_per;
            var qualityrId;
            $(document).ready(function() {
                check_forward();
                $.formatNumber(2);
            }).on("click", "#add_row", function() {
                var cur_num = parseInt($(this).parent().parent().attr("id").split("_")[1]) + 1;
                $(this).parent().parent().toggleClass("row_last");
                $(this).parent().parent().addClass("row_normal");
                if ($(this).parent().parent().hasClass("last_item")) {
                    $(this).parent().parent().toggleClass("last_item");
                    $("#test_case").append(
                            "<tr id='row_" + cur_num + "' class='row_last last_item on'>"
                            + "<th>Test " + cur_num + "</th>"
                            + "<td><input class='cup_item' name='ct_" + (parseInt(cur_num) * 5 - 4) + "' id='ct_" + (parseInt(cur_num) * 5 - 4) + "' type='text' style='width: 94%'/></td>"
                            + "<td><input class='cup_item' name='ct_" + (parseInt(cur_num) * 5 - 3) + "' id='ct_" + (parseInt(cur_num) * 5 - 3) + "' type='text' style='width: 94%'/></td>"
                            + "<td><input class='cup_item' name='ct_" + (parseInt(cur_num) * 5 - 2) + "' id='ct_" + (parseInt(cur_num) * 5 - 2) + "' type='text' style='width: 94%'/></td>"
                            + "<td><input class='cup_item' name='ct_" + (parseInt(cur_num) * 5 - 1) + "' id='ct_" + (parseInt(cur_num) * 5 - 1) + "' type='text' style='width: 94%'/></td>"
                            + "<td><input class='cup_item' name='ct_" + (parseInt(cur_num) * 5) + "' id='ct_" + (parseInt(cur_num) * 5) + "' type='text' style='width: 94%'/></td>"
                            + "<td class='rejected'><input type='text' class='rejected_value' style='width: 94%'/></td>"
                            + "<td class='rejected total' align='left'>/" + cur_num * 5 + "<a class='mpcth-icon-plus-squared' href='javascript:void(0)' id='add_row' style='margin-left: 5px'></a><a class='mpcth-icon-minus-squared' href='javascript:void(0)' id='remove_row' style='margin-left: 5px'></a>"
                            + "</td>"
                            + "</tr>"
                            );
                    stylingTestCupTable();
                } else {
                    $(this).parent().parent().next().toggleClass("row_normal");
                    $(this).parent().parent().next().addClass("row_last");
                    $(this).parent().parent().next().toggleClass("off");
                    $(this).parent().parent().next().addClass("on");
                    $(this).parent().parent().next().css("display", "");
                    stylingTestCupTable();
                }
            }).on("click", "#remove_row", function() {
                if ($(this).parent().parent().attr("id").split("_")[1] !== "1") {
                    $(this).parent().parent().toggleClass("row_last");
                    $(this).parent().parent().toggleClass("on");
                    $(this).parent().parent().addClass("off");
                    $(this).parent().parent().addClass("row_normal");
                    $(this).parent().parent().css("display", "none");
                    $(this).parent().parent().prev().toggleClass("row_normal");
                    $(this).parent().parent().prev().addClass("row_last");
                    stylingTestCupTable();
                }
            }).on("click", "#type_filter li", function() {
                //$("#type_filter li.chosen").toggleClass("chosen");
                //$(this).addClass("chosen");
                var qr_type = $(this).attr("id").split("_")[1];
                $("#qrType_id").val(qr_type);
                sendJsonRequest("qrRefList", "get_qr_ref_list", {"qrType_id": $("#type_filter li.chosen").attr("id").split("_")[1], "filter_ref": $("#filter_ref").val()}, 0);
            }).on("click", "#qrRefList li", function() {
                var qr_id = $(this).attr("id").split("_")[1];
                var type = $("#type_filter li.chosen").attr("id").split("_")[1];
                qualityrId = qr_id;
                $("#qrid").val(qr_id);
                //$(".ref_list li.chosen").toggleClass("chosen");
                //$(this).addClass("chosen");
                
                
//                $.sendRequest({
//                    target: "#qr_content",
//                    action: "get_qr_content",
//                    data: {"qr_id": qr_id},
//                    status: "replace_html",
//                    optional: {decimal: 2}
//                });
                
                $.sendRequest({
                    action: "get_qr_content_1",
                    data: {"qr_id": qr_id, "type": type},
                    optional: {decimal: 2}
                }, function(msg) {
                    try {
                        var data = JSON.parse(msg);
                        var file = getAbsolutePath() + "/js/templates/quality/qr_content.html";
                        $.when($.get(file))
                                .done(function(tmplData) {
                            var tmpl = $.templates(tmplData);
                            $("#qr_content").html(tmpl.render(data));
                            $.formatNumber(2);
                        });
                        
                    } catch (e) {
                        $.error(e);
                    }
                });
                $.formatNumber(2);
            }).on("click", "#save", function() {
                //check total
                var total_per = parseFloat($("#total_per").text());
                if (total_per == 100) {
                    
                    //callMessageDialog({type:"error",message:"Current total is over 100!!!"});
                    //total_per -= parseFloat($(this).val());

                    //check null cup test
                    //send request
                    $("#totalcup").val($(".row_last td.total").text().split("/")[1]);
                    $("#rejected_value").val($(".row_last input.rejected_value").val());
                    $.ajax({
                        type: 'POST',
                        async: false,
                        data: $("#quality_form").serialize(),
                        url: getAbsolutePath() + "/update_quality_report.htm",
                        success: function(msg) {
                            if (msg >= 0) {
                                $.success("Update completed");
                                $.sendRequest({
                                    target: "#qr_content",
                                    action: "get_qr_content",
                                    data: {qr_id: qualityrId},
                                    status: "replace_html"
                                });
                            } else {
                                $.error("update failed");
                            }
                        }
                    });
                }else{
                    $.warning("The Total Should Be 100 Percent");
                }
            }).on("change", "input.single", function() {
                total_per = 0;
                $(".single").each(function() {
                    if ($(this).val() === "") {
                        $(this).val("0");
                    }
                    total_per += parseFloat($(this).val());
                });
                $("#total_per").html(total_per);
                $.formatNumber(2);
            }).on("click", "#btn_view_list", function() {
                $("#quality_form").attr("action", getAbsolutePath() + "/quality_listview.htm");
                $("#quality_form").submit();
            }).on("keyup","#black,#broken",function() {
                var black = $("#black").val() != "" ? $("#black").val() : 0;
                var broken = $("#broken").val() != "" ? $("#broken").val() : 0;
                $("#black_broken").val(parseFloat(black) + parseFloat(broken));
            });
            function stylingTestCupTable() {
                $("#test_case tr.row_normal td.rejected").css("display", "none");
                $("#test_case tr.row_last td.rejected").css("display", "");
            }

            function check_forward() {
                if ($("#initial_id").val() != null && $("#initial_id").val() != "") {
                    var qr_type = $("#initial_type").val();
                    $("#qrType_id").val(qr_type);
                    var qr_id = '${initial_id}';
                    qualityrId = qr_id;
                    $.sendRequest({
                        target: "#qrRefList",
                        action: "get_qr_ref_list",
                        data: {"qrType_id": qr_type, "filter_ref": $("#filter_ref").val()},
                        status: "replace_html",
                        functions: [
                            {"name": "setHighlight", "param": []}
                        ]
                    });
                    //sendJsonRequest("qrRefList", "get_qr_ref_list", {"qrType_id": qr_type, "filter_ref": $("#filter_ref").val()}, 0, [{"name": "setHighlight", "param": []}]);

                    $("#qrid").val(qr_id);
                    $("#qr_" + qr_id).addClass("chosen");
                    scrollElTop("#qrRefList", "li.chosen");
                    $.sendRequest({
                        action: "get_qr_content_1",
                        data: {"qr_id": qr_id, "type": qr_type},
                        optional: {decimal: 2}
                    }, function(msg) {
                        try {
                            var data = JSON.parse(msg);
                            var file = getAbsolutePath() + "/js/templates/quality/qr_content.html";
                            $.when($.get(file))
                                    .done(function(tmplData) {
                                var tmpl = $.templates(tmplData);
                                $("#qr_content").html(tmpl.render(data));
                                $.formatNumber(2);
                            });

                        } catch (e) {
                            $.error(e);
                        }
                    });
                    
                    //sendJsonRequest("qr_content", "get_qr_content", {"qr_id": qr_id}, 0);
                }
            }

            function setHighlight() {
                $("#qr_" + qualityrId).addClass("chosen");
                scrollElTop("#qrRefList", "li.chosen");
            }
        </script>
    </body>
</html>

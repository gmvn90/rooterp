<%-- 
    Document   : stock_listview
    Created on : Dec 6, 2013, 2:19:58 PM
    Author     : kiendn
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Stock Plan</title>
        <style>
            table.sIP {margin-left: 30px; margin-top: 30px}
            table td{padding: 0; margin: 0}
            div#ip_section, .sStorage {width: 1875px}
            .sHead td {
                font-weight: bold;
                border-bottom: 2px solid black;
                font-size: 13px;
                text-align: center;
            }
            .sBody td, .sTotal td {
                font-size: 11px;
                text-align: center;
            }
            .sTitle:first-child td {border-top: none}
            .sFoot td{
                padding: 5px 0 5px 20px;
                background: #EEE !important;
                font-weight: bold;  
                font-size: 13px;
            }
            .sTitle td{
                padding-left: 20px;
                background: grey;
                color: white;
                border: 1px solid black;
                font-size: 13px;
            }
            .sTotal td:first-child {padding-left: 20px}
            .sTotal:last-child td {padding-bottom: 15px;}
            .sTotal td{
                padding-bottom: 25px;
                font-weight: bold;
                border-top: 1px solid black;
            }
            .sIP{float: left}
            table.sIP, table.sStorage, .sHead td, .sIP td {border-collapse: collapse}
            .sWidth60 {width: 60px}
            div#ip_section {position: relative; height: auto; overflow: hidden}
            table.sSummarize {float: left; margin-top: 30px; margin-left: 80px;}
            table.sSummarize td:nth-child(2){text-align: right !important; padding-right: 10px}
            table.sStorage tr.sBody:nth-child(even){background-color: #def;}
            table.sStorage tr.sBody:nth-child(even) td{background: none}
            .formatNumber,.formatNumber3 {text-align: right !important; padding-right: 10px!important}
            .xp_grade {text-align: left !important; padding-left: 10px}
            .hid{display: none !important}
            a.expandCollapse{cursor: pointer; padding-right: 15px}
            table.sStorage input[type=checkbox]{margin: 0}
            tr.sHead.notborder td{border-bottom: none !important; vertical-align: center}
        </style>
    </head>
    <body>
        <jsp:include page="header.jsp">
            <jsp:param name="url" value="29"/>
            <jsp:param name="page" value="70"/>
        </jsp:include>
        <div style="width: 1900px ! important; padding: 15px; background: none repeat scroll 0% 0% white; height: auto; overflow: hidden; min-height: 400px" class="container_16 border" id="wrapper">
            <div style="width: 100%; overflow: hidden; height: auto; border-bottom: 1px solid #B8C2CC; padding-bottom: 10px">
                <table class="table_filter" style='float: left'>
                    <thead>
                        <tr style="font-size: 13px">
                            <th width="100px">Client</th>
                            <th width="190px">Pledge</th>
                            <th width="190px">Grade</th>
                            <th width="190px">Warehouse</th>
                            <th width="190px">Date</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td width="100px">
                                <input id="client_filter_text" type="text" class="text_filter_child">
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
                            <td width="190px">
                                <input id="warehouse_filter_text" type="text" style="width: 190px;" class="text_filter_child">
                                <ul id="warehouse_filter" class="filter">
                                </ul>
                            </td>
                            <td width="190px" style="vertical-align: top">
                                <label>
                                    <input type="text" id="date_filter" class="date_picker_text" readonly="true">
                                    <a href="javascript:void(0);" class="clear_date_filter">Reset</a>
                                </label>
                            </td>
                        </tr>
                    </tbody>
                </table>
                <input type='button' id='btnSearch' value='Search' style='float: left; margin-left: 30px; margin-top: 140px'/>
                <input type='button' id='stockReport' value='Print' style='float: left; margin-left: 30px; margin-top: 140px'/>

            </div>
            <div id="search_result" style="width: 100%;overflow: hidden; height: auto; border-bottom: 1px solid #B8C2CC; padding-bottom: 10px; padding-top: 30px">
            </div>
            <input type="button" class="btnSaveChange hid" style="float: left; margin-left: 20px; margin-top: 15px" value="Save"/>
        </div>
        <jsp:include page="footer.jsp"/>
        <script>
            $(document).ready(function() {
                //loadFilterList();
                loadClientFilter();
                loadPledgeFilter();
                loadGradeFilter();
                loadWarehouseFilter();
            }).on("click", "#btnSearch", function() {
                searchData();
            }).on("click", ".clear_date_filter", function() {
                $(this).siblings("input").val("");
            }).on("click", ".expandCollapse", function() {
                var id = $(this).parent().parent("tr").attr("id").split("_")[1];
                if ($(this).hasClass("mpcth-icon-minus")) {
                    $(this).toggleClass("mpcth-icon-minus");
                    $(this).addClass("mpcth-icon-plus");
                    $(".wn_" + id).addClass("hid");
                }
                else if ($(this).hasClass("mpcth-icon-plus")) {
                    $(this).toggleClass("mpcth-icon-plus");
                    $(this).addClass("mpcth-icon-minus");
                    $(".wn_" + id).removeClass("hid");
                }
            }).on("click", "#stockReport", function() {
                var now = $.format.date(new Date, "yyMdd");
                var search_date = $("#date_filter").val().split("-");
                var data = {
                    client: $("#client_filter li.chosen").attr("id").split("_")[1],
                    pledge: $("#pledge_filter li.chosen").attr("id").split("_")[1],
                    warehouse: $("#warehouse_filter li.chosen").attr("id").split("_")[1],
                    grade: $("#grade_filter li.chosen").attr("id").split("_")[1]
                };
                if (search_date.length == 3) {
                    var searchDate = search_date[2].substring(2) + search_date[1] + search_date[0];
                    if (searchDate != now) {
                        data["date"] = searchDate;
                    }
                }
                $.sendRequest({
                    action: "searchStock_report",
                    status: "export",
                    data: data
                });
            });

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

            function searchData(b) {
                if (b == undefined) {
                    b = true;
                }
                var now = $.format.date(new Date, "yyMdd");
                var search_date = $("#date_filter").val().split("-");
                $("#search_result").html("<div style='width: 200px; margin: 0 auto'>Loading...</div>");
                var data = {
                    client: $("#client_filter li.chosen").attr("id").split("_")[1],
                    pledge: $("#pledge_filter li.chosen").attr("id").split("_")[1],
                    warehouse: $("#warehouse_filter li.chosen").attr("id").split("_")[1],
                    grade: $("#grade_filter li.chosen").attr("id").split("_")[1]
                };
                if (search_date.length == 3) {
                    var searchDate = search_date[2].substring(2) + search_date[1] + search_date[0];
                    if (searchDate != now) {
                        data["date"] = searchDate;
                    }
                }
                $.sendRequest({
                    action: "searchStock",
                    data: data
                }, function(msg) {
                    try {
                        var data = JSON.parse(msg);
                        $.data(document.body, "searchData", data);
                        var file = getAbsolutePath() + "/js/templates/stock/stock.html";
                        $.when($.get(file))
                                .done(function(tmplData) {
                                    var tmpl = $.templates(tmplData);
                                    $("#search_result").html(tmpl.render(data));
                                    //count total of each grade
                                    countGradeTotal();
                                });
                        return b;
                    } catch (e) {
                        $.error("No Data Found");
                    }
                });
            }

            function countGradeTotal() {
                var grand_total = 0;
                var grand_pi = 0;
                $(".graderow").each(function() {
                    var id = $(this).attr("id").split("_")[1];
                    var tons = 0;
                    var sump_black = 0;
                    var sump_brown = 0;
                    var sump_fm = 0;
                    var sump_broken = 0;
                    var sump_moist = 0;
                    var sump_ocrop = 0;
                    var sump_moldy = 0;
                    var sump_asc20 = 0;
                    var sump_sc20 = 0;
                    var sump_sc19 = 0;
                    var sump_sc18 = 0;
                    var sump_sc17 = 0;
                    var sump_sc16 = 0;
                    var sump_sc15 = 0;
                    var sump_sc14 = 0;
                    var sump_sc13 = 0;
                    var sump_sc12 = 0;
                    var sump_bsc12 = 0;
                    $(".tons_" + id).each(function() {
                        tons = parseFloat(tons) + parseFloat($(this).text());
                    });
                    $(".black_tons_" + id).each(function() {
                        sump_black = parseFloat(sump_black) + parseFloat($(this).val());
                    });
                    $(".brown_tons_" + id).each(function() {
                        sump_brown = parseFloat(sump_brown) + parseFloat($(this).val());
                    });
                    $(".fm_tons_" + id).each(function() {
                        sump_fm = parseFloat(sump_fm) + parseFloat($(this).val());
                    });
                    $(".broken_tons_" + id).each(function() {
                        sump_broken = parseFloat(sump_broken) + parseFloat($(this).val());
                    });
                    $(".moist_tons_" + id).each(function() {
                        sump_moist = parseFloat(sump_moist) + parseFloat($(this).val());
                    });
                    $(".ocrop_tons_" + id).each(function() {
                        sump_ocrop = parseFloat(sump_ocrop) + parseFloat($(this).val());
                    });
                    $(".moldy_tons_" + id).each(function() {
                        sump_moldy = parseFloat(sump_moldy) + parseFloat($(this).val());
                    });
                    $(".asc20_tons_" + id).each(function() {
                        sump_asc20 = parseFloat(sump_asc20) + parseFloat($(this).val());
                    });
                    $(".sc20_tons_" + id).each(function() {
                        sump_sc20 = parseFloat(sump_sc20) + parseFloat($(this).val());
                    });
                    $(".sc19_tons_" + id).each(function() {
                        sump_sc19 = parseFloat(sump_sc19) + parseFloat($(this).val());
                    });
                    $(".sc18_tons_" + id).each(function() {
                        sump_sc18 = parseFloat(sump_sc18) + parseFloat($(this).val());
                    });
                    $(".sc17_tons_" + id).each(function() {
                        sump_sc17 = parseFloat(sump_sc17) + parseFloat($(this).val());
                    });
                    $(".sc16_tons_" + id).each(function() {
                        sump_sc16 = parseFloat(sump_sc16) + parseFloat($(this).val());
                    });
                    $(".sc15_tons_" + id).each(function() {
                        sump_sc15 = parseFloat(sump_sc15) + parseFloat($(this).val());
                    });
                    $(".sc14_tons_" + id).each(function() {
                        sump_sc14 = parseFloat(sump_sc14) + parseFloat($(this).val());
                    });
                    $(".sc13_tons_" + id).each(function() {
                        sump_sc13 = parseFloat(sump_sc13) + parseFloat($(this).val());
                    });
                    $(".sc12_tons_" + id).each(function() {
                        sump_sc12 = parseFloat(sump_sc12) + parseFloat($(this).val());
                    });
                    $(".bsc12_tons_" + id).each(function() {
                        sump_bsc12 = parseFloat(sump_bsc12) + parseFloat($(this).val());
                    });

                    grand_total = parseFloat(grand_total) + parseFloat(tons);
                    $("#total_tons_" + id).text(tons);
                    $("#wtAvg_black_" + id).text(sump_black / tons);
                    $("#wtAvg_brown_" + id).text(sump_brown / tons);
                    $("#wtAvg_fm_" + id).text(sump_fm / tons);
                    $("#wtAvg_broken_" + id).text(sump_broken / tons);
                    $("#wtAvg_moist_" + id).text(sump_moist / tons);
                    $("#wtAvg_ocrop_" + id).text(sump_ocrop / tons);
                    $("#wtAvg_moldy_" + id).text(sump_moldy / tons);
                    $("#wtAvg_asc20_" + id).text(sump_asc20 / tons);
                    $("#wtAvg_sc20_" + id).text(sump_sc20 / tons);
                    $("#wtAvg_sc19_" + id).text(sump_sc19 / tons);
                    $("#wtAvg_sc18_" + id).text(sump_sc18 / tons);
                    $("#wtAvg_sc17_" + id).text(sump_sc17 / tons);
                    $("#wtAvg_sc16_" + id).text(sump_sc16 / tons);
                    $("#wtAvg_sc15_" + id).text(sump_sc15 / tons);
                    $("#wtAvg_sc14_" + id).text(sump_sc14 / tons);
                    $("#wtAvg_sc13_" + id).text(sump_sc13 / tons);
                    $("#wtAvg_sc12_" + id).text(sump_sc12 / tons);
                    $("#wtAvg_bsc12_" + id).text(sump_bsc12 / tons);
                });
//                var storage = 0;
//                $(".grade_total_tons").each(function(){
//                    storage = parseFloat(storage) + parseFloat($(this).text());
//                });
                $(".total_tons").text(grand_total);

                $(".processing").each(function() {
                    var id = $(this).attr("id").split("_")[1];
                    var ip = $("#ip_" + id).text();
                    var total_pi = 0;
                    var xp = 0;
                    $(".xp_tons_" + id).each(function() {
                        xp = parseFloat(xp) + parseFloat($(this).text());
                    });
                    total_pi = ip - xp;
                    grand_pi = parseFloat(grand_pi) + parseFloat(total_pi);
                    $("#total_pi_tons_" + id).text(total_pi);
                });

                $(".total_pi").text(grand_pi);
                $("#total_stock").text(parseFloat(grand_pi) + parseFloat(grand_total));

                $.formatNumber(2);
                $(".formatNumber3").formatNumber(3);
            }
        </script>
    </body>
</html>

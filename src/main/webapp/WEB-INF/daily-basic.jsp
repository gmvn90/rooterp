<%--
    Document   : stock_listview
    Created on : Dec 6, 2013, 2:19:58 PM
    Author     : kiendn
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="f" uri="http://swcommodities.com/formatFunctions" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Daily Basic</title>
        <style>
            .styleTotal td {
                border-top: 1px solid #000;
            }

            .bolder {
                font-size: 12px;
            }

            #btn_add_new, #btn_delete {
                min-width: 50px !important
            }

            table.instructionDetailStandard {
                border-collapse: collapse;
            }

            .title {
                font-weight: normal !important;
                font-size: 13px !important;
                padding: 1px !important;
                text-align: left;
            }

            .subTitle {
                font-weight: bold;
                padding-top: 2px !important;
                font-size: 13px !important;
                text-align: left;
            }

            .field_value {
                font-weight: bold;
                font-size: 13px !important;
                padding: 1px !important;
                text-align: left;
            }

            .underline-bottom {
                border-bottom: 1px solid black;
            }

            table.instructionDetailStandard tr td {
                padding: 5px !important;
            }
            table.instructionDetailStandard tr th {
                padding: 5px !important;
            }


        </style>
    </head>
    <body>
        <jsp:include page="header.jsp">
            <jsp:param name="url" value="74"/>
            <jsp:param name="page" value="123"/>
        </jsp:include>
        <div style="width: 1200px ! important; padding: 15px; background: none repeat scroll 0% 0% white; height: auto; overflow: hidden"
             class="container_16 border" id="wrapper">

            <div id="detail_content">
                <div style="width: 1200px !important; min-height: 80px; height: auto; overflow: hidden;margin-right: 9px;"
                     class="">
                    <table class="instructionDetailStandard" style="padding: 20px; width: 100%">

                        <%--Exchange section--%>
                        <tr>
                            <td class="" colspan="6">
                                <div id="currency">
                                    <fieldset>
                                        <legend>Exchange</legend>
                                        <table class="" style="width: 100%; font-size: 9pt">
                                            <thead>
                                                <tr>
                                                    <th>Currency</th>
                                                    <th>Previous FX to USD</th>
                                                    <th>Prev. Date</th>
                                                    <th>Current FX to USD</th>
                                                    <th>Updated By</th>
                                                    <th></th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <tr>
                                                    <td align="center"><strong>USD to VND</strong></td>
                                                    <td align="center"><span id="curExchangePrevRatio"></span></td>
                                                    <td align="center"><span id="curExchangePrevDate"></span></td>
                                                    <td align="center"><input style="text-align:right;" type="text" id="currency_usd_vnd" name=""
                                                                              value="0" class="numberOnly"/></td>
                                                    <td align="center" id="curExchangeUpdated"></td>
                                                    <td align="center">
                                                        <c:if test="${permissonsMap.get('perm_144')}">
                                                        <input type="button" value="Save" class="" id="saveCurExchange"/>
                                                        </c:if>
                                                    </td>
                                                </tr>

                                            </tbody>
                                        </table>
                                    </fieldset>
                                </div>
                            </td>
                        </tr>
                        <!-- end Exchange Section -->
                        
                        <tr>
                            <td class="" colspan="6">
                                <fieldset>
                                    <legend>Terminal month</legend>
<c:if test="${permissonsMap.get('perm_144')}">
                                    <input type="button" id="load-daily-basis" value="Load from Trade" />
    </c:if>
                                    <br>
                                    <form action="override_daily_basis_with_value_from_trade.htm" method="POST">
<c:if test="${permissonsMap.get('perm_145')}">
                                        <input type="submit" value="Save terminal months with values from Trade" />
    </c:if>
                                    </form>
                                    <table class="" style="width: 100%; font-size: 9pt">
                                        <thead>
                                            <tr>
                                                <th>Terminal Mth.</th>
                                                <th>Prev. Date</th>
                                                <th>High</th>
                                                <th>Low</th>
                                                <th>Previous Basis</th>
                                                <th>Current Basis</th>
                                                <th class="before-trade-load">Current Value</th>
                                                <th>Authorized By</th>
                                                <th></th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach items="${dailyBasises}" var="item">
                                            <form method="post" action="update-daily-basis.htm" class="update-daily-basis-form">
                                                <tr id="daily-basis-${item.tmCode}">
                                                    <td>${item.terminalMonth}</td>
                                                    <td>${f:formatDateTime(item.previousDate)}</td>
                                                    <td><input type="text" value="${item.liffeHigh}" name="liffeHigh" class="liffeHigh" /></td>
                                                    <td><input type="text" value="${item.liffeLow}" name="liffeLow" class="liffeLow" /></td>
                                                    <td>${item.previousBasis}</td>
                                                    <td><input type="text" value="${item.currentBasis}" name="currentBasis" class="currentBasis" />
                                                    <input type="hidden" value="${item.id}" name="id" /></td>
                                                    <td class="before-trade-load"></td>
                                                    <td>${item.user.userName}: ${f:formatDateTime(item.updatedDate)}</td>
                                                    <td>
                                                        <c:if test="${permissonsMap.get('perm_145')}">
                                                        <input type="submit" value="Update" />
                                                        </c:if>
                                                    </td>
                                                </tr>
                                            </form>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                </fieldset>
                            </td>
                        </tr>

                        <%--Daily basic section Grade 	Last Ct. Diff 	Last Ct. Date 	Trade Dept Diff 	Authorized By 	--%>
                        <tr>
                            <td class="" colspan="6">
                                <fieldset>
                                    <legend>Mark to Market FOB Diff</legend>
<c:if test="${permissonsMap.get('perm_146')}">
                                    <input type="button" id="load-fob" value="Load from Trade" />
    </c:if>
                                    <form action="override_market_fob_with_value_from_trade.htm" method="POST">
<c:if test="${permissonsMap.get('perm_146')}">
                                        <input type="submit" value="Save Fob diffs with values from Trade" />
    </c:if>
                                    </form>
                                    <table class="" style="width: 100%; font-size: 9pt">
                                        <thead>
                                            <tr>
                                                <th>Grade</th>
                                                <th>Last Ct. Diff</th>
                                                <th>Last Ct. Date</th>
                                                <th>Trade Dept Diff</th>
                                                <th class="before-trade-load">Current value</th>
                                                <th>Authorized By</th>
                                                <th></th>
                                            </tr>
                                        </thead>

                                        <tbody>
                                            <c:forEach items="${markets}" var="item">
                                            <form method="post" action="update-fob.htm">
                                                <tr id="daily-basic-${item.gradeMaster.id}">
                                                    <td>${item.gradeMaster.name}</td>
                                                    <td>${item.lastDiff}</td>
                                                    <td>${f:formatDateTime(item.lastDate)}</td>
                                                    <td><input type="hidden" name="id" value="${item.id}" /><input type="text" value="${item.diff}" name="diff" class="diff" /></td>
                                                    <td class="before-trade-load"></td>
                                                    <td>${item.user.userName}:${f:formatDateTime(item.updatedDate)}</td>
                                                    <td>
                                                        <c:if test="${permissonsMap.get('perm_146')}">
                                                        <input type="submit" value="Update" />
                                                        </c:if>
                                                    </td>
                                                </tr>
                                            </form>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                </fieldset>
                            </td>
                        </tr>
                        <!-- end Daily basic Section -->


                    </table>
                </div>
            </div>

        </div>
        <style>
            .before-trade-load {
                display: none;
            }
        </style>
        <script src="${base_web_url}/js/jquery.serializejson.js"></script>
        <script>
            $(document).ready(function () {
                loadCurExchange();
                $(".js_date").datepicker({
                    dateFormat: "d-M-y",
                    changeMonth: true,
                    changeYear: true,
                    yearRange: "1945:2050"
                });
                $("#load-fob").click(function () {
                    $.ajax({
                        type: 'GET',
                        contentType: "application/json",
                        url: getAbsolutePath() + "/market-fob-diff.json",
                        success: function (msg) {
                            console.log(msg);
                            for (var i = 0; i < msg.result.length; i++) {
                                var trade = msg.result[i];
                                var container = $("#daily-basic-" + trade.gradeMaster__id);
                                if (container.length > 0) {
                                    container.find(".before-trade-load").html(container.find(".diff").val());
                                    container.find(".diff").val(trade.diff);
                                }
                            }
                            $(".before-trade-load").show();
                        }
                    });
                });
                
                $("#load-daily-basis").click(function() {
                    $.ajax({
                        type: 'GET',
                        contentType: "application/json",
                        url: getAbsolutePath() + "/daily-basic-histories.json",
                        success: function (msg) {
                            console.log(msg);
                            for (var i = 0; i < msg.length; i++) {
                                var trade = msg[i];
                                console.log("#daily-basis-" + trade.tmCode);
                                console.log(trade);
                                var container = $("#daily-basis-" + trade.tmCode);
                                console.log(container.length);
                                if (container.length > 0) {
                                    container.find(".before-trade-load").html(container.find(".currentBasis").val());
                                    container.find(".currentBasis").val(trade.currentBasis);
                                }
                            }
                            $(".before-trade-load").show();
                        }
                    });
                    
                });
                
            }).on("click", "#saveCurExchange", function () {
                var send_value = $("#currency_usd_vnd").val().replace(',', '');
                var send_id = 1;
                $.sendRequest({
                    action: "updateCurExchange/" + send_id,
                    headers: {
                        'Accept': 'application/json',
                        'Content-Type': 'application/json'
                    },
                    data: JSON.stringify({
                        send_value: send_value,
                        send_id: send_id
                    }),
                    optional: {url_type: "json"}
                }, function (msg) {
                    $.success("Update success!");
                    loadCurExchange();
                });
            }).on("submit", ".update-daily-basis-form", function() {
                console.log($(this).serializeJSON());
            });

            function loadCurExchange() {
                $.sendRequest({
                    action: "getCurExchange",
                    optional: {url_type: "json"}
                }, function (msg) {
                    console.log(msg);
                    $("#currency_usd_vnd").val(accounting.formatNumber(msg.ratio, 0, ",", "."));
                    $("#curExchangeUpdated").html(msg.username + ": " + $.format.date(msg.updated, "dd-MMM-yy hh:mm:ss"));
                    if (msg.exchangeHistory) {
                        $("#curExchangePrevDate").html($.format.date(msg.exchangeHistory.exchangeCreated, "dd-MMM-yy hh:mm:ss"));
                        $("#curExchangePrevRatio").html(msg.exchangeHistory.ratio);
                    }
                    return true;
                });
            }

        </script>
        <jsp:include page="footer.jsp"/>

    </body>
</html>

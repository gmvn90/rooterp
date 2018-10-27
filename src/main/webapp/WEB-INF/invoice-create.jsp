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
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Create Invoice</title>
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
    <jsp:param name="url" value="68"/>
    <jsp:param name="page" value="115"/>
</jsp:include>
<div style="width: 1200px ! important; padding: 15px; background: none repeat scroll 0% 0% white; height: auto; overflow: hidden"
     class="container_16 border" id="wrapper">

    <div id="detail_content">
        <div style="width: 1200px !important; min-height: 80px; height: auto; overflow: hidden;margin-right: 9px;"
             class="">
            <table class="instructionDetailStandard" style="padding: 20px; width: 100%">
                <form action="invoice-create.htm" id="x-form" method="get">
                    <tr>
                        <td></td>
                        <td class="title">Client:</td>
                        <td class="field_value">
                            <select name="clientId" id="clientId"
                                    style="width: 100%";>
                                <option value="-1" <c:if test="${clientId==-1}">selected</c:if>>All</option>
                                <c:forEach var="item" items="${clients}">
                                    <option
                                            <c:if test="${item.id==clientId}">selected</c:if>
                                            value="${item.id}">${item.name}</option>
                                </c:forEach>
                            </select>
                        </td>
                        <td></td>
                        <td class="title">Client Ref.:</td>
                        <td class="field_value">
                            <input type="text" name="clientRef" style="width: 100%; max-height: inherit" value="">
                        </td>
                    </tr>
                </form>
                <form action="invoice.htm" method="post">
                    <c:if test="${isPreGenerating == false}">
                        <input type="hidden" name="clientIdForInvoice" value="${clientId}"/>
                        <input type="hidden" name="clientRefForInvoice" value="${clientRef}"/>

                    <%--Deposit section--%>
                    <tr>
                        <td class="" colspan="6">
                            <fieldset>
                                <legend>Deposit</legend>
                                <table class="" style="width: 100%; font-size: 9pt" id="transaction-deposit">
                                    <thead>
                                    <tr>
                                        <th style="width: 15%">DI Ref.</th>
                                        <th style="width: 15%">Date</th>
                                        <th style="width: 15%">Location</th>
                                        <th style="width: 15%">Grade</th>
                                        <th style="width: 8%">Tons</th>
                                        <th style="width: 8%">Cost</th>
                                        <th style="width: 8%">Total</th>
                                        <th style="width: 8%"></th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:set var="depositTotalTons" value="${0}"/>
                                    <c:set var="depositTotal" value="${0}"/>
                                    <c:forEach var="itemDeposit" items="${deposits}">
                                        <tr data-instrucion_ref="${itemDeposit.insRef}" class="instruction-item instruction-ref-${itemDeposit.insRef}">
                                            <td style="text-align: center"><a href="transaction.htm?id=${itemDeposit.id}">${itemDeposit.insRef}</a></td>
                                            <td style="text-align: center"><fmt:formatDate
                                                    pattern="dd-MMM-yy HH:mm:ss" value="${itemDeposit.date}"/></td>
                                            <td style="text-align: center">${itemDeposit.location}</td>
                                            <td style="text-align: center">${itemDeposit.grade}</td>
                                            <td style="text-align: right"><fmt:formatNumber type="number"
                                                                                            minFractionDigits="3"
                                                                                            maxFractionDigits="3"
                                                                                            value="${itemDeposit.tons}"/></td>
                                            <td style="text-align: right"><fmt:formatNumber value="${itemDeposit.cost}" maxFractionDigits="2"
                                                                  minFractionDigits="2"/></td>
                                            <td style="text-align: right"><fmt:formatNumber type="number"
                                                                                            minFractionDigits="2"
                                                                                            maxFractionDigits="2"
                                                                                            value="${itemDeposit.total}"/></td>
                                            <td><input type="checkbox" name="selectedTransIds" value="${itemDeposit.id}" checked/></td>
                                        </tr>
                                        <c:set var="depositTotalTons" value="${depositTotalTons + itemDeposit.tons}"/>
                                        <c:set var="depositTotal"
                                               value="${depositTotal + itemDeposit.total}"/>
                                    </c:forEach>
                                    </tbody>
                                    <tfoot>
                                    <tr>
                                        <th colspan="3"></th>
                                        <th style="text-align: right">Total:</th>
                                        <th style="text-align: right"><fmt:formatNumber type="number"
                                                                                        minFractionDigits="3"
                                                                                        maxFractionDigits="3"
                                                                                        value="${depositTotalTons}"/></th>
                                        <th></th>
                                        <th style="text-align: right"><fmt:formatNumber type="number"
                                                                                        minFractionDigits="2"
                                                                                        maxFractionDigits="2"
                                                                                        value="${depositTotal}"/></th>
                                        <th></th>
                                    </tr>
                                    </tfoot>
                                </table>
                            </fieldset>
                        </td>
                    </tr>

                    <%--Upgrade section--%>
                    <tr>
                        <td class="" colspan="6">
                            <fieldset>
                                <legend>Upgrade</legend>
                                <table class="" style="width: 100%; font-size: 9pt" id="transaction-upgrade">
                                    <thead>
                                    <tr>
                                        <th style="width: 15%">Upgrade Ref.</th>
                                        <th style="width: 15%">Date</th>
                                        <th style="width: 15%">Location</th>
                                        <th style="width: 15%">PI Type</th>
                                        <th style="width: 8%">Tons</th>
                                        <th style="width: 8%">Cost</th>
                                        <th style="width: 8%">Total</th>
                                        <th style="width: 8%"></th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:set var="upgradeTotalTons" value="${0}"/>
                                    <c:set var="upgradeTotal" value="${0}"/>
                                    <c:forEach var="itemUpgrade" items="${upgrades}">
                                        <tr data-instrucion_ref="${itemUpgrade.insRef}" class="instruction-item instruction-ref-${itemUpgrade.insRef}">
                                            <td style="text-align: center"><a href="transaction.htm?id=${itemUpgrade.id}">${itemUpgrade.insRef}</a></td>
                                            <td style="text-align: center"><fmt:formatDate
                                                    pattern="dd-MMM-yy HH:mm:ss" value="${itemUpgrade.date}"/></td>
                                            <td style="text-align: center">${itemUpgrade.location}</td>
                                            <td style="text-align: center">${itemUpgrade.type}</td>
                                            <td style="text-align: right"><fmt:formatNumber type="number"
                                                                                            minFractionDigits="3"
                                                                                            maxFractionDigits="3"
                                                                                            value="${itemUpgrade.tons}"/></td>
                                            <td style="text-align: right"><fmt:formatNumber value="${itemUpgrade.cost}" maxFractionDigits="2"
                                                                  minFractionDigits="2"/></td>
                                            <td style="text-align: right"><fmt:formatNumber type="number"
                                                                                            minFractionDigits="2"
                                                                                            maxFractionDigits="2"
                                                                                            value="${itemUpgrade.total}"/></td>
                                            <td><input type="checkbox" name="selectedTransIds" value="${itemUpgrade.id}" checked/></td>
                                        </tr>
                                        <c:set var="upgradeTotalTons" value="${upgradeTotalTons + itemUpgrade.tons}"/>
                                        <c:set var="upgradeTotal"
                                               value="${upgradeTotal + itemUpgrade.total}"/>
                                    </c:forEach>
                                    </tbody>
                                    <tfoot>
                                    <tr>
                                        <th colspan="3"></th>
                                        <th style="text-align: right">Total:</th>
                                        <th style="text-align: right"><fmt:formatNumber type="number"
                                                                                        minFractionDigits="3"
                                                                                        maxFractionDigits="3"
                                                                                        value="${upgradeTotalTons}"/></th>
                                        <th></th>
                                        <th style="text-align: right"><fmt:formatNumber type="number"
                                                                                        minFractionDigits="2"
                                                                                        maxFractionDigits="2"
                                                                                        value="${upgradeTotal}"/></th>
                                        <th></th>
                                    </tr>
                                    </tfoot>
                                </table>
                            </fieldset>
                        </td>
                    </tr>

                    <%--Withdraw section--%>
                    <tr>
                        <td class="" colspan="6">
                            <fieldset>
                                <legend>Withdraw</legend>
                                <table class="" style="width: 100%; font-size: 9pt" id='transaction-withdraw'>
                                    <thead>
                                    <tr>
                                        <th style="width: 15%">SI Ref.</th>
                                        <th style="width: 15%">Date</th>
                                        <th style="width: 15%">Location</th>
                                        <th style="width: 15%">Grade</th>
                                        <th style="width: 8%">Tons</th>
                                        <th style="width: 8%">Cost</th>
                                        <th style="width: 8%">Total</th>
                                        <th style="width: 8%"></th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:set var="withdrawTotalTons" value="${0}"/>
                                    <c:set var="withdrawTotal" value="${0}"/>
                                    <c:forEach var="itemWithdraw" items="${withdraws}">
                                        <tr data-instrucion_ref="${itemWithdraw.insRef}" class="instruction-item instruction-ref-${itemWithdraw.insRef}">
                                            <td style="text-align: center"><a href="transaction.htm?id=${itemWithdraw.id}">${itemWithdraw.insRef}</a></td>
                                            <td style="text-align: center"><fmt:formatDate
                                                    pattern="dd-MMM-yy HH:mm:ss" value="${itemWithdraw.date}"/></td>
                                            <td style="text-align: center">${itemWithdraw.location}</td>
                                            <td style="text-align: center">${itemWithdraw.grade}</td>
                                            <td style="text-align: right"><fmt:formatNumber type="number"
                                                                                            minFractionDigits="3"
                                                                                            maxFractionDigits="3"
                                                                                            value="${itemWithdraw.tons}"/></td>
                                            <td style="text-align: right"><fmt:formatNumber value="${itemWithdraw.cost}" maxFractionDigits="2"
                                                                  minFractionDigits="2"/></td>
                                            <td style="text-align: right"><fmt:formatNumber type="number"
                                                                                            minFractionDigits="2"
                                                                                            maxFractionDigits="2"
                                                                                            value="${itemWithdraw.total}"/></td>
                                            <td><input type="checkbox" name="selectedTransIds" value="${itemWithdraw.id}" checked/></td>
                                        </tr>
                                        <c:set var="withdrawTotalTons" value="${withdrawTotalTons + itemWithdraw.tons}"/>
                                        <c:set var="withdrawTotal"
                                               value="${withdrawTotal + itemWithdraw.total}"/>
                                    </c:forEach>
                                    </tbody>
                                    <tfoot>
                                    <tr>
                                        <th colspan="3"></th>
                                        <th style="text-align: right">Total:</th>
                                        <th style="text-align: right"><fmt:formatNumber type="number"
                                                                                        minFractionDigits="3"
                                                                                        maxFractionDigits="3"
                                                                                        value="${withdrawTotalTons}"/></th>
                                        <th></th>
                                        <th style="text-align: right"><fmt:formatNumber type="number"
                                                                                        minFractionDigits="2"
                                                                                        maxFractionDigits="2"
                                                                                        value="${withdrawTotal}"/></th>
                                        <th></th>
                                    </tr>
                                    </tfoot>
                                </table>
                            </fieldset>
                        </td>
                    </tr>
                    <tr>
                        <td class="title" colspan="4"></td>
                        <td class="field_value" colspan="2">
                            <input
                                    class="button ui-button ui-widget ui-state-default ui-corner-all" type="submit"
                                    value="Generate" onclick="disableSubmitAfterClick(this)"/>
                        </td>
                    </tr>
                    </c:if>
                </form>
            </table>
        </div>
    </div>

</div>
<script>
    $(document).ready(function () {
        $(".js_date").datepicker({
            dateFormat: "d-M-y",
            changeMonth: true,
            changeYear: true,
            yearRange: "1945:2050"
        });
        var will_changes = "#clientId";
        $(will_changes).change(function () {
            $('#x-form').find('.js_date').each(function () {
                if ($(this).val() == '') {
                    $(this).attr('disabled', 'disabled');
                }
            });
            $('#x-form').submit();
        });
        colorizeDuplicate("transaction-deposit");
        colorizeDuplicate("transaction-withdraw");
        colorizeDuplicate("transaction-upgrade");
    }).on("click", "#btn_print", function () {
        $("select").each(function () {
            $(this).find(":selected").attr("selected", true);
        });
        $("input[type=checkbox]").filter(":checked").attr("checked", true);
        $("#detail_content").printThis();
    });
</script>
<jsp:include page="footer.jsp"/>

</body>
</html>

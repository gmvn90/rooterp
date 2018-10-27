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
    <title>Transaction Detail</title>
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
    <jsp:param name="url" value="65"/>
    <jsp:param name="page" value="113"/>
</jsp:include>
<div style="width: 1200px ! important; padding: 15px; background: none repeat scroll 0% 0% white; height: auto; overflow: hidden"
     class="container_16 border" id="wrapper">

    <div id="detail_content">
    <div style="width: 1200px !important; min-height: 80px; height: auto; overflow: hidden;margin-right: 9px;"
         class="">
        <table class="instructionDetailStandard" style="padding: 20px; width: 100%">
            <form:form action="transaction_saveApprovalStatus.htm" method="post" commandName="transactionForm">
                <tr>
                    <td></td>
                    <td class="title">Approval Status:</td>
                    <td class="field_value" colspan="3">
                        <form:select path="item.approvalStatus"
                                     style="width: 30%">
                            <form:options items="${approvalStatuses}"/>
                        </form:select>
                        <label>${transactionForm.item.approveUser.userName}:<fmt:formatDate
                                pattern="dd-MMM-yy HH:mm:ss"
                                value="${transactionForm.item.approvalDate}"/></label>
                        <c:if test="${permissonsMap.get('perm_126')}">
                        <input class="button ui-button ui-widget ui-state-default ui-corner-all" type="submit"
                               value="Save" onclick="disableSubmitAfterClick(this)"/>
                        </c:if>
                    </td>
                </tr>
            </form:form>
            <form:form action="transaction_saveInvoicedStatus.htm" method="post" commandName="transactionForm">
                <tr>
                    <td></td>
                    <td class="title">Invoiced Status:</td>
                    <td class="field_value" colspan="3">
                            ${invoicedStatuses[transactionForm.item.invoicedStatus]} --
                        <label>${transactionForm.item.invoicedUser.userName}:<fmt:formatDate
                                pattern="dd-MMM-yy HH:mm:ss"
                                value="${transactionForm.item.invoicedDate}"/></label>
                    </td>
                </tr>
            </form:form>
            <form:form action="transaction.htm" method="post" commandName="transactionForm">
            <tr>
                <td class="subTitle" colspan="3">Transaction</td>
            </tr>
            <tr>
                <td></td>
                <td class="title">Transaction Ref.:</td>
                <td class="field_value">
                    ${transactionForm.item.refNumber}
                </td>
                <td></td>
                <td class="title">Transaction Date:</td>
                <td class="field_value">
                    <fmt:formatDate pattern="dd-MMM-yy" value="${transactionForm.item.created}"/>
                </td>
            </tr>
            <tr>
                <td></td>
                <td class="title">Transaction Type:</td>
                <td class="field_value">
                    ${transactionTypes[transactionForm.item.type]}
                </td>
            </tr>
                <tr>
                    <td></td>
                    <td class="title">Invoice No.:</td>
                    <td class="field_value">
                            ${transactionForm.item.invoice.refNumber}
                    </td>
                </tr>
            <tr>
                <td></td>
                <td class="title">Client:</td>
                <td class="field_value">
                    ${transactionForm.item.client.name}
                </td>
                <td></td>
                <td class="title">Client Ref.:</td>
                <td class="field_value">
                    ${transactionForm.item.clientRef}
                </td>
            </tr>


            <tr>
                <td class="" colspan="6">
                    <fieldset>
                        <legend>Deposit</legend>
                        <table class="" style="width: 100%; font-size: 9pt">
                            <thead>
                                <tr>
                                    <th style="width: 15%">Deposit Ref.</th>
                                    <th style="width: 15%">Date</th>
                                    <th style="width: 15%">Location</th>
                                    <th style="width: 15%">Grade</th>
                                    <th style="width: 8%">Tons</th>
                                    <th style="width: 8%">Cost</th>
                                    <th style="width: 8%">Total</th>
                                    <th style="width: 8%">Yield</th>
                                    <th style="width: 8%"></th>
                                </tr>
                            </thead>
                            <tbody>
                            <c:set var="depositTotalTons" value="${0}"/>
                            <c:set var="depositTotal" value="${0}"/>
                            <c:forEach var="item" items="${transactionForm.item.depositTransactionItems}">
                                <c:set var="depositTotalTons" value="${depositTotalTons + item.tons}" />
                                <c:set var="depositTotal" value="${depositTotal + (item.tons*item.cost)}" />
                            </c:forEach>
                            <c:forEach var="itemDeposit" items="${transactionForm.item.depositTransactionItems}" varStatus="status">
                                    <tr>
                                        <td style="text-align: center">
                                            <c:if test="${itemDeposit.transaction.type eq 1}">
                                                <a href="delivery-v2.htm?id=${itemDeposit.transaction.di.id}">${itemDeposit.instRef}</a>
                                            </c:if>
                                            <c:if test="${itemDeposit.transaction.type eq 2}">
                                                <a href="processing-v2.htm?id=${itemDeposit.transaction.pi.id}">${itemDeposit.instRef}</a>
                                            </c:if>
                                        </td>
                                        <td style="text-align: center"><fmt:formatDate
                                                pattern="dd-MMM-yy HH:mm:ss" value="${itemDeposit.created}"/></td>
                                        <td style="text-align: center">${itemDeposit.location.name}</td>
                                        <td style="text-align: left">${itemDeposit.grade.name}</td>
                                        <td style="text-align: right"><fmt:formatNumber type="number" minFractionDigits="3" maxFractionDigits="3" value="${itemDeposit.tons}"/></td>
                                        <fmt:formatNumber value="${itemDeposit.cost}"
                                                        maxFractionDigits="2"
                                                        minFractionDigits="2"
                                                          groupingUsed="false"
                                                        var="theFormattedCostInput" />
                                        <td style="text-align: center"><form:input style="width: 70px; text-align: right" cssClass="numberOnly" value="${theFormattedCostInput}" path="item.depositTransactionItems[${status.index}].cost"/></td>
                                        <form:hidden path="item.depositTransactionItems[${status.index}].id"/>
                                        <td style="text-align: right"><fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${itemDeposit.cost*itemDeposit.tons}"/></td>
                                        <td style="text-align: right"><fmt:formatNumber type="percent" minFractionDigits="2" maxFractionDigits="2" value="${itemDeposit.tons/depositTotalTons}"/></td>
                                        <td></td>
                                    </tr>
                            </c:forEach>
                            </tbody>
                            <tfoot>
                                <tr>
                                    <th colspan="4"></th>
                                    <th style="text-align: right"><fmt:formatNumber type="number" minFractionDigits="3" maxFractionDigits="3" value="${depositTotalTons}"/></th>
                                    <th></th>
                                    <th style="text-align: right"><fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${depositTotal}"/></th>
                                    <th style="text-align: right"><fmt:formatNumber type="percent" minFractionDigits="2" maxFractionDigits="2" value="${depositTotalTons/depositTotalTons}"/></th>
                                </tr>
                            </tfoot>
                        </table>
                    </fieldset>
                </td>
            </tr>

            <tr>
                <td class="" colspan="6">
                    <fieldset>
                        <legend>Withdraw</legend>
                        <table class="" style="width: 100%; font-size: 9pt">
                            <thead>
                                <tr>
                                    <th style="width: 15%">Withdraw Ref.</th>
                                    <th style="width: 15%">Date</th>
                                    <th style="width: 15%">Location</th>
                                    <th style="width: 15%">Grade</th>
                                    <th style="width: 8%">Tons</th>
                                    <th style="width: 8%">Cost</th>
                                    <th style="width: 8%">Total</th>
                                    <th style="width: 8%">Yield</th>
                                    <th style="width: 8%"></th>
                                </tr>
                            </thead>
                            <tbody>
                            <c:set var="withdrawTotalTons" value="${0}"/>
                            <c:set var="withdrawTotal" value="${0}"/>
                            <c:forEach var="itemWithdraw" items="${transactionForm.item.withdrawTransactionItems}" varStatus="status">

                                    <tr>
                                        <td style="text-align: center">
                                        <c:if test="${itemWithdraw.transaction.type eq 2}">
                                            <a href="processing-v2.htm?id=${itemWithdraw.transaction.pi.id}">${itemWithdraw.instRef}</a>
                                        </c:if>
                                        <c:if test="${itemWithdraw.transaction.type eq 3}">
                                            <a href="shipping-v2.htm?id=${itemWithdraw.transaction.si.id}">${itemWithdraw.instRef}</a> - ${f:formatMoneyWithComma(itemWithdraw.transaction.si.costToFob)}
                                        </c:if>
                                        </td>
                                        <td style="text-align: center"><fmt:formatDate
                                                pattern="dd-MMM-yy HH:mm:ss" value="${itemWithdraw.created}"/></td>
                                        <td style="text-align: center">${itemWithdraw.location.name}</td>
                                        <td style="text-align: left">${itemWithdraw.grade.name}</td>
                                        <td style="text-align: right"><fmt:formatNumber type="number" minFractionDigits="3" maxFractionDigits="3" value="${itemWithdraw.tons}"/></td>
                                        <fmt:formatNumber value="${itemWithdraw.cost}"
                                                          maxFractionDigits="2"
                                                          minFractionDigits="2"
                                                          var="theFormattedCostInput" />
                                        <td style="text-align: center"><form:input style="width: 50px; text-align: right" type="number" value="${theFormattedCostInput}" path="item.withdrawTransactionItems[${status.index}].cost"/></td>
                                        <form:hidden path="item.withdrawTransactionItems[${status.index}].id"/>
                                        <td style="text-align: right"><fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${itemWithdraw.cost*itemWithdraw.tons}"/></td>
                                        <td style="text-align: right"><fmt:formatNumber type="percent" minFractionDigits="2" maxFractionDigits="2" value="${itemWithdraw.tons/depositTotalTons}"/></td>
                                        <td></td>
                                    </tr>
                                <c:set var="withdrawTotalTons" value="${withdrawTotalTons + itemWithdraw.tons}" />
                                <c:set var="withdrawTotal" value="${withdrawTotal + (itemWithdraw.tons*itemWithdraw.cost)}" />
                            </c:forEach>
                            </tbody>
                            <tfoot>
                            <tr>
                                <th colspan="4"></th>
                                <th style="text-align: right"><fmt:formatNumber type="number" minFractionDigits="3" maxFractionDigits="3" value="${withdrawTotalTons}"/></th>
                                <th></th>
                                <th style="text-align: right"><fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${withdrawTotal}"/></th>
                                <th style="text-align: right"><fmt:formatNumber type="percent" minFractionDigits="2" maxFractionDigits="2" value="${withdrawTotalTons/depositTotalTons}"/></th>
                            </tr>
                            </tfoot>
                        </table>
                    </fieldset>
                </td>
            </tr>

            <tr>
                <td class="" colspan="6">
                    <fieldset>
                        <legend>Weight Loss</legend>
                        <table class="" style="width: 100%; font-size: 9pt">
                            <thead>
                            <tr>
                                <th style="width: 15%"></th>
                                <th style="width: 15%"></th>
                                <th style="width: 15%"></th>
                                <th style="width: 15%"></th>
                                <th style="width: 8%">Tons</th>
                                <th style="width: 8%"></th>
                                <th style="width: 8%"></th>
                                <th style="width: 8%">Yield</th>
                                <th style="width: 8%"></th>
                            </tr>
                            </thead>

                            <tfoot>
                            <tr>
                                <th colspan="2"></th>
                                <th colspan="2" style="text-align: right">Storage Wt. Loss:</th>
                                <th style="text-align: right"><fmt:formatNumber type="number" minFractionDigits="3" maxFractionDigits="3" value="${transactionForm.item.storageWeightLoss}"/></th>
                                <th colspan="2"></th>
                                <th style="text-align: right"><fmt:formatNumber type="percent" minFractionDigits="2" maxFractionDigits="2" value="${transactionForm.item.storageWeightLoss/depositTotalTons}"/></th>
                                <th></th>
                            </tr>
                            <tr>
                                <th colspan="2"></th>
                                <th colspan="2" style="text-align: right">Processing Wt. Loss:</th>
                                <th style="text-align: right"><fmt:formatNumber type="number" minFractionDigits="3" maxFractionDigits="3" value="${transactionForm.item.processingWeightLoss}"/></th>
                                <th colspan="2"></th>
                                <th style="text-align: right"><fmt:formatNumber type="percent" minFractionDigits="2" maxFractionDigits="2" value="${transactionForm.item.processingWeightLoss/depositTotalTons}"/></th>
                                <th style="text-align: right"></th>
                            </tr>
                            <tr>
                                <th colspan="2"></th>
                                <th colspan="2" style="text-align: right">Total</th>
                                <th style="text-align: right"><fmt:formatNumber type="number" minFractionDigits="3" maxFractionDigits="3" value="${transactionForm.item.storageWeightLoss + transactionForm.item.processingWeightLoss}"/></th>
                                <th colspan="2"></th>
                                <th style="text-align: right"><fmt:formatNumber type="percent" minFractionDigits="2" maxFractionDigits="2" value="${(transactionForm.item.storageWeightLoss/depositTotalTons) + (transactionForm.item.processingWeightLoss/depositTotalTons)}"/></th>
                                <th style="text-align: right"></th>
                            </tr>
                            </tfoot>
                        </table>
                    </fieldset>
                </td>
            </tr>


                <form:hidden path="item.id"/>
            <tr>
                <td></td>
                <td class="title">Remark:</td>
                <td class="field_value" colspan="4">
                    <form:textarea path="item.remark"/>
                </td>
            </tr>
            <tr>
                <td class="title" colspan="2"></td>
                <td class="field_value" colspan="4">
                    <c:if test="${permissonsMap.get('perm_127')}">
                    <input
                        class="button ui-button ui-widget ui-state-default ui-corner-all" type="submit"
                        value="Save" onclick="disableSubmitAfterClick(this)"/>
                    </c:if>
                </td>
            </tr>
            </form:form>
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
    }).on("click", "#btn_print", function() {
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

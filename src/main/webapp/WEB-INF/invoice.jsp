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
    <title>Invoice Detail</title>
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
    <script type="text/javascript">
        function onChangeTest(textbox) {
            alert("Value is " + textbox.value + "\n" + "Old Value is " + textbox.oldvalue);
        }
    </script>
</head>
<body>
<jsp:include page="header.jsp">
    <jsp:param name="url" value="67"/>
    <jsp:param name="page" value="115"/>
</jsp:include>
<div style="width: 1200px ! important; padding: 15px; background: none repeat scroll 0% 0% white; height: auto; overflow: hidden"
     class="container_16 border" id="wrapper">

    <div id="detail_content">
    <div style="width: 1200px !important; min-height: 80px; height: auto; overflow: hidden;margin-right: 9px;"
         class="">
        <table class="instructionDetailStandard" style="padding: 20px; width: 100%">
            <form:form action="invoice_saveApprovalStatus.htm" method="post" commandName="invoiceForm">
                <tr>
                    <td></td>
                    <td class="title">Approval Status:</td>
                    <td class="field_value" colspan="3">
                        <form:select path="item.approvalStatus"
                                     style="width: 30%">
                            <form:options items="${approvalStatuses}"/>
                        </form:select>
                        <label>${invoiceForm.item.approvalUser.userName}:<fmt:formatDate
                                pattern="dd-MMM-yy HH:mm:ss"
                                value="${invoiceForm.item.approvalDate}"/></label>
                        <c:if test="${permissonsMap.get('perm_128')}">
                        <input class="button ui-button ui-widget ui-state-default ui-corner-all" type="submit"
                               value="Save" onclick="disableSubmitAfterClick(this)"/>
                        </c:if>
                    </td>
                </tr>
            </form:form>
            <form:form action="invoice_saveCompletionStatus.htm" method="post" commandName="invoiceForm">
                <tr>
                    <td></td>
                    <td class="title">Completion Status:</td>
                    <td class="field_value" colspan="3">
                        <c:if test="${invoiceForm.item.completionStatus eq 0}">
                            <form:select path="item.completionStatus"
                                         style="width: 30%">
                                <form:options items="${completionStatuses}"/>
                            </form:select>
                            <label>${invoiceForm.item.completionUser.userName}:<fmt:formatDate
                                    pattern="dd-MMM-yy HH:mm:ss"
                                    value="${invoiceForm.item.completionDate}"/></label>
                            <c:if test="${permissonsMap.get('perm_129')}">
                            <input class="button ui-button ui-widget ui-state-default ui-corner-all" type="submit"
                                   value="Save" onclick="disableSubmitAfterClick(this)"/>
                            </c:if>
                        </c:if>
                        <c:if test="${invoiceForm.item.completionStatus eq 1}">
                            ${completionStatuses[invoiceForm.item.completionStatus]} --
                            <label>${invoiceForm.item.completionUser.userName}:<fmt:formatDate
                                    pattern="dd-MMM-yy HH:mm:ss"
                                    value="${invoiceForm.item.completionDate}"/></label>
                        </c:if>
                    </td>
                </tr>
            </form:form>
            <tr>
                <td class="subTitle" colspan="3">Invoice Detail</td>
                <td class="subTitle" colspan="3"></td>
            </tr>
            <tr>
                <td></td>
                <td class="title">Invoice Ref.:</td>
                <td class="field_value">
                    ${invoiceForm.item.refNumber}
                </td>
                <td></td>
                <td class="title">Day overdue</td>
                <td class="field_value" style="color: red">
                    ${daysOverDue}
                    <%--<jsp:useBean id="now" class="java.util.Date"/>--%>
                    <%--<fmt:parseNumber--%>
                            <%--value="${ dateForCalculatingDateOverDue.time / (1000*60*60*24) }"--%>
                            <%--integerOnly="true" var="nowDays" scope="request"/>--%>
                    <%--<fmt:parseNumber--%>
                            <%--value="${ invoiceForm.item.dueDate.time / (1000*60*60*24) }"--%>
                            <%--integerOnly="true" var="dueDays"/>--%>
                    <%--<c:set value="${nowDays - dueDays + 1}" var="dateDiff"/>--%>
                    <%--<c:choose>--%>
                    <%--<c:when test="${dateDiff < 0}">-</c:when>--%>
                    <%--<c:otherwise>${dateDiff}</c:otherwise>--%>
                    <%--</c:choose>--%>
                </td>
            </tr>
            <tr>
                <td></td>
                <td class="title">Invoice Date:</td>
                <td class="field_value">
                    <fmt:formatDate pattern="dd-MMM-yy" value="${invoiceForm.item.created}"/>
                </td>
            </tr>
            <tr>
                <td></td>
                <td class="title">Invoice Due Date:</td>
                <td class="field_value">
                    <fmt:formatDate pattern="dd-MMM-yy" value="${invoiceForm.item.dueDate}"/>
                </td>
            </tr>
            <tr>
                <td></td>
                <td class="title">Invoice Amount:</td>
                <td class="field_value">
                    <fmt:formatNumber type="number" minFractionDigits="2"
                                      maxFractionDigits="2"
                                      value="${result.total}"/>
                </td>
            </tr>
            <tr>
                <td class="subTitle" colspan="3">Client Detail</td>
            </tr>
            <form:form action="invoice_saveCommonInfo.htm" method="post" commandName="invoiceForm" id='commonForm'>
            <tr>
                <td></td>
                <td class="title">Client:</td>
                <td class="field_value">
                    ${invoiceForm.item.client.name}
                </td>
                <td></td>
                <td class="title">Beneficiary:</td>
                <td class="field_value">
                    <form:select id='select-bankAccount' path="item.bankAccount"
                                 items="${banks}"
                                 itemValue="id" itemLabel="bank.name">
                        
                        
                    </form:select>
                    
                </td>
            </tr>
            <tr>
                <td></td>
                <td class="title">Client Ref.:</td>
                <td class="field_value">
                    ${invoiceForm.item.clientRef}
                </td>
                <td></td>
                <td class="title">Account No.:</td>
                <td class="field_value">
                    <input value="${invoiceForm.item.bankAccount.bank.name}" disabled="" id="beneficiary-account-no" />
                </td>
            </tr>
            <tr >
                <td></td>
                <td class="title"></td>
                <td class="field_value">

                </td>
                <td></td>
                <td class="title">Transfer Ins..:</td>
                <td class="field_value">
                    <textarea disabled="" id="beneficiary-account-transferIns">${invoiceForm.item.bankAccount.transferInfo}</textarea>
                    
                </td>
            </tr>
            <tr>
                <td></td>
                <td class="title"></td>
                <td class="field_value">
                    <form:textarea disabled="" id="remark" path="item.remark" style="display: none;" />
                </td>
                <td></td>
                <td class="title" colspan="3"></td>
                <td class="field_value" colspan="4">
                    
                </td>
            </tr>
            </form:form>
                <%--Payments section--%>
                <tr>
                    <td class="" colspan="6">
                        <fieldset>
                            <legend>Payment Details</legend>
                            <table class="" style="width: 100%; font-size: 9pt">
                                <thead>
                                <tr>
                                    <th style="width: 10%">Payment ref</th>
                                    <th style="width: 12%">Date</th>
                                    <th style="width: 15%">Bank</th>
                                    <th style="width: 15%">Acc No.</th>
                                    <th style="width: 8%">Amt. Paid</th>
                                    <th style="width: 8%">Balance due</th>
                                    <th style="width: 16%">Authorized by</th>
                                    <th style="width: 8%"></th>
                                </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="payment" items="${result.payments}">
                                    <form action="invoice/updatePayment.htm" method="POST">
                                    <tr>
                                        <tr>
                                            <td style="text-align: center">${payment.refNumber}</td>
                                            <td style="text-align: center">
                                                <fmt:formatDate pattern="dd-MMM-yy" value="${payment.paidDate}" var="_paidDate"/>
                                                <input class="js_date" type="text" value="${_paidDate}" name="paidDate" >
                                            </td>
                                            <td style="text-align: center">
                                                <input type="hidden" name="id" value="${payment.id}" />
                                                <input type="text" name="bank" value="${payment.bank}" />
                                            </td>
                                            <td style="text-align: center"><input type="text" name="accountNumber" value="${payment.accountNumber}" /></td>
                                            <td style="text-align: center">
                                                <fmt:formatNumber value="${payment.amount}"
                                                                  maxFractionDigits="2"
                                                                  minFractionDigits="2"
                                                                  groupingUsed="false"
                                                                  var="theFormattedPaymentAmount" />
                                                <input style="text-align: right" class="numberOnly" name="amount" value="${theFormattedPaymentAmount}" />
                                            </td>
                                            <td style="text-align: right"><fmt:formatNumber type="number" minFractionDigits="2"
                                                                  maxFractionDigits="2"
                                                                  value="${payment.remaining}"/></td>
                                            <td><label>${payment.approvalUser.userName}:<fmt:formatDate
                                                    pattern="dd-MMM-yy HH:mm:ss"
                                                    value="${payment.updated}"/></label></td>
                                            <td>
                                                <c:if test="${permissonsMap.get('perm_131')}">
                                                <input type="submit" value="Save"/>
                                                </c:if>
                                            </td>
                                        </tr>
                                    </tr>
                                    </form>
                                    </c:forEach>
                                </tbody>
                                <tbody id="payment-new" style='display: none;'>
                                    <form:form method='POST' action='invoice/addNewPayment.htm' commandName="paymentForm">
                                        <tr>
                                            <td style="text-align: center">&lt;generated after saving&gt;</td>
                                            <td style="text-align: center"><form:input type="text" path="item.paidDate" cssClass="js_date" /></td>
                                            <td style="text-align: center"><form:input type="text" path="item.bank" /></td>
                                            <td style="text-align: center"><form:input type="text" path="item.accountNumber" /></td>
                                            <td style="text-align: center"><form:input  style="text-align: right" cssClass="numberOnly" path="item.amount" /></td>
                                            <td><form:hidden path="item.invoice" value="${invoiceForm.item.id}" /></td>
                                            <td><input type="submit" value="Save" /></td>
                                            <td></td>
                                        </tr>
                                    </form:form>
                                </tbody>
                                <tfoot>
                                <tr>
                                    <td colspan="3">
                                        <c:if test="${permissonsMap.get('perm_130')}">
                                        <button id="payment-new-button">Add Pmt. Record</button>
                                        </c:if>
                                    </td>
                                    <th>Total:</th>
                                    <th style="text-align: right"><fmt:formatNumber type="number" minFractionDigits="2"
                                                                                    maxFractionDigits="2"
                                                                                    value="${result.totalPayment}"/></th>
                                    <td colspan="3"></td>
                                </tr>
                                </tfoot>
                            </table>
                        </fieldset>
                    </td>
                </tr>

                <%--Deposit section--%>
                <tr>
                    <td class="" colspan="6">
                        <fieldset>
                            <legend>Deposit</legend>
                            <table class="" style="width: 100%; font-size: 9pt">
                                <thead>
                                <tr>
                                    <th style="width: 10%">DI Ref.</th>
                                    <th style="width: 12%">Date</th>
                                    <th style="width: 15%">Location</th>
                                    <th style="width: 15%">Grade</th>
                                    <th style="width: 8%">Tons</th>
                                    <th style="width: 8%">Cost</th>
                                    <th style="width: 16%">Total</th>
                                    <th style="width: 8%"></th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:set var="depositTotalTons" value="${0}"/>
                                <c:set var="depositTotal" value="${0}"/>
                                <c:forEach var="itemDeposit" items="${deposits}">
                                    <tr>
                                        <td style="text-align: center">
                                            <a href="transaction.htm?id=${itemDeposit.id}">${itemDeposit.insRef}</a>
                                        </td>
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
                                        <td></td>
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
                            <table class="" style="width: 100%; font-size: 9pt">
                                <thead>
                                <tr>
                                    <th style="width: 10%">Upgrade Ref.</th>
                                    <th style="width: 12%">Date</th>
                                    <th style="width: 15%">Location</th>
                                    <th style="width: 15%">PI Type</th>
                                    <th style="width: 8%">Tons</th>
                                    <th style="width: 8%">Cost</th>
                                    <th style="width: 16%">Total</th>
                                    <th style="width: 8%"></th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:set var="upgradeTotalTons" value="${0}"/>
                                <c:set var="upgradeTotal" value="${0}"/>
                                <c:forEach var="itemUpgrade" items="${upgrades}">
                                    <tr>
                                        <td style="text-align: center">
                                            <a href="transaction.htm?id=${itemUpgrade.id}">${itemUpgrade.insRef}</a>
                                        </td>
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
                                        <td></td>
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
                            <table class="" style="width: 100%; font-size: 9pt">
                                <thead>
                                <tr>
                                    <th style="width: 10%">SI Ref.</th>
                                    <th style="width: 12%">Date</th>
                                    <th style="width: 15%">Location</th>
                                    <th style="width: 15%">Grade</th>
                                    <th style="width: 8%">Tons</th>
                                    <th style="width: 8%">Cost</th>
                                    <th style="width: 16%">Total</th>
                                    <th style="width: 8%"></th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:set var="withdrawTotalTons" value="${0}"/>
                                <c:set var="withdrawTotal" value="${0}"/>
                                <c:forEach var="itemWithdraw" items="${withdraws}">
                                    <tr>
                                        <td style="text-align: center">

                                            <a href="transaction.htm?id=${itemWithdraw.id}">${itemWithdraw.insRef}</a>
                                        </td>
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
                                        <td></td>
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

                <%--Others section--%>
            <tr>
                <td class="" colspan="6">
                    <fieldset>
                        <legend>Others</legend>
                        <table class="" style="width: 100%; font-size: 9pt">
                            <thead>
                            <tr>
                                <th style="width: 10%">Other ref</th>
                                <th style="width: 12%">Date</th>
                                <th colspan="2" style="width: 30%">Description</th>
                                <th style="width: 8%">Tons</th>
                                <th style="width: 8%">Cost</th>
                                <th style="width: 16%">Total</th>
                                <th style="width: 8%"></th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="otherTransaction" items="${result.otherTransactions}">
                                <form action="updateOtherTransaction.htm" method="POST">
                                    <tr>
                                        <td><input type="text" name="refNumber" value="${otherTransaction.refNumber}" /></td>
                                        <td style="text-align: center"><fmt:formatDate pattern="dd-MMM-yy HH:mm:ss" value="${otherTransaction.created}"/></td>
                                        <td colspan="2">
                                            <input type="hidden" name="id" value="${otherTransaction.id}" />
                                            <input style="width: 100%" type="text" name="description" value="${otherTransaction.description}" />
                                        </td>
                                        <td style="text-align: center">
                                            <fmt:formatNumber value="${otherTransaction.tons}"
                                                              maxFractionDigits="3"
                                                              minFractionDigits="3"
                                                              groupingUsed="false"
                                                              var="theFormattedOtherTransactionTons" />
                                            <input class="numberOnly" style="text-align: right" name="tons" value="${theFormattedOtherTransactionTons}" />
                                        </td>
                                        <td style="text-align: center">
                                            <fmt:formatNumber value="${otherTransaction.cost}"
                                                              maxFractionDigits="2"
                                                              minFractionDigits="2"
                                                              groupingUsed="false"
                                                              var="theFormattedOtherTransactionCost" />
                                            <input class="numberOnly" style="text-align: right" name="cost" value="${theFormattedOtherTransactionCost}" />
                                        </td>
                                        <td style="text-align: right"><fmt:formatNumber type="number" minFractionDigits="2"
                                                                                        maxFractionDigits="2"
                                                                                        value="${otherTransaction.total}"/>
                                        </td>
                                        <td>
                                            <c:if test="${permissonsMap.get('perm_133')}">
                                            <input type="submit" value="Save"/>
                                            </c:if>
                                        </td>
                                    </tr>
                                </form>
                            </c:forEach>
                            </tbody>
                            <tbody id="otherTransaction-new" style='display: none;'>
                            <form:form method='POST' action='addNewOtherTransaction.htm' commandName="otherTransactionForm">
                                <tr>
                                    <td><form:input type="text" path="item.refNumber" /></td>
                                    <td style="text-align: center">&lt;generated after saving&gt;</td>
                                    <td colspan="2">
                                        <form:hidden path="item.invoice" value="${invoiceForm.item.id}" />
                                        <form:input type="text" cssStyle="width: 100%" path="item.description"/>
                                    </td>
                                    <td style="text-align: center">
                                        <form:input type="text" cssClass="numberOnly" style="text-align: right" path="item.tons"/>
                                    </td>
                                    <td style="text-align: center">
                                        <form:input type="text" cssClass="numberOnly" style="text-align: right" path="item.cost"/>
                                    </td>
                                    <td style="text-align: right"></td>
                                    <td><input type="submit" value="Save"/></td>
                                </tr>
                            </form:form>
                            </tbody>
                            <tfoot>
                            <tr>
                                <th style="text-align: left" colspan="3">
                                    <c:if test="${permissonsMap.get('perm_132')}">
                                    <button id="otherTransaction-new-button">Add Others</button>
                                    </c:if>
                                </th>
                                <th colspan="2"></th>
                                <th>
                                    Total:
                                </th>
                                <th style="text-align: right"><fmt:formatNumber type="number" minFractionDigits="2"
                                                                                maxFractionDigits="2"
                                                                                value="${result.totalOther}"/>
                                </th>
                                <th></th>
                            </tr>
                            </tfoot>
                        </table>
                    </fieldset>
                </td>
            </tr>
            <tr>
                <td></td>
                <td class="title">Remark:</td>
                <td class="field_value" colspan="4">
                    <textarea style="width: 757px; height: 87px" id="remarkDuplicate">${invoiceForm.item.remark}</textarea>
                </td>
            </tr>
            <tr>
                <td class="title" colspan="3"></td>
                <td class="field_value" colspan="4">
                    <c:if test="${permissonsMap.get('perm_134')}">
                    <input
                        class="button ui-button ui-widget ui-state-default ui-corner-all" type="button"
                        value="Save" id="saveRealForm"/>
                    </c:if>
                </td>
            </tr>
            <tr>
                <td class="title" colspan="3"></td>
                <td class="field_value" colspan="4">
                    <c:if test="${permissonsMap.get('perm_135')}">
                    <input
                            class="button ui-button ui-widget ui-state-default ui-corner-all" type="button"
                            value="Export Invoice" id="btnExportInvoice"/>
                    </c:if>
                </td>
            </tr>
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
    }).on("click", "#btnExportInvoice", function() {
        $.sendRequest({
            status: "export",
            action: "getInvoiceExcel",
            data: {
                "invoiceId": '${invoiceForm.item.id}'
            }
        }, function(msg) {
            return true;
        });
    }).on("click", "#payment-new-button", function() {
        $("#payment-new").show();
    }).on("click", "#otherTransaction-new-button", function() {
        $("#otherTransaction-new").show();
    }).on("change", "#select-bankAccount", function() {
        var id = $(this).val();
        $.ajax({
            type: 'GET',
            async: false,
            dataType: 'json',
            url: getAbsolutePath() + "/banks/{0}.json".format(id),
            success: function (data) {
               $("#beneficiary-account-no").val(data.accountNumber);
               $("#beneficiary-account-transferIns").val(data.transferInfo);
            }
        });
    }).on("change keyup paste", "#remarkDuplicate", function() {
        console.log($(this).val());
        $("#remark").val($(this).val());
    }).on("click", "#saveRealForm", function() {
        $(this).attr("disabled", "disabled");
        $("#commonForm").submit();
    });


</script>
<jsp:include page="footer.jsp"/>

</body>
</html>

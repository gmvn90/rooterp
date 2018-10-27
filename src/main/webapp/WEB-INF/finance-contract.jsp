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
    <title>Finance Contract Detail</title>
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
    <jsp:param name="url" value="70"/>
    <jsp:param name="page" value="118"/>
</jsp:include>
<div style="width: 1200px ! important; padding: 15px; background: none repeat scroll 0% 0% white; height: auto; overflow: hidden"
     class="container_16 border" id="wrapper">

    <div id="detail_content">
    <div style="width: 1200px !important; min-height: 80px; height: auto; overflow: hidden;margin-right: 9px;"
         class="">
        <table class="instructionDetailStandard" style="padding: 20px; width: 100%">
            <c:if test="${is_form_edit}">
            <form:form action="financeContract_saveApprovalStatus.htm" method="post" commandName="financeContractForm">
                <tr>
                    <td></td>
                    <td class="title">Approval Status:</td>
                    <td class="field_value" colspan="3">
                        <form:select path="item.approvalStatus"
                                     style="width: 30%">
                            <form:options items="${approvalStatuses}"/>
                        </form:select>
                        <label>${financeContractForm.item.approvalUser.userName}:<fmt:formatDate
                                pattern="dd-MMM-yy HH:mm:ss"
                                value="${financeContractForm.item.approvalDate}"/></label>
                        <c:if test="${permissonsMap.get('perm_136')}">
                        <input class="button ui-button ui-widget ui-state-default ui-corner-all" type="submit"
                               value="Save" onclick="disableSubmitAfterClick(this)"/>
                        </c:if>
                    </td>
                </tr>
            </form:form>
            <form:form action="financeContract_saveCompletionStatus.htm" method="post" commandName="financeContractForm">
                <tr>
                    <td></td>
                    <td class="title">Completion Status:</td>
                    <td class="field_value" colspan="3">
                        <c:if test="${financeContractForm.item.completionStatus eq 0}">
                            <form:select path="item.completionStatus"
                                         style="width: 30%">
                                <form:options items="${completionStatuses}"/>
                            </form:select>
                            <label>${financeContractForm.item.completionUser.userName}:<fmt:formatDate
                                    pattern="dd-MMM-yy HH:mm:ss"
                                    value="${financeContractForm.item.completionDate}"/></label>
                            <c:if test="${permissonsMap.get('perm_137')}">
                            <input class="button ui-button ui-widget ui-state-default ui-corner-all" type="submit"
                                   value="Save" onclick="disableSubmitAfterClick(this)"/>
                            </c:if>
                        </c:if>
                        <c:if test="${financeContractForm.item.completionStatus eq 1}">
                            ${completionStatuses[financeContractForm.item.completionStatus]} --
                            <label>${financeContractForm.item.completionUser.userName}:<fmt:formatDate
                                    pattern="dd-MMM-yy HH:mm:ss"
                                    value="${financeContractForm.item.completionDate}"/></label>
                        </c:if>
                    </td>
                </tr>
            </form:form>

            <tr>
                <td class="subTitle" colspan="3">Finance Detail</td>
                <td class="subTitle" colspan="3"></td>
            </tr>
            <tr>
                <td></td>
                <td class="title">Finance Ref.:</td>
                <td class="field_value">
                    ${financeContractForm.item.refNumber}
                </td>
                <td></td>
                <td class="title">Day overdue</td>
                <td class="field_value" style="color: red">${financeContractForm.item.daysOverdue}
                </td>
            </tr>
            <tr>
                <td></td>
                <td class="title">Finance Date:</td>
                <td class="field_value">
                    <fmt:formatDate pattern="dd-MMM-yy" value="${financeContractForm.item.created}"/>
                </td>
            </tr>
            <tr>
                <td></td>
                <td class="title">Finance Due Date:</td>
                <td class="field_value">
                    <fmt:formatDate pattern="dd-MMM-yy" value="${financeContractForm.item.dueDate}"/>
                </td>
            </tr>
            <tr>
                <td></td>
                <td class="title">Balance unpaid:</td>
                <td class="field_value">
                        ${f:formatMoneyWithComma(balancedUnpaid)}
                </td>
            </tr>

            <%--Payments section--%>
            <tr>
                <td class="" colspan="6">
                    <fieldset>
                        <legend>Payment Details</legend>
                        <table class="" style="width: 100%; font-size: 9pt">
                            <thead>
                            <tr>
                                <th style="width: 10%">Principle:</th>
                                <th style="width: 12%"></th>
                                <th style="width: 15%"></th>
                                <th style="width: 15%"></th>
                                <th style="width: 8%"></th>
                                <th style="width: 8%; text-align: right">${f:formatMoneyWithComma(financeContractForm.item.totalFinanced)}</th>
                            </tr>
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
                            <c:forEach var="payment" items="${payments}">
                                <form action="finance/updatePayment.htm" method="POST">
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
                                            <c:if test="${permissonsMap.get('perm_139')}">
                                            <input type="submit" value="Save"/>
                                            </c:if>
                                        </td>
                                    </tr>
                                    </tr>
                                </form>
                            </c:forEach>
                            </tbody>
                            <tbody id="payment-new" style='display: none;'>
                            <form:form method='POST' action='finance/addNewPayment.htm' commandName="paymentForm">
                                <tr>
                                    <td style="text-align: center">&lt;generated after saving&gt;</td>
                                    <td style="text-align: center"><form:input type="text" path="item.paidDate" cssClass="js_date" /></td>
                                    <td style="text-align: center"><form:input type="text" path="item.bank" /></td>
                                    <td style="text-align: center"><form:input type="text" path="item.accountNumber" /></td>
                                    <td style="text-align: center"><form:input  style="text-align: right" cssClass="numberOnly" path="item.amount" /></td>
                                    <td><form:hidden path="item.financeContract" value="${financeContractForm.item.id}" /></td>
                                    <td><input type="submit" value="Save" /></td>
                                    <td></td>
                                </tr>
                            </form:form>
                            </tbody>
                            <tfoot>
                            <tr>
                                <td colspan="3">
                                    <c:if test="${permissonsMap.get('perm_138')}">
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
            <!-- end Payment Section -->

                <%--Interest Payments section--%>
                <tr>
                    <td class="" colspan="6">
                        <fieldset>
                            <legend>Interest Payment Details</legend>

                            <table class="" style="width: 100%; font-size: 9pt">
                                <thead>
                                <tr>
                                    <th style="width: 10%">Principle:</th>
                                    <th style="width: 12%"></th>
                                    <th style="width: 15%"></th>
                                    <th style="width: 15%"></th>
                                    <th style="width: 8%"></th>
                                    <th style="width: 8%; text-align: right">${f:formatMoneyWithComma(financeContractForm.item.interestIncome)}</th>
                                </tr>
                                <tr>
                                    <th style="width: 10%">Int. Pmt. ref</th>
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
                                <c:forEach var="interestPayment" items="${interestPayments}">
                                    <form action="finance/updateInterestPayment.htm" method="POST">
                                        <tr>
                                        <tr>
                                            <td style="text-align: center">${interestPayment.refNumber}</td>
                                            <td style="text-align: center"><fmt:formatDate pattern="dd-MMM-yy HH:mm:ss" value="${interestPayment.created}"/></td>
                                            <td style="text-align: center">
                                                <input type="hidden" name="id" value="${interestPayment.id}" />
                                                <input type="text" name="bank" value="${interestPayment.bank}" />
                                            </td>
                                            <td style="text-align: center"><input type="text" name="accountNumber" value="${interestPayment.accountNumber}" /></td>
                                            <td style="text-align: center">
                                                <fmt:formatNumber value="${interestPayment.amount}"
                                                                  maxFractionDigits="2"
                                                                  minFractionDigits="2"
                                                                  groupingUsed="false"
                                                                  var="theFormattedInterestPaymentAmount" />
                                                <input style="text-align: right" class="numberOnly" name="amount" value="${theFormattedInterestPaymentAmount}" />
                                            </td>
                                            <td style="text-align: right"><fmt:formatNumber type="number" minFractionDigits="2"
                                                                                            maxFractionDigits="2"
                                                                                            value="${interestPayment.remaining}"/></td>
                                            <td><label>${interestPayment.approvalUser.userName}:<fmt:formatDate
                                                    pattern="dd-MMM-yy HH:mm:ss"
                                                    value="${interestPayment.updated}"/></label></td>
                                            <td>
                                                <c:if test="${permissonsMap.get('perm_141')}">
                                                <input type="submit" value="Save"/>
                                                </c:if>
                                            </td>
                                        </tr>
                                        </tr>
                                    </form>
                                </c:forEach>
                                </tbody>
                                <tbody id="interestPayment-new" style='display: none;'>
                                <form:form method='POST' action='finance/addNewInterestPayment.htm' commandName="interestPaymentForm">
                                    <tr>
                                        <td style="text-align: center">&lt;generated after saving&gt;</td>
                                        <td style="text-align: center">&lt;generated after saving&gt;</td>
                                        <td style="text-align: center"><form:input type="text" path="item.bank" /></td>
                                        <td style="text-align: center"><form:input type="text" path="item.accountNumber" /></td>
                                        <td style="text-align: center"><form:input  style="text-align: right" cssClass="numberOnly" path="item.amount" /></td>
                                        <td><form:hidden path="item.financeContract" value="${financeContractForm.item.id}" /></td>
                                        <td><input type="submit" value="Save" /></td>
                                        <td></td>
                                    </tr>
                                </form:form>
                                </tbody>
                                <tfoot>
                                <tr>
                                    <td colspan="3">
                                        <c:if test="${permissonsMap.get('perm_140')}">
                                        <button id="interestPayment-new-button">Add Int. Pmt. Record</button>
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
                <!-- end Interest Payment Section -->

            </c:if>
            <form:form action="finance-contract.htm" method="post" commandName="financeContractForm">
            <tr>
                <td class="subTitle" colspan="3">Client Detail</td>
            </tr>
            <tr>
                <td></td>
                <td class="title">Client:</td>
                <td class="field_value">
                    <form:select path="item.client" id="client"
                                 items="${clients}"
                                 itemValue="id" itemLabel="name"/>
                </td>
                <td></td>
                <td class="title">Beneficiary:</td>
                <td class="field_value">
                    <form:select id='select-bankAccount' path="item.bankAccount"
                                 items="${banks}"
                                 itemValue="id" itemLabel="bank.name"/>
                </td>
            </tr>
            <tr>
                <td></td>
                <td class="title">Client Ref.:</td>
                <td class="field_value">
                    <form:input path="item.clientRef" />
                </td>
                <td></td>
                <td class="title">Account No.:</td>
                <td class="field_value">
                    <input value="${financeContractForm.item.bankAccount.accountNumber}" disabled="" />
                </td>
            </tr>
            <tr>
                <td></td>
                <td class="title"></td>
                <td class="field_value">

                </td>
                <td></td>
                <td class="title">Transfer Ins..:</td>
                <td class="field_value">
                    <input value="${financeContractForm.item.bankAccount.transferInfo}" disabled=""/>
                </td>
            </tr>

                <tr>
                    <td class="subTitle" colspan="3">Collateral Detail</td>
                </tr>
                <tr>
                    <td></td>
                    <td class="title">Origin:</td>
                    <td class="field_value">
                        <form:select path="item.origin" id="origin" items="${origins}"
                                     itemValue="id"
                                     itemLabel="country.shortName"/>
                    </td>
                    <td></td>
                    <td class="title">Location:</td>
                    <td class="field_value">
                        <form:select path="item.location" id="location" items="${locations}"
                                     itemValue="id"
                                     itemLabel="name"/>
                    </td>
                </tr>
                <tr>
                    <td></td>
                    <td class="title">Quality:</td>
                    <td class="field_value">
                        <form:select path="item.quality" id="quality" items="${qualities}"
                                     itemValue="id"
                                     itemLabel="name"/>
                    </td>
                    <td></td>
                    <td class="title">Tons:</td>
                    <td class="field_value">
                        <fmt:formatNumber value="${financeContractForm.item.tons}"
                                          maxFractionDigits="3"
                                          minFractionDigits="3"
                                          var="theFormattedTons" />
                        <form:input cssClass="numberOnly" value="${theFormattedTons}" path="item.tons"/>
                    </td>
                </tr>
                <tr>
                    <td></td>
                    <td class="title">Grade:</td>
                    <td class="field_value">
                        <form:select path="item.grade" id="grade" items="${grades}" itemValue="id"
                                     itemLabel="name"/>
                    </td>
                    <td></td>
                    <td class="title"></td>
                    <td class="field_value">

                    </td>
                </tr>


                <tr>
                    <td class="subTitle" colspan="3">Finance Detail</td>
                </tr>
                    <td></td>
                    <td class="title">Market Price:</td>
                    <td class="field_value">
                        <fmt:formatNumber value="${financeContractForm.item.marketPrice}"
                                          maxFractionDigits="2"
                                          minFractionDigits="2"
                                          pattern="####.##"
                                          var="theFormattedMarketPrice" />
                        <form:input cssClass="numberOnly" value="${theFormattedMarketPrice}" path="item.marketPrice"/>
                    </td>
                    <td></td>
                    <td class="title">Finance Date:</td>
                    <td class="field_value">
                        <fmt:formatDate pattern="dd-MMM-yy" value="${financeContractForm.item.created}" var="_paidDate"/>
                        <form:input path="item.created" value="${_paidDate}" cssClass="js_date"/>
                    </td>
                </tr>
                <tr>
                    <td></td>
                    <td class="title">Quality Diff.:</td>
                    <td class="field_value">
                        <fmt:formatNumber value="${financeContractForm.item.qualityDiff}"
                                          maxFractionDigits="2"
                                          minFractionDigits="2"
                                          pattern="####.##"
                                          var="theFormattedQualityDiff" />
                        <form:input cssClass="numberOnly" value="${theFormattedQualityDiff}" path="item.qualityDiff"/>
                    </td>
                    <td></td>
                    <td class="title">Total Financed:</td>
                    <td class="field_value">
                        <fmt:formatNumber value="${financeContractForm.item.totalFinanced}"
                                          maxFractionDigits="2"
                                          minFractionDigits="2"
                                          var="theFormattedTotalFinanced" />
                        <input value="${theFormattedTotalFinanced}" disabled/>
                    </td>

                </tr>
                <tr>
                    <td></td>
                    <td class="title">Market Value:</td>
                    <td class="field_value">
                        <input type="number" disabled value="${financeContractForm.item.marketValue}">
                    </td>
                    <td></td>
                    <td class="title">Interest Rate P.A.(%):</td>
                    <td class="field_value">
                        <form:input cssClass="numberOnly" path="item.interestRatePA"/>
                    </td>

                </tr>
                <tr>
                    <td></td>
                    <td class="title">Finance Percentage.(%):</td>
                    <td class="field_value">
                        <form:input cssClass="numberOnly" path="item.financePercentage"/>
                    </td>
                    <td></td>
                    <td class="title">Max. Term (Days):</td>
                    <td class="field_value">
                        <form:input cssClass="numberOnly" path="item.maxTermDays"/>
                    </td>

                </tr>
                <tr>
                    <td></td>
                    <td class="title">Provisonal Price:</td>
                    <td class="field_value">
                        <fmt:formatNumber value="${financeContractForm.item.financePrice}"
                                          maxFractionDigits="2"
                                          minFractionDigits="2"
                                          var="theFormattedFinancePrice" />
                        <input disabled value="${theFormattedFinancePrice}">
                    </td>
                    <c:if test="${is_form_edit}">
                        <td></td>
                        <td class="title">Days to Date:</td>
                        <td class="field_value">
                            <input disabled value="${financeContractForm.item.daysToDate}">
                        </td>
                    </c:if>

                </tr>
                <tr>
                    <td></td>
                    <td class="title">Advance & Diff:</td>
                    <td class="field_value">
                           ${f:formatMoneyWithComma(financeContractForm.item.advanceAndDiff)}
                    </td>
                    <td></td>
                    <td class="title">Interest To Date:</td>
                    <td class="field_value">
                        ${f:formatMoneyWithComma(financeContractForm.item.interestIncome)}
                    </td>
                </tr>
                <tr>
                    <td></td>
                    <td class="title">Monthly Storage:</td>
                    <td class="field_value">
                          ${f:formatMoneyWithComma(financeContractForm.item.monthlyStorage)}
                    </td>
                </tr>
                <tr>
                    <td></td>
                    <td class="title">Monthly Interest:</td>
                    <td class="field_value">
                         ${f:formatMoneyWithComma(financeContractForm.item.monthlyInterest)}
                    </td>
                </tr>
                <tr>
                    <td></td>
                    <td class="title">Total Stop Loss Price:</td>
                    <td class="field_value">
                            ${f:formatMoneyWithComma(financeContractForm.item.stopLoss)}
                    </td>
                </tr>
                <tr>
                    <td></td>
                    <td class="title">Terminal Month:</td>
                    <td class="field_value">
                        <form:select path="item.dailyBasis" id="location" items="${dailyBasises}"
                                     itemValue="id"
                                     itemLabel="terminalMonth"/>
                    </td>
                </tr>


            <tr>
                <td></td>
                <td class="title">Remark:</td>
                <td class="field_value" colspan="4">
                    <form:textarea cssStyle="width: 757px; height: 87px" path="item.remark"/>
                </td>
            </tr>
            <tr>
                <td class="title" colspan="3"></td>
                <td class="field_value" colspan="4">
                    <c:if test="${permissonsMap.get('perm_142')}">
                    <input
                        class="button ui-button ui-widget ui-state-default ui-corner-all" type="submit"
                        value="Save" onclick="disableSubmitAfterClick(this)"/>
                    </c:if>
                </td>
            </tr>
            </form:form>
            <tr>
                <td class="title" colspan="3"></td>
                <td class="field_value" colspan="4">
                    <c:if test="${permissonsMap.get('perm_143')}">
                    <input
                            class="button ui-button ui-widget ui-state-default ui-corner-all" type="button"
                            value="Export Consignment Contract" id="btnExportConsignmentContract"/>
                    </c:if>
                </td>
            </tr>
        </table>
    </div>
    </div>

</div>
<script>
    var currentId = ${currentId};
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
    }).on("click", "#btnExportConsignmentContract", function() {
        $.sendRequest({
            status: "export",
            action: "getConsignmentContract",
            data: {
                "financeContractId": '${financeContractForm.item.id}'
            }
        }, function(msg) {
            return true;
        });
    }).on("click", "#payment-new-button", function() {
        $("#payment-new").show();
    }).on("click", "#interestPayment-new-button", function() {
        $("#interestPayment-new").show();
    }).on("change", "#select-bankAccount", function() {
        if(currentId === -1) {
            location.href = getAbsolutePath()+ "/finance-contract.htm?tryBankId=" + $(this).val();
        } else {
            location.href = getAbsolutePath()+ "/finance-contract.htm?id="+currentId+"&tryBankId=" + $(this).val();
        }
        console.log("on change");

        })
</script>
<jsp:include page="footer.jsp"/>

</body>
</html>

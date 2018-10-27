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
    <title>Invoice List</title>
    <style>
        .styleTotal td {
            border-top: 1px solid #000;
        }

        .bolder {
            font-size: 12px;
        }

        table.dataTableStandard {
            border-collapse: collapse;
            font-size: 8pt;
        }

        table.dataTableStandard tfoot {
            text-align: left;
        }

        table.dataTableStandard tbody tr td, table.dataTableStandard thead tr th {
            border-bottom: 1px solid black;
            padding: 5px;
        }

        table.dataTableStandard thead tr th, table.dataTableStandard tbody tr th {
            background-color: #EEEEEE;
            border-top: 1px solid black;
        }
        .title {
            font-weight: bold;
            font-size: 13px !important;
            padding: 1px !important;
            text-align: right;
        }
        .subTitle {
            padding-top: 2px !important;
            font-style: italic;
        }

    </style>
</head>
<body>
<jsp:include page="header.jsp">
    <jsp:param name="url" value="67"/>
    <jsp:param name="page" value="114"/>
</jsp:include>
<div style="width: 1400px ! important; padding: 15px; background: none repeat scroll 0% 0% white; height: auto; overflow: hidden; min-height: 400px"
     class="container_16 border" id="wrapper">
    <form method="get" action="invoice-list.htm" id="x-form">
        <div style="width: 100%; overflow: hidden; height: auto; border-bottom: 1px solid #B8C2CC; padding-bottom: 10px">
            <table class="table_filter">
                <thead>
                <tr style="font-size: 13px">
                    <th width="200px">Client</th>
                    <th width="190px">Status</th>
                    <th width="190px">Date</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td width="200px">
                        <input type="text" class="text_filter" style="width: 100%; max-height: inherit">
                        <select name="clientId" id="clientId" size="5" class="filter"
                                style="width: 100%; overflow: auto">
                            <option value="-1" <c:if test="${clientId==-1}">selected</c:if>>All</option>
                            <c:forEach var="item" items="${clients}">
                                <option
                                        <c:if test="${item.id==clientId}">selected</c:if>
                                        value="${item.id}">${item.name}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td width="190px">
                        <select name="completionStatus" id="completionStatus" size="5" class="filter"
                                style="width: 100%; overflow: auto">
                            <option value="-1" <c:if test="${completionStatus==-1}">selected</c:if>>All</option>
                            <c:forEach var="item" items="${completionStatuses}">
                                <option
                                        <c:if test="${item.key==completionStatus}">selected</c:if>
                                        value="${item.key}">${item.value}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td width="190px">
                        From:<br/><fmt:formatDate pattern="d-MMM-y"
                                                  value="${startDate}" var="formatStartDate"/>
                        <input name="startDate" class="js_date" id="startDate" value="${formatStartDate}">
                        <br/>To:<br/><fmt:formatDate pattern="d-MMM-y"
                                                     value="${endDate}" var="formatEndDate"/>
                        <input name="endDate" class="js_date" id="endDate" value="${formatEndDate}">
                    </td>
                </tr>
                </tbody>
            </table>
        </div>

        <div style="width: 100%;overflow: hidden; height: auto; border-bottom: 1px solid #B8C2CC; padding-bottom: 10px">
            <h3>Transaction list</h3>
            Show <select name="perPage" id="perPage"
                         style="width: 50px">
            <c:forEach var="item" items="${perPages}">
                <option
                        <c:if test="${item==perPage}">selected</c:if> value="${item}">${item}</option>
            </c:forEach>
        </select> entries
            <table class="dataTableStandard" style="width: 100%">
                <thead>
                <tr>
                    <th>Invoice Ref.</th>
                    <th>Date</th>
                    <th>Client</th>
                    <th>Inv Amount</th>
                    <th>Due Date</th>
                    <th>Days Overdue</th>
                    <th>Amount Paid</th>
                    <th>Paid Date</th>
                    <th>Balance</th>
                    <th>Paid Status</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="item" items="${invoices}">
                    <tr class="go2Detail" data-href="invoice.htm?id=${item.id}">
                        <td>${item.refNumber}</td>
                        <td style="text-align: center"><fmt:formatDate pattern="dd-MMM-yy" value="${item.created}"/></td>
                        <td>${item.client.name}</td>
                        <td style="text-align: right"><fmt:formatNumber type="number" minFractionDigits="2"
                                                                        maxFractionDigits="2"
                                                                        value="${item.amount}"/></td>
                        <td style="text-align: center"><fmt:formatDate pattern="dd-MMM-yy" value="${item.dueDate}"/></td>
                        <td style="text-align: center">
                            ${item.daysOverDue}
                        </td>
                        <td style="text-align: right"><fmt:formatNumber type="number" minFractionDigits="2"
                                              maxFractionDigits="2"
                                              value="${item.paidAmount}"/></td>
                        <td style="text-align: center"><fmt:formatDate pattern="dd-MMM-yy" value="${item.lastPaymentDate}"/></td>
                        <td style="text-align: right">${f:formatMoneyWithComma(item.balance)}</td>
                        <td style="text-align: center" <c:if test="${! item.completed}">class='alarm'</c:if> >${completionStatuses[item.completionStatus]}</td>
                    </tr>
                </c:forEach>
                    <tr class="total-row">
                        <td colspan="3">Total</td>
                        <td style="text-align: right"><fmt:formatNumber type="number" minFractionDigits="2"
                                              maxFractionDigits="2"
                                              value="${aggregateInfo.amount}"/></td>
                        <td></td><td></td>
                        <td style="text-align: right"><fmt:formatNumber type="number" minFractionDigits="2"
                                              maxFractionDigits="2"
                                              value="${aggregateInfo.paid}"/></td>
                        <td></td>
                        <td style="text-align: right"><fmt:formatNumber type="number" minFractionDigits="2"
                                              maxFractionDigits="2"
                                              value="${aggregateInfo.balance}"/></td>
                        <td></td>
                    </tr>
                </tbody>
            </table>
            ${testhtml}
        </div>
    </form>
</div>
<script>
    $(document).ready(function () {
        $(".js_date").datepicker({
            dateFormat: "d-M-yy",
            changeMonth: true,
            changeYear: true,
            yearRange: "1945:2050"
        });
        var will_changes = "#clientId,#completionStatus,.js_date";
        $(will_changes).change(function () {
            $('#x-form').find('.js_date').each(function () {
                if ($(this).val() == '') {
                    $(this).attr('disabled', 'disabled');
                }
            });
            $('#x-form').submit();
        });
    }).on("dblclick", "tr.go2Detail", function() {
        document.location = $(this).data('href');
    });

</script>
<jsp:include page="footer.jsp"/>
</body>
</html>

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
<jsp:include page="../header.jsp">
    <jsp:param name="url" value="33"/>
    <jsp:param name="page" value="117"/>
</jsp:include>
<div style="width: 600px ! important; padding: 15px; background: none repeat scroll 0% 0% white; height: auto; overflow: hidden; min-height: 400px"
     class="container_16 border" id="wrapper">
    <form method="get" action="lu-daily-list.htm" id="x-form">
        <div style="width: 100%; overflow: hidden; height: auto; border-bottom: 1px solid #B8C2CC; padding-bottom: 10px">
            <table class="table_filter">
                <thead>
                <tr style="font-size: 13px">
                    <th width="190px">Date</th>
                </tr>
                </thead>
                <tbody>
                <tr>
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
            <h3>Report list</h3>
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
                    <th>Date</th>
                    <th>Begin Expense</th>
                    <th>Income</th>
                    <th>In-day Expense</th>
                    <th>Total</th>
                    <th>Status</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="item" items="${lus}">
                    <tr class="go2Detail" data-href="lu-daily.htm?id=${item.id}">
                        <td>${item.refNumber}</td>
                        <td style="text-align: right">${f:formatMoneyWithComma(item.beginExpense)}</td>
                        <td style="text-align: right">${f:formatMoneyWithComma(item.income)}</td>
                        <td style="text-align: right">${f:formatMoneyWithComma(item.indayExpense)}</td>
                        <td style="text-align: right">${f:formatMoneyWithComma(item.total)}</td>
                        <td style="text-align: center" <c:if test="${!item.completed}">class='alarm'</c:if> >${approvalStatuses[item.approvalStatus]}</td>
                    </tr>
                </c:forEach>
                    <tr class="total-row">
                        <td>Total</td>
                        <td style="text-align: right">${f:formatMoneyWithComma(aggregateInfo.beginExpense)}</td>
                        <td style="text-align: right">${f:formatMoneyWithComma(aggregateInfo.income)}</td>
                        <td style="text-align: right">${f:formatMoneyWithComma(aggregateInfo.indayExpense)}</td>
                        <td style="text-align: right">${f:formatMoneyWithComma(aggregateInfo.total)}</td>
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
        var will_changes = ".js_date";
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
<jsp:include page="../footer.jsp"/>
</body>
</html>

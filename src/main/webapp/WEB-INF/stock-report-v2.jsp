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
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Stock Report</title>
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
        width: 100%;
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

    table.dataTableStandard td, table.dataTableStandard th {
        width: 5%;
    }

    table.dataTableStandard tbody {
        height: 180px; /* Just for the demo          */
        overflow-y: auto; /* Trigger vertical scroll    */
        overflow-x: hidden; /* Hide the horizontal scroll */
    }

    legend {
        font-size: 10pt;
    }

    fieldset {
        margin-bottom: 30px;
    }
    .dataTableStandard th {
    
    text-align: left;
}
table.dataTableStandard tbody tr:hover {
    background-color: lightyellow;
}
</style>
</head>
<body>
<jsp:include page="header.jsp">
    <jsp:param name="url" value="64"/>
    <jsp:param name="page" value="111"/>
</jsp:include>
<div style="width: ${width}px ! important; padding: 15px; background: none repeat scroll 0% 0% white; height: auto; overflow: hidden; min-height: 400px"
     class="container_16 border" id="wrapper">
    <form method="get" action="stock-report-v2.htm" id="x-form">
        <div style="width: 100%; overflow: hidden; height: auto; border-bottom: 1px solid #B8C2CC; padding-bottom: 10px">
            <table class="table_filter">
                <thead>
                <tr style="font-size: 13px">
                    <th width="200px">View</th>
                    <%--<th width="190px">Date</th>--%>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td width="190px">
                        <select name="viewTypeId" id="viewTypeId" size="5" class="filter"
                                style="width: 100%; overflow: auto">
                            <option value="0" <c:if test="${viewTypeId==0}">selected</c:if>>Daily</option>
                            <option value="1" <c:if test="${viewTypeId==1}">selected</c:if>>Monthly</option>
                        </select>
                    </td>
                    <%--<td width="190px">--%>
                        <%--<fmt:formatDate pattern="d-MMM-y"--%>
                                        <%--value="${startDate}" var="formatStartDate"/>--%>
                        <%--<input name="startDate" class="js_date" id="startDate" value="${formatStartDate}">--%>
                    <%--</td>--%>
                </tr>
                </tbody>
            </table>
        </div>
        <div id="table-container" style="width: 100%;overflow: hidden; height: auto; border-bottom: 1px solid #B8C2CC; padding-bottom: 10px">
            <fieldset>
                <legend>${title}</legend>
                <table class="dataTableStandard" style="table-layout: fixed; width: 100%">
                    <thead>
                    <tr>
                        <th style="width: ${widthGrade}px">Grade</th>
                        <th style="width: ${widthStock}px">Stock</th>
                        <c:if test="${viewTypeId==0}">
                            <c:forEach var="date" items="${listDates}">
                                <th style="width: ${eachRowWidth}px"><fmt:formatDate pattern="dd-MMM-yy" value="${date}"/></th>
                            </c:forEach>
                        </c:if>
                        <c:if test="${viewTypeId==1}">
                            <c:forEach var="date" items="${listDates}">
                                <th style="width: ${eachRowWidth}px"><fmt:formatDate pattern="MMM-yy" value="${date}"/></th>
                            </c:forEach>
                        </c:if>
                        <th style="width: ${eachRowWidth}px">Total</th>
                    </tr>
                    </thead>
                </table>
                <div style="height: 400px; overflow: scroll">
                    <table class="dataTableStandard" style="table-layout: fixed; width: 100%; opacity: 0.9;">
                            <c:forEach var="item" items="${grades}" varStatus="outerLoop">
                                <tbody <c:if test="${outerLoop.index == fn:length(grades) - 1}">style="display: none;"</c:if>>
                                    <tr class="grade_title">
                                        <td style="width: ${widthGrade}px; ">${item.gradeName}</td>
                                        <c:forEach begin="1" end="${totalColumns - 2}" varStatus="loop">
                                            <td style="width: ${(loop.index == 1) ? widthStock: eachRowWidth}px"></td>
                                        </c:forEach>


                                    </tr>
                                    <tr class="row_info">
                                        <td class="row_title" style='width: ${widthGrade}px;'><div class="child-1">Deposit</div></td>
                                        <c:forEach var="dates" items="${item.listDates}" varStatus="loop">
                                            <c:choose>
                                                <c:when test="${dates.deposit == 0}">
                                                    <td style="width: ${(loop.index == 0) ? widthStock: eachRowWidth}px">-</td>
                                                </c:when>
                                                <c:otherwise>
                                                    <td style="width: ${(loop.index == 0) ? widthStock: eachRowWidth}px"><fmt:formatNumber type="number"
                                                                                   pattern="#,##0.00;(#,##0.00)"
                                                                                   minFractionDigits="2"
                                                                                   maxFractionDigits="2"
                                                                                   value="${dates.deposit}"/></td>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                    </tr>
                                    <tr class="row_info">
                                        <td class="row_title"><div class="child-1">Withdraw</div></td>
                                        <c:forEach var="dates" items="${item.listDates}" varStatus="loop">
                                            <c:choose>
                                                <c:when test="${dates.withdraw == 0}">
                                                    <td style="width: ${(loop.index == 0) ? widthStock: eachRowWidth}px">-</td>
                                                </c:when>
                                                <c:otherwise>
                                                    <td style="width: ${(loop.index == 0) ? widthStock: eachRowWidth}px"><fmt:formatNumber type="number"
                                                                                   pattern="#,##0.00;(#,##0.00)"
                                                                                   minFractionDigits="2"
                                                                                   maxFractionDigits="2"
                                                                                   value="${dates.withdraw}"/>
                                                    </td>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                    </tr>
                                    <tr class="row_total">
                                        <td class="row_title">Total ${item.gradeName}</td>
                                        <c:forEach var="dates" items="${item.listDates}" varStatus="loop">
                                            <c:choose>
                                                <c:when test="${dates.total == 0}">
                                                    <td style="width: ${(loop.index == 0) ? widthStock: eachRowWidth}px">-</td>
                                                </c:when>
                                                <c:otherwise>
                                                    <td style="width: ${(loop.index == 0) ? widthStock: eachRowWidth}px"><fmt:formatNumber type="number"
                                                                          pattern="#,##0.00;(#,##0.00)"
                                                                          minFractionDigits="2"
                                                                          maxFractionDigits="2"
                                                                          value="${dates.total}"/>
                                                    </td>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                    </tr>
                                </tbody>
                            </c:forEach>

                    </table>
                </div>
                <table class="dataTableStandard" style="table-layout: fixed; width: 100%">
                    <c:set var="item" scope="application" value="${grades[fn:length(grades) - 1]}"/>
                    <tbody>
                        <tr class="grade_title">
                            <td style="width: ${widthGrade}px; border-top: 2px solid #000;">${item.gradeName}</td>
                            <c:forEach begin="1" end="${totalColumns - 2}" varStatus="loop">
                                <td style="width: ${(loop.index == 1) ? widthStock: eachRowWidth}px; border-top: 2px solid #000;"></td>
                            </c:forEach>


                        </tr>
                        <tr class="row_info">
                            <td class="row_title" style='width: ${widthGrade}px;'><div class="child-1">Deposit</div></td>
                            <c:forEach var="dates" items="${item.listDates}" varStatus="loop">
                                <c:choose>
                                    <c:when test="${dates.deposit == 0}">
                                        <td style="width: ${(loop.index == 0) ? widthStock: eachRowWidth}px">-</td>
                                    </c:when>
                                    <c:otherwise>
                                        <td style="width: ${(loop.index == 0) ? widthStock: eachRowWidth}px"><fmt:formatNumber type="number"
                                                                       pattern="#,##0.00;(#,##0.00)"
                                                                       minFractionDigits="2"
                                                                       maxFractionDigits="2"
                                                                       value="${dates.deposit}"/></td>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </tr>
                        <tr class="row_info">
                            <td class="row_title"><div class="child-1">Withdraw</div></td>
                            <c:forEach var="dates" items="${item.listDates}" varStatus="loop">
                                <c:choose>
                                    <c:when test="${dates.withdraw == 0}">
                                        <td style="width: ${(loop.index == 0) ? widthStock: eachRowWidth}px">-</td>
                                    </c:when>
                                    <c:otherwise>
                                        <td style="width: ${(loop.index == 0) ? widthStock: eachRowWidth}px"><fmt:formatNumber type="number"
                                                                       pattern="#,##0.00;(#,##0.00)"
                                                                       minFractionDigits="2"
                                                                       maxFractionDigits="2"
                                                                       value="${dates.withdraw}"/>
                                        </td>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </tr>
                        <tr class="row_total">
                            <td class="row_title">Total ${item.gradeName}</td>
                            <c:forEach var="dates" items="${item.listDates}" varStatus="loop">
                                <c:choose>
                                    <c:when test="${dates.total == 0}">
                                        <td style="width: ${(loop.index == 0) ? widthStock: eachRowWidth}px">-</td>
                                    </c:when>
                                    <c:otherwise>
                                        <td style="width: ${(loop.index == 0) ? widthStock: eachRowWidth}px"><fmt:formatNumber type="number"
                                                              pattern="#,##0.00;(#,##0.00)"
                                                              minFractionDigits="2"
                                                              maxFractionDigits="2"
                                                              value="${dates.total}"/>
                                        </td>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </tr>
                    </tbody>
                </table>
                
            </fieldset>
            <div id="bottom_anchor"></div>
        </div>
    </form>
    <script>
        $(document).ready(function () {
            $("#maintable").stickyTableHeaders();
            $(".js_date").datepicker({
                dateFormat: "d-M-yy",
                changeMonth: true,
                changeYear: true,
                yearRange: "1945:2050"
            });
            var will_changes = "#viewTypeId,.js_date";
            $(will_changes).change(function () {
                $('#x-form').find('.js_date').each(function () {
                    if ($(this).val() == '') {
                        $(this).attr('disabled', 'disabled');
                    }
                });
                $('#x-form').submit();
            });
        });
    </script>
</div>
<jsp:include page="footer.jsp"/>
</body>
</html>

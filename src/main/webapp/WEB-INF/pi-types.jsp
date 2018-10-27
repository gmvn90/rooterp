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
    <title>Processing Type</title>
    <style>
        .styleTotal td {
            border-top: 1px solid #000;
        }

        .bolder {
            font-size: 12px;
        }

        #piTypeTable {
            font-size: 10pt;
            border-collapse: collapse;
        }

        #piTypeTable td,#piTypeTable th {
            padding-right: 10px;
            padding-left: 10px;
        }
    </style>
</head>
<body>
<jsp:include page="header.jsp">
    <jsp:param name="url" value="44"/>
    <jsp:param name="page" value="94"/>
</jsp:include>
<div style="width: 1050px ! important; padding: 15px; background: none repeat scroll 0% 0% white; height: auto; overflow: hidden"
     class="container_16 border" id="wrapper">
    <form id="selectClient" action="pi-types.htm" method="get">
        Client: <select id="client" name="client">
        <c:forEach var="item" items="${clients}">
            <option <c:if test="${client == item.id}">selected</c:if> value="${item.id}">${item.name}</option>
        </c:forEach>
    </select>
    </form>
    <table id="piTypeTable">
        <tr style="border-bottom: 1pt solid black !important;">
            <th colspan="3" style="text-align: left">PI Type:</th>
            <th>Units</th>
            <th style="width: 100px">Average Processing Wt. Loss</th>
            <th style="width: 100px">Average Reject Grade 3</th>
            <th>Cost USD/Mt.</th>
            <th></th>
            <th></th>
        </tr>

        <c:forEach var="piType" items="${piTypes}">
            <tr>
                <th></th>
                <th colspan="2" style="text-align: left;height: 50px; vertical-align: bottom;">${piType.name}</th>
            </tr>
            <c:set var="totalWeightLoss" value="${0}"/>
            <c:set var="totalRjGrade3" value="${0}"/>
            <c:set var="totalCost" value="${0}"/>
            <c:forEach var="item" items="${piType.piTypeItems}">
                <form action="savePITypeItemProperties.htm" method="post">
                <input type="hidden" name="clientId" value="${client}"/>
                    <input type="hidden" name="piTypeItemId" value="${item.id}"/>
                <tr>
                    <td></td>
                    <td></td>
                    <td>${item.option.name}</td>
                    <td>${item.option.optionUnit}</td>
                    <td><input class="numberOnly" name="weightLoss" style="text-align: right; width: 100px" type="text" value="${item.weightLoss}"/></td>
                    <c:set var="totalWeightLoss" value="${totalWeightLoss + item.weightLoss}" />
                    <td><input class="numberOnly" name="rejectGrade3" style="text-align: right; width: 100px" type="text" value="${item.rejectGrade3}"/></td>
                    <c:set var="totalRjGrade3" value="${totalRjGrade3 + item.rejectGrade3}" />
                    <td style="text-align: right"><fmt:formatNumber type="number" maxFractionDigits="2" value="${item.cost.costValue}"/></td>
                    <c:set var="totalCost" value="${totalCost + item.cost.costValue}" />
                    <td>${item.updater.userName}: <fmt:formatDate pattern="dd-MMM-yy hh:mm" value="${item.updated}"/></td>
                    <td><input class="button ui-button ui-widget ui-state-default ui-corner-all" style="font-size: 75%" type="submit" value="Save"/></td>
                </tr>
                </form>
            </c:forEach>

            <tr>
                <th></th>
                <th colspan="2" style="text-align: left">Total ${piType.name}</th>
                <th></th>
                <th style="text-align: right"><fmt:formatNumber type="number" maxFractionDigits="2" value="${totalWeightLoss}"/>%</th>
                <th style="text-align: right"><fmt:formatNumber type="number" maxFractionDigits="2" value="${totalRjGrade3}"/>%</th>
                <th style="text-align: right"><fmt:formatNumber type="number" maxFractionDigits="2" value="${totalCost}"/></th>
            </tr>
        </c:forEach>

    </table>
</div>
<script>
    $(document).ready(function() {
        var will_changes = "#client";
        $(will_changes).change(function() {
            $('#selectClient').submit();
        });

    });
</script>
<jsp:include page="footer.jsp"/>

</body>
</html>

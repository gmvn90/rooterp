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
    <title>Claim List</title>
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
    <jsp:param name="url" value="75"/>
    <jsp:param name="page" value="124"/>
</jsp:include>
<div style="width: 600px ! important; padding: 15px; background: none repeat scroll 0% 0% white; height: auto; overflow: hidden; min-height: 400px"
     class="container_16 border" id="wrapper">

    <form method="get" action="claim-list.htm" id="x-form">
        <div style="width: 100%; overflow: hidden; height: auto; border-bottom: 1px solid #B8C2CC; padding-bottom: 10px">
            <table class="table_filter">
                <thead>
                <tr style="font-size: 13px">
                    <th width="200px">Client</th>
                    <th width="190px">Claim Status</th>
                    <th width="190px">Date</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td width="200px">
                        <input type="text" class="text_filter" style="width: 100%; max-height: inherit">
                        <select name="clientId" id="clientId" size="5" class="filter"
                                style="width: 100%; overflow: auto">
                            <c:forEach var="item" items="${clients}">
                                <option
                                        <c:if test="${item.id==searchForm.clientId}">selected</c:if>
                                        value="${item.id}">${item.name}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td width="190px">
                        <select name="claimStatus" id="claimStatus" size="5" class="filter"
                                style="width: 100%; overflow: auto">
                            <c:forEach var="item" items="${searchForm.claimStatuses}">
                                <option
                                        <c:if test="${item.key==searchForm.claimStatus}">selected</c:if>
                                        value="${item.key}">${item.value}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td width="190px">
                        From:<br/>
                        <input name="startDate" class="js_date" id="startDate" value="${f:formatDateOnly(searchForm.startDate)}">
                        <br/>To:<br/>
                        <input name="endDate" class="js_date" id="endDate" value="${f:formatDateOnly(searchForm.endDate)}">
                    </td>
                </tr>
                </tbody>
            </table>
        </div>

        <div style="width: 100%;overflow: hidden; height: auto; border-bottom: 1px solid #B8C2CC; padding-bottom: 10px">
            <h3>Claim list</h3>
            Show <select name="perPage" id="perPage"
                         style="width: 50px">
            <c:forEach var="item" items="${perPages}">
                <option
                        <c:if test="${item==perPage}">selected</c:if> value="${item}">${item}</option>
            </c:forEach>
        </select> entries
            <input name="txtSearch" id="txtSearch" type="text" value="${txtSearch}"/>

            <table class="dataTableStandard" style="width: 100%">
                <thead>
                <tr>
                    <th>Claim Ref.</th>
                    <th>Date</th>
                    <th>SI Ref</th>
                    <th>Client</th>
                    <th>Claim Status</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="item" items="${claims}">
                    <tr class="go2Detail" data-href="shipping-instruction/${item.shippingInstructionBySiId.refNumber}/claim-detail/${item.refNumber}.htm">
                        <td>${item.refNumber}</td>
                        <td style="text-align: center"><fmt:formatDate pattern="dd-MMM-yy" value="${item.created}"/></td>
                        <td>${item.shippingInstructionBySiId.refNumber}</td>
                        <td>${item.shippingInstructionBySiId.companyMasterByClientId.name}</td>
                        <td style="text-align: center" <c:if test="${item.claimStatus == 'PENDING'}">class='alarm'</c:if> >${searchForm.claimStatuses[item.claimStatus]}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            ${testhtml}
        </div>
    </form>

</div>
<script>
    $(document).ready(function () {
        var will_changes = "#clientId,#claimStatus,.js_date";
        $(will_changes).change(function () {
            $('#x-form').find('.js_date').each(function () {
                if ($(this).val() == '') {
                    $(this).attr('disabled', 'disabled');
                }
            });
            $('#x-form').submit();
        });
    }).on("dblclick", "tr.go2Detail", function () {
        document.location = $(this).data('href');
    }).on("keypress", "#txtSearch", function(e) {
        var keycode = (e.keyCode ? e.keyCode : e.which);
        if(keycode == '13'){
            $('#x-form').submit();
        }
    }).on("click", "#createNew", function() {
        document.location = $(this).data('href');
    });
</script>
<jsp:include page="../footer.jsp"/>
</body>
</html>

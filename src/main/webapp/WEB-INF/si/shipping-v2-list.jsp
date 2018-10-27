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
    <title>Shipping List</title>
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
    </style>
</head>
<body>
<jsp:include page="../header.jsp">
    <jsp:param name="url" value="11"/>
    <jsp:param name="page" value="4"/>
</jsp:include>
<div style="width: 2000px ! important; padding: 15px; background: none repeat scroll 0% 0% white; height: auto; overflow: hidden; min-height: 400px"
     class="container_16 border" id="wrapper">
    <form method="get" action="shipping-v2-list.htm" id="x-form">
        <div style="width: 100%; overflow: hidden; height: auto; border-bottom: 1px solid #B8C2CC; padding-bottom: 10px">
            <table class="table_filter">
                <thead>
                <tr style="font-size: 13px">
                    <th width="200px">Client</th>
                    <th width="200px">Buyer</th>
                    <th width="200px">Grade</th>
                    <th width="190px">Completion Status</th>
                    <th width="190px">Shipment Status</th>
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
                                        <c:if test="${item.id == searchForm.clientId}">selected</c:if>
                                        value="${item.id}">${item.name}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td width="200px">
                        <input type="text" class="text_filter" style="width: 100%; max-height: inherit">
                        <select name="buyerId" id="buyerId" size="5" class="filter"
                                style="width: 100%; overflow: auto">
                            <c:forEach var="item" items="${buyers}">
                                <option
                                        <c:if test="${item.id==searchForm.buyerId}">selected</c:if>
                                        value="${item.id}">${item.name}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td width="200px">
                        <input type="text" class="text_filter" style="width: 100%; max-height: inherit">
                        <select name="gradeId" id="gradeId" size="5" class="filter"
                                style="width: 100%; overflow: auto">
                            <c:forEach var="item" items="${grades}">
                                <option
                                        <c:if test="${item.id==searchForm.gradeId}">selected</c:if>
                                        value="${item.id}">${item.name}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td width="190px">
                        <select name="completionStatus" id="status" size="5" class="filter"
                                style="width: 100%; overflow: auto">
                            <c:forEach var="item" items="${searchForm.completionStatuses}">
                                <option
                                        <c:if test="${item.key==searchForm.completionStatus}">selected</c:if>
                                        value="${item.key}">${item.value}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td width="190px">
                        <select name="shipmentStatus" id="shipmentStatus" size="5" class="filter"
                                style="width: 100%; overflow: auto">
                            <c:forEach var="item" items="${searchForm.completionStatuses}">
                                <option
                                        <c:if test="${item.key==searchForm.shipmentStatus}">selected</c:if>
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
        <input class="right" id="createNew" data-href="shipping-v2.htm" type="button" value="New Shipping Instruction"/>

        <div style="width: 100%;overflow: hidden; height: auto; border-bottom: 1px solid #B8C2CC; padding-bottom: 10px">
            <h3>Available Shipping Instruction</h3>
            Show <select name="perPage" id="perPage"
                         style="width: 50px">
            <c:forEach var="item" items="${perPages}">
                <option
                        <c:if test="${item==perPage}">selected</c:if> value="${item}">${item}</option>
            </c:forEach>
        </select> entries<br/>
            <input name="txtSearch" id="txtSearch" type="text" value="${txtSearch}"/>

            <table class="dataTableStandard" style="width: 100%">
                <thead>
                <tr>
                    <th>SI Ref.</th>
                    <th>Client</th>
                    <th>Client Ref.</th>
                    <th>Buyer</th>
                    <th>Buyer Ref.</th>
                    <th>First Date</th>
                    <th>Loading Date</th>
                    <th>Grade</th>
                    <th>Packing</th>
                    <th>Dest.</th>
                    <th>Total</th>
                    <th>W/Q Cert.</th>
                    <th>Shipping Line</th>
                    <th>Booking No</th>
                    <th>Closing Date</th>
                    <th>Closing Time</th>
                    <th>Sample Status</th>
                    <th>Shipment Status</th>
                    <th>Request Status</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="item" items="${shippingInstructions}">
                    <tr class="go2Detail si-detail" data-href="shipping-v2.htm?id=${item.shippingInstruction.id}" data-id='${item.shippingInstruction.id}'>
                        <td>${item.siRef}</td>
                        <td>${item.client}</td>
                        <td>${item.clientRef}</td>
                        <td>${item.buyer}</td>
                        <td>${item.buyerRef}</td>
                        <td><fmt:formatDate pattern="dd-MMM-yy" value="${item.firstDate}"/></td>
                        <td><fmt:formatDate pattern="dd-MMM-yy" value="${item.loadingDate}"/></td>
                        <td>${item.grade}</td>
                        <td>${item.packing}</td>
                        <td>${item.dest}</td>
                        <td><fmt:formatNumber type="number" minFractionDigits="3" maxFractionDigits="3"
                                              value="${item.total}"/></td>
                        <td>${item.wQCert}</td>
                        <td>${item.shippingLine}</td>
                        <td>${item.bookingNo}</td>
                        <td><fmt:formatDate pattern="dd-MMM-yy" value="${item.closingDate}"/></td>
                        <td>${item.closingTime}</td>
                        <c:choose>
                            <c:when test="${item.sampleStatus == 'Pending'}">
                                <td style="color: red">${item.sampleStatus}</td>
                            </c:when>
                            <c:otherwise>
                                <td>${item.sampleStatus}</td>
                            </c:otherwise>
                        </c:choose>
                        <c:choose>
                            <c:when test="${item.shipmentStatus == 'Pending'}">
                                <td style="color: red">${item.shipmentStatus}</td>
                            </c:when>
                            <c:otherwise>
                                <td>${item.shipmentStatus}</td>
                            </c:otherwise>
                        </c:choose>
                        <c:choose>
                            <c:when test="${item.requestStatus == 'Pending'}">
                                <td style="color: red">${item.requestStatus}</td>
                            </c:when>
                            <c:otherwise>
                                <td>${item.requestStatus}</td>
                            </c:otherwise>
                        </c:choose>
                    </tr>
                </c:forEach>
                </tbody>
                <tfoot>
                <tr class="total-row">
                    <th colspan="10">Total</th>
                    <th><fmt:formatNumber type="number" minFractionDigits="3" maxFractionDigits="3" value="${aggregate.tons}"/></th>
                    <th colspan="7"></th>
                </tr>
                </tfoot>
            </table>
            ${pagination}

        </div>
    </form>
    <script>
        $(document).ready(function () {
            $(".js_date").datepicker({
                dateFormat: "d-M-yy",
                changeMonth: true,
                changeYear: true,
                yearRange: "1945:2050"
            });
            var will_changes = "#clientId,#buyerId,#gradeId,#perPage,#status,#shipmentStatus,.js_date";
            $(will_changes).change(function () {
                $('#x-form').find('.js_date').each(function () {
                    if ($(this).val() == '') {
                        $(this).attr('disabled', 'disabled');
                    }
                });
                $('#x-form').submit();
            });

        }).on("click", "#createNew", function() {
            document.location = $(this).data('href');
        }).on("keypress", "#txtSearch", function(e) {
            var keycode = (e.keyCode ? e.keyCode : e.which);
            if(keycode == '13'){
                $('#x-form').find('.js_date').each(function () {
                    if ($(this).val() == '') {
                        $(this).attr('disabled', 'disabled');
                    }
                });
                $('#x-form').submit();
            }
        });
        $('tr[data-href]').on("dblclick", function () {
            document.location = $(this).data('href');
        });
    </script>
</div>
<jsp:include page="../footer.jsp"/>
</body>
</html>

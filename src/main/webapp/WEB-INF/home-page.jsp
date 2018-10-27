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
    <title>Home Page</title>
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
            padding: 5px;
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
<jsp:include page="header.jsp"></jsp:include>
<div style="width: 1700px ! important; padding: 15px; background: none repeat scroll 0% 0% white; height: auto; overflow: hidden; min-height: 400px"
     class="container_16 border" id="wrapper">
    <h3 align="center">Center Monitor Page</h3>
    <div style="width: 100%;overflow: hidden; height: auto; border-bottom: 1px solid #B8C2CC; padding-bottom: 10px">

        <fieldset>
            <legend>Delivery</legend>
            <table class="dataTableStandard" style="table-layout: fixed; width: 99%">
                <thead>
                <tr>
                    <th>DI Ref.</th>
                    <th>Client</th>
                    <th style="width: 6%">Supplier</th>
                    <th>Origin</th>
                    <th>Quality</th>
                    <th style="width: 10%">Grade</th>
                    <th>Packing</th>
                    <th>Total Tons</th>
                    <th>Delivered</th>
                    <th>Balance</th>
                    <th>Status</th>
                </tr>
                </thead>
            </table>
            <div style="height: 250px; overflow: scroll">
                <table class="dataTableStandard" style="table-layout: fixed; width: 100%">
                    <tbody>
                    <c:forEach var="item" items="${deliveryInstructions}">
                        <tr class="go2Detail di-detail" data-href="delivery-v2.htm?id=${item.deliveryInstruction.id}" data-id='${item.deliveryInstruction.id}'>
                            <td>${item.diRef}</td>
                            <td>${item.client}</td>
                            <td style="width: 8%">${item.supplier}</td>
                            <td>${item.origin}</td>
                            <td>${item.quality}</td>
                            <td style="width: 10%">${item.grade}</td>
                            <td>${item.packing}</td>
                            <td><fmt:formatNumber type="number" minFractionDigits="3" maxFractionDigits="3" value="${item.totalTons}"/></td>
                            <td class="item-deliverd"><fmt:formatNumber type="number" minFractionDigits="3" maxFractionDigits="3"
                                                  value="${item.deliverd}"/></td>
                            <td class="item-pending"><fmt:formatNumber type="number" minFractionDigits="3" maxFractionDigits="3" value="${item.balance}"/></td>
                            <c:choose>
                                <c:when test="${item.status == 'Pending'}">
                                    <td style="color: red">${item.status}</td>
                                </c:when>
                                <c:otherwise>
                                    <td>${item.status}</td>
                                </c:otherwise>
                            </c:choose>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <table class="dataTableStandard" style="table-layout: fixed; width: 99%">
                <thead>
                    <tr class="total-row">
                        <th>Total</th>
                        <th></th>
                        <th></th>
                        <th></th>
                        <th></th>
                        <th></th>
                        <th></th>
                        <th></th>
                        <th style="width: 10%"></th>
                        <th></th>
                        <th></th>
                        <th></th>
                        <th></th>
                        <th><fmt:formatNumber type="number" minFractionDigits="3" maxFractionDigits="3" value="${diAggregate.tons}"/></th>
                        <th><fmt:formatNumber type="number" minFractionDigits="3" maxFractionDigits="3" value="${diAggregate.deliverd}"/></th>
                        <th><fmt:formatNumber type="number" minFractionDigits="3" maxFractionDigits="3" value="${diAggregate.pending}"/></th>
                        <th></th>
                        <th></th>
                    </tr>
                </thead>
            </table>
        </fieldset>

        <fieldset>
            <legend>Processing</legend>
            <table class="dataTableStandard" style="table-layout: fixed; width: 99%">
                <thead>
                <tr>
                    <th>PI Ref.</th>
                    <th style="width: 6%">Client</th>
                    <th>Client Ref.</th>
                    <th>Type</th>
                    <th style="width: 7%">Debit Grade</th>
                    <th>Packing</th>
                    <th>Req. Date</th>
                    <th>Total Tons</th>
                    <th>Debit Tons</th>
                    <th>Credit Tons</th>
                    <th>Balance</th>
                    <th>Req. Status</th>
                    <th>Status</th>
                </tr>
                </thead>
            </table>
            <div style="height: 250px; overflow: scroll">
                <table class="dataTableStandard" style="table-layout: fixed; width: 100%">
                    <tbody>
                    <c:forEach var="item" items="${processingInstructions}">
                        <tr class="go2Detail pi-detail" data-href="processing-v2.htm?id=${item.processingInstruction.id}" data-id='${item.processingInstruction.id}'>
                            <td>${item.piRef}</td>
                            <td style="width: 8%">${item.client}</td>
                            <td>${item.clientRef}</td>
                            <td>${item.type}</td>
                            <td style="width: 9%">${item.debitGrade}</td>
                            <td>${item.packing}</td>
                            <td><fmt:formatDate pattern="dd-MMM-yy" value="${item.reqDate}"/></td>
                            <td class="item-allocated"><fmt:formatNumber type="number" minFractionDigits="3" maxFractionDigits="3"
                                                  value="${item.totalTons}"/></td>
                            <td class="item-inprocess"><fmt:formatNumber type="number" minFractionDigits="3" maxFractionDigits="3"
                                                  value="${item.debitTons}"/></td>
                            <td class="item-exprocess"><fmt:formatNumber type="number" minFractionDigits="3" maxFractionDigits="3"
                                                  value="${item.creditTons}"/></td>
                            <td class="item-pending"><fmt:formatNumber type="number" minFractionDigits="3" maxFractionDigits="3"
                                                  value="${item.balance}"/></td>
                            <c:choose>
                                <c:when test="${item.requestStatus == 'Pending'}">
                                    <td style="color: red">${item.requestStatus}</td>
                                </c:when>
                                <c:otherwise>
                                    <td>${item.requestStatus}</td>
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${item.status == 'Pending'}">
                                    <td style="color: red">${item.status}</td>
                                </c:when>
                                <c:otherwise>
                                    <td>${item.status}</td>
                                </c:otherwise>
                            </c:choose>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <table class="dataTableStandard" style="table-layout: fixed; width: 99%">
                <thead>
                    <tr class="total-row">
                        <th>Total</th>
                        <th></th>
                        <th></th>
                        <th></th>
                        <th style="width: 8%"></th>
                        <th></th>
                        <th></th>
                        <th><fmt:formatNumber type="number" minFractionDigits="3" maxFractionDigits="3" value="${piAggregate.allocated}"/></th>
                        <th><fmt:formatNumber type="number" minFractionDigits="3" maxFractionDigits="3" value="${piAggregate.inprocess}"/></th>
                        <th><fmt:formatNumber type="number" minFractionDigits="3" maxFractionDigits="3" value="${piAggregate.exprocess}"/></th>
                        <th><fmt:formatNumber type="number" minFractionDigits="3" maxFractionDigits="3" value="${piAggregate.pending}"/></th>
                        <th></th>
                        <th></th>
                    </tr>
                </thead>
            </table>
        </fieldset>

        <fieldset>
            <legend>Shipping</legend>
            <table class="dataTableStandard" style="table-layout: fixed; width: 99%">
            <thead>
                <tr>
                    <th>SI Ref.</th>
                    <th>Client</th>
                    <th>Client Ref.</th>
                    <th>Buyer</th>
                    <th>Buyer Ref.</th>
                    <th>First Date</th>
                    <th>Loading Date</th>
                    <th style="width: 8%">Grade</th>
                    <th>Packing</th>
                    <th>Dest.</th>
                    <th>Total</th>
                    <th>W/Q Cert.</th>
                    <th>Shipping Line</th>
                    <th>Booking No</th>
                    <th>Closing Date</th>
                    <th>Closing Time</th>
                    <th>Sample Status</th>
                    <th>Request Status</th>
                </tr>
                </thead>
            </table>
            <div style="height: 250px; overflow: scroll">
                <table class="dataTableStandard" style="table-layout: fixed; width: 100%">
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
                            <td style="width: 9%">${item.grade}</td>
                            <td>${item.packing}</td>
                            <td>${item.dest}</td>
                            <td><fmt:formatNumber type="number" minFractionDigits="3" maxFractionDigits="3" value="${item.total}"/></td>
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
                </table>
            </div>
            <table class="dataTableStandard" style="table-layout: fixed; width: 99%">
                <thead>
                    <tr class="total-row">
                        <th>Total</th>
                        <th></th>
                        <th></th>
                        <th></th>
                        <th></th>
                        <th></th>
                        <th></th>
                        <th></th>
                        <th></th>
                        <th></th>
                        <th><fmt:formatNumber type="number" minFractionDigits="3" maxFractionDigits="3" value="${siAggregate.tons}"/></th>
                        <th></th>
                        <th></th>
                        <th></th>
                        <th></th>
                        <th></th>
                        <th></th>
                        <th></th>
                    </tr>
                </thead>
            </table>
        </fieldset>

    </div>
</div>
<jsp:include page="footer.jsp"/>
<script>
    var refresh_disabled = ${refresh_disabled};
    $('tr[data-href]').on("dblclick", function () {
        document.location = $(this).data('href');
    });

    function refreshData() {
        $(".di-detail").each(function() {
            var elm = $(this);
            var id = elm.data("id");
            console.log(id);
            $.get(getAbsolutePath() + "/" + "get_di_info/" + id + ".json", function(data) {
                console.log(data);
                elm.find(".item-deliverd").html(data.deliverd);
                elm.find(".item-pending").html(data.pending);
            });
        });
        $(".pi-detail").each(function() {
            var elm = $(this);
            var id = elm.data("id");
            console.log(id);
            $.get(getAbsolutePath() + "/" + "get_pi_info/" + id + ".json", function(data) {
                console.log(data);
                elm.find(".item-allocated").html(data.allocated);
                elm.find(".item-inprocess").html(data.inprocess);
                elm.find(".item-exprocess").html(data.exprocess);
                elm.find(".item-pending").html(data.pending);
            });
        });
    }
    $(document).ready(function() {
        if(refresh_disabled !== 1) {
            refreshData();
            setInterval(function() {
                refreshData();
            }, 30000);
        }
        
    });
</script>
</body>
</html>

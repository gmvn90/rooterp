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
    <title>Delivery List</title>
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
<jsp:include page="header.jsp">
    <jsp:param name="url" value="10"/>
    <jsp:param name="page" value="3"/>
</jsp:include>
<div style="width: 1400px ! important; padding: 15px; background: none repeat scroll 0% 0% white; height: auto; overflow: hidden; min-height: 400px"
     class="container_16 border" id="wrapper">
    <form method="get" action="delivery-v2-list.htm" id="x-form">
        <div style="width: 100%; overflow: hidden; height: auto; border-bottom: 1px solid #B8C2CC; padding-bottom: 10px">
            <table class="table_filter">
                <thead>
                <tr style="font-size: 13px">
                    <th width="200px">Client</th>
                    <th width="200px">Supplier</th>
                    <th width="200px">Grade</th>
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
                    <td width="200px">
                        <input type="text" class="text_filter" style="width: 100%; max-height: inherit">
                        <select name="supplierId" id="supplierId" size="5" class="filter"
                                style="width: 100%; overflow: auto">
                            <option value="-1" <c:if test="${supplierId==-1}">selected</c:if>>All</option>
                            <c:forEach var="item" items="${suppliers}">
                                <option
                                        <c:if test="${item.id==supplierId}">selected</c:if>
                                        value="${item.id}">${item.name}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td width="200px">
                        <input type="text" class="text_filter" style="width: 100%; max-height: inherit">
                        <select name="gradeId" id="gradeId" size="5" class="filter"
                                style="width: 100%; overflow: auto">
                            <option value="-1" <c:if test="${gradeId==-1}">selected</c:if>>All</option>
                            <c:forEach var="item" items="${grades}">
                                <option
                                        <c:if test="${item.id==gradeId}">selected</c:if>
                                        value="${item.id}">${item.name}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td width="190px">
                        <select name="status" id="status" size="5" class="filter"
                                style="width: 100%; overflow: auto">
                            <option value="-1" <c:if test="${status==-1}">selected</c:if>>All</option>
                            <c:forEach var="item" items="${statuses}">
                                <option
                                        <c:if test="${item.status==status}">selected</c:if>
                                        value="${item.status}">${item.name}</option>
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
        <input class="right" id="createNew" data-href="delivery-v2.htm" type="button" value="New Delivery Instruction"/>

        <div style="width: 100%;overflow: hidden; height: auto; border-bottom: 1px solid #B8C2CC; padding-bottom: 10px">
            <h3>Available Delivery Instruction</h3>
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
                    <th>DI Ref.</th>
                    <th>Client</th>
                    <th>Supplier</th>
                    <th>Origin</th>
                    <th>Quality</th>
                    <th>Grade</th>
                    <th>Packing</th>
                    <th>Total Tons</th>
                    <th>Delivered</th>
                    <th>Balance</th>
                    <th>Status</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="item" items="${deliveryInstructions}">
                    <tr class="go2Detail di-detail" data-href="delivery-v2.htm?id=${item.deliveryInstruction.id}" data-id='${item.deliveryInstruction.id}'>
                        <td>${item.diRef}</td>
                        <td>${item.client}</td>
                        <td>${item.supplier}</td>
                        <td>${item.origin}</td>
                        <td>${item.quality}</td>
                        <td>${item.grade}</td>
                        <td>${item.packing}</td>
                        <td><fmt:formatNumber type="number" minFractionDigits="3" maxFractionDigits="3"
                                              value="${item.totalTons}"/></td>
                        <td class="item-deliverd"><fmt:formatNumber type="number" minFractionDigits="3"
                                                                    maxFractionDigits="3"
                                                                    value="${item.deliverd}"/></td>
                        <td class="item-pending"><fmt:formatNumber type="number" minFractionDigits="3"
                                                                   maxFractionDigits="3"
                                                                   value="${item.balance}"/></td>
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
                <tfoot>
                    <tr class="total-row">
                    <th colspan="7">Total</th>
                    <th><fmt:formatNumber type="number" minFractionDigits="3" maxFractionDigits="3" value="${aggregate.tons}"/></th>
                    <th><fmt:formatNumber type="number" minFractionDigits="3" maxFractionDigits="3" value="${aggregate.deliverd}"/></th>
                    <th><fmt:formatNumber type="number" minFractionDigits="3" maxFractionDigits="3" value="${aggregate.pending}"/></th>
                    <th colspan="1"></th>
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
            refreshData();
            var will_changes = "#clientId,#supplierId,#gradeId,#perPage,#status,.js_date";
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
        }
    </script>
</div>
<jsp:include page="footer.jsp"/>
</body>
</html>

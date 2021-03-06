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
    <title>Warehouse Master List</title>
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
    <jsp:param name="url" value="50"/>
    <jsp:param name="page" value="97"/>
</jsp:include>
<div style="width: 1400px ! important; padding: 15px; background: none repeat scroll 0% 0% white; height: auto; overflow: hidden; min-height: 400px"
     class="container_16 border" id="wrapper">
    <form method="get" action="warehouse-master-list.htm" id="x-form">
        <input class="right" id="createNew" data-href="warehouse-master.htm" type="button" value="New Warehouse Master"/>


        <div style="width: 100%;overflow: hidden; height: auto; border-bottom: 1px solid #B8C2CC; padding-bottom: 10px">
            <h3>Warehouse Master list</h3>
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
                    <th>Full name</th>
                    <th>Short name</th>
                    <th>Address 1</th>
                    <th>Address 2</th>
                    <th>Country</th>
                    <th>Representative</th>
                    <th>Role</th>
                    <th>Email</th>
                    <th>Fax</th>
                    <th>Telephone</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="item" items="${warehouses}">
                    <tr class="go2Detail" data-href="warehouse-master.htm?id=${item.id}">
                        <td>${item.fullName}</td>
                        <td>${item.name}</td>
                        <td>${item.address1}</td>
                        <td>${item.address2}</td>
                        <td>${item.country.shortName}</td>
                        <td>${item.representative}</td>
                        <td>${item.role}</td>
                        <td>${item.email}</td>
                        <td>${item.fax}</td>
                        <td>${item.telephone}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            ${testhtml}
        </div>
    </form>
    <script>
        var sampleSentId;
        $(document).ready(function () {
            var will_changes = "#perPage";
            $(will_changes).change(function () {
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
</div>
<jsp:include page="footer.jsp"/>
</body>
</html>

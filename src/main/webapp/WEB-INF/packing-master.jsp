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
    <title>Packing Master Detail</title>
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

        .title {
            font-weight: bold;
            font-size: 13px !important;
            padding: 1px !important;
            text-align: right;
        }

        .field_value {
            font-size: 13px !important;
            padding: 1px !important;
            text-align: left;
        }

        table.instructionDetailStandard tr td {
            padding: 5px !important;
        }

    </style>
</head>
<body>
<jsp:include page="header.jsp">
    <jsp:param name="url" value="59"/>
    <jsp:param name="page" value="106"/>
</jsp:include>
<div style="width: 600px ! important; padding: 15px; background: none repeat scroll 0% 0% white; height: auto; overflow: hidden"
     class="container_16 border" id="wrapper">
    <form:form action="packing-master.htm" method="post" commandName="packingMasterForm">
        <form:hidden path="item.id"/>
        <div style="width: 550px !important; min-height: 80px; height: auto; overflow: hidden;margin-right: 9px;"
             class="center">
            <p style="font-size: large">${packingMasterForm.item.name}</p>
            <div class="" style="">
                <table class="instructionDetailStandard" style="padding: 20px; width: 100%">
                    <tr>
                        <td class="title">Name:</td>
                        <td class="field_value">
                            <form:input path="item.name" type="text" style="width: 300px"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="title">Weight</td>
                        <td class="field_value">
                            <form:input path="item.weight" type="text" style="width: 300px"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="title">Kg Per Bag</td>
                        <td class="field_value">
                            <form:input path="item.kgPerBag" type="text" style="width: 300px"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="title">Bags Per Pallet</td>
                        <td class="field_value">
                            <form:input path="item.bagsPerPallet" type="text" style="width: 300px"/>
                        </td>
                    </tr>
                </table>
            </div>
            <input class="button ui-button ui-widget ui-state-default ui-corner-all left" type="submit" value="Save"/>
        </div>
    </form:form>
</div>
<script>
    $(document).ready(function () {
    });
</script>
<jsp:include page="footer.jsp"/>

</body>
</html>

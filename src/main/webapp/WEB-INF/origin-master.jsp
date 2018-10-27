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
    <title>Origin Master Detail</title>
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
    <jsp:param name="url" value="61"/>
    <jsp:param name="page" value="108"/>
</jsp:include>
<div style="width: 600px ! important; padding: 15px; background: none repeat scroll 0% 0% white; height: auto; overflow: hidden"
     class="container_16 border" id="wrapper">
    <form:form action="origin-master.htm" method="post" commandName="originMasterForm">
        <form:hidden path="item.id"/>
        <div style="width: 550px !important; min-height: 80px; height: auto; overflow: hidden;margin-right: 9px;"
             class="center">
            <p style="font-size: large">${originMasterForm.item.country.shortName}</p>
            <div class="" style="">
                <table class="instructionDetailStandard" style="padding: 20px; width: 100%">
                    <tr>
                        <td class="title">Country:</td>
                        <td class="field_value">
                            <form:select path="item.country" items="${countries}"
                                         itemValue="id" itemLabel="shortName"/></td>
                        </td>
                        <td>
                            <form:input path="item.origin" type="text" style="width: 300px"/>
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

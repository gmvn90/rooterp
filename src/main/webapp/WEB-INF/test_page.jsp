<%--
    Document   : stock_listview
    Created on : Dec 6, 2013, 2:19:58 PM
    Author     : kiendn
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Stock Plan</title>
    <style>
        .styleTotal td {
            border-top: 1px solid #000;
        }
        .bolder {
            font-size: 12px;
        }
    </style>
</head>
<body>
<jsp:include page="header.jsp">
    <jsp:param name="url" value="43"/>
    <jsp:param name="page" value="94"/>
</jsp:include>
<div style="width: 1200px ! important; padding: 15px; background: none repeat scroll 0% 0% white; height: auto; overflow: hidden; min-height: 400px" class="container_16 border" id="wrapper">
    hi
    <form:form action="test_page.htm" method="post" commandName="optionForm">
        <table border="0">
            <tr>
                <td colspan="2" align="center"><h2>Option form</h2></td>
            </tr>
            <tr>
                <td>Name:</td>
                <td>
                    <form:hidden path="option.id" />
                    <form:input path="option.name" />
                </td>
            </tr>
            <tr>
                <td>Option name:</td>
                <td><form:input path="option.optionName" /></td>
            </tr>


            <tr>
                <td colspan="5">
                    <form:select path="option.category" id="category_id" items="${categories}" itemValue="id" itemLabel="name" />
                </td>

            </tr>

            <tr>
                <td colspan="5">
                    <form:checkbox path="option.isUsd" />
                </td>

            </tr>
            <tr>
                <td colspan="2" align="center"><input type="submit" value="Register" /><input type="reset" value="Reset" /></td>
            </tr>

        </table>
    </form:form>
</div>
<jsp:include page="footer.jsp"/>

</body>
</html>

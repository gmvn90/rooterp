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
<jsp:include page="../header.jsp">
    <jsp:param name="url" value="69"/>
    <jsp:param name="page" value="118"/>
</jsp:include>
<div style="width: 600px ! important; padding: 15px; background: none repeat scroll 0% 0% white; height: auto; overflow: hidden"
     class="container_16 border" id="wrapper">
<c:if test="${is_form_edit}">
    <form:form action="lu_saveApprovalStatus.htm" method="post" commandName="luForm">
        <tr>
            <td></td>
            <td class="title">Approval Status:</td>
            <td class="field_value" colspan="3">
                <form:select path="item.approvalStatus"
                             style="width: 30%">
                    <form:options items="${approvalStatuses}"/>
                </form:select>
                <label>${luForm.item.approvalUser.userName}:<fmt:formatDate
                        pattern="dd-MMM-yy HH:mm:ss"
                        value="${luForm.item.approvalDate}"/></label>
                <c:if test="${permissonsMap.get('perm_111')}">
                    <input class="button ui-button ui-widget ui-state-default ui-corner-all" type="submit"
                           value="Save" onclick="disableSubmitAfterClick(this)"/>
                </c:if>
            </td>
        </tr>
    </form:form>
</c:if>
    <form:form action="lu-daily.htm" method="post" commandName="luForm">
        <form:hidden path="item.id"/>
        <div style="width: 550px !important; min-height: 80px; height: auto; overflow: hidden;margin-right: 9px;"
             class="center">
            <p style="font-size: large">${luForm.item.refNumber}</p>
            <div class="" style="">
                <table class="instructionDetailStandard" style="padding: 20px; width: 100%">
                    <tr>
                        <td class="title">Begin Expense</td>
                        <td class="field_value">
                            <form:input path="item.beginExpense" type="text" style="width: 300px"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="title">Begin Expense Detail</td>
                        <td class="field_value">
                            <form:textarea path="item.beginExpenseDetail"></form:textarea>
                        </td>
                    </tr>
                    <tr>
                        <td class="title">Income</td>
                        <td class="field_value">
                            <form:input path="item.income" type="text" style="width: 300px"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="title">In-Day Expense</td>
                        <td class="field_value">
                            <form:input path="item.indayExpense" type="text" style="width: 300px"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="title">In-Day Expense Detail</td>
                        <td class="field_value">
                            <form:textarea path="item.indayExpense"></form:textarea>
                        </td>
                    </tr>
                    <tr>
                        <td class="title">Total</td>
                        <td class="field_value">
                                ${luForm.item.total}
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
<jsp:include page="../footer.jsp"/>

</body>
</html>

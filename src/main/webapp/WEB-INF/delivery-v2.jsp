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
    <title>Delivery</title>
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

        table.instructionDetailStandard {
            border-collapse: collapse;
        }

        .title {
            font-weight: normal !important;
            font-size: 13px !important;
            padding: 1px !important;
            text-align: left;
        }

        .subTitle {
            font-weight: bold;
            padding-top: 2px !important;
            font-style: italic;
            text-align: left;
        }

        .field_value {
            font-weight: bold;
            font-size: 13px !important;
            padding: 1px !important;
            text-align: left;
        }

        .underline-bottom {
            border-bottom: 1px solid black;
        }

        table.instructionDetailStandard tr td {
            padding: 5px !important;

        }

    </style>
</head>
<body>
<jsp:include page="header.jsp">
    <jsp:param name="url" value="38"/>
    <jsp:param name="page" value="11"/>
</jsp:include>
<div style="width: 1100px ! important; padding: 15px; background: none repeat scroll 0% 0% white; overflow: hidden"
     class="container_16 border" id="wrapper">
    <div style="width: 190px; height: 500px" class="left">
        <div class="title center">
            Reference List
        </div>
        <div class="pc100">
            <input type="text" id="type_filtertext" class="text_filter_child"
                   style="width: 190px; margin-left: 5px">
            <ul class="ref_list filter" id="ship_list"
                style="margin-top: 15px; margin-left: 9px; padding-left: 10px; width: 157px !important">
                <c:forEach var="item" items="${dis}">
                    <li
                            <c:if test="${item.id == currentId}">class="chosen"</c:if> id="di-${item.id}"><a
                            style="display: block"
                            href="delivery-v2.htm?id=${item.id}#di-${item.id}">${item.value}</a>
                    </li>
                </c:forEach>
            </ul>
        </div>
    </div>
    <div style="width: 870px !important; margin-right: 0px !important;font-size: 9pt !important"
         class="right grid_14">


        <table class="instructionDetailStandard" style="padding: 20px; width: 100%">
            <c:if test="${is_form_edit}">
                <%--<form:form action="delivery_saveRequestStatus.htm" method="post" commandName="diForm">--%>
                    <%--<tr>--%>
                        <%--<td></td>--%>
                        <%--<td class="title">Request Status:</td>--%>
                        <%--<td class="field_value" colspan="3">--%>
                            <%--<form:select path="item.requestStatus" id="requestStatus"--%>
                                         <%--style="width: 30%">--%>
                                <%--<form:options items="${requestStatus}"/>--%>
                            <%--</form:select>--%>
                            <%--<label>${diForm.item.userByRequestUserId.userName}:<fmt:formatDate--%>
                                    <%--pattern="dd-MMM-yy HH:mm:ss" value="${diForm.item.requestDate}"/></label>--%>
                            <%--<input style="min-width: 10px"--%>
                                   <%--class="button ui-button ui-widget ui-state-default ui-corner-all"--%>
                                   <%--type="submit" value="Update" onclick="disableSubmitAfterClick(this)" />--%>
                        <%--</td>--%>
                    <%--</tr>--%>
                <%--</form:form>--%>
                <form:form action="delivery_saveCompletionStatus.htm" method="post" commandName="diForm">
                    <tr>
                        <td></td>
                        <td class="title">Completion Status:</td>
                        <td class="field_value" colspan="3">
                            <form:select path="item.status" id="status" style="width: 30%">
                                <form:options items="${completionStatus}"/>
                            </form:select>
                            <label>${diForm.item.userByUpdateUserId.userName}:<fmt:formatDate
                                    pattern="dd-MMM-yy HH:mm:ss" value="${diForm.item.updateDate}"/></label>
                            <input style="min-width: 10px"
                                   class="button ui-button ui-widget ui-state-default ui-corner-all"
                                   type="submit" value="Update" onclick="disableSubmitAfterClick(this)" />
                        </td>
                        <c:if test="${errorStatus != null}">
                            <script>
                                $.error("${errorStatus}");
                            </script>
                        </c:if>
                    </tr>
                </form:form>
            </c:if>
            <form:form action="delivery-v2.htm" method="post" commandName="diForm">
            <form:hidden path="item.id"/>
            <%--<tr class="underline-bottom" style="border-bottom: solid black 1px">--%>
                <%--<td></td>--%>
                <%--<td class="title">Request Remark</td>--%>
                <%--<td class="field_value" colspan="4">--%>
                    <%--<form:textarea path="item.requestRemark" id="requestRemark"--%>
                                   <%--></form:textarea>--%>
                <%--</td>--%>
            <%--</tr>--%>


            <tr>
                <td></td>
                <td class="title">DI Ref</td>
                <td class="field_value"><input type="text" id="refNumber" value="${diForm.item.refNumber}"
                                               disabled/></td>
                <td></td>
                <td class="title">Request Date</td>
                <td class="field_value"><input type="text" style="width: 100%" id="createdDate"
                                               value="<fmt:formatDate pattern="dd-MMM-yy" value="${diForm.item.date}"/>"
                                               disabled/></td>
            </tr>
            <tr>
                <td></td>
                <td class="title">Client</td>
                <td class="field_value"><form:select path="item.companyMasterByClientId" id="client"
                                                     items="${clients}"
                                                     itemValue="id" itemLabel="name"/></td>
                <td></td>
                <td class="title">Client Ref</td>
                <td class="field_value"><form:input path="item.clientRef" style="width: 100%"/></td>
            </tr>
            <tr>
                <td></td>
                <td class="title">Supplier</td>
                <td class="field_value"><form:select path="item.companyMasterBySupplierId" id="supplier"
                                                     items="${suppliers}"
                                                     itemValue="id" itemLabel="name"/></td>
                <td></td>
                <td class="title">Supplier Ref</td>
                <td class="field_value"><form:input path="item.supplierRef" style="width: 100%"/></td>
            </tr>
            <%--<tr>--%>
                <%--<td></td>--%>
                <%--<td class="title">Pledge</td>--%>
                <%--<td class="field_value"><form:select path="item.companyMasterByPledger" id="pledger">--%>
                    <%--<option value=""></option>--%>
                    <%--<form:options items="${banks}"--%>
                                  <%--itemValue="id" itemLabel="name"></form:options>--%>
                <%--</form:select></td>--%>
            <%--</tr>--%>
            <tr>
                <td></td>
                <td class="title">Origin</td>
                <td class="field_value"><form:select path="item.originMaster" id="origin" items="${origins}"
                                                     itemValue="id"
                                                     itemLabel="origin"/></td>
            </tr>
            <tr>
                <td></td>
                <td class="title">Quality</td>
                <td class="field_value"><form:select path="item.qualityMaster" id="quality" items="${qualities}"
                                                     itemValue="id"
                                                     itemLabel="name"/></td>
            </tr>
            <tr>
                <td></td>
                <td class="title">Grade</td>
                <td class="field_value"><form:select path="item.gradeMaster" id="grade" items="${grades}" itemValue="id"
                                                     itemLabel="name"/></td>
            </tr>
            <tr>
                <td></td>
                <td class="title">Deposit Ton</td>
                <td class="field_value"><form:input path="item.tons" class="numberOnly" type="text" style="width: 50%"
                /> Mts
                </td>
            </tr>
            <tr>
                <td></td>
                <td class="title">Delivered</td>
                <td class="field_value"><fmt:formatNumber type="number" minFractionDigits="3" maxFractionDigits="3" value="${diForm.item.deliverd}"/> Mts
                </td>
            </tr>
            <tr>
                <td></td>
                <td class="title">Pending</td>
                <td class="field_value"><fmt:formatNumber type="number" minFractionDigits="3" maxFractionDigits="3" value="${diForm.item.pending}"/> Mts
                </td>
            </tr>
            <tr>
                <td></td>
                <td class="title">Packing</td>
                <td class="field_value"><form:select path="item.packingMaster" id="packing" items="${packings}"
                                                     itemValue="id"
                                                     itemLabel="name"/></td>
            </tr>
            <%--<tr>--%>
                <%--<td></td>--%>
                <%--<td class="title">Location</td>--%>
                <%--<td class="field_value"><form:select path="item.locationMaster" id="location" items="${locations}"--%>
                                                     <%--itemValue="id"--%>
                                                     <%--itemLabel="name"/></td>--%>
            <%--</tr>--%>
            <%--<tr>--%>
                <%--<td></td>--%>
                <%--<td class="title">Weight & Quality</td>--%>
                <%--<td class="field_value"><form:select path="item.companyMasterByControllerId"--%>
                                                     <%--items="${controllers}" itemValue="id"--%>
                                                     <%--itemLabel="name"/></td>--%>
            <%--</tr>--%>
            <%--<tr>--%>
                <%--<td></td>--%>
                <%--<td class="title">First Date</td>--%>
                <%--<td class="field_value"><form:input path="item.firstDate" cssClass="js_date"/></td>--%>
                <%--<td></td>--%>
                <%--<td class="title">Last Date</td>--%>
                <%--<td class="field_value"><form:input path="item.lastDate" cssClass="js_date"/></td>--%>
            <%--</tr>--%>
            <tr>
                <td></td>
                <td class="title">Remark</td>
                <td class="field_value"><form:textarea path="item.remark"
                                                       style="width: 350px;height: 50px; margin-bottom: 25px"/></td>
            </tr>

        </table>
            <input class="button ui-button ui-widget ui-state-default ui-corner-all left" type="submit" value="Save" onclick="disableSubmitAfterClick(this)"/>
        <label class="left">${diForm.item.user.userName}:<fmt:formatDate pattern="dd-MMM-yy HH:mm:ss"
                                                                         value="${diForm.item.userUpdateDate}"/></label>
        <input class="right" type="button" value="Print"/>

        </form:form>

    </div>
</div>
<script>
    $(document).ready(function () {
        $(".js_date").datepicker({
            dateFormat: "d-M-y",
            changeMonth: true,
            changeYear: true,
            yearRange: "1945:2050"
        });

    }).on("click", "#createNew", function () {
        document.location = $(this).data('href');
    });
</script>
<jsp:include page="footer.jsp"/>

</body>
</html>

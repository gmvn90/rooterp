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
    <title>Sample Sent Type Detail</title>
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
            font-size: 13px !important;
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
<jsp:include page="../header.jsp">
    <jsp:param name="url" value="62"/>
    <jsp:param name="page" value="109"/>
</jsp:include>
<div style="width: 700px ! important; padding: 15px; background: none repeat scroll 0% 0% white; height: auto; overflow: hidden"
     class="container_16 border" id="wrapper">

    <div id="detail_content">
    <div style="width: 700px !important; min-height: 80px; height: auto; overflow: hidden;margin-right: 9px;"
         class="center">
        <table class="instructionDetailStandard" style="padding: 20px; width: 100%">
            <c:forEach items="${messages}" var="message">
                        <tr>
                            <td colspan="10">
                                <p class="alert alert-${message.type}">${message.message}</p>
                            </td>
                        </tr>
                        
                    </c:forEach>
            <c:if test="${is_form_edit}">
            <form:form action="sampleType_saveSendingStatus.htm" method="post" commandName="sendingStatusForm">
                <tr>
                    <td></td>
                    <td class="title">Sending Status:</td>
                    <td class="field_value" colspan="3">
                        <form:select path="status"
                                     style="width: 30%">
                            <form:options items="${sendingStatusForm.statuses}"/>
                        </form:select>
                        <form:hidden path="refNumber" />
                        <label>${sendingStatusForm.user}:${f:formatDateTime(sendingStatusForm.date)}</label>
                        <input class="button ui-button ui-widget ui-state-default ui-corner-all" type="submit"
                               value="Save" onclick="disableSubmitAfterClick(this)"/>
                    </td>
                </tr>
            </form:form>
            <form:form action="sampleType_saveApprovalStatus.htm" method="post" commandName="approvalStatusForm">
                <tr>
                    <td></td>
                    <td class="title">Approval Status:</td>
                    <td class="field_value" colspan="3">
                        <form:select path="status"
                                     style="width: 30%">
                            <form:options items="${approvalStatusForm.statuses}"/>
                        </form:select>
                        <form:hidden path="refNumber" />
                        <label>${approvalStatusForm.user}:${f:formatDateTime(approvalStatusForm.date)}</label>
                        <input class="button ui-button ui-widget ui-state-default ui-corner-all" type="submit"
                               value="Save" onclick="disableSubmitAfterClick(this)"/>
                    </td>
                </tr>
            </form:form>
                <tr>
                    <td colspan="3">
                        <table>
                            <c:forEach items="${item.documents}" var="single">
                            <tr>
                                <td>
                                    <a href="${single.url}" target="_blank">${single.originalName}</a>
                                </td>
                                <td>
                                    ${single.updater}: ${f:formatDateTimeAMPM(single.created)}
                                </td>
                            </tr>
                            </c:forEach>
                            <form method="POST" action="${base_web_url}/sampleType/${item.refNumber}/add-document.htm" enctype="multipart/form-data">
                                
                                    <tr>
                                        <td>
                                            <input type="file" name="file" />
                                        </td>
                                        <td>
                                            <input type="submit" value="Upload" />
                                        </td>
                                    </tr>
                                    
                                
                            </form>
                        </table>

                    </td>
                </tr>
            </c:if>
        <form:form action="sample-sent-type.htm" method="post" commandName="item">
            <tr class="underline-bottom" style="border-bottom: solid black 1px">
                <td></td>
                <td class="title">Remark:</td>
                <td class="field_value" colspan="4">
                    <form:textarea path="remark"/>
                </td>
            </tr>


            <tr>
                <td class="subTitle" colspan="3">Sample Details:</td>
            </tr>
            <tr>
                <td></td>
                <td class="title">Sample Ref.:</td>
                <td class="field_value">
                    ${item.refNumber}
                </td>
                <td></td>
                <td class="title">Sample Date:</td>
                <td class="field_value">
                    ${f:formatDateOnly(item.createdDate)}
                </td>
            </tr>
            <tr>
                <td></td>
                <td class="title">Ref.:</td>
                <td class="field_value">
                    ${item.typeSampleRef}
                </td>
                <td></td>
            </tr>
            <tr>
                <td class="subTitle" colspan="3">Party Details:</td>
            </tr>
            <tr>
                <td></td>
                <td class="title">Client:</td>
                <td class="field_value">
                    <form:select path="client" id="client">
                        <option value=""></option>
                        <form:options items="${clients}"
                                      itemValue="id" itemLabel="name"></form:options>
                    </form:select>
                </td>
                <td></td>
                <td class="title">Client Ref.:</td>
                <td class="field_value">
                    <form:input path="clientRef"/>
                </td>
            </tr>
            <tr>
                <td></td>
                <td class="title">Supplier:</td>
                <td class="field_value">
                    <form:select path="supplier" id="supplier">
                        <option value=""></option>
                        <form:options items="${suppliers}"
                                      itemValue="id" itemLabel="name"></form:options>
                    </form:select>
                </td>
                <td></td>
                <td class="title">Supplier Ref.:</td>
                <td class="field_value">
                    <form:input path="supplierRef"/>
                </td>
            </tr>
            <tr>
                <td></td>
                <td class="title">Shipper:</td>
                <td class="field_value">
                    <form:select path="shipper" id="shipper">
                        <option value=""></option>
                        <form:options items="${shippers}"
                                      itemValue="id" itemLabel="name"></form:options>
                    </form:select>
                </td>
                <td></td>
                <td class="title">Shipper Ref.:</td>
                <td class="field_value">
                    <form:input path="shipperRef"/>
                </td>
            </tr>
            <tr>
                <td></td>
                <td class="title">Buyer:</td>
                <td class="field_value">
                    <form:select path="buyer" id="buyer">
                        <option value=""></option>
                        <form:options items="${buyers}"
                                      itemValue="id" itemLabel="name"></form:options>
                    </form:select>
                </td>
                <td></td>
                <td class="title">Buyer Ref.:</td>
                <td class="field_value">
                    <form:input path="buyerRef"/>
                </td>
            </tr>

            <tr>
                <td class="subTitle" colspan="3">Quality Details:</td>
            </tr>
            <tr>
                <td></td>
                <td class="title">Origin:</td>
                <td class="field_value">
                    <form:select path="origin" id="origin" items="${origins}"
                                 itemValue="id"
                                 itemLabel="country.shortName"/>
                </td>
            </tr>
            <tr>
                <td></td>
                <td class="title">Quality:</td>
                <td class="field_value">
                    <form:select path="quality" id="quality" items="${qualities}"
                                 itemValue="id"
                                 itemLabel="name"/>
                </td>
            </tr>
            <tr>
                <td></td>
                <td class="title">Grade:</td>
                <td class="field_value">
                    <form:select path="grade" id="grade" items="${grades}" itemValue="id"
                                 itemLabel="name"/>
                </td>
            </tr>
                <form:hidden path="id"/>
                <tr>
                    <td class="subTitle" colspan="3">Sample Sent Details:</td>
                </tr>
                <tr>
                    <td></td>
                    <td class="title">User Remark:</td>
                    <td colspan="3" class="field_value">
                        <form:textarea path="userRemark"/>
                    </td>
                </tr>
                <tr>
                    <td></td>
                    <td class="title">Courier:</td>
                    <td colspan="3" class="field_value">
                        <form:select path="courier" items="${couriers}" itemValue="id" itemLabel="name"
                                     style="width: 50%">
                            <option value=""></option>
                        </form:select>
                    </td>
                </tr>
                <tr>
                    <td></td>
                    <td class="title">Tracking No.:</td>
                    <td class="field_value" colspan="3">
                        <form:input path="trackingNo" value=""/>
                    </td>
                </tr>
                <tr>
                    <td></td>
                    <td class="title">Sent Date:</td>
                    <td class="field_value" colspan="3">
                        <form:input path="sentDate" type="text" cssClass="js_date"/>
                    </td>
                </tr>
                <tr>
                    <td></td>
                    <td class="title">ETA Date:</td>
                    <td class="field_value" colspan="3">
                        <form:input path="etaDate" type="text" cssClass="js_date"/>
                    </td>
                </tr>
                <tr>
                    <td></td>
                    <td class="title">Received by:</td>
                    <td class="field_value">
                        <form:input path="recipient" type="text" />
                    </td>
                    
                    <td colspan="3">
                        <p>Lot Ref:</p>
                        <form:textarea path="lotRef" />
                    </td>
                    
                </tr>
                <tr>
                    <td class="title" colspan="2">Authorized By:</td>
                    <td class="field_value" colspan="4"><label>${item.user}:<fmt:formatDate
                            pattern="dd-MMM-yy HH:mm:ss" value="${item.updatedDate}"/></label><input
                            class="button ui-button ui-widget ui-state-default ui-corner-all" type="submit"
                            value="Save" onclick="disableSubmitAfterClick(this)"/>
                        <input
                                class="button ui-button ui-widget ui-state-default ui-corner-all"
                                value="Print" id="btn_print"/>
                    </td>
                </tr>
            </form:form>
        </table>
    </div>
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
    }).on("click", "#btn_print", function() {
        $("select").each(function () {
            $(this).find(":selected").attr("selected", true);
        });
        $("input[type=checkbox]").filter(":checked").attr("checked", true);
        $("#detail_content").printThis();
    });
</script>
<jsp:include page="../footer.jsp"/>

</body>
</html>

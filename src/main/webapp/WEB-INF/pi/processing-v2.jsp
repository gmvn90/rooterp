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
    <title>Processing</title>
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
<jsp:include page="../header.jsp">
    <jsp:param name="url" value="43"/>
    <jsp:param name="page" value="14"/>
</jsp:include>
<div id="main_content"
     style="width: 1000px ! important; padding: 15px; background: none repeat scroll 0% 0% white; height: auto; overflow: hidden"
     class="container_16 border" id="wrapper">
    <div class="left" style="width: 200px !important; margin-left: 0px; margin-right: 5px">
        <div class="title">
            Processing Ref.
        </div>
        <div class="pc100">
            <input type="text" id="type_filtertext" class="text_filter_child"
                   style="width: 190px; margin-left: 5px">
            <ul class="ref_list filter" id="ship_list"
                style="margin-top: 15px; margin-left: 9px; padding-left: 10px; width: 157px !important">
                <c:forEach var="item" items="${pis}">
                    <li
                            <c:if test="${item.id == currentId}">class="chosen"</c:if> id="pi-${item.id}"><a
                            style="display: block"
                            href="processing-v2.htm?id=${item.id}">${item.value}</a>
                    </li>
                </c:forEach>
            </ul>
        </div>
        <div class="title center" style="margin-top: 20px">
            <input class="" id="createNew" data-href="processing-v2.htm" type="button" value="Create new"/>
        </div>
    </div>
    <div class="right" style="width: 750px !important;margin-left: 0px; margin-right: 5px">
        <table class="instructionDetailStandard" style="padding: 0px; width: 100%">
            <c:forEach items="${messages}" var="message">
                <tr>
                    <td colspan="10">
                        <p class="alert alert-${message.type}">${message.message}</p>
                    </td>
                </tr>

            </c:forEach>
            <c:if test="${is_form_edit}">
                <form:form action="processing_saveRequestStatus.htm" method="post" commandName="requestStatusForm">
                    <tr>
                        <td></td>
                        <td class="title">Request Status:</td>
                        <td class="field_value" colspan="3">
                            <form:select path="status" id="requestStatus"
                                         style="width: 30%">
                                <form:options items="${requestStatusForm.statuses}"/>
                            </form:select>
                            <label>${requestStatusForm.user}:${f:formatDateTime(requestStatusForm.date)}</label>
                            <form:hidden path="refNumber" />
                            
                            <input style="min-width: 10px"
                                   class="button ui-button ui-widget ui-state-default ui-corner-all" type="submit"
                                   value="Update" onclick="disableSubmitAfterClick(this)"/>
                        </td>
                    </tr>
                </form:form>

                <form:form action="processing_saveCompletionStatus.htm" method="post" commandName="completionStatusForm">
                    <tr>
                        <td></td>
                        <td class="title">Completion Status:</td>
                        <td class="field_value">
                            <form:select path="status" id="status" style="width: 30%">
                                <form:options items="${completionStatusForm.statuses}"/>
                            </form:select>
                            <label>${completionStatusForm.user}:${f:formatDateTime(completionStatusForm.date)}</label>
                            <form:hidden path="refNumber" />
                            <input style="min-width: 10px"
                                   class="button ui-button ui-widget ui-state-default ui-corner-all" type="submit"
                                   value="Update" onclick="disableSubmitAfterClick(this)"/>
                        </td>
                    </tr>
                    <c:if test="${errorStatus != null}">
                        <script>
                            $.error("${errorStatus}");
                        </script>
                    </c:if>

                </form:form>

            </c:if>
            <form:form action="processing-v2.htm" method="post" commandName="piForm">
                <form:hidden path="item.id"/>
                <tr class="underline-bottom" style="border-bottom: solid black 1px">
                    <td></td>
                    <td class="title">Request Remarks:</td>
                    <td class="field_value" style="border-bottom: solid 0.5px; margin-bottom: 25px;">
                        <form:textarea path="item.requestRemark" id="requestRemark"></form:textarea>
                    </td>
                </tr>

                <tr>
                    <td></td>
                    <td class="title">Processing Ref:</td>
                    <td class="field_value">
                        <input type="text" style="width: 100%" id="refNumber" value="${piForm.item.refNumber}"
                               disabled/>
                    </td>
                </tr>

                <tr>
                    <td></td>
                    <td class="title">Date:</td>
                    <td class="field_value">
                        <input type="text" style="width: 100%" id="createdDate"
                               value="<fmt:formatDate pattern="dd-MMM-yy" value="${piForm.item.createdDate}"/>"
                               disabled/>
                    </td>
                </tr>

                <tr>
                    <td></td>
                    <td class="title">Client:</td>
                    <td class="field_value">
                        <form:select path="item.companyMasterByClientId" items="${clients}" itemValue="id" itemLabel="name"
                                     id="client" style="width: 100%">
                            <option value=""></option>
                        </form:select>
                    </td>
                </tr>

                <tr>
                    <td></td>
                    <td class="title">Client Ref:</td>
                    <td class="field_value">
                        <form:input path="item.clientRef" type="text" style="width: 100%" id="clientRef" value=""/>
                    </td>
                </tr>

                <tr>
                    <td></td>
                    <td class="title">Type:</td>
                    <td class="field_value">
                        <form:select style="width: 100%" path="item.piType" id="piType">
                            <option value=""></option>
                            <form:options items="${piTypes}"
                                          itemValue="id" itemLabel="name"></form:options>
                        </form:select>
                    </td>
                </tr>

                <tr>
                    <td></td>
                    <td class="title">Grade:</td>
                    <td class="field_value">
                        <form:select path="item.gradeMaster" items="${grades}" itemValue="id" itemLabel="name"
                                     id="grade" style="width: 100%">
                            <option value=""></option>
                        </form:select>
                    </td>
                </tr>

                <tr>
                    <td></td>
                    <td class="title">Quantity:</td>
                    <td class="field_value">
                        <form:input path="item.quantity" class="numberOnly" type="text" style="width: 50%"/>
                        Mts
                    </td>
                </tr>
            <tr>
                <td></td>
                <td class="title">Allocated:</td>
                <td class="field_value"><fmt:formatNumber type="number" minFractionDigits="3" maxFractionDigits="3" value="${piForm.item.allocatedWeight}"/> Mts
                </td>
            </tr>
            <tr>
                <td></td>
                <td class="title">Inprocess:</td>
                <td class="field_value"><fmt:formatNumber type="number" minFractionDigits="3" maxFractionDigits="3" value="${piForm.item.inProcessWeight}"/> Mts
                </td>
            </tr>

            <tr>
                <td></td>
                <td class="title">Exprocess:</td>
                <td class="field_value"><fmt:formatNumber type="number" minFractionDigits="3" maxFractionDigits="3" value="${piForm.item.exProcessWeight}"/> Mts
                </td>
            </tr>
            <tr>
                <td></td>
                <td class="title">Pending:</td>
                <td class="field_value"><fmt:formatNumber type="number" minFractionDigits="3" maxFractionDigits="3" value="${piForm.item.pendingWeight}"/> Mts
                </td>
            </tr>

                <tr>
                    <td></td>
                    <td class="title">Packing:</td>
                    <td class="field_value">
                        <form:select path="item.packingMaster" items="${packings}" itemValue="id" itemLabel="name"
                                     id="packing" style="width: 100%">
                            <option value=""></option>
                        </form:select>
                    </td>
                </tr>

                <tr>
                    <td></td>
                    <td class="title">Requested Credit Date:</td>
                    <td class="field_value">
                        <form:input path="item.creditDate" type="text" style="width: 100%" cssClass="js_date"
                                    id="requestedCreditDate" value=""/>
                    </td>
                </tr>

                <tr>
                    <td></td>
                    <td class="title">Remark:</td>
                    <td class="field_value">
                        <form:textarea path="item.remark" id="remark"
                                       style="width: 530px; height: 50px"></form:textarea>
                    </td>
                </tr>
        </table>
                <div class="right" style="width: 700px; margin-right: 3px; margin-top: 40px">
                    <input class="button ui-button ui-widget ui-state-default ui-corner-all" type="submit" value="Save" onclick="disableSubmitAfterClick(this)" />
                    <label class="">${piForm.item.user.userName}:<fmt:formatDate pattern="dd-MMM-yy HH:mm:ss"
                                                                                 value="${piForm.item.updatedDate}"/></label>
                    <input class="right" type="button" name="perm_8" id="btn_allocation" value="Allocation"/>
                    <input class="right" type="button" id="btn_report" value="Print" name="perm_50"/>
                </div>
            </form:form>


    </div>

    <script>
        $(document).ready(function () {
            $(".js_date").datepicker({
                dateFormat: "d-M-y",
                changeMonth: true,
                changeYear: true,
                yearRange: "1945:2050"
            });
        }).on("click", "#btn_report", function () {
            if ($(".ref_list li.chosen").html() !== undefined) {
                var po_id = $(".ref_list li.chosen").attr("id").split("-")[1];
                $.sendRequest({
                    status: "export",
                    action: "processing_report",
                    data: {po_id: po_id}
                });
            }
        }).on("click", "#btn_allocation", function () {
            $(".ref_list li").each(function () {
                if ($(this).hasClass("chosen")) {
                    var ins_id = $(".ref_list li.chosen").attr("id").split("-")[1];
                    create_hidden_form({
                        "id": "allocation_form",
                        "action": getAbsolutePath() + "/Allocation/detail.htm",
                        "method": "POST"
                    }, [{"id": "ins_id", "name": "ins_id", "value": ins_id}, {
                        "id": "ins_type",
                        "name": "ins_type",
                        "value": "P"
                    }, {id: "fw_type", name: "fw_type", value: "alloc"}]);
                    $("#allocation_form").submit();
                }
            });
        }).on("click", "#createNew", function () {
            document.location = $(this).data('href');
        });
    </script>
</div>
<jsp:include page="../footer.jsp"/>

</body>
</html>

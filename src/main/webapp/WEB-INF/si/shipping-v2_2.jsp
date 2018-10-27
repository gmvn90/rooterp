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
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="f" uri="http://swcommodities.com/formatFunctions" %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Shipping</title>
    <style>
        #sasRow, #ssRow {
            border-collapse: collapse;
        }

        #sasRow td, #sasRow th, #ssRow td, #ssRow th {
            border: 1px solid black;
        }
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
    
    <c:set var="resources" value="${pageContext.request.contextPath}" />
    <script src="${resources}/js/angular.min.js"></script>
    <script src="${resources}/js/angular-ui-router.js"></script>
    
    
    <script>
        var isCompleted = ${isCompleted};
        var shipping_instruction_id = ${currentId};
        var exchangeItem = ${exchangeString};
        var categoryList = ${categoryString};
        var is_form_edit = ${is_form_edit};
        var units = ${units};
        <c:if test="${shippingForm.client != null}">
        var globalClientId = ${shippingForm.client};
        </c:if>
        <c:if test="${shippingForm.client == null}">
        var globalClientId = "";
        </c:if>
        <c:if test="${is_form_edit}">
        var customCosts = ${customCosts};
        </c:if>
        var singleDocumentCost = ${singleDocumentCost};
        var fromBackend = ${shippingFormJS};
    </script>
    <script src="${resources}/js/app.js?version=${version}"></script>
    <script src="${resources}/js/model.js?version=${version}"></script>
</head>
<body ng-app="app">
<jsp:include page="../header.jsp">
    <jsp:param name="url" value="40"/>
    <jsp:param name="page" value="12"/>
</jsp:include>
<div id="main_content"
     style="width: 1300px ! important; padding: 15px; background: none repeat scroll 0% 0% white; height: auto; overflow: hidden"
     class="container_16 border" id="wrapper" ng-controller="ParentController" data-ng-init="init()">
    <div style="width: 200px;padding-bottom: 10px;" class="left">
        <div class="title">
            SI Ref.
        </div>
        <div class="pc100">
            <input type="text" id="type_filtertext" class="text_filter_child"
                   style="width: 190px; margin-left: 5px">
            <ul class="ref_list filter" id="ship_list"
                style="margin-left: 5px; padding-left: 10px; width: 168px !important">
                <c:forEach var="itemsi" items="${sis}">
                    <li
                            id="si-${itemsi.id}"><a
                            style="display: block" href="shipping-v2.htm?id=${itemsi.id}#si-${itemsi.id}">${itemsi.value}</a>
                    </li>
                </c:forEach>
            </ul>
        </div>
        <div class="title center" style="margin-top: 20px">
            <input class="" id="createNew" data-href="shipping-v2.htm" type="button" value="Create new"/>
        </div>
    </div>
    <div style="width: 1065px !important; margin-right: 0px !important;" class="right grid_14 ">
        <div id="detail_content">
            <div id="detail_content_no_cost">
            <div style="width: 1060px !important; min-height: 80px; height: auto; overflow: hidden;"
                 class="">
                
                <table class="instructionDetailStandard" style="margin-left: 60px">
                    <c:forEach items="${messages}" var="message">
                        <tr>
                            <td colspan="10">
                                <p class="alert alert-${message.type}">${message.message}</p>
                            </td>
                        </tr>
                        
                    </c:forEach>
                    
                    <c:if test="${is_form_edit}">
                        <form:form action="shipping_saveRequestStatus.htm" method="post" commandName="requestStatusForm">
                            <tr>
                                <td></td>
                                <td class="title">Request Status</td>
                                <td colspan="2" class="field_value">
                                    <form:select path="status" id="requestStatus" style="width: 40%">
                                        <form:options items="${requestStatusForm.statuses}"/>
                                    </form:select>
                                    <label>${requestStatusForm.user}:${f:formatDateTime(requestStatusForm.date)}</label>
                                    <form:hidden path="refNumber" />
                                </td>
                                <td>
                                    <input style="min-width: 10px"
                                           class="button ui-button ui-widget ui-state-default ui-corner-all"
                                           type="submit" value="Update Status" onclick="disableSubmitAfterClick(this);"/>
                                </td>
                            </tr>
                        </form:form>
                        <form:form action="shipping_saveShipmentStatus.htm" method="post" commandName="shipmentStatusForm">
                            
                            <tr>
                                <td>
                                    
                                </td>
                                <td class="title">Shipment status</td>
                                <td colspan="2" class="field_value">
                                    <form:select path="status" style="width: 40%">
                                        <form:options items="${shipmentStatusForm.statuses}"/>
                                    </form:select>
                                    <form:hidden path="refNumber" />
                                    <label>${shipmentStatusForm.user}:${f:formatDateTime(shipmentStatusForm.date)}</label>
                                </td>
                                <td>
                                    <input style="min-width: 10px"
                                           class="button ui-button ui-widget ui-state-default ui-corner-all"
                                           type="submit" value="Update Status" onclick="disableSubmitAfterClick(this)"/>
                                </td>
                            </tr>
                        </form:form>
                        <form:form action="shipping_saveCompletionStatus.htm" method="post" commandName="completionStatusForm">
                            <tr>
                                <td></td>
                                <td class="title">Completion Status</td>
                                <td colspan="2" class="field_value">
                                    <form:select path="status" style="width: 40%">
                                        <form:options items="${completionStatusForm.statuses}"/>
                                    </form:select><label>${completionStatusForm.user}:${f:formatDateTime(completionStatusForm.date)}
                                    <form:hidden path="refNumber" />
                                </td>
                                <td>
                                    <input style="min-width: 10px"
                                           class="button ui-button ui-widget ui-state-default ui-corner-all"
                                           type="submit" value="Update Status" name="perm_122"/>
                                </td>
                            </tr>
                        </form:form>

                        

                        
                        <!-- List created transaction -->

                        <tr>
                            <td></td>
                            <td>Transaction List</td>
                            <td colspan="2">
                                <table>

                                    <tbody>
                                        <c:forEach var="item" items="${transactions}">
                                                    <tr>
                                                        <td><a href="transaction.htm?id=${item.id}">${item.refNumber}</a></td>
                                                        <td>${f:formatMoneyWithComma(item.withdrawCost)}</td>
                                                        <td>${f:formatDateTimeAMPM(item.created)}</td>
                                                        <td>${item.createdUser}</td>
                                                    </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </td>
                        </tr>


                    </c:if>
        <form:form action="shipping-v2.htm" method="post" modelAttribute="shippingForm" id="shippingForm" ng-submit="actionWhenMainFormSubmit($event)">
            <form:hidden path="id"/>
                    <form:hidden path="version"/>
                    <tr class="underline-bottom" style="border-bottom: solid black 1px">
                        <td></td>
                        <td class="title">Remark:</td>
                        <td colspan="4" class="field_value">
                            <form:textarea path="remark"
                                           style="width: 575px; height: 50px" />
                        </td>
                    </tr>
                    <tr>
                        <td></td>
                        <td class="title">SI Ref:</td>
                        <td class="field_value">
                            <input type="text" value="${shippingForm.refNumber}"
                                   disabled/>
                            <form:hidden path="refNumber" />
                        </td>
                        <td class="title">SI Date:</td>
                        <td class="field_value">
                            <input type="text" style="width: 100%" id="createdDate" value="${f:formatDateTime(shippingForm.date)}" disabled/>
                        </td>
                    </tr>

                    

                    <tr>
                        <td colspan="2">
                            Parties Details:
                        </td>
                    </tr>
                    <tr>
                        <td></td>
                        <td class="title">Client:</td>
                        <td class="field_value">
                            
                            <form:select path="client" id="client" items="${clients}"
                                         itemValue="id" itemLabel="name" disabled="${isCompleted}" />

                        </td>
                        <td class="title">Client Ref.:</td>
                        <td class="field_value">
                            <form:input path="clientRef" id="clientRef" style="width: 100%" disabled="${isCompleted}"/>
                        </td>
                    </tr>
                    <tr>
                        <td></td>
                        <td class="title">Supplier:</td>
                        <td class="field_value">
                            <form:select path="supplier" id="supplier" disabled="${isCompleted}">
                                <option value=""></option>
                                <form:options items="${suppliers}"
                                              itemValue="id" itemLabel="name"></form:options>
                            </form:select>
                        </td>
                        <td class="title">Supplier Ref.:</td>
                        <td class="field_value">
                            <form:input path="supplierRef" id="supplierRef" style="width: 100%" disabled="${isCompleted}"/>
                        </td>
                    </tr>
                    <tr>
                        <td></td>
                        <td class="title">Shipper:</td>
                        <td class="field_value">
                            <form:select path="shipper" id="shipper" disabled="${isCompleted}">
                                <option value=""></option>
                                <form:options items="${shippers}"
                                              itemValue="id" itemLabel="name"></form:options>
                            </form:select>

                        </td>
                        <td class="title">Shipper Ref.:</td>
                        <td class="field_value">
                            <form:input path="shipperRef" id="shipperRef" style="width: 100%" disabled="${isCompleted}"/>
                        </td>
                    </tr>
                    <tr>
                        <td></td>
                        <td class="title">Buyer:</td>
                        <td class="field_value">
                            <form:select path="buyer" id="buyer" disabled="${isCompleted}">
                                <option value=""></option>
                                <form:options items="${buyers}"
                                              itemValue="id" itemLabel="name"></form:options>
                            </form:select>
                        </td>
                        <td class="title">Buyer Ref.:</td>
                        <td class="field_value">
                            <form:input path="buyerRef" id="buyerRef" style="width: 100%" disabled="${isCompleted}"/>
                        </td>
                    </tr>
                    <tr>
                        <td></td>
                        <td class="title">Consignee:</td>
                        <td class="field_value">
                            <form:select path="consignee" id="consignee" disabled="${isCompleted}">
                                <option value=""></option>
                                <form:options items="${consignees}"
                                              itemValue="id" itemLabel="name"></form:options>
                            </form:select>
                        </td>
                    </tr>

                    <tr>
                        <td></td>
                        <td class="title">Notify Parties:<a class="mpcth-icon-plus-squared notify_but" id="cloneNotify" href="javascript:void(0)"></a>  <a class="mpcth-icon-minus-squared notify_but" id="deleteNotify" href="javascript:void(0)"></a></td>
                        <td class="field_value">
                            <table id="notifyContainer">
                                <tbody>
                                    <tr ng-repeat="item in options.notifyParties"><td>{{item.name}}</td></tr>
                                    <tr class="notifyRow">
                                        <td>
                                            <select class="notifyParty" ng-model="currentNotifyParty" ng-disabled="isCostDisabled()">
                                                <c:forEach items="${notifiers}" var="item">
                                                    <option value="${item.id}">${item.name}</option>
                                                </c:forEach>
                                            </select>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </td>
                    </tr>
                    
                    <tr>
                        <td colspan="2">
                            Shipping Details:
                        </td>
                    </tr>
                    <tr>
                        <td></td>
                        <td class="title">Shipping Period: First Date:</td>
                        <td class="field_value">
                            <form:input path="fromDate" value="${f:formatDateOnlyWithTwoNumberYear(shippingForm.fromDate)}" cssClass="js_date" id="fromDate" disabled="${isCompleted}"/>
                        </td>
                        <td class="title">Last Date:</td>
                        <td class="field_value">
                            <form:input path="toDate" value="${f:formatDateOnlyWithTwoNumberYear(shippingForm.toDate)}" cssClass="js_date" id="toDate" disabled="${isCompleted}"/>
                        </td>
                    </tr>
                    <tr>
                        <td></td>
                        <td class="title">Shipping Line:</td>
                        <td class="field_value">
                            <form:select path="shippingLine" id="shippingLine" disabled="${isCompleted}">
                                <option value=""></option>
                                <form:options items="${shippingLines}"
                                              itemValue="id" itemLabel="name"></form:options>
                            </form:select>
                        </td>
                        <td class="title">Service Ct. No.:</td>
                        <td class="field_value">
                            <form:input path="serviceContractNo" id="serviceContractNo" style="width: 100%" disabled="${isCompleted}"/>
                        </td>
                    </tr>
                    <tr>
                        <td></td>
                        <td class="title">Feeder Vessel:</td>
                        <td class="field_value">
                            <form:input path="feederVessel" id="feederVessel" style="width: 100%" disabled="${isCompleted}"/>
                        </td>
                        <td class="title">Etd:</td>
                        <td class="field_value">
                            <form:input path="feederEts" value="${f:formatDateOnlyWithTwoNumberYear(shippingForm.feederEts)}" id="feederEts" cssClass="js_date" disabled="${isCompleted}"/>
                        </td>
                        <td class="title">Eta:</td>
                        <td class="field_value">
                            <form:input path="feederEta" value="${f:formatDateOnlyWithTwoNumberYear(shippingForm.feederEta)}" id="feederEta" cssClass="js_date" disabled="${isCompleted}"/>
                        </td>
                    </tr>
                    <tr>
                        <td></td>
                        <td class="title">Mother Vessel:</td>
                        <td class="field_value">
                            <form:input path="oceanVessel" id="oceanVessel" style="width: 100%" disabled="${isCompleted}"/>
                        </td>
                        <td class="title">Etd:</td>
                        <td class="field_value">
                            <form:input path="oceanEts" value="${f:formatDateOnlyWithTwoNumberYear(shippingForm.oceanEts)}" id="oceanEts" cssClass="js_date" disabled="${isCompleted}"/>
                        </td>
                        <td class="title">Eta:</td>
                        <td class="field_value">
                            <form:input path="oceanEta" value="${f:formatDateOnlyWithTwoNumberYear(shippingForm.oceanEta)}" id="oceanEta" cssClass="js_date" disabled="${isCompleted}"/>
                        </td>
                    </tr>
                    <tr>
                        <td></td>
                        <td class="title">Point of Loading:</td>
                        <td class="field_value">
                            <form:select path="loadingPort" id="loadingPort" disabled="${isCompleted}">
                                <option value=""></option>
                                <form:options items="${ports}"
                                              itemValue="id" itemLabel="name"></form:options>
                            </form:select>
                        </td>
                        <td class="title">Loading date:</td>
                        <td class="field_value">
                            <form:input path="loadDate" value="${f:formatDateOnlyWithTwoNumberYear(shippingForm.loadDate)}" cssClass="js_date" id="loadDate" disabled="${isCompleted}"/>
                        </td>
                    </tr>
                    <tr>
                        <td></td>
                        <td class="title">Booking Ref.:</td>
                        <td class="field_value">
                            <form:input path="bookingRef" id="bookingRef" style="width: 100%" disabled="${isCompleted}"/>
                        </td>
                        <td class="title">Closing Date:</td>
                        <td class="field_value">
                            <form:input path="closingDate" value="${f:formatDateOnlyWithTwoNumberYear(shippingForm.closingDate)}" id="closingDate" cssClass="js_date" disabled="${isCompleted}"/>
                        </td>
                        <td class="title">Closing Time:</td>
                        <td class="field_value">
                            <form:input path="closingTime" id="closingTime" style="width: 100%" disabled="${isCompleted}"/>
                        </td>
                    </tr>
                    <tr>
                        <td></td>
                        <td class="title">Transit Port:</td>
                        <td class="field_value">
                            <form:select path="transitPort" id="transitPort" disabled="${isCompleted}">
                                <option value=""></option>
                                <form:options items="${ports}"
                                              itemValue="id" itemLabel="name"></form:options>
                            </form:select>
                        </td>
                        <td class="title">Full Cont. Return:</td>
                        <td class="field_value">
                            <form:input path="fullContReturn" id="fullContReturn" style="width: 100%" disabled="${isCompleted}"/>
                        </td>
                    </tr>
                    <tr>
                        <td></td>
                        <td class="title">Point of Discharge:</td>
                        <td class="field_value">
                            <form:select path="dischargePort" id="dischargePort" disabled="${isCompleted}">
                                <option value=""></option>
                                <form:options items="${ports}"
                                              itemValue="id" itemLabel="name"></form:options>
                            </form:select>
                        </td>
                        <td class="title">Container Status:</td>
                        <td class="field_value">
                            <form:select path="containerStatus" id="containerStatus" style="width: 30%" disabled="${isCompleted}">
                                <form:options items="${containerStatus}"/>
                            </form:select>
                        </td>
                    </tr>
                    <tr>
                        <td></td>
                        <td class="title">B/L Number:</td>
                        <td class="field_value">
                            <form:input path="blNumber" id="blNumber" style="width: 100%" disabled="${isCompleted}"/>
                        </td>
                        <td class="title">B/L Date:</td>
                        <td class="field_value">
                            <form:input path="blDate" value="${f:formatDateOnlyWithTwoNumberYear(shippingForm.blDate)}" id="blDate" cssClass="js_date" disabled="${isCompleted}"/>
                        </td>
                    </tr>
                    <tr>
                        <td></td>
                        <td class="title">ICO Number:</td>
                        <td class="field_value">
                            <form:input path="icoNumber" id="icoNumber" style="width: 100%" disabled="${isCompleted}"/>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            Allocation Details:
                        </td>
                    </tr>
                    <tr>
                        <td></td>
                        <td class="title">Origin:</td>
                        <td class="field_value">
                            <form:select path="origin" id="origin" items="${origins}" itemValue="id"
                                         itemLabel="country.shortName" disabled="${isCompleted}"/>
                        </td>
                        <td class="title">Quality:</td>
                        <td class="field_value">
                            <form:select path="quality" id="quality" items="${qualities}" itemValue="id"
                                         itemLabel="name" disabled="${isCompleted}"/>
                        </td>
                    </tr>
                    <tr>
                        <td></td>
                        <td class="title">Allocation Grade:</td>
                        <td class="field_value">
                            <form:select path="allocationGrade" id="allocationGrade" disabled="${isCompleted}">
                                <option value=""></option>
                                <form:options items="${grades}"
                                              itemValue="id" itemLabel="name"></form:options>
                            </form:select>
                        </td>
                        <td class="title">Document Grade:</td>
                        <td class="field_value">
                            <form:select path="grade" id="grade" items="${grades}"
                                         itemValue="id" itemLabel="name" disabled="${isCompleted}"/>
                        </td>
                    </tr>
                    <tr>
                        <td></td>
                        <td class="title">Allocation Tons:</td>
                        <td class="field_value">
                            
                            <form:input id="totalTons" path="totalTons" form="shippingForm" ng-model="options.totalTons"  class="numberOnly" type="text" style="width: 50%" disabled="${isCompleted}"/> Mts


                        </td>
                    </tr>

                    <c:if test="${is_form_edit}">
                    <tr id="shippingAdviceSentArea">
                        <td colspan="2">Shipping Advice:</td>
                        <td colspan="4">
                            <table id="sasRow">
                                <thead>
                                <tr>
                                    <th>File Ref.</th>
                                    <th>Email Info</th>
                                    <th>Created By:</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="item" items="${shippingAdvice.shippingAdviceSents}">
                                <tr class="">
                                    <td><a href='${baseStaticShippingFolder}${fileName}' target='_blank'>${refNumber}</a></td>
                                    <td>${email}</td>
                                    <td>${user.userName}:<fmt:formatDate value="${date}" pattern="dd-MMM-yy"/> </td>
                                </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </td>
                    </tr>
                    <tr><td colspan="2"></td><td colspan="4"><input value="Add shipping advice" form="view_shipping_advice" id="view_shipping_advice_btn" type="submit" ng-disabled="isCostDisabled()"></td></tr>
                    <tr><td colspan="2"></td><td colspan="4"><input type="submit" form="view_loading_report" id="view_loading_report_btn" value="Add loading report" ng-disabled="isCostDisabled()"></td></tr>
                    <tr><td colspan="2"></td>
                        <td colspan="4">
                            <label for="claim-form-submit" class="ui-button ui-widget ui-state-default ui-corner-all" role="button">Add claim</label>
                            <c:forEach items="${claims}" var="cl">
                                <a href="shipping-instruction/${shippingForm.refNumber}/claim-detail/${cl.refNumber}.htm">${cl.refNumber}</a>
                            </c:forEach>
                        </td>
                    </tr>
                    
                    <tr id="sampleSentArea">
                        <td colspan="2">Sample Sent:</td>
                        <td colspan="4">
                            
                            <table id="ssRow">
                                <thead>
                                <tr>
                                    <th>Sample Sent Ref.</th>
                                    <th>Courier</th>
                                    <th>AWB</th>
                                    <th>Status</th>
                                    <th>Remarks</th>
                                    <th>Last updated</th>
                                    <th>Remark update</th>
                                    <th></th>
                                </tr>
                                </thead>
                                <tbody id = "listSampleSent">
                                    <c:forEach var="item" items="${shippingForm.sampleSents}" varStatus="status">
                                    <tr class="">
                                        <td><a href="shipping-instruction/${shippingForm.refNumber}/sample-sent/${item.refNumber}.htm">${item.refNumber}</a></td>
                                        <td>${item.courier}</td>
                                        <td>${item.trackingNo}</td>
                                        <td>${item.sendingStatus}</td>
                                        <td><textarea class="ssRemark" name="remark" form="ss-form-update-${status.index}" placeholder="sample sent remark..." >${item.remark}</textarea></td>
                                        <td>${item.user}: ${f:formatDateOnly(item.updatedDate)}</td>
                                        <td class="remarkContainer">
                                            <c:if test="${not empty item.saveRemarkUser}">${item.saveRemarkUser}: ${f:formatDateOnly(item.saveRemarkDate)}</c:if>
                                        </td>
                                        <td>
                                            <input type="submit" value="Update" form="ss-form-update-${status.index}" class="sampleSent-remarkUpdate" ng-disabled="isCostDisabled()"/>
                                            <input type="hidden" name="id" value="${item.id}" form="ss-form-update-${status.index}">
                                            <input type="hidden" value="${item.id}" class="sampleSent-id">
                                        </td>
                                    </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </td>
                    </tr>
                    <tr><td colspan="2"></td><td colspan="4"><button id="add_sample_sent">Add sample sent</button></td></tr>
                    <tr>
                        <td></td>
                        <td class="title">User Remark:</td>
                        <td colspan="6" class="field_value">
                            <form:textarea path="userRemark" />
                        </td>
                    </tr>
                    <tr>
                        <td></td>
                        <td class="title">Shipping mark:</td>
                        <td colspan="6" class="field_value">
                            <form:textarea path="shippingMark" />
                        </td>
                    </tr>
                    </c:if>
                </table>
            </div>
            </div>
            <table cellpadding="0" cellspacing="0" class="table_border_bottom_head cost_fixed_table has-cost-info">
                <tbody>
                <tr>
                    <td colspan="11">
                        Cost to FOB:
                    </td>
                </tr>
                <tr>
                    <td></td>
                    <td colspan="10">
                        Quantity & Packing Details:
                    </td>
                </tr>
                <tr>
                    <td></td>
                    <td>
                        Packing:
                    </td>
                    <td colspan="2">
                        <select id="selectBag" ng-disabled="isCostDisabled()" ng-readonly="isCostDisabled()" ng-model="options.packingCostCategory" ng-options="item.id as item.name for item in globalPackings" ></select>
                        <input type="text" name="packingCostCategory" ng-model="options.packingCostCategory" form="shippingForm" style="display: none;">
                    </td>
                    <td colspan="3">
                        Export containers: <input ng-disabled="isCostDisabled()" type="number" id="export_containers" ng-model='options.numberOfContainer' name="numberOfContainer" form="shippingForm"/>
                    </td>
                    <td colspan="4">
                        Mt./Cont. <input type="number" id="tonPerContainer" name="tonPerContainer" form="shippingForm"
                                         ng-model="options.tonPerContainer" step="0.001" ng-readonly="isCostDisabled()"/>
                    </td>
                </tr>
                </tbody></table>
            <ui-view></ui-view>
        </div>

        <c:if test="${is_form_edit}">
        <table class='files_container' id="reference_files">
            <tbody>
            <tr>
                <td></td>
                <td></td>
                <td></td>
            </tr>
            </tbody>
            <tbody>
            <tr>
                <td colspan="3">Reference Files
                    <input type="hidden" value="" name="hidden_reference_files" class="hidden_reference_files"/>
                </td>
            </tr>
            <tr>
                <td>Name</td>
                <td>File name</td>
                <td></td>
            </tr>
            
            </tbody>
            <tbody class='tr_reference_file_container'>
                <c:forEach items="${referenceFileSents}" var="single">
                    <tr>
                        <td><input type="text" value="${single.remindName}" ng-disabled="isCostDisabled()"/><input type="button" value="Save" class="updateFileSentBtn" data-id="${single.id}"></td>
                        <td><a href="${single.url}" target="_blank">${single.originalName}</a></td>
                        <td>${single.updater}: <fmt:formatDate value="${single.created}" pattern="${dateTimeFormat}"/></td>
                    </tr>
                </c:forEach>
            </tbody>
            <tr>
                <td><input type='file' id="reference_file_button" class='file_reference_file button_submit' data-child_name="reference_files" data-type="reference_file" ng-disabled="isCostDisabled()"></td>
                <td></td>
                <td></td>
            </tr>

        </table>

        <table class='files_container' id="internal_reference_files">
            <tbody>
            <tr>
                <td></td>
                <td></td>
                <td></td>
            </tr>
            </tbody>
            <tbody>
            <tr>
                <td colspan="3">Internal Reference Files
                    <input type="hidden" value="" name="hidden_reference_files" class="hidden_reference_files" />
                </td>
            </tr>
            <tr>
                <td></td>
                <td>File name</td>
                <td></td>
            </tr>
            
            </tbody>
            <tbody class='tr_reference_file_container'>
            <c:forEach items="${internalReferenceFileSents}" var="single">
                <tr>
                    <td>
                        <input type="text" value="${single.remindName}" ng-disabled="isCostDisabled()"/><input type="button" value="Save" class="updateFileSentBtn" data-id="${single.id}" ng-disabled="isCostDisabled()">
                    </td>
                    <td><a href="${single.url}" target="_blank">${single.originalName}</a></td>

                    <td>${single.updater}: <fmt:formatDate value="${single.created}" pattern="${dateTimeFormat}"/></td>
                </tr>
            </c:forEach>
            </tbody>
            <tr>
                <td><input type='file' id="internal_reference_file_button" class='file_reference_file button_submit' data-child_name="reference_files" data-type="internal_reference_file" ng-disabled="isCostDisabled()"/></td>
                <td>
                    
                </td>
                <td></td>
            </tr>
            <tr>
                <td colspan="3">Shipping Advice sents</td>
            </tr>
            <c:forEach items="${shippingAdvice.shippingAdviceSents}" var="single">
                <tr>
                    <td></td>
                    <td><a href="${base_static_host}/ShippingAdvice/${single.fileName}">${single.refNumber}</a></td>
                    <td>${single.user.userName}: ${f:formatDateTime(single.date)}</td>
                </tr>
            </c:forEach>

        </table>
        </c:if>
        <div class="right" style="width: 1060px; margin-top: 20px;">
            <input class="button ui-button ui-widget ui-state-default ui-corner-all selenium-main-form-submit-button" type="submit" ng-disabled="isCostDisabled()" value="Save" />
            <input class="right" type="button" value="Print" id="btn_print" name="perm_46"/>
            <input class="right" type="button" value="Report" id="btn_report" name="perm_85"/>
        </div>
        <div id="dialog-form" title="Print SI Report" style="display: none">
            <p>Please choose detail mode</p>
        </div>
        
        <div id="choose-courier" title="Choose courier:" style="display: none">
                <select id="select-couriers" name="courierId" form="sample_sent_form">
                    <c:forEach items="${couriers}" var="item">
                        <option value="${item.id}">${item.name}</option>
                    </c:forEach>
                </select>
                <input type="text" form="sample_sent_form" name="siId" value="${currentId}" style="display: none;">
        </div>


                
        </form:form>
                    
        <form id="view_shipping_advice" method="POST" action="${base_web_url}/shipping-instruction/${currentId}/shipping_advice.htm"></form>
        <form id="view_loading_report" method="POST" action="${base_web_url}/shipping-instruction/${currentId}/generate_contract_weight_note.htm"></form>
        <form method="post" id="sample_sent_form" action="${base_web_url}/new_addSingleSampleSent.htm"></form>
        <c:forEach var="item" items="${shippingForm.sampleSents}" varStatus="status">
            <form id="ss-form-update-${status.index}" method="post" action="${base_web_url}/shipping-instruction/${currentId}/update-sample-sent-user-remark.htm"></form>
        </c:forEach>
        <form method="post" id="custom-cost-form" action="${base_web_url}/shipping-instruction/${currentId}/add-custom-cost.htm">
            <input type="submit" name="action" value="Submit" id="custom-cost-form-submit" style="display: none;" ng-disabled="isCostDisabled()">
        </form>
        <c:forEach var="item" items="${customCostsJSP}" varStatus="status">
            <form id="custom-cost-form-${status.index}" method="post" action="${base_web_url}/shipping-instruction/${currentId}/remove-custom-cost.htm">
                <input type="submit" value="Remove" id="custom-cost-form-${status.index}-submit" style="display: none;" ng-disabled="isCostDisabled()">
            </form>
        </c:forEach>
        <form method="post" id="claim-form" action="${base_web_url}/new_addSingleClaim.htm">
            <input type="hidden" name="siId" value="${currentId}">
            <input type="hidden" name="siRef" value="${shippingForm.refNumber}">
            <input type="submit" name="action" value="Submit" id="claim-form-submit" style="display: none;">
        </form>

    </div>
    
</div>
<jsp:include page="../footer.jsp"/>


<script src="${resources}/js/si.js?version=${version}"></script>


<script type="text/ng-template" id="main.html">
    <p class="no-cost-info" ng-if="!hasCost">No cost information. Please contact admin for more detail</p>


    <table ng-if="hasCost && model.ready()" cellpadding="0" cellspacing="0" class="table_border_bottom_head cost_fixed_table has-cost-info">
        <tbody>
        
        <tr>
            <td></td>
            <td colspan="10">Exchange: USD/VND &nbsp; <span id="ratio">{{exchangeItem.ratio}}</span></td>
        </tr>
        </tbody>
        <tbody>
        <tr>
            <td colspan="7">

            </td>
            <td>
                Units
            </td>
            <td>
                Unit Price Vnd
            </td>
            <td>
                Unit Price USD
            </td>
            <td>
                Cost USD/Mt.
            </td>
        </tr>
        </tbody>
        <tr>
            <td></td>
            <td colspan="10">
                <span class="bolder">Courier costs</span>
            </td>
        </tr>
        <tr ng-init='level=4'>
            <td colspan="{{level - 2}}">
                
            </td>
            <td colspan="2">Documents</td>
            <td colspan="{{(6-level)}}">
                
            </td>
            <td colspan="1">
                <input type="number" ng-model="options.optionalDocumentNumber" name="optionalDocumentNumber" form="shippingForm" ng-disabled="isCostDisabled()">
            </td>
            <td>
                Per BL
            </td>
            <td style="text-align: right">
                {{singleDocumentCost * exchangeItem.ratio * options.optionalDocumentNumber | number: 0}}
            </td>
            <td style="text-align: right">
                {{singleDocumentCost * options.optionalDocumentNumber}}
            </td>
            <td style="text-align: right">
                {{getOptionalDocumentCost() | number: 2}}
            </td>
        </tr>
        <tbody ng-repeat="item in model.getSampleSents()">
            <tr ng-if='item.courier' ng-init='level=4'>
                <td colspan="{{level - 2}}">
                
                </td>
                <td colspan="2">Sample sents</td>
                <td colspan="{{(6-level)}}">
                    {{item.courier}}
                </td>
                <td colspan="1">
                    {{item.refNumber}}
                </td>
                <td>
                    Per B/L 
                </td>
                <td style="text-align: right">
                    {{(item.cost) | number: 0}}
                </td>
                <td style="text-align: right">
                    {{(item.cost) | number: 2}}
                </td>
                <td style="text-align: right">
                    {{(item.cost / getTons()) | number: 2}}
                </td>
            </tr>
        </tbody>
        <tr class="styleTotal">
            <td></td>
            <td colspan="9">
                <span class="bolder">Total Courier costs:</span>
            </td>
            <td style="text-align: right">
            <span class="total" data-total="{{getSampleSentsCost()}}">{{getSampleSentsCost() + getOptionalDocumentCost() | number: 2}}</span>
            </td>
        </tr>
        <tr>
            <td></td>
            <td colspan="10">
                <span class="bolder">Packing costs</span>
            </td>
        </tr>
        <tbody ng-repeat="item in getCopyOptionsByCategory(options.packingCostCategory)">
            <tr ng-include src="'oneCostNew.html'" onload="level=3; showCheckbox=false"></tr>
        </tbody>
        <tr class="styleTotal">
            <td></td>
            <td colspan="9">
                <span class="bolder">Total packing cost:</span>
            </td>
            <td style="text-align: right">
            <span class="total" data-total="{{getTotalCostInCategory(options.packingCostCategory)}}">{{getTotalCostInCategory(options.packingCostCategory) | number: 2}}</span>
            </td>
        </tr>


        <tr>
            <td></td>
            <td colspan="10">
                <span class="bolder">Loading and Transport</span>
            </td>
        </tr>
        <tbody ng-repeat="item in getCopyOptionsByCategory(options.loadingAndTransportCategory)">
            <tr ng-include src="'oneCostNew.html'" onload="level=3; showCheckbox=false"></tr>
        </tbody>
        <tr class="styleTotal">
            <td></td>
            <td colspan="9">
                <span class="bolder">Total Loading and Transport:</span>
            </td>
            <td style="text-align: right">
                <span class="total" data-total="{{getTotalCostInCategory(options.loadingAndTransportCategory)}}">{{getTotalCostInCategory(options.loadingAndTransportCategory) | number: 2}}</span>
            </td>
        </tr>


        <tr>
            <td></td>
            <td colspan="10">
                <span class="bolder">Document</span>
            </td>
        </tr>
        <tbody ng-repeat="item in getCopyOptionsByCategory(options.documentCategory)">
            <tr>
                <td colspan="3">
                </td>
                <td colspan="3">
                    {{item.name}}
                </td>
                <td colspan="1">
                    <input type="checkbox" option="{{item.optionName}}"
                           class="isEnable" ng-model="item._isSelected" ng-disabled="isCostDisabled()"
                    />
                    <input ng-if="item._isSelected" name="documentCosts" type="text" value="{{item.optionName}}" form="shippingForm" style="display: none;" />
                </td>
                <td>
                    {{item.unit}}
                </td>
                <td style="text-align: right">
                    {{(item.costValueInVND) | number: 0}}
                </td>
                <td style="text-align: right">
                    {{(item.costValue) | number: 2}}
                </td>
                <td style="text-align: right">
                    <span ng-if="item._isSelected">{{item.getValuePerNetricTon(options.tonPerContainer, options.numberOfContainer) | number: 2}}</span>
                    <span ng-if="!item._isSelected">---</span>
                </td>
            </tr>
        </tbody>


        <tr>
            <td colspan="3">

            </td>
            <td colspan="3">
                Quality & Weight Certificate
            </td>
            <td>
                <select ng-disabled="isCostDisabled()" ng-model="options.certificateCost" ng-options="item.optionName as item.name for item in getOptionsByCategory(options.certificateCategory)">
                </select>
            </td>
            <td colspan="4">
            </td>
        </tr>
        {{_op=getOption(options.certificateCost); ""}}
        <tr id="{{_op.optionName}}" class="certificateCost" ng-init="">
            <td colspan="4">
            </td>
            <td colspan="2">
                For the account of:

            </td>
            <td colspan="1">
                <select ng-disabled="isCostDisabled()">
                    <option>Customer</option>
                </select>
            </td>

            <td>
                {{_op.unit}}
            </td>
            <td style="text-align: right">
                {{_op.costValueInVND | number: 0}}
            </td>
            <td style="text-align: right">
                {{_op.costValue | number: 2}}
            </td>
            <td style="text-align: right">
                {{_op.getValuePerNetricTon(options.tonPerContainer, options.numberOfContainer) | number: 2}}
            </td>
        </tr>

        <tr>
            <td colspan="11"></td>
        </tr>
        <tr>
            <td colspan="3">

            </td>
            <td>
                Fumigation Certificate
            </td>
            <td>
                <select ng-disabled="isCostDisabled()" ng-model="options.fumigationProviderCategory" ng-options="item.id as item.name for item in getChildrenByCategoryIncludingOptional(options.fumigationCategory)"></select>
                <input type="text" name="fumigationProviderCategory" form="shippingForm" ng-model="options.fumigationProviderCategory" style="display: none;" />
            </td>
            <td colspan="5">
            </td>

        </tr>
        <tr class="fumigationCost" ng-if="options.fumigationProviderCategory != -1">
            <td colspan="4">
            </td>
            <td>
                For the account of:
            </td>
            <td colspan="6">
                <select ng-disabled="isCostDisabled()">
                    <option>Customer</option>
                </select>

            </td>
        </tr>
        <tr ng-if="options.fumigationProviderCategory != -1">
            <td colspan="4"></td>
            <td colspan="2">Fumigation details</td>
            <td>
                <select ng-disabled="isCostDisabled()" ng-model="options.fumigationDetailCost" ng-options="item.optionName as item.name for item in getOptionsByCategory(options.fumigationProviderCategory)">
                </select>
                <input type="text" name="fumigationDetailCost" form="shippingForm" ng-model="options.fumigationDetailCost" style="display: none;">
            </td>
            {{_op1=getOption(options.fumigationDetailCost); ""}}
            <td>
                {{_op1.unit}}
            </td>
            <td style="text-align: right">
                {{_op1.costValueInVND | number: 0}}
            </td>
            <td style="text-align: right">
                {{_op1.costValue | number: 2}}
            </td>
            <td style="text-align: right">
                {{_op1.getValuePerNetricTon(options.tonPerContainer, options.numberOfContainer) | number: 2}}
            </td>
        </tr>

        <tr ng-if="options.fumigationProviderCategory != -1">
            {{_op2=getOption(options.fumigationInStore); ""}}
            <td colspan="4">
            </td>
            <td colspan="2">
                {{_op2.name}}
            </td>
            <td colspan="1">
                <input type="checkbox" class="isEnable" ng-model="_op2._isSelected" ng-disabled="isCostDisabled()"
                />
                <input type="text" style="display: none;" ng-if="_op2._isSelected" name="fumigationInStore" ng-model="options.fumigationInStore" form="shippingForm">
            </td>
                
            <td>
                {{_op2.unit}}
            </td>
            <td style="text-align: right">
                {{_op2.costValueInVND | number: 0}}
            </td>
            <td style="text-align: right">
                {{_op2.costValue | number: 2}}
            </td>
            <td style="text-align: right">
                <span ng-if="options.fumigationInStore != ''">{{_op2.getValuePerNetricTon(options.tonPerContainer, options.numberOfContainer) | number: 2}}</span>
                <span ng-if="options.fumigationInStore == ''">---</span>
            </td>
        </tr>

        

        <tr class="styleTotal">
            <td></td>
            <td colspan="9">
                <span class="bolder">Total Document:</span>
            </td>
            <td style="text-align: right">
                {{_cost_value = getTotalCostInOptionalCategory(options.documentCategory)
                + getCostValueInUsd(options.certificateCost, false)
                + getCostValueInUsd(options.fumigationInStore, true)
                + getCostValueInUsd(options.fumigationDetailCost); ""}}
                <span class="total" data-total="{{_cost_value}}">{{_cost_value | number: 2}}</span>
            </td>
        </tr>


        <tr>
            <td></td>
            <td colspan="10">
                <span class="bolder">Optional packing item</span>
            </td>
        </tr>
        <tbody ng-repeat="item in getCopyOptionsByCategory(options.optionalCategory)">
            <tr>
                <td colspan="3">
                </td>
                <td colspan="3">
                    {{item.name}}
                </td>
                <td colspan="1" option="{{item.optionName}}">
                    <input type="checkbox" option="{{item.optionName}}"
                           class="isEnable" ng-model="item._isSelected" ng-disabled="isCostDisabled()"
                    />
                    <input ng-if="item._isSelected" name="packingItemCosts" type="text" style="display: none;" value="{{item.optionName}}" form="shippingForm" />
                </td>
                <td>
                    {{item.unit}}
                </td>
                <td style="text-align: right">
                    {{(item.costValueInVND) | number: 0}}
                </td>
                <td style="text-align: right">
                    {{(item.costValue) | number: 2}}
                </td>
                <td style="text-align: right">
                    <span ng-if="item._isSelected">{{item.getValuePerNetricTon(options.tonPerContainer, options.numberOfContainer) | number: 2}}</span>
                    <span ng-if="! item._isSelected">---</span>
                </td>
            </tr>
        </tbody>
        <tr class="styleTotal">
            <td></td>
            <td>&nbsp;&nbsp;&nbsp;</td>
            <td colspan="8">
                <span class="bolder">Total Optional packing  item:</span>
            </td>
            <td style="text-align: right">
                <span class="total" data-total="{{getTotalCostInOptionalCategory(options.optionalCategory)}}">{{getTotalCostInOptionalCategory(options.optionalCategory) | number: 2}}</span>
            </td>
        </tr>



        <tr>
            <td>

            </td>
            <td colspan="5">
                Marking
            </td>
            <td>

                <select ng-model="options.markingCategory" ng-disabled="isCostDisabled()" ng-options="__item1.id as __item1.name for __item1 in getChildrenByCategoryIncludingOptional(options.allMarkingCategory)"></select>
                <input type="text" name="markingCategory" ng-model="options.markingCategory" form="shippingForm" style="display: none;">
            </td>
            <td colspan="4">
            </td>
        </tr>

        <tbody ng-repeat="item in getCopyOptionsByCategory(options.markingCategory)">
            <tr ng-include src="'oneCostNew.html'" onload="level=3; showCheckbox=false"></tr>
        </tbody>

        <tr class="styleTotal">
            <td></td>
            <td colspan="9">
                <span class="bolder">Total Marking:</span>
            </td>
            <td style="text-align: right">
                <span class="total"  data-total="{{getTotalCostInCategory(options.markingCategory)}}">{{getTotalCostInCategory(options.markingCategory) | number: 2}}</span>
            </td>
        </tr>
        <tr>
            <td></td>
            <td colspan="10">
                <span class="bolder">Other costs</span>
            </td>
        </tr>

        <tbody ng-repeat="item in options.customCosts">
            <td colspan="3"></td>

            <td colspan="3"><input type="text" disabled="" ng-model="item.optionName"></td>
            <td>
            </td>
            <td>
                <select ng-model="item.optionUnit" disabled="" ng-options="unit for unit in units"></select>
            </td>
            <td style="text-align: right">
                {{item.costInVND | number: 0}}
            </td>
            <td style="text-align: right">
                <input class="smaller numberOnly" ng-model="item.costValue" disabled="">
            </td>
            <td style="text-align: right">
                {{getCostPerMetricTon(item) | number: 2}}
                <input type="hidden" name="id" value="{{item.id}}" form="custom-cost-form-{{$index}}">
               
                

                <label for="custom-cost-form-{{$index}}-submit" class="ui-button ui-widget ui-state-default ui-corner-all" role="button" aria-disabled="false">Remove</label>
            </td>
        </tbody>
        <tr>
            <td colspan="3"></td>
            <td colspan="4"><input type="text" ng-model="customCost.optionName" class="longer" name="optionName" form="custom-cost-form" ng-disabled="isCostDisabled()"></td>
            <td>
                <select ng-model="customCost.optionUnit" ng-options="unit for unit in units track by unit" name="optionUnit" form="custom-cost-form" ng-disabled="isCostDisabled()"></select>
            </td>
            <td style="text-align: right">
                
            </td>
            <td style="text-align: right">
                <input class="smaller numberOnly" ng-model="customCost.costValue" name="costValue" form="custom-cost-form" ng-disabled="isCostDisabled()">
            </td>
            <td style="text-align: right">
                {{getCostPerMetricTon(customCost) | number: 1}}
            </td>
        </tr>

        <tr>
            <td></td>
            <td colspan="10">
                <label for="custom-cost-form-submit" class="ui-button ui-widget ui-state-default ui-corner-all" role="button" >Add custom cost</label>
            </td>
        </tr>

        <tr class="styleTotal">
            <td></td>
            <td colspan="9">
                <span class="bolder">Total Other costs:</span>
            </td>
            <td style="text-align: right">
                <span class="total" data-total="{{getTotalCustomCosts()}}">{{getTotalCustomCosts() | number: 2}}</span>
            </td>
        </tr>



        <tbody>
        <tr class="styleTotal">
            <td colspan="10">Total Costs to FOB:</td>

            <td style="text-align: right"><span id="totalAll" name="totalCost">{{(getTotalCostInCategory(options.markingCategory)
                + getTotalCostInOptionalCategory(options.documentCategory)
                + getCostValueInUsd(options.certificateCost, false)
                + getTotalCostInOptionalCategory(options.optionalCategory)
                + getTotalCostInCategory(options.loadingAndTransportCategory)
                + getCostValueInUsd(options.fumigationInStore, true)
                + getCostValueInUsd(options.fumigationDetailCost)
                + getTotalCostInCategory(options.packingCostCategory)
                + getSampleSentsCost()) 
                + getTotalCustomCosts()
                + getOptionalDocumentCost() | number: 2}}</span></td>
        </tr>
        </tbody>
        
    </table>
    
    <input  ng-model="options.certificateCost" name="certificateCost" form="shippingForm" style="display: none">
    <input  ng-model="options.costNames" name="costNames" form="shippingForm" style="display: none">


</script>

<script type='text/xhtml' id='singleFileContainer'>
    <tr>
        <td><input type="text" value="{{:data.remindName}}"/><input type="button" value="Save" class="updateFileSentBtn" data-id="{{:data.id}}"><input type="hidden" name="{{:name}}" value="{{:data.id}}"/></td>
        <td><a href="{{:data.fileUpload.url}}" target="_blank">{{:data.fileUpload.originalName}}</a></td>
        <td>{{:data.updater}}: {{:~formatDate(data.created)}}</td>
    </tr>
</script>
<script type='text/ng-template' id="oneCostNew.html">
    <tr>
                <td colspan="{{level}}">
                </td>
                <td colspan="{{(6-level)}}">
                    {{item.name}}
                </td>
                <td colspan="1" option="{{item.optionName}}">
                    <input ng-if="showCheckbox" type="checkbox" name="isEnable" option="{{item.optionName}}"
                           class="isEnable" ng-model="item._isSelected" ng-disabled="isCostDisabled()"
                    />
                </td>
                <td>
                    {{item.unit}}
                </td>
                <td style="text-align: right">
                    {{(item.costValueInVND) | number: 0}}
                </td>
                <td style="text-align: right">
                    {{(item.costValue) | number: 2}}
                </td>
                <td style="text-align: right">
                    <span ng-if="! showCheckbox || item._isSelected">{{item.getValuePerNetricTon(options.tonPerContainer, options.numberOfContainer) | number: 2}}</span>
                    <span ng-if="showCheckbox && ! item._isSelected">---</span>
                </td>
            </tr>
</script>

</body>
</html>

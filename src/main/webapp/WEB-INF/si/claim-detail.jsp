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
    <title>Claim Detail</title>
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
        input.noStyle {
            background-color: transparent;
            border: 0px solid;
        }
    </style>
    <script type="text/javascript">
        var siRef = "${si.refNumber}";
        var claimRef = "${claimForm.refNumber}";
    </script>
</head>
<body>
<jsp:include page="../header.jsp">
    <jsp:param name="url" value="76"/>
    <jsp:param name="page" value="125"/>
</jsp:include>
<div style="width: 1600px ! important; padding: 15px; background: none repeat scroll 0% 0% white; height: auto; overflow: hidden"
     class="container_16 border" id="wrapper">

    <div id="detail_content">
        <div style="width: 1600px !important; min-height: 80px; height: auto; overflow: hidden;margin-right: 9px;"
             class="center">
            <table class="instructionDetailStandard" style="padding: 20px; width: 65%; margin-left: 250px">
                <form:form action="${base_web_url}/shipping-instruction/${si.refNumber}/update-claim-approval-status.htm"
                           method="post" commandName="approvalStatusForm">
                    <tr>
                        <td></td>
                        <td class="title">Approval Status:</td>
                        <td class="field_value" colspan="3">
                            <form:select path="status"
                                         style="width: 30%">
                                <form:options items="${approvalStatusForm.statuses}"/>
                            </form:select>
                            <form:hidden path="refNumber"/>
                            <label>${approvalStatusForm.user}:${f:formatDateTimeAMPM(approvalStatusForm.date)} </label>
                            <input class="button ui-button ui-widget ui-state-default ui-corner-all" type="submit"
                                   value="Save" onclick="disableSubmitAfterClick(this)"/>
                        </td>
                    </tr>
                </form:form>
                <form:form action="${base_web_url}/shipping-instruction/${si.refNumber}/claim-detail/${claimForm.refNumber}.htm" method="post" commandName="claimForm" id="claimForm">
                    <form:hidden path="refNumber"/>
                    <tr>
                        <td class="subTitle" colspan="3">Claim Details:</td>
                    </tr>
                    <tr>
                        <td></td>
                        <td class="title">SI Ref.:</td>
                        <td class="field_value">
                                ${si.refNumber}
                        </td>
                        <td></td>
                        <td class="title">SI Date:</td>
                        <td class="field_value">
                            <fmt:formatDate pattern="dd-MMM-yy" value="${si.date}"/>
                        </td>
                    </tr>
                    <tr>
                        <td></td>
                        <td class="title">Claim Ref.:</td>
                        <td class="field_value">
                            ${claimForm.refNumber}
                        </td>
                        <td></td>
                        <td class="title">Claim Date:</td>
                        <td class="field_value">
                            <fmt:formatDate pattern="dd-MMM-yy hh:mm:ss"
                                            value="${claimForm.created}"/>
                        </td>
                    </tr>
                    <tr>
                        <td></td>
                        <td class="title">Debit Note</td>
                        <td class="field_value">
                            <form:input path="debitNote" value=""/>
                        </td>
                        <td></td>
                        <td class="title">Debit Note Date:</td>
                        <td class="field_value">
                            <form:input path="debitNoteDate" type="text" cssClass="js_date"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="subTitle" colspan="3">Party Details:</td>
                    </tr>
                    <tr>
                        <td></td>
                        <td class="title">Client:</td>
                        <td class="field_value">
                                ${si.client}
                        </td>
                        <td></td>
                        <td class="title">Client Ref.:</td>
                        <td class="field_value">
                                ${si.clientRef}
                        </td>
                    </tr>
                    <tr>
                        <td></td>
                        <td class="title">Supplier:</td>
                        <td class="field_value">
                                ${si.supplier}
                        </td>
                        <td></td>
                        <td class="title">Supplier Ref.:</td>
                        <td class="field_value">
                                ${si.supplierRef}
                        </td>
                    </tr>
                    <tr>
                        <td></td>
                        <td class="title">Shipper:</td>
                        <td class="field_value">
                                ${si.shipper}
                        </td>
                        <td></td>
                        <td class="title">Shipper Ref.:</td>
                        <td class="field_value">
                                ${si.shipperRef}
                        </td>
                    </tr>
                    <tr>
                        <td></td>
                        <td class="title">Buyer:</td>
                        <td class="field_value">
                                ${si.buyer}
                        </td>
                        <td></td>
                        <td class="title">Buyer Ref.:</td>
                        <td class="field_value">
                                ${si.buyerRef}
                        </td>
                    </tr>
                    <tr>
                        <td class="subTitle" colspan="3">Shipping Details:</td>
                    </tr>
                    <tr>
                        <td></td>
                        <td class="title">B/L Number</td>
                        <td class="field_value">${si.blNumber}</td>
                        <td></td>
                        <td class="title">B/L Date:</td>
                        <td class="field_value"><fmt:formatDate pattern="dd-MMM-yy"
                                                                value="${si.blDate}"/></td>
                    </tr>
                    <tr>
                        <td></td>
                        <td class="title">Feeder Vessel</td>
                        <td class="field_value"></td>
                        <td></td>
                        <td class="title">Etd</td>
                        <td class="field_value"><fmt:formatDate pattern="dd-MMM-yy"
                                                                value="${si.feederEts}"/></td>
                        <td></td>
                        <td class="title">Eta</td>
                        <td class="field_value"><fmt:formatDate pattern="dd-MMM-yy"
                                                                value="${si.feederEta}"/></td>
                    </tr>
                    <tr>
                        <td></td>
                        <td class="title">Point of Loading</td>
                        <td class="field_value"></td>
                    </tr>
                    <tr>
                        <td></td>
                        <td class="title">Transit port</td>
                        <td class="field_value"></td>
                    </tr>
                    <tr>
                        <td></td>
                        <td class="title">Mother Vessel</td>
                        <td class="field_value">${si.oceanVessel}</td>
                        <td></td>
                        <td class="title">Etd</td>
                        <td class="field_value"><fmt:formatDate pattern="dd-MMM-yy"
                                                                value="${si.oceanEts}"/></td>
                        <td></td>
                        <td class="title">Eta</td>
                        <td class="field_value"><fmt:formatDate pattern="dd-MMM-yy"
                                                                value="${si.oceanEta}"/></td>
                    </tr>
                    <tr>
                        <td></td>
                        <td class="title">Transit port</td>
                        <td class="field_value"></td>
                    </tr>

                    <tr>
                        <td class="subTitle" colspan="3">Weighing Details:</td>
                    </tr>
                    <tr>
                        <td></td>
                        <td class="title">Sworn Weighers</td> <!-- Warehouse master in database -->
                        <td class="field_value">
                            <form:select path="swornWeigherId" id="swornWeigherId" items="${warehouses}" itemValue="id"
                                         itemLabel="name"/>
                        </td>
                        <td></td>
                        <td class="title">Landing Date:</td>
                        <td class="field_value">
                            <form:input path="landingDate" type="text" cssClass="js_date"/>
                        </td>
                    </tr>
                    <tr>
                        <td></td>
                        <td class="title">Warehouse</td> <!-- Warehouse master in database -->
                        <td class="field_value">
                            <form:select path="warehouseId" id="warehouseId" items="${warehouses}" itemValue="id"
                                         itemLabel="name"/>
                        </td>
                        <td></td>
                        <td class="title">First date of weighing</td>
                        <td class="field_value">
                            <form:input path="firstDateOfWeighing" type="text" cssClass="js_date"/>
                        </td>
                    </tr>
                    <tr>
                        <td></td>
                        <td class="title">Packing</td>
                        <td class="field_value">${si.packing}</td>
                        <td></td>
                        <td class="title">Final date of weighing</td>
                        <td class="field_value">
                            <form:input path="finalDateOfWeighing" type="text" cssClass="js_date"/>
                        </td>
                    </tr>
                    <tr>
                        <td></td>
                        <td class="title">Unloading Method</td>
                        <td class="field_value">
                            <form:input path="unloadingMethod" value=""/>
                        </td>
                        <td></td>
                        <td class="title">Sampling Date</td>
                        <td class="field_value">
                            <form:input path="samplingDate" type="text" cssClass="js_date"/>
                        </td>
                    </tr>
                    <tr>
                        <td></td>
                        <td class="title">Contract Franchise</td>
                        <td class="field_value">
                            <form:input path="contractFranchise" class="numberOnly" type="text" />%
                        </td>
                        <td></td>
                        <td class="title">Destination Franchise</td>
                        <td class="field_value">
                            <form:input path="destinationFranchise" class="numberOnly" type="text" />%
                        </td>
                    </tr>
                    <tr>
                        <td></td>
                        <td class="title">Remark</td>
                        <td class="field_value">
                            <form:textarea path="remark"/>
                        </td>
                    </tr>

                    <tr>
                        <td class="title" colspan="2"></td>
                        <td class="field_value" colspan="4"><label>${claimForm.createdUser}:<fmt:formatDate
                                pattern="dd-MMM-yy HH:mm:ss" value="${claimForm.updated}"/></label>
                            <input
                                    class="button ui-button ui-widget ui-state-default ui-corner-all" type="submit"
                                    value="Save" onclick="disableSubmitAfterClick(this)" form="claimForm"/>
                        </td>
                    </tr>
                    
                    

                </form:form>
            </table>
            
            <table>
                <tr>
                    <td>
                        <table class="si_wn_detail" style="margin-top: 20px;">
                            <tbody>
                                <tr class="head">
                                    <th class="text" colspan="3">Container Details:</th>
                                </tr>
                                <tr class="head">
                                    <th class="text">Container No.:</th>
                                    <th class="text">Seal No.:</th>
                                    <th class="text">Bags:</th>
                                </tr>
                                <c:forEach var="item" items="${aggegrate.shipWeightDetail}">
                                    <tr class="input_row">
                                        <td class="text">${item.containerNo}</td>
                                        <td class="text">${item.sealNo}</td>
                                        <td class="text"><input type="text" class="iwnbag numberOnly alignRight noStyle" value="${f:formatMoneyWithComma(item.noOfBags)}"  disabled=""></td>
                                    </tr>
                                </c:forEach>
                                <tr align="right">
                                    <th colspan="2">Total:</th>
                                    <th>
                                        ${f:formatMoneyWithComma(aggegrate.totalShipWeightDetail.noOfBags)}
                                    </th>
                                </tr>
                                <tr><td>&nbsp;</td></tr>
                                <tr><td>&nbsp;</td></tr>
                            </tbody>
                        </table>
                    </td>
                    <td>
                        <table class="si_wn_detail" style="margin-top: 20px;">
                            <tbody>
                                <tr class="head">
                                    <th class="text" colspan="3">Shipped Weight Details:</th>
                                </tr>
                                <tr class="head">
                                    <th class="text">Gross Weight Mt.:</th>
                                    <th class="text">Tare Weight Mt.:</th>
                                    <th class="text">Net Weight Mt.:</th>
                                </tr>
                                <c:forEach var="item" items="${aggegrate.shipWeightDetail}">
                                    <tr class="input_row" align="right">
                                        <td class="text"><input type="text" class="iwnbag numberOnly alignRight noStyle" value="${f:formatMoneyWithComma(item.grossWeight)}"  disabled=""></td>
                                        <td class="text"><input type="text" class="iwnbag numberOnly alignRight noStyle" value="${f:formatMoneyWithComma(item.tareWeight)}"  disabled=""></td>
                                        <td class="text"><input type="text" class="iwnbag numberOnly alignRight noStyle" value="${f:formatMoneyWithComma(item.netWeight)}"  disabled=""></td>
                                    </tr>
                                </c:forEach>
                                <tr align="right">
                                    <th class="text">${f:formatMoneyWithComma(aggegrate.totalShipWeightDetail.grossWeight)}</th>
                                    <th class="text">${f:formatMoneyWithComma(aggegrate.totalShipWeightDetail.tareWeight)}</th>
                                    <th class="text">${f:formatMoneyWithComma(aggegrate.totalShipWeightDetail.netWeight)}</th>
                                </tr>
                                <tr><td colspan="3">&nbsp;</td></tr>
                                <tr><td colspan="3">&nbsp;</td></tr>
                            </tbody>
                        </table>
                    </td>
                    <td>
                        <table class="si_wn_detail" style="margin-top: 20px;">
                            <tbody>
                                <tr class="head">
                                    <th class="text" colspan="3">Arrival Weight Details:</th>
                                </tr>
                                <tr class="head">
                                    <th class="text">Gross Weight Mt.:</th>
                                    <th class="text">Tare Weight Mt.:</th>
                                    <th class="text">Net Weight Mt.:</th>
                                </tr>
                                <c:forEach var="item" items="${aggegrate.arrivalWeightDetail}">
                                    <tr class="input_row claimWeightNoteContainer" align="right">
                                        <td class="text"><input type="text" class="iwnbag numberOnly alignRight" value="${item.grossWeight}"  name="grossWeight"></td>
                                        <td class="text"><input type="text" class="iwnbag numberOnly alignRight" value="${item.tareWeight}" name="tareWeight"></td>
                                        <td class="text">
                                            <input type="text" class="iwnbag numberOnly alignRight noStyle" value="${item.netWeight}"  disabled="">
                                            <input type="hidden" value="${item.weightNoteId}" name="weightNoteId">
                                        </td>
                                    </tr>
                                </c:forEach>
                                <tr align="right">
                                    <th class="text">${f:formatMoneyWithComma(aggegrate.totalArrivalWeightDetail.grossWeight)}</th>
                                    <th class="text">${f:formatMoneyWithComma(aggegrate.totalArrivalWeightDetail.tareWeight)}</th>
                                    <th class="text">${f:formatMoneyWithComma(aggegrate.totalArrivalWeightDetail.netWeight)}</th>
                                </tr>
                                <tr><td colspan="3">&nbsp;</td></tr>
                                <tr>
                                    <td colspan="3">
                                        <input class="button ui-button ui-widget ui-state-default ui-corner-all" type="submit"
                                               value="Update" id="saveClaimWeightNotes" /><span>${claimForm.updatedArrivalWeightNoteUser}</span>:
                                        <span>${f:formatDateTimeAMPM(claimForm.updateArrivalWeightNoteDate)}</span></td>
                                </tr>
                            </tbody>
                        </table>
                    </td>
                    <td>
                        <table class="si_wn_detail" style="margin-top: 20px;">
                            <tbody>
                                <tr class="head">
                                    <th class="text" colspan="3">Weight Loss Details:</th>
                                </tr>
                                <tr class="head">
                                    <th class="text">Gross Weight Mt.:</th>
                                    <th class="text">Tare Weight Mt.:</th>
                                    <th class="text">Net Weight Mt.:</th>
                                </tr>
                                <c:forEach var="item" items="${aggegrate.weightLossDetail}">
                                    <tr class="input_row">
                                        <td class="text"><input type="text" class="iwnbag numberOnly alignRight noStyle" value="${f:formatMoneyWithComma(item.grossWeight)}"  disabled=""></td>
                                        <td class="text"><input type="text" class="iwnbag numberOnly alignRight noStyle" value="${f:formatMoneyWithComma(item.tareWeight)}"  disabled=""></td>
                                        <td class="text"><input type="text" class="iwnbag numberOnly alignRight noStyle" value="${f:formatMoneyWithComma(item.netWeight)}"  disabled=""></td>
                                    </tr>
                                </c:forEach>
                                <tr align="right">
                                    <th class="text">${f:formatMoneyWithComma(aggegrate.totalWeightLossDetail.grossWeight)}</th>
                                    <th class="text">${f:formatMoneyWithComma(aggegrate.totalWeightLossDetail.tareWeight)}</th>
                                    <th class="text">${f:formatMoneyWithComma(aggegrate.totalWeightLossDetail.netWeight)}</th>
                                </tr>
                                <tr><th colspan="3" style='text-align: right'>0.5 %   ${f:formatMoneyWithComma(aggegrate.theoryFranchiseWeight)}</th></tr>
                                <tr><th colspan="3" style='text-align: right'>${f:formatMoneyWithComma(aggegrate.diffBetweenRealAndClaim)}</th></tr>
                            </tbody>
                        </table>
                        
                    </td>
                </tr>










                <tr>
                    <td>
                        <table class="si_wn_detail" style="margin-top: 20px;">
                            <tbody>
                                <tr class="head">
                                    <th class="text" colspan="3">Container Details:</th>
                                </tr>
                                <tr class="head">
                                    <th class="text">Container No.:</th>
                                    <th class="text">Seal No.:</th>
                                    <th class="text">Bags:</th>
                                </tr>
                                <c:forEach var="item" items="${aggegrate.shipWeightDetail}">
                                    <tr class="input_row">
                                        <td class="text">${item.containerNo}</td>
                                        <td class="text">${item.sealNo}</td>
                                        <td class="text"><input type="text" class="iwnbag numberOnly alignRight noStyle" value="${f:formatMoneyWithComma(item.noOfBags)}"  disabled=""></td>
                                    </tr>
                                </c:forEach>
                                <tr align="right">
                                    <th colspan="2">Total:</th>
                                    <th>
                                        ${f:formatMoneyWithComma(aggegrate.totalShipWeightDetail.noOfBags)}
                                    </th>
                                </tr>
                            </tbody>
                        </table>
                    </td>
                    <td>
                        <table class="si_wn_detail" style="margin-top: 20px;">
                            <tbody>
                                <tr class="head">
                                    <th class="text" colspan="3">Internal Shipped Weight Detials:</th>
                                </tr>
                                <tr class="head">
                                    <th class="text">Gross Weight Mt.:</th>
                                    <th class="text">Tare Weight Mt.:</th>
                                    <th class="text">Net Weight Mt.:</th>
                                </tr>
                                <c:forEach var="item" items="${aggegrate.internalShipWeightDetail}">
                                    <tr class="input_row">
                                        <td class="text"><input type="text" class="iwnbag numberOnly alignRight noStyle" value="${f:formatMoneyWithComma(item.grossWeight)}"  disabled=""></td>
                                        <td class="text"><input type="text" class="iwnbag numberOnly alignRight noStyle" value="${f:formatMoneyWithComma(item.tareWeight)}"  disabled=""></td>
                                        <td class="text"><input type="text" class="iwnbag numberOnly alignRight noStyle" value="${f:formatMoneyWithComma(item.netWeight)}"  disabled=""></td>
                                    </tr>
                                </c:forEach>
                                <tr align="right">
                                    <th class="text">${f:formatMoneyWithComma(aggegrate.totalInternalShipWeightDetail.grossWeight)}</th>
                                    <th class="text">${f:formatMoneyWithComma(aggegrate.totalInternalShipWeightDetail.tareWeight)}</th>
                                    <th class="text">${f:formatMoneyWithComma(aggegrate.totalInternalShipWeightDetail.netWeight)}</th>
                                </tr>
                            </tbody>
                        </table>
                    </td>
                    <td>
                        <table class="si_wn_detail" style="margin-top: 20px;">
                            <tbody>
                                <tr class="head">
                                    <th class="text" colspan="3">Arrival Weight Details:</th>
                                </tr>
                                <tr class="head">
                                    <th class="text">Gross Weight Mt.:</th>
                                    <th class="text">Tare Weight Mt.:</th>
                                    <th class="text">Net Weight Mt.:</th>
                                </tr>
                                <c:forEach var="item" items="${aggegrate.arrivalWeightDetail}">
                                    <tr class="input_row">
                                        <td class="text"><input type="text" class="iwnbag numberOnly alignRight noStyle" value="${f:formatMoneyWithComma(item.grossWeight)}"  disabled=""></td>
                                        <td class="text"><input type="text" class="iwnbag numberOnly alignRight noStyle" value="${f:formatMoneyWithComma(item.tareWeight)}"  disabled=""></td>
                                        <td class="text"><input type="text" class="iwnbag numberOnly alignRight noStyle" value="${f:formatMoneyWithComma(item.netWeight)}"  disabled=""></td>
                                    </tr>
                                </c:forEach>
                                <tr align="right">
                                    <th class="text">${f:formatMoneyWithComma(aggegrate.totalArrivalWeightDetail.grossWeight)}</th>
                                    <th class="text">${f:formatMoneyWithComma(aggegrate.totalArrivalWeightDetail.tareWeight)}</th>
                                    <th class="text">${f:formatMoneyWithComma(aggegrate.totalArrivalWeightDetail.netWeight)}</th>
                                </tr>
                            </tbody>
                        </table>
                    </td>
                    <td>
                        <table class="si_wn_detail" style="margin-top: 20px;">
                            <tbody>
                                <tr class="head">
                                    <th class="text" colspan="3">Internal Weight Loss Details:</th>
                                </tr>
                                <tr class="head">
                                    <th class="text">Gross Weight Mt.:</th>
                                    <th class="text">Tare Weight Mt.:</th>
                                    <th class="text">Net Weight Mt.:</th>
                                </tr>
                                <c:forEach var="item" items="${aggegrate.internalWeightLossDetail}">
                                    <tr class="input_row">
                                        <td class="text"><input type="text" class="iwnbag numberOnly alignRight noStyle" value="${f:formatMoneyWithComma(item.grossWeight)}"  disabled=""></td>
                                        <td class="text"><input type="text" class="iwnbag numberOnly alignRight noStyle" value="${f:formatMoneyWithComma(item.tareWeight)}"  disabled=""></td>
                                        <td class="text"><input type="text" class="iwnbag numberOnly alignRight noStyle" value="${f:formatMoneyWithComma(item.netWeight)}"  disabled=""></td>
                                    </tr>
                                </c:forEach>
                                <tr align="right">
                                    <th class="text">${f:formatMoneyWithComma(aggegrate.totalInternalWeightLossDetail.grossWeight)}</th>
                                    <th class="text">${f:formatMoneyWithComma(aggegrate.totalInternalWeightLossDetail.tareWeight)}</th>
                                    <th class="text">${f:formatMoneyWithComma(aggegrate.totalInternalWeightLossDetail.netWeight)}</th>
                                </tr>
                            </tbody>
                        </table>
                        
                    </td>
                </tr>
                <tr>
                    <td colspan="4">
                        <table>
                            <c:forEach items="${claimForm.documents}" var="single">
                            <tr>
                                <td>
                                    <a href="${single.url}" target="_blank">${single.originalName}</a>
                                </td>
                                <td>
                                    ${single.updater}: ${f:formatDateTimeAMPM(single.created)}
                                </td>
                            </tr>
                            </c:forEach>
                            <form method="POST" action="${base_web_url}/shipping-instruction/${si.refNumber}/claim/${claimForm.refNumber}/add-document.htm" enctype="multipart/form-data">
                                
                                    <tr>
                                        <td>
                                            <input type="file" name="file" />
                                        </td>
                                        <td>
                                            <input type="submit" />
                                        </td>
                                    </tr>
                                    
                                
                            </form>
                        </table>

                    </td>
                </tr>
            </table>
            
            
        </div>
    </div>
</div>
<script>
    $(document).ready(function () {
        console.log("ready");
        $(".js_date").datepicker({
            dateFormat: "d-M-y",
            changeMonth: true,
            changeYear: true,
            yearRange: "1945:2050"
        });
        $("#saveClaimWeightNotes").click(function() {
            var elm = $(this);
            elm.attr("disabled", "true");
            var data = [];
            $(".claimWeightNoteContainer").each(function (i, val) {
                data.push(getFormData($(val).find("input").serializeArray()));
            });
            console.log(data);
            
            $.ajax({
                url: getAbsolutePath() + "/shipping-instruction/"+siRef+"/claim-weight-note/"+claimRef+".htm",
                type: 'POST',
                processData: false, // tell jQuery not to process the data
                contentType: "application/json", // tell jQuery not to set contentType
                data: JSON.stringify(data),
                success: function (data1) {
                    goToPage(getAbsolutePath() + "/shipping-instruction/"+siRef+"/claim-detail/"+claimRef+".htm");
                }
            });

        });
    });
</script>
<jsp:include page="../footer.jsp"/>

</body>
</html>

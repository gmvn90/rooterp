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
    <title>Sample List</title>
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
    <jsp:param name="url" value="41"/>
    <jsp:param name="page" value="90"/>
</jsp:include>
<div style="width: 1400px ! important; padding: 15px; background: none repeat scroll 0% 0% white; height: auto; overflow: hidden; min-height: 400px"
     class="container_16 border" id="wrapper">
    <form method="get" action="sample-sent-v2-list.htm" id="x-form">
        <div style="width: 100%; overflow: hidden; height: auto; border-bottom: 1px solid #B8C2CC; padding-bottom: 10px">
            <table class="table_filter">
                <thead>
                <tr style="font-size: 13px">
                    <th width="200px">Sample Type</th>
                    <th width="200px">Client</th>
                    <th width="200px">Buyer</th>
                    <th width="190px">Sent Status</th>
                    <th width="190px">Approval Status</th>
                    <th width="190px">Date</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>
                        <select name="ssType" id="ssType" size="5" class="filter"
                                style="width: 100%; overflow: auto">
                            <option value="-1" <c:if test="${ssType==-1}">selected</c:if>>All</option>
                            <c:forEach var="item" items="${ssTypes}">
                                <option
                                        <c:if test="${item.status==ssType}">selected</c:if>
                                        value="${item.status}">${item.name}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td width="200px">
                        <input type="text" class="text_filter" style="width: 100%; max-height: inherit">
                        <select name="clientId" id="clientId" size="5" class="filter"
                                style="width: 100%; overflow: auto">
                            <option value="-1" <c:if test="${clientId==-1}">selected</c:if>>All</option>
                            <c:forEach var="item" items="${clients}">
                                <option
                                        <c:if test="${item.id==clientId}">selected</c:if>
                                        value="${item.id}">${item.name}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td width="200px">
                        <input type="text" class="text_filter" style="width: 100%; max-height: inherit">
                        <select name="buyerId" id="buyerId" size="5" class="filter"
                                style="width: 100%; overflow: auto">
                            <option value="-1" <c:if test="${buyerId==-1}">selected</c:if>>All</option>
                            <c:forEach var="item" items="${buyers}">
                                <option
                                        <c:if test="${item.id==buyerId}">selected</c:if>
                                        value="${item.id}">${item.name}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td width="190px">
                        <select name="sentStatus" id="sentStatus" size="5" class="filter"
                                style="width: 100%; overflow: auto">
                            <option value="-1" <c:if test="${sentStatus==-1}">selected</c:if>>All</option>
                            <c:forEach var="item" items="${sentStatuses}">
                                <option
                                        <c:if test="${item.status==sentStatus}">selected</c:if>
                                        value="${item.status}">${item.name}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td width="190px">
                        <select name="approvalStatus" id="approvalStatus" size="5" class="filter"
                                style="width: 100%; overflow: auto">
                            <option value="-1" <c:if test="${approvalStatus==-1}">selected</c:if>>All</option>
                            <c:forEach var="item" items="${approvalStatuses}">
                                <option
                                        <c:if test="${item.status==approvalStatus}">selected</c:if>
                                        value="${item.status}">${item.name}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td width="190px">
                        From:<br/><fmt:formatDate pattern="d-MMM-y"
                                                  value="${startDate}" var="formatStartDate"/>
                        <input name="startDate" class="js_date" id="startDate" value="${formatStartDate}">
                        <br/>To:<br/><fmt:formatDate pattern="d-MMM-y"
                                                     value="${endDate}" var="formatEndDate"/>
                        <input name="endDate" class="js_date" id="endDate" value="${formatEndDate}">
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <input class="right" id="createNew" data-href="sample-sent-type.htm" type="button" value="New Type Sample"/>

        <div style="width: 100%;overflow: hidden; height: auto; border-bottom: 1px solid #B8C2CC; padding-bottom: 10px">
            <h3>Sample sent list</h3>
            Show <select name="perPage" id="perPage"
                         style="width: 50px">
            <c:forEach var="item" items="${perPages}">
                <option
                        <c:if test="${item==perPage}">selected</c:if> value="${item}">${item}</option>
            </c:forEach>
        </select> entries
            <table class="dataTableStandard" style="width: 100%">
                <thead>
                <tr>
                    <th>Sample Ref.</th>
                    <th>Sample Type</th>
                    <th>Ref.</th>
                    <th>Client</th>
                    <th>Client Ref.</th>
                    <th>Buyer</th>
                    <th>Buyer Ref.</th>
                    <th>First Date</th>
                    <th>Origin</th>
                    <th>Quality</th>
                    <th>Grade</th>
                    <th>Courier</th>
                    <th>Eta Date</th>
                    <th>AWB No.</th>
                    <th>Sent Date</th>
                    <th>Sent Status</th>
                    <th>Approval Status</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="item" items="${sampleSents}">
                    <tr class="go2Detail" data-href="<c:if test="${item.isSampleSent}">shipping-instruction/${item.siRef}/sample-sent/${item.sampleRef}.htm</c:if><c:if test="${not item.isSampleSent}">sample-sent-type.htm?id=${item.sampleRef}</c:if>">
                        <td>${item.sampleRef}</td>
                        <td>${item.type}</td>
                        <td>${item.siRef}</td>
                        <td>${item.client}</td>
                        <td>${item.clientRef}</td>
                        <td>${item.buyer}</td>
                        <td>${item.buyerRef}</td>
                        <td><fmt:formatDate pattern="dd-MMM-yy" value="${item.firstDate}"/></td>
                        <td>${item.origin}</td>
                        <td>${item.quality}</td>
                        <td>${item.grade}</td>
                        <td>${item.courier}</td>
                        <td><fmt:formatDate pattern="dd-MMM-yy" value="${item.etaDate}"/></td>
                        <td>${item.aWBNo}</td>
                        <td><fmt:formatDate pattern="dd-MMM-yy" value="${item.sentDate}"/></td>
                        <c:choose>
                            <c:when test="${item.sentStatus == 'Pending'}">
                                <td style="color: red">${item.sentStatus}</td>
                            </c:when>
                            <c:otherwise>
                                <td>${item.sentStatus}</td>
                            </c:otherwise>
                        </c:choose>
                        <c:choose>
                            <c:when test="${item.approvalStatus == 'Pending'}">
                                <td style="color: red">${item.approvalStatus}</td>
                            </c:when>
                            <c:otherwise>
                                <td>${item.approvalStatus}</td>
                            </c:otherwise>
                        </c:choose>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            ${pagination}
        </div>
    </form>
    <script>
        var sampleSentId;
        $(document).ready(function () {
            $(".js_date").datepicker({
                dateFormat: "d-M-yy",
                changeMonth: true,
                changeYear: true,
                yearRange: "1945:2050"
            });
            var will_changes = "#ssType,#clientId,#buyerId,#sentStatus,#approvalStatus,.js_date";
            $(will_changes).change(function () {
                $('#x-form').find('.js_date').each(function () {
                    if ($(this).val() == '') {
                        $(this).attr('disabled', 'disabled');
                    }
                });
                $('#x-form').submit();
            });
        }).on("dblclick", "tr.go2Detail", function() {
            document.location = $(this).data('href');
//            sampleSentId = $(this).data("id");
//            $.sendRequest({
//                action: "getSampleSentFullDetail",
//                data: {
//                    "sampleSentId": sampleSentId
//                },
//                optional: {url_type: "json"}
//            }, function (msg) {
//                //var data = JSON.parse(msg);
//                var sampleSentPopUp = $.templates("#sampleSentPopUp");
//                if ($('#detailBox').length == 0) {
//                    $("body").append(sampleSentPopUp.render(msg));
//                }
//                $('#detailBox').fadeIn(300, function() {
//                    $("#btnCancel").click(function() {
//                        closeOpacity();
//                    });
//                    generateSelection("#courier", "clientMaster", msg.courier, {
//                        action : "get_allcompany_filter",
//                        offOverlay : false,
//                        reWriteData: true
//                    });
//                    $("#sentStatus").val(msg.sentStatus);
//                    $("#approvalStatus").val(msg.approvalStatus);
//                });
//                return false;
//            });
        }).on("click", "#btnSaveSampleSent", function() {
            $.sendRequest({
                action : "updateSampleSent",
                data : {
                    ssId : sampleSentId,
                    courier : $("#courier").val(),
                    trackingNo : $("#trackingNo").val(),
                    sentDate: $("#sentDate").val(),
                    etaDate: $("#etaDate").val(),
                    sentStatus: $("#popSentStatus").val(),
                    approvalStatus: $("#popApprovalStatus").val()
                },
                optional : {
                    url_type : "json"
                }
            }, function(msg) {
                $("#detailBox").remove();
                $.success("Update Succeeded");
                $('#x-form').submit();
            });
        }).on("click", "#createNew", function() {
            document.location = $(this).data('href');
        });

    </script>
    <script id="sampleSentPopUp" type="text/xhtml">
        <div id='detailBox' style='top: 20% !important;'>
            <fieldset>
                <table id="sampleSentDetailTable" style="width: 650px; margin-left: 100px;">
                    <tr>
                        <td class="subTitle" colspan="3">Sample Details:</td>
                    </tr>
                    <tr>
                        <td></td>
                        <td class="title">Sample Ref.:</td>
                        <td>{{:refNumber}}</td>
                        <td></td>
                        <td class="title">Sample Date:</td>
                        <td>{{:createdDate}}</td>
                    </tr>
                    <tr>
                        <td></td>
                        <td class="title">SI Ref.:</td>
                        <td>{{:siRef}}</td>
                        <td></td>
                        <td class="title">SI Date:</td>
                        <td>{{:siDate}}</td>
                    </tr>

                    <tr>
                        <td class="subTitle" colspan="3">Party Details:</td>
                    </tr>
                    <tr>
                        <td></td>
                        <td class="title">Client:</td>
                        <td>{{:client}}</td>
                        <td></td>
                        <td class="title">Client Ref.:</td>
                        <td>{{:clientRef}}</td>
                    </tr>
                    <tr>
                        <td></td>
                        <td class="title">Supplier:</td>
                        <td>{{:supplier}}</td>
                        <td></td>
                        <td class="title">Supplier Ref.:</td>
                        <td>{{:supplierRef}}</td>
                    </tr>
                    <tr>
                        <td></td>
                        <td class="title">Shipper:</td>
                        <td>{{:shipper}}</td>
                        <td></td>
                        <td class="title">Shipper Ref.:</td>
                        <td>{{:shipperRef}}</td>
                    </tr>
                    <tr>
                        <td></td>
                        <td class="title">Buyer:</td>
                        <td>{{:buyer}}</td>
                        <td></td>
                        <td class="title">Buyer Ref.:</td>
                        <td>{{:buyerRef}}</td>
                    </tr>

                    <tr>
                        <td class="subTitle" colspan="3">Shipping Details:</td>
                    </tr>
                    <tr>
                        <td></td>
                        <td class="title">First Date:</td>
                        <td>{{:firstDate}}</td>
                        <td></td>
                        <td class="title">Last Date:</td>
                        <td>{{:lastDate}}</td>
                    </tr>

                    <tr>
                        <td class="subTitle" colspan="3">Quality Details:</td>
                        <td class="subTitle" colspan="3">Certificates:</td>
                    </tr>
                    <tr>
                        <td></td>
                        <td class="title">Origin:</td>
                        <td>{{:origin}}</td>
                        <td></td>
                        <td class="title">Quality & Weight:</td>
                        <td>{{:qualityWeightCerti}}</td>
                    </tr>
                    <tr>
                        <td></td>
                        <td class="title">Quality:</td>
                        <td>{{:quality}}</td>
                        <td></td>
                        <td class="title">Fumigation:</td>
                        <td>{{:fumigation}}</td>
                    </tr>
                    <tr>
                        <td></td>
                        <td class="title">Grade:</td>
                        <td>{{:grade}}</td>
                    </tr>

                    <tr>
                        <td class="subTitle" colspan="3">Sample Sent Details:</td>
                    </tr>
                    <tr>
                        <td></td>
                        <td class="title">Courier:</td>
                        <td colspan="3"><select name="courier" id="courier" style="width: 100%">

                        </select></td>
                    </tr>
                    <tr>
                        <td></td>
                        <td class="title">Tracking No.:</td>
                        <td colspan="3"><input type="text" class="" style="" name="trackingNo" id="trackingNo" value="{{:trackingNo}}"/></td>
                    </tr>
                    <tr>
                        <td></td>
                        <td class="title">Sent Date:</td>
                        <td colspan="3"><input type="text" class="date_picker_text" name="sentDate" id="sentDate" value="{{:sentDate}}"
                                               style=""/></td>
                    </tr>
                    <tr>
                        <td></td>
                        <td class="title">ETA Date:</td>
                        <td colspan="3"><input type="text" class="date_picker_text" name="etaDate" id="etaDate" value="{{:etaDate}}"
                                               style=""/></td>
                    </tr>
                    <tr>
                        <td></td>
                        <td class="title">Sending Status:</td>
                        <td colspan="3"><select name="popSentStatus" id="popSentStatus" style="width: 100%">
                            <option value="0">Pending</option>
                            <option value="1">Sent</option>
                        </select></td>
                    </tr>
                    <tr>
                        <td></td>
                        <td class="title">Approval Status:</td>
                        <td colspan="3"><select name="popApprovalStatus" id="popApprovalStatus" style="width: 100%">
                            <option value="0">Pending</option>
                            <option value="1">Approved</option>
                            <option value="2">Rejected</option>
                        </select></td>
                    </tr>

                    <tr>
                        <td class="title" colspan="2">Authorized By:</td>
                        <td colspan="4">{{:userName}}:{{:updatedDate}}<input type="button" id="btnSaveSampleSent" value="Save" name=""/></td>

                    </tr>
                </table>

            </fieldset>
            <input class="center" style="margin-top: 50px" type="button" id="btnCancel" value="Cancel" name=""/>
        </div>
    </script>
</div>
<jsp:include page="footer.jsp"/>
</body>
</html>

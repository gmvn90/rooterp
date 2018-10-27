
<%--
  Created by IntelliJ IDEA.
  User: gmvn
  Date: 8/9/16
  Time: 3:12 PM
  To change this template use File | Settings | File Templates.
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
    <title>Shipping report</title>
    <style>
        table.advice_detail {

        }

        td.title {
            padding-left: 50px !important;
        }

        td.content {
            padding-left: 70px !important;
        }

        td.content input {
            border-color: initial !important;
            border-radius: 0px !important;
        }

        table.si_wn_detail {
            color: #333;
            width: 1030px;
            border-collapse: collapse;
            border-spacing: 0;
        }

        table.si_wn_detail td {
            border-bottom: 1px solid black;
            height: 20px;
        }

        table.si_wn_detail tr.head {
            background: #DFDFDF; /* Darken header a bit */
            font-weight: bold;
            border-bottom: 1px solid black;
        }

        table.si_wn_detail tr.foot {
            background: aliceblue; /* Darken header a bit */
            font-weight: bold;
            border-bottom: 0px solid black;
        }

        table.si_wn_detail th.text {
            text-align: left;
        }

        table.si_wn_detail td.text {
            text-align: left;
            width: 170px;
        }

        table.si_wn_detail th.number {
            text-align: right;
        }

        table.si_wn_detail td.number {
            text-align: right;
            width: 150px;
        }

        table.si_wn_detail td.number input {
            text-align: right;
            width: 150px;
        }
        input[type="text"] {
            width: 50px;
        }
        .advice_detail input[type="text"] {
            width: 150px;
        }
        input[type="text"]:disabled {
            background: #dddddd;
        }
        .si_wn_detail input[type="button"] {
            font-size: 90%;
            min-width: 70px;
        };

    </style>

</head>
<body>
<jsp:include page="../header.jsp">
    <jsp:param name="url" value="40"/>
    <jsp:param name="page" value="87"/>
</jsp:include>
<div id="main_content" style='width: 1050px'>
    
    <div id="common_info"><table class="advice_detail" style="">
    <tbody><tr>
        <td colspan="7">Claim Details:</td>
    </tr>
    <tr>
        <td></td>
        <td class="title" align="right">SI Ref.:</td>
        <td class="content" align="left">
            <input type="text" class="" style="" name="" id="" value="${si.refNumber}" disabled="">
        </td>
        <td class="title" align="right">SI Date:</td>
        <td class="content" align="left">
            <input type="text" class="" style="" name="" id="" value="${f:formatDateTimeAMPM(si.date)}" disabled="">
        </td>
    </tr>
    <tr>
        <td></td>
        <td class="title" align="right">Shipping Advice Ref.:</td>
        <td class="content" align="left">
            <input type="text" class="" style="" name="" id="" value="${si.shippingAdviceRefNumber}" disabled="">
        </td>
        <td class="title" align="right">SA Date:</td>
        <td class="content" align="left">
            <input type="text" class="" style="" name="" id="" value="${f:formatDateTimeAMPM(si.shippingAdviceDate)}" disabled="">
        </td>
    </tr>

    <tr>
        <td colspan="7">Party Details:</td>
    </tr>
    <tr>
        <td></td>
        <td class="title" align="right">Client:</td>
        <td class="content" align="left">
            <input type="text" class="" style="" name="" id="" value="${si.client}" disabled="">
        </td>
        <td class="title" align="right">Client Ref.:</td>
        <td class="content" align="left">
            <input type="text" class="" style="" name="" id="" value="${si.clientRef}" disabled="">
        </td>
    </tr>
    <tr>
        <td></td>
        <td class="title" align="right">Supplier:</td>
        <td class="content" align="left">
            <input type="text" class="" style="" name="" id="" value="${si.supplier}" disabled="">
        </td>
        <td class="title" align="right">Supplier Ref.:</td>
        <td class="content" align="left">
            <input type="text" class="" style="" name="" id="" value="${si.supplierRef}" disabled="">
        </td>
    </tr>
    <tr>
        <td></td>
        <td class="title" align="right">Shipper:</td>
        <td class="content" align="left">
            <input type="text" class="" style="" name="" id="" value="${si.shipper}" disabled="">
        </td>
        <td class="title" align="right">Shipper Ref.:</td>
        <td class="content" align="left">
            <input type="text" class="" style="" name="" id="" value="${si.shipperRef}" disabled="">
        </td>
    </tr>
    <tr>
        <td></td>
        <td class="title" align="right">Buyer:</td>
        <td class="content" align="left">
            <input type="text" class="" style="" name="" id="" value="${si.buyer}" disabled="">
        </td>
        <td class="title" align="right">Buyer Ref.:</td>
        <td class="content" align="left">
            <input type="text" class="" style="" name="" id="" value="${si.buyerRef}" disabled="">
        </td>
    </tr>
    <tr>
        <td></td>
        <td class="title" align="right">Consignee:</td>
        <td class="content" align="left">
            <input type="text" class="" style="" name="" id="" value="${si.consignee}" disabled="">
        </td>
    </tr>
    <tr>
        <td></td>
        <td class="title" align="right">Notify Party:</td>
        <td class="content" align="left">
            
        </td>
    </tr>

    <tr>
        <td colspan="7">Shipping Details:</td>
    </tr>
    <tr>
        <td></td>
        <td class="title" align="right">B/L Number:</td>
        <td class="content" align="left">
            <input type="text" class="" style="" name="" id="" value="${si.blNumber}" disabled="">
        </td>
        <td class="title" align="right">B/L Date:</td>
        <td class="content" align="left">
            <input type="text" class="" style="" name="" id="" value="${f:formatDateOnly(si.blDate)}" disabled="">
        </td>
    </tr>
    <tr>
        <td></td>
        <td class="title" align="right">Container Status:</td>
        <td class="content" align="left">
            <input type="text" class="" style="" name="" id="" value="${si.containerStatus}" disabled="">
        </td>
    </tr>
    <tr>
        <td></td>
        <td class="title" align="right">Feeder Vessel:</td>
        <td class="content" align="left">
            <input type="text" class="" style="" name="" id="" value="${si.feederVessel}" disabled="">
        </td>
        <td class="title" align="right">Etd:</td>
        <td class="content" align="left">
            <input type="text" class="" style="" name="" id="" value="${f:formatDateOnly(si.feederEts)}" disabled="">
        </td>
        <td class="title" align="right">Eta:</td>
        <td class="content" align="left">
            <input type="text" class="" style="" name="" id="" value="${f:formatDateOnly(si.feederEta)}" disabled="">
        </td>
    </tr>
    <tr>
        <td></td>
        <td class="title" align="right">Port of Loading:</td>
        <td class="content" align="left">
            <input type="text" class="" style="" name="" id="" value="${si.loadingPort}" disabled="">
        </td>
    </tr>
    <tr>
        <td></td>
        <td class="title" align="right">Transit Port:</td>
        <td class="content" align="left">
            <input type="text" class="" style="" name="" id="" value="${si.transitPort}" disabled="">
        </td>
    </tr>
    <tr>
        <td></td>
        <td class="title" align="right">Mother Vessel:</td>
        <td class="content" align="left">
            <input type="text" class="" style="" name="" id="" value="${si.oceanVessel}" disabled="">
        </td>
        <td class="title" align="right">Etd:</td>
        <td class="content" align="left">
            <input type="text" class="" style="" name="" id="" value="${f:formatDateOnly(si.oceanEts)}" disabled="">
        </td>
        <td class="title" align="right">Eta:</td>
        <td class="content" align="left">
            <input type="text" class="" style="" name="" id="" value="${f:formatDateOnly(si.oceanEta)}" disabled="">
        </td>
    </tr>
    <tr>
        <td></td>
        <td class="title" align="right">Port of Discharge:</td>
        <td class="content" align="left">
            <input type="text" class="" style="" name="" id="" value="${si.destination}" disabled="">
        </td>
    </tr>
    <tr>
        <td></td>
        <td class="title" align="right">ICO Number</td>
        <td class="content" align="left">
            <input type="text" class="" style="" name="" id="" value="${si.icoNumber}" disabled="">
        </td>
    </tr>


    <tr>
        <td colspan="7">Shipping Goods:</td>
    </tr>
    <tr>
        <td></td>
        <td class="title" align="right">Origin:</td>
        <td class="content" align="left">
            <input type="text" class="" style="" name="" id="" value="${si.origin}" disabled="">
        </td>
    </tr>
    <tr>
        <td></td>
        <td class="title" align="right">Quality:</td>
        <td class="content" align="left">
            <input type="text" class="" style="" name="" id="" value="${si.quality}" disabled="">
        </td>
    </tr>
    <tr>
        <td></td>
        <td class="title" align="right">Remarks:</td>
        <td colspan="3" class="content" align="left">
            <textarea type="text" class="" style="width: 475px; height: 75px" name="" id="sa_remark" disabled="">${si.remark}</textarea>
        </td>
    </tr>
</tbody></table>

    <table class="si_wn_detail" style="margin-top: 20px;">
        <tbody>
            <tr class="head">
                <th class="text" colspan="3">Container Details:</th>
                <th class="text" colspan="3">Shipped Weight Details:</th>
            </tr>
            <tr class="head">
                <th class="text">Container No.:</th>
                <th class="text">Seal No.:</th>
                <th class="text">Bags:</th>
                <th class="number">Gross Weight Mt.</th>
                <th class="number">Tare Weight Mt.</th>
                <th class="number">Net Weight Mt.</th>
            </tr>
            <c:forEach var="item" items="${aggegrate.contractWeightNotes}">
                <tr class="input_row">
                    <td class="text">${item.containerNo}</td>
                    <td class="text">${item.sealNo}</td>
                    <td class="text"><input type="text" class="iwnbag numberOnly" value="${item.noOfBags}" id="inputbag_0" disabled=""></td>
                    <td class="number"><input type="text" class="iwngross numberOnly" value="${item.grossWeight}" id="inputgross_0" disabled=""></td>
                    <td class="number"><input type="text" class="iwntare numberOnly" value="${item.tareWeight}" id="inputtare_0" disabled=""></td>
                    <td class="number"><input type="text" class="iwnnet" value="${item.netWeight}" id="inputnet_0" disabled=""></td>
                </tr>
            </c:forEach>

            <tr class="foot">
                    <th class="number"></th>
                    <th class="text">Total:</th>
                    <th class="text" id="tiwnbag">${aggegrate.totalContractWeightNotes.noOfBags}</th>
                    <th class="number" id="tiwngross">${aggegrate.totalContractWeightNotes.grossWeight}</th>
                    <th class="number" id="tiwntare">${aggegrate.totalContractWeightNotes.tareWeight}</th>
                    <th class="number" id="tiwnnet">${aggegrate.totalContractWeightNotes.netWeight}</th>
            </tr>

        </tbody>
    </table>

    <table class="si_wn_detail" style="margin-top: 20px;">
        <tbody>
            <tr class="head">
                <th class="text" colspan="3">Container Details:</th>
                <th class="text" colspan="3">Shipped Weight Details:</th>
            </tr>
            <tr class="head">
                <th class="text">Container No.:</th>
                <th class="text">Seal No.:</th>
                <th class="text">Bags:</th>
                <th class="number">Gross Weight Mt.</th>
                <th class="number">Tare Weight Mt.</th>
                <th class="number">Net Weight Mt.</th>
            </tr>
            <c:forEach var="item" items="${aggegrate.weightNotes}">
                <tr class="input_row">
                    <td class="text">${item.containerNo}</td>
                    <td class="text">${item.sealNo}</td>
                    <td class="text"><input type="text" class="iwnbag numberOnly" value="${item.noOfBags}" id="inputbag_0" disabled=""></td>
                    <td class="number"><input type="text" class="iwngross numberOnly" value="${item.grossWeight}" id="inputgross_0" disabled=""></td>
                    <td class="number"><input type="text" class="iwntare numberOnly" value="${item.tareWeight}" id="inputtare_0" disabled=""></td>
                    <td class="number"><input type="text" class="iwnnet" value="${item.netWeight}" id="inputnet_0" disabled=""></td>
                </tr>
            </c:forEach>

            <tr class="foot">
                    <th class="number"></th>
                    <th class="text">Total:</th>
                    <th class="text" id="tiwnbag">${aggegrate.totalWeightNotes.noOfBags}</th>
                    <th class="number" id="tiwngross">${aggegrate.totalWeightNotes.grossWeight}</th>
                    <th class="number" id="tiwntare">${aggegrate.totalWeightNotes.tareWeight}</th>
                    <th class="number" id="tiwnnet">${aggegrate.totalWeightNotes.netWeight}</th>
            </tr>

            <tr>
                <td colspan="6"></td>
            </tr>

            <tr class="foot">
                <th class="number"></th>
                <th class="text">Variance:</th>
                <th class="text"></th>
                <th class="number" id="varianceGross">${aggegrate.diff.grossWeight}</th>
                <th class="number" id="varianceTare">${aggegrate.diff.tareWeight}</th>
                <th class="number" id="varianceNet">${aggegrate.diff.netWeight}</th>
            </tr>

        </tbody>
    </table>




</div>
    <input class="center" style="margin-top: 20px" type="button" id="btn_generate_pdf" value="Generate PDF" name=""/>
    <a class="right" href="<c:url value = "/shipping-v2.htm?id=${id}"/>">Go back to SI</a>
    &nbsp;&nbsp;<a class="right" href="<c:url value = "/shipping-instruction/${id}/loading_report.htm"/>">Go to loading report</a>
</div>
<jsp:include page="../footer.jsp"/>
<script type="text/javascript">
        var shipping_instruction_id = "${id}";
        $(document).ready(function() {
            console.log("ready");

            $("#btn_generate_pdf").click(function () {
                    var result = {
                        rows: []
                    };
                    var si_id = shipping_instruction_id;
                    var i = 0;
                    $("tr.input_row").each(function () {
                        var row = {
                            bag: $("#inputbag_" + i).val(),
                            gross: $("#inputgross_" + i).val(),
                            tare: $("#inputtare_" + i).val(),
                            net: $("#inputnet_" + i).val()
                        };
                        console.log(row);
                        result.rows.push(row);
                        i++;
                    });
                    $.sendRequest({
                        action: "shipping-instruction/" +  si_id + "/generate_shipping_advice_pdf",
                        
                    }, function (msg) {
                        var fileUrl = {
                            fileUrl: msg
                        };
                        console.log(msg);
                        var sendPdfPopUp = $.templates("#sendPdfPopUp");
                        if ($('#detailBox').length == 0) {
                            $("body").append(sendPdfPopUp.render(fileUrl));
                        }
                        $('#detailBox').fadeIn(300, function() {
                            $("#btnCancel").click(function() {
                                closeOpacity();
                            });
                        });

                        return false;
                    });

                });
            
        });

    </script>

    <script id="sendPdfPopUp" type="text/xhtml">
        <div id='detailBox' style='top: 20% !important'>
            <div style='height: 300px;width: 100%; overflow: scroll;'>
                <embed src="{{:fileUrl}}" type="application/pdf" width="100%" height="100%"/>
            </div>
            <table style="margin-top: 20px; font-size: 9pt">
                <tr>
                    <td>To:</td>
                    <td><select style="width: 400px;" class="multiEmail" name="" id="selectEmailTo" multiple="multiple"></select></td>
                    <td rowspan='2'><input class="center" style="" type="button" id="btnSendPdf" value="Send" name=""/></td>
                </tr>
                <tr>
                    <td>CC:</td>
                    <td><select style="width: 400px;" class="multiEmail" name="" id="selectEmailCc" multiple="multiple"></select></td>
                </tr>
                <tr>
                    <td><input class="center" style="margin-top: 50px" type="button" id="btnCancel" value="Cancel this file" name=""/></td>
                </tr>
            </table>
        </div>
    </script>
</body>



</html>

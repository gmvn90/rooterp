
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
        <title>Loading report</title>
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
                width: 60px;
            }
            input[type="text"]:disabled {
                background: #dddddd;
            };
            
            .longer_input {
                width: 150px;
            };
            
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
        <div id="main_content" style='width: 1300px'>
            <div id="common_info">

                <table class="advice_detail" style="">
                    <tr>
                        <td colspan="7">Loading report:</td>
                    </tr>
                    <tr>
                        <td></td>
                        <td class="title" align="right">Booking No.:</td>
                        <td class="content" align="left" colspan="5">
                            ${si.bookingRef}
                        </td>
                    </tr>
                    <tr>
                        <td></td>
                        <td class="title" align="right">Shipping line:</td>
                        <td class="content" align="left" colspan="5">
                            ${si.shippingLineCompanyMaster}
                        </td>
                    </tr>
                </table>

                <table class="si_wn_detail">
                    <tr>
                        <td>No</td>
                        <td>Container No</td>
                        <td>Seal No</td>
                        <td>WEIGHT NW(KGS)</td>
                        <td>TW (KGS)</td>
                        <td>GW (KGS)</td>
                        <td>TW EMPTY</td>
                        <td>Dry bag(kgs)</td>
                        <td>Kraft(kgs)</td>
                        <td>Carton(kgs)</td>
                        <td>Pallet(kgs)</td>
                        <td>VGM(KGS)</td>
                        <td>MAX GROSS</td>
                        <td>Tally</td>
                        <td>Kind of bag</td>
                        <td></td>
                    </tr>

                    <c:forEach var="item" items="${contractWeightNotes}" varStatus="loop">
                        <tr class="contractWeightNoteContainer">
                            <td></td>
                            <td>${item.containerNo}</td>
                            <td>${item.sealNo}</td>
                            <td><input type="text" class="number netWeight" value="${item.contractNetWeight}" disabled=""></td>
                            <td><input type="text" class="number changable tareWeight" name="contractTareWeight" value="${item.contractTareWeight}"></td>
                            <td><input type="text" class="number changable grossWeight" name="contractGrossWeight" value="${item.contractGrossWeight}"></td>
                            <td><input type="text" class="number emptyContainerWeight" name="emptyContainerWeight" value="${item.emptyContainerWeight}"></td>
                            <td><input type="text" class="number dryBagWeight" name="dryBagWeight" value="${item.dryBagWeight}"></td>
                            <td><input type="text" class="number draftWeight" name="draftWeight" value="${item.draftWeight}"></td>
                            <td><input type="text" class="number cartonWeight" name="cartonWeight" value="${item.cartonWeight}"></td>
                            <td><input type="text" class="number palletWeight" name="palletWeight" value="${item.palletWeight}"></td>
                            <td><input type="text" class="number vgmWeight" value="${item.vgmWeight}" disabled=""></td>
                            <td><input type="text" class="number" name="maxGrossContainerWeight" value="${item.maxGrossContainerWeight}"></td>
                            <td><input type="text" class="number noOfBag" name="tallyQuantity" value="${item.tallyQuantity}"></td>
                            <td>
                                ${item.packing}
                                <input type="hidden" value="${item.weightNoteId}" name="weightNoteId" />
                            </td>
                            <td><c:if test="${loop.index != 0}"><input type="button" value="Copy Prev" class="copyPrevious"></c:if></td>
                        </tr>
                    </c:forEach>
                        <tr class="contractWeightNoteAggegrateContainer">
                            <td></td>
                            <td>Total</td>
                            <td></td>
                            <td><input type="text" class="number totalContractNetWeight" value="${aggegrate.totalContractWeightNotes.netWeight}" disabled=""></td>
                            <td><input type="text" class="number totalContractTareWeight" name="" value="${aggegrate.totalContractWeightNotes.tareWeight}" disabled=""></td>
                            <td><input type="text" class="number totalContractGrossWeight" name="" value="${aggegrate.totalContractWeightNotes.grossWeight}" disabled=""></td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td><input type="text" class="number totalNoOfBag" value="${aggegrate.totalContractWeightNotes.noOfBags}" disabled=""></td>
                            <td></td>
                            <td></td>
                        </tr>
                        <tr>
                            <td colspan="16">Weight note info</td>
                        </tr>
                    <c:forEach var="item" items="${aggegrate.weightNotes}" varStatus="loop">
                        <tr class="weightNoteContainer">
                            <td></td>
                            <td>${item.containerNo}</td>
                            <td>${item.sealNo}</td>
                            <td><input type="text" class="number" value="${item.netWeight}" disabled=""></td>
                            <td><input type="text" class="number" name="" value="${item.tareWeight}" disabled=""></td>
                            <td><input type="text" class="number" name="" value="${item.grossWeight}" disabled=""></td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td><input type="text" class="number" value="${item.noOfBags}" disabled=""></td>
                            <td></td>
                            <td></td>
                        </tr>
                    </c:forEach>
                    <tr class="weightNoteAggegrateContainer">
                        <td></td>
                        <td><b>Total</b></td>
                        <td></td>
                        <td><input type="text" class="number" value="${aggegrate.totalWeightNotes.netWeight}" disabled=""></td>
                        <td><input type="text" class="number" name="" value="${aggegrate.totalWeightNotes.tareWeight}" disabled=""></td>
                        <td><input type="text" class="number" name="" value="${aggegrate.totalWeightNotes.grossWeight}" disabled=""></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td><input type="text" class="number" value="${aggegrate.totalWeightNotes.noOfBags}" disabled=""></td>
                        <td></td>
                        <td></td>
                    
                    </tr>
                    <tr class="varianceContainer">
                        <td></td>
                        <td><b>Variance</b></td>
                        <td></td>
                        <td><input type="text" class="number variantNetWeight" value="${aggegrate.diff.netWeight}" disabled=""></td>
                        <td><input type="text" class="number variantTareWeight" name="" value="${aggegrate.diff.tareWeight}" disabled=""></td>
                        <td><input type="text" class="number variantGrossWeight" name="" value="${aggegrate.diff.grossWeight}" disabled=""></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td><input type="text" class="number variantNoOfBags" value="${aggegrate.diff.noOfBags}" disabled=""></td>
                        <td></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td colspan="16"><input type="button" value="Save All" id="saveContractWeightNotes"></td>
                    </tr>
                </table>

            </div>
            <form id="attach-loading-report-form" method="POST" action="${base_web_url}/shipping-instruction/${id}/attach-loading-report.htm"></form>
            <input class="center" style="margin-top: 20px" type="button" id="export-summary-report" value="Export loading report"/>
            <input form="attach-loading-report-form" class="center" style="margin-top: 20px" type="submit" value="Attach loading report"/>
            <a class="right" href="${base_web_url}/shipping-instruction/${id}/shipping_advice.htm">Go to shipping advice</a>

        </div>
        <jsp:include page="../footer.jsp"/>
        <script type="text/javascript">
            function getTotalInputs(klass) {
                var sum = 0;
                $(".contractWeightNoteContainer ." + klass).each(function(i, val) {
                    sum += parseFloat($(val).val());
                });
                return sum;
            }
            var totalWeightNotes = {
              netWeight: ${aggegrate.totalWeightNotes.netWeight},
              grossWeight: ${aggegrate.totalWeightNotes.grossWeight},
              tareWeight: ${aggegrate.totalWeightNotes.tareWeight},
              noOfBags: ${aggegrate.totalWeightNotes.noOfBags},
            };
            var totalContractWeightNotes = {
              netWeight: ${aggegrate.totalContractWeightNotes.netWeight},
              grossWeight: ${aggegrate.totalContractWeightNotes.grossWeight},
              tareWeight: ${aggegrate.totalContractWeightNotes.tareWeight},
              noOfBags: ${aggegrate.totalContractWeightNotes.noOfBags},
            };
            function getTotalContractGrossWeight() {
                return getTotalInputs("grossWeight");
            }
            function getTotalContractNetWeight() {
                return getTotalInputs("netWeight");
            }
            function getTotalContractTareWeight() {
                return getTotalInputs("tareWeight");
            }
            function getTotalNoOfBag() {
                return getTotalInputs("noOfBag");
            }
            function updateTotal() {
                totalContractWeightNotes.netWeight = getTotalContractNetWeight();
                $(".totalContractNetWeight").val(totalContractWeightNotes.netWeight);
                totalContractWeightNotes.tareWeight = getTotalContractTareWeight();
                $(".totalContractTareWeight").val(totalContractWeightNotes.tareWeight);
                totalContractWeightNotes.grossWeight = getTotalContractGrossWeight();
                $(".totalContractGrossWeight").val(totalContractWeightNotes.grossWeight);
                totalContractWeightNotes.noOfBags = getTotalNoOfBag();
                $(".totalNoOfBag").val(totalContractWeightNotes.noOfBags);
                
                $(".variantGrossWeight").val(totalContractWeightNotes.grossWeight - totalWeightNotes.grossWeight);
                $(".variantNetWeight").val(totalContractWeightNotes.netWeight - totalWeightNotes.netWeight);
                $(".variantTareWeight").val(totalContractWeightNotes.tareWeight - totalWeightNotes.tareWeight);
                $(".variantNoOfBags").val(totalContractWeightNotes.noOfBags - totalWeightNotes.noOfBags);
            };
            
            function updateNetWeight(elm) {
                var container = elm.closest(".contractWeightNoteContainer");
                container.find(".netWeight").val(
                        parseFloat(container.find(".grossWeight").val()) - parseFloat(container.find(".tareWeight").val()));
            };
            
            
            var shipping_instruction_id = "${id}";
            function updateContractWeightNotes(shippingId, serializedData) {
                $.ajax({
                    url: getAbsolutePath() + '/shipping-instruction/' + shippingId + '/update_contract_weight_notes.htm',
                    type: 'POST',
                    processData: false, // tell jQuery not to process the data
                    contentType: "application/json", // tell jQuery not to set contentType
                    data: serializedData,
                    success: function (data) {
                        goToPage(getAbsolutePath() + '/shipping-instruction/' + shippingId + '/loading_report.htm');
                    }
                });
            }
            ;

            function sumbmitGenerateContractWeightNoteInfo(shippingId) {
                $.ajax({
                    url: base_web_url + '/shipping-instruction/' + shippingId + '/generate_contract_weight_note.htm',
                    type: 'POST',
                    processData: false, // tell jQuery not to process the data
                    contentType: false, // tell jQuery not to set contentType
                    success: function (data) {
                        goToPage(base_web_url + "/shipping-v2.htm?id=" + shippingId);
                    }
                });

            }
            ;

            $(document).ready(function () {
                console.log("ready");
                $("#saveContractWeightNotes").click(function () {
                    var elm = $(this);
                    var data = [];
                    $(".contractWeightNoteContainer").each(function (i, val) {
                        data.push(getFormData($(val).find("input").serializeArray()));
                    });
                    console.log(data);
                    updateContractWeightNotes(shipping_instruction_id, JSON.stringify(data));
                });

                $("#export-summary-report").click(function (e) {
                    $.ajax({
                        url: base_web_url + '/shipping-instruction/' + shipping_instruction_id + '/generate_summary_report.htm',
                        type: 'POST',
                        processData: false, // tell jQuery not to process the data
                        contentType: "application/json", // tell jQuery not to set contentType
                        success: function (data) {
                            window.open(data, '_blank');
                        }
                    });
                });
                $(".copyPrevious").click(function () {
                    var elm = $(this);
                    var thisTr = elm.closest("tr");
                    var previousTr = thisTr.prev();
                    previousTr.find("input[type='text'][name]").each(function () {
                        var thisInput = $(this);
                        thisTr.find("input[name=\"" + thisInput.attr("name") + "\"]").val(thisInput.val());
                    });
                    updateNetWeight(thisTr.find(".tareWeight"));
                    updateTotal();
                });
                $(".grossWeight,.tareWeight").on("input", function() {
                    updateNetWeight($(this));
                    updateTotal();
                });
                $(".noOfBag").on("input", function() {
                    updateTotal();
                });
            });

            



        </script>
    </body>



</html>

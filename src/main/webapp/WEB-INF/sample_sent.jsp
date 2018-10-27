<%--
  Created by IntelliJ IDEA.
  User: gmvn
  Date: 9/11/16
  Time: 11:59 AM
  To change this template use File | Settings | File Templates.
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Sample Sent</title>
    <style>
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
<c:set var="initial_id" value="${initial_id}"></c:set>

<div id="main_content"
     style="width: 1500px ! important; padding: 15px; background: none repeat scroll 0% 0% white; height: auto; overflow: hidden"
     class="container_16 border" id="wrapper">
    <div style="overflow: hidden; height: auto; border-bottom: 1px solid #B8C2CC; padding-bottom: 10px">
        <table class="table_filter">
            <thead>
            <tr style="font-size: 13px">
                <th width="200px">Client</th>
                <th width="120px">Sent Status</th>
                <th width="120px">Approval Status</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>
                    <input type="text" style="width: 200px" class="text_filter_child">
                    <ul id="client_filter" class="filter">
                        <li id="client_-1" class="chosen">All</li>
                    </ul>
                </td>
                <td>
                    <input type="text" style="width: 120px;" class="text_filter_child">
                    <ul id="sentStatus_filter" class="filter">
                        <li id="sentStatus_-1" class="chosen">All</li>
                        <li id="sentStatus_0">Pending</li>
                        <li id="sentStatus_3">Sent</li>
                    </ul>
                </td>
                <td>
                    <input type="text" style="width: 120px;" class="text_filter_child">
                    <ul id="approvalStatus_filter" class="filter">
                        <li id="approvalStatus_-1" class="chosen">All</li>
                        <li id="approvalStatus_0">Pending</li>
                        <li id="approvalStatus_1">Approved</li>
                        <li id="approvalStatus_2">Rejected</li>
                    </ul>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
    <div style="width: 100%; overflow: hidden; height: auto; border-bottom: 1px solid #B8C2CC; padding-bottom: 10px">
        <table id='tableSampleSentList' style='width: 100%'>
            <thead>
            <tr>
                <th>Sample Ref.</th>
                <th>SI Ref.</th>
                <th>Client</th>
                <th>Client Ref.</th>
                <th>Buyer</th>
                <th>First Date</th>
                <th></th>
                <th>Origin</th>
                <th>Quality</th>
                <th>Grade</th>
                <th></th>
                <th>Courier</th>
                <th>AWB No.</th>
                <th>Sent Date</th>
                <th></th>
                <th>Sent Status</th>
                <th></th>
                <th>Approval Status</th>
            </tr>
            </thead>
            <tbody>

            </tbody>
        </table>
    </div>

</div>
<script type="text/javascript">
    var oTable;
    var sampleSentId;
    $(document).ready(function () {
        generateSelection("#client_filter", "CompanyMaster", "client_-1", {
            action: "get_allcompany_filter",
            type: "list",
            prefix: "client_"
        });
        oTable = $('#tableSampleSentList').dataTable({
            "aLengthMenu": [[10, 25, 50, 100, -1], [10, 25, 50, 100, "All"]],
            "bProcessing": true,
            "bServerSide": true,
            "bFilter": true,
            "bInfo": false,
            "sAjaxSource": "sampleSentListSource.htm",
            "fnServerParams": function (aoData) {
                aoData.push(
                        {"name": "clientId", "value": $("#client_filter li.chosen").attr("id").split("_")[1]},
                        {"name": "sentStatus", "value": $("#sentStatus_filter li.chosen").attr("id").split("_")[1]},
                        {"name": "approvalStatus", "value": $("#approvalStatus_filter li.chosen").attr("id").split("_")[1]}
                );
            },
            "aoColumnDefs": [
                {"sClass": "empty_col", "aTargets": [6,10,14,16]},
                {"sWidth": "80px", "aTargets": [7,8]},
                {"sWidth": "90px", "aTargets": [0,1,15,17]},
                {"sWidth": "120px", "aTargets": [2,3,4,11,12]},
                {"sWidth": "200px", "aTargets": [9,5,13]}
            ],
            "fnServerData": function (sSource, aoData, fnCallback) {
                $.getJSON(sSource, aoData, function (json) {
                    /* Do whatever additional processing you want on the callback, then tell DataTables */
//                            $("#bought").text(accounting.formatMoney(json.bought, ""));
//                            $("#sold").text(accounting.formatMoney(json.sold, ""));

                    console.log(json);
                    fnCallback(json);
                });
            },
            "fnDrawCallback": function (oSettings, json) {
                $("tbody tr[class*=data_row]").each(function () {
                    //Set authentication for updating sample sent
                    //$(this).attr('name', 'perm_40');
                    if ($(this).attr("id") !== 'mytable_footer') {
                        $(this).attr("title", "Double Click To Update Sample Sent");
                    }
                });
                //show tool tip
                $('.masterTooltip').hover(function () {
                    // Hover over code
                    var title = $(this).attr('title');
                    $(this).data('tipText', title).removeAttr('title');
                    $('<p class="tooltip"></p>')
                            .text(title)
                            .appendTo('body')
                            .fadeIn('slow');
                }, function () {
                    // Hover out code
                    $(this).attr('title', $(this).data('tipText'));
                    $('.tooltip').remove();
                }).mousemove(function (e) {
                    var mousex = e.pageX + 20; //Get X coordinates
                    var mousey = e.pageY + 10; //Get Y coordinates
                    $('.tooltip')
                            .css({
                                top: mousey,
                                left: mousex
                            })
                });
            },
            "sPaginationType": "full_numbers",
            "sDom": 'Rlfrtip',
            "oColVis": {
                "fnLabel": function(index, title, th) {
                    if (title != "") {
                        return (index + 1) + '. ' + title;
                    }
                }
            }
        });
    }).on("click", "#client_filter li", function() {
        doDataTableFilter(oTable);
    }).on("click", "#sentStatus_filter li", function() {
        doDataTableFilter(oTable);
    }).on("click", "#approvalStatus_filter li", function() {
        doDataTableFilter(oTable);
    }).on("dblclick", "tr.data_row", function() {
        sampleSentId = $(this).attr("id").split("_")[1];
        $.sendRequest({
            action: "getSampleSentFullDetail",
            data: {
                "sampleSentId": sampleSentId
            },
            optional: {url_type: "json"}
        }, function (msg) {
            //var data = JSON.parse(msg);
            var sampleSentPopUp = $.templates("#sampleSentPopUp");
            if ($('#detailBox').length == 0) {
                $("body").append(sampleSentPopUp.render(msg));
            }
            $('#detailBox').fadeIn(300, function() {
                $("#btnCancel").click(function() {
                    closeOpacity();
                });
                generateSelection("#courier", "clientMaster", msg.courier, {
                    action : "get_allcompany_filter",
                    offOverlay : false,
                    reWriteData: true
                });
                $("#sentStatus").val(msg.sentStatus);
                $("#approvalStatus").val(msg.approvalStatus);
            });

            return false;
        });

    }).on("click", "#btnSaveSampleSent", function() {
        $.sendRequest({
            action : "updateSampleSent",
            data : {
                ssId : sampleSentId,
                courier : $("#courier").val(),
                trackingNo : $("#trackingNo").val(),
                sentDate: $("#sentDate").val(),
                etaDate: $("#etaDate").val(),
                sentStatus: $("#sentStatus").val(),
                approvalStatus: $("#approvalStatus").val()
            },
            optional : {
                url_type : "json"
            }
        }, function(msg) {
            $("#detailBox").remove();
            $.success("Update Succeeded");
            doDataTableFilter(oTable);
        });
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
                    <td colspan="3"><select name="sentStatus" id="sentStatus" style="width: 100%">
                        <option value="0">Pending</option>
                        <option value="1">Sent</option>
                    </select></td>
                </tr>
                <tr>
                    <td></td>
                    <td class="title">Approval Status:</td>
                    <td colspan="3"><select name="approvalStatus" id="approvalStatus" style="width: 100%">
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
</body>
</html>

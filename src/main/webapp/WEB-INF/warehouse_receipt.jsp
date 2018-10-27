<%-- 
    Document   : warehouse_receipt
    Created on : Jul 19, 2013, 5:04:04 PM
    Author     : kiendn
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <jsp:include page="header.jsp">
            <jsp:param name="url" value="16"/>
            <jsp:param name="page" value="10"/>
        </jsp:include>
        <script type="text/javascript" src="js/templates/warehouse_receipt.js"></script>
        <style>
            table#WN_table,
            table#selected_table{
                padding: 5px;
                font-size: 11px;
            }
            table.table_filter ul {height: 200px !important}
            .wn_container {padding: 0; padding-top: 10px}
            .tablescroll_wrapper {height: 200px !important}
        </style>
        <c:set var="ins_type" value="${not empty ins_type ? ins_type : 'IM'}"></c:set>
            <form id="wrc_form">
                <div style="width: 1500px ! important; padding: 15px; background: none repeat scroll 0% 0% white; height: auto; overflow: hidden; min-height: 750px;" class="container_16 border" id="wrapper">
                    <div class="left border" style="width: 250px !important; margin-left: 0px; margin-right: 5px; padding: 10px">
                        <div>
                            <label>WR/WC Ref</label>
                        </div>
                        <div class="pc100">
                            <input type="text" id="filter_ref" value="" style="width: 250px; margin-top: 0px; margin-bottom: 1px;" />
                            <ul class="ref_list" id="wr_list" style="margin-top: 15px; padding-left: 10px; width: 225px !important">
                            </ul>
                        </div>
                        <div style="margin-top: 15px">
                            <input type="button" id="btn_add_new" value="Add" name="perm_54"/>
                            <input type="button" id="btn_delete" value="Del" name="perm_55"/>
                        </div>
                    </div>
                    <div class="right border" style="width: 1200px;height: auto; overflow: hidden; min-height: 650px">
                        <div style="width: 100%; overflow: hidden; height: auto; padding-bottom: 10px">
                            <table class="table_filter" width="200px" style="float: left; margin: 10px 0 0 10px">
                                <thead>
                                    <tr style="font-size: 13px">
                                        <th width="100px">Type</th>
                                        <th width="190px">Reference Number</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <td>
                                            <input type="text" class="text_filter_child">
                                            <ul id="type_filter" class="filter" name="perm_40">
                                                <li id="inst_type_IM" class="${ins_type eq "IM" ? "chosen" : ""}">Import</li>
                                            <li id="inst_type_EX" class="${ins_type eq "EX" ? "chosen" : ""}">Export</li>
                                        </ul>
                                    </td>
                                    <td>
                                        <input type="text" style="width: 190px" class="text_filter_child">
                                        <ul id="instruction_ref_filter" class="filter">
                                        </ul>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                        <div id="wr_info" style="float: right; height: auto; overflow: hidden; min-height: 250px;width: 800px; padding-top: 15px">
                            <div class="pc100">
                                <div class="pc40 left cell">
                                    <span class="right label">Date</span>
                                </div>
                                <div class="pc50 left cell">
                                    <input type="text" style="width: 150px" id="wr_date" name="wr_date" value="" readonly=""/>
                                </div>
                            </div>
                            <div class="pc100">
                                <div class="pc40 left cell">
                                    <span class="right label">Origin</span>
                                </div>
                                <div class="pc50 left cell">
                                    <span class="label">Vietnam</span>
                                </div>
                            </div>
                            <div class="pc100">
                                <div class="pc40 left cell">
                                    <span class="right label">Quality</span>
                                </div>
                                <div class="pc50 left cell">
                                    <span class="label">Robusta</span>
                                </div>
                            </div>
                            <div class="pc100">
                                <div class="pc40 left cell">
                                    <span class="right label">Grade</span>
                                </div>
                                <div class="pc50 left cell">
                                    <span class="label"></span>
                                </div>
                            </div>
                            <div class="pc100">
                                <div class="pc40 left cell">
                                    <span class="right label">Supplier/Buyer</span>
                                </div>
                                <div class="pc50 left cell">
                                    <span class="label"></span>
                                </div>
                            </div>
                            <div class="pc100">
                                <div class="pc40 left cell">
                                    <span class="right label">Remark</span>
                                </div>
                                <div class="pc50 left cell">
                                    <textarea id="remark" rows="4" cols="50">

                                    </textarea>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="right" style="width: 1200px;height: auto; overflow: hidden; min-height: 400px; border-top: 1px solid #B8C2CC">
                        <fieldset id="weight_note_table" class="wn_container" style="clear: both;width: 100%; overflow: hidden; height: auto; min-height: 200px; border-bottom: 1px solid #B8C2CC; padding-bottom: 10px">
                            <legend>WN Table</legend>
                            <table id="WN_table">
                                <thead>
                                    <tr>
                                        <th width="200px">WeightNote Reference</th>
                                        <th width="100px">Status</th>
                                        <th width="200px">Quality Report Reference</th>
                                        <th width="100px">Status</th>
                                        <th width="250px">Grade</th>
                                        <th width="100px">Number Of Bags</th>
                                        <th width="100px">Tons</th>
                                        <th width="100px">Select</th>
                                    </tr>
                                </thead>

                                <tfoot>
                                    <tr>
                                        <th width="200px"></th>
                                        <th width="100px"></th>
                                        <th width="200px"></th>
                                        <th width="100px"></th>
                                        <th width="250px"></th>
                                        <th width="100px"></th>
                                        <th width="100px"></th>
                                        <th width="100px"></th>
                                    </tr>
                                </tfoot>
                            </table>
                        </fieldset>
                        <fieldset id="selected_weight_note" class="wn_container" style="clear: both;width: 100%; overflow: hidden; height: auto; min-height: 200px; border-bottom: 1px solid #B8C2CC; padding-bottom: 10px">
                            <legend>Selected WN</legend>
                            <table id="selected_table">
                                <thead>
                                    <tr>
                                        <th width="200px">WeightNote Reference</th>
                                        <th width="100px">Status</th>
                                        <th width="200px">Quality Report Reference</th>
                                        <th width="100px">Status</th>
                                        <th width="250px">Grade</th>
                                        <th width="100px">Number Of Bags</th>
                                        <th width="100px">Tons</th>
                                        <th width="100px">Select</th>
                                    </tr>
                                </thead>
                                <tfoot>
                                    <tr>
                                        <th width="200px"></th>
                                        <th width="100px"></th>
                                        <th width="200px"></th>
                                        <th width="100px"></th>
                                        <th width="250px"></th>
                                        <th width="100px"></th>
                                        <th width="100px"></th>
                                        <th width="100px"></th>
                                    </tr>
                                </tfoot>
                            </table>
                        </fieldset>
                        <table>
                            <tr>
                                <td>Weight Controller</td>
                                <td><select id="weight_controller"></select></td>
                            </tr>
                            <tr>
                                <td>Quality Controller</td>
                                <td><select id="quality_controller"></select></td>
                            </tr>
                            <tr>
                                <td>Completed</td>
                                <td><input type="checkbox" id="wr_status" value="0"/></td>
                            </tr>
                        </table>

                    </div>
                </div>
                <div class="right" style="width: 1200px; margin-top: 20px">
                    <input class="left" type="button" id="btn_save" value="Save" name="perm_56"/>
                    <!-- <input class="right" type="button" id="btn_list" value="List View"/> -->
                    <input class="right" type="button" id="btn_print" value="Print"/>
                </div>                     
            </div>
        </form>
        <jsp:include page="footer.jsp"></jsp:include>
        <script>
            $(document).ready(function() {
                sendJsonRequest("instruction_ref_filter", "get_di_si_ref", {type: $("#type_filter li.chosen").attr("id").split("_")[2]}, 1);
                sendJsonRequest("weight_controller", "selection_company", {}, 0);
                sendJsonRequest("quality_controller", "selection_company", {}, 0);
                $("#WN_table").tableScroll({height: 150});
                $("#selected_table").tableScroll({height: 150});
                //load wr_ref_list
                sendJsonRequest("wr_list", "get_wr_list", {type: $("#type_filter li.chosen").attr("id").split("_")[2]}, 1);
            });
            $(document).on("click", "#type_filter li", function() {
                //reset instruction_ref_filter to beginning
                $("#instruction_ref_filter li").each(function() {
                    if ($(this).attr("id") != "ins_-1") {
                        $(this).remove();
                    }
                });
                sendJsonRequest("instruction_ref_filter", "get_di_si_ref", {type: $("#type_filter li.chosen").attr("id").split("_")[2]}, 1);
            }).on("click", "#instruction_ref_filter li", function() {
                sendJsonRequest("wr_info", "load_ins_info", {
                    inst_id: $(this).attr("id").split("_")[1],
                    type: $("#type_filter li.chosen").attr("id").split("_")[2]
                }, 0, [
                    {name: "loadAvailableWnTable", params: ["weight_note_table", "WN_table"]}
                ]);
                var arr = new Array();
                $.data(document.body, "selected_wn", arr);
                //load wr_info
                //load wn table
//                sendJsonRequest("wr_info","load_wr_info",
//                {
//                    "inst_id":$(this).attr("id").split("_")[1],
//                    "type":$("#type_filter li.chosen").attr("id").split("_")[2],
//                    "wr_id":($("#wr_list li.chosen") != undefined) ? $("#wr_list li.chosen").attr("id").split("_")[1] : -1
//                },1);
            }).on("click", "#wr_list li", function() {
                //load selected table
                //if status is completed, hide weight note div
                //reset selected_wn
                //reset warehouse_receipt
                //disabled list of inst and type
                $("#wr_-1").remove();
                sendJsonRequest("", "get_wr", {id: $("#wr_list li.chosen").attr("id").split("_")[1]}, 5, [
                    {name: "getDiByWrId", params: []},
                    {name: "getWnSelectedListFromData", params: []},
                    {name: "reloadWRInfo", params: []}
                ], true, ["warehouse_receipt"]);

            }).on("click", "#btn_add_new", function() {
                //reload di list
                $.sendRequest({
                    target: "instruction_ref_filter",
                    action: "get_di_si_ref",
                    data: {type: $("#type_filter li.chosen").attr("id").split("_")[2]},
                    status: "replace_html"
                });
                //change the date
                getCurrentDateText("#wr_date", 2);
                //prepend new item to wr_list
                if ($("#wr_list li.chosen").length > 0 && $("#wr_-1").length == 0) {
                    $("#wr_list li.chosen").toggleClass("chosen");
                }
                if ($("#wr_-1").length == 0) {
                    $("#wr_list").prepend("<li class='chosen' id='wr_-1'>New Item</li>");
                }

                $("#wr_date").val("");
                //create json for loading selected wn
                reset_wr_data();
                $.data(document.body, "warehouse_receipt").id = -1;
                reloadWRInfo();
                $("#selected_table tbody").html("");
            }).on("click", ".avai", function() {
                //check wr_list is chosen or not
                if ($("#wr_list li.chosen").length > 0) {
                    var current_selected_inst = $("#instruction_ref_filter li.chosen").attr("id").split("_")[1];
                    if (current_selected_inst == $.data(document.body, "warehouse_receipt").instId || $.data(document.body, "warehouse_receipt").id == -1) {
                        //load current element into the body
                        $.data(document.body, "selected_wn").push({
                            btn_id: $(this).attr("id"),
                            wn_id: $(this).attr("id").split("_")[1],
                            wn_ref: $(this).parent().parent().children(".wn_ref").text(),
                            wn_status: $(this).parent().parent().children().children(".wn_status").text(),
                            wn_style: $(this).parent().parent().children().children(".wn_status").attr("style"),
                            qr_ref: $(this).parent().parent().children(".qr_ref").text(),
                            qr_status: $(this).parent().parent().children().children(".qr_status").text(),
                            qr_style: $(this).parent().parent().children().children(".qr_status").attr("style"),
                            wn_grade: $(this).parent().parent().children(".wn_grade").text(),
                            wn_bags: $(this).parent().parent().children(".wn_bags").text(),
                            wn_net: $(this).parent().parent().children(".wn_net").text(),
                            btnClass: "btnSelected",
                            value: "Delete"
                        });
                        //reload the selected_wn
                        //$("#selected_table tbody").loadTemplate("templates/selected_wn.html", $("body").data("selected_wn"));
                        var html = $.templates.wn_row.render($.data(document.body, "selected_wn"));
                        if ($("#selected_table tbody").length == 0)
                            $("#selected_table").append("<tbody></tbody>");
                        $("#selected_table tbody").html(html);
                        $("input[type=button]").button();
                        //delete current row
                        $(this).parent().parent().remove();
                    } else {
                        callMessageDialog({type: "error", message: "Please choose correct Instruction"});
                    }
                } else {
                    //alert dialog implements later
                }
            }).on("click", ".btnSelected", function() {
                var id = $(this).attr("id").split("_")[1];
                //prepend to WN_table tbody
                var data = {
                    btn_id: $(this).attr("id"),
                    wn_id: $(this).attr("id").split("_")[1],
                    wn_ref: $(this).parent().parent().children(".wn_ref").text(),
                    wn_status: $(this).parent().parent().children().children(".wn_status").text(),
                    wn_style: $(this).parent().parent().children().children(".wn_status").attr("style"),
                    qr_ref: $(this).parent().parent().children(".qr_ref").text(),
                    qr_status: $(this).parent().parent().children().children(".qr_status").text(),
                    qr_style: $(this).parent().parent().children().children(".qr_status").attr("style"),
                    wn_grade: $(this).parent().parent().children(".wn_grade").text(),
                    wn_bags: $(this).parent().parent().children(".wn_bags").text(),
                    wn_net: $(this).parent().parent().children(".wn_net").text(),
                    btnClass: "avai",
                    value: "Select"
                };
                var arr = $.data(document.body, "selected_wn");
                for (var i = 0; i < arr.length; i++) {
                    if (arr[i].wn_id == id) {
                        $.data(document.body, "selected_wn").splice(i, 1);
                    }
                }
                $("#WN_table tbody").prepend($.templates.wn_row.render(data));
                $("#selected_table tbody").html($.templates.wn_row.render($.data(document.body, "selected_wn")));
                $("input[type=button]").button();
            }).on("click", "#btn_save", function() {
                var arr = new Array();
                for (var i = 0; i < $.data(document.body, "selected_wn").length; i++) {
                    arr.push($.data(document.body, "selected_wn")[i].wn_id);
                }
                submit_page($("#wr_list li.chosen").attr("id").split("_")[1],
                        $("#type_filter li.chosen").attr("id").split("_")[2],
                        $("#instruction_ref_filter li.chosen").attr("id").split("_")[1],
                        $("#weight_controller").val(),
                        $("#quality_controller").val(),
                        $("#wr_status").val(),
                        $("#remark").text(),
                        arr);

            }).on("click", "#wr_status", function() {
                if ($(this).val() == 1) {
                    $(this).val(0);
                    $("#weight_note_table").show();
                } else {
                    $(this).val(1);
                    $("#weight_note_table").hide();
                }
            }).on("click", "#btn_delete", function() {
                //check chosen
                if ($("#wr_list li.chosen").length > 0) {
                    var arr = new Array();
                    for (var i = 0; i < $.data(document.body, "selected_wn").length; i++) {
                        arr.push($.data(document.body, "selected_wn")[i].wn_id);
                    }
                    $(document.body, "warehouse_receipt").status = 2;
                    sendJsonRequest("", "deleteWr", {data: JSON.stringify($.data(document.body, "warehouse_receipt")), wn_list: $.data(document.body, "warehouse_receipt").wn.toString()}, -1, [
                        {name: "removeWr", params: []}
                    ]);

                } else {
                    $.warning("Please choose a WR/WC")
                }
            }).on("click", "#btn_print", function() {
                var id = $("#wr_list .chosen").attr("id").split("_")[1];
                if (id > -1) {
                    var file = getAbsolutePath() + "/js/templates/WR/report.html";
                    $.when($.get(file))
                            .done(function(tmplData) {
                        if ($("#printEl").length == 0) {
                            $("body").append("<div id='printEl'></div>");
                        }
                        $.sendRequest({
                            target: "#printEl",
                            action: "print_wr",
                            status: "apply_template",
                            data: {id: id},
                            optional: {opt: "replace", tmpl: tmplData + ""},
                            functions: [
                                {name: "printEl", params: []}
                            ]
                        });
                    });
                }
            });

            function printEl() {
                $("#printEl").printThis({importCSS: false, pageTitle: "SWCBD", removeInline: false});
                $("#printEl").remove();
                $("#printStatus").remove();
            }

            function loadAvailableWnTable(parent, id) {
                if ($.data(document.body, "warehouse_receipt") != undefined && $.data(document.body, "warehouse_receipt").status == 0) {
                    $("#weight_note_table").show();
                    var data = {
                        inst_id: $("#instruction_ref_filter li.chosen").attr("id").split("_")[1],
                        type: $("#type_filter li.chosen").attr("id").split("_")[2]
                    };
                    //check scroll el exist or not
                    if ($("#" + parent + " div.tablescroll").length > 0) {
                        //append to id body
                        sendJsonRequest(id, "load_avail_wn_scroll", data, 0);
                    } else {
                        if ($("#" + id + " tbody").length > 0)
                            $("#" + id + " tbody").remove();
                        sendJsonRequest(id, "load_avail_wn", data, 1, [{name: "tableSroll", params: ["#WN_table", {height: 150}]}]);
                    }
                } else {
                    $("#weight_note_table").hide();
                }
            }
            function getDiByWrId() {
                $("#instruction_ref_filter li.chosen").toggleClass("chosen");
                $("#ins_" + $.data(document.body, "warehouse_receipt").instId).addClass("chosen");
                loadAvailableWnTable("weight_note_table", "WN_table");
//                sendJsonRequest("instruction_ref_filter", "get_inst_by_wr_id", {id: $.data(document.body, "warehouse_receipt").instId}, 0, [
//                    {name: "chooseFirstElement", params: ["instruction_ref_filter"]},
//                    {name: "loadAvailableWnTable", params: ["weight_note_table", "WN_table"]},
//                ]);
            }
        </script>
    </body>
</html>

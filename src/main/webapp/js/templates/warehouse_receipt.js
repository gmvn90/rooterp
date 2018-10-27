$.templates({
    wn_row: '<tr>' +
            '<td width="200px" class="wn_ref" style="width: 196px;">{{:wn_ref}}</td>' +
            '<td width="100px" style="width: 104px;"><span class="wn_status" style="{{:wn_style}}">{{:wn_status}}</span></td>' +
            '<td width="200px" class="qr_ref" style="width: 196px;">{{:qr_ref}}</td>' +
            '<td width="100px" style="width: 104px;"><span class="qr_status" style="{{:qr_style}}">{{:qr_status}}</span></td>' +
            '<td width="250px" class="wn_grade" style="width: 239px;">{{:wn_grade}}</td>' +
            '<td width="100px" class="wn_bags" style="width: 104px;">{{:wn_bags}}</td>' +
            '<td width="100px" class="wn_net" style="width: 102px;">{{:wn_net}}</td>' +
            '<td width="100px" style="width: 103px;"><input type="button" id="{{:btn_id}}" class="{{:btnClass}}" value="{{:value}}"/></td>' +
            '</tr>',
    wr_info: "<div class ='pc100'> " +
            "    <div class='pc40 left cell'>" +
            "        <span class='right label'>Date</span>" +
            "    </div>" +
            "    <div class='pc50 left cell'>" +
            "        <input type='text' style='width: 150px' class='date_picker_text' id='wr_date' name='wr_date' value='{{:date}}' readonly=''/>" +
            "    </div>" +
            "</div>" +
            "<div class='pc100'>" +
            "    <div class='pc40 left cell'>" +
            "        <span class='right label'>Origin</span>" +
            "    </div>" +
            "    <div class='pc50 left cell'>" +
            "        <span class='label'>Vietnam</span>" +
            "    </div>" +
            "</div>" +
            "<div class='pc100'>" +
            "    <div class='pc40 left cell'>" +
            "        <span class='right label'>Quality</span>" +
            "    </div>" +
            "    <div class='pc50 left cell'>" +
            "        <span class='label'>Robusta</span>" +
            "    </div>" +
            "</div>" +
            "<div class='pc100'>" +
            "    <div class='pc40 left cell'>" +
            "        <span class='right label'>Grade</span>" +
            "    </div>" +
            "    <div class='pc50 left cell'>" +
            "        <span class='label'>{{:grade}}</span>" +
            "    </div>" +
            "</div>" +
            "<div class='pc100'>" +
            "    <div class='pc40 left cell'>" +
            "        <span class='right label'>Supplier/Buyer</span>" +
            "    </div>" +
            "    <div class='pc50 left cell'>" +
            "        <span class='label'>{{:supplier}}</span>" +
            "    </div>" +
            "</div>" +
            "<div class='pc100'>" +
            "    <div class='pc40 left cell'>" +
            "        <span class='right label'>Remark</span>" +
            "    </div>" +
            "    <div class='pc50 left cell'>" +
            "        <textarea rows='4' cols='50'>" +
            "        {{:remark}}" +
            "        </textarea>" +
            "    </div>" +
            "</div>"
});

var default_wr = {
    id: "0",
    refNumber: "",
    instType: "",
    instId: "0",
    qrId: "0",
    weightControllerId: "0",
    qualityControllerId: "0",
    date: "",
    status: "0",
    remark: "",
    lastUpdated: "",
    userId: "0",
    supplier: "",
    grade: "",
    wn: []
}

$.data(document.body, "warehouse_receipt", default_wr);

function reset_wr_data() {
    $.extend($.data(document.body, "warehouse_receipt"),default_wr);
    $.data(document.body, "selected_wn", {});
}

function submit_page(id, instType, instId, weightControllerId, qualityControllerId, status, remark, wn) {
    var warehouse_receipt = $.data(document.body, "warehouse_receipt");
    var new_wr = {
        id: id,
        refNumber: (warehouse_receipt != undefined) ? warehouse_receipt.refNumber : "",
        instType: instType,
        instId: instId,
        qrId: (warehouse_receipt != undefined) ? warehouse_receipt.qrId : 0,
        weightControllerId: weightControllerId,
        qualityControllerId: qualityControllerId,
        date: (warehouse_receipt != undefined) ? warehouse_receipt.date : $("#wr_date").val(),
        status: status,
        remark: remark,
        lastUpdated: (warehouse_receipt != undefined) ? warehouse_receipt.lastUpdated : "",
        userId: (warehouse_receipt != undefined) ? warehouse_receipt.userId : 0,
        supplier: (warehouse_receipt != undefined) ? warehouse_receipt.supplier : "",
        grade: (warehouse_receipt != undefined) ? warehouse_receipt.grade : "",
        wn: wn
    };
    if (id == -1) {
        //new wr
        //check instId, weightControllerId,qualityControllerId
        if (instId > 0 && weightControllerId > 0 && qualityControllerId > 0) {
            //sendJsonRequest("", "update_warehouse_receipt", {data: JSON.stringify(new_wr), wn_list: new_wr.wn.toString()}, 5, [{name: "add_new_item", params: []}], true, ["warehouse_receipt"]);
            $.sendRequest({
                action: "update_warehouse_receipt",
                data: {data: JSON.stringify(new_wr), wn_list: new_wr.wn.toString()},
                status: "append_data",
                functions: [
                    {name: "add_new_item", params:[]},
                    {name: "reloadWRInfo", params:[]}
                ],
                optional: ["warehouse_receipt"]
            });
        }

    } else if (id > -1) {
        //update
        if (JSON.stringify(warehouse_receipt) != JSON.stringify(new_wr)) {
//            sendJsonRequest("", "update_warehouse_receipt", {data: JSON.stringify(new_wr), wn_list: new_wr.wn.toString()}, 5, [], true, ["warehouse_receipt"]);
            $.sendRequest({
                action: "update_warehouse_receipt",
                data: {data: JSON.stringify(new_wr), wn_list: new_wr.wn.toString()},
                status: "append_data",
                functions: [
                    {name: "reloadWRInfo", params:[]},
                    {name: "showSuccessMessage", params: ["Update Succeeded"]}
                ],
                optional: ["warehouse_receipt"]
            });
        }//else do nothing
    }
    //else do nothing
}

function showSuccessMessage(msg){
    $.success(msg);
}
//warehouse_receipt obj must not be empty
function add_new_item() {
//remove new item
    $("#wr_list li.chosen").remove();
    //prepend to the list new item from warehouse_receipt obj
    $("#wr_list").prepend("<li class='chosen' id='wr_" + $("body").data("warehouse_receipt").id + "'>" + $("body").data("warehouse_receipt").refNumber + "</li>");
}

function getInstIdFromData() {
    return "ins_" + $("body").data("warehouse_receipt").instId;
}

function renderSelectedWn(el) {
    //console.log($.templates.wn_row);
    $("body").data("selected_wn", $("body").data("response_wn").wn_list);
    if ($(el + " tbody").length == 0)
        $(el).append("<tbody></tbody>");
    $(el).html($.templates.wn_row.render($("body").data("selected_wn")));
    $("input[type=button]").button();
}

function getWnSelectedListFromData() {
    //console.log("wn_list : " + $("body").data("warehouse_receipt").wn.toString());
    sendJsonRequest("", "getWnSelectedListFromData", {wn_list: $("body").data("warehouse_receipt").wn.toString()}, 5, [
        {name: "renderSelectedWn", params: ["#selected_table"]}
    ], true, ["response_wn"]);
}

function reloadWRInfo() {
    $("#wr_info").html($.templates.wr_info.render($("body").data("warehouse_receipt")));
    $("#weight_controller").val($("body").data("warehouse_receipt").weightControllerId);
    $("#quality_controller").val($("body").data("warehouse_receipt").qualityControllerId);
    $("#wr_status").val($("body").data("warehouse_receipt").status);
    if ($("body").data("warehouse_receipt").status == 1) {
        $("#wr_status").prop("checked",true);
        $("#weight_note_table").hide();
    } else {
        $("#wr_status").prop("checked", false);
        $("#weight_note_table").show();
    }
}

function removeUnchosenInstList() {
    $("#instruction_ref_filter li").each(function() {
        //console.log("chosen: " + $(this).hasClass("chosen"));
        if (!$(this).hasClass("chosen")) {
            $(this).remove();
        }
    });
}

function removeWr() {
    $("#wr_list li.chosen").remove();
    reset_wr_data();
    reloadWRInfo();
    $("#selected_table tbody").html("");
    $("#WN_table tbody").html("");
}
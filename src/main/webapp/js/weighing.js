$.templates({
    wnr: "<h1>{{:wn_ref}}</h1>" +
            "        <h2>" +
            "            WEIGHT NOTE RECEIPT" +
            "        </h2>" +
            "        <div class='content'>" +
            "            <h1>" +
            "                {{:wnr}}" +
            "            </h1>" +
            "            <table>" +
            "                <tr>" +
            "                    <td class='title'>Grade</td>" +
            "                    <td>{{:grade}}</td>" +
            "                </tr>" +
            "                <tr>" +
            "                    <td class='title'>Gross Weight (Mt)</td>" +
            "                    <td>{{:gross}}</td>" +
            "                </tr>" +
            "                <tr>" +
            "                    <td class='title'>Tare Weight (Mt)</td>" +
            "                    <td>{{:tare}}</td>" +
            "                </tr>" +
            "                <tr>" +
            "                    <td class='title'>Net Weight (Mt)</td>" +
            "                    <td>{{:net}}</td>" +
            "                </tr>" +
            "                <tr>" +
            "                    <td class='title'>Date</td>" +
            "                    <td>{{:date}}</td>" +
            "                </tr>" +
            "            </table>" +
            "            <div class='print'>" +
            "                <div class='barcode'>" +
            "                </div>" +
            "                <div class='print_date' style='clear:both; float: right; font-size: 9px'>" +
            "                    {{:printDate}}" +
            "                </div>" +
            "            </div>" +
            "        </div>"
});
var delayTimer;
var flag_area_change;
$('body').keypress(function (event) {
    if (event.which === 13) {
        event.preventDefault();
        save_wnr();
    }
});
$(document).on("click", "#type_filter li", function () {
    $("#type_filter li.chosen").toggleClass("chosen");
    $(this).addClass("chosen");
    var wn_type = $(this).attr("id").split("_")[1];
//    console.log("wn_type=" + wn_type);
    loadByWnType(wn_type);
    if (wn_type != 5) {
        loadInsRefList("weighing_form", "instruction_ref_filter", "get_ins_ref", wn_type);
    }
    resetRow();
    reloadWeightNoteRefList(true);

}).on("click", "#btn_add_new", function () {
    if (!$(this).hasClass("disabled")) {
        //check inst has been checked or not
        if ($("#instruction_ref_filter li.chosen").attr("id") != "ins_-1") {
            //new wn that is based on a specific type and ins
            //get type
            var type = $("#type_filter li.chosen").attr("id").split("_")[1];
            //get ins_id
            var ins_id = $("#instruction_ref_filter li.chosen").attr("id").split("_")[1];
            $.sendRequest({
                action: "new_wn",
                data: {ins_type: type, ins_id: ins_id}
            }, function (msg) {
                try {
                    $("#wn_id").val(msg);
                    getWn();
                    resetRow();
                    reloadWeightNoteRefList(function () {
                        chooseFirstElement("process_list");
                    });
                    return true;
                } catch (e) {
                    $.error(e);
                }
            });
        } else {
            $.warning("Please choose instruction");
            //callMessageDialog({type: "error", message: "Please choose one instruction"});
        }
    }
}).on("click", "#btnNewIpWNR", function () {
    if (!$(this).hasClass("disabled")) {
        var mode = $("input[name=truck_mode]:checked").val() != undefined ? $("input[name=truck_mode]:checked").val() : "0";
        console.log("MODE " + mode);
        switch (mode) {
            case "0":                 /* normal mode. */
                $.sendRequest({//check wnr exist
                    action: "check_wnr_for_ip",
                    data: {ref_number: $("#allocated_wnr_id").val(), inst_id: $.data(document.body, "wn_info")[0].inst_id, wnid: $.data(document.body, "wn_info")[0].id},
                    status: "append_data",
                    optional: ["result"],
                    functions: [
                        {name: "addNewIpWnr", params: []}
                    ]
                });
                //sendJsonRequest("weighing_form", "check_wnr_for_ip", {"ref_number": $("#allocated_wnr_id").val(), "inst_id": $("#instruction_ref_filter li.chosen").attr("id").split("_")[1]}, 5, [{"name": "addNewIpWnr", "params": []}], false, ["result"]);
                break;
            case "1": /* gross mode */
                if ($("#weighing_section").length == 0) {
                    console.log("gross");
                    var msg = {};
                    var data = {};
                    var rows = [];
                    data["packing"] = $("#wnr_packing").val();
                    data["wn_id"] = $(".ref_list li.chosen").attr("id").split("_")[1];
                    if ($("#allocated_wnr_id").val() == "") {
                        rows.push({a_row: "Please Enter Truck Number"});
                    } else {
                        data["truck_no"] = $("#allocated_wnr_id").val();
                    }
                    if ($("#wnr_noOfBag").val() == "") {
                        rows.push({a_row: "Please Enter Number Of Bags"});
                    } else {
                        data["noOfBags"] = $("#wnr_noOfBag").val();
                    }
                    if ($("#wnr_gross").val() == "") {
                        rows.push({a_row: "Please Enter Gross Weight"});
                    } else {
                        data["gross"] = $("#wnr_gross").val();
                    }
                    if (rows.length > 0) {
                        msg["title"] = "Cannot Create New Weight Note Receipt";
                        msg["msg"] = rows;
                        $.fn.callDialog(msg, {type: "error"});
                    } else {
                        var action = "add_gross_truck";
                        var status = "append_html";
                        if ($.trim($("#wnr_content").html()).length == 0) {
                            action = "add_first_gross_truck";
                            status = "replace_html";
                            //sendJsonRequest("wnr_content", "add_first_wnr_ip", data, 0, [{name: "do_printing", params: []}]);
                        }
                        $.sendRequest({
                            target: "#wnr_content",
                            action: action,
                            data: data,
                            status: status,
                            functions: [
                                {name: "do_printing", params: []}
                            ]
                        });
                    }
                } else {
                    $.warning("The list is full! Please create or choose another weight note");
                }
                break;
            case "2": /* tare mode */
                if ($("#weighing_section").length == 0) {

                } else {
                    $.warning("The list is full! Please create or choose another weight note");
                }
                break;
        }
        //if gross mode
    }
}).on("click", ".filter li", function () {
    $(this).parent("ul").children("li.chosen").toggleClass("chosen");
    $(this).addClass("chosen");
    //call filter function
    resetRow();
    reloadWeightNoteRefList(true);
    if ($(this).parent("ul").attr("id") == "instruction_ref_filter") {
        $("#wn_content").html("");
    }
}).on("click", "#btnSaveWN", function () {
    var permitvalue = $(this).attr("permit");
    var wn_info = $.data(document.body, "wn_info")[0];
    if (wn_info.status == 0 || permitvalue == "1") {
        var data = {
            wn_id: $(".ref_list li.chosen").attr("id").split("_")[1],
            truck_no: $("#truck_no").val(),
            grade: $("#grade").val(),
            packing: $("#packing").val(),
            driver: $("#driver_name").val(),
            area: $("#area").val(),
            cell_id_name: $("#cell_id_name").val(),
            wn_status: $("#wn_status").val(),
            container_no: $("#container_no").length > 0 ? $("#container_no").val() : "",
            ico_no: $("#ico_no").length > 0 ? $("#ico_no").val() : "",
            seal_no: $("#seal_no").length > 0 ? $("#seal_no").val() : ""
        };
        save_wn(data);
    } else {
        $.warning("This weight note is untouchable, please contact administrator!");
    }
}).on("click", "#saveArea", function() {
    var wn_info = $.data(document.body, "wn_info")[0];
    $.sendRequest({
        action: "saveArea",
        data: {
            wn_id: $(".ref_list li.chosen").attr("id").split("_")[1],
            cell_id_name: $("#cell_id_name").val(),
        }
    }, function (msg) {
        if (msg == "1") {
            $.success("Update success!");
        } else {
            $.error("Update failed!");
        }
    });
}).on("click", "#btnNewWNR", function () {

    var path = "/upload_snap.htm";
    var wn_info = $.data(document.body, "wn_info")[0];
    checkWnEditable(wn_info.id);
    checkGradeExist(wn_info.id);
    if ($.data(document.body, "wn_editable") == "true") {
        if ($.data(document.body, "gradeEmpty") == "true") {
            var validate = $.validation([
                {element: "#wnr_noOfBag", msg: "Please Enter Number Of Bags"},
                {element: "#wnr_gross", msg: "Please Enter The Weight"},
                {element: "#wnr_id", msg: "Please Enter Weight note receipt reference"},
                {element: "#allocated_wnr_id", msg: "Please Enter Weight note receipt reference"},
                {
                    element: "#wnr_processing",
                    msg: "Processing Instruction value is invalid",
                    opts: {
                        type: "!=",
                        value: -1
                    }
                },
                {
                    element: "#grade",
                    msg: "Grade is empty",
                    opts: {
                        type: "!=",
                        value: -1
                    }
                }
            ]);
            if (validate) {
                //var checked_type = $("input[name=truck_mode]:checked").val();
                //if (checked_type == undefined || checked_type == 0) { /* if None */
                    weightNone();
                //}
                //}
                //if (checked_type == 1) { /* if Gross */
                //    if ($("#snap").length > 0) {
                //        $("#snap").val(window.external.snapping(wn_info.ref_number + "_gross", path));
                //    }
                //    weightGross();
                //}
                //if (checked_type == 2) { /* if Tare */
                //    if ($("#snap").length > 0) {
                //        $("#snap").val(window.external.snapping(wn_info.ref_number + "_tare", path));
                //        alert("after snap: " + $("#snap").val());
                //    }
                //    weightTare();
                //}
            }
        } else {
            $.warning("Please Update Grade Before Adding New WNR");
        }
    } else {
        $.warning("This weight note is untouchable, please contact administrator!");
    }

}).on("keyup", "#filter_ref", function (e) {
    clearTimeout(delayTimer);
    delayTimer = setTimeout(function() {
        resetRow();
        reloadWeightNoteRefList();
    }, 2000);

}).on("click", ".wnr_print", function () {
    var id = $(this).attr("id").split("_")[2];
    print_wnr(id);
});

function weightNone() {
    var wn = $.data(document.body, "wn_info")[0];
    var wn_id = wn.id;
    var type = wn.type;
    var inst_id = wn.inst_id;
//    if (type == 'EX') {
//        $.sendRequest({
//            action: "check_container_seal",
//            data: {wn_id: wn_id},
//            status: "append_data",
//            optional: ["add_wnr_response"],
//            functions: [
//                {name: "save_wnr", params: []}
//            ]
//        });
    if (type == 'IM' || type == 'XP') {
        //if ($("#area").val() == "" || $("#cell_id_name").val() == "") {
        //    $.error("Please choose area and save Weight Note before adding new Weight Note Receipt!")
        //} else {
        //    var cur_area = ($("#cell_id_name").val() !== "") ? $("#cell_id_name").val() : 0;
        //    $.sendRequest({
        //        action: "check_wn_area",
        //        data: {wn_id: wn_id, cur_area: cur_area},
        //        status: "append_data",
        //        optional: ["add_wnr_response"],
        //        functions: [
        //            {name: "save_wnr", params: []}
        //        ]
        //    });
        save_wnr();
        //}
    } else if (type == 'IP' || type == 'EX') {
        $.sendRequest({//check wnr exist
            action: "check_wnr_for_ip",
            data: {ref_number: $("#allocated_wnr_id").val(), inst_id: inst_id, wnid: wn_id},
            status: "append_data",
            optional: ["result"],
            functions: [
                {name: "addNewIpWnr", params: []}
            ]
        });
    }
}

function weightGross() {
    var type = $.data(document.body, "wn_info")[0].type;
    var wn_id = $.data(document.body, "wn_info")[0].id;
    var data = {};
    if (type == 'IM') {
        if ($("#weighing_section").html().trim() == "") {
            data = {
                type: 'IM',
                packing: $("#wnr_packing").val(),
                wn_id: wn_id,
                truck_no: $("#truck_no").val(),
                noOfBags: $("#wnr_noOfBag").val(),
                gross: $("#wnr_gross").val(),
                area: $("#cell_id_name").val()
            };
            if ($("#snap").length > 0) {
                data["snap"] = $("#snap").val();
            }
            $.sendRequest({
                target: "#wnr_content",
                action: "bridge_gross",
                data: data,
                status: "handle_response",
                func: function () {
                    if (msg == "-1") {
                        $.error("Update Failed");
                    } else {
                        var data = JSON.parse(msg)
                        var file = getAbsolutePath() + "/js/templates/weighing/wnr_row.html";
                        $.data(document.body, "wn_info")[0].wnrs.push(data);
                        $.when($.get(file))
                                .done(function (tmplData) {
                                    $("#weighing_section").append($.templates(tmplData).render(data));
                                    $("input[type=button]").button();
                                    getPackingOption({
                                        target: "#wnr_packing_" + data.id,
                                        value: data.packing
                                    });
                                    recountWnrField();
                                    do_printing_json(data);
                                });
                    }
                }
            });
        } else {
            $.warning("The list is full! Please create or choose another weight note");
        }
    } else if (type == 'EX') {
        if ($("#weighing_section").html().trim() != "") {
            //check wnr
            var wnr = $("#allocated_wnr_id").val();
            $.sendRequest({
                action: "check_wnr",
                data: {wn_id: wn_id, wnr: wnr},
                status: "append_data",
                optional: ["response_data"],
                functions: [
                    {name: "updateExportGross", params: [wn_id]}
                ]
//                func: function() {
//                    var data = JSON.parse(msg)
//                    if (data[0] > 0) {                        /* wnr does exist */
//                        $.sendRequest({
//                            action: "bridge_gross",
//                            data: {
//                                type: 'EX',
//                                gross: $("#wnr_gross").val(),
//                                wn_id: wn_id,
//                                wnr_id: data[0]
//                            },
//                            status: "append_data",
//                            optional: ["response_data"],
//                            functions: [
//                                {name: "afterUpdateExportBridge", params: []}
//                            ]
////                            func: function() {
////                                if (msg == 1) {
////                                    $.success("Update Completed");
////                                } else {
////                                    $.error("Update Failed");
////                                }
////                            }
//                        });
//                    } else {
//                        $.error("Weight Note Receipt does not exist");
//                    }
//                }
            });
        } else {
            $.warning("Please update truck weight before get gross weight");
        }
    }
}

function save_wnr() {
    //if ($.data(document.body, "add_wnr_response") == "1") {
        var data = {
            wn_id: $(".ref_list li.chosen").attr("id").split("_")[1],
            packing: $("#wnr_packing").val(),
            gross: $("#wnr_gross").val()
        };
        var len = $.trim($("#wnr_content").html()).length;
        var action = (len == 0) ? "add_first_wnr" : "add_wnr";
        //if wnr_content is empty means that there aren't any wnr was saved before -> add html to wnr_content
        //else append an wnr_row to tbody
        //if ($.trim($("#wnr_content").html()).length == 0) {

        $.sendRequest({
            action: action,
            data: data,
            status: "handle_response",
            func: function () {
                try {
                    console.log(msg);
                    var data = JSON.parse(msg);
                    //if (data.msg == "pallet") {
                    //    $.error("Pallet does not exist in the database!");
                    //}
                } catch (e) {
                    console.log(e);
                    if (msg != "") {
                        if ($.trim($("#weighing_section").html()).length == 0) {
                            $("#weighing_section").html(msg);
                        } else {
                            $("#weighing_section").append(msg);
                        }
                        recountWnrField();
                        do_printing();
                    }
                }
            }
        });
//            sendJsonRequest("wnr_content", "add_first_wnr", data, 0, [{name: "do_printing", params: []},{"name": "recountWnrField", "params": []}]);
//        } else {
//            sendJsonRequest("weighing_section", "add_wnr", data, 1, [{"name": "recountWnrField", "params": []}, {name: "do_printing", params: []}]);
//        }
//    } else if ($.data(document.body, "add_wnr_response") == "2") {
//        $.error("Container No and Seal No are not a vailable!");
//    } else if ($.data(document.body, "add_wnr_response") == "3") {
//        $.error("Please choose a correct area!");
//    } else if ($.data(document.body, "add_wnr_response") == "4") {
//        $.error("Please save Weight Note area before adding new Weight Note Receipt!")
//    }
}

function hide_el() {
    $("#footer").addClass("noPrint");
    $("#header_form").addClass("noPrint");
    $("#weighing_form").addClass("noPrint");
    $("body").addClass("soft");
}

function release_el() {
    $("#footer").toggleClass("noPrint");
    $("#header_form").toggleClass("noPrint");
    $("#weighing_form").toggleClass("noPrint");
    $("body").toggleClass("soft");
}

function do_printing() {
    //alert("start do printing");
    var chosen_type = $("#type_filter li.chosen").attr("id").split("_")[1];
    if (chosen_type != '2' && chosen_type != '4') {
        var wnr_json = {
            wn_ref: $(".table_detail_content:first tbody tr:first-child td:nth-child(2)").text(),
            wnr: $("#weighing_section tr:last-child td:nth-child(2) input").val(),
            //quality: "Robusta",
            grade: $("#grade option:selected").text(),
            gross: $("#weighing_section tr:last-child td:nth-child(7) input").val(),
            tare: $("#weighing_section tr:last-child td:nth-child(8) input").val(),
            net: $("#weighing_section tr:last-child td:nth-child(9) input").val(),
            date: $("#weighing_section tr:last-child td:nth-child(3) input").val(),
            //quantity: $("#weighing_section tr:last-child td:nth-child(5) input").val(),
            printDate: $.format.date(new Date, "dd-MMM-yy hh:mm:ss a")
        };
        if ($("#printEl").length == 0) {
            $("body").append("<div id='printEl'></div>");
        }

        $("#printEl").html($.templates.wnr.render(wnr_json));
        $("#printEl div.barcode").barcode(wnr_json.wnr, "code128", {output: "css"});
        //alert($("#printEl").attr("id"));
        if ($("#wsf").length == 0) {
            $("#printEl").printElement({pageTitle: "HQM"});
            $("#printEl").remove();
            $("#printStatus").remove();
        } else {
            //change value wsf to pending (0) if it's 1 (complete) to prevent print twice (unknown reason)
            if ($("#wsf").val() == 1) {
                $("#wsf").val(0);
            }
            hide_el();
            window.external.printing();
        }
    }
}
function recountWnrField() {
    var noOfBag = 0, pallet = 0, bag_weight = 0, gross = 0, tare = 0, net = 0;

    $(".noOfBag").each(function () {
        noOfBag = parseInt(noOfBag) + parseInt($(this).val());
    });

    //$(".pallet").each(function () {
    //    pallet = parseFloat(pallet) + parseFloat($(this).val());
    //});

    $(".bag_weight").each(function () {
        bag_weight = parseFloat(bag_weight) + parseFloat($(this).val());
    });

    $(".gross").each(function () {
        gross = parseFloat(gross) + parseFloat($(this).val());
    });

    $(".tare").each(function () {
        tare = parseFloat(tare) + parseFloat($(this).val());
    });

    $(".net").each(function () {
        net = parseFloat(net) + parseFloat($(this).val());
    });

    $("#total_bags").attr("value", noOfBag);
    $("#total_bags_weight").attr("value", bag_weight);
    //$("#total_pallet").attr("value", pallet);
    $("#total_gross").attr("value", gross);
    $("#total_tare").attr("value", tare);
    $("#total_net").attr("value", net);
}

function select_inst() {
    chooseElement("instruction_ref_filter", "ins_" + $.data(document.body, "wn_info")[0].inst_id);
}

function addNewIpWnr() {
    //get data from body with key name: result
    if ($("body").data("result") == -1) {
        $.error("WNR này không tồn tại, hoặc chưa được allocate!!");
        //callMessageDialog({type: "error", message: "WNR này không tồn tại, hoặc chưa được allocate"});
    } else if ($("body").data("result") == -2) {
        $.error("Không thể cân hàng đang được thế chấp!!");
    } else if ($("body").data("result") == -3) {
        $.error("Không thể cân hàng khác chất lượng!!");
    } else if ($("body").data("result") == -4) {
        $.error("Vui lòng cập nhật packing!!");
    } else {
        //add new wnr
        var data = {
            "wn_id": $(".ref_list li.chosen").attr("id").split("_")[1],
            "packing": $("#wnr_packing").val(),
            //"noOfbag": $("#wnr_noOfBag").val(),
            "oldwnr": $("body").data("result"),
            "gross": $("#wnr_gross").val()
        };
        //if wnr_content is empty means that there aren't any wnr was saved before -> add html to wnr_content
        //else append an wnr_row to tbody
        if ($.trim($("#wnr_content").html()).length == 0) {
            sendJsonRequest("wnr_content", "add_first_wnr_ip", data, 0, [{name: "do_printing", params: []}]);
        } else {
            sendJsonRequest("weighing_section", "add_wnr_ip", data, 1, [{"name": "recountWnrField", "params": []}, {name: "do_printing", params: []}]);
        }
    }
}
function save_wn(data) {
    $.sendRequest({
        target: "#wn_content",
        action: "save_wn",
        data: data,
        status: "append_html"
    }, function (msg) {
        if (msg == "1") {
            $.success("Update success!");
            getWn(data.wn_id, false);
        } else {
            $.error("Update failed!");
        }
    });
}



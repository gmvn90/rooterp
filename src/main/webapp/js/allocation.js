var avail_table;
var alloc_table;
$.templates({
    inst_info: "<li>" +
            "<span width='50px'>Grade</span>" +
            "<input type='text' id='inst_grade' value='{{:grade}}' readonly=''/>" +
            "</li>" +
            "<li>" +
            "<span width='50px'>Quantity</span>" +
            "<input type='text' id='inst_quantity' class='formatNumber' value='{{:quantity}}' readonly=''/>" +
            "</li>" +
            "<li>" +
            "<span width='50px'>Allocated</span>" +
            "<input type='text' id='inst_allocated' class='formatNumber' value='{{:allocated}}' readonly=''/>" +
            "</li>" +
            "<li>" +
            "<span width='50px'>Pending</span>" +
            "<input type='text' id='inst_pending' class='formatNumber' value='{{:pending}}' readonly=''/>" +
            "</li>"
});
$(document).on("click", "#grade_filter li", function() {
    doDataTableFilter(avail_table);
});
var loadTable = function(){
    avail_table = $("#available_wn").dataTable({
        "bAutoWidth": false,
        "aLengthMenu": [[10, 25, 50], [10, 25, 50]],
        "bProcessing": true,
        "bServerSide": true,
        "sAjaxSource": getAbsolutePath() + "/available_wn_source.htm",
        "fnServerParams": function(aoData) {
            aoData.push(
                    {"name": "grade", "value": ($("#grade_filter li.chosen").length > 0) ? $("#grade_filter li.chosen").attr("id").split("_")[1] : -1}
            );
        },
        "aoColumnDefs": [
            {"sWidth": "50px", "aTargets": [5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21]},
            {"sWidth": "250px", "aTargets": [3]},
            {"sWidth": "100px", "aTargets": [1, 2]},
            {"sWidth": "20px", "aTargets": [0]},
            {"sWidth": "80px", "aTargets": [4]},
            {"sClass": "formatNumber", "aTargets": [4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21]}
        ],
        "fnDrawCallback": function(oSettings, json) {
            //alert("drawback");
            $("input[type=button]").button();
            $.formatNumber(3);
            $("tbody tr[class*=data_row]").each(function() {
                //$(this).attr('name', 'perm_5');
                $(this).attr("title", "Click Button To View Detail");
            });
            //show tool tip
            $('.masterTooltip').hover(function() {
                // Hover over code
                var title = $(this).attr('title');
                $(this).data('tipText', title).removeAttr('title');
                $('<p class="tooltip"></p>')
                        .text(title)
                        .appendTo('body')
                        .fadeIn('slow');
            }, function() {
                // Hover out code
                $(this).attr('title', $(this).data('tipText'));
                $('.tooltip').remove();
            }).mousemove(function(e) {
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
    alloc_table = $("#allocated_wn").dataTable({
        "bAutoWidth": false,
        "aLengthMenu": [[10, 25, 50], [10, 25, 50]],
        "bProcessing": true,
        "bServerSide": true,
        "sAjaxSource": getAbsolutePath() + "/allocate_wn_source.htm",
        "fnServerParams": function(aoData) {
            aoData.push(
                    {"name": "ins", "value": ($("#instruction_ref_filter li.chosen") != undefined) ? $("#instruction_ref_filter li.chosen").attr("id").split("_")[1] : -1},
            {"name": "type", "value": $("#type_filter li.chosen").attr("id").split("_")[2]}
            );
        },
        "aoColumnDefs": [
            {"sWidth": "50px", "aTargets": [5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21]},
            {"sWidth": "250px", "aTargets": [3]},
            {"sWidth": "100px", "aTargets": [1, 2]},
            {"sWidth": "20px", "aTargets": [0]},
            {"sWidth": "80px", "aTargets": [4]},
            {"sClass": "formatNumber", "aTargets": [4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21]}
        ],
        "fnDrawCallback": function(oSettings, json) {
            //alert("drawback");
            $("input[type=button]").button();
            $.formatNumber(3);
            $("tbody tr[class*=data_row]").each(function() {
                //$(this).attr('name', 'perm_5');
                $(this).attr("title", "Click Button To View Detail");
            });
            //show tool tip
            $('.masterTooltip').hover(function() {
                // Hover over code
                var title = $(this).attr('title');
                $(this).data('tipText', title).removeAttr('title');
                $('<p class="tooltip"></p>')
                        .text(title)
                        .appendTo('body')
                        .fadeIn('slow');
            }, function() {
                // Hover out code
                $(this).attr('title', $(this).data('tipText'));
                $('.tooltip').remove();
            }).mousemove(function(e) {
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
}
$(document).on("click", ".available_detail_wn", function() {
//    var styles = new Array();
//    styles.push(
//            {"name": "max-height", "style": "850px"},
//    {"name": "width", "style": "850px"},
//    {"name": "top", "style": "320px"},
//    {"name": "overflow", "style": "auto"},
//    {"name": "font-size", "style": "13px"}
//    );
//    sendJsonRequest("allocation_form", "get_available_wnr", {"id": $(this).parent().parent("tr").attr("id").split("_")[1]}, 4, null, true, {"opac": "0.6", "styles": styles});
    var wn_id = $(this).parent().parent("tr").attr("id").split("_")[1];
    var file = getAbsolutePath() + "/js/templates/allocation/wn_detail.html";
    $.when($.get(file))
            .done(function(tmplData) {
        $.sendRequest({
            action: "get_available_wnr",
            status: "open_dialog",
            data: {id: wn_id},
            optional: {action: "apply_template", template: tmplData},
        });
    });
}).on("click", ".allocated_detail_wn", function() {
    var wn_id = $(this).parent().parent("tr").attr("id").split("_")[1];
    var inst_id = $("#instruction_ref_filter li.chosen").attr("id").split("_")[1];
    var file = getAbsolutePath() + "/js/templates/allocation/wn_detail.html";
    $.when($.get(file))
            .done(function(tmplData) {
        $.sendRequest({
            action: "get_allocated_wnr",
            status: "open_dialog",
            data: {id: wn_id, inst_id: inst_id},
            optional: {action: "apply_template", template: tmplData},
        });
    });
//    $.sendRequest({
//        target: "#allocation_form",
//        action: "get_allocated_wnr",
//        data: {"id": $(this).parent().parent("tr").attr("id").split("_")[1], inst_id: $("#instruction_ref_filter li.chosen").attr("id").split("_")[1]},
//        status: "open_dialog",
//        optional: {opac: "0.6", styles: [
//                {"name": "max-height", "style": "850px"},
//                {"name": "width", "style": "850px"},
//                {"name": "top", "style": "10%"},
//                {"name": "overflow", "style": "auto"},
//                {"name": "font-size", "style": "13px"}
//            ]}
//    });
    //sendJsonRequest("allocation_form", "get_allocated_wnr", {"id": $(this).parent().parent("tr").attr("id").split("_")[1], inst_id: $("#instruction_ref_filter li.chosen").attr("id").split("_")[1]}, 4, null, true, {"opac": "0.6", "styles": styles});
}).on("click", "#btn_allocate_wn", function() {
    var that = this;
    if (!$(this).hasClass("disabled")) {
        $(that).addClass("disabled");
        var data = "";
        $(".available_ck").each(function() {
            if ($(this).is(":checked")) {
                if (data == "") {
                    data = $(this).attr("id").split("_")[2];
                } else {
                    data += "," + $(this).attr("id").split("_")[2];
                }
            }
        });
        if (data != "") {
            $.sendRequest({
                action:"allocate_wn1",
                data: {
                    data: data.toString(), 
                    ins_id: $("#instruction_ref_filter li.chosen").attr("id").split("_")[1], 
                    type: $("#type_filter li.chosen").attr("id").split("_")[2]
                },
                status: "append_data",
                optional: ["wnr_result"],
                functions: [
                    {name: "show_wnr_result", params: []},
                    {name: "doDataTableFilter", params: [avail_table, "#available_wn_wrapper"]}, 
                    {name: "doDataTableFilter", params: [alloc_table, "#allocated_wn_wrapper"]},
                    {name: "reenable_button", params: [that]},
                ]
            });
            //sendJsonRequest("allocation_form", "allocate_wn", {"data": data.toString(), "ins_id": $("#instruction_ref_filter li.chosen").attr("id").split("_")[1], "type": $("#type_filter li.chosen").attr("id").split("_")[2]}, -1, [{"name": "doDataTableFilter", "params": [avail_table, "#available_wn_wrapper"]}, {"name": "doDataTableFilter", "params": [alloc_table, "#allocated_wn_wrapper"]}]);
        } else {
            $(that).removeClass("disabled");
            //show alert dialog, implement later
            $.info("Please Choose Weight Note/Weight Note Receipt Before Allocate");
        }
    }
}).on("click", "#btn_deallocate_wn", function() {
    if (!$(this).hasClass("disabled")) {
        var data = "";
        $(".allocated_ck").each(function() {
            if ($(this).is(":checked")) {
                if (data == "") {
                    data = $(this).attr("id").split("_")[2];
                } else {
                    data += "," + $(this).attr("id").split("_")[2];
                }
            }
        });
        if (data != "") {
            $.sendRequest({
                action: "deallocate_wn1",
                data: {
                    "data": data.toString(),
                    "ins_id": $("#instruction_ref_filter li.chosen").attr("id").split("_")[1],
                    "type": $("#type_filter li.chosen").attr("id").split("_")[2]
                },
                status: "append_data",
                optional: ["wnr_result"],
                functions: [
                    {name: "show_wnr_result", params: []},
                    {name: "reloadAvailableWn", params: []},
                    {name: "reloadAllocatedWn", params: []}
                ]
            });
        } else {
            $.info("Please Choose Weight Note/Weight Note Receipt Before De-Allocate");
        }
    }
}).on("click", "#instruction_ref_filter li", function() {
    //load inst_info
    getInstructionInfoEl(this);
    //if grade list is empty --> li.length == 1 (include all)
    if ($("#grade_filter li").length == 1) {
        //get grade by ins_id, and type
        $.sendRequest({
            target: "#allocation_form",
            action: "get_grade_by_ins_id",
            data: {"id": $("#instruction_ref_filter li.chosen").attr("id").split("_")[1], "type": $("#type_filter li.chosen").attr("id").split("_")[2]},
            status: "append_html",
            functions: [
                {"name": "scrollElTop", "params": ["#grade_filter", "li.chosen"]},
                {"name": "reloadAvailableWn", "params": []},
                {"name": "reloadAllocatedWn", "params": []}
            ]
        });
//        sendJsonRequest("allocation_form", "get_grade_by_ins_id",
//                {"id": $("#instruction_ref_filter li.chosen").attr("id").split("_")[1], "type": $("#type_filter li.chosen").attr("id").split("_")[2]}, 1, [
//            {"name": "scrollElTop", "params": ["#grade_filter", "li.chosen"]},
//            {"name": "reloadAvailableWn", "params": []},
//            {"name": "reloadAllocatedWn", "params": []}
//        ]);
    } else {//if grade list is not empty
        $.sendRequest({
            target: "#allocation_form",
            action: "get_a_grade_by_ins_id",
            data: {"id": $("#instruction_ref_filter li.chosen").attr("id").split("_")[1], "type": $("#type_filter li.chosen").attr("id").split("_")[2]},
            status: "append_data",
            optional: ["grade"],
            functions: [
                {"name": "chooseElFromList", "params": ["#grade_filter li", {"name": "getGradeFromBody", "params": ["grade_"]}, 1, "id", "chosen"]},
                {"name": "scrollElTop", "params": ["#grade_filter", "li.chosen"]},
                {"name": "reloadAvailableWn", "params": []},
                {"name": "reloadAllocatedWn", "params": []}
            ]
        });
//        sendJsonRequest("allocation_form", "get_a_grade_by_ins_id", {"id": $("#instruction_ref_filter li.chosen").attr("id").split("_")[1], "type": $("#type_filter li.chosen").attr("id").split("_")[2]}, 5, [
//            {"name": "chooseElFromList", "params": ["#grade_filter li", {"name": "getGradeFromBody", "params": ["grade_"]}, 1, "id", "chosen"]},
//            {"name": "scrollElTop", "params": ["#grade_filter", "li.chosen"]},
//            {"name": "reloadAvailableWn", "params": []},
//            {"name": "reloadAllocatedWn", "params": []}
//        ], true, ["grade"]);
    }

}).on("click", "#allocate_wnr", function() {
    var data = "";
    $(".ck_wnr").each(function() {
        if ($(this).is(":checked")) {
            if (data == "") {
                data = $(this).attr("id").split("_")[2];
            } else {
                data += "," + $(this).attr("id").split("_")[2];
            }
        }
    });
    if (data != "") {
        $.sendRequest({
            action: "allocate_wnr1",
            data: {
                data: data.toString(), 
                wn_id: $("#dialog_wn_id").val(),
                ins_id: $("#instruction_ref_filter li.chosen").attr("id").split("_")[1], 
                type: $("#type_filter li.chosen").attr("id").split("_")[2]
            },
            status: "append_data",
            optional: ["wnr_result"],
            functions: [
                {"name": "removeElementId", "params": [$(this).parent().attr("id")]}, 
                {"name": "closeOpacity", "params": []}, 
                {name: "show_wnr_result", params: []},
                {"name": "doDataTableFilter", "params": [avail_table, "#available_wn_wrapper"]}, 
                {"name": "doDataTableFilter", "params": [alloc_table, "#allocated_wn_wrapper"]}
            ],
        });
    } else {
        $.warning("Please Choose Weight Note Receipt");
    }
}).on("click", "#deallocate_wnr", function() {
    var data = "";
    $(".ck_wnr").each(function() {
        if ($(this).is(":checked")) {
            if (data == "") {
                data = $(this).attr("id").split("_")[2];
            } else {
                data += "," + $(this).attr("id").split("_")[2];
            }
        }
    });

    if (data != "") {
        $.sendRequest({
            action: "deallocate_wnr1",
            data: {
                data: data.toString(), 
                wn_id: $("#dialog_wn_id").val(), 
                ins_id: $("#instruction_ref_filter li.chosen").attr("id").split("_")[1], 
                type: $("#type_filter li.chosen").attr("id").split("_")[2]
            },
            status: "append_data",
            optional: ["wnr_result"],
            functions: [
                {name: "removeElementId", params: [$(this).parent().attr("id")]}, 
                {name: "closeOpacity", params: []}, 
                {name: "show_wnr_result", params: []},
                {name: "doDataTableFilter", params: [avail_table, "#available_wn_wrapper"]}, 
                {name: "doDataTableFilter", params: [alloc_table, "#allocated_wn_wrapper"]}
            ]
        });
    } else {
        $.warning("Please Choose Weight Note Receipt");
    }
}).on("click", "#close_dialog", function() {
    $(this).parent("div").remove();
    closeOpacity();
});
function getGradeFromBody(prefix) {
    var data = ($.data(document.body, "grade") != undefined) ? $.data(document.body, "grade") : $("body").data("grade");
    return prefix + data;
}
function reloadAvailableWn() {
    doDataTableFilter(avail_table, "#available_wn_wrapper");
}
function reloadAllocatedWn() {
    doDataTableFilter(alloc_table, "#allocated_wn_wrapper");
}
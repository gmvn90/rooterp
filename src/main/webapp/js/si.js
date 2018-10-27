function ajaxUpload(fileObject, serverType, callback) {
    var formData = new FormData();
    formData.append('file', fileObject[0].files[0]);
    formData.append("name", $(fileObject).closest(".files_container").find(".name").val());
    formData.append("emails", $(fileObject).closest(".files_container").find(".emails").val());
    formData.append("type", serverType);
    $.ajax({
        url: getAbsolutePath() + '/shipping-instruction/' + shipping_instruction_id + '/upload-file-sent.json',
        type: 'POST',
        data: formData,
        processData: false, // tell jQuery not to process the data
        contentType: false, // tell jQuery not to set contentType
        success: function(data) {
            console.log(data);
            callback(data);
        }
    });
};

function updateFileSent(id, remindName, callback) {
    var formData = new FormData();
    formData.append("id", id);
    formData.append("remindName", remindName);
    $.ajax({
        url: getAbsolutePath() + "/shipping-instruction/" + shipping_instruction_id + "/update-file-sent.json",
        type: 'POST',
        data: formData,
        processData: false, // tell jQuery not to process the data
        contentType: false, // tell jQuery not to set contentType
        success: function(data) {
            console.log(data);
            callback(data);
        }
    });
};

function getLastDateOfMonth(date) {
    var lastDayOfMonth = new Date(date.getFullYear(), date.getMonth() + 1, 0);
    return lastDayOfMonth;
};

function setDefault() {
    $("#supplier").val(globalClientId);
    $("#shipper").val(globalClientId);
    $("#allocationGrade").change(function(e) {
        $("#grade").val($(e.target).val());
    });
    $("#romDate").change(function(e) {
        var lastDate = getLastDateOfMonth($(e.target).datepicker("getDate"));
        $("#toDate").val($.datepicker.formatDate("d-M-y", lastDate));
        $("#loadDate").val($.datepicker.formatDate("d-M-y", lastDate));
    });
};
$(document).ready(function() {
    $(".js_date").datepicker({
        dateFormat: "d-M-y",
        changeMonth: true,
        changeYear: true,
        yearRange: "1945:2050"
    });
    setDefault();
}).on("click", "#createNew", function() {
    document.location = $(this).data('href');
}).on("click", "#add_sample_sent", function() {
    var dialog = $("#choose-courier").dialog({
        autoOpen: false,
        height: 150,
        width: 300,
        modal: true,
        buttons: {
            "Add": function() {

                $("#sample_sent_form").submit();
                dialog.dialog("close");
            },
        },
        close: function() {
            dialog.dialog("close");
        }
    });
    dialog.dialog("open");
    return false;

}).on("click", "#btn_report", function() {
        var dialog = $("#dialog-form").dialog({
            autoOpen: false,
            height: 150,
            width: 300,
            modal: true,
            buttons: {
                "Weight Note": function() {
                    $.sendRequest({
                        action: "printShippingReport",
                        status: "export",
                        data: { si_id: shipping_instruction_id }
                    }, function() {
                        dialog.dialog("close");
                        return true;
                    });
                },
                "Weight Note Receipt": function() {
                    $.sendRequest({
                        action: "printShippingReportDetail",
                        status: "export",
                        data: { si_id: shipping_instruction_id }
                    }, function() {
                        dialog.dialog("close");
                        return true;
                    });
                }
            },
            close: function() {
                dialog.dialog("close");
            }
        });
        dialog.dialog("open");
}).on("click", "#btn_print", function() {

    var dialog = $("#dialog-form").dialog({
        autoOpen: false,
        height: 150,
        width: 300,
        modal: true,
        buttons: {
            "Without Cost": function() {
                $("select").each(function() {
                    $(this).find(":selected").attr("selected", true);
                });
                $("input[type=checkbox]").filter(":checked").attr("checked", true);
                $("#detail_content_no_cost").printThis();
                return true;
            },
            "With Cost": function() {
                $("select").each(function() {
                    $(this).find(":selected").attr("selected", true);
                });
                $("input[type=checkbox]").filter(":checked").attr("checked", true);
                $("#detail_content").printThis();
                return true;
            }
        },
        close: function() {
            dialog.dialog("close");
        }
    });
    dialog.dialog("open");

}).on("click", "#cloneNotify", function() {
    $("#notifyContainer").append($(".notifyRow").first().clone());
}).on("click", "#deleteNotify", function() {
    if ($("#notifyContainer tr").children().length != 1) {
        $(".notifyRow").last().remove();
    }
}).on("change", ".button_submit", function() {
    var elm = $(this);
    var child_name = elm.data("child_name");
    var type = elm.data("type");
    console.log(child_name);
    var serverType = "Internal";
    if (type == "reference_file") {
        serverType = "External";
    }
    ajaxUpload(elm.closest(".files_container").find(".file_reference_file"), serverType, function(data) {

        elm.closest(".files_container").find(".tr_reference_file_container")
            .append($.templates("#singleFileContainer").render({
                data: data,
                name: child_name,
            }, { formatDate: function(b) {
                    return $.format.date(new Date(b), getDateFormatString()); } }));

    });
}).on("click", ".updateFileSentBtn", function() {
    var remindName = $(this).siblings().val();
    var id = $(this).data("id");
    console.log(remindName, id);
    updateFileSent(id, remindName, function(data) { console.log(data); });
});

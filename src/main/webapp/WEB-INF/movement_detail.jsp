<%-- 
    Document   : movement
    Created on : Nov 19, 2013, 10:45:11 AM
    Author     : kiendn
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <jsp:include page="header.jsp">
            <jsp:param name="url" value="25"/>
            <jsp:param name="page" value="61"/>
        </jsp:include>
        <form id="processing_form" method="POST">
            <div id="main_content" style='width: 1200px !important'>
                <div class="left border" style="width: 200px !important; margin-left: 0px; margin-right: 5px">
                    <div>
                        <label>Processing Ref</label>
                    </div>
                    <div class="pc100">
                        <input type="text" id="filter_ref" value="" style="width: 96%; margin-top: 0px; margin-bottom: 1px;" />
                        <ul class="ref_list" id="process_list" style="margin-top: 15px; margin-left: 5px; padding-left: 10px; width: 160px !important">
                        </ul>
                    </div>
                    <div style="margin-top: 15px">
                        <input type="button" id="btn_add_new" value="Add" name="perm_63"/>
                        <input type="button" id="btn_delete" value="Del"  name="perm_65"/>
                    </div>
                </div>
                <div class="grid_10 main_container" style="width: 970px !important;margin-left: 0px; margin-right: 5px; overflow: hidden; height: auto; padding-bottom: 10px">
                    <span>Detail</span>
                    <div id="detail_content" class="pc100" style="height: 500px">
                        <div class="pc60 left cell">
                            <div class="pc100">
                                <div class="pc40 left cell">
                                    <span class="right label">Movement Ref</span>
                                </div>
                                <div class="pc50 left cell">
                                    <input type="text" style="width: 97%" id="refNumber" name="ref_number" value="" readonly=""/>
                                </div>
                            </div>
                            <div class="pc100">
                                <div class="pc40 left cell">
                                    <span class="right label">Pledge</span>
                                </div>
                                <div class="pc50 left cell">
                                    <select name="pledge" id="pledge" style="width: 100%" rq_type="text">
                                        <option value=""></option>
                                    </select>
                                </div>
                            </div>
                            <div class="pc100">
                                <div class="pc40 left cell">
                                    <span class="right label">Client</span>
                                </div>
                                <div class="pc50 left cell">
                                    <select name="client" id="client" style="width: 100%" rq_type="text">
                                        <option value=""></option>
                                    </select>
                                </div>
                            </div>

                            <div class="pc100">
                                <div class="pc40 left cell">
                                    <span class="right label">Client Ref</span>
                                </div>
                                <div class="pc50 left cell">
                                    <input type="text" style="width: 97%" id="clientRef" name="clientRef" value="" rq_type="text"/>
                                </div>
                            </div>

                            <div class="pc100">
                                <div class="pc40 left cell">
                                    <span class="right label">Date</span>
                                </div>
                                <div class="pc50 left cell">
                                    <span class="current_date_time" id="date" rq_type="text"></span>
                                </div>
                            </div>

                            <div class="pc100">
                                <div class="pc40 left cell">
                                    <span class="right label">Origin</span>
                                </div>
                                <div class="pc50 left cell">
                                    <select name="origin" id="originId" style="width: 100%" rq_type="text">
                                        <option value="1" selected>VietNam</option>
                                    </select>
                                </div>
                            </div>

                            <div class="pc100">
                                <div class="pc40 left cell">
                                    <span class="right label">Quality</span>
                                </div>
                                <div class="pc50 left cell">
                                    <select name="quality" id="qualityId" style="width: 100%" rq_type="text">
                                        <option value="1" selected>Robusta</option>
                                        <option value="2">Arabica</option>
                                    </select>
                                </div>
                            </div>

                            <div class="pc100">
                                <div class="pc40 left cell">
                                    <span class="right label">Grade</span>
                                </div>
                                <div class="pc50 left cell">
                                    <select name="grade" id="grade" style="width: 100%" rq_type="text">
                                        <option value=""></option>
                                    </select>
                                </div>
                            </div>

                            <div class="pc100">
                                <div class="pc40 left cell">
                                    <span class="right label">Packing</span>
                                </div>
                                <div class="pc50 left cell">
                                    <select name="packing" id="packing" style="width: 100%" rq_type="text">
                                        <option value=""></option>
                                    </select>
                                </div>
                            </div>

                            <div class="pc100">
                                <div class="pc40 left cell">
                                    <span class="right label">Quantity</span>
                                </div>
                                <div class="pc50 left cell">
                                    <input type="text" style="width: 50%" id="tons" class="numberOnly" name="quantities" value=""  rq_type="text"/> Mts
                                </div>
                            </div>
                            <div class="pc100">
                                <div class="pc40 left cell">
                                    <span class="right label">Processing Period</span>
                                </div>
                                <div class="pc50 left cell" style='margin-bottom: 15px'>
                                    <div style='width: 100px; height: 30px;position: relative; float: left'>
                                        <span style='position: absolute; left: 0; top: 0; font-size: 10px'>From Date</span>
                                        <input type="text" style="position: absolute; width: 100px; left: 0; top: 12px" class="date_picker_text" id='fromDate' name="txtFrom" value="" rq_type="text"/>
                                    </div>
                                    <div style='width: 100px; height: 30px;position: relative; float: right'>
                                        <span style='position: absolute; left: 0; top: 0; font-size: 10px'>To Date</span>
                                        <input type="text" style="position: absolute; width: 100px; left: 0; top: 12px" class="date_picker_text" id='toDate' name="txtTo" value="" rq_type="text"/>
                                    </div>
                                </div>
                            </div>
                            <div class="pc100">
                                <div class="pc40 left cell">
                                    <span class="right label">Re-weight</span>
                                </div>
                                <div class="pc50 left cell">
                                    <input type="checkbox" id="type" value="0" rq_type="text"/>
                                </div>
                            </div>
                            <div class="pc100">
                                <div class="pc40 left cell">
                                    <span class="right label">Area</span>
                                </div>
                                <div class="pc50 left cell">
                                    <input type="text" style="width: 50px" id="areaCode" name="area_name" value="" rq_type="text"/>
                                </div>
                            </div>
                            <div class="pc100">
                                <div class="pc40 left cell">
                                    <span class="right label">Remark</span>
                                </div>
                                <div class="pc50 left cell">
                                    <textarea name="remark" id='remark' style="width: 530px; height: 50px" rq_type="text"></textarea>
                                </div>
                            </div>
                            <div class="pc100">
                                <div class="pc40 left cell"  style='margin-top: 25px'>
                                    <span class="right label">Completed</span>
                                </div>
                                <div class="pc50 left cell"  style='margin-top: 25px'>
                                    <input type="checkbox" name="completed" id="status" value="0" rq_type="text"/>
                                </div>
                            </div>
                            <div class="pc100" style='margin-top: 10px'>
                                <div class="pc40 left cell">
                                    <span class="right label">By Order of</span>
                                </div>
                                <div class="pc50 left cell" id='username'>
                                    <!-- By order of -->
                                </div>
                            </div>
                        </div>
                        <div class="pc40 right cell">
                            <div class="pc100">
                                <div class="pc40 left cell">
                                    <span class="right label">Allocated</span>
                                </div>
                            </div>
                            <div class="pc100">
                                <div class="pc40 left cell">
                                    <span class="right label mts"id='allocated'>0.000</span>
                                </div>
                            </div>
                            <div class="pc100">
                                <div class="pc40 left cell">
                                    <span class="right label">Moved</span>
                                </div>
                            </div>
                            <div class="pc100">
                                <div class="pc40 left cell">
                                    <span class="right label mts" id='moved'>0.000</span>
                                </div>
                            </div>

                            <div class="pc100">
                                <div class="pc40 left cell">
                                    <span class="right label">Pending</span>
                                </div>
                            </div>
                            <div class="pc100">
                                <div class="pc40 left cell">
                                    <span class="right label mts" id='pending'>0.000</span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="right" style="width: 990px; margin-right: 3px; margin-top: 20px">
                    <input class="left" type="button" id="btn_save" value="Save"  name="perm_64"/>
                    <input class="right" type="button" id="btn_list_view" value="List View" />
                    <input class="right" type="button" name="perm_66" id="btn_allocation" value="Allocation" />
                    <input class="right" type="button" id="btn_report" value="Print"  name="perm_68"/>
                </div>
                <input type="hidden" id="user" value="0"/>
                <input type="hidden" id="area" name="area" value="0" rq_type="text"/>
                <input type="hidden" id="fw_type" name="fw_type" value=""/>
                <input type="hidden" id="fw_id" name="fw_id" value=""/>
                <input type="hidden" id="wl_value" name="wl_value" value=""/>
                <input type='hidden' id='id' value='${initial_id}'/> 
            </div>
        </form>
        <jsp:include page="footer.jsp"/>
        <script>
            $(document).ready(function() {
                $.data(document.body, "mov_info", $.getData());
                var opt = {all: true};
                generateSelection("#grade", "GradeMaster", -1, {action: "gradeInStock"});
                generateSelection("#client", "CompanyMaster", -1);
                generateSelection("#pledge", "CompanyMaster", -1);
                generateSelection("#packing", "PackingMaster", -1);
                generateSelection("#processing_type", "ProcessingType", -1, opt);
                if ($.data(document.body, "mov_info").id == undefined) {
                    $("#btn_save").hide();
                }
                loadRefList(0);
            }).on("click", "#areaCode", function() {
                var area = $.data(document.body, "mov_info") != undefined && $.data(document.body, "mov_info").area != undefined ? $.data(document.body, "mov_info").area : 0;
                var file = getAbsolutePath() + "/js/templates/area/map_container.html";
                $.when($.get(file))
                        .done(function(tmplData) {
                    $.sendRequest({
                        action: "get_warehouse",
                        status: "open_dialog",
                        data: {area: area},
                        optional: {action: "apply_template", template: tmplData, type: "map_container"}
                    });
                });
            }).on("click", ".choose_cell", function() {
                $("#areaCode").val($("#map_select option:selected").text() + "-" + $(this).parent().parent("td").attr("ordinate"));
                $("#area").val($(this).parent().parent("td").attr("id"));
                $.closeDialog("#message_box");
            }).on("click", "#btn_add_new", function() {
                delete($.data(document.body, "mov_info")["id"]);
                $.resetForm({
                    ext: [
                        {element: "#date", type: "text", value: ""}
                    ]
                });
                $("#btn_save").show();
            }).on("click", "#btn_save", function() {
                var validate = $.validation([
                    {
                        element: "#client",
                        msg: "Please Choose Client Name",
                        opts: {
                            type: "!=",
                            value: -1
                        }
                    },
                    {
                        element: "#grade",
                        msg: "Please Choose A Grade",
                        opts: {
                            type: "!=",
                            value: -1
                        }
                    },
                    {
                        element: "#packing",
                        msg: "Please Choose Packing",
                        opts: {
                            type: "!=",
                            value: -1
                        }
                    },
                    {element: "#tons", msg: "Please Enter The Tons"},
                    {
                        element: "#tons",
                        msg: "Tons Must Be Greater Than 0",
                        opts: {
                            type: ">",
                            value: 0
                        }
                    },
                    {element: "#area_name", msg: "Please Choose Area"}
                ]);
                if (validate) {
                    var data = $.getData();
                    var isNew = ($.data(document.body, "mov_info").id == undefined);
                    //data["id"] = -1;
                    data["date"] = $.format.date(new Date, "dd-MM-yyyy");
                    $.extend($.data(document.body, "mov_info"), data);
                    $.sendRequest({
                        action: "update_movement",
                        data: {data: JSON.stringify($.data(document.body, "mov_info"))},
                        optional: {url_type: "json"}
                    }, function(msg) {
                        if (msg != null) {
                            $.success("Update Succeeded");
                            loadMoveInfo(msg);
                            $.extend($.data(document.body, "mov_info"), msg);
                            if (isNew) {
                                loadRefList($.data(document.body, "mov_info").id, {return: false});
                            }
//                            loadRefList(0);
//                            return true;
                        } else {
                            $.error("Cannot Insert/Update Movement");
                        }
                    });
                }
            }).on("click", ".ref_list li", function() {
                var id = $(this).attr("id");
                $.sendRequest({
                    action: "getCoreInfo",
                    data: {id: id, value: "MovementView"},
                    optional: {url_type: "json"}
                }, function(msg) {
                    var data = (typeof(msg) == 'string') ? JSON.parse(msg) : msg;
//                    $.parseData({data: data});
                    loadMoveInfo(msg);
                    $.extend($.data(document.body, "mov_info"), data);
                    $("#btn_save").show();
                    return true;
                });
            }).on("click", "#btn_allocation", function() {
                var id = $.data(document.body, "mov_info").id;
                if (id != undefined) {
                    create_hidden_form({"id": "allocation_form", "action": getAbsolutePath() + "/Allocation/detail.htm", "method": "POST"}, [{"id": "ins_id", "name": "ins_id", "value": id}, {"id": "ins_type", "name": "ins_type", "value": "M"}, {id: "fw_type", name: "fw_type", value: "alloc"}]);
                    $("#allocation_form").submit();
                }
            });
            function loadMoveInfo(mov) {
                //$.parseData({data: mov});
                $.sendRequest({
                    action: "getUserName",
                    data: {id: mov.user}
                }, function(msg) {
//                    $("#username").text(msg);
                    mov["username"] = msg;
                    $.sendRequest({
                        action: "loadAllocatedMoved",
                        data: {id: mov.id}
                    }, function(msg) {
                        console.log("loadAllocatedMoved "); 
                        console.log(JSON.parse(msg));
                        console.log(mov.id);
                        $.extend(mov, JSON.parse(msg));
                        $.parseData({data: mov});
                        $(".mts").append(" Mts");
                        return true;
                    });
                });
                //sendRequest load allocated, pending tons.

                
            }
            function loadRefList(id, opt) {
                var options = {"return": true}
                if (opt != undefined) {
                    $.extend(options, opt);
                }
                $.sendRequest({
                    action: "load_mov_ref_list",
                    data: {id: id, search: $("#filter_ref").val()}
                }, function(msg) {
                    var data = JSON.parse(msg);
                    var file = getAbsolutePath() + "/js/templates/common/li.html";
                    $.when($.get(file))
                            .done(function(tmplData) {
                        $("ul.ref_list").html($.templates(tmplData).render(data));
                        scrollElTop("ul.ref_list", "li.chosen");
                    });
                    return options["return"];
                });
            }
            function checkForward() {
                if ($("#id").val() != 0) {

                }
            }
        </script>
    </body>
</html>

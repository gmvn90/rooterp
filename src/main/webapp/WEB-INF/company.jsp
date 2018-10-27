<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Company Page</title>
        <style type="text/css">
            input, select, textarea {
                box-sizing: border-box;
                -moz-box-sizing: border-box;
                -webkit-box-sizing: border-box;
                -ms-box-sizing: border-box;
            }
            .dataTable {border-collapse: collapse}
            .dataTable tr th {border-collapse: collapse; border: 1px solid black;}
            .dataTable tr th {font-size: 13px;}
            .dataTable tr td {padding: 5px;}
            .dataTable tr td.formatNumber {text-align: right}
            .dataTable tr.data_row td {font-size: 11px; border-collapse: collapse; border: 1px solid black;}
            .dataTable thead {background: #EEEEEE}
            .dataTable tbody tr.data_row:hover{cursor: pointer; background: #e5eff8}
            .empty_col {border: none !important; background: white; color: white}
            .dataTable tr#mytable_footer {font-weight: bold}
            table#filtering thead th {border-bottom: 1px solid black}
            table#filtering th, table#filtering td {padding: 10px; min-width: 100px}            
            table#filtering thead tr th {border-collapse: collapse; }
            a.button{
                font-size: 30px;
                position: absolute;
                text-decoration: none;
                color: black;
                cursor: pointer;
                left: 20px;
            }
            a.button:active{
                text-decoration: none;
                color: black;
            }
            #moveToRight{
                top: 170px;
            }
            #moveToLeft{
                top: 250px;
            }
            #message_box.white {
                width: 770px;
                background: whitesmoke;
                padding-left: 15px;
                left: 30%;
            }
            #companytype_section li{
                width: 100%;
                text-align: left;
            }
            #companytype_section li.ctype_status{
                margin-right: 10px;
            }
            #companytype_section{
                width: 120px;
                padding-left: 10px;
                line-height: 20px;
            }
        </style>
    </head>
    <body>
        <jsp:include page="header.jsp">
            <jsp:param name="url" value="26"/>
            <jsp:param name="page" value="69"/>
        </jsp:include>
        <form action="Company" id="company_form" method="post">
            <div id="main_content" style="width: 1500px;">  
                <div style="width: 100%; border-bottom: 1px solid gray; height: 180px; position: relative">
                    <table class="table_filter left">
                        <thead>
                            <tr style="font-size: 13px">
                                <th width="200px">Type</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td width="200px">
                                    <input id="type_filtertext" type="text" class="text_filter_child" style="width: 200px">
                                    <ul id="type_filter" class="filter">
                                    </ul>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                    <input style="position: absolute; right: 0px; bottom: 10px" type="button" id="assign_company_type" value="Assign Company Type" />
                </div>
                <table id='myTable' style='width: 100%;'>
                    <thead>
                        <tr>
                            <th rowspan="3"></th>
                            <th colspan="2" rowspan="2">Full Name</th>
                            <th colspan="2" rowspan="2">Name</th>
                            <th colspan="2">Address</th>
                            <th rowspan="3">Representative</th>
                            <th colspan="2" rowspan="2">Representative Role</th>
                            <th rowspan="3">E-mail</th>
                            <th rowspan="3">Fax</th>
                            <th rowspan="3">Telephone</th>
                            <th rowspan="3">Country</th>
                            <th rowspan="3">Active</th>
                        </tr>
                        <tr>
                            <th colspan="2">#1</th>
                        </tr>
                        <tr>
                            <th>EN</th>
                            <th>VIE</th>
                            <th>EN</th>
                            <th>VIE</th>
                            <th>EN</th>
                            <th>VIE</th>
                            <th>EN</th>
                            <th>VIE</th>
                        </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </div>
        </form>
        <jsp:include page="footer.jsp"></jsp:include>
        <script type="text/x-jsrender" id="tmpl_companytype_list">
            {{for companytype_list}}
            <li><input type="checkbox" name="ctype_{{:id}}" id="ctype_{{:id}}" class="ctype_status" value="0" style='float: left'/>{{:name}}</li>
            {{/for}}
        </script>
        <script type="text/javascript">
            var oTable;
            $(document).ready(function() {
                //load_companytype_list
                generateSelection("#type_filter", "CompanyType", "type_-1", {action: "load_companytypes", type: "list", prefix: "type_", all: true});
                oTable = $('#myTable').dataTable({
                    "aLengthMenu": [[10, 25, 50, 100, -1], [10, 25, 50, 100, "All"]],
                    "bProcessing": true,
                    "bServerSide": true,
                    "sAjaxSource": "company_ajax_source.htm",
                    "aoColumnDefs": [
                        {"sWidth": "15px", "aTargets": [0]},
                        {"sWidth": "105px", "aTargets": [1,2]},
                        {"sWidth": "77px", "aTargets": [3,4]},
                        {"sWidth": "170px", "aTargets": [5,6]},
                        {"sWidth": "107px", "aTargets": [7]},
                        {"sWidth": "70px", "aTargets": [8,9]},
                        {"sWidth": "90px", "aTargets": [10]},
                        {"sWidth": "63px", "aTargets": [11]},
                        {"sWidth": "77px", "aTargets": [12]},
                        {"sWidth": "62px", "aTargets": [13]},
                        {"sWidth": "43px", "aTargets": [14]}
                    ],
                    "fnServerParams": function(aoData) {
                        aoData.push(
                                {"name": "company_type", "value": $("#type_filter li.chosen").attr("id").split("_")[1]}
                        );
                    },
                    "fnServerData": function(sSource, aoData, fnCallback) {
                        $.getJSON(sSource, aoData, function(json) {
                            fnCallback(json);
                        });
                    },
                    "fnDrawCallback": function(oSettings, json) {
                        $("tbody tr[class*=data_row]").each(function() {
                            if ($(this).attr("id") !== 'mytable_footer') {
                                $(this).attr("title", "Double Click To View Company Detail");
                            }
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
                            $('.tooltip').css({
                                top: mousey,
                                left: mousex
                            });
                        });
                    },
                    "sPaginationType": "full_numbers",
                    "sDom": 'Rlfrtip'
                });
            });

            $(document).on("dblclick", "#myTable tr", function() {
                if ($(this).attr("id") !== 'mytable_footer') {
                    var id = $(this).attr("id").split("_")[1];
                    //pos = oTable.fnGetPosition(this);
                    $('#id').val(id);
                    //openDialog("company_form", "load_company", 0.6);
                    getDetailCompany(id);
                }
            }).on("click", "#assign_company_type", function() {
                var file = getAbsolutePath() + "/js/templates/company/updateCompanyType.html";
                $.when($.get(file))
                        .done(function(tmplData) {
                    var options = {template: tmplData, type: "white", isAppendBody: false};
                    $.fn.callDialog({}, options);
                    loadCompanyTypeList();
                });
            }).on("click", "#btn_save", function() {
                updateTypes();
            }).on("click", "#company_list li", function() {
                var id = $(this).attr("id").split("_")[2];
                $(this).remove();
                selectList({
                    id: id
                }, function(id) {
                    $("#selected_companies").html("");
                    generateSelection("#selected_companies", "selectedData", "selected_comp_-1", {
                        action: "", type: "list", prefix: "selected_comp_"
                    });
                    $("#selected_companies").scrollTo($("#selected_comp_" + id), 300);
                });
            }).on("click", "#selected_companies li", function() {
                var id = $(this).attr("id").split("_")[2];
                $(this).remove();
                selectList({
                    id: id,
                    data1: "selectedData",
                    data2: "CompanyMaster"
                }, function(id) {
                    $("#company_list").html("");
                    generateSelection("#company_list", "CompanyMaster", "avail_comp_-1", {
                        action: "get_allcompany_filter", type: "list", prefix: "avail_comp_"
                    });
                    $("#company_list").scrollTo($("#avail_comp_" + id), 300);
                });
            }).on("click", "#moveToRight", function() {
                transferData({
                    data1: "selectedData",
                    data2: "CompanyMaster"
                });
            }).on("click", "#moveToLeft", function() {
                transferData();
            }).on("click","#type_filter li", function(){
                doDataTableFilter(oTable);
            });

            function transferData(opts, callback) {
                var ext = {
                    data1: "CompanyMaster",
                    data2: "selectedData",
                }
                if (opts != undefined) {
                    $.extend(ext, opts);
                }
                var dt1 = !$.data(document.body, ext.data1) ? [] : $.data(document.body, ext.data1);
                var dt2 = !$.data(document.body, ext.data2) ? [] : $.data(document.body, ext.data2);
                $.merge(dt1, dt2);
                dt2 = [];
                $.data(document.body, ext.data2, dt2);
                $.data(document.body, ext.data1, dt1);

                $("#company_list").html("");
                generateSelection("#company_list", "CompanyMaster", "avail_comp_-1", {
                    action: "get_allcompany_filter", type: "list", prefix: "avail_comp_"
                });
                $("#selected_companies").html("");
                generateSelection("#selected_companies", "selectedData", "selected_comp_-1", {
                    action: "", type: "list", prefix: "selected_comp_"
                });
            }

            function selectList(opts, callback) {
                var ext = {
                    data1: "CompanyMaster",
                    data2: "selectedData",
                    id: 0
                }
                if (opts != undefined) {
                    $.extend(ext, opts);
                }
                var temp = {};
                var dt1 = $.data(document.body, ext.data1);
                $.each(dt1, function(i, val) {
                    if (val != undefined) {
                        if (val.id == ext.id) {
                            temp = val;
                            dt1.splice(i, 1);
                        }
                    }
                });
                $.data(document.body, ext.data1, dt1);
                var dt2 = $.data(document.body, ext.data2);
                if (dt2 == undefined) {
                    dt2 = [];
                }
                dt2.push(temp);
                $.data(document.body, ext.data2, dt2);
                if (callback != undefined) {
                    callback(ext.id);
                }
            }

            function getDetailCompany(id) {
                var file = getAbsolutePath() + "/js/templates/company/company.html";
                $.when($.get(file))
                        .done(function(tmplData) {
                    $.sendRequest({
                        action: "load_detail_company",
                        data: {id: id},
                        status: "open_dialog",
                        optional: {turnOffLayout: true, url_type: "json", action: "apply_template", template: tmplData, type: "forminput"}
                    });
                });
            }

            function loadCompanyTypeList() {
                $.sendRequest({
                    target: "#companytype_section",
                    action: "load_companytype_list",
                    status: "apply_template",
                    optional: {opt: "replace", tmpl: "#tmpl_companytype_list"}
                }, function(msg) {
                    return false;
                });
            }

            function loadCompanyTypeInfo(id) {
                $.sendRequest({
                    action: "load_companytype_info",
                    data: {comp_id: id},
                    status: "append_data",
                    optional: ["companytype_info"],
                    functions: [
                        {name: "parseCompanyTypeInfo", params: []}
                    ]
                });
            }
            function parseCompanyTypeInfo() {
                var com_info = $.data(document.body, "companytype_info");
                var types = com_info.roles;
                $(".ctype_status").each(function() {
                    $(this).prop("checked", false);
                });
                for (var i = 0; i < types.length; i++) {
                    $("#ctype_" + types[i].role).prop("checked", true);
                }
            }
            function updateTypes() {
                //specify new or update
                //if is update check pwd, if new check pwd is null
                if (!$.data(document.body, "selectedData")) {
                    $.error("Please Choose Company");
                } else {

                    var types = "";
                    var types = [];
                    $(".ctype_status").each(function() {
                        var type = {};
                        type[$(this).attr("id").split("_")[1]] = $(this).prop("checked");
                        types.push(type);
                    });
                    var companies = "";
                    var selectedData = $.data(document.body, "selectedData");
                    for (var i = 0; i < selectedData.length; i++) {
                        if (i == 0) {
                            companies = selectedData[i].id;
                        } else {
                            companies += "," + selectedData[i].id;
                        }
                    }
//                    types = "[" + types.substr(0, types.length - 1) + "]";
//                    var a = JSON.parse(types);
//                    console.log(a);
//                $("input:radio[name^=permission_]").each(function() {
//                    if (name == "" || name != $(this).attr("name")) {
//                        name = $(this).attr("name");
//                        var value = $("input:radio[name=" + name + "]:checked").val();
//                        perms.push({page: name.split("_")[1], perm: (value != undefined) ? value : 0});
//                    }
//                    //console.log(name);
//                });
//                $.extend($.data(document.body, "user_info"), $.getData({
//                    extend: [
//                        {key: "perms", value: JSON.stringify(perms)}
//                    ]
//                }));
//                console.log(user_info);
                    $.sendRequest({
                        action: "update_company_type",
                        data: {
                            typestr: JSON.stringify(types),
                            companies: companies
                        }
                    }, function(msg) {
                        $.success("Update success!");
                        return false;
                    });
                }


            }
        </script>  
    </body>
</html>

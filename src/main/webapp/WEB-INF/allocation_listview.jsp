\<%--
    Document   : allocation_listview
    Created on : Aug 22, 2013, 10:18:23 AM
    Author     : shind_000
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
            <jsp:param name="url" value="15"/>
            <jsp:param name="page" value="17"/>
        </jsp:include>
        <style>
            ul.list_date_filter li:hover{background: white !important; color: black !important; cursor: default;}
        </style>
        <form id="allocation_form">
            <div style="width: 980px ! important; padding: 15px; background: none repeat scroll 0% 0% white; height: auto; overflow: hidden; min-height: 400px" class="container_16 border" id="wrapper">
                <div style="width: 100%; overflow: hidden; height: auto; border-bottom: 1px solid #B8C2CC; padding-bottom: 10px">
                    <table class="table_filter">
                        <thead>
                            <tr style="font-size: 13px">
                                <th width="150px">Type</th>
                                <th width="150px">Reference Number</th>
                                <th width="250px">Grade</th>
                                <th width="150px">Status</th>
                                <th width="150px">Date</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>
                                    <input type="text" class="text_filter_child">
                                    <ul id="type_filter" class="filter" name="perm_40">
                                        <li id="inst_type_P" class="chosen">Processing</li>
                                        <li id="inst_type_E">Export</li>
                                    </ul>
                                </td>
                                <td>
                                    <input type="text" style="width: 150px" class="text_filter_child">
                                    <ul id="instruction_ref_filter" class="filter">
                                        <li id="inst_-1" class="chosen">All</li>
                                    </ul>
                                </td>
                                <td>
                                    <input type="text" style="width: 250px;" class="text_filter_child">
                                    <ul id="grade_filter" class="filter">
                                        <li id="grade_-1" class="chosen">All</li>
                                    </ul>
                                </td>
                                <td>
                                    <input type="text" style="width: 150px;" class="text_filter_child">
                                    <ul id="status_filter" class="filter">
                                        <li id="status_-1" class="chosen">All</li>
                                        <li id="status_0">Pending</li>
                                        <li id="status_1">Complete</li>
                                    </ul>
                                </td>
                                <td>
                                    <ul style="width: 100%; line-height: 20px" class="list_date_filter">
                                        <li>
                                            <span width="30px">From Date</span>
                                            <label>
                                                <input type="text" id="from_date_filter" class="date_picker_text" readonly="true"/>
                                                <a href="javascript:void(0);" class="clear_date_filter">Reset</a>
                                            </label>
                                        </li>
                                        <li>
                                            <span width="30px">To Date</span>
                                            <label>
                                                <input type="text" id="to_date_filter" class="date_picker_text" readonly="true"/>
                                                <a href="javascript:void(0);" class="clear_date_filter">Reset</a>
                                            </label>
                                        </li>
                                    </ul>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <div style="width: 100%;overflow: hidden; height: auto; border-bottom: 1px solid #B8C2CC; padding-bottom: 10px">
                    <table id="allocation_table">
                        <thead>
                            <tr>
                                <th></th>
                                <th>Reference Number</th>
                                <th>Grade</th>
                                <th>Packing</th>
                                <th>Quantity</th>
                                <th>Allocated Tons</th>
                                <th>Delivered Tons</th>
                                <th>From Date</th>
                                <th>To Date</th>
                            </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
                </div>
            </div>
        </form>
        <jsp:include page="footer.jsp"/>      
        <script>
            $(document).on("click","#type_filter li,#instruction_ref_filter li,#grade_filter li,#status_filter li",function(){
                doDataTableFilter(mytable);
            }).on("change",".date_picker_text",function(){
                doDataTableFilter(mytable);
            }).on("click",".clear_date_filter",function(){
                $(this).siblings("input").val("");
                doDataTableFilter(mytable);
            });
            var mytable;
            $(document).ready(function() {
                getFilterValue($("#type_filter li.chosen").attr("id").split("_")[2]);
                mytable = $("#allocation_table").dataTable({
                    "bAutoWidth": false,
                    "aLengthMenu": [[10, 25, 50], [10, 25, 50]],
                    "bProcessing": true,
                    "bServerSide": true,
                    "sAjaxSource": getAbsolutePath() + "/allocation_list_source.htm",
                    "fnServerParams": function(aoData) {
                        aoData.push(
                                {"name": "inst_id", "value": $("#instruction_ref_filter li.chosen").attr("id").split("_")[1]},
                                {"name": "grade", "value": $("#grade_filter li.chosen").attr("id").split("_")[1]},
                                {"name": "status", "value": $("#status_filter li.chosen").attr("id").split("_")[1]},
                                {"name": "type", "value": $("#type_filter li.chosen").attr("id").split("_")[2]},
                                {name:"from_date",value:$("#from_date_filter").val()},
                                {name:"to_date",value:$("#to_date_filter").val()}
                        );
                    },
                    "aoColumnDefs": [
                        {"sWidth": "30px", "aTargets": [0]},
                        {"sWidth": "100px", "aTargets": [1,3,4,5,6,7,8]},
                        {"sWidth": "207px", "aTargets": [2]},
                        {"sClass": "formatNumber", "aTargets": [4,5,6]}
                    ],
                    "fnDrawCallback": function(oSettings, json) {
                        //alert("drawback");
                        $("input[type=button]").button();
                        $("td.formatNumber").each(function() {
                            var vl = accounting.toFixed(parseFloat($(this).text()), 2);
                            $(this).text(accounting.formatMoney(vl, ""));
                        });
                        $("tbody tr[class*=data_row]").each(function() {
                            $(this).attr('name', 'perm_8');
                            $(this).attr("title", "Double Click To View Detail");
                        });
                        $("tr[class*=data_row]").auth({action:"dblclick"});
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
//                    "sDom": 'Rlfrtip',
//                    "oColVis": {
//                        "fnLabel": function(index, title, th) {
//                            if (title != "") {
//                                return (index + 1) + '. ' + title;
//                            }
//                        }
//                    }
                });
               
            });
            $("#type_filter li").on("click", function() {
                console.log($(this).attr("id").split("_")[2]);
                getFilterValue($(this).attr("id").split("_")[2]);
            });
            $(document).on("dblclick", ".data_row", function() {
                var ins_id = $(this).attr("id").split("_")[1];
                var ins_type = $("#type_filter li.chosen").attr("id").split("_")[2];
                create_hidden_form({"id": "allocation_form_detail", "action": getAbsolutePath() + "/Allocation/detail.htm", "method": "POST"}, [{"id": "ins_id", "name": "ins_id", "value": ins_id}, {"id": "ins_type", "name": "ins_type", "value": ins_type},{id:"fw_type",name:"fw_type",value:"alloc"}]);
                $("#allocation_form_detail").submit();
            });
            function getFilterValue(type_id) {
                sendJsonRequest("instruction_ref_filter", "alloc_get_inst_by_type",
                        {type: type_id}
                , 0, [
                    {name: "sendJsonRequest", params: ["grade_filter", "alloc_get_grade_by_type",
                            {type: type_id}
                            , 0]}
                ]);
            }
        </script>
    </body>
</html>

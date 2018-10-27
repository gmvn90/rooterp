<%-- 
    Document   : reports
    Created on : Jul 17, 2013, 9:38:37 AM
    Author     : minhdn
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title></title>
    </head>
    <body>
        <jsp:include page="header.jsp">
            <jsp:param name="url" value="17"/>
            <jsp:param name="page" value="6"/>
        </jsp:include>
        <form id="report_" method="POST">
            <div style="width: 680px ! important; padding: 15px; background: none repeat scroll 0% 0% white; height: auto; overflow: hidden" class="container_16 border" id="wrapper">
                <div style="width: 190px;padding-bottom: 10px; height: 414px" class="left border">
                    <div class="title center">
                        Report
                    </div>
                    <div class="pc100">
                        <ul class="ref_list" id="type_filter" style="margin-left: 5px; padding-left: 10px; width: 158px !important; height: 370px">
                            <li class="chosen" id="type_stock">Export/Import Report</li>
                        </ul>
                    </div>
                </div>
                <div style="width: 470px !important; margin-right: 0px !important;font-size: 9pt !important" class="right grid_14 border">
                    <div class="title center">
                        Detail
                    </div>
                    <div id="rp_detail" style="min-height: 400px">

                        <div style="text-align: center; width: 400px !important; min-height: 300px; height: auto; overflow: hidden; border-top: 1px solid gray" class="center">
                            <div style="margin-top: 50px; margin-left: 70px">
                                <select name="exim_report_type" id="exim_report_type">
                                    <option value="IM">Import</option>
                                    <option value="EX">Export</option>
                                </select>
                                <select id="client_exim_report"></select><br/>
                                
                                <ul style="width: 50%; line-height: 20px" class="list_date_filter">
                                    <li>
                                        <span style="margin-right: 40px">From Date</span>
                                        <label>
                                            <input type="text" id="from_date_filter" class="date_picker_text" readonly="true" val=""/>
                                            <a href="javascript:void(0);" class="clear_date_filter">Reset</a>
                                        </label>
                                    </li>
                                    <li>
                                        <span style="margin-right: 40px">To Date</span>
                                        <label>
                                            <input type="text" id="to_date_filter" class="date_picker_text" readonly="true" val=""/>
                                            <a href="javascript:void(0);" class="clear_date_filter">Reset</a>
                                        </label>
                                    </li>
                                    <li><input type="checkbox" id="isDetail" value="0"/> In Detail</li>
                                </ul>
                                
                            </div>
                            
                            <input class="" type="button" id="print_exim_report" value="Print" name="" style="margin-right: 50px; margin-top: 10px"/>
                        </div>
                    </div>
                </div>
            </div>
        </form>
        <jsp:include page="footer.jsp"/>
        <script>
            $(document).ready(function () {
                generateSelection("#client_exim_report", "UpdateClient", "client_-1", {
                    all: true, action: "getClientList", type: "select", prefix: "client_", allText: "All"
                });
            }).on("click", ".clear_date_filter", function () {
                $(this).siblings("input").val("");
            }).on("click", "#print_exim_report", function () {
                var client_check = $("#client_exim_report option:selected").val();
                if (client_check === "0" || client_check === "-1") {
                    client_check = "-1";
                    //$.error("Please choose a specific company!");
                }
                $.sendRequest({
                    status: "export",
                    action: "get_exim_report",
                    data: {
                        "type": $("#exim_report_type").val(),
                        "from": $("#from_date_filter").val(),
                        "to": $("#to_date_filter").val(),
                        "client": $("#client_exim_report option:selected").val(),
                        "client_name": $("#client option:selected").html(),
                        "isDetail": $("#isDetail").prop("checked")
                    }
                });

            });
        </script>
    </body>
</html>

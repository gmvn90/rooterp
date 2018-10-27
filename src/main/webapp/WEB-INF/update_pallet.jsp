<%-- 
    Document   : update_pallet
    Created on : Sep 10, 2013, 3:22:08 PM
    Author     : kiendn
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Update Pallet</title>
    </head>
    <body>
        <jsp:include page="header.jsp">
            <jsp:param name="url" value="22"/>
            <jsp:param name="page" value="24"/>
        </jsp:include>
        <style>
            table#update_pallet_table{margin-left: auto; margin-right: auto; border-spacing: 15px}
            table#update_pallet_table span{font-size: 13px}
            table#log_table{width: 100%; font-size: 13px}
            table#log_table, table#log_table th, table#log_table td {border-collapse: collapse;  border: 1px solid black}
            table#log_table tbody tr td {text-align: center}
        </style>
        <form id="pallet_form">
            <div style="width: 600px ! important; padding: 15px; background: none repeat scroll 0% 0% white; height: auto; overflow: hidden; min-height: 300px;" class="container_16 border" id="wrapper">
                <h3>Update Pallet</h3>
                <hr/>
                <table id="update_pallet_table">
                    <tr>
                        <td><span>Pallet</span></td>
                        <td><input type="text" id="pallet_ref"/></td>
                    </tr>
                    <tr>
                        <td><span>Weight</span></td>
                        <td><input type="text" id="weight"/></td>
                    </tr>
                    <tr>
                        <td><input type="button" id="btn_new" value="New"/></td>
                        <td><input type="button" id="btn_save" value="Save"/></td>
                    </tr>
                </table>
                <hr/>
                <h3>Recent Weighted Pallets</h3>
                <table id="log_table">
                    <thead>
                        <tr>
                            <th>Pallet</th>
                            <th>Date</th>
                            <th>Weight</th>
                            <th>Status</th>
                        </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </div>
        </form>
        <jsp:include page="footer.jsp"></jsp:include>
        <script>
            $.templates({
                log_row: "<tr><td>{{:name}}</td><td>{{:pdate}}</td><td>{{:pvalue}}</td><td style='color: {{if pstatus == 1}}black;{{else}}red;{{/if}}'>{{if pstatus == 1}}Completed{{else}}Failed{{/if}}</td></tr>",
                new_pallet: "<p>Enter number of pallets that need to be added into the system</p>  <input type='text' id='txt_new_pallet' class='numberOnly'/> <input type='button' id='btn_add_pallet' value='Add'/>"
            });
            $(document).on("keypress", "body", function(event) {
                if (event.which == 13) {
                    event.preventDefault();
                    savePallet();
                }
            }).on("click", "#btn_save", function() {
                savePallet();
            }).on("click", "#btn_new", function() {
                $.data(document.body, "new_pallet_ui", {});
//                callDialog("#pallet_form", $.templates.new_pallet, "new_pallet_ui", {css: [
//                        {"border": "1px solid"},
//                        {"color": "#9F6000"},
//                        {"background-color": "#FEEFB3"},
//                        {"font-size": "13px"},
//                        {"display": "block"},
//                        {"width": "400px"},
//                        {"left": "45%"},
//                        {"top": "30%"}
//                    ], autoCloseOverlay: true});
                callSimpleDialog({
                    target: "#pallet_form",
                    template: "<p>Enter number of pallets that need to be added into the system</p>  <input type='text' id='txt_new_pallet' class='numberOnly'/> <input type='button' id='btn_add_pallet' value='Add'/>",
                    styles: [
                        {"border": "1px solid"},
                        {"color": "#9F6000"},
                        {"background-color": "#FEEFB3"},
                        {"font-size": "13px"},
                        {"display": "block"},
                        {"width": "400px"},
                        {"left": "45%"},
                        {"top": "30%"}
                    ]
                });
            }).on("click", "#btn_add_pallet", function() {
                sendJsonRequest("", "add_new_pallet", {quantity: $("#txt_new_pallet").val()}, 5, [
                    {name: "check_result", params: []}
                ], true, ["adding_result"]);
            });
            function savePallet() {
                if ($("#pallet_ref").val() != "" && $("#weight").val() != "") {
                    sendJsonRequest("", "do_update_pallet", {name: $("#pallet_ref").val(), value: $("#weight").val()}, 5, [
                        {name: "render_log_row", params: []}
                    ], true, ["row"]);
                }
            }
            function render_log_row() {
                $("#log_table tbody").prepend($.templates.log_row.render($.data(document.body, "row")));
            }
            function check_result() {
                if ($("#detailBox").length > 0) {
                    $("#detailBox").remove();
                }
                if ($.data(document.body, "adding_result") == 1) {
                    callMessageDialog({type:"success",message:"Adding succeeded"});
                } else {
                    callMessageDialog({type:"error",message:"Adding failed"});
                }
                if ($("#detailBox").length > 0) {
                    $(this).remove();
                }
            }
        </script>
    </body>
</html>
